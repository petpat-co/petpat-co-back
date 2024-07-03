package com.smile.petpat.post.rehoming.service;

import com.smile.petpat.post.rehoming.domain.RehomingCommand;
import com.smile.petpat.post.rehoming.domain.RehomingInfo;
import com.smile.petpat.post.rehoming.dto.RehomingPagingDto;
import com.smile.petpat.post.rehoming.dto.RehomingResDto;
import com.smile.petpat.post.rehoming.dto.RehomingUpdateReqDto;
import com.smile.petpat.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RehomingService {
    void registerRehoming(String userEmail, RehomingCommand rehomingCommand);

    RehomingPagingDto listRehomingForMember(String userEmail, Pageable pageable);

    RehomingPagingDto listRehoming(Pageable pageable);

    RehomingResDto detailRehomingForMember(Long postId, String userEmail);

    RehomingResDto detailRehoming(Long postId);

//    RehomingResDto updateRehoming(String userEmail, Long postId, RehomingCommand rehomingCommand);

    // 4. 분양 글 수정
    RehomingResDto updateRehoming(String userEmail, Long postId, RehomingUpdateReqDto rehomingUpdateReqDto);

    void deleteRehoming(String userEmail, Long postId);

    void updateStatusFinding(String userEmail, Long postId);

    void updateStatusReserved(String userEmail, Long postId);

    void updateStatusMatched(String userEmail, Long postId);

    RehomingPagingDto getCategoryListForMember(String userEmail, Long categoryId, Long typeId, Pageable pageable);
    RehomingPagingDto getCategoryList(Long categoryId, Long typeId, Pageable pageable);

    List<RehomingInfo> fetchTrendingRehoming(User user);
}
