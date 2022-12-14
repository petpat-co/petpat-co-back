package com.smile.petpat.post.trade.service;

import com.smile.petpat.post.category.domain.TradeCategoryDetail;
import com.smile.petpat.post.category.repository.TradeCategoryDetailRepository;
import com.smile.petpat.post.trade.domain.Trade;
import com.smile.petpat.post.trade.domain.TradeReader;
import com.smile.petpat.post.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class TradeReaderImpl implements TradeReader {
    private final TradeRepository tradeRepository;
    private final TradeCategoryDetailRepository tradeCategoryDetailRepository;

    @Override
    public List<Trade> readTradeList() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade readTradeById(Long tradeId) {
        return   tradeRepository.findById(tradeId).orElseThrow(
                ()  -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );
    }

    @Override
    public TradeCategoryDetail readTradeCategoryDetailById(Long tradeCategoryDetailId){
       return tradeCategoryDetailRepository.findByTradeCategoryDetailId(tradeCategoryDetailId).orElseThrow(
               () -> new IllegalArgumentException("존재하지 않는 중고거래 카테고리입니다.")
       );
    }

    public void userChk(Long tradeId,Long userId){
       Trade trade = readTradeById(tradeId);
       if(!trade.getUser().getId().equals(userId)) {
           throw new IllegalArgumentException("권한이 없습니다.");
       }
    }
}
