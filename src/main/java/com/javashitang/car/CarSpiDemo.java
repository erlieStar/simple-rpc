package com.javashitang.car;

import com.javashitang.car.Car;

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
