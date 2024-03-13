package com.smile.petpat.post.common.bookmarks.service;

import java.util.HashMap;

public interface BookmarkService {
    HashMap<String, String> bookmarkPost(String postType, Long postId, String userEmail);
}
