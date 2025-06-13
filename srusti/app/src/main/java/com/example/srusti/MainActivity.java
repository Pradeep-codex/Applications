package com.example.srusti;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    Spinner designSpinner;
    String[] designTypes = {"Select a design", "Arabic", "Indian", "Bridal", "Simple", "Modern"};

    boolean userIsInteracting = false;  // Track if user has interacted

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        designSpinner = findViewById(R.id.designSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, designTypes) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.spinner_item, parent, false);
                }
                TextView tv = convertView.findViewById(R.id.spinner_item_text);
                tv.setText(getItem(position));
                return convertView;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.spinner_item, parent, false);
                }
                TextView tv = convertView.findViewById(R.id.spinner_item_text);
                tv.setText(getItem(position));
                return convertView;
            }
        };

        designSpinner.setAdapter(adapter);

        designSpinner.setOnTouchListener((v, event) -> {
            userIsInteracting = true;  // user touched spinner, now react to selection
            return false;  // let spinner handle the touch event normally
        });

        designSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userIsInteracting) {
                    String selected = designTypes[position];
                    Intent intent = new Intent(MainActivity.this, DesignActivity.class);
                    intent.putExtra("designType", selected);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }
}
