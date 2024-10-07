package com.itx.cases.impl;

import com.itx.cases.IGetPriceUsecase;
import com.itx.core.dto.PriceDto;
import com.itx.core.exceptions.ErrorEnum;
import com.itx.core.exceptions.NotFoundException;
import com.itx.core.exceptions.ValidationException;
import com.itx.core.mapper.PriceMapper;
import com.itx.provider.JPAPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class GetPriceUsecase implements IGetPriceUsecase {

    @Autowired
    private JPAPriceRepository jpaPriceRepository;
    @Autowired
    private PriceMapper priceMapper;

    @Override
    public PriceDto getProductPrice(Integer brandId,
                                    LocalDateTime applicationDate,
                                    Long productId) {
        validate(brandId, applicationDate, productId);
        return jpaPriceRepository.findBestPrice(brandId, productId, applicationDate)
                .map(priceMapper::priceToPriceDto)
                .orElseThrow(() -> new NotFoundException(ErrorEnum.PRICE_NOT_FOUND_ERROR));
    }

    private void validate(Integer brandId,
                          LocalDateTime applicationDate,
                          Long productId) {
        if (Objects.isNull(brandId) || brandId < 1) throw new ValidationException(ErrorEnum.INVALID_BRAND_ERROR);
        if (Objects.isNull(productId) || productId < 1) throw new ValidationException(ErrorEnum.INVALID_PRODUCT_ERROR);
        if (Objects.isNull(applicationDate) || LocalDateTime.now().minusYears(5L).isAfter(applicationDate))
            throw new ValidationException(ErrorEnum.INVALID_APPLICATION_DATE_ERROR);
    }
}
