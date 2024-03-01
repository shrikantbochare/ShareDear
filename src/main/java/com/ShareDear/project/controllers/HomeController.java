package com.ShareDear.project.controllers;

import com.ShareDear.project.entities.Post;
import com.ShareDear.project.entities.User;
import com.ShareDear.project.security.SecurityServiceDao;
import com.ShareDear.project.service.CommanServiceInterface;
import com.ShareDear.project.service.CommanServiceInterface2;
import com.ShareDear.project.service.ServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private CommanServiceInterface2 commanService2;
    private ServiceDao serviceDao;
    private CommanServiceInterface commanService;

    private SecurityServiceDao securityService;



    @Autowired
    public HomeController(CommanServiceInterface2 commanService2, ServiceDao serviceDao, CommanServiceInterface commanService, SecurityServiceDao securityService) {
        this.commanService2 = commanService2;
        this.serviceDao = serviceDao;
        this.commanService = commanService;
        this.securityService = securityService;
    }





//  ===============>  Home page request handlers start ===============>
    @GetMapping("/posts")
    public String getHome(Model model, @RequestParam("page") int page, @ModelAttribute("currentUser") User currentUser) {
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
            model.addAttribute("pageName", "home/posts");
            model.addAttribute("totalPages", posts.getTotalPages());
        }
        return "ShareDear/Home";
    }
//    <============== Home page request handlers end  <===============
}
