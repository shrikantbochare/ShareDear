package com.ShareDear.project.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "req_id")
    private int req_id;

    @Column(name = "status")
    private String status;

    @Column(name = "from_user_id")
    private int from_user_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User to_user_id;

    public Request() {
    }

    public Request(String status, int from_user_id, User to_user_id) {

        this.status = status;
        this.from_user_id = from_user_id;
        this.to_user_id = to_user_id;
    }

    public int getReq_id() {
        return req_id;
    }

    public void setReq_id(int req_id) {
        this.req_id = req_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(int from_user_id) {
        this.from_user_id = from_user_id;
    }

    public User getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(User to_user_id) {
        this.to_user_id = to_user_id;
    }

//    @Override
//    public String toString() {
//        return "Request{" +
//                "req_id=" + req_id +
//                ", status='" + status + '\'' +
//                ", from_user_id=" + from_user_id +
//                '}';
//    }
}
