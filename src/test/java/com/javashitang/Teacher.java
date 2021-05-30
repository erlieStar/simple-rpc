package com.javashitang;

public class Teacher implements Person {

    public Teacher() {
        System.out.println("Teacher");
    }
    @Override
    public void getDesc() {
        System.out.println("teacher desc");
    }
}
