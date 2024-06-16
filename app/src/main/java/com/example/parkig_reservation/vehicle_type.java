package com.example.parkig_reservation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class vehicle_type extends AppCompatActivity {

     RadioGroup radioGroupVehicleType;
     Button buttonSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_type);

        radioGroupVehicleType = findViewById(R.id.radioGroupVehicleType);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        String selectedLocation = getIntent().getStringExtra("selected_location");

        // Print selected location to logcat to check if it's received correctly
        Log.d("Selected_Location", "Selected location: " + selectedLocation);


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupVehicleType.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    // No option selected
                    Toast.makeText(getApplicationContext(), "Please select a vehicle type", Toast.LENGTH_SHORT).show();
                } else {
                    // Get the selected vehicle type
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String selectedVehicleType = selectedRadioButton.getText().toString();

                    // You can use the selectedVehicleType as needed
                    Toast.makeText(getApplicationContext(), "Selected: " + selectedVehicleType, Toast.LENGTH_SHORT).show();

                    // Optionally, you can pass the selected vehicle type to the next activity
                    Intent intent2 = new Intent(getApplicationContext(), user_form.class);
                    intent2.putExtra("VEHICLE_TYPE", selectedVehicleType);
                    startActivity(intent2);

                }
            }
        });
    }
}