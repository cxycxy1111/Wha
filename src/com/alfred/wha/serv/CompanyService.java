package com.alfred.wha.serv;

import com.alfred.wha.dao.CompanyDAO;
import com.alfred.wha.util.MethodTool;

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
     * 查询公司列表
     * @param status
     * @param del
     * @return
     */
    public String queryCompanyList(int status,boolean del) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = companyDAO.queryAll(status,del);
        if (arrayList.size() == 0) {
            return EMPTY;
        }
        return MethodTool.transformFromCollection(arrayList);
    }

    /**
     * 查询公司信息
     * @param company_id
     * @return
     */
    public String queryCompanyInfo(long company_id) {
        return MethodTool.transformFromCollection(companyDAO.queryById(company_id));
    }

    /**
     * 编辑公司
     * @param company_id
     * @param name
     * @return
     */
    public String edit(long company_id,String name) {
        if (companyDAO.changeName(company_id,name)) {
            return SUCCESS;
        }
        return FAIL;
    }

}
