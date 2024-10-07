package com.itx.cases;

import com.itx.core.dto.PriceDto;

import java.time.LocalDateTime;

public interface IGetPriceUsecase {

    PriceDto getProductPrice(Integer brandId, LocalDateTime applicationDate, Long productId);
}
