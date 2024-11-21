
package com.example.b07demosummer2024;


//Change to having only hash map as a field so we don't have a million fields

import static com.example.b07demosummer2024.Constants.housingData;

//constructor
public class Footprint {
    private boolean[] isAnswered;
    private final String email; //For matching each footprint with a user
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
    private double q9_1;
    private double q9_2;
    private double q9_3;
    private double q9_4;
    private double q10;
    private int q11;
    private int q12;
    private int q13;
    private int q14;
    private int q15;
    private double q16;
    private double q17;
    private double q18;
    private double q19;
    private double q20;
    private double q21;
    private double totalFood;
    private double totalTransport;
    private double totalHousing;
    private double totalConsumption;
    private double totalFootprint;

    public Footprint(String user_email) {
        this.email = user_email;
        this.isAnswered = new boolean[24];
    }

    //ADD SOME GETTERS AND SETTERS


    public void setQ1(double info) {
        this.q1 = info;
    }

    public void setQ2(double info) {
        this.q2 = info;
    }

    public void setQ3(double info) {
        this.q3 = info;
    }

    public void setQ4(double info) {
        this.q4 = info;
    }

    public void setQ5(double info) {
        this.q5 = info;
    }

    public void setQ6(double info) {
        this.q6 = info;
    }

    public void setQ7(double info) {
        this.q7 = info;
    }

    public void setQ8(double info) {
        this.q8 = info;
    }

    //Rewrite the setter function for Q9
    public void setQ9_1(double info) {
        this.q9_1 = info;
    }

    public void setQ9_2(double info) {
        this.q9_2 = info;
    }

    public void setQ9_3(double info) {
        this.q9_3 = info;
    }

    public void setQ9_4(double info) {
        this.q9_4 = info;
    }

    public void setQ10(double info) {
        this.q10 = info;
    }

    public void setQ11(int info) {
        this.q11 = info;
    }

    public void setQ12(int info) {
        this.q12 = info;
    }

    public void setQ13(int info) {
        this.q13 = info;
    }

    public void setQ14(int info) {
        this.q14 = info;
    }

    public void setQ15(int info) {
        this.q15 = info;
    }

    public void setQ16(double info) {
        this.q16 = info;
    }

    public void setQ17(double info) {
        this.q17 = info;
    }

    public void setQ18(double info) {
        this.q18 = info;
    }

    public void setQ19(double info) {
        this.q19 = info;
    }

    public void setQ20(double info) {
        this.q20 = info;
    }

    public void setQ21(double info) {
        this.q21 = info;
    }

    public void setTotalTransport() {
        if (q1 == 1) {
            totalTransport = q2 + q3 + q4 + q5 + q6 + q7;
        } else {
            totalTransport = q4 + q5 + q6 + q7;
        }
    }

    public void setTotalFood() {
        if (q8 == 0) {
            totalFood = q9_1 + q9_2 + q9_3 + q9_4 + q10;
        } else {
            totalFood = q8 + q10;
        }
    }

    public void setTotalHousing() {
        totalHousing =  housingData[q11][q12][q13][q15][q14];
        if(q16 != q14) totalHousing += 233;
    }

