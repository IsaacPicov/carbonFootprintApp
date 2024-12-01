package com.example.b07demosummer2024;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Calculate {

    private static final FirebaseDatabase db = FirebaseDatabase.getInstance("https://b07finalproject-4e3be-default-rtdb.firebaseio.com/");
    private static final Map<String, Double> EMISSION_FACTORS = new HashMap<>();

    static {
        EMISSION_FACTORS.put("gasoline", 0.24);
        EMISSION_FACTORS.put("electric", 0.1);
        EMISSION_FACTORS.put("diesel", 0.27);
        EMISSION_FACTORS.put("bus", 1.2);
        EMISSION_FACTORS.put("train", 0.6);
        EMISSION_FACTORS.put("subway", 0.4);
        EMISSION_FACTORS.put("shorthaul", 150.0);
        EMISSION_FACTORS.put("mediumhaul", 300.0);
        EMISSION_FACTORS.put("longhaul", 600.0);
        EMISSION_FACTORS.put("beef", 27.0);
        EMISSION_FACTORS.put("plantbased", 2.0);
        EMISSION_FACTORS.put("chicken", 6.9);
        EMISSION_FACTORS.put("tshirt", 5.5);
        EMISSION_FACTORS.put("jeans", 33.4);
        EMISSION_FACTORS.put("shoes", 14.0);
        EMISSION_FACTORS.put("laptop", 200.0);
        EMISSION_FACTORS.put("smartphone", 80.0);
        EMISSION_FACTORS.put("tablet", 120.0);
        EMISSION_FACTORS.put("electricity", 0.6);
        EMISSION_FACTORS.put("water", 0.2);
        EMISSION_FACTORS.put("gas", 2.3);
    }

    public static void calculateAndUpdateDailyTotal(String userId) {
        DatabaseReference dailyLogsRef = db.getReference("users/" + userId + "/dailyLogs/" + LocalDate.now());
        dailyLogsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DataSnapshot dailyLogs = task.getResult();
                double totalEmissions = 0.0;

                for (String type : EMISSION_FACTORS.keySet()) {
                    double factor = EMISSION_FACTORS.get(type);
                    double quantity = getQuantityForType(dailyLogs, type);
                    totalEmissions += factor * quantity;
                }

                dailyLogsRef.child("total_emissions").setValue(totalEmissions);
            }
        });
    }

    private static double getQuantityForType(DataSnapshot dailyLogs, String type) {
        if (type.equalsIgnoreCase("gasoline")) {
            DataSnapshot vehicle = dailyLogs.child("transportation/vehicle");
            return vehicle.child("distance").getValue(Double.class) != null ? vehicle.child("distance").getValue(Double.class) : 0.0;
        }
        if (type.equalsIgnoreCase("bus") || type.equalsIgnoreCase("train") || type.equalsIgnoreCase("subway")) {
            DataSnapshot publicTransport = dailyLogs.child("transportation/publicTransport");
            return publicTransport.child("hours").getValue(Double.class) != null ? publicTransport.child("hours").getValue(Double.class) : 0.0;
        }
        if (type.equalsIgnoreCase("shorthaul") || type.equalsIgnoreCase("mediumhaul") || type.equalsIgnoreCase("longhaul")) {
            DataSnapshot flights = dailyLogs.child("transportation/flights");
            return flights.child(type).getValue(Double.class) != null ? flights.child(type).getValue(Double.class) : 0.0;
        }
        if (type.equalsIgnoreCase("beef") || type.equalsIgnoreCase("plantbased") || type.equalsIgnoreCase("chicken")) {
            DataSnapshot meals = dailyLogs.child("food/meals");
            return meals.child(type).getValue(Double.class) != null ? meals.child(type).getValue(Double.class) : 0.0;
        }
        if (type.equalsIgnoreCase("electricity") || type.equalsIgnoreCase("water") || type.equalsIgnoreCase("gas")) {
            DataSnapshot energyBills = dailyLogs.child("energyBills");
            return energyBills.child(type).getValue(Double.class) != null ? energyBills.child(type).getValue(Double.class) : 0.0;
        }
        if (type.equalsIgnoreCase("tshirt") || type.equalsIgnoreCase("jeans") || type.equalsIgnoreCase("shoes")) {
            DataSnapshot shopping = dailyLogs.child("shopping");
            return shopping.child(type).getValue(Double.class) != null ? shopping.child(type).getValue(Double.class) : 0.0;
        }
        return 0.0;
    }
}
