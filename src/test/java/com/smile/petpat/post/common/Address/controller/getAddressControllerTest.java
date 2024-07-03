package com.smile.petpat.post.common.Address.controller;

import com.smile.petpat.annotation.WithMockCustomUser;
import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.common.Address.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@WebMvcTest(AddressController.class)
public class getAddressControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private AddressService addressService;
    private List<String> expectedResult = new ArrayList<>();


    @BeforeEach
    void setup(){
        Mockito.when(addressService.getProvinceList())
                .thenReturn(expectedResult);

        Mockito.when(addressService.getCityList(Mockito.anyString()))
                .thenReturn(expectedResult);

        Mockito.when(addressService.getDistrictList(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(expectedResult);

        Mockito.when(addressService.getTownsList(Mockito.anyString(),Mockito.anyString(),Mockito.anyString()))
                .thenReturn(expectedResult);
    }

    @Test
    @DisplayName("SUCCESS_getProvinceTest")
    @WithMockCustomUser
    void success_getProvinceTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/address/province")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("SUCCESS"));
    }

    @Test
    @DisplayName("SUCCESS_getCityTest")
    @WithMockCustomUser
    void success_getDistrcitTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/address/city")
                .param("province","서울특별시")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("SUCCESS"));
    }

    @Test
    @DisplayName("SUCCESS_getDistrictTest")
    @WithMockCustomUser
    void success_getDistrictTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/address/district")
                        .param("province","서울특별시")
                        .param("city","")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("SUCCESS"));
    }

    @Test
    @DisplayName("SUCCESS_getTownTest")
    @WithMockCustomUser
    void success_getTownTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/address/town")
                        .param("province","서울특별시")
                        .param("city","")
                        .param("district","종로구")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("SUCCESS"));
    }
}
