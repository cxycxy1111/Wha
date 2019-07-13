package com.alfred.wha.serv;

import com.alfred.wha.dao.LogDao;

public class Service {

    private LogDao logDao;
    private static final String prefix = "{\"status\":\"";
    private static final String id_prefix = "{\"id\":";
    private static final String datasuffix = "}";
    private static final String dataprefix = "{\"data\":";
    private static final String suffix = "\"}";
    static final String NO_SUCH_RECORD = prefix + "no_such_record" + suffix;
    static final String LOCKED = prefix + "locked" + suffix;
    static final String FAIL =prefix +  "fail" + suffix;
    static final String PARTLY_FAIL =prefix +  "partly_fail" + suffix;
    static final String SUCCESS = prefix + "success" + suffix;
    static final String DUPLICATE = prefix + "duplicate" + suffix;
    static final String NOT_MATCH = prefix + "not_match" + suffix;
    static final String QRY_RESULT_EMPTY = prefix + "empty" + suffix;
    static final String SESSION_EXPIRED=prefix + "session_expired" + suffix;
    static final String AUTHORIZE_FAIL=prefix + "authorize_fail" + suffix;

    static final int LOG_OPERATE_EDIT = 1;
    static final int LOG_OPERATE_CHANGE_ICON = 2;
    static final int LOG_OPERATE_CHANGE_PWD = 3;
    static final int LOG_OPERATE_DELETE = 4;
    static final int LOG_OPERATE_RECOVER = 5;
    static final int LOG_OPERATE_LOCK = 6;
    static final int LOG_OPERATE_UNLOCK = 7;
    static final int LOG_OPERATE_PASS = 8;
    static final int LOG_OPERATE_REJECT = 9;


    static final String path_icon = "/usr/local/Whale/UserIcon/";
    static final String path_timeline_content = "/usr/local/Whale/Timeline/";
    static final String path_page_content = "/usr/local/Whale/Pages/";

    public Service() {
        logDao = new LogDao();
    }

}
