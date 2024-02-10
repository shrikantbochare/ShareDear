package com.ShareDear.project.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int post_id;

    @Column(name = "img_loc")
    private String img_loc;

    @Column(name = "tag")
    private String tag;

    @Column(name = "post_date")
    private Date post_date;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user_id;

    public Post(User user_id) {
        this.user_id = user_id;
    }

    public Post() {
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getImg_loc() {
        return img_loc;
    }

    public void setImg_loc(String img_loc) {
        this.img_loc = img_loc;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getPost_date() {
        return post_date;
    }

    public void setPost_date(Date post_date) {
        this.post_date = post_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

//    @Override
//    public String toString() {
//        return "Post{" +
//                "post_id=" + post_id +
//                '}';
//    }
}
