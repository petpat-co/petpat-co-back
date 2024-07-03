package com.smile.petpat.post.rehoming.repository.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smile.petpat.image.domain.ImagePriority;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.rehoming.domain.RehomingInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.smile.petpat.image.domain.QImage.image;
import static com.smile.petpat.post.common.bookmarks.domain.QBookmark.bookmark;
import static com.smile.petpat.post.common.likes.domain.QLikes.likes;
import static com.smile.petpat.post.rehoming.domain.QRehoming.rehoming;
import static com.smile.petpat.user.domain.QUser.user;

public class RehomingRepositoryImpl implements RehomingRepositoryQuerydsl {
    private final JPAQueryFactory queryFactory;

    public RehomingRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 분양 글 목록 조회 (회원)
    @Override
    public Page<RehomingInfo> rehomingListForMember(String userEmail, Pageable pageable) {
        QueryResults<RehomingInfo> results = queryFactory
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
                .leftJoin(rehoming.user, user)
                .orderBy(rehoming.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<RehomingInfo> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    // 분양 글 목록 조회 (비회원)
    @Override
    public Page<RehomingInfo> rehomingList(Pageable pageable) {
        QueryResults<RehomingInfo> results;
        results = queryFactory
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
                .leftJoin(rehoming.user, user)
                .orderBy(rehoming.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<RehomingInfo> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    // 분양 글 카테고리별 목록 조회 (회원)
    @Override
    public Page<RehomingInfo> rehomingCategoryListForMember(String userEmail, Long categoryId, Long typeId, Pageable pageable) {

        QueryResults<RehomingInfo> results = queryFactory
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
                .leftJoin(rehoming.user, user)
                .where(rehoming.category.categoryGroupId.eq(categoryId),
                        rehoming.type.petCategoryId.eq(typeId))
                .orderBy(rehoming.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<RehomingInfo> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    // 분양 글 카테고리별 목록 조회 (비회원)
    @Override
    public Page<RehomingInfo> rehomingCategoryList(Long categoryId, Long typeId, Pageable pageable) {
        QueryResults<RehomingInfo> results;
        results = queryFactory
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
                .leftJoin(rehoming.user, user)
                .where(rehoming.category.categoryGroupId.eq(categoryId),
                        rehoming.type.petCategoryId.eq(typeId))
                .orderBy(rehoming.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<RehomingInfo> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    /* 사용하는 데 없음
    @Override
    public RehomingResDto readRehomingDetailForMember(String userEmail, Long rehomingId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                RehomingResDto.class,
                                rehoming.rehomingId,
                                rehoming.postType,
                                rehoming.user.id,
                                rehoming.user.nickname,
                                rehoming.title,
                                rehoming.content,
                                rehoming.petName,
                                rehoming.petAge,
                                rehoming.category,
                                rehoming.type,
                                rehoming.gender,
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
                                rehoming.status,
                                rehoming.createdAt,
                                rehoming.updatedAt,
                                rehoming.dhppl,
                                rehoming.covidEnteritis,
                                rehoming.kennelCough,
                                rehoming.influenza,
                                rehoming.rabies,
                                rehoming.comprehensiveVaccine,
                                rehoming.fpv,
                                rehoming.felv,
                                rehoming.isNeutralized
                        )
                )
                .from(rehoming)
                .where(rehoming.rehomingId.eq(rehomingId))
                .fetchOne();
    }

     */

    @Override
    public List<RehomingInfo> fetchTrendingRehoming(Long userId, LocalDateTime startOfWeek, LocalDateTime endOfWeek) {
        return queryFactory
                .select(
                        Projections.constructor(
                                RehomingInfo.class,
                                rehoming.rehomingId,
                                rehoming.postType,
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(image.filePath)
                                                .from(image)
                                                .where(
                                                        image.postId.eq(rehoming.rehomingId)
                                                                .and(image.postType.eq(PostType.REHOMING))
                                                                .and(image.priority.eq(ImagePriority.PRIORITY_1))
                                                ), "image"),
                                rehoming.title,
                                rehoming.address,
                                rehoming.status,
                                ExpressionUtils.as(
                                        select(likes.count())
                                                .from(likes)
                                                .where(
                                                        likes.user.id.eq(userId)
                                                                .and(likes.postId.eq(rehoming.rehomingId))
                                                ), "isLiked"),
                                ExpressionUtils.as(
                                        select(bookmark.count())
                                                .from(bookmark)
                                                .where(
                                                        bookmark.user.id.eq(userId)
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
                .leftJoin(likes).on(rehoming.rehomingId.eq(likes.postId).and(likes.postType.eq(PostType.REHOMING)))
                .where(rehoming.createdAt.between(startOfWeek, endOfWeek))
                .orderBy(likes.count().desc(), rehoming.createdAt.desc())
                .groupBy(rehoming.rehomingId)
                .limit(3)
                .fetch();
    }

}
