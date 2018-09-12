package com.alfred.wha.dao;

import com.alfred.wha.util.SQLHelper;

import java.sql.SQLException;

public class AdminUserLoginLogDAO extends DAO{

    private SQLHelper helper = new SQLHelper();

    public AdminUserLoginLogDAO() {

    }

    /**
     * 记录登录日志
     * @param input_user_name
     * @param input_pwd
     * @param is_sucess
     * @param login_time
     * @param login_ip
     * @param system_version
     * @param system_model
     * @param device_brand
     * @param app_version
     * @return
     */
    public boolean recordLoginLog(String input_user_name,String input_pwd, int is_sucess,String login_time,
                                  String login_ip,String system_version,String system_model,String device_brand,
                                  String app_version) {
        String sql = "INSERT INTO user_login_log (input_user_name,input_pwd,is_sucess,login_time," +
                "login_ip,system_version,system_model,device_brand,app_version) VALUES ('" +
                input_user_name + "','" + input_pwd + "'," + is_sucess + ",'" + login_time + "','" + login_ip
                + "','" + system_version + "','" + system_model + "','" + device_brand + "','" + app_version + "'";
        boolean b = false;
        try {
            b = helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

}
