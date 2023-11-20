package com.smile.petpat.post.qna.service;

import com.smile.petpat.post.qna.domain.QnaCommand;
import com.smile.petpat.post.rehoming.dto.RehomingPagingDto;
import com.smile.petpat.user.domain.User;
import org.springframework.data.domain.Pageable;

public interface QnaService {
    void registerQna(QnaCommand qnaCommand, User user);

    RehomingPagingDto listQna(User user, Pageable pageable);

}
