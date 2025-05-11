package com.deliveryplanner.service;

import com.deliveryplanner.data.DistrictCatalog;
import com.deliveryplanner.entity.Package;
import com.deliveryplanner.entity.Vehicle;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class LoaderService {

    public void assignPackageToVehicle(List<Package> packages, List<Vehicle> vehicles) {

        Map<String, Set<String>> warsawDistrictAndNeighbours = DistrictCatalog.getWarsawDistrictAndNeighbours();
        Map<String, List<Package>> packagesByDistrict = groupPackagesByDistrict(packages, warsawDistrictAndNeighbours);
        Map<String, List<Package>> sortedDistrictPackages = getSortedDistrictPackages(packagesByDistrict);

        Set<String> visitedDistricts = new HashSet<>();
        Queue<String> queue = new LinkedList<>(sortedDistrictPackages.keySet());

        while (!queue.isEmpty()) {
            String district = queue.poll();

            if (visitedDistricts.contains(district)) continue;

            List<Package> districtPackages = sortedDistrictPackages.getOrDefault(district, List.of());

            if (districtPackages.isEmpty()) {
                visitedDistricts.add(district);
                continue;
            }

            boolean allPackagesLoaded = true; //future assignPackagesToAvailableVehicles(districtPackages, vehicles);

            if (allPackagesLoaded) {
                visitedDistricts.add(district);
            } else {
                queue.add(district);
            }

            for (String neighbor : warsawDistrictAndNeighbours.getOrDefault(district, Set.of())) {
                if (!visitedDistricts.contains(neighbor)
                        && sortedDistrictPackages.containsKey(neighbor)
                        && !queue.contains(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }


    }

    List<Vehicle> getSortedVehiclesByMaxCapacity(List<Vehicle> vehicles) {
        return vehicles.stream()
                .filter(Vehicle::getIsAvailable)
                .sorted(Comparator.comparingInt(Vehicle::getMaxCapacity).reversed())
                .toList();
    }

    Map<String, List<Package>> getSortedDistrictPackages(Map<String, List<Package>> groupedPackagesByDistrict) {
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

    Map<String, List<Package>> groupPackagesByDistrict(List<Package> packages, Map<String, Set<String>> districtCatalog) {
        Map<String, List<Package>> packagesByDistrict = new LinkedHashMap<>();
        List<String> districts = new ArrayList<>(districtCatalog.keySet());
        districts.forEach(district -> {
            List<Package> packagesForDistrict = packages.stream()
                    .filter(p -> p.getAddress() != null && p.getAddress().getDistrict() != null &&
                            district.equals(p.getAddress().getDistrict().getName()))
                    .toList();
            if (!packagesForDistrict.isEmpty()) {
                packagesByDistrict.put(district, packagesForDistrict);

            }
        });
        return packagesByDistrict;
    }

}
