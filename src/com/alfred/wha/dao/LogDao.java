package com.alfred.wha.dao;

import com.alfred.wha.util.SQLHelper;
import com.alfred.wha.util.Tool;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.sql.SQLException;

public class LogDao{

    private static SQLHelper helper = new SQLHelper();

    /**
     *
     * @param id
     * @param operate_type
     * @param operator
     * @param operator_type
     * @param content
     * @return
     */
    public static void recordAdminUserLog(long id,int operate_type,long operator,int operator_type,String content) {
        add("admin_user",id,operate_type,operator,operator_type,content);
    }

    public static void recordUserLog(long id,int operate_type,long operator,int operator_type,String content) {
        add("user",id,operate_type,operator,operator_type,content);
    }

    public static void recordEventLog(long id,int operate_type,long operator,int operator_type,String content) {
        add("event",id,operate_type,operator,operator_type,content);
    }

    public static void recordTimelineLog(long id,int operate_type,long operator,int operator_type,String content) {
        add("timeline",id,operate_type,operator,operator_type,content);
    }

    private static void add(String table_name,long id,int operate_type,long operator,int operator_type,String content) {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(table_name)
                .append("_log ").append("(").append(table_name).append("_id,operate_type,operator,operator_type,operate_time,content) VALUES(")
                .append(id).append(",")
                .append(operate_type).append(",")
                .append(operator).append(",")
                .append(operator_type).append(",'")
                .append(Tool.getTime()).append("','")
                .append(content).append("')");
        executeSql(builder.toString());
    }

    private static void executeSql(String sql) {
        boolean b = false;
        try {
            b = helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
