package com.itx.core.mapper;

import com.itx.core.dto.PriceDto;
import com.itx.core.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    @Mapping(target = "productId", source = "price.product.id")
    @Mapping(target = "brandId", source = "price.brand.id")
    @Mapping(target = "priceId", source = "price.id")
    PriceDto priceToPriceDto(Price price);
}
