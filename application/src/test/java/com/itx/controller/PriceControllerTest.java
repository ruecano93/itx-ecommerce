package com.itx.controller;

import com.itx.EcommerceServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = EcommerceServiceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void tryGetPriceInfo_Test1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ecommerce/brand/1/product/price")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("applicationDate", "2020-06-14T10:00:00Z")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("35455"))
                .andExpect(jsonPath("$.brandId").value("1"))
                .andExpect(jsonPath("$.priceId").value("1"))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00Z"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59Z"))
                .andExpect(jsonPath("$.price").value("35.5"))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void tryGetPriceInfo_Test2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ecommerce/brand/1/product/price")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("applicationDate", "2020-06-14T16:00:00Z")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("35455"))
                .andExpect(jsonPath("$.brandId").value("1"))
                .andExpect(jsonPath("$.priceId").value("2"))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00Z"))
                .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00Z"))
                .andExpect(jsonPath("$.price").value("25.45"))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void tryGetPriceInfo_Test3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ecommerce/brand/1/product/price")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("applicationDate", "2020-06-14T21:00:00Z")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("35455"))
                .andExpect(jsonPath("$.brandId").value("1"))
                .andExpect(jsonPath("$.priceId").value("1"))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00Z"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59Z"))
                .andExpect(jsonPath("$.price").value("35.5"))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void tryGetPriceInfo_Test4() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ecommerce/brand/1/product/price")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("applicationDate", "2020-06-15T10:00:00Z")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("35455"))
                .andExpect(jsonPath("$.brandId").value("1"))
                .andExpect(jsonPath("$.priceId").value("3"))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T00:00:00Z"))
                .andExpect(jsonPath("$.endDate").value("2020-06-15T11:00:00Z"))
                .andExpect(jsonPath("$.price").value("30.5"))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void tryGetPriceInfo_Test5() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ecommerce/brand/1/product/price")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("applicationDate", "2020-06-16T21:00:00Z")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("35455"))
                .andExpect(jsonPath("$.brandId").value("1"))
                .andExpect(jsonPath("$.priceId").value("4"))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T16:00:00Z"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59Z"))
                .andExpect(jsonPath("$.price").value("38.95"))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }
}
