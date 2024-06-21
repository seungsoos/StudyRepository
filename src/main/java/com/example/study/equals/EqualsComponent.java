package com.example.study.equals;

public class EqualsComponent {

    public static void main(String[] args) {

        Equals equals1 = new Equals("name", "address", 10);
        Equals equals2 = new Equals("name", "address", 10);

        System.out.println(" equals 비교 ::" + equals1.equals(equals2));
        System.out.println(" == 비교 ::" + (equals1 == equals2));

    }
}
