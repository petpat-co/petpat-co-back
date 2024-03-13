package com.smile.petpat.post.trade.service;

import com.smile.petpat.post.trade.domain.TradeCommand;
import com.smile.petpat.post.trade.domain.TradeInfo;
import com.smile.petpat.user.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TradeService{

    Long registerTrade(TradeCommand tradeCommand, User user);

    TradeInfo.TradePagingListInfo listTrade(User user, Pageable pageable);

    void deleteTrade(Long tradeId,User user);

    TradeInfo.TradeDetail updateTrade(User user, Long postId,TradeCommand tradeCommand);

    TradeInfo.TradeDetail tradeDetail(Long tradeId);

    TradeInfo.TradeDetail tradeDetailforUser(Long tradeId, User user);

    void updateStatusFinding(User user, Long postId);

    void updateStatusReserved(User user, Long postId);

    void updateStatusMatched(User user, Long postId);


    List<TradeInfo.TradeList> fetchTrendingTrade(User user);
}
