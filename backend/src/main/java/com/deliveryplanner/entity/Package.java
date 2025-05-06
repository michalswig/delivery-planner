package com.deliveryplanner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Package {
    private Long id;
    private Integer size;
    private Address address;
    private Boolean isLoaded;


}
