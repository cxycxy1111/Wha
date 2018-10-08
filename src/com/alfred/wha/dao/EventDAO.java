package com.alfred.wha.dao;

import com.alfred.wha.util.Tool;
import com.alfred.wha.util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class EventDAO extends DAO{

    private static final int QRY_BY_CREATOR = 1;
    private static final int QRY_BY_EVENT = 2;
    private static final int QRY_BY_STATUS = 3;
    private SQLHelper helper = new SQLHelper();

    public EventDAO() {

    }

    /**
     * 新增
     * @param title
     * @param creator
     * @param creator_type
     * @param happen_time
     * @return
     */
    public boolean add(String title,long creator,int creator_type,String happen_time) {
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
                creator + "," +
                creator_type + ",'" +
                Tool.getTime() + "','" +
                happen_time + "')";
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
     * 删除
     * @param id
     * @return
     */
    public boolean recover(long id) {
        return executeSql("UPDATE event SET del=0 WHERE id=" + id);
    }

    /**
     * 修改
     * @param id
     * @param name
     * @param happen_time
     * @return
     */
    public boolean change(long id,String name,String happen_time) {
        return executeSql("UPDATE event SET title='" + name + "',happen_time='" + happen_time + "' WHERE id=" + id);
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
     * 通过标题查询是否存在
     * @param id
     * @param title
     * @return
     */
    public boolean isExist(long id,String title) {
        String sql = "SELECT * FROM event WHERE title = '" + title + "' AND id != " + id;
        return helper.query(sql).size() != 0;
    }

    /**
     * 通过标题查询是否存在
     * @param title
     * @return
     */
    public boolean isExist(String title) {
        String sql = "SELECT * FROM event WHERE title = '" + title + "'";
        return helper.query(sql).size() != 0;
    }

    /**
     * 通过ID查询是否存在
     * @param id
     * @return
     */
    public boolean isExist(long id) {
        String sql = "SELECT * FROM event WHERE id = " + id;
        return helper.query(sql).size() != 0;
    }

    /**
     * 通过创建者查询
     * @param creator
     * @param creator_type
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByCreator(long creator,int creator_type,int page_no,int length) {
        return complexQuery(QRY_BY_CREATOR,page_no,length,STATUS_IGNORE,DEL_NO,NULL,creator,creator_type);
    }

    public ArrayList<HashMap<String,Object>> simpleQuery() {
        return helper.query("SELECT id,title FROM event WHERE status=0 AND del=0");
    }

    /**
     * 通过事件ID查询
     * @param event_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByEvent(long event_id,int page_no,int length) {
        return complexQuery(QRY_BY_EVENT,page_no,length,STATUS_NORMAL,DEL_NO,event_id,NULL,USER_TYPE_MANAGER);
    }

    /**
     * 查询未通过
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryRejected(int page_no,int length) {
        return complexQuery(QRY_BY_STATUS,page_no,length,STATUS_REJECTED,DEL_NO,NULL,NULL,USER_TYPE_MANAGER);
    }

    /**
     * 查询已通过
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryPassed(int page_no,int length) {
        return complexQuery(QRY_BY_STATUS,page_no,length,STATUS_PASSED,DEL_NO,NULL,NULL,USER_TYPE_MANAGER);
    }

    /**
     * 查询未审核
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryUncheck(int page_no,int length) {
        return complexQuery(QRY_BY_STATUS,page_no,length,STATUS_NEEDCHECK,DEL_NO,NULL,NULL,USER_TYPE_MANAGER);
    }

    /**
     * 查询已删除
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryDeleted(int page_no,int length) {
        return complexQuery(QRY_BY_STATUS,page_no,length,STATUS_PASSED,DEL_YES,NULL,NULL,USER_TYPE_MANAGER);
    }
    /**
     * 复杂查询
     * @param queryType 1通过创建者查询 2通过具体ID查询 3通过状态查询
     * @param status
     * @param del
     * @param event_id
     * @param creator
     * @param creator_type
     * @return
     */
    public ArrayList<HashMap<String,Object>> complexQuery(int queryType,int page_no,int length,int status,int del,long event_id,long creator,int creator_type) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        switch (queryType) {
            case QRY_BY_CREATOR://通过创建者查询
                builder.append("e.id,")
                        .append("e.title,")
                        .append("e.del,")
                        .append("e.status,")
                        .append("e.happen_time,")
                        .append("e.subcribe_count,")
                        .append("FROM event e ");
                builder.append("LEFT JOIN user u ON e.creator=u.id ")
                        .append("LEFT JOIN admin_user au ON e.creator=au.id ");
                builder.append(" WHERE ")
                        .append("e.creator=")
                        .append(creator)
                        .append(" AND e.creator_type=")
                        .append(creator_type);
                break;
            case QRY_BY_EVENT://通过具体ID查询
                builder.append("e.id,")
                        .append("e.title,")
                        .append("e.del,")
                        .append("e.status,")
                        .append("e.happen_time,")
                        .append("e.subcribe_count,")
                        .append("e.creator_type,")
                        .append("e.creator,")
                        .append("trim(CASE e.creator_type WHEN 0 THEN au.nick_name ELSE u.nick_name END) nick_name,")
                        .append("trim(CASE e.creator_type WHEN 0 THEN au.icon ELSE u.icon END) icon ")
                        .append("FROM event e ");
                builder.append("LEFT JOIN user u ON e.creator=u.id ")
                        .append("LEFT JOIN admin_user au ON e.creator=au.id ");
                builder.append(" WHERE ");
                builder.append("e.id=").append(event_id);
                break;
            case QRY_BY_STATUS://通过状态查询
                builder.append("e.id,")
                        .append("e.title,")
                        .append("e.del,")
                        .append("e.status,")
                        .append("e.happen_time,")
                        .append("e.subcribe_count,")
                        .append("e.creator_type,")
                        .append("e.creator,")
                        .append("trim(CASE e.creator_type WHEN 0 THEN au.nick_name ELSE u.nick_name END) nick_name,")
                        .append("trim(CASE e.creator_type WHEN 0 THEN au.icon ELSE u.icon END) icon ")
                        .append("FROM event e ");
                builder.append("LEFT JOIN user u ON e.creator=u.id ")
                        .append("LEFT JOIN admin_user au ON e.creator=au.id ");
                builder.append(" WHERE ");
                if (del == 0){
                    builder.append("e.status=").append(status).append(" AND e.del=0 ");
                } else {
                    builder.append(" e.del=1");
                }
                builder.append(" ORDER BY e.id DESC ");
                builder.append(" LIMIT ").append((page_no-1)*length).append(",").append(length);
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
