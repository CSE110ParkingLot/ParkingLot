package com.example.linning.loginregister;

/**
 * Created by linning on 10/24/15.
 */
public class User {

    //instance variables. Information that each user should have.
    String name, username, password;
    int phone;

    /* Constructor, sets instance variables on creation of new user object.
     * To be called when a user registers a new account
     * */
    public User(String name, int phone, String username, String password){
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    /* Alternate constructor for if a user does not wish to specify their name and phone number
     */
    public User(String username, String password) {
        this("", -1, username, password);
    }

}
