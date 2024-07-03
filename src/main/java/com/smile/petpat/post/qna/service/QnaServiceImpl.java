package com.smile.petpat.post.qna.service;

import com.smile.petpat.image.dto.ImageResDto;
import com.smile.petpat.image.service.ImageService;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.common.CommonUtils;
import com.smile.petpat.post.qna.domain.*;
import com.smile.petpat.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService{

    private final QnaStore qnaStore;
    private final QnaReader qnaReader;
    private final CommonUtils commonUtils;
    private final ImageService imageService;

    @Override
    @Transactional
    public void registerQna(QnaCommand qnaCommand, User user) {
        User validedUser = commonUtils.userChk(user.getUserEmail());
        Qna initQna = qnaCommand.toRegisterEntity(validedUser);
        Qna qna = qnaStore.store(initQna);

        imageService.uploadPostImage(qnaCommand.getImages(), qna.getQnaId(), qna.getPostType());
    }

//    @Override
//    public QnaInfo.QnaPagingListInfo listQna(User user, Pageable pageable) {
//        Page<QnaInfo.QnaList> listQna = qnaReader.readQnaList(user, pageable);
//        return new QnaInfo.QnaPagingListInfo(listQna);
//    }

    @Override
    public QnaInfo.QnaPagingListInfo listQna(Pageable pageable) {
        Page<QnaInfo.QnaList> listQna = qnaReader.readQnaList(pageable);
        return new QnaInfo.QnaPagingListInfo(listQna);
    }

    @Override
    @Transactional
    public QnaInfo.QnaDetail updateQna(User user, Long postId, QnaCommand qnaCommand) {
        //이미지를 제외한 Qna 게시글 수정
        Qna qna = qnaReader.userChk(postId, user.getId());
        Qna initQna = qnaCommand.toUpdateEntity(user, postId);
        qna.update(initQna);

        //이미지 수정
        imageService.updateImage(qnaCommand.getImages(),qnaCommand.getDeletedImageId(), postId,PostType.QNA);
        return getQnaInfo(postId, user, qna);

    }


    @Override
    @Transactional
    public QnaInfo.QnaDetail detailQna(Long postId) {
        Qna qna = qnaReader.readQnaById(postId);

        // 조회수 계산
        qna.updateViewCnt(qna);

        QnaInfo.QnaDetail qnaDetail = qnaReader.readQnaDetail(postId);
        List<ImageResDto> imageList = imageService.getImagesByPost(postId, qna.getPostType());

        return new QnaInfo.QnaDetail(qnaDetail, imageList);
    }

    @Override
    @Transactional
    public void deleteQna(Long postId, User user) {
        qnaStore.delete(postId, user.getId());
        imageService.removePostImage(postId, PostType.QNA);
    }

    private QnaInfo.QnaDetail getQnaInfo(Long postId, User user, Qna qna) {
        List<ImageResDto> imgList = imageService.getImagesByPost(postId, PostType.QNA);
        return new QnaInfo.QnaDetail();
    }


}
