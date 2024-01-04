package com.ShareDear.project.controllers;

import com.ShareDear.project.entities.Post;
import com.ShareDear.project.entities.ProfilePic;
import com.ShareDear.project.entities.Request;
import com.ShareDear.project.entities.User;
import com.ShareDear.project.security.SecurityServiceDao;
import com.ShareDear.project.service.CommanServiceDao;
import com.ShareDear.project.service.CommanServiceDao2;
import com.ShareDear.project.service.ServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/")
public class MainController {
    private CommanServiceDao2 commanService2;
    private  ServiceDao serviceDao;
    private CommanServiceDao commanService;

    private SecurityServiceDao securityService;

    @Autowired
    public MainController(CommanServiceDao2 commanService2, ServiceDao serviceDao, CommanServiceDao commanService,SecurityServiceDao securityService)
    {
        this.commanService2 = commanService2;
        this.serviceDao=serviceDao;
        this.commanService=commanService;
        this.securityService=securityService;
    }


// =============> Model Attribute Method Start ============>
    // Spring MVC will always make a call to that method first, before it
    // calls any request handler methods. Basically, @ModelAttribute methods
    // are invoked before the controller methods annotated with @RequestMapping
    // are invoked. This is because the model object has to be created before
    // any processing starts inside the controller methods.
    // adds an attribute named msg to all models defined in the controller class.
    @ModelAttribute
    public void currentUser(Model model, Principal p)
    {
        if(p != null)
        {

            String username = p.getName();
            User currentUser = serviceDao.getUserByUsername(username);
            model.addAttribute("currentUser",currentUser);
        }
    }
// <========= Model Attribute method End  <==========






//  ===============>  Home page request handlers start ===============>
    @GetMapping("home")
    public String getHome(Model model,@RequestParam("page") int page,@ModelAttribute("currentUser") User currentUser) {
        List<User> friends = commanService.allFriendsOfMine(currentUser);
        Pageable pageable = PageRequest.of(page,5);
        Page<Post> posts = serviceDao.postsOfFriends(friends,pageable);
        if(posts.isEmpty())
        {
            model.addAttribute("noData","yes");
        }
        else
        {
            model.addAttribute("myhome", posts);
            model.addAttribute("pageNo", page);
            model.addAttribute("pageName", "home");
            model.addAttribute("totalPages", posts.getTotalPages());
        }
        return "ShareDear/Home";
    }
//    <============== Home page request handlers end  <===============




//   ==============> profile page request handlers start ===============>
    @GetMapping("profile")
    public String getProfile(Model model,@ModelAttribute("currentUser") User currentUser)
    {
//        DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
//        model.addAttribute("birthDate",df.format(currentUser.getBirthDate()));
        return "ShareDear/Profile";
    }


    @PostMapping("profileUpdate")
    public String showProfile(@ModelAttribute("user") User user)
    {
//, @RequestParam("password") String  password
////        System.out.println(user.getBirthDate());
//        if(bdate.isEmpty() && birthDate.isEmpty())
//        {
//            user.setBirthDate(null);
//        } else if (!birthDate.isEmpty()) {
//            user.setBirthDate(Date.valueOf(bdate));
//        } else
//        {
//            user.setBirthDate(Date.valueOf(birthDate));
//        }


        System.out.println("----------");
        if(user.getPassword().isEmpty())
        {
            User currentUser = serviceDao.getUser(user.getId());
            user.setPassword(currentUser.getPassword());
        }
        else
        {System.out.println("--------2");
            securityService.encryptPassword(user);
        }
        serviceDao.UpdateUser(user);
        System.out.println("updated"+user.getPassword());
        return "redirect:/profile";

}



    @PostMapping("profilePictureUpdate")
    public String changeProfilePicture(@ModelAttribute("currentUser") User user, @RequestParam("profile_img") MultipartFile file)throws IOException
    {
        ProfilePic profilePic = user.getProfilePic();
        commanService2.profileUpdate(profilePic,file);
        serviceDao.updateProfilePic(profilePic);
        return "redirect:/profile";
    }


