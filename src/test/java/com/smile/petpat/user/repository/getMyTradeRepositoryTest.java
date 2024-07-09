package com.smile.petpat.user.repository;

import com.smile.petpat.post.common.Address.Dto.AddressReqDto;
import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.common.Address.service.AddressService;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.dto.ProfileDto;
import com.smile.petpat.utils.TestUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
@Transactional
public class getMyTradeRepositoryTest {
    @Autowired private UserRepository userRepository;
    @Autowired private TestUtils testUtils;
    @Autowired private AddressService addressService;
    private User user;
    private User anotherUser;

    private Address address;
    private Pageable pageable = PageRequest.of(0,10);

    @BeforeEach
    void setup(){
        user = testUtils.createUser(1);
        anotherUser = testUtils.createUser(2);
        address =addressService.getAddress(new AddressReqDto( "서울특별시","","마포구","연남동"));

        testUtils.createTradePosts(user,address,5);
        testUtils.createTradePosts(anotherUser,address,2);
    }

    @Nested
    @DisplayName("SUCCESS")
    class Success{
        @Test
        @DisplayName("SUCCESS")
        void success(){
            List<ProfileDto.TradeResponse> response1 = userRepository.getMyTrade(user.getId(),pageable).getContent();
            List<ProfileDto.TradeResponse> response2 = userRepository.getMyTrade(anotherUser.getId(),pageable).getContent();

            assertEquals(5,response1.size());
            assertEquals(2,response2.size());
        }
    }
}
