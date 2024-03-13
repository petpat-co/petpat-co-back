package com.smile.petpat.post.rehoming.domain;

import com.smile.petpat.common.exception.CustomException;
import com.smile.petpat.common.response.ErrorCode;
import com.smile.petpat.post.category.domain.CategoryGroup;
import com.smile.petpat.post.category.domain.PetCategory;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.common.status.PostStatus;
import com.smile.petpat.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Builder
@ToString
public class RehomingCommand {

    private User user;
    @NotNull(message = "이미지는 필수값입니다.")
    private List<MultipartFile> rehomingImg;
    @NotBlank(message = "제목은 필수값입니다.") private String title;
    @NotBlank(message = "설명은 필수값입니다.") private String content;
    @NotBlank(message = "이름은 필수값입니다.") private String petName;
    private String petAge;
    @NotNull(message = "카테고리는 필수값입니다.") private Long category;
    @NotNull(message = "종은 필수값입니다.") private Long type;
    @NotNull(message = "성별은 필수값입니다.") private PetGender gender;
    private String cityName;
    private String cityCountryName;
    private String townShipName;
    private String detailAdName;
    private String fullAdName;

    public RehomingCommand toCommand() {
        if (rehomingImg.size() > 5) {
            throw new CustomException(ErrorCode.EXCEEDED_MAX_IMAGE_COUNT);
        }
        else if (rehomingImg.get(0).isEmpty()) {
            throw new CustomException(ErrorCode.BELOW_MIN_IMAGE_COUNT);
        }
        return RehomingCommand.builder()
                .rehomingImg(rehomingImg)
                .title(title)
                .content(content)
                .petName(petName)
                .petAge(petAge)
                .category(category)
                .type(type)
                .gender(gender)
                .cityName(cityName)
                .cityCountryName(cityCountryName)
                .townShipName(townShipName)
                .detailAdName(detailAdName)
                .fullAdName(fullAdName)
                .build();
    }

    public Rehoming toRegisterEntity(User user, CategoryGroup category, PetCategory type) {
        return Rehoming.builder()
                .user(user)
                .title(title)
                .content(content)
                .petName(petName)
                .petAge(getPetAge())
                .category(category)
                .type(type)
                .gender(gender)
                .cityName(cityName)
                .cityCountryName(cityCountryName)
                .townShipName(townShipName)
                .detailAdName(detailAdName)
                .fullAdName(fullAdName)
                .status(PostStatus.REHOMING_FINDING)
                .postType(PostType.REHOMING)
                .build();
    }

    public Rehoming toUpdateEntity(User user, Long rehomingId, CategoryGroup category, PetCategory type, PostStatus status) {
        return Rehoming.builder()
                .rehomingId(rehomingId)
                .user(user)
                .title(title)
                .content(content)
                .petName(petName)
                .petAge(getPetAge())
                .category(category)
                .type(type)
                .gender(gender)
                .cityName(cityName)
                .cityCountryName(cityCountryName)
                .townShipName(townShipName)
                .detailAdName(detailAdName)
                .fullAdName(fullAdName)
                .status(status)
                .build();
    }

    public LocalDate getPetAge() {

        if (this.petAge == null || petAge.equals("")) {
            return null;
        }
        return LocalDate.parse(petAge, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public enum PetGender {
        BOY, GIRL
    }
}
