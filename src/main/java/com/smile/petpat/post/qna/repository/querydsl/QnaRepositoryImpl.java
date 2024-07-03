package com.smile.petpat.post.qna.repository.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.qna.domain.QnaInfo;
import com.smile.petpat.post.trade.domain.TradeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.smile.petpat.image.domain.QImage.image;
import static com.smile.petpat.post.common.bookmarks.domain.QBookmark.bookmark;
import static com.smile.petpat.post.common.likes.domain.QLikes.likes;
import static com.smile.petpat.post.qna.domain.QQna.qna;
import static com.smile.petpat.post.trade.domain.QTrade.trade;

public class QnaRepositoryImpl implements QnaRepositoryQueryDsl {
    private final JPAQueryFactory queryFactory;

    public QnaRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

//    @Override
//    public Page<QnaInfo.QnaList> qnaList(Long userId, Pageable pageable) {
//        QueryResults<QnaInfo.QnaList> results;
//        results = queryFactory
//                .select
//                        (Projections.constructor
//                                (QnaInfo.QnaList.class,
//                                        qna.qnaId,
//                                        qna.user.nickname,
//                                        qna.title,
//                                        ExpressionUtils.as(
//                                                JPAExpressions
//                                                        .select(image.filePath)
//                                                        .from(image)
//                                                        .where(
//                                                                image.postId.eq(qna.qnaId)
//                                                                        .and(image.postType.eq(PostType.QNA))
//                                                        )
//                                                        .orderBy(image.imageId.asc()).limit(1)
//                                                ,"image"),
//                                        qna.postType,
//                                        qna.viewCnt,
//                                        qna.createdAt
//                                )
//                        )
//                .from(qna)
//                .orderBy(qna.createdAt.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetchResults();
//
//        List<QnaInfo.QnaList> content = results.getResults();
//        long total = results.getTotal();
//
//        return new PageImpl<>(content, pageable, total);
//    }

    @Override
    public Page<QnaInfo.QnaList> qnaList(Pageable pageable) {
        QueryResults<QnaInfo.QnaList> results;
        results = queryFactory
                .select
                        (Projections.constructor
                                (QnaInfo.QnaList.class,
                                        qna.qnaId,
                                        qna.user.nickname,
                                        qna.title,
                                        ExpressionUtils.as(
                                                JPAExpressions
                                                        .select(image.filePath)
                                                        .from(image)
                                                        .where(
                                                                image.postId.eq(qna.qnaId)
                                                                        .and(image.postType.eq(PostType.QNA))
                                                        )
                                                        .orderBy(image.imageId.asc()).limit(1)
                                                ,"image"),
                                        qna.postType,
                                        qna.viewCnt,
                                        qna.createdAt
                                )
                        )
                .from(qna)
                .orderBy(qna.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<QnaInfo.QnaList> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public QnaInfo.QnaDetail qnaDetail(Long postId) {
        return queryFactory
                .select
                        (Projections.constructor
                                (QnaInfo.QnaDetail.class,
                                        qna.qnaId,
                                        qna.user.id,
                                        qna.user.nickname,
                                        qna.title,
                                        qna.content,
                                        qna.postType,
                                        qna.viewCnt,
                                        qna.createdAt
                                )
                        )
                .from(qna)
                .where(qna.qnaId.eq(postId))
                .fetchOne();


    }

}
