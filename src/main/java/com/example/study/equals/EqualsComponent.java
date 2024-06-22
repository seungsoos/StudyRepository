package com.example.study.equals;

import java.util.HashMap;

public class EqualsComponent {


    public static void main(String[] args) {

        Equals equals1 = new Equals("name", "address", 10);
        Equals equals2 = new Equals("name", "address", 10);

        System.out.println(" equals 비교 ::" + equals1.equals(equals2));
        System.out.println(" == 비교 ::" + (equals1 == equals2));


        int i1 = equals1.hashCode();
        System.out.println("i1 = " + i1);
        int i2 = equals2.hashCode();
        System.out.println("i2 = " + i2);

        HashMap<Integer, String> hashMap = new HashMap<>();

        hashMap.put(i1, "test1");
        System.out.println("hashMap = " + hashMap);
        hashMap.put(10, "test12312123");
        hashMap.put(i2, "test2");
        System.out.println("hashMap = " + hashMap);

    }

}
