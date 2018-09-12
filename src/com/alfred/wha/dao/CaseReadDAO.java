package com.alfred.wha.dao;

import com.alfred.wha.util.Tool;
import com.alfred.wha.util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CaseReadDAO extends DAO{

    private static final int QRY_BY_USER = 1;
    private static final int QRY_BY_CASE = 2;
    private SQLHelper helper = new SQLHelper();

    public CaseReadDAO() {

    }

    /**
     * 增加
     * @param case_id
     * @param user_id
     * @param user_type
     * @return
     */
    public boolean add(long case_id,long user_id,long user_type) {
        return executeSql("INSERT INTO case_read (user_id,user_type,case_id,create_time) VALUES (" + user_id + "," + user_type + "," + case_id + ",'" + Tool.getTime() + "')");
    }

    /**
     * 按案例ID查询
     * @param case_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByCase(long case_id) {
        return complexQuery(QRY_BY_CASE,case_id,0,0);
    }

    public ArrayList<HashMap<String,Object>> queryByUser(long user_id,int user_type) {
        return complexQuery(QRY_BY_USER,0,user_id,user_type);
    }

    /**
     * 通用查询
     * @param queryType 1按案例ID查询 2按用户ID查询
     * @param case_id 案例ID
     * @param user_id 用户ID
     * @param user_type 用户类型 0管理员 1用户
     * @return
     */
    private ArrayList<HashMap<String,Object>> complexQuery(int queryType,long case_id,long user_id,int user_type) {
        StringBuilder builder = new StringBuilder();
        switch (queryType) {
            case QRY_BY_CASE://按案例ID查询
                builder.append("SELECT cr.user_type,")//用户类型
                        .append("CASE WHEN cr.user_type=0 THEN au.nick_name ELSE u.nick_name END,")
                        .append("CASE WHEN cr.user_type=0 THEN au.icon ELSE u.icon END,")
                        .append("cr.user_id,")
                        .append("create_time ")
                        .append("FROM case_read cr " +
                                "LEFT JOIN cases c ON cr.id=c.id " +
                                "LEFT JOIN admin_user au ON cr.user_id=au.id " +
                                "LEFT JOIN user u ON cr.user_id=u.id ");
                builder.append("WHERE cr.case_id=").append(case_id);
                break;
            case QRY_BY_USER://按阅读的用户ID查询
                builder.append("SELECT cr.user_type,")//用户类型
                        .append("CASE WHEN c.creator=0 THEN au.nick_name ELSE u.nick_name END,")//案例创建者的昵称
                        .append("CASE WHEN c.creator=0 THEN au.icon ELSE u.icon END,")//案例创建者的头像
                        .append("cr.user_id,")
                        .append("create_time ")
                        .append("FROM case_read cr " +
                                "LEFT JOIN cases c ON cr.id=c.id " +
                                "LEFT JOIN admin_user au ON c.creator=au.id " +
                                "LEFT JOIN user u ON c.creator=u.id ");
                builder.append("WHERE cr.user_id=").append(user_id).append(" AND user_type=").append(user_type);
        }
        builder.append(" ORDER BY create_time DESC");
        return helper.query(builder.toString());
    }

    private boolean executeSql(String sql) {
        boolean b = false;
        try {
            b = helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

}
