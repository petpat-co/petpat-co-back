package com.smile.petpat.post.trade.service;

import com.smile.petpat.post.trade.domain.Trade;
import com.smile.petpat.post.trade.domain.TradeStore;
import com.smile.petpat.post.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
public class TradeStoreImpl implements TradeStore {

    private final TradeRepository tradeRepository;
    private final TradeReaderImpl  tradeReader;


    @Override
    public Trade store(Trade trade) {
        return tradeRepository.save(trade);
    }

    @Override
    public void delete(Long tradeId,Long userId) {
        tradeReader.getTradeAndUserChk(tradeId,userId);
        tradeRepository.deleteById(tradeId);
    }
}
