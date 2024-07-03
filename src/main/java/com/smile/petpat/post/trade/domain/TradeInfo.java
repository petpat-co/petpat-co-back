package com.smile.petpat.post.trade.domain;

import com.smile.petpat.image.dto.ImageResDto;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.common.Address.util.AddressUtils;
import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.common.CalculateTime;
import com.smile.petpat.post.common.status.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TradeInfo {


    @Getter
    @ToString
    @AllArgsConstructor
    public static class TradeDetail{
        private Long postId;
        private Long userId;
        private String nickname;
        private String title;
        private String content;
        private Long price;
        private String region;
        private List<ImageResDto> imageList;
        private PostType postType;
        private boolean isLiked;
        private boolean isBookmarked;
        private int viewCnt;
        private Long likeCnt;
        private Long bookmarkCnt;
        private String tradeCategoryDetailName;
        private PostStatus status;
        private String createdAt;

        public TradeDetail(){
            
        }

        //Constructor of tradeDetailForUser,tradeDetail
        public TradeDetail(Long postId, Long userId, String nickname, String title, String content, Long price, Address address,
                           PostType postType, Long isLiked, Long isBookmarked, int viewCnt, Long likeCnt,
                           Long bookmarkCnt, String tradeCategoryDetailName, PostStatus status, LocalDateTime createdAt
        ) {
            this.postId = postId;
            this.userId = userId;
            this.nickname = nickname;
            this.title = title;
            this.content = content;
            this.price = price;
            this.region = AddressUtils.makeRegionFromAddress(address);
            this.postType = postType;
            this.isLiked = isLiked==0? false:true ;
            this.isBookmarked = isBookmarked==0? false:true;
            this.viewCnt = viewCnt;
            this.likeCnt = likeCnt;
            this.bookmarkCnt = bookmarkCnt;
            this.tradeCategoryDetailName = tradeCategoryDetailName;
            this.status =status;
            this.createdAt = CalculateTime.dateformatForPost(createdAt);
        }

        public TradeDetail(TradeDetail tradeDetail, List<ImageResDto> imageList) {
            this.postId = tradeDetail.postId;
            this.userId = tradeDetail.userId;
            this.nickname = tradeDetail.nickname;
            this.title = tradeDetail.title;
            this.content = tradeDetail.content;
            this.price = tradeDetail.price;
            this.region = tradeDetail.getRegion();
            this.imageList = imageList;
            this.postType = tradeDetail.postType;
            this.isLiked = tradeDetail.isLiked;
            this.isBookmarked = tradeDetail.isBookmarked;
            this.viewCnt = tradeDetail.viewCnt;
            this.likeCnt = tradeDetail.likeCnt;
            this.bookmarkCnt = tradeDetail.bookmarkCnt;
            this.tradeCategoryDetailName = tradeDetail.tradeCategoryDetailName;
            this.status=tradeDetail.status;
            this.createdAt = tradeDetail.createdAt;
        }

        public void setImageList(List<ImageResDto> imageList){
            this.imageList = imageList;
        }

    }
    @Getter
    @ToString
    @AllArgsConstructor
    public static class TradeList{


        private Long postId;
        private String imagePath;
        private String title;
        private Long price;
        private String region;
        private boolean isLiked;
        private boolean isBookmarked;
        private Long likeCnt;
        private Long bookmarkCnt;
        private int viewCnt;
        private PostStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;


        public TradeList(){
           
        }


        //Constructor of tradeList, tradeList_Paging, fetchTrendingTrade
        public TradeList(Long postId, String imagePath, String title, Long price, Address address, Long isLiked,
                         Long isBookmarked,int viewCnt,Long likeCnt,Long bookmarkCnt,LocalDateTime createdAt,LocalDateTime updatedAt,PostStatus postStatus) {
            this.postId = postId;
            this.imagePath = imagePath;
            this.title = title;
            this.price = price;
            this.region = AddressUtils.makeRegionFromAddress(address);
            this.isLiked = booleanChk(isLiked);
            this.isBookmarked =booleanChk(isBookmarked);
            this.viewCnt = viewCnt;
            this.likeCnt = likeCnt;
            this.bookmarkCnt = bookmarkCnt;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.status =postStatus;
        }

    }
    @Getter
    @NoArgsConstructor
    public static class TradePagingListInfo{
        private int contentCnt;
        private List<?> content = new ArrayList<>();
        private int pageSize;
        private int page;
        private int totalPage;

        public TradePagingListInfo(Page<?> pageList) {
            this.contentCnt = (int) pageList.getTotalElements();
            this.content = pageList.getContent();
            this.pageSize = pageList.getSize();
            this.page = pageList.getPageable().getPageNumber();
            this.totalPage = pageList.getTotalPages();

        }

    }



    public static  Boolean booleanChk(Long chkValue) {
        return chkValue == 0?false : true;
    }
}
