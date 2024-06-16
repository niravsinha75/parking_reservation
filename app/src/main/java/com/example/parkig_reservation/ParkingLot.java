package com.example.parkig_reservation;

import java.util.HashMap;
import java.util.Map;

// ParkingLot class representing the parking lot containing multiple slots
class ParkingLot {
    private ParkingSlot[] slots;
    private Map<String, Integer> userSlots; // Map to store the username and their assigned slot

    public ParkingLot(int capacity) {
        slots = new ParkingSlot[capacity];
        userSlots = new HashMap<>();
        for (int i = 0; i < capacity; i++) {
            slots[i] = new ParkingSlot(i + 1); // Initialize slots with IDs
        }
    }

    public boolean checkAvailability() {
        for (ParkingSlot slot : slots) {
            if (slot.isAvailable()) {
                return true; // At least one slot is available
            }
        }
        return false; // No slots available
    }

    public boolean reserveSlot(String userEmail) {
        for (ParkingSlot slot : slots) {
            if (slot.isAvailable()) {
                slot.setAvailable(false); // Reserve the slot
                slot.setUserEmail(userEmail); // Assign the user email
                userSlots.put(userEmail, slot.getId()); // Update userSlots map
                return true; // Slot reserved successfully
            }
        }
        return false; // No available slots
    }

    public boolean releaseSlot(String userEmail) {
        Integer slotId = userSlots.get(userEmail); // Get the slot assigned to the user
        if (slotId != null) {
            ParkingSlot slot = slots[slotId - 1]; // Find the slot
            slot.setAvailable(true); // Make the slot available
            slot.setUserEmail(null); // Clear the user email
            userSlots.remove(userEmail); // Remove from userSlots map
            return true; // Slot released successfully
        }
        return false; // User does not have a reserved slot
    }

}