package com.deliveryplanner.service;

import com.deliveryplanner.entity.Vehicle;
import com.deliveryplanner.entity.Package;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.deliveryplanner.utils.PackageUtil.createPackage;
import static com.deliveryplanner.utils.VehicleUtil.createVehicle;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoaderServiceTest {

    @Test
    void testAssignPackageToVehicle_singlePackageFitsInVehicle() {
        LoaderService loaderService = new LoaderService();

        List<Package> packages = List.of(
                createPackage(1L, 5, "Bielany")
        );

        List<Vehicle> vehicles = List.of(
                createVehicle(1L, 10, true)
        );

        loaderService.assignPackageToVehicle(packages, vehicles);

        Vehicle testVehicle = vehicles.get(0);
        assertEquals(1, testVehicle.getLoadedPackages().size());
        assertEquals(5, testVehicle.getActualCapacity());
        assertEquals(1L, testVehicle.getLoadedPackages().get(0).getId());
    }

    @Test
    void testPackageTooBigForAllVehicles() {
        LoaderService loaderService = new LoaderService();

        List<Package> packages = List.of(
                createPackage(1L, 20, "Wola")
        );

        List<Vehicle> vehicles = List.of(
                createVehicle(1L, 10, true)
        );

        loaderService.assignPackageToVehicle(packages, vehicles);

        assertTrue(vehicles.get(0).getLoadedPackages().isEmpty());
        assertEquals(0, vehicles.get(0).getActualCapacity());
    }

    @Test
    void testVehicleAtFullCapacity() {
        Vehicle fullVehicle = createVehicle(1L, 10, true);
        fullVehicle.setActualCapacity(10);

        LoaderService loaderService = new LoaderService();

        List<Package> packages = List.of(
                createPackage(1L, 3, "Ursynów")
        );

        loaderService.assignPackageToVehicle(packages, List.of(fullVehicle));

        assertTrue(fullVehicle.getLoadedPackages().isEmpty());
    }

    @Test
    void testMultiplePackagesExactFit() {
        LoaderService loaderService = new LoaderService();

        List<Package> packages = List.of(
                createPackage(1L, 3, "Mokotów"),
                createPackage(2L, 7, "Mokotów")
        );

        List<Vehicle> vehicles = List.of(
                createVehicle(1L, 10, true)
        );

        loaderService.assignPackageToVehicle(packages, vehicles);

        Vehicle v = vehicles.get(0);
        assertEquals(2, v.getLoadedPackages().size());
        assertEquals(10, v.getActualCapacity());
    }

    @Test
    void testTooManyPackagesForVehicle() {
        LoaderService loaderService = new LoaderService();

        List<Package> packages = List.of(
                createPackage(1L, 5, "Ochota"),
                createPackage(2L, 6, "Ochota")
        );

        List<Vehicle> vehicles = List.of(
                createVehicle(1L, 10, true)
        );

        loaderService.assignPackageToVehicle(packages, vehicles);

        Vehicle v = vehicles.get(0);
        assertEquals(1, v.getLoadedPackages().size());
        assertEquals(5, v.getActualCapacity());
    }

    @Test
    void testNoAvailableVehicles() {
        LoaderService loaderService = new LoaderService();

        List<Package> packages = List.of(
                createPackage(1L, 4, "Śródmieście")
        );

        List<Vehicle> vehicles = List.of(
                createVehicle(1L, 10, false) // Not available
        );

        loaderService.assignPackageToVehicle(packages, vehicles);

        assertTrue(vehicles.get(0).getLoadedPackages().isEmpty());
    }

    @Test
    void testEmptyInputs() {
        LoaderService loaderService = new LoaderService();

        loaderService.assignPackageToVehicle(List.of(), List.of());
        loaderService.assignPackageToVehicle(List.of(createPackage(1L, 3, "Wola")), List.of());
        loaderService.assignPackageToVehicle(List.of(), List.of(createVehicle(1L, 10, true)));

        // No exceptions thrown = test passes
    }









}