package com.example.EDUAppGPSrinagar.Login_and_Register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.EDUAppGPSrinagar.Login_and_Register.Teacher.Teacher_Register;
import com.example.EDUAppGPSrinagar.R;

public class Login_Register extends AppCompatActivity {
    Button teacherRegBtn,studentRegBtn;
    private TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        teacherRegBtn =findViewById(R.id.teacherRegBtn);
        studentRegBtn =findViewById(R.id.studentRegBtn);

//        TextView animatedText = findViewById(R.id.text1);
//        Animation rollRightAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_right_to_left);
//        animatedText.startAnimation(rollRightAnimation);

//        @SuppressLint("WrongViewCast") ImageView imageView2=findViewById(R.id.text2);
//        Animation rollRightAnimation2 = AnimationUtils.loadAnimation(this, R.anim.alpha);
//        imageView2.startAnimation(rollRightAnimation2);

        teacherRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Register.this, Teacher_Register.class));
            }
        });
        studentRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Register.this, Register.class));
            }
        });
    }
}