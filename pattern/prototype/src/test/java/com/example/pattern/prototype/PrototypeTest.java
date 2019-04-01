package com.example.pattern.prototype;

import java.io.IOException;

public class PrototypeTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Country china = new Country("China");
        Person li = new Person(china, "Li", 20, new CreditCard("1", "Li"));
        Person liCopy = li.clone();
        // true
        System.out.println(liCopy.getCountry() == li.getCountry());
        // true
        System.out.println(liCopy.getCreditCard() == li.getCreditCard());

        Person liDeepCopy = li.deepClone();
        // true
        System.out.println(liDeepCopy.getCountry() == li.getCountry());
        // false
        System.out.println(liDeepCopy.getCreditCard() == li.getCreditCard());
    }
}
