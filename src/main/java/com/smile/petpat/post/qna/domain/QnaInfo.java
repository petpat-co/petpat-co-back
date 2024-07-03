package com.smile.petpat.post.qna.domain;

import com.smile.petpat.image.dto.ImageResDto;
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

public class QnaInfo {

    @Getter
    @ToString
    public static class QnaList{
        private Long qnaId;
        private String nickname;
        private String title;
        private String imagePath;
        private PostType postType;
        private int viewCnt;

        private String createAt;

        public QnaList(){
           
        }

        public QnaList(Long qnaId, String nickname, String title, String imagePath, PostType postType, int viewCnt, String createAt) {
            this.qnaId = qnaId;
            this.nickname = nickname;
            this.title = title;
            this.imagePath = imagePath;
            this.postType = postType;
            this.viewCnt = viewCnt;
            this.createAt = createAt;
        }

        public QnaList(Long qnaId, String nickname, String title,
                       String imagePath, PostType postType,
                       int viewCnt, LocalDateTime createAt) {
            this.qnaId = qnaId;
            this.nickname = nickname;
            this.title = title;
            this.imagePath = imagePath;
            this.postType = postType;
            this.viewCnt = viewCnt;
            this.createAt = CalculateTime.dateformatForPost(createAt);
        }

    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class QnaDetail{
        private Long qnaId;
        private Long userId;
        private String nickname;
        private String title;
        private String content;
        private List<ImageResDto> imageList;
        private PostType postType;
        private int viewCnt;
        private String createAt;

        public QnaDetail(){

        }

        public QnaDetail(Long qnaId, Long userId, String nickname, String title, String content, List<ImageResDto> imageList, PostType postType,
                         int viewCnt, LocalDateTime createAt
        ) {
            this.qnaId = qnaId;
            this.userId = userId;
            this.nickname = nickname;
            this.title = title;
            this.content = content;
            this.imageList = imageList;
            this.postType = postType;
            this.viewCnt = viewCnt;
            this.createAt = CalculateTime.dateformatForPost(createAt);
        }

        public QnaDetail(Long qnaId, Long userId, String nickname, String title, String content, PostType postType, int viewCnt,
                           LocalDateTime createAt) {
            this.qnaId = qnaId;
            this.userId = userId;
            this.nickname = nickname;
            this.title = title;
            this.content = content;
            this.postType = postType;
            this.viewCnt = viewCnt;
            this.createAt = CalculateTime.dateformatForPost(createAt);
        }

        public QnaDetail(QnaDetail qnaDetail, List<ImageResDto> imageList) {
            this.qnaId = qnaDetail.qnaId;
            this.userId = qnaDetail.userId;
            this.nickname = qnaDetail.nickname;
            this.title = qnaDetail.title;
            this.content = qnaDetail.content;
            this.imageList = imageList;
            this.postType = qnaDetail.postType;
            this.viewCnt = qnaDetail.viewCnt;
            this.createAt = qnaDetail.createAt;
        }




    }

    public static  Boolean booleanChk(Long chkValue) {
        return chkValue == 0?false : true;
    }
@Getter
@NoArgsConstructor
public static class QnaPagingListInfo{
    private int contentCnt;
    private List<?> content = new ArrayList<>();
    private int pageSize;
    private int page;
    private int totalPage;

    public QnaPagingListInfo(Page<?> pageList) {
        this.contentCnt = (int) pageList.getTotalElements();
        this.content = pageList.getContent();
        this.pageSize = pageList.getSize();
        this.page = pageList.getPageable().getPageNumber();
        this.totalPage = pageList.getTotalPages();

    }

}


}
