package com.smile.petpat.post.common.Address.repository;

import com.smile.petpat.common.exception.CustomException;
import com.smile.petpat.common.response.ErrorCode;
import com.smile.petpat.post.common.Address.Dto.AddressReqDto;
import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.common.Address.service.AddressService;
import com.smile.petpat.post.trade.domain.TradeInfo;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//@WebMvcTest(AddressRepository.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class getTradesByAddressRepositoryTest {
    @Autowired private AddressService addressService;
    @Autowired private AddressRepository addressRepository;
    @Autowired private TestUtils testUtils;


    private Pageable pageable = PageRequest.of(0,10);
    private User user;
    private Address address1;
    private Address address2;

    @BeforeEach
    void setup(){
        user= testUtils.createUser(1);

        address1 = addressService.getAddress(new AddressReqDto("서울특별시","","마포구","연남동"));
        address2 = addressService.getAddress(new AddressReqDto("서울특별시","","종로구","평창동"));

        testUtils.createTradePosts(user,address1,6);
        testUtils.createTradePosts(user,address2,2);
    }

    @Nested
    @DisplayName("Success")
    public class Success{
        @Test
        @DisplayName("Success")
        void success(){
            List<TradeInfo.TradeList> tradeInfos =
                    addressRepository.getTradesByAddress(address1,pageable, user.getUserEmail()).getContent();

            assertEquals(6,tradeInfos.size());
            for(TradeInfo.TradeList tradeList: tradeInfos){
                assertEquals("서울특별시 마포구 연남동",tradeList.getRegion());
            }
        }
    }

    @Nested
    @DisplayName("Failure")
    public class Failure{
        @Test
        @DisplayName("FAIL_ADDRESS_NOT_EXIST")
        void fail_Address_Not_Exist(){
            AddressReqDto wrongAddress = new AddressReqDto("부산특별시","","마포구","연남동");

            Exception ex = assertThrows(CustomException.class,
                    ()->addressService.getAddress(wrongAddress));

            assertEquals(ErrorCode.ILLEGAL_ADDRESS_NOT_EXIST.getMessage(),
                    ex.getMessage());
        }
    }
}
