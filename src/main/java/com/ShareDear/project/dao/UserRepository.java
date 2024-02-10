package com.ShareDear.project.dao;

import com.ShareDear.project.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByUsername(String username);

    public Optional<User> findByEmail(String email);
    public Page<User> findByUsernameContainingOrNameContaining(String name1,String name, Pageable pageable);

    public  Page<User> findByIdNotIn(Set<Integer> resultSet,Pageable pageable);

    public  Page<User> findByIdIn(List<Integer> userIds,Pageable pageable);
}