    public void setTotalConsumption() {
//      Regular Recycling
        if (q19 == 1) q19 = q19*0.5;
//      Occasional Recycling
        if (q19 == 2) q19 = q19*0.7;
//      Monthly buyers
//      The electronic devices are the same for every category for... some reason
        if(q18 == 360 ){
            if(q21 == 2) q18 -= 54;
            else if(q21 == 3) q18 -=108;
            else q18 -= 180;
        }
//      Quarterly Buyers, this is a guess change this maybe idk
        else if(q18 == 120){
            if(q21 == 2) {
                q18 -= 18;
                if(q20 == 300) q20 -= 45;
                else if(q20 == 600) q20 -= 60;
                else if(q20 == 900) q20 -= 90;
                else  q20 -= 120;
            }
            else if(q21 == 3) {
                q18 -= 36;
                if(q20 == 300) q20 -= 60;
                else if(q20 == 600) q20 -= 120;
                else if(q20 == 900) q20 -= 180;
                else  q20 -= 240;
            }
            else {
                q18 -= 60;
                if(q20 == 300) q20 -= 90;
                else if(q20 == 600) q20 -= 180;
                else if(q20 == 900) q20 -= 270;
                else  q20 -= 360;
            }
        }
//      For Annual Buyers
        else if(q18 == 100){
            if(q21 == 2) {
                q18 -= 15;
                if(q20 == 300) q20 -= 45;
                else if(q20 == 600) q20 -= 60;
                else if(q20 == 900) q20 -= 90;
                else  q20 -= 120;

            }
            else if(q21 == 3) {
                q18 -= 30;
                if(q20 == 300) q20 -= 60;
                else if(q20 == 600) q20 -= 120;
                else if(q20 == 900) q20 -= 180;
                else  q20 -= 240;
            }
            else {
                q18 -= 50;
                if(q20 == 300) q20 -= 90;
                else if(q20 == 600) q20 -= 180;
                else if(q20 == 900) q20 -= 270;
                else  q20 -= 360;
            }
        }
//      For rare buyers
        else{
            if(q21 == 2) {
                q18 -= 0.75;
                if(q20 == 300) q20 -= 45;
                else if(q20 == 600) q20 -= 60;
                else if(q20 == 900) q20 -= 90;
                else  q20 -= 120;
            }
            else if(q21 == 3) {
                q18 -= 1.5;
                if(q20 == 300) q20 -= 60;
                else if(q20 == 600) q20 -= 120;
                else if(q20 == 900) q20 -= 180;
                else  q20 -= 240;
            }
            else {
                q18 -= 2.5;
                if(q20 == 300) q20 -= 90;
                else if(q20 == 600) q20 -= 180;
                else if(q20 == 900) q20 -= 270;
                else  q20 -= 360;
            }
        }
        totalConsumption = q18+q19+q20;
    }

    public void setTotalFootprint(){
        totalFootprint = totalConsumption+totalTransport+totalTransport+totalFood;
    }
    public void setTrue(int i) {
        isAnswered[i] = true;
    }

    public void setFalse(int i) {
        isAnswered[i] = false;
    }

    public double getQ1() {
        return q1;
    }

    public double getQ2() {
        return q2;
    }

    public double getQ3() {
        return q3;
    }

    public double getQ4() {
        return q4;
    }

    public double getQ5() {
        return q5;
    }

    public double getQ6() {
        return q6;
    }

    public double getQ7() {
        return q7;
    }

    public double getQ8() {
        return q8;
    }

    public double getQ9_1() {
        return q9_1;
    }

    public double getQ9_2() {
        return q9_2;
    }

    public double getQ9_3() {
        return q9_3;
    }

    public double getQ_4() {
        return q9_4;
    }

    public double getQ10() {
        return q10;
    }

    public double getQ11() {
        return q11;
    }

    public double getQ12() {
        return q12;
    }

    public double getQ13() {
        return q13;
    }

    public double getQ14() {
        return q14;
    }

    public double getQ15() {
        return q15;
    }

    public double getQ16() {
        return q16;
    }

    public double getQ17() {
        return q17;
    }

    public double getQ18() {
        return q18;
    }

    public double getQ19() {
        return q19;
    }

    public double getQ20() {
        return q20;
    }

    public double getQ21() {
        return q21;
    }

    public double getTotalFood() {
        return totalFood;
    }

    public double getTotalTransport() {
        return totalTransport;
    }

    public double getTotalHousing() {
        return totalHousing;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }
    public double getTotalFootprint(){
        return totalFootprint;
    }

    public boolean getAnswer(int i) {
        return isAnswered[i];
    }


//    TODO add the final thing to the db
//    public void addToDB(){
//        User user;
//        footprint fp = new footprint(user);
//        DatabaseReference ref =
//                FirebaseDatabase.getInstance("https://DATABASE_NAME.firebaseio.com").getReference();
//        ref.child("footprints").child("s1").setValue(fp);
//    }


//    This project is so fucking stupid. Hard coding cases makes my brain hurt
}