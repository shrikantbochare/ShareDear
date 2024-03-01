package com.ShareDear.project.Exception;


import com.ShareDear.project.entities.User;
import com.ShareDear.project.service.ServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.thymeleaf.exceptions.TemplateInputException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.Principal;

@ControllerAdvice
public class ExceptionHandler {



    private ServiceDao serviceDao;

    @Autowired
    public ExceptionHandler(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }





// =============> Model Attribute Method Start ============>
    // Spring MVC will always make a call to that method first, before it
    // calls any request handler methods. Basically, @ModelAttribute methods
    // are invoked before the controller methods annotated with @RequestMapping
    // are invoked. This is because the model object has to be created before
    // any processing starts inside the controller methods.
    // adds an attribute named currentUser to all models defined in the controller class.
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





// =============> Exception handler for failed Mail =================>
    @org.springframework.web.bind.annotation.ExceptionHandler(MessagingException.class)
    public String mailException(MessagingException e, Model model)
    {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.SERVICE_UNAVAILABLE);

        problemDetail.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
        problemDetail.setDetail("Message Not Sent");

        model.addAttribute("mailNotSent",problemDetail);
        return "redirect:/registerEmail";

    }
// <============ Exception handler for failed Mail <================






// =============> Exception handler IO Exception =================>
    @org.springframework.web.bind.annotation.ExceptionHandler(IOException.class)
    public String IoException(IOException e,Model model)
    {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail("Bad Request");
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        return "ShareDear/somethingWentWrong";
    }
// <============= Exception handler IO Exception <=================






// =============> Exception handler global Exception =================>
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public String parentException(Exception e)
    {
        return "ShareDear/somethingWentWrong";
    }
// <============= Exception handler global Exception <=================





// =============> Exception handler TemplateInputException  =================>
    @org.springframework.web.bind.annotation.ExceptionHandler(TemplateInputException.class)
    public String templateInputExceptionHandler(TemplateInputException t)
    {
        return "ShareDear/somethingWentWrong";
    }
// <============= Exception handler TemplateInputException <=================

}
