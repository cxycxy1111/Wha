package com.alfred.wha.serv;

public class Service {

    static final String prefix = "{\"stat\":\"";
    static final String id_prefix = "{\"id\":";
    static final String datasuffix = "}";
    static final String dataprefix = "{\"data\":";
    static final String suffix = "\"}";
    static final String NO_SUCH_RECORD = prefix + "no_such_record" + suffix;
    static final String LOCKED = prefix + "locked" + suffix;
    static final String FAIL =prefix +  "fail" + suffix;
    static final String PARTLY_FAIL =prefix +  "partly_fail" + suffix;
    static final String SUCCESS = prefix + "success" + suffix;
    static final String DUPLICATE = prefix + "duplicate" + suffix;
    static final String NOT_MATCH = prefix + "not_match" + suffix;
    static final String EMPTY = prefix + "empty" + suffix;
    static final String SESSION_EXPIRED=prefix + "session_expired" + suffix;
    static final String AUTHORIZE_FAIL=prefix + "authorize_fail" + suffix;

}
