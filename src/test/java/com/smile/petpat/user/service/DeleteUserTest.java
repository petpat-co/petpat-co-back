package com.smile.petpat.user.service;

import com.smile.petpat.common.exception.CustomException;
import com.smile.petpat.common.response.ErrorCode;
import com.smile.petpat.post.category.domain.CategoryGroup;
import com.smile.petpat.post.category.domain.PetCategory;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.category.repository.CategoryGroupRepository;
import com.smile.petpat.post.category.repository.PetCategoryRepository;
import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.rehoming.domain.Rehoming;
import com.smile.petpat.post.rehoming.domain.RehomingCommand;
import com.smile.petpat.post.rehoming.repository.RehomingRepository;
import com.smile.petpat.user.domain.ProfileService;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.repository.UserRepository;
import com.smile.petpat.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class DeleteUserTest {
    @Autowired
    private ProfileService     profileService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RehomingRepository rehomingRepository;
    @Autowired
    private CategoryGroupRepository categoryGroupRepository;
    @Autowired
    private PetCategoryRepository petCategoryRepository;
    @Autowired private TestUtils testUtils;
    private Address address;

    Rehoming rehoming;
    @BeforeEach
    void setup(){
        User user = testUtils.createUser(1);

        testUtils.createRehomingPosts(user,address,1);
    }

    @Nested
    @DisplayName("아이디 삭제 성공")
    class DeleteSuccess{

        @Test
        @DisplayName("Success")
        void deleteSuccess() {
            //given
            User user = userRepository.findByUserEmail("userEmail_TEST@test.com").get();

            //when
            profileService.deleteUser(user);


            //then
            Exception ex =assertThrows(NoSuchElementException.class,
                    ()->userRepository.findById(user.getId()).get());
        }
    }

    @Nested
    @DisplayName("아이디 삭제 실패")
    class DeleteFail{

        @Test
        @DisplayName("실패 _ 존재하지 않는(이미 삭제된) 아이디")
        void deleteFail_User_Not_Exist(){
            //given
            User user = userRepository.findByUserEmail("userEmail_TEST@test.com").get();
            profileService.deleteUser(user);

            //when&&then
            Exception ex = assertThrows(CustomException.class,
                    ()-> profileService.deleteUser(user));
            assertEquals(ex.getMessage(),
                    ErrorCode.ILLEGAL_USER_NOT_EXIST.getMessage());
        }
    }
}
