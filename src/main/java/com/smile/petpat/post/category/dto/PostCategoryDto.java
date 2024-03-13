package com.smile.petpat.post.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
public class PostCategoryDto {

    @Getter
    @Setter
    @ToString
    public static class RequestRegisterCategoryGroup{
        private String firstCategoryName;
        private String postType;
    }

    @Getter
    @AllArgsConstructor
    public static class TradeCategoryResponse{
        private Long secondCategoryId;
        private String secondCategoryName;
        List<TradeCategoryDetailResponse> thirdCategoryList;
    }

    @Getter
    @AllArgsConstructor
    public static class TradeCategoryDetailResponse{
        private Long thirdCategoryId;
        private String thirdCategoryName;
        private Long thirdCategoryCnt;
    }

    @Getter
    @AllArgsConstructor
    public static class RehomingCategoryResponse{
        private Long secondCategoryId;
        private String secondCategoryName;
        private Long secondCategoryCnt;
    }

    @Getter
    @AllArgsConstructor
    public static class RehomingCategoryList {
        private Long firstCategoryId;
        private String firstCategoryName;
        private List<RehomingCategoryResponse> secondCategoryList;
    }

    @Getter
    @AllArgsConstructor
    public static class TradeCategoryList {
        private Long firstCategoryId;
        private String firstCategoryName;
        private List<TradeCategoryResponse> secondCategoryList;
    }

}
