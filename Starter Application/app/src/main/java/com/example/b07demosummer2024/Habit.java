package com.example.b07demosummer2024;

public class Habit {
    private String title;
    private String type;
    //Obligatory empty constructor lol
    public Habit(){

    }

    public Habit(String title, String type){
        this.title = title;
        this.type = type;
    }
    //Some getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
