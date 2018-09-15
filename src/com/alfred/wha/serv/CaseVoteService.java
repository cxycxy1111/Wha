package com.alfred.wha.serv;

import com.alfred.wha.dao.CaseVoteDAO;
import com.alfred.wha.util.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public class CaseVoteService extends Service{

    private CaseVoteDAO caseVoteDAO;

    public CaseVoteService() {
        caseVoteDAO = new CaseVoteDAO();
    }

    /**
     * 按创建者ID查询
     * @param user_id
     * @param user_type
     * @return
     */
    public String queryByCreator(long user_id, int user_type,int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseVoteDAO.queryByCreator(user_id,user_type,page_no,length);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 按案例ID查询
     * @param case_id
     * @param vote_type
     * @return
     */
    public String queryByCase(long case_id,int vote_type,int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseVoteDAO.queryByCase(case_id,vote_type,page_no,length);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

}
