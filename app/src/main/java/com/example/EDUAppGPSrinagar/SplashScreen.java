package com.example.EDUAppGPSrinagar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.EDUAppGPSrinagar.Login_and_Register.Login_Register;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView imageView=findViewById(R.id.splash);
        Animation rollRightAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_right_to_center);
        imageView.startAnimation(rollRightAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashScreen.this, Login_Register.class);
                startActivity(i);
                finish();
            }
        },2000);

    }
}