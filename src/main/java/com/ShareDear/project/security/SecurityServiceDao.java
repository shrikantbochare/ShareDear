package com.ShareDear.project.security;

import com.ShareDear.project.entities.User;

public interface SecurityServiceDao {

    public void encryptPassword(User user);
}
