package com.ShareDear.project.security;

import com.ShareDear.project.dao.UserRepository;
import com.ShareDear.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailSerice implements UserDetailsService {

    private UserRepository userdao;

    @Autowired
    public CustomUserDetailSerice(UserRepository userdao) {
        this.userdao = userdao;
    }



// =========> Find Logged In User and Load its Details to Log In ==========>
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        final User user = userdao.findByUsername(username);
        if(user == null)
        {
            throw new UsernameNotFoundException(username);
        }
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword()).build();

        return userDetails;
    }
// <========= Find Logged In User and Load its Details to Log In <==========




}
