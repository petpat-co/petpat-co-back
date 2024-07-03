package com.smile.petpat.post.trade.domain;

import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.category.domain.TradeCategoryDetail;
import com.smile.petpat.post.category.repository.TradeCategoryDetailRepository;
import com.smile.petpat.post.common.status.PostStatus;
import com.smile.petpat.post.trade.repository.TradeRepository;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.domain.UserRole;
import com.smile.petpat.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class TradeTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TradeCategoryDetailRepository tradeCategoryDetailRepository;

    @Autowired
    private TradeRepository tradeRepository;

    User user1;
    @BeforeEach
    void beforeEach(){
            user1 = User
                    .builder()
                    .userEmail("test0001@email.com")
                    .nickname("닉네임테스트1")
                    .password("passwordtest1234@")
                    .profileImgPath("http://testUserProfile1.jpg")
                    .loginType(User.loginTypeEnum.NORMAL)
                    .userRole(UserRole.ROLE_USER)
                    .build();
            userRepository.save(user1);



    }
    @DisplayName("거래 게시물을 등록한다.")
    @Test
    void createTrade(){
        //given
        TradeCategoryDetail tradeCategoryDetail1 = getTradeCategoryDetail(1L);
        Trade saveTrade = initTrade(tradeCategoryDetail1);
        long beforeCount = tradeRepository.count();

        tradeRepository.save(saveTrade);
        // when
        long afterCount = tradeRepository.count();

        // then
        assertThat(afterCount).isEqualTo(beforeCount+1);

    }


    @DisplayName("거래 게시물을 등록할때 거래상태를 판매중으로 등록한다.")
    @Test
    void CreateTradeToTradeFinding() {
        // given
        TradeCategoryDetail tradeCategoryDetail1 = getTradeCategoryDetail(1L);
        Trade saveTrade = initTrade(tradeCategoryDetail1);
        tradeRepository.save(saveTrade);
        // when

        Trade trade = tradeRepository.findById(saveTrade.getTradeId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물 입니다.")
        );
        // then
        assertThat(trade.getStatus()).isEqualByComparingTo(PostStatus.TRADE_FINDING);
    }

    @DisplayName("거래 게시물의 거래상태를 예약중으로 변경한다.")
    @Test
    void ChangeTradeStatusToTradeReserved() {
        // given
        TradeCategoryDetail tradeCategoryDetail1 = getTradeCategoryDetail(1L);
        Trade saveTrade = initTrade(tradeCategoryDetail1);
        tradeRepository.save(saveTrade);

        // when
        Trade trade = tradeRepository.findById(saveTrade.getTradeId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물 입니다.")
        );
        trade.isReserved();

        // then
        assertThat(trade.getStatus()).isEqualByComparingTo(PostStatus.TRADE_RESERVING);

    }
    @DisplayName("거래 게시물의 거래상태를 판매완료로 변경한다.")
    @Test
    void ChangeTradeStatusToTradeMatched() {
        // given
        TradeCategoryDetail tradeCategoryDetail1 = getTradeCategoryDetail(1L);
        Trade saveTrade = initTrade(tradeCategoryDetail1);
        tradeRepository.save(saveTrade);

        // when
        Trade trade = tradeRepository.findById(saveTrade.getTradeId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물 입니다.")
        );
        trade.isMatched();

        // then
        assertThat(trade.getStatus()).isEqualByComparingTo(PostStatus.TRADE_COMPLETED);
    }

    // 공통 메소드
    private Trade initTrade(TradeCategoryDetail tradeCategoryDetail1) {
        return Trade.builder()
                .user(user1)
                .title("제목테스트")
                .content("내용 테스트")
                .price(10000L)
                .tradeCategoryDetail(tradeCategoryDetail1)
                .build();
    }

    private TradeCategoryDetail getTradeCategoryDetail(Long categoryId) {
        return tradeCategoryDetailRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 카테고리디테일아이디 입니다.")
        );
    }
}