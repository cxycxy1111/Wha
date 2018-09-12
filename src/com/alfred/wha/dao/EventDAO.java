package com.alfred.wha.dao;

import com.alfred.wha.util.MethodTool;
import com.alfred.wha.util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class EventDAO extends DAO{

    private SQLHelper helper = new SQLHelper();

    public EventDAO() {

    }

    /**
     * 新增
     * @param title
     * @param creator_id
     * @param creator_type
     * @param happen_time
     * @return
     */
    public boolean add(String title,long creator_id,int creator_type,String happen_time) {
        String sql = "INSERT INTO event (title," +
                "del," +
                "status," +
                "subcribe_count," +
                "creator," +
                "creator_type," +
                "create_time,happen_time) VALUES ('" +
                title + "'," +
                "0," +
                "1," +
                "0," +
                creator_id + "," +
                creator_type + ",'" +
                MethodTool.getTime() + "','" +
                happen_time + "'";
        return executeSql(sql);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean delete(long id) {
        return executeSql("UPDATE event SET del=1 WHERE id=" + id);
    }

    /**
     * 修改
     * @param id
     * @param name
     * @param happen_time
     * @return
     */
    public boolean change(long id,String name,String happen_time) {
        return executeSql("UPDATE event SET name='" + name + "',happen_time='" + happen_time + "' WHERE id=" + id);
    }

    /**
     * 通过审核
     * @param id
     * @return
     */
    public boolean pass(long id) {
        return executeSql("UPDATE event SET status=0 WHERE id=" + id);
    }

    /**
     * 未通过审核
     * @param id
     * @return
     */
    public boolean reject(long id) {
        return executeSql("UPDATE event SET status=2 WHERE id=" + id);
    }

    /**
     * 通过创建者查询
     * @param creator
     * @param creator_type
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByCreator(long creator,int creator_type) {
        return complexQuery(1,STATUS_IGNORE,DEL_NO,0,creator,creator_type);
    }

    /**
     * 通过事件ID查询
     * @param event_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByEvent(long event_id) {
        return complexQuery(2,STATUS_NORMAL,DEL_NO,event_id,0,USER_TYPE_MANAGER);
    }

    /**
     * 通过状态查询
     * @param status
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByStatus(int status) {
        return complexQuery(3,status,DEL_NO,0,0,USER_TYPE_MANAGER);
    }

    /**
     * 复杂查询
     * @param queryType 1通过创建者查询 2通过具体ID查询 3通过状态查询
     * @param status
     * @param del
     * @param event_id
     * @param creator_id
     * @param creator_type
     * @return
     */
    public ArrayList<HashMap<String,Object>> complexQuery(int queryType,int status,boolean del,long event_id,long creator_id,int creator_type) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        switch (queryType) {
            case 1://通过创建者查询
                builder.append("e.id,")
                        .append("e.title,")
                        .append("e.del")
                        .append("e.status")
                        .append("e.happen_time")
                        .append("e.subcribe_count")
                        .append("FROM event e ");
                builder.append("LEFT JOIN user u ON e.creator_id=u.id ")
                        .append("LEFT JOIN admin_user au ON e.creator=au.id ");
                builder.append(" WHERE ")
                        .append("e.creator_id=")
                        .append(creator_id)
                        .append(" AND e.creator_type=")
                        .append(creator_type);
                break;
            case 2://通过具体ID查询
                builder.append("e.id,")
                        .append("e.title,")
                        .append("e.del")
                        .append("e.status")
                        .append("e.happen_time")
                        .append("e.subcribe_count")
                        .append("e.creator_type")
                        .append("e.creator,")
                        .append("CASE WHEN e.creator_type=0 THEN au.nick_name ELSE u.nick_name END,")
                        .append("CASE WHEN e.creator_type=0 THEN au.icon ELSE u.icon END ")
                        .append("FROM event e ");
                builder.append("LEFT JOIN user u ON e.creator_id=u.id ")
                        .append("LEFT JOIN admin_user au ON e.creator=au.id ");
                builder.append(" WHERE ");
                builder.append("e.id=").append(event_id);
                break;
            case 3://通过状态查询
                builder.append("e.id,")
                        .append("e.title,")
                        .append("e.del")
                        .append("e.status")
                        .append("e.happen_time")
                        .append("e.subcribe_count")
                        .append("e.creator_type")
                        .append("e.creator,")
                        .append("CASE WHEN e.creator_type=0 THEN au.nick_name ELSE u.nick_name END,")
                        .append("CASE WHEN e.creator_type=0 THEN au.icon ELSE u.icon END ")
                        .append("FROM event e ");
                builder.append("LEFT JOIN user u ON e.creator_id=u.id ")
                        .append("LEFT JOIN admin_user au ON e.creator=au.id ");
                builder.append(" WHERE ");

                if (!del){
                    builder.append("e.status=").append(status).append(" AND ");
                }
                builder.append(" e.del=").append(del);
                builder.append(" ORDER BY e.id DESC");
            default:break;
        }
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
