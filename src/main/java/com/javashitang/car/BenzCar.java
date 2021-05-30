package com.javashitang.car;

public class BenzCar implements Car {

    public BenzCar() {
        System.out.println("BenzCar");
    }

    @Override
    public void getBrand() {
        System.out.println("benz");
    }
}
