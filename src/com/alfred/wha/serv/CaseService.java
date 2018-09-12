package com.alfred.wha.serv;

import com.alfred.wha.dao.CaseDAO;
import com.alfred.wha.util.Tool;
import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;
import java.util.HashMap;

public class CaseService extends Service{

    private CaseDAO caseDAO;

    public CaseService() {
        caseDAO = new CaseDAO();
    }

    /**
     * 新增
     * @param is_allow_duplicate
     * @param event_id
     * @param title
     * @param content
     * @param creator_id
     * @param creator_type 0管理员 1用户
     * @return
     */
    public String add(boolean is_allow_duplicate,long event_id,String title,String content,long creator_id,int creator_type) {
        if (caseDAO.isExist(title)) {
            if (is_allow_duplicate) {
                if (caseDAO.add(event_id,title,content,creator_id,creator_type)){
                    return SUCCESS;
                }
                return FAIL;
            }
            return DUPLICATE;
        }
        if (caseDAO.add(event_id,title,content,creator_id,creator_type)){
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public String delete(long id) {
        if (!caseDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (caseDAO.delete(id)) {
            return SUCCESS;
        }
        return FAIL;
    }
    /**
     * 审核通过
     * @param id
     * @return
     */
    public String pass(long id) {
        if (!caseDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (caseDAO.pass(id)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 审核通过
     * @param id
     * @return
     */
    public String reject(long id) {
        if (!caseDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (caseDAO.reject(id)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 更新
     * @param is_allow_duplicate
     * @param id
     * @param title
     * @param content
     * @return
     */
    public String update(boolean is_allow_duplicate,long id,String title,String content) {
        if (caseDAO.isExist(id,title)) {
            if (is_allow_duplicate) {
                if (caseDAO.update(id,title,content)) {
                    return SUCCESS;
                }
                return FAIL;
            }
            return DUPLICATE;
        }
        if (caseDAO.update(id, title, content)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 更新浏览数
     * @param id
     * @return
     */
    public String updateViewCount(long id) {
        if (caseDAO.isExist(id)) {
            if (caseDAO.updateViewCount(id)) {
                return SUCCESS;
            }
            return FAIL;
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 更新赞同数
     * @param id
     * @return
     */
    public String updateUpvoteCount(long id) {
        if (caseDAO.isExist(id)) {
            if (caseDAO.updateUpvoteCount(id)) {
                return SUCCESS;
            }
            return FAIL;
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 更新反对数
     * @param id
     * @return
     */
    public String updateDownvoteCount(long id) {
        if (caseDAO.isExist(id)) {
            if (caseDAO.updateDownvoteCount(id)) {
                return SUCCESS;
            }
            return FAIL;
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 查询自己订阅的事件的新案例
     * @param user_id
     * @return
     */
    public String querySelfSubcribe(long user_id, int user_type) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseDAO.querySelfSubcribe(user_id,user_type);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询自己创建的案例
     * @param user_id
     * @return
     */
    public String querySelfCreate(long user_id,int user_type) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseDAO.querySelfCreate(user_id,user_type);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询案例详情
     * @param case_id
     * @return
     */
    public String queryDetail(long case_id) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseDAO.queryDetail(case_id);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询未审核的案例列表
     * @return
     */
    public String queryUnchecked() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseDAO.queryUnchecked();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询已删除的案例列表
     * @return
     */
    public String queryDeleted() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseDAO.queryDeleted();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询已通过的案例列表
     * @return
     */
    public String queryPassed() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseDAO.queryPassed();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询未通过的案例列表
     * @return
     */
    public String queryRejected() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseDAO.queryRejected();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

}
