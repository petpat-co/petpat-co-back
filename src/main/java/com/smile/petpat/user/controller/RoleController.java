package com.smile.petpat.user.controller;

import com.smile.petpat.user.service.UserDetailsImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")

public class RoleController {

    @PreAuthorize("hasRole('GUEST')")
    @RequestMapping(value = "/guest", method = RequestMethod.POST)
    public String guest() {
        return "guest입니다.";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String user(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return "user인" + userDetails.getUser().getNickname()+"입니다.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public String admin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return "admin인" + userDetails.getUser().getNickname()+"입니다.";

    }
}
