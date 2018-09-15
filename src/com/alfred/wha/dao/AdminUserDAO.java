package com.alfred.wha.dao;

import com.alfred.wha.util.Tool;
import com.alfred.wha.util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminUserDAO extends DAO{

    private static final int QRY_DETAIL = 1;
    private static final int QRY_STATUS = 2;
    private SQLHelper helper = new SQLHelper();

    public AdminUserDAO() {

    }


    /**
     * 新增
     * @param company_id
     * @param username
     * @param pwd
     * @param type
     * @param email
     * @param creator
     * @return
     */
    public boolean add(long company_id,String username,String pwd,int type,String email,long creator) {

        String sql = "INSERT INTO admin_user (" +
                "company_id," +
                "user_name," +
                "pwd," +
                "del," +
                "type," +
                "status," +
                "nick_name," +
                "email," +
                "create_time," +
                "creator," +
                "icon) VALUES (" +
                company_id + ",'" +
                username + "','" +
                Tool.getMd5FromString(pwd) + "'," +
                "0," +
                type + "," +
                "0,'" +
                username + "','" +
                email + "','" +
                Tool.getTime() + "'," +
                creator + "," +
                "'defult_icon.png')";
        return executeSql(sql);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean delete(long id) {
        String sql = "UPDATE admin_user SET del=1 WHERE id=" + id;
        return executeSql(sql);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean recover(long id) {
        String sql = "UPDATE admin_user SET del=0cover WHERE id=" + id;
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
        return executeSql("UPDATE admin_user SET nick_name='" + new_nick_name + "',email='" + new_email+"',motto='" + new_motto + "' WHERE id=" +id);
    }

    /**
     * 修改密码
     * @param id
     * @param pwd
     * @return
     */
    public boolean updatePwd(long id,String pwd) {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE admin_user SET pwd='").append(Tool.getMd5FromString(pwd)).append("' WHERE id=").append(id);
        return executeSql(builder.toString());
    }

    /**
     * 修改头像
     * @param id
     * @param icon_name
     * @return
     */
    public boolean changeIcon(long id,String icon_name) {
        return executeSql("UPDATE admin_user SET icon='" + icon_name + "' WHERE id=" + id);
    }

    /**
     * 修改昵称
     * @param id
     * @param nick_name
     * @return
     */
    public boolean changeNickNameAndMotto(long id,String nick_name,String motto) {
        String builder = "UPDATE admin_user SET nick_name='" + nick_name +
                "',motto='" + motto + "' WHERE id=" + id;
        return executeSql(builder);
    }

    /**
     * 锁定
     * @param id
     * @return
     */
    public boolean lock(long id) {
        String sql = "UPDATE admin_user SET status=1 WHERE id=" + id;
        return executeSql(sql);
    }

    /**
     * 解锁
     * @param id
     * @return
     */
    public boolean unlock(long id) {
        String sql = "UPDATE admin_user SET status=0 WHERE id=" + id;
        return executeSql(sql);
    }

    /**
     * 通过用户名查询密码
     * @param user_name
     * @return
     */
    public String queryPwdByUserName(String user_name) {
        String sql = "SELECT pwd FROM admin_user WHERE user_name = '" + user_name + "'";
        return String.valueOf(helper.query(sql).get(0).get("pwd"));
    }

    /**
     * 通过用户名查询ID
     * @param user_name
     * @return
     */
    public String queryIdByUserName(String user_name) {
        return String.valueOf(helper.query("SELECT id FROM admin_user WHERE user_name='" + user_name + "'").get(0).get("id"));
    }

    /**
     * 通过用户名查询是否存在
     * @param user_name
     * @return
     */
    public boolean isExist(String user_name) {
        String sql = "SELECT * FROM admin_user WHERE user_name = '" + user_name + "'";
        if (helper.query(sql).size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 通过用户名查询是否存在
     * @param id
     * @return
     */
    public boolean isExist(long id) {
        String sql = "SELECT * FROM admin_user WHERE id = " + id;
        if (helper.query(sql).size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否已经删除
     * @param id
     * @return
     */
    public boolean isDel(long id) {
        String sql="SELECT del FROM admin_user WHERE id=" + id;
        return Tool.getBooleanFromArrayList(helper.query(sql),"del");
    }

    /**
     * 判断是否已被锁定
     * @param id
     * @return
     */
    public boolean isLocked(long id) {
        String sql = "SELECT status FROM admin_user WHERE id=" + id;
        if (Tool.getIntegerFromArrayList(helper.query(sql),"status") == 1) {
            return true;
        }
        return false;
    }

    public int queryTypeById(long id) {
        String sql = "SELECT type FROM admin_user WHERE id=" + id;
        return Tool.getIntegerFromArrayList(helper.query(sql),"type");
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryDetail(long id,int page_no,int length) {
        return complexQuery(QRY_DETAIL,page_no,length,id,DEL_NO,0);
    }

    /**
     * 查询已锁定的管理员
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryLocked(int page_no,int length) {
        return complexQuery(QRY_STATUS,page_no,length,0,DEL_NO,STATUS_LOCKED);
    }

    /**
     * 查询状态正常的管理员
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryNormal(int page_no,int length) {
        return complexQuery(QRY_STATUS,page_no,length,0,DEL_NO,STATUS_NORMAL);
    }

    /**
     * 查询已删除的管理员
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryDeleted(int page_no,int length) {
        return complexQuery(QRY_STATUS,page_no,length,0,DEL_YES,STATUS_NORMAL);
    }

    /**
     * 复杂查询
     * @param query_type
     * @param id
     * @param del
     * @param status
     * @return
     */
    private ArrayList<HashMap<String,Object>> complexQuery(int query_type,int page_no,int length,long id, int del, int status){
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");

        switch (query_type) {
            case QRY_DETAIL:
                builder.append("au.id,")
                        .append("au.nick_name,")
                        .append("au.user_name,")
                        .append("au.status,")
                        .append("au.del,")
                        .append("au.type,")
                        .append("au.email,")
                        .append("au.create_time,")
                        .append("au.icon,")
                        .append("au.motto")
                        .append("au.company_id,")
                        .append("c.name ");
                builder.append("FROM admin_user au");
                builder.append("LEFT JOIN company c ON au.id=company_id ");
                builder.append("WHERE au.id=").append(id);
                break;
            case QRY_STATUS:
                builder.append("au.id,")
                        .append("au.nick_name,")
                        .append("au.create_time,")
                        .append("au.status,")
                        .append("au.del,")
                        .append("au.type,")
                        .append("au.company_id,")
                        .append("c.name ");
                builder.append("FROM admin_user au ");
                builder.append("LEFT JOIN company c ON au.company_id=c.id ");
                if (del == 1) {
                    builder.append("WHERE au.del=1");
                }else {
                    builder.append("WHERE au.del=0 AND au.status=").append(status);
                }
                break;
                default:break;
        }
        builder.append(" LIMIT ").append((page_no-1)*length).append(",").append(length);
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
