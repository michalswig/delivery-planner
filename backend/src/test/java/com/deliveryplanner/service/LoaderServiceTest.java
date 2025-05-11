package com.deliveryplanner.service;

import com.deliveryplanner.data.DistrictCatalog;
import com.deliveryplanner.entity.Package;
import com.deliveryplanner.entity.Vehicle;
import com.deliveryplanner.utils.PackageUtil;
import com.deliveryplanner.utils.VehicleUtil;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LoaderServiceTest {

    private List<Vehicle> getSortedVehiclesByMaxCapacity(List<Vehicle> vehicles) {
        return vehicles.stream()
                .filter(Vehicle::getIsAvailable)
                .sorted(Comparator.comparingInt(Vehicle::getMaxCapacity).reversed())
                .toList();
    }

    @Test
    void shouldReturnOnlyAvailableAndSortedByMaxCapacityVehicles() {
        //given
        Vehicle vehicleOne = VehicleUtil.createVehicle(1L, 3, true);
        Vehicle vehicleTwo = VehicleUtil.createVehicle(2L, 10, false);
        Vehicle vehicleThree = VehicleUtil.createVehicle(3L, 6, true);
        Vehicle vehicleFour = VehicleUtil.createVehicle(4L, 9, true);
        List<Vehicle> vehicles = Arrays.asList(vehicleOne, vehicleTwo, vehicleThree, vehicleFour);
        LoaderService loaderService = new LoaderService();

        //when
        List<Vehicle> sortedVehiclesByMaxCapacity = loaderService.getSortedVehiclesByMaxCapacity(vehicles);
        List<Boolean> areAllVehiclesAvailable = new ArrayList<>();
        for (Vehicle vehicle : sortedVehiclesByMaxCapacity) {
            boolean isAvailable = vehicle.getIsAvailable();
            areAllVehiclesAvailable.add(isAvailable);
        }

        //then
        assertEquals(vehicleOne, sortedVehiclesByMaxCapacity.get(sortedVehiclesByMaxCapacity.size() - 1));
        assertEquals(vehicleFour, sortedVehiclesByMaxCapacity.get(0));
        assertFalse(sortedVehiclesByMaxCapacity.contains(vehicleTwo));
        assertTrue(areAllVehiclesAvailable.stream().allMatch(b -> b));
    }

    @Test
    void shouldGroupedPackagesByDistrict(){
        //given
        Package packageBielany = PackageUtil.createPackage(1L, 1, "Bielany");
        Package packageWola = PackageUtil.createPackage(2L, 2, "Wola");
        Package packageOchota = PackageUtil.createPackage(3L, 3, "Ochota");
        Package packageUrsus = PackageUtil.createPackage(4L, 4, "Ursus");
        Package packageWawer = PackageUtil.createPackage(5L, 5, "Wawer");
        Package packageBemowo = PackageUtil.createPackage(6L, 6, "Bemowo");
        List<Package> packages = Arrays.asList(packageBielany, packageWola, packageOchota, packageUrsus, packageWawer, packageBemowo);
        LoaderService loaderService = new LoaderService();

        //when
        Map<String, List<Package>> groupPackagesByDistrict = loaderService.groupPackagesByDistrict(packages, DistrictCatalog.getWarsawDistrictAndNeighbours());

        //then
        assertEquals(packages.size(), groupPackagesByDistrict.size());
    }




}