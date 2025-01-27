package com.example.parkig_reservation;

public class User {
    private String userid;
    private String password;
    private String email;
    private String username;
    private String phone;

    // Default constructor (required for Firebase)
    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    // Parameterized constructor
    public User(String userid, String email, String username, String phone, String password) {
        this.userid = userid;
        this.password = password;
        this.email = email;
        this.username = username;
        this.phone = phone;
    }

    // Getters and setters



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

