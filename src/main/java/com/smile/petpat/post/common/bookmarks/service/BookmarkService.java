package com.smile.petpat.post.common.bookmarks.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface BookmarkService {
    HashMap<String, String> bookmarkPost(String postType, Long postId, String userEmail);
}
