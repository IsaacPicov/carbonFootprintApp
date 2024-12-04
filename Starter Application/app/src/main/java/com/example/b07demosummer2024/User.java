package com.example.b07demosummer2024;

public class User {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private int takenSurvey;

    public User() {}

    public User(String userId, String firstName, String lastName, String email, int takenSurvey) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.takenSurvey = takenSurvey;
    }

    // Getters and setters
    public String getId() { return userId; }
    public void setId(String id) { this.userId = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getTakenSurvey() { return takenSurvey; }
    public void setTakenSurvey(int takenSurvey) { this.takenSurvey = takenSurvey; }
}
