package com.alfred.wha.dao;

import com.alfred.wha.util.SQLHelper;
import com.alfred.wha.util.Tool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DiscussionPageUserFavouriteDAO extends DAO{

    private SQLHelper helper = new SQLHelper();

    public DiscussionPageUserFavouriteDAO() {
        super();
    }

    public boolean addFavourite(long discussion_page_id,long user_id) {
        boolean b = false;
        try {
            b = helper.update("INSERT INTO discussion_page_user_favourite (user_id,discussion_page_id,del,create_time)" +
                    " VALUES ("+ user_id+ "," + discussion_page_id + ",0,'" + Tool.getTime() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return b;
    }


    public boolean deleteFavourite(long discussion_page_id,long user_id) {
        boolean b = false;
        try {
            b = helper.update("UPDATE discussion_page_user_favourite SET del = 1 WHERE user_id =" + user_id +
                    " AND discussion_page_id="+ discussion_page_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return b;
    }

    public boolean updateFavourite(long discussion_page_id,long user_id) {
        boolean b = false;
        try {
            b = helper.update("UPDATE discussion_page_user_favourite SET del = 0 WHERE user_id =" + user_id +
                    " AND discussion_page_id="+ discussion_page_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return b;
    }

    public boolean isExist(long discussion_page_id,long user_id) {
        boolean b = false;
        b = helper.query("SELECT * FROM  discussion_page_user_favourite WHERE user_id =" + user_id +
                " AND discussion_page_id="+ discussion_page_id+ "").size() != 0;
        return b;
    }

    public boolean isDeleted(long discussion_page_id,long user_id) {
        ArrayList<HashMap<String,Object>> list = new ArrayList<>();
        list = helper.query("SELECT * FROM  discussion_page_user_favourite WHERE user_id =" + user_id +
                " AND discussion_page_id="+ discussion_page_id+ " AND del=1");
        if (list.size() == 0) {
            return false;
        }else {
            return true;
        }
    }

    public ArrayList<HashMap<String,Object>> queryFavouriteListByUserId(long user_id) {
        ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        list = helper.query("SELECT b.id,b.title,b.summary FROM discussion_page_user_favourite a " +
                "LEFT JOIN discussion_page b ON a.discussion_page_id=b.id " +
                "WHERE a.del = 0 AND b.del=0 AND b.status=0 AND a.user_id=" + user_id + " " +
                "ORDER BY a.create_time DESC ");
        return list;
    }

    public ArrayList<HashMap<String,Object>> queryByDiscussionIds(long user_id,String ids) {
        return helper.query("SELECT * FROM discussion_page_user_favourite " +
                "WHERE user_id=" + user_id + " AND discussion_page_id IN ("+ids + ") AND del=0");
    }


}
