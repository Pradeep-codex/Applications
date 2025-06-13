package com.example.nikitha;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText vehicleNumberInput;
    Button selectSlotButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vehicleNumberInput = findViewById(R.id.vehicleNumberInput);
        selectSlotButton = findViewById(R.id.selectSlotButton);

        selectSlotButton.setOnClickListener(v -> {
            String vehicleNumber = vehicleNumberInput.getText().toString().trim();

            if(vehicleNumber.isEmpty()){
                Toast.makeText(MainActivity.this, "Please enter vehicle number", Toast.LENGTH_SHORT).show();
                return;
            }

            // Start SlotActivity and pass vehicle number
            Intent intent = new Intent(MainActivity.this, SlotActivity.class);
            intent.putExtra("vehicleNumber", vehicleNumber);
            startActivity(intent);
        });
    }
}
