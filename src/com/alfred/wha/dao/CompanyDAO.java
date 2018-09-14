package com.alfred.wha.dao;

import com.alfred.wha.util.Tool;
import com.alfred.wha.util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CompanyDAO extends DAO{

    private static final int QRY_BY_STATUS = 1;
    private static final int QRY_BY_CREATOR = 2;
    private static final int QRY_BY_COMPANY = 3;
    private SQLHelper helper = new SQLHelper();

    public CompanyDAO() {

    }

    /**
     * 新增
     * @param name
     * @param creator
     * @param creator_type
     * @return
     */
    public boolean add(String name,long creator,int creator_type) {
        String sql = "INSERT INTO company (" +
                "name," +
                "del," +
                "status," +
                "create_time," +
                "creator," +
                "creator_type) VALUES ( '" +
                name + "'," +
                "0," +
                "1,'" +
                Tool.getTime() + "'," +
                creator + "," +
                creator_type + ")";
        return executeSql(sql);
    }

    /**
     * 删除
     * @param company_id
     * @return
     */
    public boolean delete(long company_id) {
        String sql = "UPDATE company SET del=1 WHERE id=" + company_id;
        return executeSql(sql);
    }

    /**
     * 恢复
     * @param company_id
     * @return
     */
    public boolean recover(long company_id) {
        String sql = "UPDATE company SET del=0 WHERE id=" + company_id;
        return executeSql(sql);
    }

    /**
     * 更改名称
     * @param company_id
     * @param name
     * @return
     */
    public boolean changeName(long company_id,String name) {
        String sql = "UPDATE company SET name='" + name + "' WHERE id=" + company_id;
        return executeSql(sql);
    }

    /**
     * 通过审核
     * @param id
     * @return
     */
    public boolean pass(long id) {
        return executeSql("UPDATE company SET status=0 WHERE id=" + id);
    }

    /**
     * 未通过审核
     * @param id
     * @return
     */
    public boolean reject(long id) {
        return executeSql("UPDATE company SET status=0 WHERE id=" + id);
    }

    /**
     * 通过公司名查询是否存在
     * @param name
     * @return
     */
    public boolean isExist(String name) {
        String sql = "SELECT * FROM company WHERE name = '" + name + "'";
        return helper.query(sql).size() != 0;
    }

    /**
     * 通过ID查询是否存在
     * @param id
     * @return
     */
    public boolean isExist(long id) {
        String sql = "SELECT * FROM company WHERE id = " + id;
        return helper.query(sql).size() != 0;
    }

    /**
     * 通过名称查询
     * @param id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByCompany(long id) {
        return complexQuery(QRY_BY_COMPANY,id,NULL,NULL,STATUS_PASSED,DEL_NO);
    }


    /**
     * 通过名称查询
     * @param creator
     * @param creator_type
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByCreator(long creator,int creator_type) {
        return complexQuery(QRY_BY_CREATOR,NULL,creator,creator_type,STATUS_PASSED,DEL_NO);
    }

    /**
     * 待审核的公司列表
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryUnchecked() {
        return complexQuery(QRY_BY_STATUS,NULL,NULL,NULL,STATUS_NEEDCHECK,DEL_NO);
    }

    /**
     * 已通过的公司列表
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryPassed() {
        return complexQuery(QRY_BY_STATUS,NULL,NULL,NULL,STATUS_PASSED,DEL_NO);
    }

    /**
     * 未通过的公司列表
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryRejected() {
        return complexQuery(QRY_BY_STATUS,NULL,NULL,NULL,STATUS_REJECTED,DEL_NO);
    }

    /**
     * 已删除的公司列表
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryDeleted() {
        return complexQuery(QRY_BY_STATUS,NULL,NULL,NULL,STATUS_PASSED,DEL_YES);
    }

    /**
     * 复杂查询
     * @param queryType 查询条件 1按del与status查询 2按指定ID查询 3按创建者id查询
     * @param status
     * @param del
     * @return
     */
    private ArrayList<HashMap<String,Object>> complexQuery(int queryType,long id,long creator,int creator_type,int status,int del) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");//公司ID
        switch (queryType) {
            //1按del与status查询
            case QRY_BY_STATUS:
                builder.append("c.id,")//公司ID
                        .append("c.name,")//公司名称
                        .append("c.del,")//是否已删除
                        .append("c.status,")//公司状态
                        .append("c.creator,")//创建人id
                        .append("c.creator_type,")//创建人类型
                        .append("CASE WHEN c.creator_type=0 THEN au.nick_name ELSE u.nick_name END,")//创建人昵称
                        .append("CASE WHEN c.creator_type=0 THEN au.icon ELSE u.icon END,")//创建人头像
                        .append("c.creator_type ")
                        .append("FROM company c ")
                        .append("LEFT JOIN user u ON c.creator=u.id ")
                        .append("LEFT JOIN admin_user au ON c.creator=au.id ");
                builder.append("WHERE ");
                if (del == 0) {
                    builder.append("c.status=").append(status).append(" AND ");
                }
                builder.append("c.del=").append(del);
                break;
            //1按指定ID查询
            case QRY_BY_COMPANY:
                builder.append("c.id,")//公司ID
                        .append("c.name,")//公司名称
                        .append("c.del,")//是否已删除
                        .append("c.status,")//公司状态
                        .append("c.creator,")//创建人id
                        .append("c.creator_type")//创建人类型
                        .append("CASE WHEN c.creator_type=0 THEN au.nick_name ELSE u.nick_name END,")//创建人昵称
                        .append("CASE WHEN c.creator_type=0 THEN au.icon ELSE u.icon END,")//创建人头像
                        .append("c.create_type ")
                        .append("FROM company c ")
                        .append("LEFT JOIN user u ON c.creator=u.id ")
                        .append("LEFT JOIN admin_user au ON c.creator=au.id ");
                builder.append("WHERE c.id=")
                        .append(id);
                break;
            //3按创建者id查询
            case QRY_BY_CREATOR:
                builder.append("c.id,")//公司ID
                        .append("c.name,")//公司名称
                        .append("c.del,")//是否已删除
                        .append("c.status,")//公司状态
                        .append("FROM company c ");
                builder.append("WHERE creator=")
                        .append(creator)
                        .append(" AND creator_type=")
                        .append(creator_type);
                break;
            default:break;
        }
        builder.append(" ORDER BY c.name");
        return helper.query(builder.toString());
    }

    /**
     * 通过名称查询
     * @param name
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByName(String name) {
        return helper.query("SELECT * FROM company WHERE name='" + name + "'");
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
