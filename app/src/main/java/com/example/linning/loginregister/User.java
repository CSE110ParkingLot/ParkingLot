package com.example.linning.loginregister;

/**
 * Created by linning on 10/24/15.
 */
public class User {
    String name, username, password;
    int phone;


    public User(String name, int phone, String username, String password){
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this("", -1, username, password);
    }

}
