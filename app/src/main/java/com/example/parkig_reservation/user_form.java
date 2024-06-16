package com.example.parkig_reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class user_form extends AppCompatActivity {

    Button btnTimeDate;
    EditText vehicleModel, vehicleNumber, ownerName,parkingDuration;
    TextView selectedRadioButton;
    DatabaseReference userRef;

    Calendar selectedDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);

        btnTimeDate = findViewById(R.id.TimeDate_bt);
        vehicleModel = findViewById(R.id.vehicle_Model);
        vehicleNumber = findViewById(R.id.vehicle_Number);
        ownerName = findViewById(R.id.Name);
        parkingDuration = findViewById(R.id.parkingDuration);
        selectedRadioButton = findViewById(R.id.selectedRadioButton);

        // Initialize Firebase Database reference
        userRef = FirebaseDatabase.getInstance().getReference("UserInformation");

        // Get selected vehicle type from intent
        String vehicleType = getIntent().getStringExtra("VEHICLE_TYPE");
        selectedRadioButton.setText("Vehicle Type: " + vehicleType);

        // Initialize selectedDateTime with current date and time
        selectedDateTime = Calendar.getInstance();

        // Set click listener for btnTimeDate
        btnTimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });
    }

    private void showDateTimePicker() {
        // Get current date and time
        int year = selectedDateTime.get(Calendar.YEAR);
        int month = selectedDateTime.get(Calendar.MONTH);
        int dayOfMonth = selectedDateTime.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = selectedDateTime.get(Calendar.HOUR_OF_DAY);
        int minute = selectedDateTime.get(Calendar.MINUTE);

        // Create and show date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(user_form.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update selectedDateTime with chosen date
                        selectedDateTime.set(Calendar.YEAR, year);
                        selectedDateTime.set(Calendar.MONTH, monthOfYear);
                        selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Create and show time picker dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(user_form.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        // Update selectedDateTime with chosen time
                                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        selectedDateTime.set(Calendar.MINUTE, minute);

                                        // Display selected date and time
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                        String dateTimeString = sdf.format(selectedDateTime.getTime());
                                        showReservationDialog();

                                        // Update Firebase database with selected date and time
                                        updateUserInformation(dateTimeString);
                                    }
                                }, hourOfDay, minute, false);
                        timePickerDialog.show();
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private void updateUserInformation(String dateTimeString) {
        // Get current user ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Get other user information
            String model = vehicleModel.getText().toString().trim();
            String number = vehicleNumber.getText().toString().trim();
            String name = ownerName.getText().toString().trim();
            String vehicleType = selectedRadioButton.getText().toString().trim().replace("Vehicle Type: ", "");
            int duration = Integer.parseInt(parkingDuration.getText().toString().trim()); // Parking duration

            // Create UserInfo object
            UserInfo userInfo = new UserInfo(model, number, vehicleType, name, dateTimeString, duration);

            // Save user information to Firebase database
            userRef.child(userId).setValue(userInfo);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_form_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_return_to_main) {
            // Return to main activity
            Intent intent = new Intent(user_form.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the UserFormActivity
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showReservationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Reservation Successful")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button, do nothing or add any action
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}