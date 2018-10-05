package com.alfred.wha.serv;

import com.alfred.wha.dao.CaseDAO;
import com.alfred.wha.dao.CaseReadDAO;
import com.alfred.wha.dao.CaseVoteDAO;
import com.alfred.wha.dao.LogDao;
import com.alfred.wha.util.Tool;
import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;
import java.util.HashMap;

public class CaseService extends Service{

    private CaseDAO caseDAO;
    private CaseVoteDAO caseVoteDAO;
    private CaseReadDAO caseReadDAO;

    public CaseService() {
        caseDAO = new CaseDAO();
        caseVoteDAO = new CaseVoteDAO();
        caseReadDAO = new CaseReadDAO();
    }

    /**
     * 新增
     * @param is_allow_duplicate
     * @param event_id
     * @param title
     * @param content
     * @param creator
     * @param creator_type 0管理员 1用户
     * @return
     */
    public String add(boolean is_allow_duplicate,long event_id,String title,String content,long creator,int creator_type) {
        if (caseDAO.isExist(title)) {
            if (is_allow_duplicate) {
                if (caseDAO.add(event_id,title,content,creator,creator_type)){
                    return SUCCESS;
                }
                return FAIL;
            }
            return DUPLICATE;
        }
        if (caseDAO.add(event_id,title,content,creator,creator_type)){
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public String delete(long id,long operator,int operator_type) {
        if (!caseDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (caseDAO.delete(id)) {
            LogDao.recordCaseLog(id,LOG_OPERATE_DELETE,operator,operator_type,"删除案例");
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public String recover(long id,long operator,int operator_type) {
        if (!caseDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (caseDAO.recover(id)) {
            LogDao.recordCaseLog(id,LOG_OPERATE_RECOVER,operator,operator_type,"恢复案例");
            return SUCCESS;
        }
        return FAIL;
    }
    /**
     * 审核通过
     * @param id
     * @return
     */
    public String pass(long id,long operator,int operator_type) {
        if (!caseDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (caseDAO.pass(id)) {
            LogDao.recordCaseLog(id,LOG_OPERATE_PASS,operator,operator_type,"审核通过案例");
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 审核通过
     * @param id
     * @return
     */
    public String reject(long id,long operator,int operator_type) {
        if (!caseDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (caseDAO.reject(id)) {
            LogDao.recordCaseLog(id,LOG_OPERATE_REJECT,operator,operator_type,"审核驳回案例");
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
    public String update(boolean is_allow_duplicate,long id,long event_id,long operator,int operator_type,String title,String content) {
        if (caseDAO.isExist(id,title)) {
            if (is_allow_duplicate) {
                if (caseDAO.update(id,event_id,title,content)) {
                    LogDao.recordCaseLog(id,LOG_OPERATE_EDIT,operator,operator_type,"新内容为:" + content.substring(0,180));
                    return SUCCESS;
                }
                return FAIL;
            }
            return DUPLICATE;
        }
        if (caseDAO.update(id,event_id, title, content)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 更新赞同数
     * @param case_id
     * @param user_id
     * @param user_type
     * @return
     */
    public String upvote(long case_id,long user_id,int user_type) {
        if (caseDAO.isExist(case_id)) {
            if (caseVoteDAO.wasVoted(case_id,user_id,user_type)) {//如果已经投票
                if (caseVoteDAO.queryVoteType(case_id,user_id,user_type) != 0) {//如果原来是反对
                    caseVoteDAO.updateVoteType(case_id,user_id,user_type,0);//案例投票中间表改为赞成
                    caseDAO.updateUpvoteCount(case_id,true);//案例表的赞成数加一
                    caseDAO.updateDownvoteCount(case_id,false);//案例表的反对数减一
                    LogDao.recordCaseLog(case_id,LOG_OPERATE_EDIT,user_id,user_type,"upvote_count+1");
                    LogDao.recordCaseLog(case_id,LOG_OPERATE_EDIT,user_id,user_type,"downvote_count-1");
                    return SUCCESS;
                }else {//原来已是赞成
                    caseVoteDAO.delete(case_id,user_id,user_type);//删除案例投票中间表相应数据
                    caseDAO.updateUpvoteCount(case_id,false);//案例表的赞成数减一
                    LogDao.recordCaseLog(case_id,LOG_OPERATE_EDIT,user_id,user_type,"upvote_count-1");
                    return SUCCESS;
                }
            }else {
                caseVoteDAO.newUpvote(case_id,user_type,user_type);
                caseDAO.updateUpvoteCount(case_id,true);
                LogDao.recordCaseLog(case_id,LOG_OPERATE_EDIT,user_id,user_type,"upvote_count+1");
                return SUCCESS;
            }
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 更新赞同数
     * @param case_id
     * @param user_id
     * @param user_type
     * @return
     */
    public String downvote(long case_id,long user_id,int user_type) {
        if (caseDAO.isExist(case_id)) {
            if (caseVoteDAO.wasVoted(case_id,user_id,user_type)) {//如果已经投票
                if (caseVoteDAO.queryVoteType(case_id,user_id,user_type) != 1) {//如果原来是赞成
                    caseVoteDAO.updateVoteType(case_id,user_id,user_type,1);//案例投票中间表改为反对
                    caseDAO.updateDownvoteCount(case_id,true);//案例表的反对数加一
                    caseDAO.updateUpvoteCount(case_id,false);//案例表的赞同数减一
                    LogDao.recordCaseLog(case_id,LOG_OPERATE_EDIT,user_id,user_type,"downvote_count+1");
                    LogDao.recordCaseLog(case_id,LOG_OPERATE_EDIT,user_id,user_type,"upvote_count-1");
                    return SUCCESS;
                }else {//原来已是反对
                    caseVoteDAO.delete(case_id,user_id,user_type);//删除案例投票中间表相应数据
                    caseDAO.updateDownvoteCount(case_id,false);//案例表的赞成数减一
                    LogDao.recordCaseLog(case_id,LOG_OPERATE_EDIT,user_id,user_type,"downvote_count-1");
                    return SUCCESS;
                }
            }else {
                caseVoteDAO.newUpvote(case_id,user_type,user_type);
                caseDAO.updateUpvoteCount(case_id,true);
                LogDao.recordCaseLog(case_id,LOG_OPERATE_EDIT,user_id,user_type,"upvote_count+1");
                return SUCCESS;
            }
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 查询自己订阅的事件的新案例
     * @param user_id
     * @param user_type
     * @return
     */
    public String querySelfSubcribe(long user_id, int user_type,int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseDAO.querySelfSubcribe(user_id,user_type,page_no,length);
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
    public String querySelfCreate(long user_id,int user_type,int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseDAO.querySelfCreate(user_id,user_type,page_no,length);
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
    public String queryDetail(long case_id,long user_id,int user_type,int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseDAO.queryDetail(case_id,page_no,length);
        if (arrayList.size() != 0) {
            caseReadDAO.add(case_id,user_id,user_type);
            caseDAO.updateViewCount(case_id);
            return Tool.transformFromCollection(arrayList);
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 查询未审核的案例列表
     * @return
     */
    public String queryUnchecked(int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseDAO.queryUnchecked(page_no,length);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询已删除的案例列表
     * @return
     */
    public String queryDeleted(int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseDAO.queryDeleted(page_no,length);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询已通过的案例列表
     * @return
     */
    public String queryPassed(int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseDAO.queryPassed(page_no,length);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询未通过的案例列表
     * @return
     */
    public String queryRejected(int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = caseDAO.queryRejected(page_no,length);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

}
