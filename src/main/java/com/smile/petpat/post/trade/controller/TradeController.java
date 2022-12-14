package com.smile.petpat.post.trade.controller;

import com.smile.petpat.common.response.SuccessResponse;
import com.smile.petpat.post.trade.domain.TradeCommand;
import com.smile.petpat.post.trade.domain.TradeDto;
import com.smile.petpat.post.trade.service.TradeServiceImpl;
import com.smile.petpat.user.service.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"post_trade_api"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trade")
public class TradeController {

    private final TradeServiceImpl tradeService;

    /**
     * 중고거래 게시물 등록
     * @return 성공 시 200 Success 반환
     */
    @ApiOperation(value = "중고거래 게시물 등록", notes = "중고거래 게시물 등록")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public SuccessResponse registerTrade(@ModelAttribute @Valid TradeDto.CommonTrade tradeDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        TradeCommand tradeCommand = tradeDto.toCommand();
        tradeService.registerTrade(tradeCommand,userDetails.getUser());
        return SuccessResponse.success("ok");
    }

    /**
     * 중고거래 게시물 목록 조회
     * @return 성공 시 200 Success 와 함께 분양게시물 목록 반환
     */
    @ApiOperation(value = "중고거래 게시물 목록 조회", notes = "중고거래 게시물 목록 조회")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public SuccessResponse listTrade(){
       return SuccessResponse.success(tradeService.listTrade(),"ok");
    }

    /**
     * 중고거래 게시물 상세 조회
     * @return 성공 시 200 Success 와 함께 분양게시물 상세 반환
     */
    @ApiOperation(value = "중고거래 게시물 상세조회", notes = "중고거래 게시물 상세조회")
    @RequestMapping(value = "/{tradeId}",method = RequestMethod.GET)
    public SuccessResponse detailTrade(@PathVariable Long tradeId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails == null) {
            return SuccessResponse.success(tradeService.tradeDetail(tradeId),"ok");
        } else {
            return SuccessResponse.success(tradeService.tradeDetailforUser(tradeId, userDetails.getUser()),"ok");
        }
    }

    /**
     * 중고거래 게시물 수정
     * @return 성공 시 200 Success 와 함께 수정한 분양게시물 반환
     */
    @ApiOperation(value = "중고거래 게시물 수정", notes = "중고거래 게시물 수정")
    @RequestMapping(value = "/{postId}",method = RequestMethod.PUT)
    public SuccessResponse updateTrade(@RequestBody TradeDto.CommonTrade tradeDto,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @PathVariable Long postId
    ){
        TradeCommand tradeCommand = tradeDto.toCommand();
        return SuccessResponse.success(tradeService.updateTrade(tradeCommand,userDetails.getUser(),postId));
    }

    /**
     * 중고거래 게시물  삭제
     * @return 성공 시 200 Success 반환
     */
    @ApiOperation(value = "중고거래 게시물 삭제", notes = "중고거래 게시물 삭제")
    @RequestMapping(value = "/{tradeId}",method = RequestMethod.DELETE)
    public SuccessResponse deleteTrade(@PathVariable Long tradeId,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        tradeService.deleteTrade(tradeId,userDetails.getUser());
        return SuccessResponse.success("ok");
    }

}
