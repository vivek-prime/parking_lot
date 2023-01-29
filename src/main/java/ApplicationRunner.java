import exception.CustomException;
import model.*;
import service.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationRunner {
    public static void main(String[] args) throws CustomException {
//        gates
        List<Gate> entryGateList = new ArrayList<>();
        List<Gate> exitGateList = new ArrayList<>();
        entryGateList.add(new Gate(1, true, GateType.ENTRY_GATE));
        exitGateList.add(new Gate(2, true, GateType.EXIT_GATE));

//        Vehicle Map
        Map<VehicleType, Map<String, ParkingSpot>> vehicleTypeParkingSpotMap = new HashMap<>();
        Map<String, ParkingSpot> twoWheelerMap = new HashMap<>();
        twoWheelerMap.put("t01", new ParkingSpot("t01", true, VehicleType.TWO_WHEELER));
//        twoWheelerMap.put("t02", new ParkingSpot("t02", true, VehicleType.TWO_WHEELER));
//        twoWheelerMap.put("t03", new ParkingSpot("t03", true, VehicleType.TWO_WHEELER));
        Map<String, ParkingSpot> fourWheelerMap = new HashMap<>();
        fourWheelerMap.put("f01", new ParkingSpot("f01", true, VehicleType.FOUR_WHEELER));
//        fourWheelerMap.put("f02", new ParkingSpot("f02", true, VehicleType.FOUR_WHEELER));
//        fourWheelerMap.put("f03", new ParkingSpot("f03", true, VehicleType.FOUR_WHEELER));
        vehicleTypeParkingSpotMap.put(VehicleType.TWO_WHEELER, twoWheelerMap);
        vehicleTypeParkingSpotMap.put(VehicleType.FOUR_WHEELER, fourWheelerMap);

        EntryService entryService = new EntryServiceImpl();
        ExitService exitService = new ExitServiceImpl();


        ParkingLotApplication parkingLotApplication = ParkingLotApplication.getParkingLotApplicationInstance(
                entryGateList, exitGateList, vehicleTypeParkingSpotMap, entryService, exitService
        );

        Vehicle bike1 = new Vehicle("bike1", VehicleType.TWO_WHEELER);
        Vehicle car1 = new Vehicle("car1", VehicleType.FOUR_WHEELER);

        ParkingTicket bikeParkingTicket = parkingLotApplication.onVehicleEntry(bike1, null);
        System.out.println(bikeParkingTicket);


        ParkingTicket carParkingTicket = parkingLotApplication.onVehicleEntry(car1, null);
        System.out.println(carParkingTicket);

        parkingLotApplication.onVehicleExit(bikeParkingTicket, PaymentType.CASH);
        parkingLotApplication.onVehicleExit(carParkingTicket, PaymentType.UPI);
    }
}
