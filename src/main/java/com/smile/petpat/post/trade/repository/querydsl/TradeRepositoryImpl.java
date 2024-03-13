package com.smile.petpat.post.trade.repository.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.rehoming.domain.RehomingInfo;
import com.smile.petpat.post.trade.domain.TradeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.smile.petpat.image.domain.QImage.image;
import static com.smile.petpat.post.common.bookmarks.domain.QBookmark.bookmark;
import static com.smile.petpat.post.common.likes.domain.QLikes.likes;
import static com.smile.petpat.post.trade.domain.QTrade.trade;

public class TradeRepositoryImpl implements TradeRepositoryQueryDsl{
    private final JPAQueryFactory queryFactory;

    public TradeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<TradeInfo.TradeList> tradeList(Long userId) {
               return  queryFactory
                .select
                        (Projections.constructor
                                (TradeInfo.TradeList.class,
                                        trade.tradeId,
                                        trade.user.nickname,
                                        trade.title,
                                        trade.price,
                                        trade.cityName,
                                        trade.cityCountryName,
                                        ExpressionUtils.as(
                                                JPAExpressions
                                                        .select(image.filePath)
                                                        .from(image)
                                                        .where(
                                                                image.postId.eq(trade.tradeId)
                                                                        .and(image.postType.eq(PostType.TRADE))
                                                        )
                                                        .orderBy(image.imageId.asc()).limit(1)
                                                ,"image"),
                                        ExpressionUtils.as(
                                                JPAExpressions
                                                        .select(likes.count())
                                                        .from(likes)
                                                        .where(
                                                                likes.user.id.eq(userId)
                                                                        .and(likes.postId.eq(trade.tradeId))
                                                        ), "isLiked"),
                                        ExpressionUtils.as(
                                                JPAExpressions
                                                        .select(bookmark.count())
                                                        .from(bookmark)
                                                        .where(
                                                                bookmark.user.id.eq(userId)
                                                                        .and(bookmark.postId.eq(trade.tradeId))
                                                        ), "isBookmarked"),
                                        trade.viewCnt,
                                        ExpressionUtils.as(
                                                JPAExpressions
                                                        .select(likes.count())
                                                        .from(likes)
                                                        .where(likes.postId.eq(trade.tradeId)),
                                                "likeCnt"),
                                        ExpressionUtils.as(
                                                JPAExpressions
                                                        .select(bookmark.count())
                                                        .from(bookmark)
                                                        .where(bookmark.postId.eq(trade.tradeId)),
                                                "bookmarkCnt")
                                )
                        )
                .from(trade)
                .fetch();
    }

    @Override
    public Page<TradeInfo.TradeList> tradeList_Paging(Long userId, Pageable pageable) {
        QueryResults<TradeInfo.TradeList> results;
        results = queryFactory
                .select
                        (Projections.constructor
                                (TradeInfo.TradeList.class,
                                        trade.tradeId,
                                        ExpressionUtils.as(
                                                JPAExpressions
                                                        .select(image.filePath)
                                                        .from(image)
                                                        .where(
                                                                image.postId.eq(trade.tradeId)
                                                                        .and(image.postType.eq(PostType.TRADE))
                                                                        .and(image.repImgNY.eq(true))
                                                        )
                                                ,"image"),
                                        trade.title,
                                        trade.price,
                                        trade.cityName,
                                        trade.cityCountryName,
                                        trade.townShipName,

                                        ExpressionUtils.as(
                                                JPAExpressions
                                                        .select(likes.count())
                                                        .from(likes)
                                                        .where(
                                                                likes.user.id.eq(userId)
                                                                        .and(likes.postId.eq(trade.tradeId))
                                                        ), "isLiked"),
                                        trade.viewCnt,
                                        trade.status
                                )
                        )
                .from(trade)
                .orderBy(trade.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<TradeInfo.TradeList> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public TradeInfo.TradeDetail tradeDetail(Long userId, Long tradeId) {
        return queryFactory
                .select
                        (Projections.constructor
                                (TradeInfo.TradeDetail.class,
                                        trade.tradeId,
                                        trade.user.id,
                                        trade.user.nickname,
                                        trade.title,
                                        trade.content,
                                        trade.price,
                                        trade.cityName,
                                        trade.cityCountryName,
                                        trade.townShipName,
                                        trade.detailAdName,
                                        trade.fullAdName,
                                        trade.postType,
                                        ExpressionUtils.as(
                                                JPAExpressions
                                                        .select(likes.count())
                                                        .from(likes)
                                                        .where(
                                                                likes.user.id.eq(userId)
                                                                        .and(likes.postId.eq(trade.tradeId))
                                                        ), "isLiked"),
                                        ExpressionUtils.as(
                                                JPAExpressions
                                                        .select(bookmark.count())
                                                        .from(bookmark)
                                                        .where(
                                                                bookmark.user.id.eq(userId)
                                                                        .and(bookmark.postId.eq(trade.tradeId))
                                                        ), "isBookmarked"),
                                        trade.viewCnt,
                                        ExpressionUtils.as(
                                                JPAExpressions
                                                        .select(likes.count())
                                                        .from(likes)
                                                        .where(likes.postId.eq(trade.tradeId)),
                                                "likeCnt"),
                                        ExpressionUtils.as(
                                                JPAExpressions
                                                        .select(bookmark.count())
                                                        .from(bookmark)
                                                        .where(bookmark.postId.eq(trade.tradeId)),
                                                "bookmarkCnt"),
                                        trade.tradeCategoryDetail.tradeCategoryDetailName,
                                        trade.status,
                                        trade.createdAt
                                )
                        )
                .from(trade)
                .where(trade.tradeId.eq(tradeId))
                .fetchOne();
    }

    @Override
    public List<TradeInfo.TradeList> fetchTrendingTrade(Long userId, LocalDateTime startOfWeek, LocalDateTime endOfWeek) {
         return queryFactory
                 .select
                         (Projections.constructor
                                 (TradeInfo.TradeList.class,
                                         trade.tradeId,
                                         ExpressionUtils.as(
                                                 JPAExpressions
                                                         .select(image.filePath)
                                                         .from(image)
                                                         .where(
                                                                 image.postId.eq(trade.tradeId)
                                                                         .and(image.postType.eq(PostType.TRADE))
                                                                         .and(image.repImgNY.eq(true))
                                                         )
                                                 ,"image"),
                                         trade.title,
                                         trade.price,
                                         trade.cityName,
                                         trade.cityCountryName,
                                         ExpressionUtils.as(
                                                 JPAExpressions
                                                         .select(likes.count())
                                                         .from(likes)
                                                         .where(
                                                                 likes.user.id.eq(userId)
                                                                         .and(likes.postId.eq(trade.tradeId))
                                                         ), "isLiked"),
                                         trade.viewCnt,
                                         trade.status
                                 )
                         )
                 .from(trade)
                 .leftJoin(likes).on(trade.tradeId.eq(likes.postId).and(likes.postType.eq(PostType.TRADE)))
                 .where(trade.createdAt.between(startOfWeek,endOfWeek))
                 .orderBy(likes.count().desc(),trade.createdAt.desc())
                 .groupBy (trade.tradeId)
                 .limit(3)
                 .fetch();
    }
}
