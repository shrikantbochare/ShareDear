package com.ShareDear.project.service;

import com.ShareDear.project.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.sql.Date;
import java.util.List;

public interface CommanServiceDao {

    public Date getTodayDate();


    User getCurrentLoggedInUser(Principal p);

    Page<User> filteredUsers(User currentUser, Pageable pageable);


    public List<Integer> requestSentByMe(int id);


    public List<Integer> usersWhoSendRequest(User u);


    public List<User> allFriendsOfMine(User u);



}
