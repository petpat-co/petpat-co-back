package com.smile.petpat.post.trade.domain;

import com.smile.petpat.post.category.domain.TradeCategoryDetail;
import com.smile.petpat.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TradeReader {
    Page<TradeInfo.TradeList> readTradeList(User user, Pageable pageable);
    Trade readTradeById(Long tradeId);
    TradeCategoryDetail readTradeCategoryDetailById(Long tradeCategoryDetailId);
    TradeInfo.TradeDetail readTradeDetail(Long userId, Long tradeId);
    Trade userChk(Long tradeId,Long userId);

    List<TradeInfo.TradeList> fetchTrendingTrade(Long userId);
}
