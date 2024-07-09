package com.smile.petpat.user.controller;

import com.smile.petpat.annotation.WithMockCustomUser;
import com.smile.petpat.user.domain.ProfileService;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.dto.ProfileDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

@WebMvcTest(ProfileController.class)
@ActiveProfiles("test")
public class getMyTradeControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private ProfileService profileService;
    private Pageable pageable = PageRequest.of(0,10);
    private Page<ProfileDto.TradeResponse> expectedResult
            = new PageImpl<>(Collections.emptyList(),pageable,0);

    @BeforeEach
    void setup(){
        Mockito.when(profileService.getMyTrade(Mockito.any(User.class),Mockito.any(Pageable.class)))
                .thenReturn(expectedResult);
    }

    @Test
    @DisplayName("SUCCESS")
    @WithMockCustomUser
    void success() throws  Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/profile/trade")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("SUCCESS"));
    }

    @Test
    @DisplayName("FAIL_USER_NOT_EXIST")
    void fail() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/profile/trade")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(401));
    }
}
