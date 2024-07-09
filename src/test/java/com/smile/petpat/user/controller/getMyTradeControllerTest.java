package com.smile.petpat.user.controller;

import com.smile.petpat.user.domain.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProfileController.class)
@ActiveProfiles("test")
public class getMyTradeControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private ProfileService profileService;


}
