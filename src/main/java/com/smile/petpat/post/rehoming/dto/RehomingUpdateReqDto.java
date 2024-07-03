package com.smile.petpat.post.rehoming.dto;

import com.smile.petpat.common.exception.CustomException;
import com.smile.petpat.common.response.ErrorCode;
import com.smile.petpat.post.category.domain.CategoryGroup;
import com.smile.petpat.post.category.domain.PetCategory;
import com.smile.petpat.post.common.Address.Dto.AddressReqDto;
import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.common.status.PostStatus;
import com.smile.petpat.post.rehoming.domain.Rehoming;
import com.smile.petpat.post.rehoming.domain.RehomingCommand;
import com.smile.petpat.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Builder
@ToString
public class RehomingUpdateReqDto {
    private User user;
    private List<MultipartFile> newImages;
    private List<Long> deletedImageIds;
    @NotBlank(message = "제목은 필수값입니다.") private String title;
    @NotBlank(message = "설명은 필수값입니다.") private String content;
    @NotBlank(message = "이름은 필수값입니다.") private String petName;
    private String petAge;
    @NotNull(message = "카테고리는 필수값입니다.") private Long category;
    @NotNull(message = "종은 필수값입니다.") private Long type;
    @NotNull(message = "성별은 필수값입니다.") private RehomingCommand.PetGender gender;

    private AddressReqDto addressReqDto;
    private String province;
    private String city;
    private String district;
    private String town;
    private String detailAdName;
    private boolean dhppl;
    private boolean covidEnteritis;
    private boolean kennelCough;
    private boolean influenza;
    private boolean rabies;
    private boolean comprehensiveVaccine;
    private boolean fpv;
    private boolean felv;
    private boolean isNeutralized;



    public Rehoming toUpdateEntity(User user, Long rehomingId, CategoryGroup category, PetCategory type, PostStatus status,Address address) {
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
                .address(address)
                .detailAdName(detailAdName)
                .status(status)
                .dhppl(dhppl)
                .covidEnteritis(covidEnteritis)
                .kennelCough(kennelCough)
                .influenza(influenza)
                .rabies(rabies)
                .comprehensiveVaccine(comprehensiveVaccine)
                .fpv(fpv)
                .felv(felv)
                .isNeutralized(isNeutralized)
                .build();
    }

    public LocalDate getPetAge() {

        if (this.petAge == null || petAge.equals("")) {
            return null;
        }
        return LocalDate.parse(petAge, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }


}
