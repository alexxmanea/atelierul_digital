package com.atelierul_digital.entities;

public class Job {
    private String jobId;
    private String[] type;
    private int salary;
    private boolean negotiable;
    private String employerId;
    private String[] petsIds;
    private String jobDescription;
    private boolean available;

    public Job(String jobId, String[] type, int salary, boolean negotiable, String employerId,
               String[] petsIds, String jobDescription) {
        this.jobId = jobId;
        this.type = type;
        this.salary = salary;
        this.negotiable = negotiable;
        this.employerId = employerId;
        this.petsIds = petsIds;
        this.jobDescription = jobDescription;
        this.available = true;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public boolean isNegotiable() {
        return negotiable;
    }

    public void setNegotiable(boolean negotiable) {
        this.negotiable = negotiable;
    }

    public String getEmployerId() {
        return employerId;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    public String[] getPetsIds() {
        return petsIds;
    }

    public void setPetsIds(String[] petsIds) {
        this.petsIds = petsIds;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
