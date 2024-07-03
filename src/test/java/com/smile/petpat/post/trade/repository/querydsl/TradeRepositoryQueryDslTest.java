//package com.smile.petpat.post.trade.repository.querydsl;
//
//import com.querydsl.core.QueryResults;
//import com.querydsl.core.types.ExpressionUtils;
//import com.querydsl.core.types.Projections;
//import com.querydsl.jpa.JPAExpressions;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.smile.petpat.image.domain.ImagePriority;
//import com.smile.petpat.image.domain.QImage;
//import com.smile.petpat.post.category.domain.PostType;
//import com.smile.petpat.post.trade.domain.QTrade;
//import com.smile.petpat.post.trade.domain.Trade;
//import com.smile.petpat.post.trade.domain.TradeInfo;
//import com.smile.petpat.user.domain.QUser;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//
//import javax.persistence.EntityManager;
//
//import java.util.List;
//
//import static com.smile.petpat.image.domain.QImage.*;
//import static com.smile.petpat.post.common.bookmarks.domain.QBookmark.bookmark;
//import static com.smile.petpat.post.common.likes.domain.QLikes.likes;
//import static com.smile.petpat.post.trade.domain.QTrade.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class TradeRepositoryQueryDslTest {
//    static {
//        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
//    }
//    @Autowired
//    EntityManager em;
//    JPAQueryFactory queryFactory;
//
//    @BeforeEach
//    public void before() {
//        queryFactory = new JPAQueryFactory(em);
//
//    }
//    @Test
//    @DisplayName("중고거래 게시판 리스트 조회")
//    public void trade_list_read(){
//        Long userId = 1L;
//        List<TradeInfo.TradeList> result = queryFactory
//                .select
//                        (Projections.constructor
//                                (TradeInfo.TradeList.class,
//                                trade.tradeId,
//                                trade.user.nickname,
//                                trade.title,
//                                trade.price,
//                                trade.cityName,
//                                trade.cityCountryName,
//                                ExpressionUtils.as(
//                                        JPAExpressions
//                                                .select(image.filePath)
//                                                .from(image)
//                                                .where(
//                                                        image.postId.eq(trade.tradeId)
//                                                                .and(image.postType.eq(PostType.TRADE))
//                                                                .and(image.priority.eq(ImagePriority.PRIORITY_1))
//                                                )
//                                        ,"image"),
//                                ExpressionUtils.as(
//                                        JPAExpressions
//                                                .select(likes.count())
//                                                .from(likes)
//                                                .where(
//                                                        likes.user.id.eq(userId)
//                                                                .and(likes.postId.eq(trade.tradeId))
//                                                ), "isLiked"),
//                                ExpressionUtils.as(
//                                        JPAExpressions
//                                                .select(bookmark.count())
//                                                .from(bookmark)
//                                                .where(
//                                                        bookmark.user.id.eq(userId)
//                                                                .and(bookmark.postId.eq(trade.tradeId))
//                                                ), "isBookmarked"),
//                                trade.viewCnt,
//                                ExpressionUtils.as(
//                                        JPAExpressions
//                                                .select(likes.count())
//                                                .from(likes)
//                                                .where(likes.postId.eq(trade.tradeId)),
//                                        "likeCnt"),
//                                ExpressionUtils.as(
//                                        JPAExpressions
//                                                .select(bookmark.count())
//                                                .from(bookmark)
//                                                .where(bookmark.postId.eq(trade.tradeId)),
//                                        "bookmarkCnt")
//                                )
//                        )
//                .from(trade)
//                .fetch();
//
//        for (TradeInfo.TradeList TradeList : result) {
//            System.out.println("TradeInfo = " + TradeList.toString());
//        }
//    }
//    @Test
//    @DisplayName("중고거래 게시판 리스트 조회(paging)")
//    public void trade_list_read_usedPaging(){
//        Long userId = 1L;
//        PageRequest pageable = PageRequest.of(1,5);
//        QueryResults<TradeInfo.TradeList> result = queryFactory
//                .select
//                        (Projections.constructor
//                                (TradeInfo.TradeList.class,
//                                        trade.tradeId,
//                                        trade.user.nickname,
//                                        trade.title,
//                                        trade.price,
//                                        trade.cityName,
//                                        trade.cityCountryName,
//                                        ExpressionUtils.as(
//                                                JPAExpressions
//                                                        .select(image.filePath)
//                                                        .from(image)
//                                                        .where(
//                                                                image.postId.eq(trade.tradeId)
//                                                                        .and(image.postType.eq(PostType.TRADE))
//                                                                        .and(image.priority.eq(ImagePriority.PRIORITY_1))
//                                                        )
//                                                ,"image"),
//                                        ExpressionUtils.as(
//                                                JPAExpressions
//                                                        .select(likes.count())
//                                                        .from(likes)
//                                                        .where(
//                                                                likes.user.id.eq(userId)
//                                                                        .and(likes.postId.eq(trade.tradeId))
//                                                        ), "isLiked"),
//                                        ExpressionUtils.as(
//                                                JPAExpressions
//                                                        .select(bookmark.count())
//                                                        .from(bookmark)
//                                                        .where(
//                                                                bookmark.user.id.eq(userId)
//                                                                        .and(bookmark.postId.eq(trade.tradeId))
//                                                        ), "isBookmarked"),
//                                        trade.viewCnt,
//                                        ExpressionUtils.as(
//                                                JPAExpressions
//                                                        .select(likes.count())
//                                                        .from(likes)
//                                                        .where(likes.postId.eq(trade.tradeId)),
//                                                "likeCnt"),
//                                        ExpressionUtils.as(
//                                                JPAExpressions
//                                                        .select(bookmark.count())
//                                                        .from(bookmark)
//                                                        .where(bookmark.postId.eq(trade.tradeId)),
//                                                "bookmarkCnt")
//                                )
//                        )
//                .from(trade)
//                .orderBy(trade.createdAt.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetchResults();
//        List<TradeInfo.TradeList> lists = result.getResults();
//        long total = result.getTotal();
//
//        for (TradeInfo.TradeList TradeList : lists) {
//            System.out.println("TradeInfo = " + TradeList.toString());
//        }
//        System.out.println(total +"+"+ pageable);
//    }
//
//    @Test
//    @DisplayName("중고거래 게시판 상세조회")
//    public void trade_detail_read(){
//        Long userId= 1L;
//        Long tradeId=2L;
//        TradeInfo.TradeDetail result =  queryFactory
//                .select
//                        (Projections.constructor
//                                (TradeInfo.TradeDetail.class,
//                                        trade.tradeId,
//                                        trade.user.id,
//                                        trade.user.nickname,
//                                        trade.title,
//                                        trade.content,
//                                        trade.price,
//                                        trade.cityName,
//                                        trade.cityCountryName,
//                                        trade.townShipName,
//                                        trade.detailAdName,
//                                        trade.fullAdName,
//                                        trade.postType,
//                                        ExpressionUtils.as(
//                                                JPAExpressions
//                                                        .select(likes.count())
//                                                        .from(likes)
//                                                        .where(
//                                                                likes.user.id.eq(userId)
//                                                                        .and(likes.postId.eq(trade.tradeId))
//                                                        ), "isLiked"),
//                                        ExpressionUtils.as(
//                                                JPAExpressions
//                                                        .select(bookmark.count())
//                                                        .from(bookmark)
//                                                        .where(
//                                                                bookmark.user.id.eq(userId)
//                                                                        .and(bookmark.postId.eq(trade.tradeId))
//                                                        ), "isBookmarked"),
//                                        trade.viewCnt,
//                                        ExpressionUtils.as(
//                                                JPAExpressions
//                                                        .select(likes.count())
//                                                        .from(likes)
//                                                        .where(likes.postId.eq(trade.tradeId)),
//                                                "likeCnt"),
//                                        ExpressionUtils.as(
//                                                JPAExpressions
//                                                        .select(bookmark.count())
//                                                        .from(bookmark)
//                                                        .where(bookmark.postId.eq(trade.tradeId)),
//                                                "bookmarkCnt"),
//                                        trade.tradeCategoryDetail.tradeCategoryDetailName
//                                )
//                        )
//                .from(trade)
//                .where(trade.tradeId.eq(tradeId))
//                .fetchOne();
//        System.out.println(result.toString());
//    }
//
//}