package com.example.srusti;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DesignActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton backButton;

    int[] arabicImages = {
            R.drawable.arabic_1, R.drawable.arabic_2, R.drawable.arabic_3,
            R.drawable.arabic_4, R.drawable.arabic_5, R.drawable.arabic_6,
            R.drawable.arabic_7, R.drawable.arabic_8
    };

    int[] indianImages = {
            R.drawable.indian_1, R.drawable.indian_2, R.drawable.indian_7,
            R.drawable.indian_1, R.drawable.indian_6, R.drawable.indian_7,
            R.drawable.indian_8
    };

    int[] bridalImages = {
            R.drawable.bridal_1, R.drawable.bridal_5, R.drawable.bridal_3,
            R.drawable.bridal_4, R.drawable.bridal_1, R.drawable.bridal_3,
            R.drawable.bridal_7, R.drawable.bridal_8
    };

    int[] simpleImages = {
            R.drawable.arabic_1, R.drawable.arabic_2, R.drawable.arabic_3,
            R.drawable.arabic_4, R.drawable.arabic_5, R.drawable.arabic_6,
            R.drawable.arabic_7, R.drawable.arabic_8
    };

    int[] modernImages = {
            R.drawable.indian_1, R.drawable.indian_2, R.drawable.indian_7,
            R.drawable.indian_1, R.drawable.indian_6, R.drawable.indian_7,
            R.drawable.indian_8
    };

    int[] imagesToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        recyclerView = findViewById(R.id.designRecyclerView);
        backButton = findViewById(R.id.backButton);

        // Get selected design type
        Intent intent = getIntent();
        String designType = intent.getStringExtra("designType");

        // Choose image set
        switch (designType) {
            case "Arabic":
                imagesToShow = arabicImages;
                break;
            case "Indian":
                imagesToShow = indianImages;
                break;
            case "Bridal":
                imagesToShow = bridalImages;
                break;
            case "Simple":
                imagesToShow = simpleImages;
                break;
            case "Modern":
                imagesToShow = modernImages;
                break;
            default:
                imagesToShow = new int[]{};
        }

        // Set adapter
        DesignAdapter adapter = new DesignAdapter(this, imagesToShow, position -> {
            Intent fullScreenIntent = new Intent(DesignActivity.this, FullImageActivity.class);
            fullScreenIntent.putExtra("imageArray", imagesToShow);
            fullScreenIntent.putExtra("position", position);
            startActivity(fullScreenIntent);
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());
    }
}
