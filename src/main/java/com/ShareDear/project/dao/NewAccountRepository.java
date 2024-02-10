package com.ShareDear.project.dao;

import com.ShareDear.project.entities.NewAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NewAccountRepository extends JpaRepository<NewAccount,Integer> {

    @Query("Select u from NewAccount u where u.email = :email order by u.acc_id desc limit 1")
    public NewAccount findByEmail(@Param("email") String email);

    public void deleteByEmail(String email);
}
