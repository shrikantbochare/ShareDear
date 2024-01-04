package com.ShareDear.project.dao;

import com.ShareDear.project.entities.Post;
import com.ShareDear.project.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository  extends JpaRepository<Post,Integer> {


    @Query("From Post where user_id=:data")
    public Page<Post> findPostsOfUserByIdOrderByPost_dateDesc(@Param("data") User user, Pageable pageable);
    @Query("From Post where user_id in :data")
    public Page<Post> findPostsByUsersOrderByPost_dateDesc(@Param("data") List<User> users, Pageable pageable);
}
