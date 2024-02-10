package com.ShareDear.project.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "profile")
public class ProfilePic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="img")
    private String img;

    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User user_id;


    public ProfilePic() {
    }

    public ProfilePic(String img, User user_id) {
        this.img = img;
        this.user_id = user_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

//    @Override
//    public String toString() {
//        return "ProfilePic{" +
//                "id=" + id +
//                ", img='" + img + '\'' +
//                ", user_id=" + user_id +
//                '}';
//    }
}
