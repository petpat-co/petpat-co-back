package com.smile.petpat.user.repository;

import com.smile.petpat.post.common.Address.Dto.AddressReqDto;
import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.common.Address.service.AddressService;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.dto.ProfileDto;
import com.smile.petpat.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@Import(TestUtils.class)
@ActiveProfiles("test")
@Transactional
public class getMyRehomingRepositoryTest {
    @Autowired private UserRepository userRepository;
    @Autowired private TestUtils testUtils;
    @Autowired private AddressService addressService;
    Pageable pageable = PageRequest.of(0,10);
    private User user;
    private User anotherUser;


    @BeforeEach
    void setup(){
        //2명의 User 생성
        user = testUtils.createUser(1);
        anotherUser = testUtils.createUser(2);

        Address address = addressService.getAddress(new AddressReqDto("서울특별시","","마포구","연남동"));


        //사용자가 5개의 Rehoming 게시글 작성
        testUtils.createRehomingPosts(user,address,5);

        //다른 사용자가 2개의 Rehoming 게시글 작성
        testUtils.createRehomingPosts(anotherUser,address,2);
    }



    @Nested
    @DisplayName("SUCCESS")
    public class Success {
        @Test
        @DisplayName("SUCCESS")
        void Success() {
            //when
            List<ProfileDto.RehomingResponse> responses1 =
                    userRepository.getMyRehoming(user.getId(), pageable).getContent();
            List<ProfileDto.RehomingResponse> responses2 =
                    userRepository.getMyRehoming(anotherUser.getId(), pageable).getContent();

            //then
            assertEquals(5, responses1.size());
            assertEquals(2,responses2.size());

        }
    }
}
