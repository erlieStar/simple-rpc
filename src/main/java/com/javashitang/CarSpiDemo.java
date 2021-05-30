package com.javashitang;

import java.util.ServiceLoader;

public class CarSpiDemo {

    public static void main(String[] args) {
        ServiceLoader<Car> carServiceLoader = ServiceLoader.load(Car.class);
        System.out.println("---");
        // benz
        // bmw
        carServiceLoader.forEach(Car::getBrand);
    }
}
