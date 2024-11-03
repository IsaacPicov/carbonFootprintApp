package com.example.b07demosummer2024;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Change to having only hash map as a field so we don't have a million fields
public class footprint {
    private boolean q1;
    private String q2;
    private int q3;
    private String q4;
    private String q5;
    private String q6;
    private String q7;
    private String q8;
    //Question 9 only pops up if the user selects that they eat meat
    //Question 9 will be stored as an array of answers
    private String[] q9;

    private String q10;
    private String q11;
    private int q12;
    private int q13;
    private String q14;
    private int q15;
    private String q16;
    private String q17;
    private String q18;
    private String q19;
    private String q20;
    private String q21;

    public footprint(){}

    //ADD SOME GETTERS AND SETTERS
    public void addToDB(){
        footprint fp = new footprint();
        DatabaseReference ref =
                FirebaseDatabase.getInstance("https://DATABASE_NAME.firebaseio.com").getReference();
        ref.child("footprints").child("s1").setValue(fp);
    }

    public void onCreate(){

    }



}
