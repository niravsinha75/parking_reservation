package com.example.parkig_reservation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextView signup, login_as_admin;
    EditText email_2, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_2 = findViewById(R.id.email_2);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        login_as_admin = findViewById(R.id.login_as_admin);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        login_as_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Admin_login.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_log = email_2.getText().toString().trim();
                String password_log = password.getText().toString().trim();

                if (!isValidEmail(email_log)) {
                    email_2.setError("Enter a valid Email");
                    return;
                }

                if (!isValidPassword(password_log)) {
                    password.setError("Password must be at least 8 characters");
                    return;
                }

                // Proceed with authentication
                authenticateUser(email_log, password_log);
            }
        });
    }

    private boolean isValidEmail(String email_2) {
        return !TextUtils.isEmpty(email_2) && Patterns.EMAIL_ADDRESS.matcher(email_2).matches();
    }


    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    private void authenticateUser(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId = user.getUid(); // Get the user ID
                            Toast.makeText(Login.this, "Authentication successful.",
                                    Toast.LENGTH_SHORT).show();
                            // Save the logged-in status and username
                            MyPreferences preferences = new MyPreferences(Login.this);
                            preferences.setUserId(userId);


                            // Proceed to your main activity or any other action
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Finish login activity
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder exitdialog = new AlertDialog.Builder(this);

        exitdialog.setTitle("Exit?");
        exitdialog.setIcon(R.drawable.baseline_exit_to_app_24);
        exitdialog.setMessage("Are you sure you want to exit the App? ");
        exitdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Login.super.onBackPressed();
            }
        });
        exitdialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });

        exitdialog.show();
    }

}
