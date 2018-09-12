package com.alfred.wha.serv;

import com.alfred.wha.dao.ProductDAO;
import com.alfred.wha.util.MethodTool;
import com.alfred.wha.util.SQLHelper;

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
    public String delete(long product_id) {
        if (productDAO.delete(product_id)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     *
     * @param status
     * @param del
     * @return
     */
    public String queryCompanyList(int status,boolean del) {
        return MethodTool.transformFromCollection(productDAO.simpleQuery(del,status));
    }

    /**
     * 更新产品
     * @param product_id
     * @param company_id
     * @param name
     * @return
     */
    public String update(long product_id,long company_id,String name) {
        if (productDAO.changeName(product_id,company_id,name)) {
            return SUCCESS;
        }
        return FAIL;
    }

}
