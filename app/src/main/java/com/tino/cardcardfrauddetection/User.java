package com.tino.cardcardfrauddetection;

/**
 * Created by ishaandhamija on 15/11/17.
 */

public class User {

    String cardNumber;
    String password;
    String email;
    String contactNo;
    String name;

    public User(String cardNumber, String password, String email, String contactNo, String name) {
        this.cardNumber = cardNumber;
        this.password = password;
        this.email = email;
        this.contactNo = contactNo;
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getName() {
        return name;
    }
}