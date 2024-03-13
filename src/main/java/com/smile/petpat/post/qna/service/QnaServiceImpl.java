package com.smile.petpat.post.qna.service;

import com.smile.petpat.image.domain.ImageUploadManager;
import com.smile.petpat.image.domain.ImageUploader;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.common.CommonUtils;
import com.smile.petpat.post.qna.domain.*;
import com.smile.petpat.post.qna.repository.QnaRepository;
import com.smile.petpat.post.rehoming.dto.RehomingPagingDto;
import com.smile.petpat.post.trade.domain.Trade;
import com.smile.petpat.post.trade.domain.TradeInfo;
import com.smile.petpat.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService{

    private final QnaStore qnaStore;
    private final QnaReader qnaReader;
    private final CommonUtils commonUtils;
    private final ImageUploader imageUploader;
    private final ImageUploadManager imageUploadManager;

    @Override
    @Transactional
    public void registerQna(QnaCommand qnaCommand, User user) {
        User validedUser = commonUtils.userChk(user.getUserEmail());
        Qna initQna = qnaCommand.toRegisterEntity(validedUser);
        Qna qna = qnaStore.store(initQna);

        imageUploadManager.uploadPostImage(qnaCommand.getImages(), qna.getQnaId(), qna.getPostType());
    }

    @Override
    public QnaInfo.QnaPagingListInfo listQna(User user, Pageable pageable) {
        Page<QnaInfo.QnaList> listQna = qnaReader.readQnaList(user, pageable);
        return new QnaInfo.QnaPagingListInfo(listQna);
    }

    @Override
    @Transactional
    public QnaInfo.QnaDetail updateQna(User user, Long postId, QnaCommand qnaCommand) {
        Qna qna = qnaReader.userChk(postId, user.getId());
        Qna initQna = qnaCommand.toUpdateEntity(user, postId);
        qna.update(initQna);

        List<MultipartFile> images = qnaCommand.getImages();
        imageUploadManager.updateImage(images, postId, PostType.QNA);
        return getQnaInfo(postId, user, qna);

    }


    @Override
    @Transactional
    public QnaInfo.QnaDetail detailQnaForUser(Long postId, User user) {
        Qna qna = qnaReader.readQnaById(postId);
        qna.updateViewCnt(qna);

        QnaInfo.QnaDetail qnaDetail = qnaReader.readQnaDetailForUser(user.getId(), postId);
        List<String> imageList = imageUploader.readImgList(postId, qna.getPostType());
        return new QnaInfo.QnaDetail(qnaDetail, imageList);

    }

    @Override
    @Transactional
    public QnaInfo.QnaDetail detailQna(Long postId) {
        List<String> imgList = imageUploader.readImgList(postId, PostType.QNA);
        Qna qna = qnaReader.readQnaById(postId);

        // 조회수 계산
        qna.updateViewCnt(qna);

        QnaInfo.QnaDetail qnaDetail = qnaReader.readQnaDetail(postId);
        List<String> imageList = imageUploader.readImgList(postId, qna.getPostType());

        return new QnaInfo.QnaDetail(qnaDetail, imageList);
    }

    @Override
    @Transactional
    public void deleteQna(Long postId, User user) {
        qnaStore.delete(postId, user.getId());
        imageUploadManager.removePostImage(postId, PostType.QNA);
    }

    private QnaInfo.QnaDetail getQnaInfo(Long postId, User user, Qna qna) {
        List<String> imgList = imageUploader.readImgList(postId, PostType.QNA);
        return new QnaInfo.QnaDetail();
    }


}
