package com.example.srusti;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.widget.Button;  // Import Button

public class FullImageActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    Button closeButton;  // Change to Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        viewPager = findViewById(R.id.fullImageView);
        closeButton = findViewById(R.id.closeBtn);

        int[] images = getIntent().getIntArrayExtra("imageArray");
        int position = getIntent().getIntExtra("position", 0);

        closeButton.setOnClickListener(v -> finish());

        if (images != null && images.length > 0) {
            FullImageAdapter adapter = new FullImageAdapter(images, this);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(position, false);
        }
    }
}
