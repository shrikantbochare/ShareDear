package com.ShareDear.project.service;

import com.ShareDear.project.entities.Post;
import com.ShareDear.project.entities.ProfilePic;
import com.ShareDear.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommanService2 implements CommanServiceDao2{


    private CommanServiceDao commanServiceDao;

    @Value("${path.profile.image}")
    private String profileImgPath;

    @Value("${path.post.image}")
    private String postImgPath;

    @Autowired
    public CommanService2(CommanServiceDao commanServiceDao) {
        this.commanServiceDao = commanServiceDao;
    }

    // ===========> Get a path to store Post Images ============>
    @Override
    public void postUpload(Post post, MultipartFile file) throws IOException
    {

//            File thePath = new ClassPathResource("static/post_images").getFile();
//            Path path = Paths.get(thePath.getAbsolutePath()+File.separator+file.getOriginalFilename());
//            post.setImg_loc(file.getOriginalFilename());
//            file.transferTo(path);


        String fileName = file.getOriginalFilename();
        String imgPath = postImgPath + File.separator + fileName;

        File f = new File(postImgPath);
        if(!f.exists())
        {
            f.mkdir();
        }

        Files.copy(file.getInputStream(),Paths.get(imgPath));
        post.setImg_loc(fileName);

    }
// <============  Get a path to store Post Images End <===============




// ===========> Get a path to Store Profile Images ==============>
    @Override
    public void profileUpdate(ProfilePic profilePic, MultipartFile file) throws IOException
    {

//            File thePath = new ClassPathResource("static/Profile_images").getFile();
//            Path path = Paths.get(thePath.getAbsolutePath()+File.separator+file.getOriginalFilename());
//            profilePic.setImg(file.getOriginalFilename());
//            file.transferTo(path);

        String fileName = file.getOriginalFilename();
        String imgPath = profileImgPath + File.separator + fileName;

        File f = new File(profileImgPath);
        if(!f.exists())
        {
            f.mkdir();
        }


        Files.copy(file.getInputStream(),Paths.get(imgPath));
        profilePic.setImg(fileName);
    }

// <===========  Get a path to Store Profile Images End  <===============





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

}
