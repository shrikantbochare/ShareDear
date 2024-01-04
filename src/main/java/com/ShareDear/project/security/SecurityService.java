package com.ShareDear.project.security;

import com.ShareDear.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements SecurityServiceDao {


    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


// ==========> Encode Password  ==========>
    @Override
    public void encryptPassword(User user)
    {
        String encPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encPass);
    }
// <========== Encode Password <============
}
