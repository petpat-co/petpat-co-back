package com.smile.petpat.post.category.dto;

import com.smile.petpat.post.category.domain.PetCategory;
import com.smile.petpat.post.category.domain.TradeCategory;
import com.smile.petpat.post.category.domain.TradeCategoryDetail;
import com.smile.petpat.post.trade.domain.Trade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

public class PostCategoryInfo {

    public static class CategoryGroupRes{

    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PetCategoryRes {
        private Long secondCategoryId;
        private String secondCategoryName;

        public PetCategoryRes(PetCategory petCategory) {
            this.secondCategoryId = petCategory.getPetCategoryId();
            this.secondCategoryName = petCategory.getPetCategoryName();
        }
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TradeCategoryRes{
        private Long secondCategoryId;
        private String secondCategoryName;

        public TradeCategoryRes(TradeCategory tradeCategory){
            this.secondCategoryId = tradeCategory.getTradeCategoryId();
            this.secondCategoryName = tradeCategory.getTradeCategoryName();
        }

    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TradeCategoryDetailRes{
        private Long thirdCategoryId;
        private String thirdCategoryName;

        public TradeCategoryDetailRes(TradeCategoryDetail tradeCategoryDetail){
            this.thirdCategoryId = tradeCategoryDetail.getTradeCategoryDetailId();
            this.thirdCategoryName = tradeCategoryDetail.getTradeCategoryDetailName();
        }
    }




}
