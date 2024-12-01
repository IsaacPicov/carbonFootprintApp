package com.example.b07demosummer2024;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ConsumptionActivitiesFragment extends Fragment{


    private Button buttonClothes, buttonElectronics, buttonMiscellaneous, buttonBills;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumption_activities, container, false);

        buttonClothes = view.findViewById(R.id.buttonClothes);
        buttonElectronics = view.findViewById(R.id.buttonElectronics);
        buttonMiscellaneous = view.findViewById(R.id.buttonMiscellaneous);
        buttonBills = view.findViewById(R.id.buttonBills);
//
        buttonClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ClothesFragment());
            }
        });
//
        buttonElectronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ElectronicsFragment());
            }
        });
//
        buttonMiscellaneous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadFragment(new MiscellaneousFragment()); }
        });

        buttonBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadFragment(new BillsFragment()); }
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
