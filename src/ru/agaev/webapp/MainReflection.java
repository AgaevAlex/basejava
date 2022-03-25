package ru.agaev.webapp;

import ru.agaev.webapp.model.Resume;

import java.lang.reflect.Field;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException {
        Resume resume = new Resume();
        Field field = resume.getClass().getDeclaredFields()[0];
        System.out.println(field.getName());
        field.get(resume);
        System.out.println(resume);
    }
}
