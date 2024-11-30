package com.example.b07demosummer2024;

import java.util.HashMap;

public class Activity {

    private String activity_type;
    private String information;

    public Activity() {}

    public Activity(String activity_type, String information) {
        this.activity_type = activity_type;
        this.information = information;
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

    public void setInformation(String information) {
        this.information = information;
    }
}