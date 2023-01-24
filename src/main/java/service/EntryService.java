package service;

import exception.CustomException;
import model.ParkingSpot;
import model.ParkingTicket;
import model.Vehicle;
import model.VehicleType;

import java.util.Map;

public interface EntryService extends UpdateParkingSpot {
    void checkAvailability(VehicleType vehicleType, Map<VehicleType, Integer> availableParkingSpace) throws CustomException;

    ParkingSpot getParkingSpot(Vehicle vehicle, Map<VehicleType, Map<String, ParkingSpot>> map);

    ParkingTicket getParkingTicket(ParkingSpot parkingSpot);
}
