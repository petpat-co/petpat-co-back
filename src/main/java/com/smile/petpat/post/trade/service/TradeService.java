package com.smile.petpat.post.trade.service;

import com.smile.petpat.post.trade.domain.TradeCommand;
import com.smile.petpat.post.trade.domain.TradeInfo;
import com.smile.petpat.user.domain.User;

import java.util.List;

public interface TradeService{

    void registerTrade(TradeCommand tradeCommand, User user);

    List<TradeInfo> listTrade();

    void deleteTrade(Long tradeId,User user);

    TradeInfo updateTrade(TradeCommand tradeCommand, User user, Long postId);

    TradeInfo tradeDetail(Long tradeId);

    TradeInfo tradeDetailforUser(Long tradeId, User user);
}
