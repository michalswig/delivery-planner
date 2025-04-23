package com.deliveryplanner.service;

import com.deliveryplanner.entity.Package;
import com.deliveryplanner.entity.Vehicle;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class LoaderService {

    public void assignPackageToVehicle(List<Package> packages, List<Vehicle> vehicles) {

        List<Vehicle> vehiclesSortedByMaxCapacity = getSortedVehiclesByMaxCapacity(vehicles);

        LinkedHashMap<String, List<Package>> packagesGroupedByDistrict = groupPackagesByDistrict(packages);

        LinkedHashMap<String, List<Package>> districtSortedByPackagesSize = getSortedDistrictPackages(packagesGroupedByDistrict);

        for (Map.Entry<String, List<Package>> entry : packagesGroupedByDistrict.entrySet()) {
            String district = entry.getKey();
            List<Package> singleDistrictPackages = districtSortedByPackagesSize.getOrDefault(district, new ArrayList<>());
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
                .sorted((v1, v2) -> Integer.compare(v1.getMaxCapacity(), v2.getMaxCapacity()))
                .toList();
    }

    private LinkedHashMap<String, List<Package>> getSortedDistrictPackages(Map<String, List<Package>> packagesByDistrict) {
        LinkedHashMap<String, List<Package>> sortedMap = new LinkedHashMap<>();

        packagesByDistrict.entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .forEach(entry -> sortedMap.put(entry.getKey(), entry.getValue()));

        return sortedMap;
    }

    private LinkedHashMap<String, List<Package>> groupPackagesByDistrict(List<Package> packages) {
        List<String> warsawDistricts = Arrays.asList(
                "Bemowo", "Białołęka", "Bielany", "Mokotów", "Ochota",
                "Praga-Południe", "Praga-Północ", "Rembertów", "Śródmieście", "Targówek",
                "Ursus", "Ursynów", "Wawer", "Wesoła", "Wilanów", "Włochy", "Wola", "Żoliborz"
        );

        LinkedHashMap<String, List<Package>> packagesByDistrict = new LinkedHashMap<>();

        for (String warsawDistrict : warsawDistricts) {
            List<Package> packagesForDistrict = new ArrayList<>();
            for (Package p : packages) {
                if (warsawDistrict.equals(p.getAddress().getDistrict())) {
                    packagesForDistrict.add(p);
                }
            }
            packagesByDistrict.put(warsawDistrict, packagesForDistrict);
        }
        return packagesByDistrict;
    }

}
