package com.smile.petpat.post.common.Address.controller;

import com.smile.petpat.annotation.WithMockCustomUser;
import com.smile.petpat.post.common.Address.Dto.AddressReqDto;
import com.smile.petpat.post.common.Address.service.AddressService;
import com.smile.petpat.post.trade.domain.TradeInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@WebMvcTest(AddressController.class)
public class getTradesByAddressConrollerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private AddressService addressService;
    private TradeInfo.TradePagingListInfo expectedResult = new TradeInfo.TradePagingListInfo();

    @BeforeEach
    void setup(){
        Mockito.when(addressService.getTradesByAddress(Mockito.any(AddressReqDto.class),Mockito.any(Pageable.class),Mockito.anyString()))
                .thenReturn(expectedResult);
    }

    @Test
    @DisplayName("SUCCESS")
    @WithMockCustomUser
    void success() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/address/trade")
                .param("province","서울특별시")
                .param("city","")
                .param("district","마포구")
                .param("town","연남동")
                .param("page","0")
                .param("size","10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("SUCCESS"));
    }

    @Nested
    @DisplayName("FAILURE")
    class Failure{
        @Test
        @DisplayName("FAIL_USER_NOT_EXIST")
        void fail() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/address/trade")
                            .param("province", "서울특별시")
                            .param("city", "")
                            .param("district", "마포구")
                            .param("town", "연남동")
                            .param("page", "0")
                            .param("size", "10")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is(401));
        }
    }
}
