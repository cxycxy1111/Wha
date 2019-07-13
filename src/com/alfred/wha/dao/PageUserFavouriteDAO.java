package com.alfred.wha.dao;

import com.alfred.wha.util.SQLHelper;
import com.alfred.wha.util.Tool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PageUserFavouriteDAO extends DAO{

    private SQLHelper helper = new SQLHelper();

    public PageUserFavouriteDAO() {
        super();
    }

    public boolean addFavourite(long page_id,long user_id) {
        boolean b = false;
        try {
            b = helper.update("INSERT INTO page_favourite (user_id,page_id,status,del,create_time)" +
                    " VALUES ("+ user_id+ "," + page_id + ",0,0,'" + Tool.getTime() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return b;
    }


    public boolean deleteFavourite(long page_id,long user_id) {
        boolean b = false;
        try {
            b = helper.update("UPDATE page_favourite SET del = 1 WHERE user_id =" + user_id +
                    " AND page_id="+ page_id+ "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return b;
    }

    public boolean updateFavourite(long page_id,long user_id) {
        boolean b = false;
        try {
            b = helper.update("UPDATE page_favourite SET del = 0 WHERE user_id =" + user_id +
                    " AND page_id="+ page_id+ "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return b;
    }

    public boolean isExist(long page_id,long user_id) {
        boolean b = false;
        b = helper.query("SELECT * FROM  page_favourite WHERE user_id =" + user_id +
                " AND page_id=" + page_id+ "").size() != 0;
        return b;
    }

    public boolean isDeleted(long page_id,long user_id) {
        boolean b = false;
        b = helper.query("SELECT * FROM  page_favourite WHERE user_id =" + user_id +
                " AND page_id="+ page_id+ " AND del=1").size() != 0;
        return b;
    }

    public ArrayList<HashMap<String,Object>> queryFavouriteListByUserId(long user_id,int page_no) {
        int a = (page_no-1)*20;
        ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        list = helper.query("SELECT b.id," +
                "b.source," +
                "b.page_url," +
                "b.page_title," +
                "b.page_summary," +
                "b.happen_time," +
                "b.event_id," +
                "trim(CASE b.user_type WHEN 1 THEN '今日简讯' ELSE b.source END) source," +
                "trim(CASE b.user_type WHEN 1 THEN c.nick_name ELSE b.author END) author " +
                "FROM page_favourite a " +
                "LEFT JOIN page b ON a.page_id=b.id " +
                "LEFT JOIN user c ON b.user_id = c.id " +
                "LEFT JOIN admin_user d ON b.user_id=d.id " +
                "WHERE a.del = 0 AND b.del=0 AND b.status=0 AND a.user_id=" + user_id + " " +
                "ORDER BY a.create_time DESC " +
                "LIMIT " + a + ",20");
        return list;
    }


}
