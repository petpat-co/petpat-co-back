package com.smile.petpat.post.common.Address.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Transactional
@ActiveProfiles("test")
public class getAddressRepositoryTest {
    @Autowired private AddressRepository addressRepository;

    @Test
    @DisplayName("SUCCESS_getProvinceTest")
    void success_getProvinceTest(){
        List<String> provinceList = addressRepository.findProvincesDistinct();

        assertEquals(3,provinceList.size());
        assertTrue(provinceList.containsAll(List.of("서울특별시","경기도","경상남도")));
    }

    @Test
    @DisplayName("SUCCESS_getCityTest")
    void success_getCityTest(){
        List<String> cityList1 = addressRepository.findCitiesDistinct("서울특별시");
        List<String> cityList2 = addressRepository.findCitiesDistinct("경기도");

        assertEquals(0,cityList1.size());
        assertEquals(2,cityList2.size());
        assertTrue(cityList2.containsAll(List.of("고양시","용인시")));
    }

    @Test
    @DisplayName("SUCCESS_getDistrictTest")
    void success_getDistrictTest(){
        List<String> districtList1 = addressRepository.findDistrictsDistinct("서울특별시","");
        List<String> districtList2 = addressRepository.findDistrictsDistinct("경기도","용인시");

        assertEquals(2,districtList1.size());
        assertTrue(districtList1.containsAll(List.of("마포구","종로구")));
        assertEquals(2,districtList2.size());
        assertTrue(districtList2.containsAll(List.of("기흥구","사흥구")));
    }

    @Test
    @DisplayName("SUCCESS_getTownTest")
    void success_getTownTest(){
        List<String> townList1 = addressRepository.findTownsDistinct("서울특별시","","종로구");
        List<String> townList2 = addressRepository.findTownsDistinct("경기도","용인시","기흥구");

        assertEquals(3, townList1.size());
        assertTrue(townList1.containsAll(List.of("청운동","평창동","누하동")));
        assertEquals(2, townList2.size());
        assertTrue(townList2.containsAll(List.of("상갈동","하갈동")));
    }
}
