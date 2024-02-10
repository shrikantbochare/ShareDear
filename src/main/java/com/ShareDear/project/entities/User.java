package com.ShareDear.project.entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    @Column(name = "birthdate")
    private Date birthDate;

    @Column(name = "education")
    private String education;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "address")
    private String  address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;



    @OneToMany(mappedBy = "user_id",fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.MERGE})
    private List<Post> posts;

    @OneToMany(mappedBy = "to_user_id",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Request> requests;

    @OneToOne(mappedBy = "user_id",cascade = { CascadeType.REMOVE ,CascadeType.PERSIST},fetch = FetchType.EAGER)
    private ProfilePic profilePic;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE})
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "uid1"),
            inverseJoinColumns = @JoinColumn(name = "uid2")
    )
    private List<User> myFriends;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE})
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "uid2"),
            inverseJoinColumns = @JoinColumn(name = "uid1")
    )
    private List<User> iAmFriend;


    public ProfilePic getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(ProfilePic profilePic) {
        this.profilePic = profilePic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }


    public List<User> getMyFriends() {
        return myFriends;
    }

    public void setMyFriends(List<User> myFriends) {
        this.myFriends = myFriends;
    }

    public List<User> getiAmFriend() {
        return iAmFriend;
    }

    public void setiAmFriend(List<User> iAmFriend) {
        this.iAmFriend = iAmFriend;
    }



    public User() {
    }

    public User(String name, String username, String password, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void addPost(Post post)
    {
        if(posts == null)
        {
            posts = new ArrayList<>();
        }

        posts.add(post);
    }

    public void addRequest(Request request)
    {
        if(requests == null)
        {
            requests = new ArrayList<>();
        }

        requests.add(request);
    }

    public void addToFriend(User u)
    {
        if(myFriends == null)
        {
            myFriends = new ArrayList<>();
        }

        myFriends.add(u);
    }

    public void addedToFriend(User u)
    {
        if(iAmFriend == null)
        {
            iAmFriend = new ArrayList<>();
        }

        iAmFriend.add(u);
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", email='" + email + '\'' +
//                ", age=" + age +
//                ", birthDate=" + birthDate +
//                ", education='" + education + '\'' +
//                ", occupation='" + occupation + '\'' +
//                ", address='" + address + '\'' +
//                ", city='" + city + '\'' +
//                ", state='" + state + '\'' +
//                '}';
//    }
}
