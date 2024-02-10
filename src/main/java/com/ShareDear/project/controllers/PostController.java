package com.ShareDear.project.controllers;

import com.ShareDear.project.entities.Post;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;


@Controller
@RequestMapping("/post")
public class PostController {

    private CommanServiceDao2 commanService2;
    private ServiceDao serviceDao;
    private CommanServiceDao commanService;

    private SecurityServiceDao securityService;


    @Autowired
    public PostController(CommanServiceDao2 commanService2, ServiceDao serviceDao, CommanServiceDao commanService, SecurityServiceDao securityService) {
        this.commanService2 = commanService2;
        this.serviceDao = serviceDao;
        this.commanService = commanService;
        this.securityService = securityService;
    }




// ==============> Post page request handlers start ===============>

    @GetMapping("/create")
    public String getPost(Model model)
    {
        Post post = new Post();
        model.addAttribute("mypost",post);

        return "ShareDear/Post_update";
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute("mypost") Post post, @RequestParam("post-img") MultipartFile file, @ModelAttribute("currentUser") User currentUser) throws IOException
    {
        if(!file.isEmpty())
        {
            commanService2.postUpload(post,file);
        }
        Date date = commanService.getTodayDate();
        post.setPost_date(date);
        post.setUser_id(currentUser);

        currentUser.addPost(post);

        serviceDao.UpdateUser(currentUser);
        return "redirect:/post/all?page=0";
    }



//   <=============== Post page request handlers end  <===============








//===============> My Posts page request handlers start ===============>

    @GetMapping("/all")
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
            model.addAttribute("pageName", "post/all");
        }
        return "ShareDear/Home";
    }

    @GetMapping("/userPosts")
    public String getAllFriendsPosts(Model model, @RequestParam("uid") int id,@ModelAttribute("currentUser") User currentUser,@RequestParam("page") int page)
    {
        User user = serviceDao.getUser(id);
        if(commanService2.checkFriend(currentUser,user))
        {
            Pageable pageable = PageRequest.of(page,5);
            Page<Post> posts = serviceDao.postsOfUserById(user,pageable);
            if (posts.isEmpty())
            {
                model.addAttribute("noData","yes");
            }
            else
            {
                model.addAttribute("myhome",posts);
                model.addAttribute("pageNo",page);
                model.addAttribute("pageName","post/userPosts");
                model.addAttribute("totalPages",posts.getTotalPages());
                model.addAttribute("userId",id);
            }
            return "ShareDear/Home";
        }
        else
        {
            return "redirect:/users/all?page=0";
        }



    }

//  <============== My Posts page request handlers  end  <===============


}
