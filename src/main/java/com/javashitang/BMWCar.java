package com.javashitang;

public class BMWCar implements Car {

    public BMWCar() {
        System.out.println("BMWCar");
    }
    @Override
    public void getBrand() {
        System.out.println("bmw");
    }
}
