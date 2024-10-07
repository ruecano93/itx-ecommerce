package com.itx.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class PriceDto {

    private Integer id;

    private Long productId;

    private Integer brandId;

    private Long priceId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private BigDecimal price;

    private String currency;
}
