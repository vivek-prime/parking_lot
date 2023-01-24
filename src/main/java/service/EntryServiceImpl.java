package service;

import exception.CustomException;
import model.ParkingSpot;
import model.ParkingTicket;
import model.Vehicle;
import model.VehicleType;

import java.time.Instant;
import java.util.Map;

public class EntryServiceImpl implements EntryService {
    @Override
    public ParkingSpot getParkingSpot(Vehicle vehicle, Map<VehicleType, Map<String, ParkingSpot>> vehicleTypeParkingSpotMap) {
        VehicleType vehicleType = vehicle.getVehicleType();
        Map<String, ParkingSpot> parkingSpotMap = vehicleTypeParkingSpotMap.get(vehicleType);
        for (ParkingSpot parkingSpot : parkingSpotMap.values()) {
            if (parkingSpot.isEmpty())
                return parkingSpot;
        }
        return null;
    }

    @Override
    public ParkingTicket getParkingTicket(ParkingSpot parkingSpot) {
        return new ParkingTicket(Instant.now(), parkingSpot);
    }

    @Override
    public void checkAvailability(VehicleType vehicleType, Map<VehicleType, Integer> availableParkingSpace) throws CustomException {
        if (!(availableParkingSpace.get(vehicleType) > 0))
            throw new CustomException("Parking Space not available");
    }

    @Override
    public void updateParkingSpot(ParkingSpot parkingSpot, Map<VehicleType, Integer> availableParkingSpace, Map<VehicleType, Map<String, ParkingSpot>> parkingSpotMap) {
        VehicleType vehicleType = parkingSpot.getVehicleType();
        String parkingId = parkingSpot.getParkingId();
        availableParkingSpace.put(vehicleType, availableParkingSpace.get(vehicleType) - 1);
        ParkingSpot parkingSpotToBeUpdated = parkingSpotMap.get(vehicleType).get(parkingId);
        parkingSpotToBeUpdated.setEmpty(false);
    }
}
