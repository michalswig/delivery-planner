package com.deliveryplanner;

import com.deliveryplanner.entity.Address;
import com.deliveryplanner.entity.Vehicle;
import com.deliveryplanner.entity.Package;

import java.util.ArrayList;

public class Configuration {

    public static Vehicle createVehicle(Long id, int maxCapacity, boolean isAvailable) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        vehicle.setMaxCapacity(maxCapacity);
        vehicle.setActualCapacity(0); // no packages yet
        vehicle.setCapacity(maxCapacity); // assuming same as max
        vehicle.setIsAvailable(isAvailable);
        vehicle.setLoadedPackages(new ArrayList<>());
        return vehicle;
    }

    public static Package createPackage(Long id, int size, String district) {
        Package pkg = new Package();
        pkg.setId(id);
        pkg.setSize(size);
        pkg.setIsLoaded(false);

        Address address = new Address();
        address.setDistrict(district);
        pkg.setAddress(address);

        return pkg;
    }

}
