package com.ShareDear.project.dao;

import com.ShareDear.project.entities.Request;
import com.ShareDear.project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request,Integer> {

    @Query("From Request where to_user_id=:data")
    public List<Request> findRequestsByUser(@Param("data") User user);

    @Query("From Request where from_user_id=:data")
    public List<Request> findRequestsByUserById(@Param("data") int id);



}
