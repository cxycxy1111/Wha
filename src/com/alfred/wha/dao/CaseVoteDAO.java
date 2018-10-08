package com.alfred.wha.dao;

import com.alfred.wha.util.Tool;
import com.alfred.wha.util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CaseVoteDAO extends DAO{

    private static final int QRY_BY_USER = 1;
    private static final int QRY_BY_CASE = 2;
    private SQLHelper helper = new SQLHelper();

    public CaseVoteDAO() {

    }

    /**
     * 首次点赞
     * @param case_id
     * @param user_id
     * @param user_type
     * @return
     */
    public boolean newUpvote(long case_id,long user_id,int user_type) {
        return executeSql("INSERT INTO case_vote (user_id," +
                "user_type," +
                "case_id," +
                "vote_type," +
                "create_time," +
                "last_modify_time," +
                "del) VALUES ("+
                user_id+","+
                user_type + "," + case_id + ",0,'" + Tool.getTime() + "','"+ Tool.getTime() + "',0)");
    }

    /**
     * 首次反对
     * @param case_id
     * @param user_id
     * @param user_type
     * @return
     */
    public boolean newDownvote(long case_id,long user_id,int user_type) {
        return executeSql("INSERT INTO case_vote (user_id," +
                "user_type," +
                "case_id," +
                "vote_type," +
                "create_time," +
                "last_modify_time," +
                "del) VALUES ("+
                user_id+","+
                user_type + "," + case_id + ",1,'" + Tool.getTime() + "','"+ Tool.getTime() + "',0)");
    }

    /**
     * 改变点赞类型
     * @param case_id 案例id
     * @param user_id 用户id
     * @param user_type 用户类型
     * @param vote_type 投票类型
     * @return
     */
    public boolean updateVoteType(long case_id,long user_id,int user_type,int vote_type) {
        return executeSql("UPDATE case_vote SET vote_type=" + vote_type + " ,del=0 " +
                "WHERE user_id=" + user_id + " AND user_type=" + user_type + " AND case_id=" + case_id);
    }

    /**
     * 删除（取消赞成或反对）
     * @param case_id
     * @param user_id
     * @param user_type
     * @return
     */
    public boolean delete(long case_id,long user_id,int user_type) {
        return executeSql("UPDATE case_vote SET del=1 " +
                "WHERE user_id=" + user_id + " AND case_id=" + case_id + " AND user_type=" + user_id + " AND user_type=" + user_type);
    }

    /**
     * 查询投票类型
     * @param case_id
     * @param user_id
     * @param user_type
     * @return 投票类型
     */
    public int queryVoteType(long case_id,long user_id,int user_type) {
        String sql = "SELECT vote_type FROM case_vote WHERE case_id=" + case_id + " AND user_id=" + user_id + " AND user_type=" + user_type;
        return Tool.getIntegerFromArrayList(helper.query(sql),"vote_type");
    }

    /**
     * 按创建者ID查询
     * @param user_id
     * @param user_type
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByCreator(long user_id,int user_type,int page_no,int length) {
        return complexQuery(QRY_BY_USER,page_no,length,NULL,user_id,user_type,-1);
    }

    /**
     * 按案例ID查询
     * @param case_id
     * @param vote_type
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByCase(long case_id,int vote_type,int page_no,int length) {
        return complexQuery(QRY_BY_CASE,page_no,length,case_id,NULL,USER_TYPE_MANAGER,vote_type);
    }

    /**
     * 复杂查询
     * @param query_type 查询类型 1按用户ID查询 2按案例ID查询
     * @param case_id
     * @param user_id
     * @param user_type 0管理员 1用户
     * @param vote_type 0赞同 1反对
     * @return
     */
    private ArrayList<HashMap<String,Object>> complexQuery(int query_type,int page_no,int length,long case_id,long user_id,int user_type,int vote_type) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");

        switch (query_type) {
            //按用户ID查询
            case QRY_BY_USER:
                builder.append("cv.case_id,")
                        .append("cv.vote_type,")
                        .append("c.creator,")
                        .append("creator_type,")
                        .append("trim(CASE c.creator_type WHEN 0 THEN au.nick_name ELSE u.nick_name) nick_name,")
                        .append("trim(CASE c.creator_type WHEN 0 THEN au.icon ELSE u.icon) icon")
                        .append("FROM case_vote cv ")
                        .append("LEFT JOIN cases c ON cv.case_id=c.id ")
                        .append("LEFT JOIN admin_user au ON c.creator=au.id ")
                        .append("LEFT JOIN user u ON c.creator=u.id ");
                builder.append("WHERE cv.user_id=").append(user_id).append(" AND cv.user_type=").append(user_type);
                break;
            //按案例ID查询
            case QRY_BY_CASE:
                builder.append("trim(CASE cv.user_type WHEN 0 THEN trim(au.nick_name) user_nickname ELSE trim(u.nick_name) nickname,")
                        .append("trim(CASE cv.user_type WHEN 0 THEN trim(au.icon) user_icon ELSE trim(u.icon) icon,")
                        .append("cv.user_id,")
                        .append("cv.user_type,")
                        .append("cv.case_id,")
                        .append("cv.vote_type,")
                        .append("FROM case_vote cv ")
                        .append("LEFT JOIN cases c ON cv.case_id=c.id ")
                        .append("LEFT JOIN user u ON cv.user_id=u.id ")
                        .append("LEFT JOIN admin_user au ON cv.user_id=au.id ");
                builder.append("WHERE cv.case_id=").append(case_id);
                break;
            default:break;
        }
        if (vote_type !=-1) {
            builder.append(" AND cv.vote_type=").append(vote_type);
        }
        builder.append(" ORDER BY cv.last_modify_time DESC ");
        builder.append(" LIMIT ").append((page_no-1)*length).append(",").append(length);
        return helper.query(builder.toString());
    }

    /**
     * 是否曾经赞成或反对
     * @param case_id 案例ID
     * @param user_id 用户ID
     * @param user_type 用户类型
     * @return
     */
    public boolean wasVoted(long case_id,long user_id,int user_type) {
        if (helper.query("SELECT * FROM case_vote " +
                "WHERE case_id=" + case_id + " AND user_id=" + user_id + " AND user_type=" + user_type).size() !=0) {
            return true;
        }
        return false;
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
