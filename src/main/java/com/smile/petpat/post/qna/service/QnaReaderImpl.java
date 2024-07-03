package com.smile.petpat.post.qna.service;

import com.smile.petpat.post.qna.domain.Qna;
import com.smile.petpat.post.qna.domain.QnaInfo;
import com.smile.petpat.post.qna.domain.QnaReader;
import com.smile.petpat.post.qna.repository.QnaRepository;
import com.smile.petpat.post.trade.domain.Trade;
import com.smile.petpat.post.trade.domain.TradeInfo;
import com.smile.petpat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class QnaReaderImpl implements QnaReader {
    private final QnaRepository qnaRepository;

    @Override
    public Qna readQnaById(Long qnaId) {
        return qnaRepository.findById(qnaId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );
    }

//    @Override
//    public Page<QnaInfo.QnaList> readQnaList(User user, Pageable pageable) {
//        return qnaRepository.qnaList(user.getId(), pageable);
//    }

    @Override
    public Page<QnaInfo.QnaList> readQnaList(Pageable pageable) {
        return qnaRepository.qnaList(pageable);
    }


    public QnaInfo.QnaDetail readQnaDetail(Long postId) {
        readQnaById(postId);
        return qnaRepository.qnaDetail(postId);
    }

    @Override
    public Qna userChk(Long postId, Long userId){
        Qna qna = readQnaById(postId);
        if(!qna.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        return qna;
    }
}
