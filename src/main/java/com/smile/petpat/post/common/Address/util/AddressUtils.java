package com.smile.petpat.post.common.Address.util;

import com.smile.petpat.post.common.Address.domain.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressUtils {
    public static String makeRegionFromAddress(Address address){
        if(address.getCity().equals(""))
            return address.getProvince()+" "+address.getDistrict()+" "+address.getTown();
        if(address.getDistrict().equals(""))
            return address.getProvince()+" "+address.getCity()+" "+address.getTown();
        return address.getProvince()+" "+address.getCity()+" "+address.getDistrict()+" "+address.getTown();
    }
}
