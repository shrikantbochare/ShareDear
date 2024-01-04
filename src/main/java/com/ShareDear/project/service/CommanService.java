package com.ShareDear.project.service;

import com.ShareDear.project.dao.PostRepository;
import com.ShareDear.project.dao.RequestRepository;
import com.ShareDear.project.dao.UserRepository;
import com.ShareDear.project.entities.Request;
import com.ShareDear.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CommanService implements CommanServiceDao{

    private ServiceDao serviceDao;
//    private RequestDao requestDao;
//    private Userdao userdao;
//    private PostDao postDao;
    private UserRepository userRepository;
    private RequestRepository requestRepository;
    private PostRepository postRepository;

    @Autowired
    public CommanService(ServiceDao serviceDao, RequestRepository requestRepository, UserRepository userRepository,PostRepository postRepository) {
        this.serviceDao = serviceDao;
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }




// =========> Get Todays Date Start ===========>
    @Override
    public Date getTodayDate()
    {
        Long millis=System.currentTimeMillis();

        // creating a new object of the class Date
       Date date = new Date(millis);
        return date;
    }
// <=========> Get Todays Date End <===========




// =========> Get Current Logged In User Start ===========>
    @Override
    public User getCurrentLoggedInUser(Principal p) {
        if(p == null)
        {
            return null;
        }
        else
        {
            User currentUser = userRepository.findByUsername(p.getName());
            return currentUser;
        }
    }
// <========= Get Current Logged In User End <===========




// ==========> Get All My Friends Start ============>
    @Override
    public List<User> allFriendsOfMine(User u) {
        List<User> myFriends = u.getMyFriends();
        List<User> iAmFriend = u.getiAmFriend();
        myFriends.addAll(iAmFriend);
        return myFriends;

    }
// <========== Get All My Friends End <============




// ==========> Get All Filtered Users(Users = All Users - (Friends + requestSentByMe + usersWhoSendRequest + Me) Start ============>
    @Override
    public Page<User> filteredUsers(User currentUser, Pageable pageable) {
        int id = currentUser.getId();
        Set<Integer> userId = new HashSet<>();

        if(this.requestSentByMe(id) != null)
        {
            userId.addAll(this.requestSentByMe(id));
        }
        if(this.usersWhoSendRequest(currentUser)!= null)
        {
            userId.addAll(this.usersWhoSendRequest(currentUser));
        }

        List<User> myFriends = new ArrayList<>();

        myFriends.addAll(currentUser.getMyFriends());
        myFriends.addAll(currentUser.getiAmFriend());

        if(!myFriends.isEmpty())
        {
            for(User friend: myFriends)
            {
                userId.add(friend.getId());
            }
        }

        userId.add(id);
        Page<User> filteredUsers = userRepository.findByIdNotIn(userId,pageable);
        return  filteredUsers;
    }
// <========== Get All Filtered Users End <===========




// ==========> Get Users to whom I sent request Start ==========>
    @Override
    public List<Integer> requestSentByMe(int id) {
        List<Request> requests =  requestRepository.findRequestsByUserById(id);

        if(!requests.isEmpty())
        {
            List<Integer> userId = new ArrayList<>();;
            for(Request request: requests)
            {
                userId.add(request.getTo_user_id().getId());
            }

            return userId;
        }

        return null;
    }
// ==========> Get Users to whom I sent request End ==========>





// ==========> Get Users Whom Sent request to me Start ==========>
    @Override
    public List<Integer> usersWhoSendRequest(User user) {
        List<Request> requests = requestRepository.findRequestsByUser(user);

        if(!requests.isEmpty())
        {
            List<Integer> userIds = new ArrayList<>();
            for(Request request: requests)
            {

                userIds.add(request.getFrom_user_id());
            }

            return userIds;
        }

        return null;
    }
// ==========> Get Users Whom Sent request to me End ==========>

}
