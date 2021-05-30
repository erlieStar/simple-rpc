package com.javashitang;

public class Student implements Person {

    public Student() {
        System.out.println("Student");
    }

    @Override
    public void getDesc() {
        System.out.println("student desc");
    }
}
