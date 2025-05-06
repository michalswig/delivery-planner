package com.deliveryplanner.service;

import com.deliveryplanner.data.DistrictCatalog;
import com.deliveryplanner.entity.Package;
import com.deliveryplanner.entity.Vehicle;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class LoaderService {

    public void assignPackageToVehicle(List<Package> packages, List<Vehicle> vehicles) {

        List<Vehicle> vehiclesSortedByMaxCapacity = getSortedVehiclesByMaxCapacity(vehicles);

        Map<String, List<Package>> packagesGroupedByDistrict = groupPackagesByDistrict(packages, DistrictCatalog.warsawDistricts());

        Map<String, List<Package>> packagesSortedByDistrictAndSize = getSortedDistrictPackages(packagesGroupedByDistrict);

        for (Map.Entry<String, List<Package>> entry : packagesGroupedByDistrict.entrySet()) {
            String district = entry.getKey();
            List<Package> singleDistrictPackages = packagesSortedByDistrictAndSize.getOrDefault(district, new ArrayList<>());
            Iterator<Vehicle> vehicleIterator = vehiclesSortedByMaxCapacity.iterator();

            while (!singleDistrictPackages.isEmpty() && vehicleIterator.hasNext()) {
                Vehicle vehicle = vehicleIterator.next();
                Integer remainingCapacity = vehicle.getMaxCapacity() - vehicle.getActualCapacity();

                Iterator<Package> packageIterator = singleDistrictPackages.iterator();
                while (packageIterator.hasNext()) {
                    Package currentPackage = packageIterator.next();
                    if (currentPackage.getSize() <= remainingCapacity) {
                        vehicle.loadPackage(currentPackage);
                        remainingCapacity -= currentPackage.getSize();
                        packageIterator.remove();
                    }
                }

                if (vehicle.getActualCapacity() >= remainingCapacity) {
                    vehicle.setIsAvailable(false);
                }

                if (singleDistrictPackages.isEmpty()) {
                    log.info("Packages left unassigned in {}:", entry.getKey());
                }
            }
        }
    }

    private List<Vehicle> getSortedVehiclesByMaxCapacity(List<Vehicle> vehicles) {
        return vehicles.stream()
                .filter(Vehicle::getIsAvailable)
                .sorted(Comparator.comparingInt(Vehicle::getMaxCapacity))
                .toList();
    }

    private Map<String, List<Package>> getSortedDistrictPackages(Map<String, List<Package>> packagesByDistrict) {
        Map<String, List<Package>> sortedPackagesByDistrictAndPackageSize = new LinkedHashMap<>();

        packagesByDistrict.entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size())) // sort districts by number of packages
                .forEach(entry -> {
                    List<Package> sortedPackages = new ArrayList<>(entry.getValue());
                    sortedPackages.sort(Comparator.comparingInt(Package::getSize).reversed()); // sort packages by size
                    sortedPackagesByDistrictAndPackageSize.put(entry.getKey(), sortedPackages);
                });

        return sortedPackagesByDistrictAndPackageSize;
    }

    private Map<String, List<Package>> groupPackagesByDistrict(List<Package> packages, List<String> districts) {

        Map<String, List<Package>> packagesByDistrict = new LinkedHashMap<>();

        for (String warsawDistrict : districts) {
            List<Package> packagesForDistrict = new ArrayList<>();
            for (Package p : packages) {
                if (warsawDistrict.equals(p.getAddress().getDistrict().getName())) {
                    packagesForDistrict.add(p);
                }
            }
            packagesByDistrict.put(warsawDistrict, packagesForDistrict);
        }
        return packagesByDistrict;
    }

}
