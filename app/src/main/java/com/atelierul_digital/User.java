package com.atelierul_digital;

public class User {
    public static final String DEFAULT_DESCRIPTION = "No description";
    public static final String DEFAULT_OCCUPATION = "Not specified";
    public static final String DEFAULT_GENDER = "Not specified";
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private String phoneNumber;
    private int petsCount;
    private String[] petsIds;
    private String description;
    private String[] jobsDone;
    private String[] reviews;
    private String occupation;
    private String gender;

    public User(String userId, String email, String firstName, String lastName, String city,
                String phoneNumber) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.petsCount = 0;
        this.petsIds = null;
        this.description = DEFAULT_DESCRIPTION;
        this.jobsDone = null;
        this.reviews = null;
        this.occupation = DEFAULT_OCCUPATION;
        this.gender = DEFAULT_GENDER;
    }

    public User() {

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPetsCount() {
        return petsCount;
    }

    public void setPetsCount(int petsCount) {
        this.petsCount = petsCount;
    }

    public String[] getPetsIds() {
        return petsIds;
    }

    public void setPetsIds(String[] petsIds) {
        this.petsIds = petsIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getJobsDone() {
        return jobsDone;
    }

    public void setJobsDone(String[] jobsDone) {
        this.jobsDone = jobsDone;
    }

    public String[] getReviews() {
        return reviews;
    }

    public void setReviews(String[] reviews) {
        this.reviews = reviews;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void addPet() {
        ++this.petsCount;
    }
}
