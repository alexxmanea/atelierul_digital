package com.atelierul_digital;

public class Pet {
    private String id;
    private String name;
    private String type;
    private int age;
    private String ownerId;
    private String gender;
    private String description;

    public Pet(String id, String name, String type, int age, String ownerId, String gender,
               String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.age = age;
        this.description = description;
        this.ownerId = ownerId;
        this.gender = gender;
    }

    public Pet() {

    }

    public static boolean isValidName(String name) {
        return !name.isEmpty() && name.length() <= 30;
    }

    public static boolean isValidAge(int age) {
        return age >= 1 && age <= 100;
    }

    public static boolean isValidDescription(String description) {
        return !description.isEmpty() && description.length() <= 200;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
