package com.itx.controller;

import com.itx.cases.IGetPriceUsecase;
import com.itx.core.dto.PriceDto;
import com.itx.mapper.PriceApiMapper;
import org.openapitools.api.EcommerceApi;
import org.openapitools.model.PriceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;


@RestController
public class ProductPriceController implements EcommerceApi {

    @Autowired
    private IGetPriceUsecase getPriceUsecase;
    @Autowired
    private PriceApiMapper priceApiMapper;

    @Override
    public ResponseEntity<PriceApi> getPriceInfo(Integer brandId,
                                                  OffsetDateTime applicationDate,
                                                  Long productId) {
        PriceDto priceDto = getPriceUsecase.getProductPrice(brandId, applicationDate.toLocalDateTime(), productId);
        return ResponseEntity.ok(priceApiMapper.priceDtoToPriceApi(priceDto));
    }

}
