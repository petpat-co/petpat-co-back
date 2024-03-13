package com.smile.petpat.post.rehoming.service;

import com.smile.petpat.post.rehoming.domain.Rehoming;
import com.smile.petpat.post.rehoming.domain.RehomingStore;
import com.smile.petpat.post.rehoming.repository.RehomingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RehomingStoreImpl implements RehomingStore {
    private final RehomingRepository rehomingRepository;
    private final RehomingReaderImpl rehomingReader;

    /* 1. 분양 게시글 등록*/
    @Override
    public Rehoming store(Rehoming initRehoming) {
        return rehomingRepository.save(initRehoming);
    }

    /* 2. 분양 게시글 삭제 */
    @Override
    public void delete(String userEmail, Long postId) {
        Rehoming rehoming = rehomingReader.readRehomingById(postId);
        rehomingReader.userChk(userEmail, rehoming);
        rehomingRepository.deleteById(postId);
    }
}
