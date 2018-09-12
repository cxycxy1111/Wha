package com.alfred.wha.serv;

import com.alfred.wha.dao.CompanyDAO;
import com.alfred.wha.util.Tool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CompanyService extends Service{

    private CompanyDAO companyDAO;

    public CompanyService() {
        companyDAO = new CompanyDAO();
    }

    /**
     * 新增
     * @param name
     * @param creator
     * @param creator_type
     * @return
     */
    public String add(String name,long creator,int creator_type) {
        if (companyDAO.queryByName(name).size()!=0) {
            return DUPLICATE;
        }
        if (companyDAO.add(name,creator,creator_type)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 删除
     * @param company_id
     * @return
     */
    public String delete(long company_id) {
        if (companyDAO.delete(company_id)) {
            return SUCCESS;
        }
        return FAIL;
    }



    /**
     * 更改名称
     * @param company_id
     * @param name
     * @return
     */
    public String changeName(long company_id,boolean is_ignore_name_dupicate,String name) {
        if (companyDAO.isExist(name)) {
            if (is_ignore_name_dupicate) {
                companyDAO.changeName(company_id,name);
            }
            return DUPLICATE;
        }
        if (companyDAO.changeName(company_id,name)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 通过审核
     * @param id
     * @return
     */
    public String pass(long id) {
        if (companyDAO.pass(id)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 通过ID查询
     * @param id
     * @return
     */
    public String queryByCompany(long id) {
        if (!companyDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(companyDAO.queryByCompany(id));
    }


    /**
     * 通过名称查询
     * @param creator
     * @param creator_type
     * @return
     */
    public String queryByCreator(long creator,int creator_type) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = companyDAO.queryByCreator(creator,creator_type);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 待审核的公司列表
     * @return
     */
    public String queryUnchecked() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = companyDAO.queryUnchecked();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 已通过的公司列表
     * @return
     */
    public String queryPassed() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = companyDAO.queryPassed();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 未通过的公司列表
     * @return
     */
    public String queryRejected() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = companyDAO.queryRejected();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 已删除的公司列表
     * @return
     */
    public String queryDeleted() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = companyDAO.queryDeleted();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 未通过审核
     * @param id
     * @return
     */
    public String reject(long id) {
        if (companyDAO.reject(id)) {
            return SUCCESS;
        }
        return FAIL;

    }

}
