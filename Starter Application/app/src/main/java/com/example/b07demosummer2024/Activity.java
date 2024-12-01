package com.example.b07demosummer2024;

import java.util.HashMap;
import java.util.Map;

public class Activity {

    private String id;

    private String activity_type;
    private String information;

    private Map<String, Object> information_map;

    public Activity() {}

    public Activity(String id, String activity_type, String information) {
        this.id = id;
        this.activity_type = activity_type;
        this.information = information;
    }

    public String getId() {
        return id;
    }

    public void setId(String activity_type) {
        this.id = id;
    }

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }

    public String getInformation() {
        return information.toString();
    }

    public Map<String, Object> getInformationMap() {
        if (information_map == null) {
            information_map = new HashMap<>();
            if (information != null) {
                String[] pairs = information.split(",");
                for (String pair : pairs) {
                    String[] keyValue = pair.split(":");
                    if (keyValue.length == 2) {
                        information_map.put(keyValue[0].trim(), keyValue[1].trim());
                    }
                }
            }
        }
        return information_map;
    }

    public Map<String, String> getInformationAsMap() {
        Map<String, String> formattedInformation = new HashMap<>();

        if (getInformationMap() != null) {
            for (Map.Entry<String, Object> entry : getInformationMap().entrySet()) {
                formattedInformation.put(entry.getKey(), entry.getValue().toString());
            }
        }

        return formattedInformation;
    }

    public void setInformation(String information) {
        this.information = information;
        this.information_map = null;
    }
}
