package com.smile.petpat.post.qna.domain;

import com.smile.petpat.post.trade.domain.TradeInfo;
import com.smile.petpat.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnaReader {
    Qna readQnaById(Long qnaId);

    void userChk(Long userId, Long qnaId);

    Page<QnaInfo.QnaList> readQnaList(User user, Pageable pageable);
}
