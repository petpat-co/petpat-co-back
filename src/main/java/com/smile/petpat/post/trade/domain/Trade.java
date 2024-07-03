package com.smile.petpat.post.trade.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.smile.petpat.config.comm.Timestamped;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.category.domain.TradeCategoryDetail;
import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.common.status.PostStatus;
import com.smile.petpat.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "TB_TRADE")
public class Trade extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRADE_ID")
    private Long tradeId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "USER_ID",name = "USER_ID")
    private User user;

    @Column(name = "TITLE", nullable = false, length = 20)
    private String title;

    @Column(name = "CONTENT", nullable = false, length = 2000)
    private String content;

    @Column(name = "PRICE", nullable = false, length = 8)
    private Long price;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    @JsonBackReference //양방향 관계의 엔티티의 직렬화 방향 설정 -> 순환참조 방지
    private Address address;

    @Column(name = "DETAIL_AD_NAME")
    private String detailAdName;

    @Column(name = "POST_TYPE" , nullable = false)
    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @Column(name = "VIEW_CNT", nullable = false)
    private int viewCnt;

    @ManyToOne
    @JoinColumn(referencedColumnName = "",name = "TRADE_CATEGORY_DETAIL", nullable = false)
    private TradeCategoryDetail tradeCategoryDetail;

    public Trade() {
    }


     // 중고거래 게시물 등록
    @Builder
    public Trade(User user, String title, String content, Long price, String detailAdName, TradeCategoryDetail tradeCategoryDetail,Address address) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.price = price;
        this.address =address;
        this.detailAdName = detailAdName;
        this.postType = PostType.TRADE;
        this.status = PostStatus.TRADE_FINDING;
        this.viewCnt = 0;
        this.tradeCategoryDetail = tradeCategoryDetail;
    }

    @Builder
    public Trade(Long tradeId, User user, String title, String content, Long price, Address address, String detailAdName, PostType postType, PostStatus status, int viewCnt, TradeCategoryDetail tradeCategoryDetail) {
        this.tradeId = tradeId;
        this.user = user;
        this.title = title;
        this.content = content;
        this.price = price;
        this.address = address;
        this.detailAdName = detailAdName;
        this.postType =postType;
        this.status= status;
        this.postType =PostType.TRADE;
        this.status = PostStatus.TRADE_FINDING;
        this.viewCnt = viewCnt;
        this.tradeCategoryDetail = tradeCategoryDetail;
    }

    public void update(Trade trade){
        this.title = trade.getTitle();
        this.content = trade.getContent();
        this.price = trade.getPrice();
        this.address =trade.address;
        this.detailAdName = trade.getDetailAdName();
        this.tradeCategoryDetail = trade.getTradeCategoryDetail();
    }

    public void isFinding() {
        this.status = PostStatus.TRADE_FINDING;
    }

    public void isReserved() {
        this.status = PostStatus.TRADE_RESERVING;
    }

    public void isMatched() {
        this.status = PostStatus.TRADE_COMPLETED;
    }

    public void updateViewCnt(Trade trade) {
        this.viewCnt = trade.getViewCnt() + 1;
    }
}
