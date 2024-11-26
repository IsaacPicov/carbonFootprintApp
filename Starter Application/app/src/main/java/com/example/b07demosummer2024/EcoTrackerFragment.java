package com.example.b07demosummer2024;

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

    private Button buttonTransportation, buttonFoodConsumption, buttonConsumptionAndShopping, buttonCO2eDisplay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ecotracker, container, false);

        buttonTransportation = view.findViewById(R.id.buttonTransportation);
        buttonFoodConsumption = view.findViewById(R.id.buttonFoodConsumption);
        buttonConsumptionAndShopping= view.findViewById(R.id.buttonConsumptionAndShopping);
        buttonCO2eDisplay = view.findViewById(R.id.buttonDailyCO2eDisplay);

        buttonTransportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new TransportationActivitiesFragment());
            }
        });

        buttonFoodConsumption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new RecyclerViewFragment());
            }
        });

        buttonConsumptionAndShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new RecyclerViewFragment());
            }
        });

        buttonCO2eDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new CO2eDisplayFragment());
            }
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
