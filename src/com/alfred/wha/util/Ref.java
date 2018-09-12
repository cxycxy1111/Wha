package com.alfred.wha.util;

public class Ref {

    public static final String prefix = "{\"stat\":\"";
    public static final String id_prefix = "{\"id\":";
    public static final String dataprefix = "{\"data\":";
    public static final String suffix = "\"}";
    public static final String datasuffix = "}";
    public static final String NSR = prefix + "no_such_record" + suffix;
    public static final String LOCKED = prefix + "locked" + suffix;
    public static final String EXE_FAIL =prefix +  "exe_fail" + suffix;
    public static final String EXE_PARTLY_FAIL =prefix +  "exe_partly_fail" + suffix;
    public static final String EXE_SUC = prefix + "exe_suc" + suffix;
    public static final String DUPLICATE = prefix + "duplicate" + suffix;
    public static final String INST_NOT_MATCH = prefix + "institution_not_match" + suffix;
    public static final String BALANCE_NOT_ENOUGH = prefix + "balance_not_enough" + suffix;
    public static final String NOT_MATCH = prefix + "not_match" + suffix;
    public static final String QRY_RESULT_EMPTY_RESULT = prefix + "empty_result" + suffix;
    public static final String SESSION_EXPIRED=prefix + "session_expired" + suffix;
    public static final String AUTHORIZE_FAIL=prefix + "authorize_fail" + suffix;
    public static final String COURSEPLAN_EXPIRED = prefix + "courseplan_expired" + suffix;
    public static final String MEMBER_CARD_EXPIRED = prefix + "member_card_expired" + suffix;
    public static final String REST_TIMES_NOT_ENOUGH = prefix + "rest_times_not_enough" + suffix;

    public static final int TYPE_MEMBER_COURSE = 1;
    public static final int TYPE_TRAINNER_COURSE = 2;
    public static final int TYPE_COLLECTION_COURSE = 3;
    public static final int TYPE_PRIVATE_COURSE = 4;

    public static final int TYPE_OPERATE_ADD = 1;
    public static final int TYPE_OPERATE_REMOVE = 2;
    public static final int TYPE_OPERATE_MODIFY = 3;

    public static final int TYPE_COURSE_PLAN_BOOK_STATE_UNBOOK = 0;
    public static final int TYPE_COURSE_PLAN_BOOK_STATE_BOOKED = 1;
    public static final int TYPE_COURSE_PLAN_BOOK_STATE_ATTENDED = 2;


    public static final int TYPE_BALANCE_CARD = 1;
    public static final int TYPE_TIMES_CARD = 2;
    public static final int TYPE_TIME_CARD = 3;

    public static final String SC_ALLOW_VIEW_TEACHER = "allow_view_teacher";
    public static final String SC_ALLOW_MANAGE_TEACHER = "allow_manage_teacher";
    public static final String SC_ALLOW_VIEW_CLASSROOM = "allow_view_classroom";
    public static final String SC_ALLOW_MANAGE_CLASSROOM = "allow_manage_classroom";
    public static final String SC_ALLOW_VIEW_CARD_TYPE = "allow_view_card_type";
    public static final String SC_ALLOW_MANAGE_CARD_TYPE = "allow_manage_card_type";
    public static final String SC_ALLOW_MANAGE_COURSE = "allow_manage_course";
    public static final String SC_ALLOW_MANAGE_COURSEPLAN = "allow_manage_courseplan";
    public static final String SC_ALLOW_VIEW_MEMBER = "allow_view_member";
    public static final String SC_ALLOW_MANAGE_MEMBER = "allow_manage_member";
    public static final String SC_ALLOW_VIEW_MEMBER_CARD = "allow_view_member_card";
    public static final String SC_ALLOW_MANAGE_MEMBER_CARD = "allow_manage_member_card";
    public static final String SC_ALLOW_DEDUCT_AFTER_ARREARANGE = "allow_deduct_after_arrearage";
    public static final String SC_ALLOW_DEDUCT_AFTER_OVERDUE = "allow_deduct_after_overdue";
    public static final String SC_ALLOW_BOOK_AFTER_ARREARANGE = "allow_book_after_arrearage";
    public static final String SC_ALLOW_BOOK_AFTER_OVERDUE = "allow_book_after_overdue";
    public static final String SC_ALLOW_ATTENDANCE_AFTER_ARREARANGE = "allow_attendance_after_arrearage";
    public static final String SC_ALLOW_ATTENDANCE_AFTER_OVERDUE = "allow_attendance_after_overdue";

}
