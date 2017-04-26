package com.example.abdullahhashmi.passwordmanager;

import java.io.Serializable;

/**
 * Created by abdullahhashmi on 4/26/17.
 */

@SuppressWarnings("serial")
public class RecordData implements Serializable {

    private String username;
    private String password;

    public RecordData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
