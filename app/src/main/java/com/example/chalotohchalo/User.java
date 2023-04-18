package com.example.chalotohchalo;

public class User {
    private String name;
    private String gender;
    private String email;
    private String phone;
    private String password;

    public User() {
    }

    public User(String name, String gender, String email, String phone, String password) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // Getters and setters for the fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
