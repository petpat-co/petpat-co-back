package com.smile.petpat.post.rehoming.repository.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.category.domain.QCategoryGroup;
import com.smile.petpat.post.category.domain.QPetCategory;
import com.smile.petpat.post.rehoming.domain.RehomingInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.querydsl.jpa.JPAExpressions.type;
import static com.smile.petpat.image.domain.QImage.image;
import static com.smile.petpat.post.category.domain.QCategoryGroup.categoryGroup;
import static com.smile.petpat.post.category.domain.QPetCategory.petCategory;
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
    public Page<RehomingInfo> rehomingListForMember(Long userId, Pageable pageable) {
        QueryResults<RehomingInfo> results = queryFactory
                .select(
                        Projections.constructor(
                                RehomingInfo.class,
                                rehoming.rehomingId,
                                Expressions.as(
                                        select(image.filePath)
                                                .from(image)
                                                .where(image.postId.eq(rehoming.rehomingId),
                                                        image.postType.eq(PostType.REHOMING),
                                                        image.repImgNY.eq(true))
                                                , "rehomingImg"),
                                rehoming.user.id,
                                rehoming.user.nickname,
                                rehoming.title,
                                rehoming.petName,
                                rehoming.category.categoryGroupName,
                                rehoming.type.petCategoryName,
                                rehoming.gender,
                                rehoming.status,
                                rehoming.postType,
                                rehoming.createdAt,
                                rehoming.updatedAt,
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
                                        "bookmarkCnt")
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
                                Expressions.as(
                                        select(image.filePath)
                                                .from(image)
                                                .where(image.postId.eq(rehoming.rehomingId),
                                                        image.postType.eq(PostType.REHOMING),
                                                        image.repImgNY.eq(true))
                                        , "rehomingImg"),
                                rehoming.user.id,
                                rehoming.user.nickname,
                                rehoming.title,
                                rehoming.petName,
                                rehoming.category.categoryGroupName,
                                rehoming.type.petCategoryName,
                                rehoming.gender,
                                rehoming.status,
                                rehoming.postType,
                                rehoming.createdAt,
                                rehoming.updatedAt,
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
                                        "bookmarkCnt")
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

    @Override
    public Page<RehomingInfo> rehomingCategoryListForMember(Long userId, Long categoryId, Long typeId, Pageable pageable) {

        QueryResults<RehomingInfo> results = queryFactory
                .select(
                        Projections.constructor(
                                RehomingInfo.class,
                                rehoming.rehomingId,
                                Expressions.as(
                                        select(image.filePath)
                                                .from(image)
                                                .where(image.postId.eq(rehoming.rehomingId),
                                                        image.postType.eq(PostType.REHOMING),
                                                        image.repImgNY.eq(true))
                                        , "rehomingImg"),
                                rehoming.user.id,
                                rehoming.user.nickname,
                                rehoming.title,
                                rehoming.petName,
                                rehoming.category.categoryGroupName,
                                rehoming.type.petCategoryName,

                                rehoming.gender,
                                rehoming.status,
                                rehoming.postType,
                                rehoming.createdAt,
                                rehoming.updatedAt,
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
                                        "bookmarkCnt")
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

    @Override
    public Page<RehomingInfo> rehomingCategoryList(Long categoryId, Long typeId, Pageable pageable) {
        QueryResults<RehomingInfo> results;
        results = queryFactory
                .select(
                        Projections.constructor(
                                RehomingInfo.class,
                                rehoming.rehomingId,
                                Expressions.as(
                                        select(image.filePath)
                                                .from(image)
                                                .where(image.postId.eq(rehoming.rehomingId),
                                                        image.postType.eq(PostType.REHOMING),
                                                        image.repImgNY.eq(true))
                                        , "rehomingImg"),
                                rehoming.user.id,
                                rehoming.user.nickname,
                                rehoming.title,
                                rehoming.petName,
                                rehoming.category.categoryGroupName,
                                rehoming.type.petCategoryName,
                                rehoming.gender,
                                rehoming.status,
                                rehoming.postType,
                                rehoming.createdAt,
                                rehoming.updatedAt,
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
                                        "bookmarkCnt")
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

}
