package com.alfred.wha.serv;

import com.alfred.wha.dao.EventDAO;
import com.alfred.wha.dao.EventSubscribeDAO;
import com.alfred.wha.dao.LogDao;
import com.alfred.wha.util.Tool;
import java.util.ArrayList;
import java.util.HashMap;

public class EventService extends Service{

    private EventDAO eventDAO;
    private EventSubscribeDAO eventSubcribeDAO;

    public EventService() {
        eventDAO = new EventDAO();
        eventSubcribeDAO = new EventSubscribeDAO();
    }

    /**
     * 新增
     * @param title
     * @param creator_id
     * @param creator_type
     * @param happen_time
     * @return
     */
    public String add(String title,boolean is_alow_duplicate,long creator_id,int creator_type,String happen_time) {
        if (eventDAO.isExist(title)) {
            if (is_alow_duplicate) {
                if (eventDAO.add(title,creator_id,creator_type,happen_time)) {
                    return SUCCESS;
                }
                return FAIL;
            }
            return DUPLICATE;
        }
        if (eventDAO.add(title,creator_id,creator_type,happen_time)) {
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
        if (!eventDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (eventDAO.delete(id)) {
            LogDao.recordEventLog(id,LOG_OPERATE_DELETE,operator,operator_type,"删除事件");
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
        if (!eventDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (eventDAO.recover(id)) {
            LogDao.recordEventLog(id,LOG_OPERATE_RECOVER,operator,operator_type,"恢复事件");
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 修改
     * @param id
     * @param name
     * @param happen_time
     * @return
     */
    public String change(long id,long operator,int operator_type,boolean is_alow_duplicate,String name,String happen_time) {
        if (eventDAO.isExist(id,name)) {
            if (is_alow_duplicate){
                if (eventDAO.change(id,name,happen_time)) {
                    LogDao.recordCompanyLog(id,LOG_OPERATE_EDIT,operator,operator_type,"新标题为:" + name + ",发生时间为:" + happen_time);
                    return SUCCESS;
                }
                return FAIL;
            }
            return DUPLICATE;
        }
        if (eventDAO.change(id,name,happen_time)) {
            LogDao.recordEventLog(id,LOG_OPERATE_EDIT,operator,operator_type,"新标题为:" + name + ",发生时间为:" + happen_time);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 通过审核
     * @param id
     * @return
     */
    public String pass(long id,long operator,int operator_type) {
        if (eventDAO.isExist(id)) {
            if (eventDAO.pass(id)) {
                LogDao.recordEventLog(id,LOG_OPERATE_PASS,operator,operator_type,"审核通过事件");
                return SUCCESS;
            }
            return FAIL;
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 未通过审核
     * @param id
     * @return
     */
    public String reject(long id,long operator,int operator_type) {
        if (eventDAO.isExist(id)) {
            if (eventDAO.reject(id)) {
                LogDao.recordEventLog(id,LOG_OPERATE_PASS,operator,operator_type,"审核驳回事件");
                return SUCCESS;
            }
            return FAIL;
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 关注
     * @param user_id
     * @param event_id
     * @return
     */
    public String subcribe(long user_id,int user_type,long event_id) {
        if (!eventSubcribeDAO.isSubcribe(user_id,user_type,event_id)) {
            if (eventSubcribeDAO.subcribe(user_id,user_type,event_id)) {
                return SUCCESS;
            }
            return FAIL;
        }
        return unsubcribe(user_id, user_type, event_id);
    }

    /**
     * 取消关注
     * @param user_id
     * @param event_id
     * @return
     */
    public String unsubcribe(long user_id,int user_type,long event_id) {
        if (eventSubcribeDAO.isSubcribe(user_id,user_type,event_id)) {
            if (eventSubcribeDAO.unsubcribe(user_id,user_type,event_id)) {
                return SUCCESS;
            }
            return FAIL;
        }
        return subcribe(user_id, user_type, event_id);
    }

    /**
     * 通过创建者查询
     * @param creator
     * @param creator_type
     * @return
     */
    public String queryByCreator(long creator, int creator_type,int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = eventDAO.queryByCreator(creator,creator_type,page_no,length);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 通过事件ID查询
     * @param event_id
     * @return
     */
    public String queryByEvent(long event_id) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = eventDAO.queryByEvent(event_id,1,1);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询未通过
     * @return
     */
    public String queryRejected(int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = eventDAO.queryRejected(page_no,length);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询已通过
     * @return
     */
    public String queryPassed(int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = eventDAO.queryPassed(page_no,length);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询未审核
     * @return
     */
    public String queryUncheck(int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = eventDAO.queryUncheck(page_no,length);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询已删除
     * @return
     */
    public String queryDeleted(int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = eventDAO.queryDeleted(page_no,length);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }


}
