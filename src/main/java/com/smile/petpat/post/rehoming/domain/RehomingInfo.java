package com.smile.petpat.post.rehoming.domain;

import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.common.CalculateTime;
import com.smile.petpat.post.common.status.PostStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RehomingInfo {
    private Long postId;
    private PostType postType;
    private String imagePath;
    private String title;
    private String region;
    private PostStatus status;
    private boolean isLiked;
    private boolean isBookmarked;
    private int viewCnt;
    private Long likeCnt;
    private Long bookmarkCnt;
    private String createdAt;
    private String updatedAt;

    // 회원
    public RehomingInfo(Long postId, PostType postType, String imagePath,
                        String title, String cityName, String cityCountryName,
                        String townShipName, PostStatus status,
                        Long isLiked, Long isBookmarked, int viewCnt, Long likeCnt, Long bookmarkCnt,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.postType = postType;
        this.imagePath = imagePath;
        this.title = title;
        this.region = cityName + " " + cityCountryName + " " + townShipName;
        this.status = status;
        this.isLiked = booleanChk(isLiked);
        this.isBookmarked = booleanChk(isBookmarked);
        this.viewCnt = viewCnt;
        this.likeCnt = likeCnt;
        this.bookmarkCnt = bookmarkCnt;
        this.createdAt = CalculateTime.dateformatForPost(createdAt);
        this.updatedAt = CalculateTime.dateformatForPost(updatedAt);
    }

    private Boolean booleanChk(Long chkValue) {
        return chkValue != 0;
    }

    // 비회원
    public RehomingInfo(Long postId, PostType postType,
                        String imagePath, String title,
                        String cityName, String cityCountryName,
                        String townShipName, PostStatus status,
                        int viewCnt, Long likeCnt, Long bookmarkCnt,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.postType = postType;
        this.imagePath = imagePath;
        this.title = title;
        this.region = cityName + " " + cityCountryName+ " " + townShipName;
        this.status = status;
        this.isLiked = false;
        this.isBookmarked = false;
        this.viewCnt = viewCnt;
        this.likeCnt = likeCnt;
        this.bookmarkCnt = bookmarkCnt;
        this.createdAt = CalculateTime.dateformatForPost(createdAt);
        this.updatedAt = CalculateTime.dateformatForPost(updatedAt);
    }
}
