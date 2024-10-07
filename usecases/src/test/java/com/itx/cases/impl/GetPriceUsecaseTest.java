package com.itx.cases.impl;

import com.itx.core.dto.PriceDto;
import com.itx.core.exceptions.ErrorEnum;
import com.itx.core.exceptions.NotFoundException;
import com.itx.core.exceptions.ValidationException;
import com.itx.core.mapper.PriceMapper;
import com.itx.core.model.Brand;
import com.itx.core.model.Price;
import com.itx.core.model.Product;
import com.itx.provider.JPAPriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPriceUsecaseTest {

    @InjectMocks
    private GetPriceUsecase getPriceUsecase;
    @Mock
    private JPAPriceRepository jpaPriceRepository;
    @Spy
    private PriceMapper priceMapper = Mappers.getMapper(PriceMapper.class);

    @Test
    void tryGetProductPrice_WhenBrandIsNotValid() {
        Integer brandId = -2;
        Long productId = 3456L;
        LocalDateTime date = LocalDateTime.now();
        final var exception = assertThrows(ValidationException.class, () ->
                getPriceUsecase.getProductPrice(brandId, date, productId));
        assertNotNull(exception);
        assertEquals(ErrorEnum.INVALID_BRAND_ERROR, exception.getError());
        verify(jpaPriceRepository, never()).findBestPrice(any(Integer.class), anyLong(), any(LocalDateTime.class));
    }

    @Test
    void tryGetProductPrice_WhenProductIsNotValid() {
        Integer brandId = 2;
        Long productId = -3456L;
        LocalDateTime date = LocalDateTime.now();
        final var exception = assertThrows(ValidationException.class, () ->
                getPriceUsecase.getProductPrice(brandId, date, productId));
        assertNotNull(exception);
        assertEquals(ErrorEnum.INVALID_PRODUCT_ERROR, exception.getError());
        verify(jpaPriceRepository, never()).findBestPrice(any(Integer.class), anyLong(), any(LocalDateTime.class));
    }

    @Test
    void tryGetProductPrice_WhenDateIsNotValid() {
        Integer brandId = 2;
        Long productId = 456L;
        LocalDateTime date = LocalDateTime.now().minusYears(20L);
        final var exception = assertThrows(ValidationException.class, () ->
                getPriceUsecase.getProductPrice(brandId, date, productId));
        assertNotNull(exception);
        assertEquals(ErrorEnum.INVALID_APPLICATION_DATE_ERROR, exception.getError());
        verify(jpaPriceRepository, never()).findBestPrice(any(Integer.class), anyLong(), any(LocalDateTime.class));
    }

    @Test
    void tryGetProductPrice_WhenProductNotFound() {
        Integer brandId = 2;
        Long productId = 456L;
        LocalDateTime date = LocalDateTime.now();
        Mockito.when(jpaPriceRepository.findBestPrice(2, 456L, date)).thenReturn(Optional.empty());
        final var exception = assertThrows(NotFoundException.class, () ->
                getPriceUsecase.getProductPrice(brandId, date, productId));
        assertNotNull(exception);
        assertEquals(ErrorEnum.PRICE_NOT_FOUND_ERROR, exception.getError());
        verify(jpaPriceRepository, times(1)).findBestPrice(any(Integer.class), anyLong(), any(LocalDateTime.class));
    }

    @Test // refactor to spy in mappers
    void tryGetProductPrice_WhenAllIsOk() {
        Integer brandId = 2;
        Long productId = 456L;
        LocalDateTime date = LocalDateTime.now();
        Price price = new Price();
        BigDecimal priceVal = BigDecimal.valueOf(45.67);
        price.setPrice(priceVal);
        Product product = new Product();
        product.setId(productId);
        price.setProduct(product);
        Brand brand = new Brand();
        brand.setId(brandId);
        price.setBrand(brand);
        Mockito.when(jpaPriceRepository.findBestPrice(2, 456L, date)).thenReturn(Optional.of(price));
        PriceDto result = getPriceUsecase.getProductPrice(brandId, date, productId);
        assertNotNull(result);
        assertEquals(brandId, result.getBrandId());
        assertEquals(productId, result.getProductId());
        assertEquals(priceVal, result.getPrice());
        verify(jpaPriceRepository, times(1)).findBestPrice(any(Integer.class), anyLong(), any(LocalDateTime.class));
    }
}
