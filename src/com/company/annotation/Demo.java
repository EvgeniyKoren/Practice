package com.company.annotation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class Demo {

    public static Properties download() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void initializeObject(Object object, Properties properties)
            throws NoSuchFieldException, IllegalAccessException {
        if (object == null) {
            System.out.println("Nothing to initialize...");
            return;
        }
        Class<?> cl = object.getClass();
        if (cl.isAnnotationPresent(SetObjectFields.class) && !cl.getAnnotation(SetObjectFields.class).instantiated()) {
            Field name = cl.getDeclaredField("name");
            if (shouldInitialize(name)) {
                name.setAccessible(true);
                name.set(object, properties.getProperty("user.name"));
            }
            Field num = cl.getDeclaredField("num");
            if (shouldInitialize(num)) {
                num.setAccessible(true);
                int numProp = Integer.parseInt(properties.getProperty("user.number"));
                num.setInt(object, numProp);
            }
        }
    }

    private static boolean shouldInitialize(Field field) {
        return field.isAnnotationPresent(UnInitialized.class);
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Properties prop = download();
        System.out.println(prop.getProperty("user.name"));
        User user = new User();
        initializeObject(user, prop);
        System.out.println(user.toString());
    }
}
