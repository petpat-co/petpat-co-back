package com.smile.petpat.user.repository;

import com.smile.petpat.post.category.domain.CategoryGroup;
import com.smile.petpat.post.category.domain.PetCategory;
import com.smile.petpat.post.category.repository.CategoryGroupRepository;
import com.smile.petpat.post.category.repository.PetCategoryRepository;
import com.smile.petpat.post.rehoming.domain.Rehoming;
import com.smile.petpat.post.rehoming.domain.RehomingCommand;
import com.smile.petpat.post.rehoming.repository.RehomingRepository;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.dto.ProfileDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class getMyRehomingRepositoryTest {
    @Autowired
    private RehomingRepository rehomingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CategoryGroupRepository categoryGroupRepository;
    @Autowired
    private PetCategoryRepository petCategoryRepository;
    Pageable pageable = PageRequest.of(0,10);
    User user;

    @BeforeEach
    void setup(){
        //2명의 User 생성
        user = createUser(1);
        User anotherUser = createUser(2);

        CategoryGroup categoryGroup =categoryGroupRepository.findById(1L).get();
        PetCategory petCategory = petCategoryRepository.findById(1L).get();

        Rehoming rehoming_TEST;

        //사용자가 5개의 Rehoming 게시글 작성
        createRehomingPosts(user,categoryGroup, petCategory,5);

        //다른 사용자가 2개의 Rehoming 게시글 작성
        createRehomingPosts(anotherUser,categoryGroup,petCategory,2);
    }



    @Nested
    @DisplayName("Success")
    public class Success{
        @Test
        @DisplayName("내가 작성한 분양글 조회 성공")
        void Success(){
            //when
            List<ProfileDto.RehomingResponse> responses =
                    userRepository.getMyRehoming(user.getId(),pageable).getContent();

            //then
            assertEquals(5, responses.size());
            //TODO, 조회한 게시글의 작성자가 user가 맞는 지 검증 필요, RehomingResponse 수정 필요
        }

    }
    @Nested
    @DisplayName("Failure")
    class Failure{
        @Test
        @DisplayName("존재하지 않는 User가 조회 시")
        void fail_UserId_Not_Exist(){
            List<ProfileDto.RehomingResponse> responses =
                    userRepository.getMyRehoming(-1L,pageable).getContent();

            assertTrue(responses.isEmpty());
        }
    }


    private User createUser(int num) {
        User user = User.builder()
                .userEmail("userEmail_TEST_"+num)
                .nickname("nickname_TEST_"+num)
                .password(passwordEncoder.encode("test11!!"))
                .profileImgPath("TEST.jpg_"+num)
                .loginType(User.loginTypeEnum.NORMAL)
                .build();

        return userRepository.save(user);
    }
    private void createRehomingPosts(User user, CategoryGroup categoryGroup, PetCategory petCategory,int num) {
        Rehoming rehoming_TEST;
        for(int i = 0; i<num; i++){
            rehoming_TEST = Rehoming.builder()
                    .user(user)
                    .title("title_TEST "+num)
                    .content("content_TEST "+num)
                    .petName("petName_TEST "+num)
                    .category(categoryGroup)
                    .type(petCategory)
                    .gender(RehomingCommand.PetGender.BOY)
                    .cityName("cityName_TEST "+num)
                    .cityCountryName("cityCountryName_TEST "+num)
                    .townShipName("townShipName_TEST "+num)
                    .fullAdName("fullAdName_TEST "+num)
                    .build();

            rehomingRepository.save(rehoming_TEST);
        }
    }
}
