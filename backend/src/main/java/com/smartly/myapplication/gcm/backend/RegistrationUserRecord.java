package com.smartly.myapplication.gcm.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The Objectify object model for device registrations we are persisting
 */
@Entity
public class RegistrationUserRecord {

    @Id
    Long id;

    @Index
    private String regId;
    private String username;
    private String email;
    private String password;

    public RegistrationUserRecord() {
    }

    public String getRegId() {

        return regId;
    }

    public void setRegId(String regId) {

        this.regId = regId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}