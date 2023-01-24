package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Gate {
    private int gateNumber;
    private boolean isOpen;
    private GateType gateType;
}
