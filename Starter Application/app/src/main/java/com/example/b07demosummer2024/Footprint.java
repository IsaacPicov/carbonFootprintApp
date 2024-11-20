
package com.example.b07demosummer2024;


//Change to having only hash map as a field so we don't have a million fields

//constructor
public class Footprint {
    private final boolean [] isAnswered;
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

    public Footprint( String user_email){
        this.email = user_email;
        this.isAnswered = new boolean[24];
    }

    //ADD SOME GETTERS AND SETTERS


    public void setQ1(double info){
        this.q1 = info;
    }
    public void setQ2(double info){
        this.q2 = info;
    }
    public void setQ3(double info){
        this.q3 = info;
    }
    public void setQ4(double info){
        this.q4 = info;
    }
    public void setQ5(double info){
        this.q5 = info;
    }
    public void setQ6(double info){
        this.q6 = info;
    }
    public void setQ7(double info){
        this.q7 = info;
    }
    public void setQ8(double info){
        this.q8 = info;
    }
    //Rewrite the setter function for Q9
    public void setQ9_1(double info){this.q9_1 = info;}
    public void setQ9_2(double info){this.q9_2 = info;}
    public void setQ9_3(double info){this.q9_3 = info;}
    public void setQ9_4(double info){this.q9_4 = info;}
    public void setQ10(double info){
        this.q10 = info;
    }

    public void setQ11(double info){
        this.q12 = info;
    }

    public void setQ12(double info){
        this.q12 = info;
    }

    public void setQ13(double info){
        this.q13 = info;
    }

    public void setQ14(double info){
        this.q14 = info;
    }

    public void setQ15(double info){
        this.q15 = info;
    }

    public void setQ16(double info){
        this.q16 = info;
    }

    public void setQ17(double info){
        this.q17 = info;
    }

    public void setQ18(double info){
        this.q18 = info;
    }

    public void setQ19(double info){
        this.q19 = info;
    }

    public void setQ20(double info){
        this.q20 = info;
    }

    public void setQ21(double info){
        this.q21 = info;
    }

    public void setTrue(int i){
        isAnswered[i] = true;
    }
    public void setFalse(int i){
        isAnswered[i] = false;
    }
    public double getQ1(){
        return q1;
    }
    public double getQ2(){
        return q2;
    }
    public double getQ3(){
        return q3;
    }
    public double getQ4(){
        return q4;
    }
    public double getQ5(){
        return q5;
    }
    public double getQ6(){
        return q6;
    }
    public double getQ7(){
        return q7;
    }
    public double getQ8(){
        return q8;
    }

//    public double getQ9(){
//        return q9;
//    }
    public double getQ10(){
        return q10;
    }
    public double getQ11(){
        return q11;
    }
    public double getQ12(){
        return q12;
    }
    public double getQ13(){
        return q13;
    }
    public double getQ14(){
        return q14;
    }
    public double getQ15(){
        return q15;
    }
    public double getQ16(){
        return q16;
    }
    public double getQ17(){
        return q17;
    }
    public double getQ18(){
        return q18;
    }
    public double getQ19(){
        return q19;
    }
    public double getQ20(){
        return q20;
    }
    public double getQ21(){
        return q21;
    }
    public boolean getAnswer(int i){
        return isAnswered[i];
    }
//    public void addToDB(){
//        User user;
//        footprint fp = new footprint(user);
//        DatabaseReference ref =
//                FirebaseDatabase.getInstance("https://DATABASE_NAME.firebaseio.com").getReference();
//        ref.child("footprints").child("s1").setValue(fp);
//    }



}
