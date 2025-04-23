package com.deliveryplanner.enums;

public enum VehicleType {
    CAR("Car"),
    TRUCK("Truck"),
    MOTORCYCLE("Motorcycle"),
    BICYCLE("Bicycle");

    private final String type;

    VehicleType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
