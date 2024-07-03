package com.smile.petpat.post.rehoming.repository.querydsl;

import com.smile.petpat.post.rehoming.domain.RehomingInfo;
import com.smile.petpat.post.rehoming.dto.RehomingResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface RehomingRepositoryQuerydsl {
    Page<RehomingInfo> rehomingListForMember(String userEmail, Pageable pageable);

    Page<RehomingInfo> rehomingList(Pageable pageable);

    Page<RehomingInfo> rehomingCategoryListForMember(String userEmail, Long categoryId, Long typeId, Pageable pageable);
    
    Page<RehomingInfo> rehomingCategoryList(Long categoryId, Long typeId, Pageable pageable);

//    RehomingResDto readRehomingDetailForMember(String userEmail, Long rehomingId);

    List<RehomingInfo> fetchTrendingRehoming(Long userId, LocalDateTime startOfWeek, LocalDateTime endOfWeek);
}
