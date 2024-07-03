package com.smile.petpat.post.common.Address.repository;

import com.smile.petpat.post.common.Address.domain.Address;
import com.smile.petpat.post.common.Address.repository.querydsl.AddressRepositoryQuerydsl;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long>, AddressRepositoryQuerydsl {
    Optional<Address> findAddressByProvinceAndCityAndDistrictAndTown(String province, String city, String district, String town);

    @Query("SELECT DISTINCT a.province FROM TB_ADDRESS a")
    List<String> findProvincesDistinct();

    @Query("SELECT DISTINCT a.city from TB_ADDRESS a WHERE a.province = :province AND a.city <> ''")
    List<String> findCitiesDistinct(@Param("province")String province);

    @Query("SELECT DISTINCT a.district from TB_ADDRESS a WHERE a.province = :province AND a.city =:city AND a.district <>''")
    List<String> findDistrictsDistinct(@Param("province") String province,
                                       @Param("city") String city);

    @Query("SELECT a.town from TB_ADDRESS a WHERE a.province = :province AND a.city = :city AND a.district = :district")
    List<String> findTownsDistinct(@Param("province")String province,
                                  @Param("city")String city,
                                  @Param("district")String district);
}
