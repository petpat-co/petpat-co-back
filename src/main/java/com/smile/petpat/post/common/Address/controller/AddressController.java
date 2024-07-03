package com.smile.petpat.post.common.Address.controller;

import com.smile.petpat.common.response.SuccessResponse;
import com.smile.petpat.post.common.Address.Dto.AddressReqDto;
import com.smile.petpat.post.common.Address.service.AddressService;
import com.smile.petpat.user.service.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "AddressController",description = "주소 API입니다.")
@RequiredArgsConstructor
@RequestMapping("/api/v1/address")
public class AddressController {
    private final AddressService addressService;

    @Operation(summary = "주소에 해당하는 분양글 조회", description = "주소에 해당하는 분양글 조회")
    @RequestMapping(value = "/rehoming",method = RequestMethod.GET)
    public SuccessResponse getRehomingsByAddress(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @ModelAttribute @Valid AddressReqDto addressReqDto,
                                                 @PageableDefault Pageable pageable){
        return SuccessResponse.success(addressService.getRehomingsByAddress(addressReqDto,pageable,userDetails.getUsername()));
    }

    @Operation(summary = "주소에 해당하는 분양글 조회", description = "주소에 해당하는 분양글 조회")
    @RequestMapping(value = "/trade",method = RequestMethod.GET)
    public SuccessResponse getTradesByAddress(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @ModelAttribute @Valid AddressReqDto addressReqDto,
                                                 @PageableDefault Pageable pageable){
        return SuccessResponse.success(addressService.getTradesByAddress(addressReqDto,pageable,userDetails.getUsername()));
    }

    @Operation(summary = "광역시,특별시,도 목록 조회",description = "광역시,특별시,도 목록 조회")
    @RequestMapping(value = "/province",method = RequestMethod.GET)
    public SuccessResponse getProvince(){
        return SuccessResponse.success(addressService.getProvinceList());
    }

    @Operation(summary = "시,군 목록 조회", description = "시,군 목록 조회")
    @RequestMapping(value = "/city",method = RequestMethod.GET)
    public SuccessResponse getCity(@RequestParam String province){
        return SuccessResponse.success(addressService.getCityList(province));
    }

    @Operation(summary = "구 목록 조회", description = "구 목록 조회")
    @RequestMapping(value = "/district",method = RequestMethod.GET)
    public SuccessResponse getDistrict(@RequestParam String province,
                                   @RequestParam String city){
        return SuccessResponse.success(addressService.getDistrictList(province,city));
    }
    @Operation(summary = "동,면 목록 조회", description = "동,면 목록 조회")
    @RequestMapping(value = "/town",method = RequestMethod.GET)
    public SuccessResponse getTown(@RequestParam String province,
                                   @RequestParam String city,
                                   @RequestParam String district){
        return SuccessResponse.success(addressService.getTownsList(province,city,district));
    }
}
