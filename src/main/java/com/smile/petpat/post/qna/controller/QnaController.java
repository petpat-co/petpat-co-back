package com.smile.petpat.post.qna.controller;

import com.smile.petpat.common.response.SuccessResponse;
import com.smile.petpat.post.qna.domain.QnaCommand;
import com.smile.petpat.post.qna.domain.QnaDto;
import com.smile.petpat.post.qna.service.QnaService;
import com.smile.petpat.user.service.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "QnaController", description = "Qna API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qna")
public class QnaController {

    private final QnaService qnaService;

    /**
     * Qna 게시물 등록
     * @return 성공 시 200 Success 반환
     */
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Qna 게시물 등록", description = "Qna 게시물 등록")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public SuccessResponse registerQna(@ModelAttribute @Valid QnaDto.CommonQna qnaDto,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails){
        QnaCommand qnaCommand = qnaDto.toCommand();
        qnaService.registerQna(qnaCommand, userDetails.getUser());
        return SuccessResponse.success("ok");
    }

    /**
     * Qna 게시물 목록 조회
     * @return 성공 시 200 Success 반환
     */

    @PreAuthorize("hasRole('GUEST')")
    @Operation(summary = "Qna 게시물 리스트 조회", description = "Qna 게시물 리스트 조회")
    @RequestMapping(value = "/public",method = RequestMethod.GET)
    public SuccessResponse listQna(@PageableDefault() Pageable pageable){
        return SuccessResponse.success(qnaService.listQna(pageable), "ok");

    }

    /**
     * Qna 게시물 상세 조회
     * @return 성공 시 200 Success 및 해당 게시물 반환
     */

    @PreAuthorize("hasRole('GUEST')")
    @Operation(summary = "Qna 상세 조회", description = "Qna 상세 조회")
    @RequestMapping(value = "/public/detail/{postId}", method = RequestMethod.GET)
    public SuccessResponse detailQnaPublic(@PathVariable Long postId) {
        return SuccessResponse.success(qnaService.detailQna(postId), "OK");
    }



    /**
     * Qna 게시물 수정
     * @return 성공 시 200 Success 반환
     */
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Qna 게시물 수정", description = "Qna 게시물 수정")

    @RequestMapping(value = "/{postId}",method = RequestMethod.PUT)
    public SuccessResponse updateQna(@RequestBody QnaDto.QnAUpdateDto qnaDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @PathVariable Long postId){
        QnaCommand qnaCommand = qnaDto.toCommand();
        return SuccessResponse.success(qnaService.updateQna(userDetails.getUser(), postId, qnaCommand));

    }

    /**
     * Qna 게시물 삭제
     * @return 성공 시 200 Success 반환
     */
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Qna 게시물 삭제", description = "Qna 게시물 삭제")
    @RequestMapping(value = "/{postId}",method = RequestMethod.DELETE)
    public SuccessResponse deleteQna(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @RequestParam Long postId){
        qnaService.deleteQna(postId, userDetails.getUser());
        return SuccessResponse.noDataSuccess("OK");

    }
}
