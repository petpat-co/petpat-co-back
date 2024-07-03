package com.smile.petpat.post.common.Address.service;

import com.smile.petpat.common.exception.CustomException;
import com.smile.petpat.common.response.ErrorCode;
import com.smile.petpat.post.common.Address.Dto.AddressReqDto;
import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.common.Address.repository.AddressRepository;
import com.smile.petpat.post.common.CommonUtils;
import com.smile.petpat.post.trade.domain.TradeInfo;
import com.smile.petpat.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class getTradesByAddressServiceTest {
    @MockBean private AddressRepository addressRepository;
    @MockBean private CommonUtils commonUtils;
    @Autowired private AddressService addressService;

    private User user;
    private User wrongUser;
    private Pageable pageable = PageRequest.of(0,10);
    private Page<TradeInfo.TradeList> expectedResult
            = new PageImpl<>(Collections.emptyList(),pageable,0);
    private AddressReqDto addressReqDto;
    private Address address;

    @BeforeEach
    void setup(){
        user = new User();
        user.setUserEmail("testUser");
        wrongUser = new User();
        wrongUser.setUserEmail("wrongUser");

        mockAddressRepository();
        mockCommonUtils();
    }

    @Nested
    public class Success{
        @Test
        @DisplayName("SUCCESS")
        void success(){
            TradeInfo.TradePagingListInfo tradePagingListInfo=
                    addressService.getTradesByAddress(addressReqDto,pageable,user.getUserEmail());

            assertEquals(expectedResult.getContent(),tradePagingListInfo.getContent());

            Mockito.verify(addressRepository).getTradesByAddress(address,pageable, user.getUserEmail());
        }
    }

    @Nested
    public class Failure{
        @Test
        @DisplayName("FAIL_ADDRESS_NOT_EXIST")
        void fail_Address_Not_Exist(){
            AddressReqDto wrongAddress = new AddressReqDto("부산특별시","","마포구","연남동");

            Exception ex =assertThrows(CustomException.class,
                    ()-> addressService.getTradesByAddress(wrongAddress,pageable,user.getUserEmail()));
            assertEquals(ErrorCode.ILLEGAL_ADDRESS_NOT_EXIST.getMessage(),ex.getMessage());
        }

        @Test
        @DisplayName("FAIL_USER_NOT_EXIST")
        void fail_User_Not_Exist(){
            Exception ex = assertThrows(CustomException.class,
                    ()->addressService.getTradesByAddress(addressReqDto,pageable,wrongUser.getUserEmail()));
            assertEquals(ErrorCode.ILLEGAL_USER_NOT_EXIST.getMessage(),
                    ex.getMessage());
        }
    }
    private void mockCommonUtils() {
        Mockito.when(commonUtils.userChk(user.getUserEmail()))
                .thenReturn(user);
        Mockito.when(commonUtils.userChk(wrongUser.getUserEmail()))
                .thenThrow(new CustomException(ErrorCode.ILLEGAL_USER_NOT_EXIST));
    }

    private void mockAddressRepository() {
        Mockito.when(addressRepository.findAddressByProvinceAndCityAndDistrictAndTown("서울특별시", "", "마포구", "연남동"))
                .thenReturn(Optional.of(new Address("서울특별시", "", "마포구", "연남동")));

        addressReqDto = new AddressReqDto("서울특별시","","마포구","연남동");
        address = addressService.getAddress(addressReqDto);

        Mockito.when(addressRepository.getTradesByAddress(address,pageable, user.getUserEmail()))
                .thenReturn(expectedResult);
    }
}
