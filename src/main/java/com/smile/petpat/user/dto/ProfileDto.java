package com.smile.petpat.user.dto;

import com.smile.petpat.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

public class ProfileDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RehomingResponse{
        private Long rehomingId;
        private String rehomingImgUrl;
        private String title;
        private LocalDateTime createdAt;
        private int viewCnt;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TradeResponse{
        private Long tradeId;
        private String tradeImgUrl;
        private String title;
        private LocalDateTime createdAt;
        private int viewCnt;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class QnaResponse{
        private Long QnaId;
        private String title;
        private LocalDateTime createdAt;
        private Long viewCnt;
        private int commentCnt;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    public static class CommentResponse{
        private Long QnAId;
        private Long commentId;
        private String title;
        private String commentContent;
        private LocalDateTime createdAt;
    }

    @Getter
    @Setter
    public static class ProfileResponse{
        private Long userId;
        private String userEmail;
        private String nickname;
        private String profileImgUrl;

        public ProfileResponse(User user){
            this.userId = user.getId();
            this.userEmail = user.getUserEmail();
            this.nickname = user.getNickname();
            this.profileImgUrl = user.getProfileImgPath();
        }
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class RecentDealResponse{
        private Long id;
        private String title;
        private String sellerName;
        private LocalDateTime dealDate;
        private String dealImage;
    }
}
