package com.alfred.wha.serv;

import com.alfred.wha.dao.EventDAO;
import com.alfred.wha.util.Tool;
import java.util.ArrayList;
import java.util.HashMap;

public class EventService extends Service{

    private EventDAO eventDAO;

    public EventService() {
        eventDAO = new EventDAO();
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
    public String delete(long id) {
        if (!eventDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (eventDAO.delete(id)) {
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
    public String change(long id,boolean is_alow_duplicate,String name,String happen_time) {
        if (eventDAO.isExist(id,name)) {
            if (is_alow_duplicate){
                if (eventDAO.change(id,name,happen_time)) {
                    return SUCCESS;
                }
                return FAIL;
            }
            return DUPLICATE;
        }
        if (eventDAO.change(id,name,happen_time)) {
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
        if (eventDAO.isExist(id)) {
            if (eventDAO.pass(id)) {
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
    public String reject(long id) {
        if (eventDAO.isExist(id)) {
            if (eventDAO.reject(id)) {
                return SUCCESS;
            }
            return FAIL;
        }
        return QRY_RESULT_EMPTY;
    }

    /**
     * 通过创建者查询
     * @param creator
     * @param creator_type
     * @return
     */
    public String queryByCreator(long creator, int creator_type) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = eventDAO.queryByCreator(creator,creator_type);
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
        arrayList = eventDAO.queryByEvent(event_id);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询未通过
     * @return
     */
    public String queryRejected() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = eventDAO.queryRejected();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询已通过
     * @return
     */
    public String queryPassed() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = eventDAO.queryPassed();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询未审核
     * @return
     */
    public String queryUncheck() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = eventDAO.queryUncheck();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 查询已删除
     * @return
     */
    public String queryDeleted() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = eventDAO.queryDeleted();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }


}
