package com.aniketchatterjee.myapplication;

import java.util.HashMap;

public class Singleton<T> {

    private static final HashMap<Class<?>, Object> instances = new HashMap<>();

    private Singleton() {

    }

    public static synchronized <T> T getInstance(Class<T> clazz) {
        Object instance = instances.get(clazz);
        if (instance == null) {
            try {
                instance = clazz.newInstance();
                instances.put(clazz, instance);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return clazz.cast(instance);
    }
}
