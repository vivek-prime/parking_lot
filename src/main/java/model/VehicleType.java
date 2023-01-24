package model;

import lombok.Getter;

@Getter
public enum VehicleType {
    TWO_WHEELER(10), FOUR_WHEELER(20);
    private int price;

    VehicleType(int price) {
        this.price = price;
    }
}
