package com.deliveryplanner.service;

import com.deliveryplanner.entity.Vehicle;
import com.deliveryplanner.entity.Package;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.deliveryplanner.Configuration.createPackage;
import static com.deliveryplanner.Configuration.createVehicle;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoaderServiceTest {

    @Test
    void testAssignPackageToVehicle_singlePackageFitsInVehicle() {
        LoaderService loaderService = new LoaderService();

        List<Package> packages = List.of(
                createPackage(1L, 5, "Wola")
        );

        List<Vehicle> vehicles = List.of(
                createVehicle(1L, 10, true)
        );

        loaderService.assignPackageToVehicle(packages, vehicles);

        Vehicle testVehicle = vehicles.get(0);
        assertEquals(1, testVehicle.getLoadedPackages().size());
        assertEquals(5, testVehicle.getActualCapacity());
        assertTrue(testVehicle.getLoadedPackages().get(0).getId().equals(1L));
    }


}