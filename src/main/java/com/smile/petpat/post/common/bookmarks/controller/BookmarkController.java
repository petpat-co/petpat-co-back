package com.smile.petpat.post.common.bookmarks.controller;

import com.smile.petpat.common.response.SuccessResponse;
import com.smile.petpat.post.common.bookmarks.service.BookmarkServiceImpl;
import com.smile.petpat.user.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks")
@Slf4j
public class BookmarkController {
    private final BookmarkServiceImpl bookmarkService;

    @RequestMapping(value = "/{postType}/{postId}", method = RequestMethod.POST)
    public SuccessResponse<HashMap<String, String>> isBookmark(@PathVariable String postType, @PathVariable Long postId,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return SuccessResponse.success(bookmarkService.bookmarkPost(postType, postId, userDetails.getUsername()), "OK");
    }
}
