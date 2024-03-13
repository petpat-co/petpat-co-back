package com.smile.petpat.post.trade.domain;

import com.smile.petpat.post.category.domain.PostType;
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
        private List<String> imageList;
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

        public TradeDetail(Long postId, Long userId, String nickname, String title, String content, Long price,
                           String cityName, String cityCountryName, String townShipName, String detailAdName,
                           String fullAdName, List<String> imageList, PostType postType, boolean isLiked,
                           boolean isBookmarked, int viewCnt, Long likeCnt, Long bookmarkCnt, String tradeCategoryDetailName, PostStatus status,LocalDateTime createdAt
        ) {
            this.postId = postId;
            this.userId = userId;
            this.nickname = nickname;
            this.title = title;
            this.content = content;
            this.price = price;
            this.region = cityName + " " + cityCountryName;
            this.imageList = imageList;
            this.postType = postType;
            this.isLiked = isLiked;
            this.isBookmarked = isBookmarked;
            this.viewCnt = viewCnt;
            this.likeCnt = likeCnt;
            this.bookmarkCnt = bookmarkCnt;
            this.tradeCategoryDetailName = tradeCategoryDetailName;
            this.status =status;
            this.createdAt = CalculateTime.dateformatForPost(createdAt);
        }

        public TradeDetail(Long postId, Long userId, String nickname, String title, String content, Long price,
                           String cityName, String cityCountryName, String townShipName, String detailAdName,
                           String fullAdName, PostType postType, Long isLiked, Long isBookmarked, int viewCnt,
                           Long likeCnt, Long bookmarkCnt, String tradeCategoryDetailName,PostStatus status,LocalDateTime createdAt) {
            this.postId = postId;
            this.userId = userId;
            this.nickname = nickname;
            this.title = title;
            this.content = content;
            this.price = price;
            this.region = cityName + " " + cityCountryName + " " + townShipName;
            this.postType = postType;
            this.isLiked = booleanChk(isLiked);
            this.isBookmarked = booleanChk(isBookmarked);
            this.viewCnt = viewCnt;
            this.likeCnt = likeCnt;
            this.bookmarkCnt = bookmarkCnt;
            this.tradeCategoryDetailName = tradeCategoryDetailName;
            this.status = status;
            this.createdAt = CalculateTime.dateformatForPost(createdAt);
        }

        public TradeDetail(TradeDetail tradeDetail, List<String> imageList) {
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
        private int viewCnt;
        private PostStatus status;


        public TradeList(){
           
        }


        public TradeList(Long postId, String imagePath, String title, Long price, String cityName, String cityCountryName,
                         String townshipName, Long isLiked, int viewCnt, PostStatus status) {
            this.postId = postId;
            this.imagePath = imagePath;
            this.title = title;
            this.price = price;
            this.region = cityName + " " + cityCountryName + " " + townshipName;
            this.isLiked = booleanChk(isLiked);
            this.viewCnt = viewCnt;
            this.status = status;
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
