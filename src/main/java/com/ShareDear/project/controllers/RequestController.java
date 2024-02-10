package com.ShareDear.project.controllers;

import com.ShareDear.project.entities.Request;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/request")
public class RequestController {



    private CommanServiceDao2 commanService2;
    private ServiceDao serviceDao;
    private CommanServiceDao commanService;

    private SecurityServiceDao securityService;


    @Autowired
    public RequestController(CommanServiceDao2 commanService2, ServiceDao serviceDao, CommanServiceDao commanService, SecurityServiceDao securityService) {
        this.commanService2 = commanService2;
        this.serviceDao = serviceDao;
        this.commanService = commanService;
        this.securityService = securityService;
    }






// ============> show friend requests page start =============>
    @GetMapping("/all")
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
            model.addAttribute("pageName", "request/all");
        }

        return "ShareDear/My_friends";
    }

// <============ show  friend requests page end <=============





//===============> Sending request handlers start ===============>

    @GetMapping("/sendRequest")
    public String sendFriendRequest(@RequestParam("uid") int id,@ModelAttribute("currentUser") User currentUser)
    {
        User toUser = serviceDao.getUser(id);
        Request request = new Request("Pending",currentUser.getId(),toUser);
        serviceDao.saveRequest(request);
        return "redirect:/users/all?page=0";
    }

//  <============== Sending request handlers  end  <===============






// ==============> Accept and Reject request handlers Start =============>
    @GetMapping("/acceptRequest")
    public String acceptRequest(@RequestParam("uid") int id,@ModelAttribute("currentUser") User currentUser)
    {
        User user = serviceDao.getUser(id);
        user.addToFriend(currentUser);
        serviceDao.cancleRequest(currentUser,id);
        return "redirect:/request/all?page=0";
    }


    @GetMapping("/rejectRequest")
    public String rejectRequest(@RequestParam("uid") int id,@ModelAttribute("currentUser") User currentUser)
    {
        serviceDao.cancleRequest(currentUser,id);
        return "redirect:/request/all?page=0";
    }
// <============== Accept and Reject request handlers End <=============


}
