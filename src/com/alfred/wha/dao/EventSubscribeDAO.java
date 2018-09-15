package com.alfred.wha.dao;

import com.alfred.wha.util.Tool;
import com.alfred.wha.util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class EventSubscribeDAO extends DAO{

    private static final int QRY_BY_EVENT = 1;
    private static final int QRY_BY_SUBCRIBE_USER=2;
    private SQLHelper helper = new SQLHelper();

    public EventSubscribeDAO() {

    }

    /**
     * 关注
     * @param user_id
     * @param event_id
     * @return
     */
    public boolean subcribe(long user_id,int user_type,long event_id) {
        return executeSql("INSERT INTO event_subscribe (" +
                "event_id," +
                "user_id," +
                "user_type" +
                "subscribe_time) VALUES (" +
                event_id + "," +
                user_id + ","+
                user_type + ",'" +
                Tool.getTime() + "')");
    }

    /**
     * 取消关注
     * @param user_id
     * @param event_id
     * @return
     */
    public boolean unsubcribe(long user_id,long user_type,long event_id) {
        return executeSql("DELETE FROM event_subscribe " +
                "WHERE " +
                "user_id=" + user_id +
                " AND user_type=" + user_type +
                " AND event_id=" + event_id);
    }

    /**
     * 查询是否关注
     * @param user_id
     * @param event_id
     * @return
     */
    public boolean isSubcribe(long user_id,int user_type,long event_id) {
        return helper.query("SELECT * FROM event_subscribe " +
                "WHERE user_id=" + user_id + " AND event_id=" + event_id).size() !=0;
    }

    /**
     * 通过用户查询所关注的事件列表
     * @param user_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByUser(long user_id,int user_type,int page_no,int length) {
        return complexQuery(QRY_BY_SUBCRIBE_USER,page_no,length,user_id,user_type,0);
    }

    /**
     * 根据事件查询事件列表
     * @param event_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByEvent(long event_id,int page_no,int length) {
        return complexQuery(QRY_BY_EVENT,page_no,length,0,0,event_id);
    }

    /**
     * 复杂查询
     * @param queryType 1通过事件ID查询 2通过用户ID查询
     * @param user_id
     * @param user_type
     * @param event_id
     * @return
     */
    private ArrayList<HashMap<String,Object>> complexQuery(int queryType,int page_no,int length,long user_id,int user_type,long event_id) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        switch (queryType) {
            case QRY_BY_EVENT:
                builder.append("es.user_id,")
                        .append("es.user_type,")
                        .append("CASE WHEN es.user_type=0 THEN au.nick_name ELSE u.nick_name END,")
                        .append("CASE WHEN es.user_type=0 THEN au.icon ELSE u.icon END ");
                builder.append("FROM event_subscribe es ");
                builder.append("LEFT JOIN user u ON es.user_id=u.id ")
                        .append("LEFT JOIN admin_user au ON es.user_id=au.id ");
                builder.append("WHERE es.event_id=").append(event_id);
                break;
            case QRY_BY_SUBCRIBE_USER:
                builder.append("es.event_id,")
                        .append("e.title,")
                        .append("e.subcribe_count,")
                        .append("e.create_time,")
                        .append("e.happen_time ");
                builder.append("FROM event_subscribe es ");
                builder.append("LEFT JOIN event e ON es.event_id=e.id ");
                builder.append("WHERE es.user_id=").append(user_id)
                        .append(" AND es.user_type=").append(user_type)
                        .append(" AND e.del=0");
                break;
            default:break;
        }
        builder.append(" ORDER BY es.id DESC");
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
