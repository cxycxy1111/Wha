package com.alfred.wha.dao;

import com.alfred.wha.util.SQLHelper;
import com.alfred.wha.util.Tool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ResetPasswordDAO extends DAO{

    private SQLHelper helper = new SQLHelper();

    public ResetPasswordDAO() {
        super();
    }

    //新增匹配记录
    public boolean add(String email_address,String code) {
        boolean b = false;
        try {
            b =  helper.update("INSERT INTO reset_password (email_address,reset_code,is_validate,total_match_times,create_time) " +
                    "VALUES ('" + email_address + "','" + code + "',1,0,'" + Tool.getTime() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    //查询可用匹配记录
    public ArrayList<HashMap<String,Object>> queryValidList(String email_adress) {
        return helper.query("SELECT * FROM reset_password WHERE email_address = '" + email_adress + "' AND is_validate = 1");
    }

    //更新总匹配次数
    public void updateTotalMatchTimes (String email_address) {
        boolean b = false;
        ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        list = this.queryValidList(email_address);
        int i = Tool.getIntegerFromArrayList(list,"total_match_times");
        int j = i+1;
        try {
            b = helper.update("UPDATE reset_password SET total_match_times = " + j + " WHERE id=" + Tool.getLongFromArrayList(list,"id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int queryTotalMatchTimes(String email_address) {
        ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        list = helper.query("SELECT total_match_times FROM reset_password WHERE email_address = '" + email_address + "' AND is_validate = 1");
        return Tool.getIntegerFromArrayList(list,"total_match_times");
    }

    //使匹配记录失效
    public void invalidate (long id) {
        boolean b = false;
        try {
            b = helper.update("UPDATE reset_password SET is_validate = 0 WHERE id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
