package com.alfred.wha.dao;

import com.alfred.wha.util.SQLHelper;
import com.alfred.wha.util.Tool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PageDAO extends DAO{

    private SQLHelper helper = new SQLHelper();

    public PageDAO() {

    }

    /**
     * 新增文章
     * @param event_id
     * @param page_title
     * @param page_summary
     * @param status
     * @param user_id
     * @param user_type
     * @param author
     * @param source
     * @param happen_time
     * @return
     */
    public boolean add(long event_id,String page_title,String page_summary,int status,long user_id,int user_type,String author,String source,String happen_time,String url) {
        boolean b = false;
        try {
            b = helper.update("INSERT INTO page (event_id,page_title,page_summary,status,user_id,user_type,favourite_count,author,source,create_time,happen_time,del,page_url) VALUES ("
            +event_id + ",'" + page_title + "','" + page_summary + "'," + status + "," + user_id + "," + user_type + ",0,'" + author +
                    "','" + source + "','" + Tool.getTime() + "','" + happen_time + "',0,'" + url + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    public boolean delete(long id) {
        boolean b = false;
        try {
            b = helper.update("UPDATE page SET del = 1 WHERE id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 更新
     * @param id
     * @param page_title
     * @param page_summary
     * @param author
     * @param source
     * @param url
     * @return
     */
    public boolean update (long id,String page_title,String page_summary,String author,String source,String url) {
        boolean b = false;
        try {
            b = helper.update("UPDATE page SET " +
                    "page_title='" + page_title + "'," +
                    "page_summary='" + page_summary + "'," +
                    "author='" + author + "'," +
                    "source='" + source + "', " +
                    "page_url='" + url + "' " +
                    "WHERE id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }


    public void upvotePage(long id) {
        boolean b = false;
        try {
            b = helper.update("UPDATE page SET " +
                    "favourite_count=(" +
                        "SELECT i.favourite_count FROM (" +
                            "SELECT favourite_count FROM page " +
                            "WHERE id=" +id + ") i)+1 " +
                    "WHERE id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void downvotePage(long id) {
        boolean b = false;
        try {
            b = helper.update("UPDATE page SET " +
                    "favourite_count=(" +
                        "SELECT i.favourite_count FROM (" +
                            "SELECT favourite_count FROM page " +
                            "WHERE id=" +id + ") i)-1 " +
                    "WHERE id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询文章
     * @param page_no
     * @param length
     * @return
     */
    public ArrayList<HashMap<String,Object>> query(int page_no,int length) {
        int a = (page_no-1)*length;
        return helper.query("SELECT " +
                "a.user_type," +
                "a.page_url," +
                "a.id," +
                "a.event_id," +
                "a.page_title," +
                "a.page_summary," +
                "a.happen_time," +
                "trim(CASE a.user_type WHEN 1 THEN c.nick_name ELSE a.author END) author," +
                "trim(CASE a.user_type WHEN 1 THEN '今日简讯' ELSE a.source END) source," +
                "b.title," +
                "trim(CASE a.user_type WHEN 0 THEN d.nick_name ELSE c.nick_name END) nick_name FROM page a " +
                "LEFT JOIN event b ON a.event_id=b.id " +
                "LEFT JOIN user c ON a.user_id=c.id " +
                "LEFT JOIN admin_user d ON a.user_id=d.id " +
                "WHERE b.del=0 AND b.status=0 AND a.del=0 AND a.status=0 " +
                "ORDER BY a.happen_time DESC " +
                "LIMIT " + a +"," + length);
    }

    /**
     * 通过事件ID查询相关文章
     * @param event_id
     * @param page_no
     * @param length
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByEvent(long event_id,int page_no,int length) {
        int a = (page_no-1)*length;
        return helper.query("SELECT " +
                "a.user_type," +
                "a.page_url," +
                "a.id," +
                "a.event_id," +
                "a.page_title," +
                "a.page_summary," +
                "a.happen_time," +
                "trim(CASE a.user_type WHEN 1 THEN c.nick_name ELSE a.author END) author," +
                "trim(CASE a.user_type WHEN 1 THEN '今日简讯' ELSE a.source END) source," +
                "trim(CASE a.user_type WHEN 0 THEN d.nick_name ELSE c.nick_name END) nick_name FROM page a " +
                "LEFT JOIN event b ON a.event_id=b.id " +
                "LEFT JOIN user c ON a.user_id=c.id " +
                "LEFT JOIN admin_user d ON a.user_id=d.id " +
                "WHERE a.del=0 AND a.status=0 AND a.event_id=" + event_id + " " +
                "ORDER BY a.happen_time DESC " +
                "LIMIT " + a +"," + length);
    }

    /**
     * 通过文章ID查询文章详情
     * @param id 文章ID
     * @param page_no
     * @param length
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByPage(long id,int page_no,int length) {
        int a = (page_no-1)*length;
        return helper.query("SELECT a.favourite_count," +
                "a.user_type," +
                "a.page_url," +
                "a.id," +
                "a.event_id," +
                "a.page_title," +
                "a.page_summary," +
                "a.happen_time," +
                "trim(CASE a.user_type WHEN 1 THEN c.nick_name ELSE a.author END) author," +
                "trim(CASE a.user_type WHEN 1 THEN '今日简讯' ELSE a.source END) source," +
                "trim(CASE a.user_type WHEN 0 THEN d.nick_name ELSE c.nick_name END) nick_name FROM page a " +
                "LEFT JOIN event b ON a.event_id=b.id " +
                "LEFT JOIN user c ON a.user_id=c.id " +
                "LEFT JOIN admin_user d ON a.user_id=d.id " +
                "WHERE b.del=0 AND b.status=0 AND a.del=0 AND a.status=0 AND a.id=" + id + " " +
                "ORDER BY a.happen_time DESC " +
                "LIMIT " + a +"," + length);
    }

    /**
     * 通过类型查询相关文章
     * @param type
     * @param page_no
     * @param length
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByType(String type,int page_no,int length) {
        int a = (page_no-1)*length;
        return helper.query("SELECT " +
                "a.user_type," +
                "a.page_url," +
                "a.id," +
                "a.event_id," +
                "a.page_title," +
                "a.page_summary," +
                "a.happen_time," +
                "trim(CASE a.user_type WHEN 1 THEN c.nick_name ELSE a.author END) author," +
                "trim(CASE a.user_type WHEN 1 THEN '今日简讯' ELSE a.source END) source," +
                "trim(CASE a.user_type WHEN 0 THEN d.nick_name ELSE c.nick_name END) nick_name FROM page a " +
                "LEFT JOIN event b ON a.event_id=b.id " +
                "LEFT JOIN user c ON a.user_id=c.id " +
                "LEFT JOIN admin_user d ON a.user_id=d.id " +
                "WHERE b.del=0 AND b.status=0 AND a.del=0 AND a.status=0 AND b.type IN ( " + type + ") " +
                "ORDER BY a.happen_time DESC " +
                "LIMIT " + a + "," + length);
    }

    public ArrayList<HashMap<String,Object>> queryByCreator(long user_id,int user_type,int page_no,int length) {
        int count = (page_no-1)*length;
        return helper.query("SELECT * FROM page " +
                "WHERE user_id=" + user_id + " AND user_type=" + user_type + " AND del=0 " +
                "ORDER BY create_time DESC " +
                "LIMIT " + count + "," + length);
    }

    /**
     * 查询最大的ID
     * @return
     */
    public int queryMaxId() {
        return Tool.getIntegerFromArrayList(helper.query("SELECT max(id) FROM page"),"max(id)");
    }
}
