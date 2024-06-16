package com.example.parkig_reservation;

// ParkingSlot class representing each parking slot
class ParkingSlot {
    private int id;
    private boolean available;
    private String userEmail; // Changed field name to userEmail

    public ParkingSlot(int id) {
        this.id = id;
        this.available = true; // Initially all slots are available
        this.userEmail = null; // No user initially
    }

    public int getId() {
        return id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
