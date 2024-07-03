package com.smile.petpat.user.repository.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smile.petpat.image.domain.ImagePriority;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.user.dto.ProfileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.smile.petpat.image.domain.QImage.image;
import static com.smile.petpat.post.common.bookmarks.domain.QBookmark.bookmark;
import static com.smile.petpat.post.common.likes.domain.QLikes.likes;
import static com.smile.petpat.post.qna.domain.QComment.comment;
import static com.smile.petpat.post.qna.domain.QQna.qna;
import static com.smile.petpat.post.rehoming.domain.QRehoming.rehoming;
import static com.smile.petpat.post.trade.domain.QTrade.trade;
import static com.smile.petpat.user.domain.QUser.user;

public class UserRepositoryImpl implements ProfileRepositoryQuerydsl {
    private final JPAQueryFactory queryFactory;
    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

   //내 분양글 조회
    @Override
    public Page<ProfileDto.RehomingResponse> getMyRehoming(Long userId, Pageable pageable) {
        QueryResults<ProfileDto.RehomingResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                ProfileDto.RehomingResponse.class,
                                rehoming.rehomingId,
                                Expressions.as(
                                        select(image.filePath)
                                                .from(image)
                                                .where(image.postId.eq(rehoming.rehomingId),
                                                        image.postType.eq(PostType.REHOMING),
                                                        image.priority.eq(ImagePriority.PRIORITY_1))
                                        , "rehomingImg"),
                                rehoming.title,
                                rehoming.createdAt,
                                rehoming.viewCnt
                        )
                )
                .from(rehoming)
                .leftJoin(rehoming.user,user)
                .where(rehoming.user.id.eq(userId))
                .orderBy(rehoming.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(results.getResults(),pageable,results.getTotal());
    }

    //내가 남긴 댓글 조회
    @Override
    public Page<ProfileDto.CommentResponse> getMyComment(Long userId, Pageable pageable){
        QueryResults<ProfileDto.CommentResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                ProfileDto.CommentResponse.class,
                                qna.qnaId,
                                comment.commentId,
                                qna.title,
                                comment.content,
                                comment.createdAt
                                )
                )
                .from(comment)
                .leftJoin(comment.user, user)
                .leftJoin(comment.qna, qna)
                .where(comment.user.id.eq(userId))
                .orderBy(comment.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(results.getResults(),pageable,results.getTotal());
    }

    //내 판매글 조회
    @Override
    public Page<ProfileDto.TradeResponse> getMyTrade(Long userId, Pageable pageable) {
        QueryResults<ProfileDto.TradeResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                ProfileDto.TradeResponse.class,
                                trade.tradeId,
                                Expressions.as(
                                        select(image.filePath)
                                                .from(image)
                                                .where(image.postId.eq(trade.tradeId),
                                                        image.postType.eq(PostType.TRADE),
                                                        image.priority.eq(ImagePriority.PRIORITY_1))
                                        , "tradeImgUrl"),
                                trade.title,
                                trade.createdAt,
                                trade.viewCnt
                                )
                )
                .from(trade)
                .leftJoin(trade.user,user)
                .where(trade.user.id.eq(userId))
                .orderBy(trade.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(results.getResults(),pageable,results.getTotal());
    }

    //내가 쓴 질문게시글 조회
    @Override
    public Page<ProfileDto.QnaResponse> getMyQna(Long userId, Pageable pageable) {
        QueryResults<ProfileDto.QnaResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                ProfileDto.QnaResponse.class,
                                qna.qnaId,
                                qna.title,
                                qna.createdAt,
                                qna.viewCnt,
                                qna.comments.size()
                        )
                )
                .from(qna)
                .leftJoin(qna.user,user)
                .where(qna.user.id.eq(userId))
                .orderBy(qna.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(results.getResults(),pageable,results.getTotal());

    }

    @Override
    public Page<ProfileDto.RehomingResponse> getRehomingByBookmark(Long userId, Pageable pageable) {
        QueryResults<ProfileDto.RehomingResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                ProfileDto.RehomingResponse.class,
                                rehoming.rehomingId,
                                Expressions.as(
                                        select(image.filePath)
                                                .from(image)
                                                .where(image.postId.eq(rehoming.rehomingId),
                                                        image.postType.eq(PostType.REHOMING),
                                                        image.priority.eq(ImagePriority.PRIORITY_1))
                                        , "rehomingImg"),
                                rehoming.title,
                                rehoming.createdAt,
                                rehoming.viewCnt
                        )
                )
                .from(rehoming)
                .leftJoin(rehoming.user,user)
                .leftJoin(bookmark)
                    .on(bookmark.postId.eq(rehoming.rehomingId))
                .where(bookmark.user.id.eq(userId)
                        ,bookmark.postType.eq(PostType.REHOMING))
                .orderBy(rehoming.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(results.getResults(),pageable,results.getTotal());
    }

    @Override
    public Page<ProfileDto.TradeResponse> getTradeByBookmark(Long userId, Pageable pageable) {
        QueryResults<ProfileDto.TradeResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                ProfileDto.TradeResponse.class,
                                trade.tradeId,
                                Expressions.as(
                                        select(image.filePath)
                                                .from(image)
                                                .where(image.postId.eq(trade.tradeId),
                                                        image.postType.eq(PostType.TRADE),
                                                        image.priority.eq(ImagePriority.PRIORITY_1))
                                        , "tradeImgUrl"),
                                trade.title,
                                trade.createdAt,
                                trade.viewCnt
                        )
                )
                .from(trade)
                .leftJoin(trade.user,user)
                .leftJoin(bookmark)
                .on(bookmark.postId.eq(trade.tradeId))
                .where(bookmark.user.id.eq(userId)
                        ,bookmark.postType.eq(PostType.TRADE))
                .orderBy(trade.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(results.getResults(),pageable,results.getTotal());
    }

    @Override
    public Page<ProfileDto.QnaResponse> getQnAByBookmark(Long userId, Pageable pageable) {
        QueryResults<ProfileDto.QnaResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                ProfileDto.QnaResponse.class,
                                qna.qnaId,
                                qna.title,
                                qna.createdAt,
                                qna.viewCnt,
                                qna.comments.size()
                        )
                )
                .from(qna)
                .leftJoin(qna.user,user)
                .leftJoin(bookmark)
                .on(bookmark.postId.eq(qna.qnaId))
                .where(bookmark.user.id.eq(userId)
                        ,bookmark.postType.eq(PostType.QNA))
                .orderBy(qna.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(results.getResults(),pageable,results.getTotal());
    }

    @Override
    public Page<ProfileDto.RehomingResponse> getRehomingByLike(Long userId, Pageable pageable) {
        QueryResults<ProfileDto.RehomingResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                ProfileDto.RehomingResponse.class,
                                rehoming.rehomingId,
                                Expressions.as(
                                        select(image.filePath)
                                                .from(image)
                                                .where(image.postId.eq(rehoming.rehomingId),
                                                        image.postType.eq(PostType.REHOMING),
                                                        image.priority.eq(ImagePriority.PRIORITY_1))
                                        , "rehomingImg"),
                                rehoming.title,
                                rehoming.createdAt,
                                rehoming.viewCnt
                        )
                )
                .from(rehoming)
                .leftJoin(rehoming.user,user)
                .leftJoin(likes)
                .on(likes.postId.eq(rehoming.rehomingId))
                .where(likes.user.id.eq(userId)
                        ,likes.postType.eq(PostType.REHOMING))
                .orderBy(rehoming.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(results.getResults(),pageable,results.getTotal());
    }

    @Override
    public Page<ProfileDto.TradeResponse> getTradeByLike(Long userId, Pageable pageable) {
        QueryResults<ProfileDto.TradeResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                ProfileDto.TradeResponse.class,
                                trade.tradeId,
                                Expressions.as(
                                        select(image.filePath)
                                                .from(image)
                                                .where(image.postId.eq(trade.tradeId),
                                                        image.postType.eq(PostType.TRADE),
                                                        image.priority.eq(ImagePriority.PRIORITY_1))
                                        , "tradeImgUrl"),
                                trade.title,
                                trade.createdAt,
                                trade.viewCnt
                        )
                )
                .from(trade)
                .leftJoin(trade.user,user)
                .leftJoin(likes)
                .on(likes.postId.eq(trade.tradeId))
                .where(likes.user.id.eq(userId)
                        ,likes.postType.eq(PostType.TRADE))
                .orderBy(trade.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(results.getResults(),pageable,results.getTotal());
    }

    @Override
    public Page<ProfileDto.QnaResponse> getQnAByLike(Long userId, Pageable pageable) {
        QueryResults<ProfileDto.QnaResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                ProfileDto.QnaResponse.class,
                                qna.qnaId,
                                qna.title,
                                qna.createdAt,
                                qna.viewCnt,
                                qna.comments.size()
                        )
                )
                .from(qna)
                .leftJoin(qna.user,user)
                .leftJoin(likes)
                .on(likes.postId.eq(qna.qnaId))
                .where(likes.user.id.eq(userId)
                        ,likes.postType.eq(PostType.QNA))
                .orderBy(qna.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(results.getResults(),pageable,results.getTotal());
    }

    @Override
    public ProfileDto.RecentDealResponse getMyRecentDeal(Long userId) {
        return null;
    }


}
