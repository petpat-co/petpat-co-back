package com.smile.petpat.post.rehoming.domain;

import com.smile.petpat.post.category.domain.CategoryGroup;
import com.smile.petpat.post.category.domain.PetCategory;

import java.util.List;

public interface RehomingReader {
    Rehoming readRehomingById(Long rehomingId);

    void userChk(String userEmail, Rehoming rehoming);

    CategoryGroup readCategoryById(Long categoryId);

    PetCategory readPetTypeById(Long petCategoryId);
    List<RehomingInfo> fetchTrendingRehoming(Long userId);
}
