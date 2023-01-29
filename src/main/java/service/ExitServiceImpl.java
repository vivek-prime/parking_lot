package service;

import model.ParkingSpot;
import model.ParkingTicket;
import model.VehicleType;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public class ExitServiceImpl implements ExitService, UpdateParkingSpot {
    @Override
    public Double getParkingFee(ParkingTicket parkingTicket) {
        int vehicleTypeFee = parkingTicket.getParkingSpot().getVehicleType().getPrice();
        return (double) (Duration.between(parkingTicket.getEntryTime(), Instant.now()).toMillis() * vehicleTypeFee);
    }

    @Override
    public void updateParkingSpot(ParkingSpot parkingSpot, Map<VehicleType, Integer> availableParkingSpace, Map<VehicleType, Map<String, ParkingSpot>> parkingSpotMap) {
        VehicleType vehicleType = parkingSpot.getVehicleType();
        String parkingId = parkingSpot.getParkingId();
        availableParkingSpace.put(vehicleType, availableParkingSpace.get(vehicleType) + 1);
        ParkingSpot parkingSpotToBeUpdated = parkingSpotMap.get(vehicleType).get(parkingId);
        parkingSpotToBeUpdated.setEmpty(true);
    }
}
