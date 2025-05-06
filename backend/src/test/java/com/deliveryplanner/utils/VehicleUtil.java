package com.deliveryplanner.utils;

import com.deliveryplanner.entity.Vehicle;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;

@UtilityClass
public class VehicleUtil {

    public static Vehicle createVehicle(Long id, int maxCapacity, boolean isAvailable) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        vehicle.setMaxCapacity(maxCapacity);
        vehicle.setActualCapacity(0);
        vehicle.setCapacity(maxCapacity);
        vehicle.setIsAvailable(isAvailable);
        vehicle.setLoadedPackages(new ArrayList<>());
        return vehicle;
    }

}
