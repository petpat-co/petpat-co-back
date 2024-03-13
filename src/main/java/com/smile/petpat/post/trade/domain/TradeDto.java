package com.smile.petpat.post.trade.domain;

import com.smile.petpat.common.exception.CustomException;
import com.smile.petpat.post.category.domain.TradeCategoryDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.smile.petpat.common.response.ErrorCode.BELOW_MIN_IMAGE_COUNT;
import static com.smile.petpat.common.response.ErrorCode.EXCEEDED_MAX_IMAGE_COUNT;


@Getter
public class TradeDto {
    private static final int MIN_IMAGE_COUNT_IDX =0;
    private static final int MAX_IMAGE_COUNT_IDX =5;

    @Getter
    @Setter
    @ToString
    public static class CommonTrade{

        @NotBlank(message = "제목은 필수값입니다.")
        private String title;
        @NotBlank(message = "내용은 필수값입니다.")
        private String content;
        @NotNull(message = "가격은 필수값입니다.")
        private Long price;
        @NotNull(message = "시도군명은 필수값입니다.")
        private String cityName;
        @NotNull(message = "시도군명은 필수값입니다.")
        private String cityCountryName;
        @NotNull(message = "시도군명은 필수값입니다.")
        private String townShipName;
        @NotNull(message = "시도군명은 필수값입니다.")
        private String detailAdName;
        @NotNull(message = "시도군명은 필수값입니다.")
        private String fullAdName;
       @NotNull(message = "카테고리는 필수값입니다.")
        private Long tradeCategoryDetailId;
       @NotNull(message = "이미지는 1장이상 첨부해야합니다.")
        private List<MultipartFile> images;

        public CommonTrade(){}

        public CommonTrade(String title, String content, Long price, String cityName, String cityCountryName, String townShipName, String detailAdName, String fullAdName, Long tradeCategoryDetailId, List<MultipartFile> images) {
            if(!images.get(MIN_IMAGE_COUNT_IDX).getName().isEmpty()) throw new CustomException(BELOW_MIN_IMAGE_COUNT);
            if(images.get(MAX_IMAGE_COUNT_IDX).isEmpty()) throw new CustomException(EXCEEDED_MAX_IMAGE_COUNT);
            this.title = title;
            this.content = content;
            this.price = price;
            this.cityName = cityName;
            this.cityCountryName = cityCountryName;
            this.townShipName = townShipName;
            this.detailAdName = detailAdName;
            this.fullAdName = fullAdName;
            this.tradeCategoryDetailId = tradeCategoryDetailId;
            this.images = images;
        }

        public TradeCommand toCommand() {
         return TradeCommand.builder()
                 .title(title)
                 .content(content)
                 .price(price)
                 .cityName(cityName)
                 .cityCountryName(cityCountryName)
                 .townShipName(townShipName)
                 .detailAdName(detailAdName)
                 .fullAdName(fullAdName)
                 .tradeCategoryDetailId(tradeCategoryDetailId)
                 .images(images)
                 .build();


        }
    }

}
