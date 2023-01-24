package service;

import model.ParkingSpot;
import model.VehicleType;

import java.util.Map;

public interface UpdateParkingSpot {
    void updateParkingSpot(ParkingSpot parkingSpot, Map<VehicleType, Integer> availableParkingSpace, Map<VehicleType, Map<String, ParkingSpot>> parkingSpotMap);
}
