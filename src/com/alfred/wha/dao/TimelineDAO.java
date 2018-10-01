package com.alfred.wha.dao;

import com.alfred.wha.util.Tool;
import com.alfred.wha.util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TimelineDAO extends DAO{

    private static final int QRY_BY_EVENT = 1;
    private static final int QRY_BY_CREATOR = 2;
    private static final int QRY_BY_DETAIL = 3;
    private static final int QRY_BY_STATUS = 4;
    private SQLHelper helper = new SQLHelper();

    public TimelineDAO() {

    }

    /**
     * 新增
     * @param event_id
     * @param title
     * @param content
     * @param creator
     * @param creator_type
     * @return
     */
    public boolean add(long event_id,String title,String content,long creator,int creator_type,String happen_time) {
        return executeSql("INSERT INTO timeline (" +
                "event_id," +
                "title," +
                "content," +
                "del," +
                "status," +
                "creator," +
                "creator_type," +
                "create_time,happen_time) VALUES (" +
                event_id +",'" +
                title + "','" +
                content + "'," +
                "0," +
                "1," +
                creator + "," +
                creator_type + ",'" +
                Tool.getTime() + "','" +
                happen_time + "')");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean delete(long id) {
        return executeSql("UPDATE timeline SET del=1 " +
                "WHERE id=" + id);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean recover(long id) {
        return executeSql("UPDATE timeline SET del=0 " +
                "WHERE id=" + id);
    }

    /**
     * 更新时间线
     * @param id
     * @param title
     * @param content
     * @param happen_time
     * @return
     */
    public boolean update(long id,long event_id,String title,String content,String happen_time) {
        return executeSql("UPDATE timeline SET " +
                "title='" + title + "'," +
                "content='" + content + "'," +
                "happen_time='" + happen_time + "'," +
                "event_id=" + event_id +
                " WHERE id=" + id);
    }

    /**
     * 审核通过
     * @param id
     * @return
     */
    public boolean pass(long id) {
        return updateStatus(String.valueOf(id),true);
    }

    /**
     * 审核不通过
     * @param id
     * @return
     */
    public boolean reject(long id) {
        return updateStatus(String.valueOf(id),false);
    }

    /**
     * 批量审核通过
     * @param ids
     * @return
     */
    public boolean batchPass(String ids){
        return updateStatus(ids,true);
    }

    /**
     * 批量审核不通过
     * @param ids
     * @return
     */
    public boolean batchReject(String ids) {
        return updateStatus(ids,false);
    }

    /**
     * 通过标题查询是否存在
     * @param id
     * @param title
     * @return
     */
    public boolean isExist(long id,String title) {
        String sql = "SELECT * FROM timeline WHERE title = '" + title + "' AND id != " + id;
        return helper.query(sql).size() != 0;
    }

    /**
     * 通过标题查询是否存在
     * @param title
     * @return
     */
    public boolean isExist(String title) {
        String sql = "SELECT * FROM timeline WHERE title = '" + title + "'";
        return helper.query(sql).size() != 0;
    }

    /**
     * 通过ID查询是否存在
     * @param id
     * @return
     */
    public boolean isExist(long id) {
        String sql = "SELECT * FROM timeline WHERE id = " + id;
        return helper.query(sql).size() != 0;
    }

    /**
     * 通过事件查询时间线，按时间线发生事件倒序排列
     * @param event_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByEvent(long event_id,int page_no,int length) {
        return complexQuery(QRY_BY_EVENT,page_no,length,NULL,event_id,NULL,NULL,DEL_NO,STATUS_NORMAL);
    }

    /**
     * 通过创建人查询时间线，按创建事件倒序排列
     * @param creator
     * @param creator_type
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByCreator(long creator,int creator_type,int page_no,int length) {
        return complexQuery(QRY_BY_CREATOR,page_no,length,NULL,NULL,creator,creator_type,DEL_NO,STATUS_NORMAL);
    }

    /**
     * 通过时间线ID查询具体情况
     * @param timeline
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByTimeline(long timeline,int page_no,int length) {
        return complexQuery(QRY_BY_DETAIL,page_no,length,timeline,NULL,NULL,NULL,DEL_NO,STATUS_NORMAL);
    }

    public ArrayList<HashMap<String,Object>> queryNormal(int page_no,int length) {
        return complexQuery(QRY_BY_STATUS,page_no,length,NULL,NULL,NULL,NULL,DEL_NO,STATUS_NORMAL);
    }

    public ArrayList<HashMap<String,Object>> queryRejected(int page_no,int length) {
        return complexQuery(QRY_BY_STATUS,page_no,length,NULL,NULL,NULL,NULL,DEL_NO,STATUS_REJECTED);
    }

    public ArrayList<HashMap<String,Object>> queryUnchecked(int page_no,int length) {
        return complexQuery(QRY_BY_STATUS,page_no,length,NULL,NULL,NULL,NULL,DEL_NO,STATUS_NEEDCHECK);
    }
    public ArrayList<HashMap<String,Object>> queryDeleted(int page_no,int length) {
        return complexQuery(QRY_BY_STATUS,page_no,length,NULL,NULL,NULL,NULL,DEL_YES,STATUS_NORMAL);
    }

    /**
     * 复杂查询
     * @param queryType 查询类型 1：通过事件查询时间线 2：通过创建人查询时间线 3：通过时间线ID查询
     * @param id
     * @param creator
     * @param event_id
     * @param creator_type 用户类型 0：管理员 1：用户
     * @return
     */
    private ArrayList<HashMap<String,Object>> complexQuery(int queryType,int page_no,int length,long id,long event_id,long creator,int creator_type,int del,int status) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("t.id,")
                .append("t.title,")
                .append("t.happen_time,");
        if (queryType != QRY_BY_DETAIL) {
            switch (queryType) {//字段
                case QRY_BY_CREATOR:
                    builder.append("substring(t.content,50)")
                            .append("FROM timeline t ");
                    break;
                default:
                    builder.append("t.creator,")
                            .append("t.creator_type,")
                            .append("(CASE WHEN t.creator_type=0 THEN au.nick_name ELSE u.nick_name END),")
                            .append("(CASE WHEN t.creator_type=0 THEN au.icon ELSE u.icon END) ");
                    builder.append("FROM timeline t ");
                    break;
            }
            builder.append("LEFT JOIN event e ON t.event_id=e.id ")
                    .append("LEFT JOIN admin_user au ON t.creator=au.id ")
                    .append("LEFT JOIN user u ON t.creator=u.id ");
            switch (queryType) {//条件
                case QRY_BY_CREATOR:
                    builder.append(" WHERE t.creator=").append(creator)
                            .append(" AND t.creator_type=").append(creator_type)
                            .append(" ORDER BY t.create_time DESC");
                    break;
                case QRY_BY_EVENT:
                    builder.append("WHERE t.event_id=").append(event_id)
                            .append(" AND t.del=0 ")//未删除
                            .append("AND t.status=0 ")//已过审
                            .append("ORDER BY t.happen_time DESC");
                    break;
                case QRY_BY_STATUS:
                    if (del == 1) {
                        builder.append("WHERE t.del=1");
                    }else {
                        builder.append("WHERE t.del=0 AND t.status=").append(status);
                    }
                    builder.append(" ORDER BY t.id DESC");
                    break;
                default:break;
            }

        }else {//详情
            builder.append("t.content,")
                    .append("t.creator,")
                    .append("t.creator_type,")
                    .append("(CASE WHEN t.creator_type=0 THEN au.nick_name ELSE u.nick_name END),")
                    .append("(CASE WHEN t.creator_type=0 THEN au.icon ELSE u.icon END),")
                    .append("truncate(e.id,0) event_id,")
                    .append("trim(e.title) event_title,")
                    .append("trim(e.happen_time) event_happen_time ")
                    .append("FROM timeline t ")
                    .append("LEFT JOIN event e ON t.event_id=e.id ")
                    .append("LEFT JOIN admin_user au ON t.creator=au.id ")
                    .append("LEFT JOIN user u ON t.creator=u.id ");
            builder.append(" WHERE t.id=")
                    .append(id);
        }
        builder.append(" LIMIT ").append((page_no-1)*length).append(",").append(length);
        return helper.query(builder.toString());
    }

    /**
     * 更新状态通用SQL
     * @param ids id字符串
     * @param pass 是否通过
     * @return
     */
    private boolean updateStatus(String ids,boolean pass) {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE timeline SET status=");
        if (pass) {
            builder.append("0");
        }else {
            builder.append("2");
        }
        builder.append(" WHERE id IN (").append(ids).append(")");
        return executeSql(builder.toString());
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
