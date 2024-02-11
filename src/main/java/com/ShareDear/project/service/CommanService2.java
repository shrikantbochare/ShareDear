package com.ShareDear.project.service;

import com.ShareDear.project.entities.Post;
import com.ShareDear.project.entities.ProfilePic;
import com.ShareDear.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

@Service
public class CommanService2 implements CommanServiceDao2{


    private CommanServiceDao commanServiceDao;
    @Autowired
    public CommanService2(CommanServiceDao commanServiceDao) {
        this.commanServiceDao = commanServiceDao;
    }





// ===========> Get a path to store Post Images ============>
    @Override
    public void postUpload(Post post, MultipartFile file) throws IOException
    {

            File thePath = new ClassPathResource("static/post_images").getFile();
            Path path = Paths.get(thePath.getAbsolutePath()+File.separator+file.getOriginalFilename());
            post.setImg_loc(file.getOriginalFilename());
            file.transferTo(path);

    }
// <============  Get a path to store Post Images End <===============







// ===========> Get a path to Store Profile Images ==============>
    @Override
    public void profileUpdate(ProfilePic profilePic, MultipartFile file) throws IOException
    {

            File thePath = new ClassPathResource("static/Profile_images").getFile();
            Path path = Paths.get(thePath.getAbsolutePath()+File.separator+file.getOriginalFilename());
            profilePic.setImg(file.getOriginalFilename());
            file.transferTo(path);
    }

// <===========  Get a path to Store Profile Images End  <===============






// ===============> Delete picture from path ==================>
    @Override
    public void deleteProfilePicFromPath(String fileName) throws IOException
    {
        File thePath = new ClassPathResource("static/Profile_images").getFile();
        Path path = Paths.get(thePath.getAbsolutePath()+File.separator+fileName);

        File file = new File(path.toString());

        if(file.exists())
        {
            boolean result = file.delete();
        }
    }
// <=============== Delete picture from path <=================






// ===============> Delete picture from path ==================>
    @Override
    public void deletePostPicFromPath(String fileName) throws IOException
    {
        File thePath = new ClassPathResource("static/post_images").getFile();
        Path path = Paths.get(thePath.getAbsolutePath()+File.separator+fileName);

        File file = new File(path.toString());

        if(file.exists())
        {
            boolean result = file.delete();
        }
    }
// <=============== Delete picture from path <=================







// =============> Check if User is Your friend Start ============>
    @Override
    public boolean checkFriend(User currentUser, User user)
    {

        return (currentUser.getMyFriends().contains(user)) || (currentUser.getiAmFriend().contains(user));
    }
// <============ Check if User is Your friend End  <==============







// =============> Get pageable List of Users Start ============>

    @Override
    public List<User> pageableUsers(List<User> friends, int page) {
        List<User> pageable = new ArrayList<>();
        for(int i= page*5 ; i<(page*5)+5;i++)
        {
            if(i < friends.size())
            {
                pageable.add(friends.get(i));
            }
            else
            {
                break;
            }
        }

        return pageable;
    }


// <============= Get pageable List of Users End  <=============







// ==============> Get List Of Mutual Friends Start  ==============>

    @Override
    public List<User> mutualFriends(User user, User currentUser) {
        List<User> friends = commanServiceDao.allFriendsOfMine(user);
        List<User> friends2 = commanServiceDao.allFriendsOfMine(currentUser);
        List<User> users = new ArrayList<>();
        for (User friendsOfFirst : friends)
        {
            for (User friendsOfSecond : friends2)
            {
                if (friendsOfFirst == friendsOfSecond)
                {
                    users.add(friendsOfFirst);
                }
            }
        }

        users.remove(user);
        users.remove(currentUser);
        return  users;
    }


// <============== Get List Of Mutual Friends End  <================







// ==============> Send Email verification mail  ================>

    @Override
    public void sendMail(String otp, String email)
    {
        String subject = "Account Verification Mail";
        String from = "theinquisitivecoder@gmail.com";
        String to = email;
        String message = "<div>" +
                "<h2>ShareDear Security" +
                "<br> <br> "+
                "<h4>Your One Time Password is :" + otp +" </h4>" +
                "<br> <br> <br>" +
                "<p> This email is in response to a request to verify email address. <br> You can ignore this email if you did not submit this request.<br>" +
                "Use this passcode to verify email address.<br>" +
                "Passcode expires in 60 minutes.<br>" +
                "Do not reply to this email.</p>" +
                "<div>";


        //variable for gmail host
        String host = "smtp.gmail.com";

        //getting system properties
        Properties properties = System.getProperties();

        //setting some properties
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");


        //Getting the session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("theinquisitivecoder@gmail.com","jogj lwrp gqqp fpux");
            }
        });



        //Message Compose
        MimeMessage mimeMessage = new MimeMessage(session);

        try {
            mimeMessage.setFrom(from);
            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            mimeMessage.setSubject(subject);
//            mimeMessage.setText(message);
            mimeMessage.setContent(message,"text/html");


            //Send mail using Transport class
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

// <============== Send Email verification mail End  <================







// ==============> Generating Random OTP  ================>


    @Override
    public int randomOTP() {
        Random random = new Random();
        int min = 1000;
        int max = 10000;

        int otp =  random.nextInt(9000 ) + 1000 ;
        return otp;
    }
// <============== Generating Random OTP  <===============

}
