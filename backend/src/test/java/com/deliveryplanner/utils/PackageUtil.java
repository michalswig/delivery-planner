package com.deliveryplanner.utils;

import com.deliveryplanner.entity.Address;
import com.deliveryplanner.entity.District;
import com.deliveryplanner.entity.Package;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PackageUtil {

    public static Package createPackage(Long id, int size, String district) {
        com.deliveryplanner.entity.Package aPackage = new Package();
        aPackage.setId(id);
        aPackage.setSize(size);
        aPackage.setIsLoaded(false);

        Address address = new Address();
        address.setDistrict(new District(district, null));
        aPackage.setAddress(address);

        return aPackage;
    }

}
