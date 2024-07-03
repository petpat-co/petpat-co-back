package com.smile.petpat.post.common.Address.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smile.petpat.post.rehoming.domain.Rehoming;
import com.smile.petpat.post.trade.domain.Trade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "TB_ADDRESS")
@NoArgsConstructor
@Data
public class Address {
    @Id
    @Column(name = "ADDRESS_ID")
    private Long addressId;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "TOWN")
    private String town;


    @OneToMany(mappedBy = "address",cascade = CascadeType.ALL)
    @JsonManagedReference //양방향 관계의 엔티티의 직렬화 방향 설정 -> 순환참조 방지
    private List<Rehoming> rehomingList = new ArrayList<>();

    @OneToMany(mappedBy = "address",cascade = CascadeType.ALL)
    @JsonManagedReference //양방향 관계의 엔티티의 직렬화 방향 설정 -> 순환참조 방지
    private List<Trade> tradelist = new ArrayList<>();

    public Address(String province,String city, String district, String town){
        this.province= province;
        this.city = city;
        this.district= district;
        this.town = town;
    }

}
