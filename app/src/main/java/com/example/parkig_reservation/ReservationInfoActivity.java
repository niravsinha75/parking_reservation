package com.example.parkig_reservation;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReservationInfoActivity extends AppCompatActivity {

    TextView textVehicleModel;
    TextView textVehicleNumber;
    TextView ownername;
    TextView vehicle_type;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_info);

        // Initialize TextViews
        textVehicleModel = findViewById(R.id.text_vehicle_model);
        textVehicleNumber = findViewById(R.id.text_vehicle_number);
        ownername = findViewById(R.id.ownername);
        vehicle_type = findViewById(R.id.vehicle_type);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("UserInformation");

        // Retrieve reserved information from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Retrieve the latest reserved information
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserInfo userInfo = snapshot.getValue(UserInfo.class);
                    // Display the reserved information
                    displayReservationInfo(userInfo);
                    break; // Stop after displaying the first reservation
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    // Method to display the reserved information
    private void displayReservationInfo(UserInfo userInfo) {
        if (userInfo != null) {
            // Populate UI elements with the reserved information
            textVehicleModel.setText("Vehicle Model: " + userInfo.getVehicleModel());
            textVehicleNumber.setText("Vehicle Number: " + userInfo.getVehicleNumber());
            ownername.setText("Owner Name: " + userInfo.getOwnerName());
            vehicle_type.setText("Vehicle Type: " +userInfo.getSelectedVehicleType());

        }
    }
}
