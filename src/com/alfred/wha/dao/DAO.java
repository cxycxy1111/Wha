package com.alfred.wha.dao;

public class DAO {

    static final int NULL=0;

    static final int STATUS_NORMAL = 0;
    static final int STATUS_LOCKED = 1;

    static final int STATUS_PASSED = 0;
    static final int STATUS_NEEDCHECK = 1;
    static final int STATUS_REJECTED = 2;
    static final int STATUS_IGNORE = -1;

    static final int USER_TYPE_MANAGER=0;
    static final int USER_TYPE_USER=1;

    static final boolean DEL_YES=true;
    static final boolean DEL_NO=false;

    public DAO() {

    }

}
