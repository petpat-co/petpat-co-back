package com.smile.petpat.post.common.Address.repository.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smile.petpat.image.domain.ImagePriority;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.common.Address.Dto.AddressReqDto;
import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.rehoming.domain.RehomingInfo;
import com.smile.petpat.post.trade.domain.Trade;
import com.smile.petpat.post.trade.domain.TradeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.smile.petpat.image.domain.QImage.image;
import static com.smile.petpat.post.common.bookmarks.domain.QBookmark.bookmark;
import static com.smile.petpat.post.common.likes.domain.QLikes.likes;
import static com.smile.petpat.post.rehoming.domain.QRehoming.rehoming;
import static com.smile.petpat.post.trade.domain.QTrade.trade;
import static com.smile.petpat.user.domain.QUser.user;


public class AddressRepositoryImpl implements AddressRepositoryQuerydsl {
    private final JPAQueryFactory queryFactory;

    public AddressRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<RehomingInfo> getRehomingsByAddress(Address address, Pageable pageable, String userEmail) {
        QueryResults<RehomingInfo> results =queryFactory
                .select(
                        Projections.constructor(
                                RehomingInfo.class,
                                rehoming.rehomingId,
                                rehoming.postType,
                                Expressions.as(
                                        select(image.filePath)
                                                .from(image)
                                                .where(image.postId.eq(rehoming.rehomingId),
                                                        image.postType.eq(PostType.REHOMING),
                                                        image.priority.eq(ImagePriority.PRIORITY_1))
                                        , "rehomingImg"),
                                rehoming.title,
                                rehoming.address,
                                rehoming.status,
                                ExpressionUtils.as(
                                        select(likes.count())
                                                .from(likes)
                                                .where(
                                                        likes.user.userEmail.eq(userEmail)
                                                                .and(likes.postId.eq(rehoming.rehomingId))
                                                ), "isLiked"),
                                ExpressionUtils.as(
                                        select(bookmark.count())
                                                .from(bookmark)
                                                .where(
                                                        bookmark.user.userEmail.eq(userEmail)
                                                                .and(bookmark.postId.eq(rehoming.rehomingId))
                                                ), "isBookmarked"),
                                rehoming.viewCnt,
                                ExpressionUtils.as(
                                        select(likes.count())
                                                .from(likes)
                                                .where(likes.postId.eq(rehoming.rehomingId)),
                                        "likeCnt"),
                                ExpressionUtils.as(
                                        select(bookmark.count())
                                                .from(bookmark)
                                                .where(bookmark.postId.eq(rehoming.rehomingId)),
                                        "bookmarkCnt"),
                                rehoming.createdAt,
                                rehoming.updatedAt
                        )
                )
                .from(rehoming)
                .where(rehoming.address.eq(address))
                .leftJoin(rehoming.user,user)
                .orderBy(rehoming.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<RehomingInfo> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content,pageable,total);
    }

    @Override
    public Page<TradeInfo.TradeList> getTradesByAddress(Address address, Pageable pageable, String userEmail) {
        QueryResults<TradeInfo.TradeList> results =queryFactory
                .select(
                        Projections.constructor(
                                TradeInfo.TradeList.class,
                                trade.tradeId,
                                Expressions.as(
                                        select(image.filePath)
                                                .from(image)
                                                .where(image.postId.eq(trade.tradeId),
                                                        image.postType.eq(PostType.TRADE),
                                                        image.priority.eq(ImagePriority.PRIORITY_1))
                                        , "imagePath"),
                                trade.title,
                                trade.price,
                                trade.address,
                                ExpressionUtils.as(
                                        select(likes.count())
                                                .from(likes)
                                                .where(
                                                        likes.user.userEmail.eq(userEmail)
                                                                .and(likes.postId.eq(trade.tradeId))
                                                ), "isLiked"),
                                ExpressionUtils.as(
                                        select(bookmark.count())
                                                .from(bookmark)
                                                .where(
                                                        bookmark.user.userEmail.eq(userEmail)
                                                                .and(bookmark.postId.eq(trade.tradeId))
                                                ), "isBookmarked"),
                                trade.viewCnt,
                                ExpressionUtils.as(
                                        select(likes.count())
                                                .from(likes)
                                                .where(likes.postId.eq(trade.tradeId)),
                                        "likeCnt"),
                                ExpressionUtils.as(
                                        select(bookmark.count())
                                                .from(bookmark)
                                                .where(bookmark.postId.eq(trade.tradeId)),
                                        "bookmarkCnt"),
                                trade.createdAt,
                                trade.updatedAt,
                                trade.status
                                )
                )
                .from(trade)
                .where(trade.address.eq(address))
                .leftJoin(trade.user,user)
                .orderBy(trade.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<TradeInfo.TradeList> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content,pageable,total);
    }
}
