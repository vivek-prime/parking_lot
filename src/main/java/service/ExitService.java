package service;

import model.ParkingTicket;

public interface ExitService extends UpdateParkingSpot {
    Long getParkingFee(ParkingTicket parkingTicket);
}
