package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ParkingTicket {
    private Instant entryTime;
    private ParkingSpot parkingSpot;
}