    @GetMapping("profilePictureRemove")
    public String removeProfilePicture(@RequestParam("pid") int id)
    {
        ProfilePic profilePic = serviceDao.findProfileByItsId(id);
        profilePic.setImg("default_profile_pic.jpg");
        serviceDao.updateProfilePic(profilePic);
        return "redirect:/profile";
    }
//   <============== profile page request handlers end  <===============





//===============> UserDetails page request handlers start ===============>
    @GetMapping("userDetails")
    public String getUser(Model model,@RequestParam("uid") int userId,@ModelAttribute("currentUser") User currentUser)
    {
        User user = serviceDao.getUser(userId);
        if(commanService2.checkFriend(currentUser,user))
        {
            model.addAttribute("friend","yes");
        }
        model.addAttribute("user",user);
        return "ShareDear/user_profile";
    }
//<============== UserDetails page request handlers end  <===============





// ============> show friend requests page start =============>
    @GetMapping("requests")
    public String showAllRequests(Model model,@ModelAttribute("currentUser") User currentUser,@RequestParam("page") int page)
    {

        List<Integer> userIds = commanService.usersWhoSendRequest(currentUser);
        if(userIds == null)
        {
            model.addAttribute("noData","yes");
        }
        else
        {
            Pageable pageable = PageRequest.of(page,5);
            Page<User> users = serviceDao.getAllUsersWhoSendRequests(userIds,pageable);
            model.addAttribute("allUsers",users);
            model.addAttribute("pageNo", page);
            model.addAttribute("totalPages", users.getTotalPages());
            model.addAttribute("pageName", "requests");
        }

        return "ShareDear/My_friends";
    }

// <============ show  friend requests page end <=============





//===============> Sending request handlers start ===============>

    @GetMapping("sendRequest")
    public String sendFriendRequest(@RequestParam("uid") int id,@ModelAttribute("currentUser") User currentUser)
    {
        User toUser = serviceDao.getUser(id);
        Request request = new Request("Pending",currentUser.getId(),toUser);
        serviceDao.saveRequest(request);
        return "redirect:/allUsers?page=0";
    }

//  <============== Sending request handlers  end  <===============





// ==============> Accept and Reject request handlers Start =============>
    @GetMapping("acceptRequest")
    public String acceptRequest(@RequestParam("uid") int id,@ModelAttribute("currentUser") User currentUser)
    {
        User user = serviceDao.getUser(id);
        user.addToFriend(currentUser);
        serviceDao.cancleRequest(currentUser,id);
        return "redirect:/requests?page=0";
    }


    @GetMapping("rejectRequest")
    public String rejectRequest(@RequestParam("uid") int id,@ModelAttribute("currentUser") User currentUser)
    {
        serviceDao.cancleRequest(currentUser,id);
        return "redirect:/requests?page=0";
    }
// <============== Accept and Reject request handlers End <=============





//===============> My Friends page request handlers start ===============>

    @GetMapping("friends")
    public String getFriends(Model model,@ModelAttribute("currentUser") User currentUser,@RequestParam("page") int page)
    {
        List<User> friends = commanService.allFriendsOfMine(currentUser);
        if(friends.isEmpty())
        {
            model.addAttribute("noData","yes");
        }
        else
        {
            float result = ((float) friends.size() /5);
            int pages = (int) Math.ceil(result);
            List<User> pageable = commanService2.pageableUsers(friends,page);

            model.addAttribute("allUsers",pageable);
            model.addAttribute("pageNo", page);
            model.addAttribute("totalPages", pages);
            model.addAttribute("pageName", "friends");
        }

        return "ShareDear/My_friends";
    }

