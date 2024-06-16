package com.example.parkig_reservation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboard extends AppCompatActivity {

    Button viewUsersButton;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        viewUsersButton = findViewById(R.id.view_users_button);
        logoutButton = findViewById(R.id.admin_logout_button);
        Handler handler = new Handler();

        viewUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(AdminDashboard.this, ViewUsersActivity.class));
                        finish();
                    }
                },3000);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform logout action
                logout();
            }
        });
    }

    private void logout() {
        // Perform logout action, for example, using Firebase Auth or SharedPreferences
        // For example:
        // FirebaseAuth.getInstance().signOut();
        // Then navigate to the login activity
        startActivity(new Intent(AdminDashboard.this, Login.class));
        finish(); // Close the AdminDashboard activity
    }
}