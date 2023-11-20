package com.smile.petpat.post.qna.service;

import com.smile.petpat.image.domain.ImageUploadManager;
import com.smile.petpat.post.qna.domain.*;
import com.smile.petpat.post.qna.repository.QnaRepository;
import com.smile.petpat.post.rehoming.dto.RehomingPagingDto;
import com.smile.petpat.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService{

    private final QnaStore qnaStore;
    private final QnaReader qnaReader;
    private final ImageUploadManager imageUploadManager;

    @Override
    @Transactional
    public void registerQna(QnaCommand qnaCommand, User user) {
        Qna initQna = qnaCommand.toRegisterEntity(user);
        Qna qna = qnaStore.store(initQna);

        imageUploadManager.uploadPostImage(qnaCommand.getImages(), qna.getQnaId(), qna.getPostType());
    }

    @Override
    public RehomingPagingDto listQna(User user, Pageable pageable) {
        Page<QnaInfo.QnaList> listQna = qnaReader.readQnaList(user, pageable);
        RehomingPagingDto dto = new RehomingPagingDto(listQna);
        return dto;
    }

}
