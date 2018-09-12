package com.alfred.wha.serv;

import com.alfred.wha.dao.EventSubcribeDAO;
import com.alfred.wha.util.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public class EventSubcribeService extends Service{
    
    private EventSubcribeDAO eventSubcribeDAO;
    
    public EventSubcribeService() {
        eventSubcribeDAO = new EventSubcribeDAO();
    }

    /**
     * 通过用户查询所关注的事件列表
     * @param user_id
     * @return
     */
    public String queryByUser(long user_id, int user_type) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = eventSubcribeDAO.queryByUser(user_id,user_type);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 根据事件查询事件列表
     * @param event_id
     * @return
     */
    public String queryByEvent(long event_id) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = eventSubcribeDAO.queryByEvent(event_id);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }
}
