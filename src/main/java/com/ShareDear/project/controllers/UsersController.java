package com.ShareDear.project.controllers;

import com.ShareDear.project.entities.User;
import com.ShareDear.project.security.SecurityServiceDao;
import com.ShareDear.project.service.CommanServiceDao;
import com.ShareDear.project.service.CommanServiceDao2;
import com.ShareDear.project.service.ServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/users")
public class UsersController {

    private CommanServiceDao2 commanService2;
    private ServiceDao serviceDao;
    private CommanServiceDao commanService;

    private SecurityServiceDao securityService;


    @Autowired
    public UsersController(CommanServiceDao2 commanService2, ServiceDao serviceDao, CommanServiceDao commanService, SecurityServiceDao securityService) {
        this.commanService2 = commanService2;
        this.serviceDao = serviceDao;
        this.commanService = commanService;
        this.securityService = securityService;
    }








//===============> All Users page request handlers start ===============>



    @GetMapping("/all")
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
            model.addAttribute("pageName", "users/all");
        }
        return "ShareDear/My_friends";
    }



//<============== All Users page request handlers end  <===============






//===============> UserDetails page request handlers start ===============>
    @GetMapping("/userDetails")
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






//===============> My Friends page request handlers start ===============>

    @GetMapping("/friends")
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
            model.addAttribute("pageName", "users/friends");
        }

        return "ShareDear/My_friends";
    }



    @GetMapping("/userFriends")
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
                model.addAttribute("pageName","users/userFriends");
                return "ShareDear/My_friends";
            }

        }
        return "redirect:/users/all?page=0";
    }



    @GetMapping("/mutualFriends")
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
                model.addAttribute("pageName","users/mutualFriends");
                model.addAttribute("userId",id);
            }

            return "ShareDear/My_friends";
        }

        return "redirect:/users/all?page=0";
    }
//  <============== My Friends page request handlers  end  <===============






// =============> Remove Friend Request Handler Start ===========>
    @GetMapping("/removeFriend")
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

        return "redirect:/users/friends?page=0";
    }
// <============= Remove Friend Request Handler End <===========






// ============> Search User By name or username Start ============>
    @PostMapping("/searchUser")
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
            model.addAttribute("pageName","users/searchUser");
            model.addAttribute("totalPages", users.getTotalPages());
        }

        return "ShareDear/My_friends";
    }

// <============ Search User By name or username End   <==============
}
