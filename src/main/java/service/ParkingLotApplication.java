package service;

import exception.CustomException;
import lombok.Data;
import model.*;
import paymentStrategy.PaymentStrategy;
import paymentStrategy.PaymentStrategyFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used Singleton Pattern to ensure only one object is created of ParkingLotApplication
 */
@Data
public class ParkingLotApplication {
    private List<Gate> entryGateList;
    private List<Gate> exitGateList;
    private Map<VehicleType, Map<String, ParkingSpot>> vehicleTypeParkingSpotMap;
//    in case if we have ot find the nearest parking spot to entry gate , take data structure as :
//  private Map<VehicleType, List<ParkingSpot>> vehicleTypeParkingList;

    private EntryService entryService;
    private ExitService exitService;

    private Map<VehicleType, Integer> availableParkingSpace;
    private static ParkingLotApplication instance = null;

    private ParkingLotApplication(List<Gate> entryGateList, List<Gate> exitGateList, Map<VehicleType, Map<String, ParkingSpot>> vehicleTypeParkingSpotMap, EntryService entryService, ExitService exitService) {
        this.entryGateList = entryGateList;
        this.exitGateList = exitGateList;
        this.vehicleTypeParkingSpotMap = vehicleTypeParkingSpotMap;
        this.entryService = entryService;
        this.exitService = exitService;
        fillAvailableParkingSpace();
    }

    private void fillAvailableParkingSpace() {
        Map<VehicleType, Integer> availableParkingSpace = new HashMap<>();
        vehicleTypeParkingSpotMap.forEach((vehicleType, parkingSpotMap) -> {
            availableParkingSpace.put(vehicleType, parkingSpotMap.size());
        });
    }

    public static ParkingLotApplication getParkingLotApplicationInstance(List<Gate> entryGateList, List<Gate> exitGateList, Map<VehicleType, Map<String, ParkingSpot>> vehicleTypeParkingSpotMap, EntryService entryService, ExitService exitService) {
        if (instance == null)
            return new ParkingLotApplication(entryGateList, exitGateList, vehicleTypeParkingSpotMap, entryService, exitService);
        return instance;
    }

    public ParkingTicket onVehicleEntry(Vehicle vehicle, Gate entryGate) throws CustomException {
        entryService.checkAvailability(vehicle.getVehicleType(), availableParkingSpace);
        ParkingSpot parkingSpot = entryService.getParkingSpot(vehicle, vehicleTypeParkingSpotMap);
        ParkingTicket parkingTicket = entryService.getParkingTicket(parkingSpot);
        entryService.updateParkingSpot(parkingTicket.getParkingSpot(), availableParkingSpace, vehicleTypeParkingSpotMap);
        return parkingTicket;
    }

    public ParkingTicket onVehicleExit(ParkingTicket parkingTicket, PaymentType paymentType) {
        Long parkingFee = exitService.getParkingFee(parkingTicket);
        PaymentStrategy paymentStrategy = PaymentStrategyFactory.getPaymentStrategy(paymentType);
        paymentStrategy.makePayment(parkingFee);
        exitService.updateParkingSpot(parkingTicket.getParkingSpot(), availableParkingSpace, vehicleTypeParkingSpotMap);
        return parkingTicket;
    }
}