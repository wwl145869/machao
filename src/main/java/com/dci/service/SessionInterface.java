package com.dci.service;

import com.dci.pojo.Session;

public interface SessionInterface {

    void add(Session session);
    void del(Session session);
    Session query(Session session);

}
