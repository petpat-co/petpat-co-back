package com.smile.petpat.utils;

import com.smile.petpat.post.category.domain.CategoryGroup;
import com.smile.petpat.post.category.domain.PetCategory;
import com.smile.petpat.post.category.domain.TradeCategoryDetail;
import com.smile.petpat.post.category.repository.CategoryGroupRepository;
import com.smile.petpat.post.category.repository.PetCategoryRepository;
import com.smile.petpat.post.category.repository.PostCategoryGroupRepository;
import com.smile.petpat.post.category.repository.TradeCategoryDetailRepository;
import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.rehoming.domain.Rehoming;
import com.smile.petpat.post.rehoming.domain.RehomingCommand;
import com.smile.petpat.post.rehoming.repository.RehomingRepository;
import com.smile.petpat.post.trade.domain.Trade;
import com.smile.petpat.post.trade.repository.TradeRepository;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestUtils {
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private TradeRepository tradeRepository;
    @Autowired private TradeCategoryDetailRepository tradeCategoryDetailRepository;
    @Autowired private RehomingRepository rehomingRepository;
    @Autowired private CategoryGroupRepository categoryGroupRepository;
    @Autowired private PetCategoryRepository petCategoryRepository;
    public User createUser(int num){
        User user = User.builder()
                .userEmail("userEmail_TEST_" + num)
                .nickname("nickname_TEST_" + num)
                .password(passwordEncoder.encode("test11!!"))
                .profileImgPath("TEST.jpg_" + num)
                .loginType(User.loginTypeEnum.NORMAL)
                .build();

        return userRepository.save(user);
    }

    public void createTradePosts(User user, Address address, int num){
        TradeCategoryDetail tradeCategoryDetail = tradeCategoryDetailRepository.getReferenceById(1L);
        for(int i =0; i<num; i++){
            Trade trade = Trade
                    .builder()
                    .user(user)
                    .title("테스트중고거래제목"+i)
                    .content("테스트중고거래내용"+i)
                    .price(1000L)
                    .address(address)
                    .tradeCategoryDetail(tradeCategoryDetail)
                    .build();
            address.getTradelist().add(tradeRepository.save(trade));
        }
    }

    public void createRehomingPosts(User user, Address address, int num) {
        CategoryGroup categoryGroup = categoryGroupRepository.getReferenceById(1L);
        PetCategory petCategory = petCategoryRepository.getReferenceById(1L);
        Rehoming rehoming_TEST;
        for (int i = 0; i < num; i++) {
            rehoming_TEST = Rehoming.builder()
                    .user(user)
                    .title("title_TEST " + i)
                    .content("content_TEST " + i)
                    .petName("petName_TEST " + i)
                    .category(categoryGroup)
                    .type(petCategory)
                    .gender(RehomingCommand.PetGender.BOY)
                    .address(address)
                    .build();

            address.getRehomingList().add(rehomingRepository.save(rehoming_TEST));
        }
    }
}
