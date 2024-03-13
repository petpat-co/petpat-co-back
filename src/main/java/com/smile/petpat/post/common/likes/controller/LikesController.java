package com.smile.petpat.post.common.likes.controller;

import com.smile.petpat.common.response.SuccessResponse;
import com.smile.petpat.post.common.likes.service.LikesServiceImpl;
import com.smile.petpat.user.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
@Slf4j
public class LikesController {
    private final LikesServiceImpl likeService;

    @RequestMapping(value = "/{postType}/{postId}", method = RequestMethod.POST)
    public SuccessResponse<HashMap<String, String>> isLikePost(@PathVariable String postType, @PathVariable Long postId,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails){
        return SuccessResponse.success(likeService.likePost(postId, postType, userDetails.getUsername()), "OK");
    }
}
