package com.example.b07demosummer2024;

import java.util.HashMap;
import java.util.Map;

public class Activity {

    private String id;
    private String activity_type;
    private Map<String, Object> information;

    public Activity() {}

    public Activity(String id, String activity_type, Map<String, Object> information) {
        this.id = id;
        this.activity_type = activity_type;
        this.information = information;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }

    public Map<String, Object> getInformation() {
        return information;
    }

    public void setInformation(Map<String, Object> information) {
        this.information = information;
    }

    public Map<String, String> getInformationAsMap() {
        Map<String, String> formattedInformation = new HashMap<>();

        if (information != null) {
            for (Map.Entry<String, Object> entry : information.entrySet()) {
                formattedInformation.put(entry.getKey(), entry.getValue().toString());
            }
        }

        return formattedInformation;
    }
}