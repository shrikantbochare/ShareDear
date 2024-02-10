package com.ShareDear.project.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "newaccount")
public class NewAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acc_id")
    private int acc_id;

    @Column(name = "otp")
    private int otp;

    @Column(name = "email")
    private String email;


    public NewAccount() {
    }

    public NewAccount(int otp, String email) {
        this.otp = otp;
        this.email = email;
    }

    public int getOtp() {
        return otp;
    }

    public String getEmail() {
        return email;
    }
}
