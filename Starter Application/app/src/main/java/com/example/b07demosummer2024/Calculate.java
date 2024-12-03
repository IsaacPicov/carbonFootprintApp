package com.example.b07demosummer2024;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;

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
        EMISSION_FACTORS.put("Clothes", 5.5); // Example factor for clothes
        EMISSION_FACTORS.put("Miscellaneous", 10.0); // Example for all misc purchases
    }

    public static void calculateAndUpdateDailyTotal(String userId, String selectedDate) {
        DatabaseReference dailyLogsRef = db.getReference("users/" + userId + "/dailylogs/" + selectedDate);
        dailyLogsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DataSnapshot dailyLogs = task.getResult();

                double transportationTotal = calculateCategoryTotal(dailyLogs, "transportation");
                double foodTotal = calculateCategoryTotal(dailyLogs, "food");
                double consumptionTotal = calculateCategoryTotal(dailyLogs, "consumption");
                double billsTotal = calculateCategoryTotal(dailyLogs, "energy");

                double totalEmissions = transportationTotal + foodTotal + consumptionTotal + billsTotal;

                dailyLogsRef.child("total_emissions").setValue(totalEmissions);
                dailyLogsRef.child("emissions_by_category").setValue(Map.of(
                        "transportation", transportationTotal,
                        "food", foodTotal,
                        "consumption", consumptionTotal,
                        "bills", billsTotal
                ));
            }
        });
    }

    private static double calculateActivityEmissions(String activityType, Map<String, Object> information) {
        double totalEmissions = 0.0;

        switch (activityType) {
            case "transportation":
                if (information.containsKey("distance")) {
                    double distance = parseToDouble(information.get("distance"));
                    double factor = EMISSION_FACTORS.getOrDefault(information.get("vehicleType").toString().toLowerCase(), 0.0);
                    totalEmissions += distance * factor;
                } else if (information.containsKey("duration")) {
                    double duration = parseToDouble(information.get("duration"));
                    double factor = EMISSION_FACTORS.getOrDefault(information.get("transportType").toString().toLowerCase(), 0.0);
                    totalEmissions += duration * factor;
                } else if (information.containsKey("numberOfFlights")) {
                    double flights = parseToDouble(information.get("numberOfFlights"));
                    double factor = EMISSION_FACTORS.getOrDefault(information.get("flightType").toString().toLowerCase(), 0.0);
                    totalEmissions += flights * factor;
                }
                break;

            case "food":
                if (information.containsKey("mealType")) {
                    double servings = parseToDouble(information.get("servings"));
                    double factor = EMISSION_FACTORS.getOrDefault(information.get("mealType").toString().toLowerCase(), 0.0);
                    totalEmissions += servings * factor;
                }
                break;

            case "consumption":
                if (information.containsKey("quantity")) {
                    double quantity = parseToDouble(information.get("quantity"));
                    double factor = EMISSION_FACTORS.getOrDefault(information.getOrDefault("itemType", "Miscellaneous").toString(), 0.0);
                    totalEmissions += quantity * factor;
                }
                break;

            case "energy":
                if (information.containsKey("amount")) {
                    double amount = parseToDouble(information.get("amount"));
                    double factor = EMISSION_FACTORS.getOrDefault(information.get("billType").toString().toLowerCase(), 0.0);
                    totalEmissions += amount * factor;
                }
                break;
        }
        return totalEmissions;
    }

    private static double calculateCategoryTotal(DataSnapshot dailyLogs, String category) {
        double totalEmissions = 0.0;

        for (DataSnapshot activitySnapshot : dailyLogs.getChildren()) {
            String activityType = activitySnapshot.child("activity_type").getValue(String.class);
            if (activityType != null && activityType.equalsIgnoreCase(category)) {
                Map<String, Object> information = (Map<String, Object>) activitySnapshot.child("information").getValue();
                if (information != null) {
                    totalEmissions += calculateActivityEmissions(activityType, information);
                }
            }
        }
        return totalEmissions;
    }

    private static double parseToDouble(Object value) {
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + value);
            return 0.0;
        }
    }
}