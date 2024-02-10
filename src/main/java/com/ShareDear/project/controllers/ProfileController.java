package com.ShareDear.project.controllers;

import com.ShareDear.project.entities.ProfilePic;
import com.ShareDear.project.entities.User;
import com.ShareDear.project.pojo.UpdateUserPojo;
import com.ShareDear.project.security.SecurityServiceDao;
import com.ShareDear.project.service.CommanServiceDao;
import com.ShareDear.project.service.CommanServiceDao2;
import com.ShareDear.project.service.ServiceDao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;




@Controller
@RequestMapping("/profile")
public class ProfileController {

    private CommanServiceDao2 commanService2;
    private ServiceDao serviceDao;
    private CommanServiceDao commanService;

    private SecurityServiceDao securityService;


    @Autowired
    public ProfileController(CommanServiceDao2 commanService2, ServiceDao serviceDao, CommanServiceDao commanService, SecurityServiceDao securityService) {
        this.commanService2 = commanService2;
        this.serviceDao = serviceDao;
        this.commanService = commanService;
        this.securityService = securityService;
    }






//   ==============> profile page request handlers start ===============>
    @GetMapping("/")
    public String getProfile(@ModelAttribute("currentUser") User currentUser,Model model)
    {
        UpdateUserPojo updateUserPojo = new UpdateUserPojo(currentUser.getId(),currentUser.getName(),currentUser.getUsername(),
                currentUser.getEmail(),currentUser.getAge(),currentUser.getBirthDate(),currentUser.getEducation(),
                currentUser.getOccupation(),currentUser.getAddress(),currentUser.getCity(),currentUser.getState(), currentUser.getPassword());


        model.addAttribute("updateUserPojo", updateUserPojo);
        return "ShareDear/Profile";
    }


    @PostMapping("/profileUpdate")
    public String showProfile(@Valid @ModelAttribute("updateUserPojo") UpdateUserPojo user, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "ShareDear/Profile";
        }
        else
        {

            User currentUser = serviceDao.getUser(user.getId());
            currentUser.setName(user.getName());
            currentUser.setAge(user.getAge());
            currentUser.setBirthDate(user.getBirthDate());
            currentUser.setEducation(user.getEducation());
            currentUser.setOccupation(user.getOccupation());
            currentUser.setCity(user.getCity());
            currentUser.setState(user.getState());
            currentUser.setAddress(user.getAddress());
            serviceDao.UpdateUser(currentUser);
            return "redirect:/profile/";
        }

    }


    @GetMapping("/passwordUpdate")
    public String passwordUpdate(@ModelAttribute("currentUser") User currentUser,Model model)
    {
        UpdateUserPojo updateUserPojo = new UpdateUserPojo(currentUser.getId(),currentUser.getName(),currentUser.getUsername(),
                currentUser.getEmail(),currentUser.getAge(),currentUser.getBirthDate(),currentUser.getEducation(),
                currentUser.getOccupation(),currentUser.getAddress(),currentUser.getCity(),currentUser.getState(), currentUser.getPassword());

        model.addAttribute("updateUserPojo", updateUserPojo);
        return "ShareDear/Password_update";
    }

    @PostMapping("/passwordUpdateSave")
    public String passwordUpdateSave(@Valid @ModelAttribute("updateUserPojo") UpdateUserPojo user,@RequestParam("Password_old") String password_old,
                                     BindingResult bindingResult,Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "redirect:/profile/passwordUpdate";
        }
        else {

            User currentUser = serviceDao.getUser(user.getId());
            System.out.println(password_old);
            System.out.println(currentUser.getPassword());
            Boolean match = BCrypt.checkpw(password_old, currentUser.getPassword());
            if (match) {
                currentUser.setPassword(user.getPassword());
                securityService.encryptPassword(currentUser);
                serviceDao.UpdateUser(currentUser);
                model.addAttribute("Match","match");
            }
            else
            {
                model.addAttribute("NotMatch","NotMatch");
            }
            return "ShareDear/Password_update";
        }
    }


    @PostMapping("/profilePictureUpdate")
    public String changeProfilePicture(@ModelAttribute("currentUser") User user, @RequestParam("profile_img") MultipartFile file) throws IOException
    {
            ProfilePic profilePic = user.getProfilePic();
            if(!file.isEmpty())
            {
                commanService2.profileUpdate(profilePic,file);
            }
            serviceDao.updateProfilePic(profilePic);
            return "redirect:/profile/";
    }


    @GetMapping("/profilePictureRemove")
    public String removeProfilePicture(@RequestParam("pid") int id)
    {
        ProfilePic profilePic = serviceDao.findProfileByItsId(id);
        profilePic.setImg("default_pic.jpeg");
        serviceDao.updateProfilePic(profilePic);
        return "redirect:/profile/";
    }
//   <============== profile page request handlers end  <===============

}
