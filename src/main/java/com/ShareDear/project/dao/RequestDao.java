package com.ShareDear.project.dao;

import com.ShareDear.project.entities.User;

public interface RequestDao {
    void cancleRequest(User me, int from);

}
