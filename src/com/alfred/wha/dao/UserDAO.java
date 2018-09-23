package com.alfred.wha.dao;

import com.alfred.wha.util.Tool;
import com.alfred.wha.util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserDAO extends DAO{

    private static final int QRY_DETAIL = 1;
    private static final int QRY_STATUS = 2;

    private SQLHelper helper = new SQLHelper();

    public UserDAO() {

    }

    /**
     * 新增
     * @param username
     * @param pwd
     * @param email
     * @return
     */
    public boolean add(String username,String pwd,String nick_name,String email,String motto) {
        String sql = "INSERT INTO user (" +
                "user_name," +
                "pwd," +
                "del," +
                "status," +
                "nick_name," +
                "email," +
                "create_time," +
                "icon,motto) VALUES ('"
                + username + "','" +
                pwd + "'," +
                "0," +
                "0,'" +
                nick_name + "','" +
                email + "','" +
                Tool.getTime() + "'," +
                "'default_icon.png','" + motto +
                "')";
        return executeSql(sql);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean delete(long id) {
        String sql = "UPDATE user SET del=1 WHERE id=" + id;
        return executeSql(sql);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean recover(long id) {
        String sql = "UPDATE user SET del=0 WHERE id=" + id;
        return executeSql(sql);
    }

    /**
     * 锁定
     * @param id
     * @return
     */
    public boolean lock(long id) {
        String sql = "UPDATE user SET status=1 WHERE id=" + id;
        return executeSql(sql);
    }

    /**
     * 解锁
     * @param id
     * @return
     */
    public boolean unlock(long id) {
        String sql = "UPDATE user SET status=0 WHERE id=" + id;
        return executeSql(sql);
    }

    /**
     * 修改资料
     * @param id
     * @param new_nick_name
     * @param new_email
     * @param new_motto
     * @return
     */
    public boolean updateInfo(long id,String new_nick_name,String new_email,String new_motto) {
        return executeSql("UPDATE user SET nick_name='" + new_nick_name + "',email='" + new_email+"',motto='" + new_motto + "' WHERE id=" + id);
    }

    /**
     * 修改密码
     * @param id
     * @param pwd
     * @return
     */
    public boolean updatePwd(long id,String pwd) {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE user SET pwd='").append(Tool.getMd5FromString(pwd)).append("' WHERE id=").append(id);
        return executeSql(builder.toString());
    }

    /**
     * 修改头像
     * @param id
     * @param icon_name
     * @return
     */
    public boolean changeIcon(long id,String icon_name) {
        return executeSql("UPDATE user SET icon='" + icon_name + "' WHERE id=" + id);
    }

    /**
     * 修改昵称
     * @param id
     * @param nick_name
     * @return
     */
    public boolean changeNickName(long id,String nick_name) {
        return executeSql("UPDATE user SET nick_name='" + nick_name + "' WHERE id=" + id);
    }

    /**
     * 修改签名
     * @param id
     * @param motto
     * @return
     */
    public boolean changeMotto(long id,String motto) {
        return executeSql("UPDATE user SET motto='" + motto + "' WHERE id=" + id);
    }


    /**
     * 通过用户名查询密码
     * @param user_name
     * @return
     */
    public String queryPwdByUsername(String user_name) {
        String sql = "SELECT pwd FROM user WHERE user_name = '" + user_name + "'";
        return Tool.getStringFromArrayList(helper.query(sql),"pwd");
    }

    /**
     * 通过用户名查询id
     * @param user_name
     * @return
     */
    public long queryIdByUserName(String user_name) {
        String sql = "SELECT id FROM user WHERE user_name='" + user_name + "'";
        return Tool.getLongFromArrayList(helper.query(sql),"id");
    }

    public ArrayList<HashMap<String,Object>> queryDetail(long id,int page_no,int length) {
        return complexQuery(QRY_DETAIL,page_no,length,id,DEL_NO,STATUS_NORMAL);
    }

    public ArrayList<HashMap<String,Object>> queryLocked(int page_no,int length) {
        return complexQuery(QRY_STATUS,page_no,length,NULL,DEL_NO,STATUS_LOCKED);
    }

    public ArrayList<HashMap<String,Object>> queryNormal(int page_no,int length) {
        return complexQuery(QRY_STATUS,page_no,length,NULL,DEL_NO,STATUS_NORMAL);
    }

    public ArrayList<HashMap<String,Object>> queryDeleted(int page_no,int length) {
        return complexQuery(QRY_STATUS,page_no,length,NULL,DEL_YES,STATUS_NORMAL);
    }


    /**
     * 复杂查询
     * @param query_type
     * @param id
     * @param del
     * @param status
     * @return
     */
    private ArrayList<HashMap<String,Object>> complexQuery(int query_type,int page_no,int length,long id,int del,int status){
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        switch (query_type) {
            case QRY_DETAIL:
                builder.append("id,")
                        .append("nick_name,")
                        .append("user_name,")
                        .append("status,")
                        .append("del,")
                        .append("email,")
                        .append("create_time,")
                        .append("icon,")
                        .append("motto ");
                builder.append("FROM user ");
                builder.append("WHERE id=").append(id);
                break;
            case QRY_STATUS:
                builder.append("id,")
                        .append("nick_name,")
                        .append("create_time,")
                        .append("status,")
                        .append("del ");
                builder.append("FROM user ");
                builder.append("WHERE ");
                if (del == 0) {
                    builder.append("status=").append(status).append(" AND ");
                }
                builder.append("del=").append(del);
                break;
        }
        builder.append(" LIMIT ").append((page_no-1)*length).append(",").append(length);
        return helper.query(builder.toString());
    }

    /**
     * 通过用户名查询是否存在
     * @param user_name
     * @return
     */
    public boolean isExist(String user_name) {
        String sql = "SELECT * FROM user WHERE user_name = '" + user_name + "'";
        return helper.query(sql).size() != 0;
    }

    /**
     * 通过用户名查询是否存在
     * @param id
     * @return
     */
    public boolean isExist(long id) {
        String sql = "SELECT * FROM user WHERE id = " + id;
        return helper.query(sql).size() != 0;
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
