package com.example.parkig_reservation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Admin_login extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        emailEditText = findViewById(R.id.admin_email);
        passwordEditText = findViewById(R.id.admin_password);
        loginButton = findViewById(R.id.admin_login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate email
                if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Enter a valid email address");
                    return;
                }

                // Validate password
                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Password is required");
                    return;
                }

                if ("admin@gmail.com".equals(email) && "admin1234".equals(password)) {
                    startActivity(new Intent(Admin_login.this, AdminDashboard.class));
                } else {
                    Toast.makeText(Admin_login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
