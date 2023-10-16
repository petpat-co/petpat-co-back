package com.smile.petpat.post.category.controller;

import com.smile.petpat.common.response.SuccessResponse;
import com.smile.petpat.post.category.service.PostCategoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "PostCategoryController", description ="카테고리 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostCategoryController {

    private final PostCategoryServiceImpl postCategoryService;

    /**
     * 카테고리 그룹 리스트 조회
     * @return 성공 시 200 Success 와 함께 카테고리 그룹 목록 반환
     */
    @Operation(summary = "카테고리 그룹 리스트 조회", description = "카테고리 그룹 리스트 조회")
    @RequestMapping(value = "/categoryGroup/{postType}",method = RequestMethod.GET)
    public SuccessResponse getCategoryGroup(@PathVariable(value = "postType") String postType){
       return SuccessResponse.success(postCategoryService.getCategoryGroup(postType),"ok");
    }

    /**
     * 반려동물 카테고리 그룹 리스트 조회
     * @return 성공 시 200 Success 와 함께 반려동물 카테고리 그룹 목록 반환
     */
    @Operation(summary = "반려동물 카테고리 그룹 리스트 조회", description = "반려동물 카테고리 그룹 리스트 조회")
    @RequestMapping(value = "/petCategory/{categoryGroup}",method = RequestMethod.GET)
    public SuccessResponse getPetCategory(@PathVariable(value ="categoryGroup") Long categoryGroup){
       return SuccessResponse.success(postCategoryService.getPetCategory(categoryGroup));
    }

    /**
     * 중고거래 카테고리 그룹 리스트 조회
     * @return 성공 시 200 Success 와 함께 중고거래카테고리 그룹 목록 반환
     */
    @Operation(summary = "중고거래 카테고리 그룹 리스트 조회", description = "중고거래 카테고리 그룹 리스트 조회")
    @RequestMapping(value = "/tradeCategory/{categoryGroup}",method = RequestMethod.GET)
    public SuccessResponse getTradeCategory(@PathVariable(value = "categoryGroup") Long categoryGroup){
       return SuccessResponse.success(postCategoryService.getTradeCategory(categoryGroup));
    }

    /**
     * 중고거래 상세 카테고리 그룹 리스트 조회
     * @return 성공 시 200 Success 와 함께 중고거래 상세카테고리 그룹 목록 반환
     */
    @Operation(summary = "중고거래 상세카테고리 그룹 리스트 조회", description = "중고거래 상세카테고리 그룹 리스트 조회")
    @RequestMapping(value = "/tradeCategoryDetail/{tradeCategory}",method = RequestMethod.GET)
    public void getTradeCategoryDetail(@PathVariable(value = "tradeCategory") Long tradeCategory){
        postCategoryService.getTradeCategoryDetail(tradeCategory);
    }

    /**
     * 거래게시글 카테고리 조회
     * @return 성공 시 200 Success와 함께 CategoryGroup에 해당하는 TradeCategory와 TradeCategoryDetail과 그 해당하는 품목 수 조회
     */
    @Operation(summary = "중고거래 카테고리 그룹 리스트 및 갯수 조회", description = "중고거래 카테고리 그룹 리스트 및 갯수 조회")
    @RequestMapping(value = "/trade/category/{petType}",method = RequestMethod.GET)
    public SuccessResponse getTradeCategoryAndCnt(@PathVariable Long petType){
        return SuccessResponse.success(postCategoryService.getTradeCategoryAndCnt(petType));
    }

    /**
     * 거래게시글 카테고리 조회
     * @return 성공 시 200 Success와 함께 CategoryGroup에 해당하는 PetCategory와 그 해당하는 개체 수 조회
     */
    @Operation(summary = "분양 카테고리 그룹 리스트 및 갯수 조회", description = "분양 카테고리 그룹 리스트 및 갯수 조회")
    @RequestMapping(value = "/rehoming/category/{petType}",method = RequestMethod.GET)
    public SuccessResponse getRehomingCategoryAndCnt(@PathVariable Long petType){
        return SuccessResponse.success(postCategoryService.getRehomingCategoryAndCnt(petType));
    }


}
