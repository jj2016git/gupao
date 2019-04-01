package com.example.pattern.singleton.serialize;

import com.example.pattern.singleton.lazy.StaticInnerClassSingleton;

import java.io.*;

/**
 * java.io.ObjectInputStream#readOrdinaryObject(boolean)
 * obj = desc.isInstantiable() ? desc.newInstance() : null;
 * if (obj != null &&
 * handles.lookupException(passHandle) == null &&
 * desc.hasReadResolveMethod())
 * {
 * Object rep = desc.invokeReadResolve(obj);
 * if (unshared && rep.getClass().isArray()) {
 * rep = cloneArray(rep);
 * }
 * if (rep != obj) {
 * // Filter the replacement object
 * if (rep != null) {
 * if (rep.getClass().isArray()) {
 * filterCheck(rep.getClass(), Array.getLength(rep));
 * } else {
 * filterCheck(rep.getClass(), -1);
 * }
 * }
 * handles.setObject(passHandle, obj = rep);
 * }
 * }
 * 因此，普通对象序列化后反序列化得到的是一个新对象，除非desc.hasReadResolveMethod()
 */
public class StaticInnerClassSingletonTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);
        objectOutputStream.writeObject(StaticInnerClassSingleton.getInstance());

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(bis);
        StaticInnerClassSingleton obj = (StaticInnerClassSingleton) objectInputStream.readObject();
        System.out.println(obj);
        System.out.println(StaticInnerClassSingleton.getInstance());
        System.out.println(obj == StaticInnerClassSingleton.getInstance());
    }
}
