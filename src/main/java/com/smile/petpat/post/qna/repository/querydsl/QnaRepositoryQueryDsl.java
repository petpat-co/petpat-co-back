package com.smile.petpat.post.qna.repository.querydsl;

import com.smile.petpat.post.qna.domain.QnaInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QnaRepositoryQueryDsl {
//    Page<QnaInfo.QnaList> qnaList(Long userId, Pageable pageable);

    Page<QnaInfo.QnaList> qnaList(Pageable pageable);

    QnaInfo.QnaDetail qnaDetail(Long postId);

}
