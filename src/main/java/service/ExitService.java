package service;

import model.ParkingTicket;

public interface ExitService extends UpdateParkingSpot {
    Double getParkingFee(ParkingTicket parkingTicket);
}
