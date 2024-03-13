package com.smile.petpat.post.rehoming.domain;

public interface RehomingStore {
    Rehoming store(Rehoming rehoming);

    void delete(String userEmail, Long postId);
}
