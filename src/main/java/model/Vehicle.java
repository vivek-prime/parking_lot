package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vehicle {
    private String vehicleNumber;
    private VehicleType vehicleType;
}
