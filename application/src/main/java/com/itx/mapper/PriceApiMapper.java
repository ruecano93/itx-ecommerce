package com.itx.mapper;

import com.itx.core.dto.PriceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.openapitools.model.PriceApi;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface PriceApiMapper {

    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "mapLocalToOffset")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "mapLocalToOffset")
    PriceApi priceDtoToPriceApi(PriceDto priceDto);

    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "mapOffsetToLocal")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "mapOffsetToLocal")
    PriceDto priceApiToPriceDto(PriceApi priceApi);

    @Named("mapLocalToOffset")
    default OffsetDateTime mapLocalToOffset(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atOffset(ZoneOffset.UTC) : null;
    }
    @Named("mapOffsetToLocal")
    default LocalDateTime mapOffsetToLocal(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toLocalDateTime() : null;
    }
}
