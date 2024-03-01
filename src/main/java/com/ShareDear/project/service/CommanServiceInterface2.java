package com.ShareDear.project.service;

import com.ShareDear.project.entities.Post;
import com.ShareDear.project.entities.ProfilePic;
import com.ShareDear.project.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CommanServiceInterface2 {

    public void postUpload(Post post, MultipartFile file) throws IOException;

    public void profileUpdate(ProfilePic profilePic, MultipartFile file) throws IOException;

    boolean checkFriend(User currentUser, User user);

    List<User> pageableUsers( List<User> friends ,int page);

    List<User> mutualFriends(User user, User currentUser);

    void sendMail(String message, String to);

    int randomOTP();

    void deleteProfilePicFromPath(String fileName) throws IOException;


    void deletePostPicFromPath(String fileName) throws IOException;
}
