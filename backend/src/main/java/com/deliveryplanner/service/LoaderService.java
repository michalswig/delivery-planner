package com.deliveryplanner.service;

import com.deliveryplanner.data.DistrictCatalog;
import com.deliveryplanner.entity.Package;
import com.deliveryplanner.entity.Vehicle;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class LoaderService {

    public void assignPackageToVehicle(List<Package> packages, List<Vehicle> vehicles) {

        List<Vehicle> vehiclesSorted = getSortedVehiclesByMaxCapacity(vehicles);

        Map<String, List<Package>> grouped = groupPackagesByDistrict(
                packages, DistrictCatalog.getWarsawDistrictAndNeighbours());

        Map<String, List<Package>> sorted = getSortedDistrictPackages(grouped);

        for (Map.Entry<String, List<Package>> entry : sorted.entrySet()) {
            String district = entry.getKey();
            List<Package> districtPackages = new ArrayList<>(entry.getValue());

            Iterator<Vehicle> vehicleIt = vehiclesSorted.iterator();

            while (!districtPackages.isEmpty() && vehicleIt.hasNext()) {
                Vehicle vehicle = vehicleIt.next();
                int remaining = vehicle.getMaxCapacity() - vehicle.getActualCapacity();

                Iterator<Package> packageIt = districtPackages.iterator();
                while (packageIt.hasNext()) {
                    Package p = packageIt.next();
                    if (p.getSize() <= remaining) {
                        vehicle.loadPackage(p);
                        remaining -= p.getSize();
                        packageIt.remove();
                    }
                }

                if (vehicle.getActualCapacity() >= vehicle.getMaxCapacity()) {
                    vehicle.setIsAvailable(false);
                }
            }

            if (!districtPackages.isEmpty()) {
                log.info("⚠️ Unassigned packages in {}: {}", district, districtPackages.size());
            }
        }
    }

    private List<Vehicle> getSortedVehiclesByMaxCapacity(List<Vehicle> vehicles) {
        return vehicles.stream()
                .filter(Vehicle::getIsAvailable)
                .sorted(Comparator.comparingInt(Vehicle::getMaxCapacity).reversed())
                .toList();
    }

    private Map<String, List<Package>> getSortedDistrictPackages(Map<String, List<Package>> groupedPackagesByDistrict) {
        List<Map.Entry<String, List<Package>>> entries = new ArrayList<>(groupedPackagesByDistrict.entrySet());
        entries.sort(Comparator.comparingInt((Map.Entry<String, List<Package>> e) -> e.getValue().size()).reversed());

        Map<String, List<Package>> sortedBySize = new LinkedHashMap<>();

        entries.forEach(entry -> {
                List<Package> sortedPackages = new ArrayList<>(entry.getValue());
                sortedPackages.sort(Comparator.comparingInt(Package::getSize).reversed());
                sortedBySize.put(entry.getKey(), sortedPackages);
        });
        return sortedBySize;
    }

    private Map<String, List<Package>> groupPackagesByDistrict(List<Package> packages, Map<String, Set<String>> districtCatalog) {
        Map<String, List<Package>> packagesByDistrict = new LinkedHashMap<>();
        List<String> districts = new ArrayList<>(districtCatalog.keySet());
        districts.forEach(district -> {
            List<Package> packagesForDistrict = packages.stream()
                    .filter(p -> p.getAddress() != null && p.getAddress().getDistrict() != null &&
                            district.equals(p.getAddress().getDistrict().getName()))
                    .toList();
            packagesByDistrict.put(district, packagesForDistrict);
        });
        return packagesByDistrict;
    }

}
