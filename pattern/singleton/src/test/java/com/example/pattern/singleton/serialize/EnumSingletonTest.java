package com.example.pattern.singleton.serialize;


import com.example.pattern.singleton.register.EnumSingleton;

import java.io.*;

/**
 * java.io.ObjectInputStream#readEnum(boolean)
 * obj = Enum.valueOf((Class)cl, name)
 * 因此，序列化后得到的obj不会改变
 */
public class EnumSingletonTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);
        objectOutputStream.writeObject(EnumSingleton.getInstance());

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(bis);
        EnumSingleton obj = (EnumSingleton) objectInputStream.readObject();
        System.out.println(obj);
        System.out.println(EnumSingleton.getInstance());
        System.out.println(obj == EnumSingleton.getInstance());
    }
}
