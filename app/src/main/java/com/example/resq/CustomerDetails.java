package com.example.resq;

public class CustomerDetails {
    public String name;
    public String age;
    public String address;
    public String bloodGrp;
    public String relative1;
    public String relative2;
    public String relative3;

    public CustomerDetails() {
    }

    public CustomerDetails(String name, String age, String address, String bloodGrp, String relative1, String relative2, String relative3) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.bloodGrp = bloodGrp;
        this.relative1 = relative1;
        this.relative2 = relative2;
        this.relative3 = relative3;
    }
}
