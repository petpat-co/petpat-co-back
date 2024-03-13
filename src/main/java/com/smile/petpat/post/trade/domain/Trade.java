package com.smile.petpat.post.trade.domain;

import com.smile.petpat.config.comm.Timestamped;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.category.domain.TradeCategoryDetail;
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

    @Column(name = "CITY_NAME", nullable = false)
    private String cityName;

    @Column(name = "CITY_COUNTRY_NAME", nullable = false)
    private String cityCountryName;

    @Column(name = "TOWNSHIP_NAME", nullable = false)
    private String townShipName;

    @Column(name = "DETAIL_AD_NAME")
    private String detailAdName;

    @Column(name = "FULL_AD_NAME", nullable = false)
    private String fullAdName;


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
    public Trade(User user, String title, String content, Long price, String cityName, String cityCountryName, String townShipName, String detailAdName, String fullAdName, TradeCategoryDetail tradeCategoryDetail) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.price = price;
        this.cityName = cityName;
        this.cityCountryName = cityCountryName;
        this.townShipName = townShipName;
        this.detailAdName = detailAdName;
        this.fullAdName = fullAdName;
        this.postType = PostType.TRADE;
        this.status = PostStatus.TRADE_FINDING;
        this.viewCnt = 0;
        this.tradeCategoryDetail = tradeCategoryDetail;
    }

    @Builder
    public Trade(Long tradeId, User user, String title, String content, Long price, String cityName, String cityCountryName, String townShipName, String detailAdName, String fullAdName, PostType postType, PostStatus status, int viewCnt, TradeCategoryDetail tradeCategoryDetail) {
        this.tradeId = tradeId;
        this.user = user;
        this.title = title;
        this.content = content;
        this.price = price;
        this.cityName = cityName;
        this.cityCountryName = cityCountryName;
        this.townShipName = townShipName;
        this.detailAdName = detailAdName;
        this.fullAdName = fullAdName;
        this.postType =PostType.TRADE;
        this.status = PostStatus.TRADE_FINDING;
        this.viewCnt = viewCnt;
        this.tradeCategoryDetail = tradeCategoryDetail;
    }

    public void update(Trade trade){
        this.title = trade.getTitle();
        this.content = trade.getContent();
        this.price = trade.getPrice();
        this.cityName = trade.getCityName();
        this.cityCountryName = trade.getCityCountryName();
        this.townShipName = trade.getTownShipName();
        this.detailAdName = trade.getDetailAdName();
        this.fullAdName = trade.getFullAdName();
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
