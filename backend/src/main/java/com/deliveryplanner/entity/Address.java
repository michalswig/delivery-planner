package com.deliveryplanner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String street;
    private Integer streetNumber;
    private Integer apartmentNumber;
    private District district;
    private String city;
    private String state;
    private String zip;
    private String country;

}
