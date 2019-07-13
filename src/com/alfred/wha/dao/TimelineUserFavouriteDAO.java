package com.alfred.wha.dao;

import com.alfred.wha.util.SQLHelper;
import com.alfred.wha.util.Tool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TimelineUserFavouriteDAO extends DAO{

    private SQLHelper helper = new SQLHelper();

    public TimelineUserFavouriteDAO () {
        super();
    }

    public boolean addFavourite(long timeline_id,long user_id) {
        boolean b = false;
        try {
            b = helper.update("INSERT INTO timeline_user_favourite (user_id,timeline_id,del,create_time)" +
                    " VALUES ("+ user_id+ "," + timeline_id + ",0,'" + Tool.getTime() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return b;
    }


    public boolean deleteFavourite(long timeline_id,long user_id) {
        boolean b = false;
        try {
            b = helper.update("UPDATE timeline_user_favourite SET del = 1 WHERE user_id =" + user_id +
                    " AND timeline_id="+ timeline_id+ "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return b;
    }

    public boolean updateFavourite(long timeline_id,long user_id) {
        boolean b = false;
        try {
            b = helper.update("UPDATE timeline_user_favourite SET del = 0 WHERE user_id =" + user_id +
                    " AND timeline_id="+ timeline_id+ "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return b;
    }

    public boolean isExist(long timeline_id,long user_id) {
        boolean b = false;
        b = helper.query("SELECT * FROM  timeline_user_favourite WHERE user_id =" + user_id +
                " AND timeline_id="+ timeline_id+ "").size() != 0;
        return b;
    }

    public boolean isDeleted(long timeline_id,long user_id) {
        boolean b = false;
        b = helper.query("SELECT * FROM  timeline_user_favourite WHERE user_id =" + user_id +
                " AND timeline_id="+ timeline_id+ " AND del=1").size() != 0;
        return b;
    }

    public ArrayList<HashMap<String,Object>> queryFavouriteListByUserId(long user_id,int page_no) {
        int a = (page_no-1)*20;
        ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        list = helper.query("SELECT b.id,b.title,b.summary,b.happen_time FROM timeline_user_favourite a " +
                "LEFT JOIN timeline b ON a.timeline_id=b.id " +
                "WHERE a.del = 0 AND b.del=0 AND b.status=0 AND a.user_id=" + user_id + " " +
                "ORDER BY a.create_time DESC " +
                "LIMIT " + a + ",20");
        return list;
    }


}