    @GetMapping("userFriends")
     public String friendsOfFriend(Model model,@RequestParam("uid") int id,@ModelAttribute("currentUser") User currentUser,@RequestParam("page") int page)
    {

        User user = serviceDao.getUser(id);
        if(commanService2.checkFriend(currentUser,user))
        {
            List<User> friends = commanService.allFriendsOfMine(user);
            friends.remove(currentUser);

            if(friends.isEmpty())
            {
                model.addAttribute("noData","yes");
            }
            else
            {
                float result = ((float) friends.size() /5);
                int pages = (int) Math.ceil(result);
                List<User> pageable = commanService2.pageableUsers(friends,page);

                model.addAttribute("allUsers",pageable);
                model.addAttribute("pageNo", page);
                model.addAttribute("totalPages", pages);
                model.addAttribute("userId",id);
                model.addAttribute("pageName","userFriends");
                return "ShareDear/My_friends";
            }

        }
        return "redirect:/allUsers?page=0";
    }

    @GetMapping("mutualFriends")
    public String mutualFriends(Model model,@RequestParam("uid") int id,@ModelAttribute("currentUser") User currentUser,@RequestParam("page") int page)
    {
        User user = serviceDao.getUser(id);
        if(commanService2.checkFriend(currentUser,user))
        {

            List<User> users = commanService2.mutualFriends(user,currentUser);

            if(users.isEmpty())
            {
                model.addAttribute("noData","yes");
            }
            else
            {
                float result = ((float) users.size() /5);
                int pages = (int) Math.ceil(result);
                List<User> pageable = commanService2.pageableUsers(users,page);

                model.addAttribute("allUsers",pageable);
                model.addAttribute("pageNo", page);
                model.addAttribute("totalPages", pages);
                model.addAttribute("pageName","mutualFriends");
                model.addAttribute("userId",id);
            }

            return "ShareDear/My_friends";
        }

        return "redirect:/allUsers?page=0";
    }
//  <============== My Friends page request handlers  end  <===============






//===============> My Posts page request handlers start ===============>

    @GetMapping("myPosts")
    public String getAllMyPosts(Model model, @ModelAttribute("currentUser") User currentUser, @RequestParam("page") int page)
    {
        Pageable pageable = PageRequest.of(page,5);
        Page<Post> posts = serviceDao.postsOfUserById(currentUser,pageable);
        if (posts.isEmpty())
        {
            model.addAttribute("noData","yes");
        }
        else
        {
            model.addAttribute("myhome",posts);
            model.addAttribute("pageNo",page);
            model.addAttribute("totalPages",posts.getTotalPages());
            model.addAttribute("pageName", "myPosts");
        }
        return "ShareDear/Home";
    }

    @GetMapping("userPosts")
    public String getAllFriendsPosts(Model model, @RequestParam("uid") int id,@ModelAttribute("currentUser") User currentUser,@RequestParam("page") int page)
    {
        Pageable pageable = PageRequest.of(page,5);
        User user = serviceDao.getUser(id);
        if(commanService2.checkFriend(currentUser,user))
        {
            Page<Post> posts = serviceDao.postsOfUserById(user,pageable);
            if (posts.isEmpty())
            {
                model.addAttribute("noData","yes");
            }
            else
            {
                model.addAttribute("myhome",posts);
                model.addAttribute("pageNo",page);
                model.addAttribute("pageName","userPosts");
                model.addAttribute("totalPages",posts.getTotalPages());
                model.addAttribute("userId",id);
            }
            return "ShareDear/Home";
        }
        else
        {
            return "redirect:/allUsers?page=0";
        }



    }

//  <============== My Posts page request handlers  end  <===============






//===============> All Users page request handlers start ===============>



