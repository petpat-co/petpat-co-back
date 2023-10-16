package com.smile.petpat.post.rehoming.repository.querydsl;

import com.smile.petpat.post.rehoming.domain.RehomingInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RehomingRepositoryQuerydsl {
    Page<RehomingInfo> rehomingListForMember(Long userId, Pageable pageable);

    Page<RehomingInfo> rehomingList(Pageable pageable);

    Page<RehomingInfo> rehomingCategoryListForMember(Long userId, Long categoryId, Long typeId, Pageable pageable);
    
    Page<RehomingInfo> rehomingCategoryList(Long categoryId, Long typeId, Pageable pageable);
 }
