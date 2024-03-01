package com.ShareDear.project.controllers;

import com.ShareDear.project.entities.NewAccount;
import com.ShareDear.project.entities.ProfilePic;
import com.ShareDear.project.entities.User;
import com.ShareDear.project.pojo.CreateUserPojo;
import com.ShareDear.project.security.SecurityServiceDao;
import com.ShareDear.project.service.CommanServiceInterface;
import com.ShareDear.project.service.CommanServiceInterface2;
import com.ShareDear.project.service.ServiceDao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;


@Controller
@RequestMapping("/")
public class MainController {
    private CommanServiceInterface2 commanService2;
    private  ServiceDao serviceDao;
    private CommanServiceInterface commanService;

    private SecurityServiceDao securityService;

    @Autowired
    public MainController(CommanServiceInterface2 commanService2, ServiceDao serviceDao, CommanServiceInterface commanService, SecurityServiceDao securityService)
    {
        this.commanService2 = commanService2;
        this.serviceDao=serviceDao;
        this.commanService=commanService;
        this.securityService=securityService;
    }






//  ===============> Register page request handlers start ===============>

    @GetMapping("registerEmail")
    public String registerEmail(Model model,Principal p)
    {
        if(p == null)
        {
            model.addAttribute("accountCreate","accountCreate");
            model.addAttribute("pageName","EmailVerification");
            model.addAttribute("url","verifyEmail");
            return "ShareDear/EmailVerification";
        }
        else
        {
            return "redirect:/home/posts?page=0";
        }
    }

    @PostMapping("verifyEmail")
    public String verifyEmail(@RequestParam("email") String email, Model model,Principal p)
    {
        if(p == null)
        {
            Boolean exists = serviceDao.getUserByEmail(email);

            if(exists)
            {
                model.addAttribute("userExists",exists);
                model.addAttribute("pageName","EmailVerification");
                model.addAttribute("url","verifyEmail");
                return "ShareDear/EmailVerification";
            }
            else
            {
                Integer random = commanService2.randomOTP();


                NewAccount newAccount = new NewAccount(random,email);
                serviceDao.saveNewAccount(newAccount);


                String otp = random.toString();
                commanService2.sendMail(otp,email);


                model.addAttribute("mail",email);
                model.addAttribute("pageName","OtpVerification");
                model.addAttribute("url","verify");
                return "ShareDear/EmailVerification";
            }
        }
        else
        {
            return "redirect:/home/posts?page=0";
        }
    }


    @PostMapping("verify")
    public String verifyOTP(@RequestParam("otp") String otp,Model model,@RequestParam("email") String mail,Principal p)
    {
       if(p == null)
       {
           NewAccount newAccount = serviceDao.findNewAccount(mail);


           if(newAccount.getOtp() == Integer.parseInt(otp))
           {
               CreateUserPojo user = new CreateUserPojo();
               user.setEmail(mail);
               serviceDao.deleteNewAccount(mail);
               model.addAttribute("user",user);

               return "ShareDear/Register";
           }
           else
           {
               model.addAttribute("invalid","true");
               model.addAttribute("mail",mail);
               model.addAttribute("pageName","OtpVerification");
               model.addAttribute("url","verify");
               return "ShareDear/EmailVerification";
           }
       }
       else
       {
           return "redirect:/home/posts?page=0";
       }
    }


    @PostMapping("registerUser")
    public String registerUser(@Valid @ModelAttribute("user") CreateUserPojo createUserPojo, BindingResult bindingResult, Principal p)
    {
        if(p == null)
        {

            if(bindingResult.hasErrors())
            {
                return "ShareDear/Register";
            }
            else
            {
                User user = new User(createUserPojo.getName(), createUserPojo.getUsername(), createUserPojo.getPassword(), createUserPojo.getEmail());

                ProfilePic profilePic = new ProfilePic("default_pic.jpeg",user);
                securityService.encryptPassword(user);
                serviceDao.saveUser(user);
                serviceDao.createProfilePic(profilePic);
                return "redirect:/login";
            }
        }
        else
        {
            return "redirect:/home/posts?page=0";
        }
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
            return "redirect:/home/posts?page=0";
        }

    }
// <============ Login Page Request Handlers End <============






// ============> Forgot Password Handler Start ============>


    @GetMapping("forgotPassword")
    public String forgotPasssword(Model model,Principal p)
    {
      if(p == null)
      {
          model.addAttribute("pageName","EmailVerification");
          model.addAttribute("url","verifyEmailForPass");
          return "ShareDear/EmailVerification";
      }
      else
      {
          return "redirect:/home/posts?page=0";
      }
    }


    @PostMapping("verifyEmailForPass")
    public String VerifyEmailForPassChange(@RequestParam("email") String email,Model  model,Principal p)
    {
        if(p == null)
        {
            Boolean exists = serviceDao.getUserByEmail(email);

            if(exists)
            {
                Integer random = commanService2.randomOTP();


                NewAccount newAccount = new NewAccount(random,email);
                serviceDao.saveNewAccount(newAccount);


                String otp = random.toString();
                commanService2.sendMail(otp,email);


                model.addAttribute("mail",email);
                model.addAttribute("pageName","OtpVerification");
                model.addAttribute("url","verifyOtpForPassChange");
                return "ShareDear/EmailVerification";
            }
            else
            {
                model.addAttribute("userNotExists","userNotExists");
                model.addAttribute("pageName","EmailVerification");
                model.addAttribute("url","verifyEmailForPass");
                return "ShareDear/EmailVerification";
            }
        }
        else
        {
            return "redirect:/home/posts?page=0";
        }
    }



    @PostMapping("verifyOtpForPassChange")
    public String verifyOtpForPassChange(@RequestParam("otp") String otp,Model model,@RequestParam("email") String mail,Principal p)
    {
        if(p == null )
        {
            NewAccount newAccount = serviceDao.findNewAccount(mail);
            if(newAccount.getOtp() == Integer.parseInt(otp))
            {
                serviceDao.deleteNewAccount(mail);
                model.addAttribute("mail",mail);
                model.addAttribute("pageName","NewPassCreation");
                return "ShareDear/EmailVerification";
            }
            else
            {
                model.addAttribute("invalid","invalid");
                model.addAttribute("mail",mail);
                model.addAttribute("pageName","OtpVerification");
                model.addAttribute("url","verifyOtpForPassChange");
                return "ShareDear/EmailVerification";
            }

        }
        else
        {
            return "redirect:/home/posts?page=0";
        }
    }




    @PostMapping("submitNewPass")
    public String submitNewPass(@RequestParam("pass") String password ,@RequestParam("email") String email,Model model,Principal p)
    {
        if(p == null)
        {
           if(commanService.checkPassValidity(password))
           {
               Optional<User> optional = serviceDao.findUserByEmail(email);
               if (optional.isPresent())
               {
                   User user = optional.get();
                   user.setPassword(password);
                   securityService.encryptPassword(user);
                   serviceDao.UpdateUser(user);
                   model.addAttribute("mail",email);
                   model.addAttribute("pageName","PassCreationSuccess");
                   return "ShareDear/EmailVerification";
               }
               else
               {
                   return "shareDear/somethingWentWrong";
               }
           }
           else
           {
               model.addAttribute("password_error","Password must contain" +
                       " at least one small letter, capital letter, number, and special character like @ or _  " +
                       "with minimum size of 8 letters");
               model.addAttribute("mail",email);
               model.addAttribute("pageName","NewPassCreation");
               return "ShareDear/EmailVerification";
           }
        }
        else
        {
            return "redirect:/home/posts?page=0";
        }
    }


// <============ Forgot Password Handler End <============

}
