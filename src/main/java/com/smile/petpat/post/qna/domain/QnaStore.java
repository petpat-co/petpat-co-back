package com.smile.petpat.post.qna.domain;

public interface QnaStore {
    Qna store(Qna qna);

    void delete(Long qnaId, Long userId);

}
