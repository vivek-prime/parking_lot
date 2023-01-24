package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingSpot {
    private String parkingId;
    private boolean isEmpty;
    private VehicleType vehicleType;
}
