package com.dci.service;

import com.dci.pojo.Msg;
import com.dci.pojo.User;

public interface UserInterface {
    int add(User user);
    void del(User user);
    User query(User user);
     Msg chuli(Boolean req, String token,User user);
    Msg  verifyInt(int  i);
}
