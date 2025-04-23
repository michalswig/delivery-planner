package com.deliveryplanner.entity;

import com.deliveryplanner.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private Long Id;
    private VehicleType vehicleType;
    private Integer capacity;
    private Integer actualCapacity;
    private Integer maxCapacity;
    private Boolean isAvailable;
    private List<Package> loadedPackages;

    public void loadPackage(Package p) {
        if (this.loadedPackages == null) {
            this.loadedPackages = new ArrayList<>();
        }
        this.loadedPackages.add(p);
        this.actualCapacity += p.getSize();
    }

}
