package com.javashitang;

public class BenzCar implements Car {

    public BenzCar() {
        System.out.println("BenzCar");
    }

    @Override
    public void getBrand() {
        System.out.println("benz");
    }
}
