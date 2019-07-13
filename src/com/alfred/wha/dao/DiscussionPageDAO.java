package com.alfred.wha.dao;

import com.alfred.wha.util.SQLHelper;
import com.alfred.wha.util.Tool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DiscussionPageDAO extends DAO{

    private SQLHelper helper = new SQLHelper();

    public DiscussionPageDAO() {

    }

    /**
     * 新增
     * @param page_id
     * @param parent_discussion_id
     * @param event_id
     * @param user_id
     * @param summary
     * @param anonymous
     * @return
     */
    public boolean add(long page_id,long parent_discussion_id,long event_id,long user_id,int user_type,int source,String summary,int anonymous) {
        boolean b = false;
        String sql = "";
        if (source == 0) {
            sql = "INSERT INTO discussion_page (page_id,parent_discussion_id,event_id,user_id,user_type,source,discussion_summary,del,status,favourite_count,create_time,anonymous) " +
                    "VALUES (" + page_id + "," + parent_discussion_id + ","+ event_id + "," + user_id + "," + user_type + "," +
                    "CASE WHEN (" +
                        "SELECT i.source FROM (" +
                            "SELECT source FROM discussion_page " +
                            "WHERE page_id=" + page_id + " ORDER BY source DESC LIMIT 0,1) i)>=1 " +
                    "THEN (" +
                        "SELECT i.source FROM (" +
                            "SELECT source " +
                            "FROM discussion_page " +
                            "WHERE page_id=" + page_id + " ORDER BY source DESC LIMIT 0,1) i)+1 " +
                    "ELSE 1 END,'" + summary + "',0,1,0,'" + Tool.getTime() + "'," + anonymous + ")";
        }else  {
            sql = "INSERT INTO discussion_page (page_id,parent_discussion_id,event_id,user_id,user_type,source,discussion_summary,del,status,favourite_count,create_time,anonymous) " +
                    "VALUES (" + page_id + "," + parent_discussion_id + ","+ event_id + "," + user_id + "," + user_type + "," + source + ",'" + summary + "',0,1,0,'" + Tool.getTime() + "'," + anonymous + ")";
        }
        try {
            b = helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean delete(long id) {
        boolean b = false;
        try {
            b = helper.update("UPDATE discussion_page SET del=1 WHERE id=1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    public boolean addReport(long discussion_id,int type) throws SQLException {
        return helper.update("INSERT INTO discussion_page_reported (discussion_page_id,type,create_time) VALUES (" + discussion_id +"," +type + ",'" + Tool.getTime() + "')");
    }

    public void upvoteFavourite(long id) {
        boolean b = false;
        try {
            b = helper.update("UPDATE discussion_page SET " +
                    "favourite_count=(" +
                    "SELECT i.favourite_count FROM (" +
                    "SELECT favourite_count FROM discussion_page " +
                    "WHERE id=" +id + ") i)+1 " +
                    "WHERE id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void downvoteFavourite(long id) {
        boolean b = false;
        try {
            b = helper.update("UPDATE discussion_page SET " +
                    "favourite_count=(" +
                    "SELECT i.favourite_count FROM (" +
                    "SELECT favourite_count FROM discussion_page " +
                    "WHERE id=" +id + ") i)-1 " +
                    "WHERE id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过时间线查询
     * @param page_id
     * @param page_no
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByPage(long page_id, int page_no) {
        int a = (page_no-1)*20;
        return helper.query("SELECT " +
                "a.id," +
                "a.event_id," +
                "a.discussion_summary," +
                "a.favourite_count," +
                "a.anonymous," +
                "a.source," +
                "a.create_time," +
                "a.parent_discussion_id," +
                "a.user_id," +
                "trim(CASE a.user_type WHEN 0 THEN d.nick_name ELSE c.nick_name END) replier_nick_name," +
                "truncate((CASE a.user_type WHEN 0 THEN d.id ELSE c.id END),0) replier_user_id," +
                "trim(CASE b.user_type WHEN 0 THEN f.nick_name ELSE e.nick_name END) nick_name," +
                "truncate((CASE b.user_type WHEN 0 THEN f.id ELSE e.id END),0) user_id " +
                "FROM discussion_page a " +
                "LEFT JOIN discussion_page b ON a.parent_discussion_id=b.id " +
                "LEFT JOIN user c ON a.user_id=c.id " +
                "LEFT JOIN admin_user d ON a.user_id=d.id " +
                "LEFT JOIN user e ON b.user_id=e.id " +
                "LEFT JOIN admin_user f ON b.user_id=f.id " +
                "WHERE a.page_id=" + page_id + " " +
                "ORDER BY a.create_time DESC " +
                "LIMIT "+ a + ",20");
    }

    public ArrayList<HashMap<String,Object>> queryByDiscussionId(long id) {
        return helper.query("SELECT * FROM discussion_page WHERE id=" + id);
    }

    /**
     * 通过来源查询
     * @param source
     * @param page_no
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryBySource(int source, int page_no) {
        int a = (page_no-1)*20;
        return helper.query("SELECT " +
                "a.discussion_summary," +
                "a.favourite_count," +
                "a.anonymous," +
                "a.source," +
                "a.create_time," +
                "a.parent_discussion_id," +
                "a.user_id," +
                "trim(CASE a.user_type WHEN 0 THEN d.nick_name ELSE c.nick_name END) replier_nick_name," +
                "truncate((CASE a.user_type WHEN 0 THEN d.id ELSE c.id END),0) replier_user_id," +
                "trim(CASE b.user_type WHEN 0 THEN f.nick_name ELSE e.nick_name END) nick_name," +
                "truncate((CASE b.user_type WHEN 0 THEN f.id ELSE f.id END),0) user_id " +
                "FROM discussion_page a " +
                "LEFT JOIN discussion_page b ON a.parent_discussion_id=b.id " +
                "LEFT JOIN user c ON a.user_id=c.id " +
                "LEFT JOIN admin_user d ON a.user_id=d.id " +
                "LEFT JOIN user e ON b.user_id=e.id " +
                "LEFT JOIN admin_user f ON b.user_id=f.id " +
                "WHERE a.source=" + source + " " +
                "ORDER BY a.happen_time DESC " +
                "LIMIT "+ a + ",20");
    }

    /**
     * 查询最大的source
     * @param page_id
     * @return
     */
    public int queryMaxSource(long page_id) {
        return Tool.getIntegerFromArrayList(helper.query("SELECT " +
                "source " +
                "FROM discussion_page " +
                "WHERE page_id= " + page_id +
                "ORDER BY source DESC"),"source");
    }

}
