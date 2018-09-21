package com.alfred.wha.dao;

import com.alfred.wha.util.Tool;
import com.alfred.wha.util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CaseDAO extends DAO{

    private static final int QRY_SELF_CREATE=1;
    private static final int QRY_STATUS=2;
    private static final int QRY_SELF_SUBCRIBE=3;
    private static final int QRY_DETAIL=4;

    private SQLHelper helper = new SQLHelper();

    public CaseDAO() {

    }

    /**
     * 新增
     * @param event_id
     * @param title
     * @param content
     * @param creator
     * @param creator_type 0管理员 1用户
     * @return
     */
    public boolean add(long event_id,String title,String content,long creator,int creator_type) {

        String sql = "INSERT INTO cases (" +
                "event_id," +
                "title," +
                "content," +
                "del," +
                "status," +
                "upvote_count," +
                "downvote_count," +
                "view_count," +
                "creator," +
                "creator_type," +
                "create_time," +
                "update_time) VALUES (" +
                event_id + ",'" +
                title + "','" +
                content + "'," +
                "0," +
                "1," +
                "0," +
                "0," +
                "0," +
                creator + "," +
                creator_type + ",'" +
                Tool.getTime() + "'," + Tool.getTime() + ")";

        return executeSql(sql);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean delete(long id) {
        String sql = "UPDATE del=1 WHERE id=" + id;
        return executeSql(sql);
    }

    /**
     * 恢复
     * @param id
     * @return
     */
    public boolean recover(long id) {
        String sql = "UPDATE del=0 WHERE id=" + id;
        return executeSql(sql);
    }

    /**
     * 审核通过
     * @param id
     * @return
     */
    public boolean pass(long id) {
        String sql = "UPDATE status=0 WHERE id=" + id;
        return executeSql(sql);
    }

    /**
     * 审核通过
     * @param id
     * @return
     */
    public boolean reject(long id) {
        String sql = "UPDATE status=2 WHERE id=" + id;
        return executeSql(sql);
    }

    /**
     * 更新
     * @param id
     * @param title
     * @param content
     * @return
     */
    public boolean update(long id,String title,String content) {
        String sql = "UPDATE cases SET " +
                "title='" + title + "'," +
                "content='" + content + "'," +
                "update_time='" + Tool.getTime() + "' " +
                "WHERE id=" + id;
        return executeSql(sql);
    }

    /**
     * 更新浏览数
     * @param id
     * @return
     */
    public boolean updateViewCount(long id) {
        String sql = "UPDATE cases SET view_count = CASE WHEN view_count IS NULL THEN 0 ELSE view_count + 1 END " +
                "WHERE id=" + id;
        return executeSql(sql);
    }

    /**
     * 更新赞同数
     * @param id
     * @return
     */
    public boolean updateUpvoteCount(long id,boolean is_plus) {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE cases SET upvote_count = CASE WHEN upvote_count IS NULL THEN 0 ELSE upvote_count ");
        if (is_plus) {
            builder.append("+");
        }else {
            builder.append("-");
        }
        builder.append("1 END WHERE id=").append(id);
        return executeSql(builder.toString());
    }

    /**
     * 更新反对数
     * @param id
     * @return
     */
    public boolean updateDownvoteCount(long id,boolean is_plus) {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE cases SET downvote_count = CASE WHEN downvote_count IS NULL THEN 0 ELSE downvote_count ");
        if (is_plus) {
            builder.append("+");
        }else {
            builder.append("-");
        }
        builder.append("1 END WHERE id=").append(id);
        return executeSql(builder.toString());
    }

    /**
     * 通过标题查询是否存在
     * @param id
     * @param title
     * @return
     */
    public boolean isExist(long id,String title) {
        String sql = "SELECT * FROM cases WHERE title = '" + title + "' AND id != " + id;
        return helper.query(sql).size() != 0;
    }

    /**
     * 通过标题查询是否存在
     * @param title
     * @return
     */
    public boolean isExist(String title) {
        String sql = "SELECT * FROM cases WHERE title = '" + title + "'";
        return helper.query(sql).size() != 0;
    }

    /**
     * 通过ID查询是否存在
     * @param id
     * @return
     */
    public boolean isExist(long id) {
        String sql = "SELECT * FROM cases WHERE id = " + id;
        return helper.query(sql).size() != 0;
    }

    /**
     * 查询自己订阅的事件的新案例
     * @param user_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> querySelfSubcribe(long user_id,int user_type,int page_no,int length) {
        return complexQuery(QRY_SELF_SUBCRIBE,page_no,length,user_id,user_type,NULL,STATUS_PASSED,DEL_NO);
    }

    /**
     * 查询自己创建的案例
     * @param user_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> querySelfCreate(long user_id,int user_type,int page_no,int length) {
        return complexQuery(QRY_SELF_CREATE,page_no,length,user_id,user_type,NULL,STATUS_PASSED,DEL_NO);
    }

    /**
     * 查询案例详情
     * @param case_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryDetail(long case_id,int page_no,int length) {
        return complexQuery(QRY_DETAIL,page_no,length,NULL,USER_TYPE_MANAGER,case_id,STATUS_PASSED,DEL_NO);
    }

    /**
     * 查询未审核的案例列表
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryUnchecked(int page_no,int length) {
        return complexQuery(QRY_STATUS,page_no,length,NULL,USER_TYPE_MANAGER,NULL,STATUS_NEEDCHECK,DEL_NO);
    }

    /**
     * 查询已删除的案例列表
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryDeleted(int page_no,int length) {
        return complexQuery(QRY_STATUS,page_no,length,NULL,USER_TYPE_MANAGER,NULL,STATUS_PASSED,DEL_YES);
    }

    /**
     * 查询已通过的案例列表
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryPassed(int page_no,int length) {
        return complexQuery(QRY_STATUS,page_no,length,NULL,USER_TYPE_MANAGER,NULL,STATUS_PASSED,DEL_NO);
    }

    /**
     * 查询未通过的案例列表
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryRejected(int page_no,int length) {
        return complexQuery(QRY_STATUS,page_no,length,NULL,USER_TYPE_MANAGER,NULL,STATUS_REJECTED,DEL_NO);
    }

    /**
     * 复杂查询
     * @param queryType 1用户所关注的事件列表 2通过创建者ID 3通过案例的ID 4按del与status查询
     * @param user_id
     * @param user_type 0管理员 1用户
     * @param case_id
     * @return
     */
    private ArrayList<HashMap<String,Object>> complexQuery(int queryType,int page_no,int length,long user_id,int user_type,long case_id,int status,int del) {
        StringBuilder builder = new StringBuilder();
        if (queryType == QRY_DETAIL) {
            builder.append("SELECT c.id,")//案例ID
                    .append("c.title,")//案例标题
                    .append("truncate(e.id,0) event_id,")//事件ID
                    .append("trim(e.title) event_title,")//事件标题
                    .append("CASE WHEN c.creator_type=0 THEN truncate(au.id 0) user_id ELSE truncate(u.id 0) creator END,")//创建者ID
                    .append("CASE WHEN c.creator_type=0 THEN trim(au.icon) creator_icon ELSE trim(u.icon) creator_icon END,")//创建者头像
                    .append("truncate(c.happen_time) case_happen_time,")//案例发生时间
                    .append("truncate(c.create_time) case_create_time,")//案例创建时间
                    .append("c.update_time,")//更新时间
                    .append("c.upvote_count,")//赞成计数
                    .append("c.downvote_count,")//反对计数
                    .append("c.view_count,")//阅读量
                    .append("c.content ");//内容
            builder.append("FROM cases c ");
            builder.append("LEFT JOIN event e ON c.event_id=e.id ")
                    .append("LEFT JOIN user u ON c.creator=u.id ")
                    .append("LEFT JOIN admin_user au ON c.creator=au.id ");
            builder.append("WHERE c.id=").append(case_id);
        } else {
            builder.append("SELECT c.id,")//案例ID
                    .append("c.title,")//案例标题
                    .append("c.creator,")//创建者ID
                    .append("c.creator_type,")//创建者类型
                    .append("truncate(c.happen_time) case_happen_time,")//案例发生时间
                    .append("truncate(c.create_time) case_create_time,")//案例创建时间
                    .append("c.update_time,")//更新时间
                    .append("c.upvote_count,")//赞成计数
                    .append("c.downvote_count,")//反对计数
                    .append("c.view_count,")//阅读量
                    .append("truncate(e.id,0) event_id,")//事件ID
                    .append("trim(e.title) event_title,")//事件标题
                    .append("CASE WHEN c.creator_type=0 THEN au.nick_name ELSE u.nick_name END,")//创建者ID
                    .append("CASE WHEN c.creator_type=0 THEN au.icon ELSE u.icon END,")//创建者头像
                    //是否读取全内容
                    .append("substring(c.content,1,50) ");//内容
            builder.append("FROM cases c ");
            switch (queryType) {
                case QRY_SELF_SUBCRIBE:
                    builder.append("LEFT JOIN event e ON c.event_id=e.id ")
                            .append("LEFT JOIN user u ON c.creator=u.id ")
                            .append("LEFT JOIN admin_user au ON c.creator=au.id ");
                    builder.append("WHERE event_id IN (SELECT event_id FROM event_subscribe WHERE ")
                            .append("user_id=").append(user_id)
                            .append(" AND user_type=").append(user_type)
                            .append(")");
                    break;
                case QRY_SELF_CREATE:
                    builder.append("LEFT JOIN event e ON c.event_id=e.id ");
                    builder.append("WHERE c.creator=").append(user_id)
                            .append(" AND c.user_type=").append(user_type);
                    break;
                case QRY_STATUS:
                    builder.append("LEFT JOIN event e ON c.event_id=e.id ")
                            .append("LEFT JOIN user u ON c.creator=u.id ")
                            .append("LEFT JOIN admin_user au ON c.creator=au.id ");
                    if (del == 0) {
                        builder.append("e.status=").append(status).append(" AND ");
                    } else {
                        builder.append("e.del=").append(del);
                    }
                    break;
                default:break;
            }
            builder.append(" ORDER BY c.id DESC ");
            builder.append("LIMIT ").append((page_no-1)*length).append(",").append(length);
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