    @GetMapping("allUsers")
    public String getAll(Model model, @ModelAttribute("currentUser") User currentUser,@RequestParam("page") int page)
    {
        Pageable pageable = PageRequest.of(page,5);
        Page<User> users = commanService.filteredUsers(currentUser,pageable);
        if(users.isEmpty())
        {
            model.addAttribute("noData","yes");
        }
        else
        {
            model.addAttribute("allUsers",users);
            model.addAttribute("pageNo",page);
            model.addAttribute("totalPages",users.getTotalPages());
            model.addAttribute("pageName", "allUsers");
        }
        return "ShareDear/My_friends";
    }



//<============== All Users page request handlers end  <===============






// ==============> Post page request handlers start ===============>

    @GetMapping("post")
    public String getPost(Model model)
    {
        Post post = new Post();
        model.addAttribute("mypost",post);

        return "ShareDear/Post_update";
    }

    @PostMapping("upload")
    public String upload(@ModelAttribute("mypost") Post post, @RequestParam("post-img")MultipartFile file, @ModelAttribute("currentUser") User currentUser) throws IOException
    {
        commanService2.postUpload(post,file);
        Date date = commanService.getTodayDate();
        post.setPost_date(date);
        post.setUser_id(currentUser);

        currentUser.addPost(post);

        serviceDao.UpdateUser(currentUser);
        return "redirect:/post?page=0";
    }



//   <=============== Post page request handlers end  <===============





//  ===============> Register page request handlers start ===============>

    @GetMapping("register")
    public String getRegister(Model model, Principal p)
    {
        if(p == null)
        {
            User user = new User();
            model.addAttribute("user",user);

            return "ShareDear/Register";
        }
        else
        {
            return "redirect:/home?page=0";
        }
    }


    @PostMapping("registerUser")
    public String registerUser(@ModelAttribute("user") User user)
    {
        ProfilePic profilePic = new ProfilePic("default_pic.jpeg",user);
        securityService.encryptPassword(user);
        serviceDao.saveUser(user);
        serviceDao.createProfilePic(profilePic);
        System.out.println(user.getPassword());
        return "redirect:/login";
    }


//   <=============== Register page request handlers end  <===============






// ============> Login Page Request Handlers Start ============>
    @GetMapping("login")
    public String getLogin(Principal p)
    {
        if(p == null)
        {
            return "ShareDear/Login";
        }
        else
        {
            return "redirect:/home";
        }

    }
// <============ Login Page Request Handlers End <============





// =============> Remove Friend Request Handler Start ===========>
    @GetMapping("removeFriend")
    public String removeFriend(@RequestParam("uid") int id,@ModelAttribute("currentUser") User currentUser)
    {
        User user = serviceDao.getUser(id);

        if(currentUser.getMyFriends().contains(user))
        {
            currentUser.getMyFriends().remove(user);
            serviceDao.UpdateUser(currentUser);
        } else if (currentUser.getiAmFriend().contains(user)) {

            currentUser.getiAmFriend().remove(user);
            serviceDao.UpdateUser(currentUser);

        }

        return "redirect:/friends?page=0";
    }
// <============= Remove Friend Request Handler End <===========






// ============> Search User By name or username Start ============>
    @PostMapping("searchUser")
    public String searchUserInDb(@ModelAttribute("currentUser") User currentUser, Model model,@RequestParam("searchUser") String name,@RequestParam("page") int page)
    {

        Pageable pageable = PageRequest.of(page,5);
        Page<User> users = serviceDao.findUserByUsernameOrName(name,pageable);

        if(users.isEmpty())
        {

            model.addAttribute("noData","yes");
        }
        else
        {
            for( User individual : users)
            {
                if(commanService2.checkFriend(currentUser,individual))
                {
                    model.addAttribute("friend","yes");
                }
                else
                {
                    model.addAttribute("friend","no");
                }
            }


            model.addAttribute("allUsers",users);
            model.addAttribute("pageNo", page);
            model.addAttribute("pageName","searchUser");
            model.addAttribute("totalPages", users.getTotalPages());
        }

        return "ShareDear/My_friends";
    }

// <============ Search User By name or username End   <==============
}
