package com.smile.petpat.post.common.Address.service;

import com.smile.petpat.post.common.Address.repository.AddressRepository;
import com.smile.petpat.post.common.CommonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class getAddressServiceTest {
    @MockBean private AddressRepository addressRepository;
    @Autowired private AddressService addressService;

    @BeforeEach
    void setup(){
        Mockito.when(addressRepository.findProvincesDistinct())
                .thenReturn(List.of("서울특별시","경기도","경상남도"));

        Mockito.when(addressRepository.findCitiesDistinct("서울특별시"))
                .thenReturn(List.of());
        Mockito.when(addressRepository.findCitiesDistinct("경기도"))
                .thenReturn(List.of("용인시","고양시"));

        Mockito.when(addressRepository.findDistrictsDistinct("서울특별시",""))
                .thenReturn(List.of("종로구","마포구"));
        Mockito.when(addressRepository.findDistrictsDistinct("경기도","용인시"))
                .thenReturn(List.of("기흥구","사흥구"));

        Mockito.when(addressRepository.findTownsDistinct("서울특별시","","종로구"))
                .thenReturn(List.of("평창동","청운동","누하동"));
        Mockito.when(addressRepository.findTownsDistinct("경기도","용인시","기흥구"))
                .thenReturn(List.of("하갈동","상갈동"));
    }

    @Test
    @DisplayName("SUCCESS_getProvinceTest")
    void success_getProvinceTest(){
        List<String> provinceList = addressService.getProvinceList();

        assertEquals(3,provinceList.size());
        assertTrue(provinceList.containsAll(List.of("서울특별시","경기도","경상남도")));
    }

    @Test
    @DisplayName("SUCCESS_getCityTest")
    void success_getCityTest(){
        List<String> cityList1 = addressService.getCityList("서울특별시");
        List<String> cityList2 = addressService.getCityList("경기도");

        assertEquals(0,cityList1.size());
        assertEquals(2,cityList2.size());
        assertTrue(cityList2.containsAll(List.of("고양시","용인시")));
    }

    @Test
    @DisplayName("SUCCESS_getDistrictTest")
    void success_getDistrictTest(){
        List<String > districtList1 = addressService.getDistrictList("서울특별시","");
        List<String> districtList2 = addressService.getDistrictList("경기도","용인시");

        assertEquals(2, districtList1.size());
        assertTrue(districtList1.containsAll(List.of("종로구","마포구")));
        assertEquals(2, districtList2.size());
        assertTrue(districtList2.containsAll(List.of("기흥구","사흥구")));
    }

    @Test
    @DisplayName("SUCCESS_getTownTest")
    void success_getTownDistrict(){
        List<String> townList1 = addressService.getTownsList("서울특별시","","종로구");
        List<String> townList2 = addressService.getTownsList("경기도","용인시","기흥구");

        assertEquals(3,townList1.size());
        assertTrue(townList1.containsAll(List.of("평창동","청운동","누하동")));
        assertEquals(2,townList2.size());
        assertTrue(townList2.containsAll(List.of("상갈동","하갈동")));
    }
}
