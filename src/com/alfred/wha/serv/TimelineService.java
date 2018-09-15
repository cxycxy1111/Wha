package com.alfred.wha.serv;

import com.alfred.wha.dao.LogDao;
import com.alfred.wha.dao.TimelineDAO;
import com.alfred.wha.util.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public class TimelineService extends Service{

    private TimelineDAO timelineDAO;

    public TimelineService() {
        timelineDAO = new TimelineDAO();
    }

    /**
     * 新增
     * @param event_id
     * @param title
     * @param content
     * @param creator
     * @param creator_type
     * @return
     */
    public String add(boolean is_allow_duplicate,long event_id,String title,String content,long creator,int creator_type,String happen_time) {
        if (timelineDAO.isExist(title)) {
            if (is_allow_duplicate) {
                if (timelineDAO.add(event_id,title,content,creator,creator_type,happen_time)) {
                    return SUCCESS;
                }
                return FAIL;
            }
            return DUPLICATE;
        }else {
            if (timelineDAO.add(event_id,title,content,creator,creator_type,happen_time)) {
                return SUCCESS;
            }
            return FAIL;
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public String delete(long id,long operator,int operator_type) {
        if (!timelineDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (timelineDAO.delete(id)) {
            LogDao.recordTimelineLog(id,LOG_OPERATE_DELETE,operator,operator_type,"删除时间线");
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 更新时间线
     * @param id
     * @param title
     * @param content
     * @param happen_time
     * @return
     */
    public String update(boolean is_allow_duplicate,long id,long operator,int operator_type,String title,String content,String happen_time) {
        if (timelineDAO.isExist(id,title)) {
            if (is_allow_duplicate) {
                if (timelineDAO.update(id,title,content,happen_time)) {
                    LogDao.recordTimelineLog(id,LOG_OPERATE_EDIT,operator,operator_type,"标题:"+ title + ",发生时间为:"+happen_time + ",内容为:"+happen_time.substring(0,200));
                    return SUCCESS;
                }
                return FAIL;
            }
            return DUPLICATE;
        }
        if (timelineDAO.update(id,title,content,happen_time)) {
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
        if (!timelineDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (timelineDAO.pass(id)) {
            LogDao.recordTimelineLog(id,LOG_OPERATE_PASS,operator,operator_type,"审核通过时间线");
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 审核不通过
     * @param id
     * @return
     */
    public String reject(long id,long operator,int operator_type) {
        if (!timelineDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (timelineDAO.reject(id)) {
            LogDao.recordTimelineLog(id,LOG_OPERATE_REJECT,operator,operator_type,"审核驳回时间线");
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 批量审核通过
     * @param ids
     * @return
     */
    public String batchPass(String ids){
        if (timelineDAO.batchPass(ids)) {
            return SUCCESS;
        }
        return FAIL;

    }

    /**
     * 批量审核不通过
     * @param ids
     * @return
     */
    public String batchReject(String ids) {
        if (timelineDAO.batchReject(ids)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 通过事件查询时间线，按时间线发生事件倒序排列
     * @param event_id
     * @return
     */
    public String queryByEvent(long event_id) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = timelineDAO.queryByEvent(event_id);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 通过创建人查询时间线，按创建事件倒序排列
     * @param creator
     * @param creator_type
     * @return
     */
    public String queryByCreator(long creator,int creator_type) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = timelineDAO.queryByCreator(creator,creator_type);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 通过时间线ID查询具体情况
     * @param timeline
     * @return
     */
    public String queryByTimeline(long timeline) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = timelineDAO.queryByTimeline(timeline);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    public String queryNormal() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = timelineDAO.queryNormal();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    public String queryRejected() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = timelineDAO.queryRejected();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    public String queryUnchecked() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = timelineDAO.queryUnchecked();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }
    public String queryDeleted() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = timelineDAO.queryDeleted();
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

}
