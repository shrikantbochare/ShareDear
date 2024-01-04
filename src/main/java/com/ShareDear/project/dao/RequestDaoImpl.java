package com.ShareDear.project.dao;

import com.ShareDear.project.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RequestDaoImpl implements RequestDao{

    private EntityManager entityManager;

    public RequestDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public void cancleRequest(User me, int from) {
        Query theQuery = entityManager.createQuery("Delete From Request where from_user_id=:fromData And to_user_id=:meData ");

        int result = theQuery.setParameter("meData",me).setParameter("fromData",from).executeUpdate();

    }
}
