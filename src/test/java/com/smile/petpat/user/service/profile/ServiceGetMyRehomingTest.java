package com.smile.petpat.user.service.profile;

import com.smile.petpat.user.domain.ProfileService;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.dto.ProfileDto;
import com.smile.petpat.user.repository.UserRepository;
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
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Transactional
public class ServiceGetMyRehomingTest {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserRepository userRepository;
    Pageable pageable = PageRequest.of(0,10);

    User user;

    @BeforeEach
    void setup(){
        user = User.builder()
                .id(1L)
                .userEmail("userEmail_TEST")
                .nickname("nickname_TEST")
                .password(passwordEncoder.encode("test11!!"))
                .profileImgPath("TEST.jpg")
                .loginType(User.loginTypeEnum.NORMAL)
                .build();
        //사용자가 2개의 Rehoming 게시글 작성
        ProfileDto.RehomingResponse response1=
                ProfileDto.RehomingResponse.builder()
                        .rehomingId(1l)
                        .title("title_TEST 1")
                        .build();

        ProfileDto.RehomingResponse response2=
                ProfileDto.RehomingResponse.builder()
                        .rehomingId(2l)
                        .title("title_TEST 2")
                        .build();

        Page<ProfileDto.RehomingResponse> result =
                new PageImpl<>(Arrays.asList(response1,response2),pageable,2);
        Mockito.when(userRepository.getMyRehoming(user.getId(),pageable))
                .thenReturn(result);
    }
    @Nested
    @DisplayName("Success")
    class Success{

        @Test
        @DisplayName("Success")
        void success(){
            //when
            List<ProfileDto.RehomingResponse> responseList
                    = profileService.getMyRehoming(user, pageable).getContent();

            //then
            assertEquals(2,responseList.size());
            assertEquals("title_TEST 1",responseList.get(0).getTitle());
            assertEquals("title_TEST 2",responseList.get(1).getTitle());

            Mockito.verify(userRepository).getMyRehoming(user.getId(),pageable);
        }
    }

    @Nested
    @DisplayName("Failure")
    class Failure{

        @Test
        @DisplayName("사용자가 작성한 Rehmoming 게시글이 없는 경우")
        void fail_No_Rehoming_Posts(){
            //given
            Mockito.when(userRepository.getMyRehoming(user.getId(),pageable))
                    .thenReturn(Page.empty());

            //when
            List<ProfileDto.RehomingResponse> responses
                    =profileService.getMyRehoming(user,pageable).getContent();

            //then
            assertTrue(responses.isEmpty());
            Mockito.verify(userRepository).getMyRehoming(user.getId(),pageable);
        }
    }
}
