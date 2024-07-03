package com.smile.petpat.post.trade.service;

import com.smile.petpat.image.dto.ImageResDto;
import com.smile.petpat.image.service.ImageService;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.category.domain.TradeCategoryDetail;
import com.smile.petpat.post.common.Address.Dto.AddressReqDto;
import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.common.Address.service.AddressService;
import com.smile.petpat.post.common.CommonUtils;
import com.smile.petpat.post.trade.domain.*;
import com.smile.petpat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.smile.petpat.post.trade.domain.TradeInfo.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService{

    private final TradeStore  tradeStore;
    private final TradeReader tradeReader;
    private final ImageService imageService;
    private final CommonUtils commonUtils;
    private final AddressService addressService;

    //1. Trade 게시글 등록
    @Override
    @Transactional
    public TradeDetail registerTrade(TradeCommand tradeCommand, User user) {
        //AddressChk
        Address address = addressService.getAddress(new AddressReqDto(tradeCommand));
        //1. 게시물 등록
        TradeCategoryDetail categoryDetail = tradeReader.readTradeCategoryDetailById(tradeCommand.getTradeCategoryDetailId());
        Trade initTrade = tradeCommand.toRegisterEntity(user,categoryDetail,address);
        Trade trade = tradeStore.store(initTrade);

        //2. 게시물 주소 등록
        address.getTradelist().add(trade);

        //3. 게시글 사진 등록
        imageService.uploadPostImage(tradeCommand.getImages(),trade.getTradeId(),trade.getPostType());

        return getTradeInfo(trade.getTradeId());
    }

    //2. 게시물 수정
    @Override
    @Transactional
    public TradeDetail updateTrade( User user,Long tradeId,TradeCommand tradeCommand) {
        //AddressChk
        Address address = addressService.getAddress(new AddressReqDto(tradeCommand));

        //1. 이미지를 제외한 게시글 수정
        Trade trade = tradeReader.getTradeAndUserChk(tradeId, user.getId());
        TradeCategoryDetail categoryDetail = tradeReader.readTradeCategoryDetailById(tradeCommand.getTradeCategoryDetailId());
        Trade initTrade = tradeCommand.toUpdateEntity(user,categoryDetail,address);
        trade.update(initTrade);

        //2. 이미지 수정
        imageService.updateImage(tradeCommand.getImages(),tradeCommand.getDeletedImageId()
                            ,tradeId,PostType.TRADE);
        return getTradeInfo(tradeId);
    }

    //3. 중고거래 게시판 목록 반환(로그인한 유저)
    @Override
    public TradePagingListInfo listTrade(User user, Pageable pageable) {
        if(user.getUserEmail().split("@")[1].contains("guest"))
            return new TradePagingListInfo(tradeReader.readTradeList(0L,pageable));
        return new TradePagingListInfo(tradeReader.readTradeList(user.getId(),pageable));
    }

    //4-1. 중고거래 게시글 상세 조회(비회원)
    @Override
    public TradeDetail tradeDetail(Long tradeId) {
        return getTradeInfo(tradeId);
    }

    //4-2. 중고거래 게시글 상세 조회(회원)
    @Override
    public TradeDetail tradeDetailforUser(Long tradeId, User user) {
        TradeDetail tradeDetail = tradeReader.readTradeDetailForUser(user.getId(), tradeId);
        tradeDetail.setImageList(
                imageService.getImagesByPost(tradeId,PostType.TRADE)
        );
        return tradeDetail;
    }


    //5. 중고거래 게시글 삭제
    @Override
    @Transactional
    public void deleteTrade(Long tradeId, User user) {
        // 1. 게시글 삭제
        tradeStore.delete(tradeId, user.getId());
        // 2. 해당 게시물 이미지 삭제
        imageService.removePostImage(tradeId, PostType.TRADE);
    }

    //6. 인기있는 중고거래 게시글 조회
    @Override
    public List<TradeList> fetchTrendingTrade(User user) {
       return tradeReader.fetchTrendingTrade(user.getId());
    }



    //7-1. 중고거래 게시글 상태변경 [판매 중]
    @Override
    public TradeDetail updateStatusFinding(User user, Long postId) {
        Trade trade = tradeReader.readTradeById(postId);
        trade.isFinding();
        return getTradeInfo(trade.getTradeId());
    }

    //7-2. 중고거래 게시글 상태변경 [예약 중]
    @Override
    public TradeDetail updateStatusReserved(User user, Long postId) {
        Trade trade = tradeReader.readTradeById(postId);
        trade.isReserved();
        return getTradeInfo(trade.getTradeId());
    }

    //7-3. 중고거래 게시글 상태변경 [판매 완료]
    @Override
    public TradeDetail updateStatusMatched(User user, Long postId) {
        Trade trade = tradeReader.readTradeById(postId);
        trade.isMatched();
        return getTradeInfo(trade.getTradeId());
    }
    private TradeDetail getTradeInfo(Long tradeId) {
        List<ImageResDto> imgList = imageService.getImagesByPost(tradeId, PostType.TRADE);
        TradeInfo.TradeDetail tradeDetail = tradeReader.readTradeDetail(tradeId);
        return new TradeDetail(tradeDetail,imgList);
    }


}
