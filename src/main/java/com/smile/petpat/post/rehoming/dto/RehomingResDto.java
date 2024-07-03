package com.smile.petpat.post.rehoming.dto;

import com.smile.petpat.image.dto.ImageResDto;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.common.CalculateTime;
import com.smile.petpat.post.common.status.PostStatus;
import com.smile.petpat.post.rehoming.domain.Rehoming;
import com.smile.petpat.post.rehoming.domain.RehomingCommand;
import com.smile.petpat.post.common.Address.util.AddressUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class RehomingResDto  {
    private Long postId;
    private PostType postType;
    private Long userId;
    private String nickname;
    private List<ImageResDto> imageList;
    private String title;
    private String content;
    private String petName;
    private LocalDate petAge;
    private String category;
    private String type;
    private RehomingCommand.PetGender gender;
    private String region;
    private PostStatus status;
    private String createdAt;
    private String updatedAt;
    private boolean isLiked;
    private boolean isBookmarked;
    private int viewCnt;
    private int likeCnt;
    private boolean dhppl;
    private boolean covidEnteritis;
    private boolean kennelCough;
    private boolean influenza;
    private boolean rabies;
    private boolean comprehensiveVaccine;
    private boolean fpv;
    private boolean felv;
    private boolean isNeutralized;

    public RehomingResDto(Long postId, PostType postType, Long userId, String nickname,
                          String title, String content, String petName, LocalDate petAge,
                          String category, String type, RehomingCommand.PetGender gender, 
                          String cityName, String cityCountryName, String townShipName, PostStatus status,
                          LocalDateTime createdAt, LocalDateTime updatedAt, Long isLiked, Long isBookmarked, int viewCnt, int likeCnt,
                          boolean dhppl, boolean covidEnteritis, boolean kennelCough, boolean influenza, boolean rabies,
                          boolean comprehensiveVaccine, boolean fpv, boolean felv, boolean isNeutralized) {
        this.postId = postId;
        this.postType = postType;
        this.userId = userId;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.petName = petName;
        this.petAge = petAge;
        this.category = category;
        this.type = type;
        this.gender = gender;
        this.region = cityName + " " + cityCountryName + " " + townShipName;
        this.isLiked = booleanChk(isLiked);
        this.isBookmarked = booleanChk(isBookmarked);
        this.viewCnt = viewCnt;
        this.likeCnt = likeCnt;
        this.status = status;
        this.createdAt = CalculateTime.dateformatForPost(createdAt);
        this.updatedAt = CalculateTime.dateformatForPost(updatedAt);
        this.dhppl = dhppl;
        this.covidEnteritis = covidEnteritis;
        this.kennelCough = kennelCough;
        this.influenza = influenza;
        this.rabies = rabies;
        this.comprehensiveVaccine = comprehensiveVaccine;
        this.fpv = fpv;
        this.felv = felv;
        this.isNeutralized = isNeutralized;
    }
    public static  Boolean booleanChk(Long chkValue) {
        return chkValue != 0;
    }
    
    public RehomingResDto(RehomingResDto rehomingResDto, List<String> imageList) {
        this.postId = rehomingResDto.postId;
        this.postType = rehomingResDto.postType;
        this.userId = rehomingResDto.userId;
        this.nickname = rehomingResDto.nickname;
        this.title = rehomingResDto.title;
        this.content = rehomingResDto.content;
        this.petName = rehomingResDto.petName;
        this.petAge = rehomingResDto.petAge;
        this.category = rehomingResDto.category;
        this.type = rehomingResDto.type;
        this.gender = rehomingResDto.gender;
        this.region = rehomingResDto.getRegion();
        this.isLiked = rehomingResDto.isLiked;
        this.isBookmarked = rehomingResDto.isBookmarked;
        this.viewCnt = rehomingResDto.viewCnt;
        this.likeCnt = rehomingResDto.likeCnt;
        this.status = rehomingResDto.status;
        this.createdAt = rehomingResDto.createdAt;
        this.updatedAt = rehomingResDto.updatedAt;
        this.dhppl = rehomingResDto.isDhppl();
        this.covidEnteritis = rehomingResDto.isCovidEnteritis();
        this.kennelCough = rehomingResDto.isKennelCough();
        this.influenza = rehomingResDto.isInfluenza();
        this.rabies = rehomingResDto.isRabies();
        this.comprehensiveVaccine = rehomingResDto.isComprehensiveVaccine();
        this.fpv = rehomingResDto.isFpv();
        this.felv = rehomingResDto.isFelv();
        this.isNeutralized = rehomingResDto.isNeutralized();

    }

    // 회원 조회 시
    public RehomingResDto(Rehoming rehoming, List<ImageResDto> imageList, boolean isLiked, boolean isBookmarked, int likeCnt) {
        this.postId = rehoming.getRehomingId();
        this.postType = rehoming.getPostType();
        this.userId = rehoming.getUser().getId();
        this.nickname = rehoming.getUser().getNickname();
        this.title = rehoming.getTitle();
        this.content = rehoming.getContent();
        this.petName = rehoming.getPetName();
        this.petAge = rehoming.getPetAge();
        this.category = rehoming.getCategory().getCategoryGroupName();
        this.type = rehoming.getType().getPetCategoryName();
        this.gender = rehoming.getGender();
        this.region = AddressUtils.makeRegionFromAddress(rehoming.getAddress());
        this.status = rehoming.getStatus();
        this.imageList = imageList;
        this.createdAt = CalculateTime.dateformatForPost(rehoming.getCreatedAt());
        this.updatedAt = CalculateTime.dateformatForPost(rehoming.getUpdatedAt());
        this.isLiked = isLiked;
        this.isBookmarked = isBookmarked;
        this.viewCnt = rehoming.getViewCnt();
        this.likeCnt = likeCnt;
        this.dhppl = rehoming.isDhppl();
        this.covidEnteritis = rehoming.isCovidEnteritis();
        this.kennelCough = rehoming.isKennelCough();
        this.influenza = rehoming.isInfluenza();
        this.rabies = rehoming.isRabies();
        this.comprehensiveVaccine = rehoming.isComprehensiveVaccine();
        this.fpv = rehoming.isFpv();
        this.felv = rehoming.isFelv();
        this.isNeutralized = rehoming.isNeutralized();
    }

    // 비회원 조회 시
    public RehomingResDto(Rehoming rehoming, List<ImageResDto> imageList, int likeCnt) {
        this.postId = rehoming.getRehomingId();
        this.postType = rehoming.getPostType();
        this.userId = rehoming.getUser().getId();
        this.nickname = rehoming.getUser().getNickname();
        this.title = rehoming.getTitle();
        this.content = rehoming.getContent();
        this.petName = rehoming.getPetName();
        this.petAge = rehoming.getPetAge();
        this.category = rehoming.getCategory().getCategoryGroupName();
        this.type = rehoming.getType().getPetCategoryName();
        this.gender = rehoming.getGender();
        this.region = AddressUtils.makeRegionFromAddress(rehoming.getAddress());
        this.status = rehoming.getStatus();
        this.imageList = imageList;
        this.createdAt = CalculateTime.dateformatForPost(rehoming.getCreatedAt());
        this.updatedAt = CalculateTime.dateformatForPost(rehoming.getUpdatedAt());
        this.isLiked = false;
        this.isBookmarked = false;
        this.viewCnt = rehoming.getViewCnt();
        this.likeCnt = likeCnt;
        this.dhppl = rehoming.isDhppl();
        this.covidEnteritis = rehoming.isCovidEnteritis();
        this.kennelCough = rehoming.isKennelCough();
        this.influenza = rehoming.isInfluenza();
        this.rabies = rehoming.isRabies();
        this.comprehensiveVaccine = rehoming.isComprehensiveVaccine();
        this.fpv = rehoming.isFpv();
        this.felv = rehoming.isFelv();
        this.isNeutralized = rehoming.isNeutralized();
    }

}
