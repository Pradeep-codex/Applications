package com.example.nikitha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class SlotActivity extends AppCompatActivity {

    private GridLayout slotsGrid;
    private Button backButton;

    private Map<Integer, String> bookedSlots = new HashMap<>();
    private String currentVehicleNumber;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "ParkingAppPrefs";
    private static final String BOOKED_SLOTS_KEY = "bookedSlots";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot);

        slotsGrid = findViewById(R.id.slotsGrid);
        backButton = findViewById(R.id.backButton);

        // Get the vehicle number passed from MainActivity
        Intent intent = getIntent();
        currentVehicleNumber = intent.getStringExtra("vehicleNumber");

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        loadBookings();

        createSlots();

        backButton.setOnClickListener(v -> finish());
    }

    private void createSlots() {
        slotsGrid.removeAllViews();

        int totalSlots = 10;
        for (int i = 1; i <= totalSlots; i++) {
            TextView slot = new TextView(this);

            // Layout params to make slots look good
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(16, 16, 16, 16);

            slot.setLayoutParams(params);
            slot.setPadding(40, 40, 40, 40);
            slot.setTextSize(18f);
            slot.setTextColor(Color.WHITE);
            slot.setBackgroundColor(Color.parseColor("#4CAF50")); // Green by default
            slot.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            slot.setText("Slot " + i);

            // If booked, mark red and show vehicle number
            if (bookedSlots.containsKey(i)) {
                slot.setBackgroundColor(Color.RED);
                slot.setText("Slot " + i + "\n" + bookedSlots.get(i));
            }

            int slotNumber = i;
            slot.setOnClickListener(v -> onSlotClicked(slotNumber));

            slotsGrid.addView(slot);
        }
    }

    private void onSlotClicked(int slotNumber) {
        // Check if current vehicle is already booked in any slot
        boolean vehicleAlreadyBooked = false;
        int bookedSlotForVehicle = -1;

        for (Map.Entry<Integer, String> entry : bookedSlots.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(currentVehicleNumber)) {
                vehicleAlreadyBooked = true;
                bookedSlotForVehicle = entry.getKey();
                break;
            }
        }

        if (vehicleAlreadyBooked) {
            if (bookedSlotForVehicle == slotNumber) {
                // If clicked slot is the same slot vehicle already booked, allow unbooking (optional)
                bookedSlots.remove(slotNumber);
                Toast.makeText(this, "Slot " + slotNumber + " unbooked.", Toast.LENGTH_SHORT).show();
            } else {
                // Vehicle already booked in another slot
                Toast.makeText(this, "This vehicle is already booked in Slot " + bookedSlotForVehicle, Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            // If slot is already booked by another vehicle, prevent booking
            if (bookedSlots.containsKey(slotNumber)) {
                Toast.makeText(this, "Slot " + slotNumber + " is already booked.", Toast.LENGTH_SHORT).show();
                return;
            }
            // Book this slot
            bookedSlots.put(slotNumber, currentVehicleNumber);
            Toast.makeText(this, "Slot " + slotNumber + " booked for " + currentVehicleNumber, Toast.LENGTH_SHORT).show();
        }

        saveBookings();
        createSlots();
    }

    private void saveBookings() {
        // Save bookedSlots map as a single string in SharedPreferences: "slot:vehicle,slot:vehicle,..."
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, String> entry : bookedSlots.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        }
        sharedPreferences.edit().putString(BOOKED_SLOTS_KEY, sb.toString()).apply();
    }

    private void loadBookings() {
        bookedSlots.clear();
        String savedData = sharedPreferences.getString(BOOKED_SLOTS_KEY, "");
        if (!savedData.isEmpty()) {
            String[] pairs = savedData.split(",");
            for (String pair : pairs) {
                if (!pair.trim().isEmpty() && pair.contains(":")) {
                    String[] parts = pair.split(":");
                    try {
                        int slotNum = Integer.parseInt(parts[0]);
                        String vehicleNum = parts[1];
                        bookedSlots.put(slotNum, vehicleNum);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
