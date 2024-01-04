package com.ShareDear.project.service;

import com.ShareDear.project.entities.Post;
import com.ShareDear.project.entities.ProfilePic;
import com.ShareDear.project.entities.Request;
import com.ShareDear.project.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceDao {


// ==========> methods related to user - start =========>
    void saveUser(User user);

    User getUser(int id);

    void UpdateUser(User u);

    User getUserByUsername(String username);

    Page<User> findUserByUsernameOrName(String name,Pageable pageable);

// <========== methods related to user - end  <===============





// ==========>  methods related to user and posts - start ============>

    Page<Post> postsOfUserById(User user, Pageable pageable);

    Page<Post> postsOfFriends(List<User> users, Pageable pageable);

// <========== methods related to user and posts - end  <=================





// ============> methods related to user and requests - start =============>

    void saveRequest(Request request);

    Page<User> getAllUsersWhoSendRequests(List<Integer> userIds,Pageable pageable);

    void cancleRequest(User me, int from);

// <===========  methods related to user and requests - end <===============






// ==========> methods related to Profile Pic ==========>

    void createProfilePic(ProfilePic profilePic);

    void updateProfilePic(ProfilePic profilePic);

    ProfilePic findProfileByItsId(int id);

// <==========   methods related to Profile Pic End <==============
}
