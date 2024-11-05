package com.example.b07demosummer2024;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Change to having only hash map as a field so we don't have a million fields

//constructor
public class footprint {
    private int email; //For matching each footprint with a user
    private double q1;
    private double q2;
    private double q3;
    private double q4;
    private double q5;
    private double q6;
    private double q7;
    private double q8;
    //Question 9 only pops up if the user selects that they eat meat
    //Question 9 will be stored as an array of answers
    private double[] q9;

    private double q10;
    private double q11;
    private double q12;
    private double q13;
    private double q14;
    private double q15;
    private double q16;
    private double q17;
    private double q18;
    private double q19;
    private double q20;
    private double q21;

    public footprint(User user){
        this.email = user.email;
    }

    //ADD SOME GETTERS AND SETTERS

    public void setQ1(double info){
        this.s1 = info;
    }
    public void setQ2(double info){
        this.s2(); = info;
    }
    public void setQ3(double info){
        this.s3 = info;
    }
    public void setQ4(double info){
        this.s4 = info;
    }
    public void setQ5(double info){
        this.s5 = info;
    }
    public void setQ6(double info){
        this.s6 = info;
    }
    public void setQ7(double info){
        this.s7 = info;
    }
    public void setQ8(double info){
        this.s8 = info;
    }
    //Rewrite the setter function for Q9
    public void setQ9(double[] info){
        for (int i = 0; i < info.length; i++) {
            this.s9[i] = info[i];
        }
    }
    public void setQ10(double info){
        this.s10 = info;
    }
    public void setQ11(double info){
        this.s12 = info;
    }
    public void setQ12(double info){
        this.s12 = info;
    }
    public void setQ13(double info){
        this.s13 = info;
    }
    public void setQ14(double info){
        this.s14 = info;
    }
    public void setQ15(double info){
        this.s15 = info;
    }
    public void setQ16(double info){
        this.s16 = info;
    }
    public void setQ17(double info){
        this.s17 = info;
    }
    public void setQ18(double info){
        this.s18 = info;
    }
    public void setQ19(double info){
        this.s19 = info;
    }
    public void setQ20(double info){
        this.s20 = info;
    }
    public void setQ21(double info){
        this.s21 = info;
    }
    public void addToDB(){
        footprint fp = new footprint();
        DatabaseReference ref =
                FirebaseDatabase.getInstance("https://DATABASE_NAME.firebaseio.com").getReference();
        ref.child("footprints").child("s1").setValue(fp);
    }

    public void onCreate(){

    }



}
