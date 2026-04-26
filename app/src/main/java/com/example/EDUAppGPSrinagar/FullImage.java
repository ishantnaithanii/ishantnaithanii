package com.example.EDUAppGPSrinagar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ablanco.zoomy.TapListener;
import com.ablanco.zoomy.Zoomy;
import com.bumptech.glide.Glide;

public class FullImage extends AppCompatActivity {
    private ImageView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        String image = getIntent().getStringExtra("image");
        imageView = findViewById(R.id.imageViewFull);
        Glide.with(this).load(image).into(imageView);
        Zoomy.Builder builder=new Zoomy.Builder(this)
                .target(imageView)
                .animateZooming(false)
                .enableImmersiveMode(false)
                .tapListener(new TapListener() {
                    @Override
                    public void onTap(View v) {
                        Toast.makeText(FullImage.this, "Clicked", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.register();
    }
}
