package com.smile.petpat.post.trade.service;

import com.smile.petpat.post.category.domain.TradeCategoryDetail;
import com.smile.petpat.post.category.repository.TradeCategoryDetailRepository;
import com.smile.petpat.post.common.WeekRange;
import com.smile.petpat.post.trade.domain.Trade;
import com.smile.petpat.post.trade.domain.TradeInfo;
import com.smile.petpat.post.trade.domain.TradeReader;
import com.smile.petpat.post.trade.repository.TradeRepository;
import com.smile.petpat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class TradeReaderImpl implements TradeReader {
    private final TradeRepository tradeRepository;
    private final TradeCategoryDetailRepository tradeCategoryDetailRepository;

    @Override
    public Page<TradeInfo.TradeList> readTradeList(User user, Pageable pageable) {
        return tradeRepository.tradeList_Paging(user.getId(),pageable);
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

    @Override
    public TradeInfo.TradeDetail readTradeDetail(Long userId, Long tradeId) {
        readTradeById(tradeId);
        return tradeRepository.tradeDetail(userId,tradeId);
    }

    @Override
    public Trade userChk(Long tradeId,Long userId){
       Trade trade = readTradeById(tradeId);
       if(!trade.getUser().getId().equals(userId)) {
           throw new IllegalArgumentException("권한이 없습니다.");
       }
       return trade;
    }

    @Override
    public List<TradeInfo.TradeList> fetchTrendingTrade(Long userId) {
        WeekRange weekRange = new WeekRange();
        log.info("start -> {} ",weekRange.getStartOfWeek());
        log.info("end   -> {} ",weekRange.getEndOfWeek());
       return tradeRepository.fetchTrendingTrade(userId,weekRange.getStartOfWeek(),weekRange.getEndOfWeek());

    }
}
