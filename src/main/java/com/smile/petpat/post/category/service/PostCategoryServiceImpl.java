package com.smile.petpat.post.category.service;

import com.smile.petpat.post.category.domain.*;
import com.smile.petpat.post.category.dto.PostCategoryDto;
import com.smile.petpat.post.category.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.smile.petpat.post.category.dto.PostCategoryInfo.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostCategoryServiceImpl implements PostCategoryService{

    private final PostCategoryGroupRepository postCategoryGroupRepository;
    private final PetCategoryRepository petCategoryRepository;
    private final TradeCategoryRepository tradeCategoryRepository;
    private final TradeCategoryDetailRepository tradeCategoryDetailRepository;
    private final CategoryGroupRepository categoryRepository;

    @Override
    public List<CategoryGroup> getCategoryGroup(String postTypeDescription) {
        PostType postType = PostType.find(postTypeDescription);
        return postCategoryGroupRepository.findAllByPostType(postType);

    }

    @Override
    public List<PetCategoryRes> getPetCategory(Long categoryGroupId) {
        CategoryGroup categoryGroup = postCategoryGroupRepository.findByCategoryGroupId(categoryGroupId)
                .orElseThrow(
                        () -> new IllegalArgumentException("존재하지않는 카테고리그룹입니다.")
                );
        List<PetCategory> petCategoryList = petCategoryRepository.findAllByCategoryGroup(categoryGroup);
        List<PetCategoryRes> petCategoryInfo = petCategoryList.stream()
                .map(PetCategoryRes::new).collect(Collectors.toList());
        return petCategoryInfo;
    }

    @Override
    public List<TradeCategoryRes> getTradeCategory(Long categoryGroupId) {
        CategoryGroup categoryGroup = postCategoryGroupRepository.findByCategoryGroupId(categoryGroupId)
                .orElseThrow(
                        () -> new IllegalArgumentException("존재하지않는 카테고리그룹입니다.")
                );
        List<TradeCategory> tradeCategoryList = tradeCategoryRepository.findAllByCategoryGroup(categoryGroup);
        List<TradeCategoryRes> tradeCategoryInfo = tradeCategoryList.stream()
                .map(TradeCategoryRes::new).collect(Collectors.toList());
        return  tradeCategoryInfo;
    }

    @Override
    public List<TradeCategoryDetailRes> getTradeCategoryDetail(Long tradeCategoryId) {
        TradeCategory tradeCategory = tradeCategoryRepository.findByTradeCategoryId(tradeCategoryId)
                .orElseThrow(
                        () -> new IllegalArgumentException("존재하지않는 중고거래 카테고리그룹입니다.")
        );
        List<TradeCategoryDetail> tradeCategoryDetailList = tradeCategoryDetailRepository.findAllByTradeCategory(tradeCategory);
        List<TradeCategoryDetailRes> tradeCategoryDetailInfo = tradeCategoryDetailList.stream()
                .map(TradeCategoryDetailRes::new).collect(Collectors.toList());
        return tradeCategoryDetailInfo;
    }

    @Override
    public List<PostCategoryDto.TradeCategoryResponse> getTradeCategoryAndCnt(Long tradeCategoryId) {
        List<PostCategoryDto.TradeCategoryResponse> responses = new ArrayList<>();
        CategoryGroup categoryGroup = categoryRepository.findById(tradeCategoryId)
                .orElseThrow(
                        ()->new IllegalArgumentException("존재하지않는 중고거래 카테고리그룹입니다.")
                );
        List<TradeCategory> tradeCategories = tradeCategoryRepository.findAllByCategoryGroup(categoryGroup);

        for(TradeCategory tradeCategory1 : tradeCategories){
            PostCategoryDto.TradeCategoryResponse tradeCategoryResponse = new PostCategoryDto.TradeCategoryResponse(
                    tradeCategory1.getTradeCategoryId(),
                    tradeCategory1.getTradeCategoryName(),
                    categoryRepository.getTradeCategoryAndCnt(tradeCategory1.getTradeCategoryId())
            );
            responses.add(tradeCategoryResponse);
        }
        return responses;
    }

    @Override
    public List<PostCategoryDto.RehomingCategoryResponse> getRehomingCategoryAndCnt(Long categoryGroupId) {
        return categoryRepository.getRehomingCategoryAndCnt(categoryGroupId);
    }

    public List<PostCategoryDto.RehomingCategoryList> getRehomingCategoryList() {
        return postCategoryGroupRepository.findAllByPostType(PostType.REHOMING).stream()
                .map(categoryGroup -> {
                    List<PostCategoryDto.RehomingCategoryResponse> petCategoryList = categoryRepository.getRehomingCategoryAndCnt(categoryGroup.getCategoryGroupId());
                    return new PostCategoryDto.RehomingCategoryList(
                            categoryGroup.getCategoryGroupId(),
                            categoryGroup.getCategoryGroupName(),
                            petCategoryList
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PostCategoryDto.TradeCategoryList> getTradeCategoryList() {
        return postCategoryGroupRepository.findAllByPostType(PostType.TRADE).stream()
                .map(categoryGroup -> new PostCategoryDto.TradeCategoryList(
                        categoryGroup.getCategoryGroupId(),
                        categoryGroup.getCategoryGroupName(),
                        getTradeCategoryAndCnt(categoryGroup.getCategoryGroupId())
                ))
                .collect(Collectors.toList());
    }
}
