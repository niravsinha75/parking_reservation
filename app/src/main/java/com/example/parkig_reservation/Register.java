package com.example.parkig_reservation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText email, password_reg1, phone_no, username_reg;
    Button button_reg;
    TextView have_account;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registert);

        mAuth = FirebaseAuth.getInstance();

        password_reg1 = findViewById(R.id.password_reg1);
        email = findViewById(R.id.email);
        phone_no = findViewById(R.id.phone_no);
        username_reg = findViewById(R.id.username_reg);
        button_reg = findViewById(R.id.button_reg);
        have_account = findViewById(R.id.have_account);

        have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public void registerUser() {
        String userEmail = email.getText().toString().trim();
        String password = password_reg1.getText().toString().trim();
        String phone = phone_no.getText().toString().trim();
        String username = username_reg.getText().toString().trim();

        if (!validateInput()) {
            return;
        }

        // Create a new user with email and password
        mAuth.createUserWithEmailAndPassword(userEmail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Save additional user details in Firebase Realtime Database
                            assert user != null;
                            saveUserDetails(userEmail, username, phone,password);

                            // Show registration success message
                            Toast.makeText(Register.this, "Registration Successful!",
                                    Toast.LENGTH_SHORT).show();

                            // Redirect to login activity
                            startActivity(new Intent(Register.this, Login.class));
                        } else {
                            // If registration fails, display a message to the user.
                            Toast.makeText(Register.this, "Registration Failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserDetails(String email, String username, String phone, String password) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Get the current user's ID
        User newUser = new User(userId, email, username, phone, password); // Pass the userID to the User constructor
        usersRef.child(userId).setValue(newUser); // Use the userID as the key in the database
    }


    // Validate user input fields
    private boolean validateInput()
        // Your validation logic here
    {
        boolean isValid = true;
        String Email = email.getText().toString().trim();
        if (Email.length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Enter a valid email address");
            isValid = false;
        }

        String Password = password_reg1.getText().toString().trim();
        if (Password.length() == 0 || Password.length() < 8) {
            password_reg1.setError("Password must be at least 8 characters");
            isValid = false;
        }

        String Phone_n0 = phone_no.getText().toString().trim();
        if (Phone_n0.length() == 0 || Phone_n0.length() < 10) {
            phone_no.setError("Enter a valid 10-digit phone number");
            isValid = false;
        }

        String username = username_reg.getText().toString().trim();
        if (username.length() == 0 || username.length() < 3 || !username.matches("^[a-zA-Z0-9_]+$")) {
            username_reg.setError("Username must be at least 3 characters and contain only letters, numbers, and underscores");
            isValid = false;
        }


        return isValid;
    }
}
