package ru.hts.springdoclet;

import org.apache.commons.lang3.ClassUtils;

/** @author Ivan Sungurov */
public class ReflectionUtils {
    public static Class<?> getRequiredClass(String className) {
        try {
            return ClassUtils.getClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
