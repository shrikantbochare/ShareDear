package com.ShareDear.project.service;

import com.ShareDear.project.dao.*;
import com.ShareDear.project.entities.Post;
import com.ShareDear.project.entities.ProfilePic;
import com.ShareDear.project.entities.Request;
import com.ShareDear.project.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceDaoImpl implements ServiceDao{

//    private Userdao userdao;

    private UserRepository userRepository;
//    private PostDao postDao;
    private PostRepository postRepository;

    private RequestDao requestDao;
    private RequestRepository requestRepository;

//    private ProfilePicDao profilePicDao;
    private ProfilePicRepository profilePicRepository;

    @Autowired
    public ServiceDaoImpl(RequestDao requestDao,UserRepository userRepository,PostRepository postRepository,RequestRepository requestRepository,ProfilePicRepository profilePicRepository) {

        this.requestDao = requestDao;
        this.userRepository = userRepository;
        this.postRepository=postRepository;
        this.requestRepository=requestRepository;
        this.profilePicRepository=profilePicRepository;
    }

//   =========> save user  ==========>
    @Transactional
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
//  <==========  save user End  <==========




//   =========> Get Single User By its Id  From db  ==========>
    @Override
    public User getUser(int id) {
        Optional<User> result = userRepository.findById(id);

        User user = null;

        if (result.isPresent()) {
            user = result.get();
        } else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + id);
        }

        return user;
    }
//  <==========  Get All Users in db End End  <==========




// =========> Update user details ==========>
    @Transactional
    @Override
    public void UpdateUser(User u) {
        userRepository.save(u);
    }
// =========> Update user details End ==========>




//   =========> Get Single User By its Username  From db  ==========>
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
//  <==========  Get Single User By its Username  From db End  <==========





// =========> get posts of user by  user id ==========>
    @Override
    public Page<Post> postsOfUserById(User user, Pageable pageable) {
        return postRepository.findPostsOfUserByIdOrderByPost_dateDesc(user,pageable);
    }
//  <========= get posts of user by  user id  <==========




// =========> Get ALl Posts Of Friends Start ==========>
    @Override
    public Page<Post> postsOfFriends(List<User> users, Pageable pageable) {
        return postRepository.findPostsByUsersOrderByPost_dateDesc(users,pageable);
    }
// <========= Get ALl Posts Of Friends End <==========





//  ==========> Save Request =========>
    @Transactional
    @Override
    public void saveRequest(Request request) {
        requestRepository.save(request);
    }
//  <========== Save Request End <=========





//  ========> get all users who send friend request to particular user ==========>

    @Override
    public Page<User> getAllUsersWhoSendRequests(List<Integer> userIds,Pageable pageable) {
        return userRepository.findByIdIn(userIds,pageable);
    }
//  <======== get all users who send friend request to particular user  <==========





// ========> Cancel Request == delete request from db =========>
    @Transactional
    @Override
    public void cancleRequest(User me, int from) {
        requestDao.cancleRequest(me,from);
    }
// <======== Cancel Request == delete request from db End <=========





// ==========> methods related to Profile Pic ==========>

    @Transactional
    @Override
    public void createProfilePic(ProfilePic profilePic) {
        profilePicRepository.save(profilePic);
    }

    @Transactional
    @Override
    public void updateProfilePic(ProfilePic profilePic) {
        profilePicRepository.save(profilePic);
    }

    @Override
    public ProfilePic findProfileByItsId(int id) {
        Optional<ProfilePic> profilePic =  profilePicRepository.findById(id);
        ProfilePic thePic = null;

        if (profilePic.isPresent()) {
            thePic = profilePic.get();
        } else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + id);
        }

        return thePic;
    }


// <==========   methods related to Profile Pic End <==============




// ============> Find User By Name Or Username start =============>

    @Override
    public Page<User> findUserByUsernameOrName(String name,Pageable pageable) {
        return userRepository.findByUsernameContainingOrNameContaining(name,name,pageable);
    }


// <============ Find User By Name Or Username End <=============


}
