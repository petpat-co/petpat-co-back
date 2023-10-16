package com.smile.petpat.post.rehoming.controller;

import com.smile.petpat.common.response.SuccessResponse;
import com.smile.petpat.post.rehoming.domain.RehomingCommand;
import com.smile.petpat.post.rehoming.dto.RehomingPagingDto;
import com.smile.petpat.post.rehoming.service.RehomingServiceImpl;
import com.smile.petpat.user.service.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Tag(name = "RehomingController", description = "분양 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rehoming")
public class RehomingController {

    private final RehomingServiceImpl rehomingService;

    /**
     * 분양 게시물 등록
     * @return 성공 시 200 Success 반환
     */
    @Operation(summary = "분양게시글 등록", description = "분양게시글 등록")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public SuccessResponse registerRehoming(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @ModelAttribute @Valid RehomingCommand rehomingDto) {
        RehomingCommand rehomingCommand = rehomingDto.toCommand();
        rehomingService.registerRehoming(userDetails.getUser(), rehomingCommand);
        return SuccessResponse.noDataSuccess("OK");
    }

    /**
     * 분양 게시물 목록 조회
     * @return 성공 시 200 Success 및 분양 게시물 목록 반환
     */
    @Operation(summary = "분양게시글 목록 조회", description = "분양게시글 목록 조회")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public SuccessResponse listRehoming(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @PageableDefault Pageable pageable) {
        RehomingPagingDto rehomingInfos;
        if (userDetails == null) {
            rehomingInfos = rehomingService.listRehoming(pageable);
        } else {
            rehomingInfos = rehomingService.listRehomingForMember(userDetails.getUser(), pageable);
        }
        return SuccessResponse.success(rehomingInfos, "OK");
    }

    /**
     * 분양 게시물 상세 조회
     * @return 성공 시 200 Success 및 해당 게시물 반환
     */
    @Operation(summary = "분양게시글 상세 조회", description = "분양게시글 상세 조회")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public SuccessResponse detail(@RequestParam Long postId,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return SuccessResponse.success(rehomingService.detailRehoming(postId), "OK");
        }
        return SuccessResponse.success(rehomingService.detailRehomingForMember(postId, userDetails.getUser()), "OK");
    }

    /**
     * 분양 게시물 수정
     * @return 성공 시 200 Success 및 수정된 게시물 반환
     */
    @Operation(summary = "분양게시물 수정", description = "분양게시물 수정")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public SuccessResponse updateRehoming(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @RequestParam Long postId,
                                          @ModelAttribute @Valid RehomingCommand rehomingDto) {
        RehomingCommand rehomingCommand = rehomingDto.toCommand();
        return SuccessResponse.success(rehomingService.updateRehoming(userDetails.getUser(), postId, rehomingCommand), "OK");
    }

    /**
     * 분양 게시물 삭제
     * @return 성공 시 200 Success 반환
     */
    @Operation(summary = "분양게시물 삭제", description = "분양 게시물 삭제")
    @RequestMapping(value = "",method = RequestMethod.DELETE)
    public SuccessResponse deleteRehoming(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @RequestParam Long postId) {
        rehomingService.deleteRehoming(userDetails.getUser(), postId);
        return SuccessResponse.noDataSuccess("OK");
    }

    /**
     * 분양 게시물 상태값 변경
     * @return 성공 시 200 Success 및 게시물 타입 및 번호 반환
     */
    @Operation(summary = "분양게시물 상태변경 [분양 중]", description= "분양게시물 상태변경 [분양 중]")
    @RequestMapping(value = "/statusFinding", method = RequestMethod.POST)
    public SuccessResponse updateStatusFinding(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @RequestParam Long postId) {
        rehomingService.updateStatusFinding(userDetails.getUser(), postId);
        return SuccessResponse.noDataSuccess("OK");
    }

    @Operation(summary = "분양게시물 상태변경 [예약 중]", description= "분양게시물 상태변경 [예약 중]")
    @RequestMapping(value = "/statusReserved", method = RequestMethod.POST)
    public SuccessResponse updateStatusReserved(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @RequestParam Long postId) {
        rehomingService.updateStatusReserved(userDetails.getUser(), postId);
        return SuccessResponse.noDataSuccess("OK");
    }

    @Operation(summary = "분양게시물 상태변경 [예약 완료]", description= "분양게시물 상태변경 [예약 완료]")
    @RequestMapping(value = "/statusMatched", method = RequestMethod.POST)
    public SuccessResponse updateStatusMatched(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @RequestParam Long postId) {
        rehomingService.updateStatusMatched(userDetails.getUser(), postId);
        return SuccessResponse.noDataSuccess("OK");
    }

    @Operation(summary = "분양게시물 카테고리별 목록 조회", description = "분양게시물 카테고리별 목록 조회")
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public ResponseEntity<?> getCategoryList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @RequestParam("categoryId") Long categoryId, @RequestParam("typeId") Long typeId,
                                             @PageableDefault Pageable pageable) {
        RehomingPagingDto rehomingPagingDto;
        if (userDetails != null) {
            rehomingPagingDto = rehomingService.getCategoryListForMember(userDetails.getUser(), categoryId, typeId, pageable);
        }
        else rehomingPagingDto = rehomingService.getCategoryList(categoryId, typeId, pageable);
        return ResponseEntity.ok(rehomingPagingDto);

    }
}
