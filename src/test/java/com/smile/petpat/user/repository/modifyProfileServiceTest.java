package com.smile.petpat.user.repository;

import com.smile.petpat.common.exception.CustomException;
import com.smile.petpat.common.response.ErrorCode;
import com.smile.petpat.image.util.ImageUtils;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.domain.UserModify;
import com.smile.petpat.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class modifyProfileServiceTest {
    @MockBean private UserRepository userRepository;
    @Autowired private UserModify userModify;
    @MockBean private ImageUtils imageUtils;
    private UserDto.ModifyUserRequest request;
    private User user;

    @BeforeEach
    void setup() throws IOException {
        Mockito.when(userRepository.save(user)).thenReturn(user);
        user= new User();
        user.setId(1L);
        user.setUserEmail("testUser@test.com");

        request = new UserDto.ModifyUserRequest();
        request.setProfileImgFile(null);
        request.setProfileImgUrl("originImgPath");
    }

    @Nested
    @DisplayName("SUCCESS")
    class Success{
        @Test
        @DisplayName("SUCCESS")
        void success(){
            //given
            Mockito.when(userRepository.findById(user.getId()))
                    .thenReturn(Optional.of(user));
            Mockito.when(imageUtils.saveProfileImage(request.getProfileImgFile(),request.getProfileImgUrl()))
                    .thenReturn("savedFilePath");
            Mockito.when(userRepository.save(Mockito.any(User.class)))
                    .thenReturn(user);
            //when
            User userModified = userModify.modifyProfile(request,user);

            //then
            assertNotNull(userModified);
            assertEquals(user.getUserEmail(),userModified.getUserEmail());
            Mockito.verify(userRepository).findById(user.getId());
            Mockito.verify(imageUtils).saveProfileImage(request.getProfileImgFile(), request.getProfileImgUrl());
            Mockito.verify(userRepository).save(Mockito.any(User.class));
        }
    }

    @Nested
    @DisplayName("FAILURE")
    class Fail{
        @Test
        @DisplayName("FAILURE_MULTIPARTFILE_EMPTY_FILE")
        void fail_Multipartfile_Not_Image(){
            Mockito.when(userRepository.findById(user.getId()))
                    .thenReturn(Optional.of(user));
            Mockito.when(imageUtils.saveProfileImage(request.getProfileImgFile(),request.getProfileImgUrl()))
                    .thenThrow(new CustomException(ErrorCode.ILLEGAL_EMPTY_FILE));

            Exception ex = assertThrows(CustomException.class,
                    ()->userModify.modifyProfile(request,user));
            assertEquals(ErrorCode.ILLEGAL_EMPTY_FILE.getMessage(),
                    ex.getMessage());

        }

        @Test
        @DisplayName("FAILURE_WRONG_TYPE_IMAGE")
        void fail_Wrong_Type_Image(){
            Mockito.when(userRepository.findById(user.getId()))
                    .thenReturn(Optional.of(user));
            Mockito.when(imageUtils.saveProfileImage(request.getProfileImgFile(),request.getProfileImgUrl()))
                    .thenThrow(new CustomException(ErrorCode.WRONG_TYPE_IMAGE));

            Exception ex = assertThrows(CustomException.class,
                    ()->userModify.modifyProfile(request,user));
            assertEquals(ErrorCode.WRONG_TYPE_IMAGE.getMessage(),
                    ex.getMessage());

        }

        @Test
        @DisplayName("FAILURE_USER_NOT_EXIST")
        void fail_User_Not_Exist(){
            Mockito.when(userRepository.findById(user.getId()))
                    .thenReturn(Optional.empty());

            Exception ex = assertThrows(CustomException.class,
                    ()->userModify.modifyProfile(request,user));
            assertEquals(ErrorCode.ILLEGAL_USER_NOT_EXIST.getMessage(),
                    ex.getMessage());

        }
    }
}
