package com.example.parkig_reservation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class YourActivity extends AppCompatActivity {

    Button btnPickTime, btnPickDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);

    }

    private void showTimePickerDialog() {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Handle the selected time
                String time = hourOfDay + ":" + minute;
                Toast.makeText(YourActivity.this, "Selected Time: " + time, Toast.LENGTH_SHORT).show();
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void showDatePickerDialog() {
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Handle the selected date
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                Toast.makeText(YourActivity.this, "Selected Date: " + date, Toast.LENGTH_SHORT).show();
            }
        }, year, month, day);

        datePickerDialog.show();
    }
}
