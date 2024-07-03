package com.smile.petpat.post.trade.service;

import com.smile.petpat.post.trade.domain.TradeCommand;
import com.smile.petpat.user.domain.User;
import org.springframework.data.domain.Pageable;
import java.util.List;

import com.smile.petpat.post.trade.domain.TradeInfo.*;

public interface TradeService{

    TradeDetail registerTrade(TradeCommand tradeCommand, User user);

    TradePagingListInfo listTrade(User user, Pageable pageable);

    void deleteTrade(Long tradeId,User user);

    TradeDetail updateTrade(User user, Long postId,TradeCommand tradeCommand);

    TradeDetail tradeDetail(Long tradeId);

    TradeDetail tradeDetailforUser(Long tradeId, User user);

    TradeDetail updateStatusFinding(User user, Long postId);

    TradeDetail updateStatusReserved(User user, Long postId);

    TradeDetail updateStatusMatched(User user, Long postId);


    List<TradeList> fetchTrendingTrade(User user);
}
