package com.smile.petpat.user.domain;

import com.smile.petpat.user.dto.ProfileDto;
import com.smile.petpat.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProfileService {
    //내 프로필 조회
    ProfileDto.ProfileResponse getProfile(User user);
    //프로필 수정
    void modifyProfile(UserDto.ModifyUserRequest request,User user);
    //비밀번호 확인
    Boolean passwordCheck(String password, User user);
    //비밀번호 수정
    User modifyPassword(UserDto.ModifyPasswordRequest request,User user);
    //내가 작성한 분양 게시글
    Page<ProfileDto.RehomingResponse> getMyRehoming(User user, Pageable pageable);
    //내가 남긴 판매 게시글
    Page<ProfileDto.TradeResponse> getMyTrade(User user, Pageable pageable);
    //내가 남긴 질문 게시글
    Page<ProfileDto.QnaResponse> getMyQna(User user, Pageable pageable);
    //내가 남긴 댓글
    Page<ProfileDto.CommentResponse> getMyComment(User user, Pageable pageable);
    //내가 북마크 한 글
    Object getPostsByBookmark(User user, Pageable pageable, String postType);
    //내가 좋아요 한 글
    Object getPostsByLike(User user, Pageable pageable, String postType);

    ProfileDto.RecentDealResponse getMyRecentDeal();

    void deleteUser(User user);
}
