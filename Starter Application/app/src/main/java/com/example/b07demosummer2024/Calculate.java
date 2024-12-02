package com.example.b07demosummer2024;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;

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

                for (DataSnapshot activitySnapshot : dailyLogs.getChildren()) {
                    String activityType = activitySnapshot.child("activity_type").getValue(String.class);
                    Map<String, Object> information = (Map<String, Object>) activitySnapshot.child("information").getValue();

                    if (activityType != null && information != null) {
                        totalEmissions += calculateActivityEmissions(activityType, information);
                    }
                }

                dailyLogsRef.child("total_emissions").setValue(totalEmissions);
            }
        });
    }

    private static double calculateActivityEmissions(String activityType, Map<String, Object> information) {
        double totalEmissions = 0.0;

        for (Map.Entry<String, Object> entry : information.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Number) {
                double numericValue = ((Number) value).doubleValue();
                double factor = EMISSION_FACTORS.getOrDefault(key.toLowerCase(), 0.0);
                totalEmissions += numericValue * factor;
            }
        }

        return totalEmissions;
    }
}
