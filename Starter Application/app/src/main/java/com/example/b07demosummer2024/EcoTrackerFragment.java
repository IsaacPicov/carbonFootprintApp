package com.example.b07demosummer2024;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EcoTrackerFragment extends Fragment {

    private Button buttonTransportation, buttonFoodConsumption, buttonConsumptionAndShopping, buttonCO2eDisplay, buttonEcogauge, buttonHabitTracker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ecotracker, container, false);
        buttonEcogauge = view.findViewById(R.id.button11);
        buttonTransportation = view.findViewById(R.id.buttonTransportation);
        buttonFoodConsumption = view.findViewById(R.id.buttonFoodConsumption);
        buttonConsumptionAndShopping= view.findViewById(R.id.buttonConsumptionAndShopping);
        buttonCO2eDisplay = view.findViewById(R.id.buttonDailyCO2eDisplay);
        buttonHabitTracker = view.findViewById(R.id.buttonHabitTracker);
        buttonTransportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new TransportationActivitiesFragment());
            }
        });

        buttonFoodConsumption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new FoodConsumptionFragment());
            }
        });

        buttonConsumptionAndShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ConsumptionActivitiesFragment());
            }
        });

        buttonHabitTracker.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HabitTrackerFragment());
            }
        }));

        buttonCO2eDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new CO2eDisplayFragment());
            }
        });

        buttonEcogauge.setOnClickListener(v -> {
            // Create an Intent to start the target activity
            Intent intent = new Intent(getActivity(), EcoGaugeActivity.class);
            // Optional: Add data to the intent
            intent.putExtra("EXTRA_MESSAGE", "Hello from Fragment!");
            startActivity(intent);
        });
        return view;
    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}