package com.smile.petpat.post.rehoming.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.smile.petpat.config.comm.Timestamped;
import com.smile.petpat.post.category.domain.CategoryGroup;
import com.smile.petpat.post.category.domain.PetCategory;
import com.smile.petpat.post.category.domain.PostType;
import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.common.status.PostStatus;
import com.smile.petpat.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "TB_REHOMING")
@AllArgsConstructor
@Builder
@DynamicUpdate
public class Rehoming extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REHOMING_ID")
    private Long rehomingId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "TITLE", nullable = false, length = 20)
    private String title;

    @Column(name = "CONTENT", columnDefinition = "TEXT", nullable = false, length = 2000)
    private String content;

    @Column(name = "PET_NAME", nullable = false)
    private String petName;

    @Column(name = "PET_AGE")
    private LocalDate petAge;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_GROUP_ID", nullable = false)
    private CategoryGroup category;

    @ManyToOne
    @JoinColumn(name = "PET_CATEGORY_ID", nullable = false)
    private PetCategory type;

    @Column(name = "GENDER", nullable = false)
    private RehomingCommand.PetGender gender;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    @JsonBackReference //양방향 관계의 엔티티의 직렬화 방향 설정 -> 순환참조 방지
    private Address address;

    @Column(name = "DETAIL_AD_NAME")
    private String detailAdName;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @Column(name = "POST_TYPE")
    @Enumerated(EnumType.STRING)
    private PostType postType;
    @Column(name = "VIEW_CNT")
    private int viewCnt;

    @Column(name = "DHPPL_VACCINE")
    private boolean dhppl;

    @Column(name = "COVID_ENTERITIS_VACCINE")
    private boolean covidEnteritis;

    @Column(name = "KENNEL_COUGH_VACCINE")
    private boolean kennelCough;

    @Column(name = "INFLUENZA_VACCINE")
    private boolean influenza;

    @Column(name = "RABIES_VACCINE")
    private boolean rabies;

    @Column(name = "COMPREHENSIVE_VACCINE")
    private boolean comprehensiveVaccine;

    @Column(name = "FELINE_PARVOVIRUS_VACCINE")
    private boolean fpv;

    @Column(name = "FELINE_LEUKEMIA_VIRUS_VACCINE")
    private boolean felv;

    @Column(name = "IS_NEUTRALIZED")
    private boolean isNeutralized;

    public void isFinding() {
        this.status = PostStatus.REHOMING_FINDING;
    }

    public void isReserved() {
        this.status = PostStatus.REHOMING_RESERVING;
    }

    public void isMatched() {
        this.status = PostStatus.REHOMING_MATCHED;
    }

    public Rehoming(Long rehomingId, User user, String title, String content, String petName, LocalDate petAge,
                    CategoryGroup category, PetCategory type, RehomingCommand.PetGender gender,  String detailAdName,
                    PostStatus status, PostType postType, int viewCnt,
                    boolean dhppl, boolean covidEnteritis, boolean kennelCough, boolean influenza, boolean rabies,
                    boolean comprehensiveVaccine, boolean fpv, boolean felv, boolean isNeutralized) {
        this.rehomingId = rehomingId;
        this.user = user;
        this.title = title;
        this.content = content;
        this.petName = petName;
        this.petAge = petAge;
        this.category = category;
        this.type = type;
        this.gender = gender;
        this.detailAdName = detailAdName;
        this.status = status;
        this.postType = postType;
        this.viewCnt = viewCnt;
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

    public Rehoming() {

    }


    // 분양 게시글 수정
    public void update(Rehoming initRehoming,Address address) {
        this.title = initRehoming.getTitle();
        this.content = initRehoming.getContent();
        this.petName = initRehoming.getPetName();
        this.petAge = initRehoming.getPetAge();
        this.category = initRehoming.getCategory();
        this.type = initRehoming.getType();
        this.gender = initRehoming.getGender();
        this.address =address;
        this.detailAdName = initRehoming.getDetailAdName();
        this.status = initRehoming.getStatus();
        this.dhppl = initRehoming.isDhppl();
        this.covidEnteritis = initRehoming.isCovidEnteritis();
        this.kennelCough = initRehoming.isKennelCough();
        this.influenza = initRehoming.isInfluenza();
        this.rabies = initRehoming.isRabies();
        this.comprehensiveVaccine = initRehoming.isComprehensiveVaccine();
        this.fpv = initRehoming.isFpv();
        this.felv = initRehoming.isFelv();
        this.isNeutralized = initRehoming.isNeutralized();
    }

    // 조회수 증가
    public void updateViewCnt(Rehoming rehoming) {
        this.viewCnt = rehoming.getViewCnt() + 1;
    }

}


