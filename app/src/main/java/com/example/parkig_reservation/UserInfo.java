package com.example.parkig_reservation;

public class UserInfo extends User {
    private String vehicleModel;
    private String vehicleNumber;
    private String ownerName;
    private String selectedVehicleType;
    private String dateTime;
    private int parkingDuration; // New field for parking duration

    public UserInfo() {
        // Default constructor required for Firebase
    }

    public UserInfo(String vehicleModel, String vehicleNumber, String selectedVehicleType, String ownerName, String dateTime, int parkingDuration) {
        this.vehicleModel = vehicleModel;
        this.vehicleNumber = vehicleNumber;
        this.ownerName = ownerName;
        this.selectedVehicleType = selectedVehicleType;
        this.dateTime = dateTime;
        this.parkingDuration = parkingDuration; // Initialize parking duration
    }

    // Getter methods
    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getSelectedVehicleType() {
        return selectedVehicleType;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getParkingDuration() {
        return parkingDuration; // Getter for parking duration
    }
}
