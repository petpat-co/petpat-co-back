package com.smile.petpat.post.rehoming.service;

import com.smile.petpat.image.domain.ImageUploadManager;
import com.smile.petpat.image.domain.ImageUploader;
import com.smile.petpat.post.category.domain.CategoryGroup;
import com.smile.petpat.post.category.domain.PetCategory;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.common.CommonUtils;
import com.smile.petpat.post.common.status.PostStatus;
import com.smile.petpat.post.rehoming.domain.*;
import com.smile.petpat.post.rehoming.dto.RehomingPagingDto;
import com.smile.petpat.post.rehoming.dto.RehomingResDto;
import com.smile.petpat.post.rehoming.repository.RehomingRepository;
import com.smile.petpat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RehomingServiceImpl implements RehomingService {
    private final ImageUploadManager imageUploadManager;
    private final ImageUploader imageUploader;
    private final RehomingReader rehomingReader;
    private final RehomingStore rehomingStore;
    private final CommonUtils commonUtils;
    private final RehomingRepository rehomingRepository;

    // 1. 분양 글 등록
    @Override
    @Transactional
    public void registerRehoming(String userEmail, RehomingCommand rehomingCommand) {
        // userChk
        User user = commonUtils.userChk(userEmail);
        // 1-1. 게시물 등록
        CategoryGroup category = rehomingReader.readCategoryById(rehomingCommand.getCategory());
        PetCategory type = rehomingReader.readPetTypeById(rehomingCommand.getType());
        Rehoming initRehoming = rehomingCommand.toRegisterEntity(user, category, type);
        Rehoming rehoming = rehomingStore.store(initRehoming);
        // 1-2. 이미지 등록
        Long postId = rehoming.getRehomingId();
        imageUploadManager.uploadPostImage(rehomingCommand.getRehomingImg(), postId, PostType.REHOMING);
    }

    // 2-1. 분양 글 목록 조회 (회원)
    @Override
    public RehomingPagingDto listRehomingForMember(String userEmail, Pageable pageable) {
        return new RehomingPagingDto(rehomingRepository.rehomingListForMember(userEmail, pageable));
    }

    // 2-2. 분양 글 목록 조회 (비회원)
    @Override
    public RehomingPagingDto listRehoming(Pageable pageable) {
        return new RehomingPagingDto(rehomingRepository.rehomingList(pageable));
    }

    // 3-1. 분양 글 상세 조회 (회원)
    @Override
    @Transactional
    public RehomingResDto detailRehomingForMember(Long postId, String userEmail) {
        Rehoming rehoming = rehomingReader.readRehomingById(postId);
        // 조회수 계산
        rehoming.updateViewCnt(rehoming);
        return getResDto(userEmail, postId, rehoming);
    }

    // 3-2. 분양 글 상세 조회 (비회원)
    @Override
    @Transactional
    public RehomingResDto detailRehoming(Long postId) {
        Rehoming rehoming = rehomingReader.readRehomingById(postId);
        // 조회수 계산
        rehoming.updateViewCnt(rehoming);
        List<String> imgList = imageUploader.readImgList(postId, PostType.REHOMING);
        return new RehomingResDto(rehoming, imgList,
                commonUtils.getLikesCnt(postId, PostType.REHOMING));
    }

    // 4. 분양 글 수정
    @Override
    public RehomingResDto updateRehoming(String userEmail, Long postId, RehomingCommand rehomingCommand) {
        // 4-1. 게시글 존재 유무 판단
        Rehoming rehoming = rehomingReader.readRehomingById(postId);
        rehomingReader.userChk(userEmail, rehoming);
        // 4-2. 게시글 수정
        CategoryGroup category = rehomingReader.readCategoryById(rehomingCommand.getCategory());
        PetCategory type = rehomingReader.readPetTypeById(rehomingCommand.getType());
        PostStatus status = rehoming.getStatus();
        User user = commonUtils.userChk(userEmail);
        Rehoming initRehoming = rehomingCommand.toUpdateEntity(user, postId, category, type, status);
        rehoming.update(initRehoming);
        // 4-3. 이미지 수정
        List<MultipartFile> rehomingImg = rehomingCommand.getRehomingImg();
        imageUploadManager.updateImage(rehomingImg, postId, PostType.REHOMING);
        return getResDto(userEmail, postId, rehoming);
    }

    // 5. 분양 글 삭제
    @Override
    @Transactional
    public void deleteRehoming(String userEmail, Long postId) {
        // 5-1. 게시글 삭제
        rehomingStore.delete(userEmail, postId);
        // 5-2. 해당 게시글 이미지 삭제
        imageUploadManager.removePostImage(postId, PostType.REHOMING);
        // 5-3. 해당 게시글의 좋아요, 북마크 삭제
        commonUtils.delLikes(postId, PostType.REHOMING.toString(), userEmail);
        commonUtils.delBookmark(postId, PostType.REHOMING.toString(), userEmail);
    }

    private RehomingResDto getResDto(String userEmail, Long postId, Rehoming rehoming) {
        List<String> imgList = imageUploader.readImgList(postId, PostType.REHOMING);
        return new RehomingResDto(rehoming, imgList,
                commonUtils.LikePostChk(postId, PostType.REHOMING, userEmail),
                commonUtils.BookmarkPostChk(postId, PostType.REHOMING, userEmail),
                commonUtils.getLikesCnt(postId, PostType.REHOMING));
    }

    // 6. 분양 게시글 상태값 변경

    // 6-1. 분양 중
    @Transactional
    @Override
    public void updateStatusFinding(String userEmail, Long postId) {
        Rehoming rehoming = rehomingReader.readRehomingById(postId);
        rehomingReader.userChk(userEmail, rehoming);
        rehoming.isFinding();
    }

    // 6-2. 분양 예약 중
    @Transactional
    @Override
    public void updateStatusReserved(String userEmail, Long postId) {
        Rehoming rehoming = rehomingReader.readRehomingById(postId);
        rehomingReader.userChk(userEmail, rehoming);
        rehoming.isReserved();
    }

    // 6-3. 분양 예약 완료
    @Transactional
    @Override
    public void updateStatusMatched(String userEmail, Long postId) {
        Rehoming rehoming = rehomingReader.readRehomingById(postId);
        rehomingReader.userChk(userEmail, rehoming);
        rehoming.isMatched();
    }

    // 7-1. 분양 게시글 카테고리별 목록 조회 (회원)
    @Override
    public RehomingPagingDto getCategoryListForMember(String userEmail, Long categoryId, Long typeId, Pageable pageable) {
        Page<RehomingInfo> rehomingInfos = rehomingRepository.rehomingCategoryListForMember(userEmail, categoryId, typeId, pageable);
        return new RehomingPagingDto(rehomingInfos);
    }

    // 7-2. 분양 게시글 카테고리별 목록 조회 (비회원)
    @Override
    public RehomingPagingDto getCategoryList(Long categoryId, Long typeId, Pageable pageable) {
        return new RehomingPagingDto(rehomingRepository.rehomingCategoryList(categoryId, typeId, pageable));
    }
}