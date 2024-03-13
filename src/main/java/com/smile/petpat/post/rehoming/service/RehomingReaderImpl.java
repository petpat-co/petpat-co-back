package com.smile.petpat.post.rehoming.service;

import com.smile.petpat.post.category.domain.CategoryGroup;
import com.smile.petpat.post.category.domain.PetCategory;
import com.smile.petpat.post.category.repository.PetCategoryRepository;
import com.smile.petpat.post.category.repository.PostCategoryGroupRepository;
import com.smile.petpat.post.rehoming.domain.Rehoming;
import com.smile.petpat.post.rehoming.domain.RehomingReader;
import com.smile.petpat.post.rehoming.dto.RehomingResDto;
import com.smile.petpat.post.rehoming.repository.RehomingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RehomingReaderImpl implements RehomingReader {
    private final RehomingRepository rehomingRepository;

    private final PostCategoryGroupRepository categoryGroupRepository;

    private final PetCategoryRepository petCategoryRepository;

    @Override
    public Rehoming readRehomingById(Long rehomingId) {
        return rehomingRepository.findById(rehomingId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );
    }

    @Override
    public void userChk(String userEmail, Rehoming rehoming) {
        if (!rehoming.getUser().getUserEmail().equals(userEmail)) {
            throw new IllegalArgumentException("본인 글만 수정/삭제가 가능합니다.");
        }
    }

    @Override
    public PetCategory readPetTypeById(Long petCategoryId) {
        return petCategoryRepository.findById(petCategoryId).orElseThrow(
                () -> new IllegalArgumentException("해당 반려동물 종류를 찾지 못했습니다.")
        );
    }

    @Override
    public RehomingResDto readRehomingDetailForMember(String userEmail, Long rehomingId) {
        return null;
    }

    @Override
    public CategoryGroup readCategoryById(Long categoryId) {
        return categoryGroupRepository.findByCategoryGroupId(categoryId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 카테고리입니다.")
        );
    }
}
