package com.javashitang;

import java.util.ServiceLoader;

public class TestJavaSpiDemo {

    public static void main(String[] args) {
        ServiceLoader<Person> carServiceLoader = ServiceLoader.load(Person.class);
        System.out.println("---");
        // benz
        // bmw
        carServiceLoader.forEach(Person::getDesc);
    }
}
