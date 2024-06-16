package com.example.parkig_reservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button aboutus;

    ArrayList<String> arrnames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aboutus = findViewById(R.id.aboutus);
        listView = findViewById(R.id.listview);
        arrnames.add("AIMS Hospital");
        arrnames.add("Dmart");
        arrnames.add("The Sia College");
        arrnames.add("Station");
        ParkingLot parkingLot = new ParkingLot(20);

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),about_us.class);
                startActivity(i);
            }
        });

        // Listview code
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.text_color_listview,arrnames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedLocation = arrnames.get(position);

                boolean available = parkingLot.checkAvailability();
                if (available) {
                    MyPreferences preferences = new MyPreferences(getApplicationContext());
                    String userid = preferences.getUserid();
                    Toast.makeText(MainActivity.this, "slot is avaialable", Toast.LENGTH_SHORT).show();
                    // Check if userid is available
                    if (!userid.isEmpty()) {
                        // Save the selected location to the user's database
                        Toast.makeText(MainActivity.this, "Useid selected", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle case when userid is not available
                        Toast.makeText(MainActivity.this, "Userid not selected", Toast.LENGTH_SHORT).show();
                    }

                    // Proceed to vehicle_type activity
                    Intent intent = new Intent(MainActivity.this, vehicle_type.class);
                    intent.putExtra("selected_location", selectedLocation);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "No available slots.", Toast.LENGTH_SHORT).show();
                }

            }

        } );



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.opt_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if(itemId==R.id.logout)
        {
            // Perform logout action
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    private void logout() {
        // Clear the logged-in status and redirect to LoginActivity
        MyPreferences preferences = new MyPreferences(MainActivity.this);
        preferences.clearLoggedInUser();

        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish(); // Close the MainActivity
    }





}