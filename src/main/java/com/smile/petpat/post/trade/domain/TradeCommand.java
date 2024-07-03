package com.smile.petpat.post.trade.domain;

import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.category.domain.TradeCategoryDetail;
import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.common.status.PostStatus;
import com.smile.petpat.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@ToString
public class TradeCommand {
    private User user;
    private String title;
    private String content;
    private Long price;
    private String province;
    private String city;
    private String district;
    private String detailAdName;
    private String town;
    private PostType postType;
    private PostStatus postStatus;
    private Long tradeCategoryDetailId;
    private List<MultipartFile> images;
    private List<Long> deletedImageId;

    public TradeCommand(){

    }


    @Builder
    public TradeCommand(User user, String title, String content, Long price, String province, String city, String district, String detailAdName, String town, Long tradeCategoryDetailId, List<MultipartFile> images,List<Long> deletedImageId) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.price = price;
        this.province = province;
        this.city = city;
        this.district = district;
        this.detailAdName = detailAdName;
        this.town = town;
        this.tradeCategoryDetailId = tradeCategoryDetailId;
        this.images = images;
        this.deletedImageId = deletedImageId;
    }

    public Trade toRegisterEntity(User user, TradeCategoryDetail tradeCategoryDetail, Address address){
        return Trade.builder()
                .user(user)
                .title(title)
                .content(content)
                .price(price)
                .address(address)
                .detailAdName(detailAdName)
                .tradeCategoryDetail(tradeCategoryDetail)
                .build();
    }

    public Trade toUpdateEntity(User user,TradeCategoryDetail tradeCategoryDetail,Address address){
        return Trade.builder()
                .user(user)
                .title(title)
                .content(content)
                .price(price)
                .address(address)
                .detailAdName(detailAdName)
//                .postType(PostType.TRADE)
//                .status(PostStatus.TRADE_FINDING)
                .tradeCategoryDetail(tradeCategoryDetail)
                .build();
    }
}
