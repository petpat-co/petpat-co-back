package com.smile.petpat.user.service;

import com.smile.petpat.common.exception.CustomException;
import com.smile.petpat.common.response.ErrorCode;
import com.smile.petpat.post.common.CommonUtils;
import com.smile.petpat.user.domain.ProfileService;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.domain.UserModify;
import com.smile.petpat.user.dto.ProfileDto;
import com.smile.petpat.user.dto.UserDto;
import com.smile.petpat.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository profileRepository;
    private final UserModify userModify;
    private final CommonUtils commonUtils;

    @Override
    public ProfileDto.ProfileResponse getProfile(User user) {
        return new ProfileDto.ProfileResponse(user);
    }

    @Override
    public void modifyProfile(UserDto.ModifyUserRequest request, User user) {
        userModify.modifyProfile(request,user);
    }

    @Override
    public Boolean passwordCheck(String password, User user) {
        return userModify.passwordCheck(password,user);
    }

    @Override
    public User modifyPassword(UserDto.ModifyPasswordRequest request, User user) {
        return modifyPassword(request,user);
    }

    @Override
    public Page<ProfileDto.RehomingResponse> getMyRehoming(User user, Pageable pageable) {
        commonUtils.userChk(user.getUserEmail());
        return profileRepository.getMyRehoming(user.getId(),pageable);
    }

    @Override
    public Page<ProfileDto.TradeResponse> getMyTrade(User user, Pageable pageable) {
        return profileRepository.getMyTrade(user.getId(),pageable);
    }

    @Override
    public Page<ProfileDto.QnaResponse> getMyQna(User user, Pageable pageable) {

        return profileRepository.getMyQna(user.getId(),pageable);
    }

    @Override
    public Page<ProfileDto.CommentResponse> getMyComment(User user, Pageable pageable) {

        return profileRepository.getMyComment(user.getId(),pageable);
    }


    @Override
    public Object getPostsByBookmark(User user, Pageable pageable, String postType) {
        if (postType.equals("TRADE")) {
            Page<ProfileDto.TradeResponse> results = profileRepository.getTradeByBookmark(user.getId(), pageable);
            return results;
        } else if (postType.equals("QNA")) {
            Page<ProfileDto.QnaResponse> results = profileRepository.getQnAByBookmark(user.getId(), pageable);
            return results;
        } else {
            Page<ProfileDto.RehomingResponse> results = profileRepository.getRehomingByBookmark(user.getId(), pageable);
            return results;
        }
    }

    @Override
    public Object getPostsByLike(User user, Pageable pageable, String postType) {
        if (postType.equals("TRADE")) {
            Page<ProfileDto.TradeResponse> results = profileRepository.getTradeByLike(user.getId(), pageable);
            return results;
        } else if (postType.equals("QNA")) {
            Page<ProfileDto.QnaResponse> results = profileRepository.getQnAByLike(user.getId(), pageable);
            return results;
        } else {
            Page<ProfileDto.RehomingResponse> results = profileRepository.getRehomingByLike(user.getId(), pageable);
            return results;
        }
    }

    @Override
    public ProfileDto.RecentDealResponse getMyRecentDeal() {
        return null;
    }

    @Override
    public void deleteUser(User user) {
        profileRepository.findById(user.getId()).orElseThrow(
                ()-> new CustomException(ErrorCode.ILLEGAL_USER_NOT_EXIST)
        );
        profileRepository.deleteById(user.getId());
    }

}
