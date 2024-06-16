package com.example.parkig_reservation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Find the ImageView you want to animate
        ImageView logoImageView = findViewById(R.id.imageView);

        // Load the animation
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Set the animation to the ImageView
        logoImageView.startAnimation(fadeInAnimation);

        // Set up a listener to handle the end of the animation
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
                        Boolean check = pref.getBoolean("flag",false);

                        Intent iNext;
                        if(check){ // for true(User is logged in)
                            iNext = new Intent(getApplicationContext(),MainActivity.class);
                        }
                        else{   // for false(Either first time or user is logged out)
                            iNext = new Intent(getApplicationContext(),Login.class);
                        }
                        startActivity(iNext);
                    }
                },1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeated
            }
        });

    }
}