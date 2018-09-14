package com.alfred.wha.serv;

import com.alfred.wha.dao.LogDao;
import com.alfred.wha.dao.ProductDAO;
import com.alfred.wha.util.Tool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductService extends Service{

    private ProductDAO productDAO;

    public ProductService() {
        productDAO = new ProductDAO();
    }

    /**
     * 新增产品
     * @param company_id
     * @param name
     * @param creator
     * @param creator_type
     * @return
     */
    public String add(long company_id,String name,long creator,int creator_type) {
        if (productDAO.queryByName(name).size()==0) {
            return DUPLICATE;
        }
        if (productDAO.add(company_id,name,creator,creator_type)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 删除产品
     * @param product_id
     * @return
     */
    public String delete(long product_id,long operator,int operator_type) {
        if (productDAO.delete(product_id)) {
            LogDao.recordProductLog(product_id,LOG_OPERATE_DELETE,operator,operator_type,"");
            return SUCCESS;
        }
        return FAIL;
    }



    /**
     * 产品通过审核
     * @param product_id
     * @return
     */
    public String pass(long product_id,long operator,int operator_type) {
        if (!productDAO.isExist(product_id)) {
            return QRY_RESULT_EMPTY;
        }
        if (productDAO.pass(product_id)) {
            LogDao.recordProductLog(product_id,LOG_OPERATE_PASS,operator,operator_type,"");
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 产品未通过审核
     * @param product_id
     * @return
     */
    public String reject(long product_id,long operator,int operator_type) {
        if (!productDAO.isExist(product_id)) {
            return QRY_RESULT_EMPTY;
        }
        if (productDAO.reject(product_id)) {
            LogDao.recordProductLog(product_id,LOG_OPERATE_REJECT,operator,operator_type,"");
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 更改产品信息
     * @param product_id
     * @param company_id
     * @param name
     * @return
     */
    public String changeName(long product_id,long operator,int operator_type,long company_id,String name) {
        if (!productDAO.isExist(product_id)) {
            return QRY_RESULT_EMPTY;
        }
        if (productDAO.changeName(product_id,company_id,name)) {
            LogDao.recordProductLog(product_id,LOG_OPERATE_EDIT,operator,operator_type,"新名称为:" + name);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 查询产品具体情况
     * @param product_id
     * @return
     */
    public String queryByProduct(long product_id) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = productDAO.queryByProduct(product_id);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 通过公司查询产品列表
     * @param company_id
     * @return
     */
    public String queryByCompany(long company_id) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = productDAO.queryByCompany(company_id);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 通过创建者查询产品列表
     * @param creator
     * @param creator_type
     * @return
     */
    public String queryByCreator(long creator,int creator_type) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = productDAO.queryByCreator(creator,creator_type);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询已通过的产品
     * @return
     */
    public String queryPassed() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = productDAO.queryPassed();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询未审核的产品
     * @return
     */
    public String queryUncheck() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = productDAO.queryPassed();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询未通过的产品
     * @return
     */
    public String queryRejected() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = productDAO.queryRejected();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询已删除的产品
     * @return
     */
    public String queryDeleted() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = productDAO.queryDeleted();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

}
