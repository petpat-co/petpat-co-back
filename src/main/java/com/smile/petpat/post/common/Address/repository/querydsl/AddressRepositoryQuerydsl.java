package com.smile.petpat.post.common.Address.repository.querydsl;

import com.smile.petpat.post.common.Address.Dto.AddressReqDto;
import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.rehoming.domain.RehomingInfo;
import com.smile.petpat.post.trade.domain.TradeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AddressRepositoryQuerydsl {
    Page<RehomingInfo> getRehomingsByAddress(Address address, Pageable pageable, String userEmail);
    Page<TradeInfo.TradeList> getTradesByAddress(Address address,Pageable pageable,String userEmail);
}
