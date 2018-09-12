package com.alfred.wha.serv;

import com.alfred.wha.dao.CaseReadDAO;
import com.alfred.wha.util.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public class CaseReadService extends Service{

    private CaseReadDAO caseReadDAO;

    public CaseReadService() {
        caseReadDAO = new CaseReadDAO();
    }

    /**
     * 按案例ID查询
     * @param case_id
     * @return
     */
    public String queryByCase(long case_id) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseReadDAO.queryByCase(case_id);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 通过用户ID查询
     * @param user_id
     * @param user_type
     * @return
     */
    public String queryByUser(long user_id,int user_type) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseReadDAO.queryByUser(user_id,user_type);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

}
