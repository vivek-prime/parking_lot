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
    private Map<VehicleType, Map<String, ParkingSpot>> vehicleTypeParkingSpotMap;  // key - parkingId
//    in case if we have to find the nearest parking spot to entry gate , take data structure as :
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
        availableParkingSpace = new HashMap<>();
        vehicleTypeParkingSpotMap.forEach((vehicleType, parkingSpotMap) -> {
            availableParkingSpace.put(vehicleType, parkingSpotMap.size());
        });
    }

    public static ParkingLotApplication getParkingLotApplicationInstance(List<Gate> entryGateList, List<Gate> exitGateList, Map<VehicleType,
            Map<String, ParkingSpot>> vehicleTypeParkingSpotMap, EntryService entryService, ExitService exitService) {
        if (instance == null) {
            synchronized (ParkingLotApplication.class) {
                if (instance == null)
                    instance = new ParkingLotApplication(entryGateList, exitGateList, vehicleTypeParkingSpotMap, entryService, exitService);
            }
        }
        return instance;
    }

    public ParkingTicket onVehicleEntry(Vehicle vehicle, Gate entryGate) throws CustomException {
        entryService.checkAvailability(vehicle.getVehicleType(), availableParkingSpace);
        ParkingSpot parkingSpot = entryService.getParkingSpot(vehicle, vehicleTypeParkingSpotMap);
        ParkingTicket parkingTicket = entryService.getParkingTicket(parkingSpot);
        entryService.updateParkingSpot(parkingTicket.getParkingSpot(), availableParkingSpace, vehicleTypeParkingSpotMap);
        return parkingTicket;
    }

    public ParkingTicket onVehicleExit(ParkingTicket parkingTicket, PaymentType paymentType) throws CustomException {
        Double parkingFee = exitService.getParkingFee(parkingTicket);
        PaymentStrategy paymentStrategy = PaymentStrategyFactory.getPaymentStrategy(paymentType);
        PaymentStatus paymentStatus = paymentStrategy.makePayment(parkingFee);
        if (paymentStatus.equals(PaymentStatus.FAILED))
            throw new CustomException("Payment Failed, try with different method");
        exitService.updateParkingSpot(parkingTicket.getParkingSpot(), availableParkingSpace, vehicleTypeParkingSpotMap);
        return parkingTicket;
    }
}