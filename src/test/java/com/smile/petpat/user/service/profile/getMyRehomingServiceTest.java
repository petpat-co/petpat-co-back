package com.smile.petpat.user.service.profile;

import com.smile.petpat.common.exception.CustomException;
import com.smile.petpat.common.response.ErrorCode;
import com.smile.petpat.post.common.CommonUtils;
import com.smile.petpat.user.domain.ProfileService;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.dto.ProfileDto;
import com.smile.petpat.user.repository.UserRepository;
import com.smile.petpat.utils.TestUtils;
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
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class getMyRehomingServiceTest {
    @Autowired
    private ProfileService profileService;
    @MockBean
    private UserRepository userRepository;
    @MockBean private CommonUtils commonUtils;
    Pageable pageable = PageRequest.of(0,10);
    private Page<ProfileDto.RehomingResponse> expectedResult
            = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);

    private User user;
    private User deletedUser;

    @BeforeEach
    void setup(){
        user =new User();
        user.setId(1L);
        user.setUserEmail("TestUser1");

        Mockito.when(userRepository.getMyRehoming(user.getId(),pageable))
                .thenReturn(expectedResult);
    }
    @Nested
    @DisplayName("SUCCESS")
    class Success{

        @Test
        @DisplayName("SUCCESS")
        void success(){
            Page<ProfileDto.RehomingResponse> response = profileService.getMyRehoming(user,pageable);

            assertEquals(expectedResult.getContent(), response.getContent());
            Mockito.verify(userRepository).getMyRehoming(user.getId(),pageable);
        }
    }

    @Nested
    @DisplayName("FAILURE")
    class Failure{

        @Test
        @DisplayName("FAIL_USER_NOT_EXIST")
        void fail_User_Not_Exist(){
            deletedUser = new User();
            deletedUser.setUserEmail(" ");
            deletedUser.setDeleted(true);

            Mockito.doThrow(new CustomException(ErrorCode.ILLEGAL_USER_NOT_EXIST))
                    .when(commonUtils).userChk(deletedUser.getUserEmail());

            Exception ex = assertThrows(CustomException.class,
                    ()-> profileService.getMyRehoming(deletedUser,pageable));
            assertEquals(ErrorCode.ILLEGAL_USER_NOT_EXIST.getMessage(),
                    ex.getMessage());
        }
    }
}
