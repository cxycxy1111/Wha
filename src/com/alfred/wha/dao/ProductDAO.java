package com.alfred.wha.dao;

import com.alfred.wha.util.Tool;
import com.alfred.wha.util.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductDAO extends DAO{

    private static final int QRY_BY_PRODUCT = 1;
    private static final int QRY_BY_COMPANY = 2;
    private static final int QRY_BY_CREATOR = 3;
    private static final int QRY_BY_STATUS = 4;
    private SQLHelper helper = new SQLHelper();

    public ProductDAO() {

    }

    /**
     * 新增
     * @param company_id
     * @param name
     * @param creator
     * @param creator_type
     * @return
     */
    public boolean add(long company_id,String name,long creator,int creator_type) {
        String sql = "INSERT INTO product (" +
                "company_id," +
                "name," +
                "del," +
                "status," +
                "create_time," +
                "creator," +
                "creator_type) VALUES ("
                +company_id +",'" +
                name + "'," +
                "0," +
                "1,'" +
                Tool.getTime() + "'," +
                creator + "," +
                creator_type + ")";
        return Tool.executeSql(sql);
    }

    /**
     * 删除
     * @param product_id
     * @return
     */
    public boolean delete(long product_id) {
        return executeSql("UPDATE product SET del=1 WHERE id=" + product_id);
    }

    /**
     * 删除
     * @param product_id
     * @return
     */
    public boolean recover(long product_id) {
        return executeSql("UPDATE product SET del=0 WHERE id=" + product_id);
    }

    /**
     * 产品通过审核
     * @param product_id
     * @return
     */
    public boolean pass(long product_id) {
        return executeSql("UPDATE product SET status=0 WHERE id=" + product_id);
    }

    /**
     * 产品未通过审核
     * @param product_id
     * @return
     */
    public boolean reject(long product_id) {
        return executeSql("UPDATE product SET status=2 WHERE id=" + product_id);
    }

    /**
     * 更改产品信息
     * @param product_id
     * @param company_id
     * @param name
     * @return
     */
    public boolean changeName(long product_id,long company_id,String name) {
        return executeSql("UPDATE product SET name='" + name + "',company_id= " + company_id + " WHERE id=" + product_id);
    }

    /**
     * 通过用户名查询是否存在
     * @param name
     * @return
     */
    public boolean isExist(String name) {
        String sql = "SELECT * FROM product WHERE name = '" + name + "'";
        return helper.query(sql).size() != 0;
    }

    /**
     * 通过用户名查询是否存在
     * @param id
     * @return
     */
    public boolean isExist(long id) {
        String sql = "SELECT * FROM product WHERE id = " + id;
        return helper.query(sql).size() != 0;
    }

    /**
     * 通过名称查询产品
     * @param name
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByName(String name) {
        return helper.query("SELECT * FROM product WHERE name='" + name + "'");
    }

    /**
     * 查询所有产品
     * @param del
     * @param status
     * @return
     */
    public ArrayList<HashMap<String,Object>> simpleQuery(boolean del,int status) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT id,title,del,status FROM product WHERE del=");
        if (del) {
            builder.append("1");
        }else {
            builder.append("0");
        }
        builder.append(" AND status=").append(status);
        return helper.query(builder.toString());
    }

    /**
     * 查询产品具体情况
     * @param product_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByProduct(long product_id,int page_no,int length) {
        return complexQuery(QRY_BY_PRODUCT,page_no,length,NULL,product_id,NULL,NULL,DEL_NO,STATUS_PASSED);
    }

    /**
     * 通过公司查询产品列表
     * @param company_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByCompany(long company_id,int page_no,int length) {
        return complexQuery(QRY_BY_COMPANY,page_no,length,company_id,NULL,NULL,NULL,DEL_NO,STATUS_PASSED);
    }

    /**
     * 通过创建者查询产品列表
     * @param creator
     * @param creator_type
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryByCreator(long creator,int creator_type,int page_no,int length) {
        return complexQuery(QRY_BY_PRODUCT,page_no,length,NULL,NULL,creator,creator_type,DEL_NO,STATUS_PASSED);
    }

    /**
     * 查询已通过的产品
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryPassed(int page_no,int length) {
        return complexQuery(QRY_BY_STATUS,page_no,length,NULL,NULL,NULL,NULL,DEL_NO,STATUS_PASSED);
    }

    /**
     * 查询未审核的产品
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryUncheck(int page_no,int length) {
        return complexQuery(QRY_BY_STATUS,page_no,length,NULL,NULL,NULL,NULL,DEL_NO,STATUS_NEEDCHECK);
    }

    /**
     * 查询未通过的产品
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryRejected(int page_no,int length) {
        return complexQuery(QRY_BY_STATUS,page_no,length,NULL,NULL,NULL,NULL,DEL_NO,STATUS_REJECTED);
    }

    /**
     * 查询已删除的产品
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryDeleted(int page_no,int length) {
        return complexQuery(QRY_BY_STATUS,page_no,length,NULL,NULL,NULL,NULL,DEL_YES,STATUS_NORMAL);
    }

    /**
     *
     * @param queryType 查询类型 1根据产品ID精确查询 2根据公司ID查询 3根据创建者ID查询
     * @param company_id
     * @param product_id
     * @param creator
     * @param creator_type
     * @param del
     * @param status
     * @return
     */
    private ArrayList<HashMap<String,Object>> complexQuery(int queryType,int page_no,int length,long company_id,long product_id,long creator,int creator_type,int del,int status) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        switch (queryType) {
            case QRY_BY_PRODUCT://根据产品ID精确查询
                builder.append("p.id,")
                        .append("p.name,")
                        .append("truncate(c.id,0) company_id,")
                        .append("trim(c.name) company_name,")
                        .append("p.status,")
                        .append("p.del,")
                        .append("CASE WHEN p.creator_type=0 THEN au.nick_name ELSE u.nick_name END,")
                        .append("CASE WHEN p.creator_type=0 THEN au.icon ELSE u.icon END,")
                        .append("p.creator,")
                        .append("p.creator_type,")
                        .append("p.create_time ")
                        .append(" FROM product p ")
                        .append("LEFT JOIN company c ON p.company_id=c.id ")
                        .append("LEFT JOIN user u ON p.creator=u.id ")
                        .append("LEFT JOIN admin_user au ON p.creator=au.id ");
                builder.append("WHERE p.id=").append(product_id);
                break;
            case QRY_BY_COMPANY://根据公司ID查询
                builder.append("p.id,")
                        .append("p.name,")
                        .append("p.status,")
                        .append("p.del,")
                        .append("CASE WHEN p.creator_type=0 THEN au.nick_name ELSE u.nick_name END,")
                        .append("CASE WHEN p.creator_type=0 THEN au.icon ELSE u.icon END,")
                        .append("p.creator,")
                        .append("p.creator_type,")
                        .append("p.create_time,")
                        .append(" FROM product p ")
                        .append("LEFT JOIN user u ON p.creator=u.id ")
                        .append("LEFT JOIN admin_user au ON p.creator=au.id ");
                builder.append("WHERE p.company_id=").append(company_id)
                        .append(" AND p.del=").append(del)
                        .append(" AND p.status= ").append(status);
                break;
            case QRY_BY_CREATOR://根据创建者ID查询
                builder.append("p.id,")
                        .append("p.name,")
                        .append("truncate(c.id,0) company_id,")
                        .append("trim(c.name) company_name,")
                        .append("p.status,")
                        .append("p.del,")
                        .append("p.create_time,")
                        .append(" FROM product p ")
                        .append("LEFT JOIN company c ON p.company_id=c.id ");
                builder.append("WHERE p.creator=").append(creator)
                        .append(" AND p.creator_type=").append(creator_type)
                        .append(" AND p.del=").append(del).append(" AND p.status=").append(status);
                break;
            case QRY_BY_STATUS:
                builder.append("p.id,")
                        .append("p.name,")
                        .append("p.status,")
                        .append("p.del,")
                        .append("CASE WHEN p.creator_type=0 THEN au.nick_name ELSE u.nick_name END,")
                        .append("CASE WHEN p.creator_type=0 THEN au.icon ELSE u.icon END,")
                        .append("p.creator,")
                        .append("p.creator_type,")
                        .append("p.create_time ")
                        .append(" FROM product p ")
                        .append("LEFT JOIN user u ON p.creator=u.id ")
                        .append("LEFT JOIN admin_user au ON p.creator=au.id ");
                builder.append("WHERE ");
                if (del==0) {
                    builder.append("p.status=").append(status).append(" AND ");
                }
                builder.append("p.del=").append(del);
                break;
            default:break;
        }
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
