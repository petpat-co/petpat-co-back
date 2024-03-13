package com.smile.petpat.post.common.likes.service;

import com.smile.petpat.user.domain.User;

import java.util.HashMap;

public interface LikesService {
    HashMap<String, String> likePost(Long postId, String postType, String userEmail);
}
