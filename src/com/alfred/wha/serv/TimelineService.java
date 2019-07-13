package com.alfred.wha.serv;

import com.alfred.wha.dao.AdDAO;
import com.alfred.wha.dao.LogDao;
import com.alfred.wha.dao.TimelineDAO;
import com.alfred.wha.dao.TimelineUserFavouriteDAO;
import com.alfred.wha.util.Tool;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TimelineService extends Service{

    private TimelineDAO timelineDAO;
    private TimelineUserFavouriteDAO timelineUserFavouriteDAO;
    private AdDAO adDAO;

    public TimelineService() {
        timelineDAO = new TimelineDAO();
        timelineUserFavouriteDAO = new TimelineUserFavouriteDAO();
        adDAO = new AdDAO();
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
    public String add(boolean is_allow_duplicate,long event_id,String title,String summary,String content,long creator,int creator_type,String happen_time) {
        if (timelineDAO.isExist(title)) {
            if (is_allow_duplicate) {
                if (timelineDAO.add(event_id,title,summary,creator,creator_type,happen_time)) {
                    int id = timelineDAO.queryMaxId();
                    Tool.writeFiles(id,path_timeline_content,content);
                    return SUCCESS;
                }
                return FAIL;
            }
            return DUPLICATE;
        }else {
            if (timelineDAO.add(event_id,title,summary,creator,creator_type,happen_time)) {
                int id = timelineDAO.queryMaxId();
                Tool.writeFiles(id,path_timeline_content,content);
                return SUCCESS;
            }
            return FAIL;
        }
    }


    public String addReport(long timeline_id,int content_type,int type) throws SQLException {
        if (timelineDAO.addReport(timeline_id,content_type,type)) {
            return SUCCESS;
        }else  {
            return FAIL;
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public String delete(long id,long operator,int operator_type) {
        if (timelineDAO.delete(id)) {
            LogDao.recordTimelineLog(id,LOG_OPERATE_DELETE,operator,operator_type,"删除时间线");
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
        if (!timelineDAO.isExist(id)) {
            return QRY_RESULT_EMPTY;
        }
        if (timelineDAO.recover(id)) {
            LogDao.recordTimelineLog(id,LOG_OPERATE_RECOVER,operator,operator_type,"恢复时间线");
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
    public String update(boolean is_allow_duplicate,long id,long operator,int operator_type,String title,String summary,String content,String happen_time) {
        if (timelineDAO.isExist(id,title)) {
            if (is_allow_duplicate) {
                if (timelineDAO.update(id,title,summary,happen_time)) {
                    LogDao.recordTimelineLog(id,LOG_OPERATE_EDIT,operator,operator_type,"标题:"+ title + ",发生时间为:"+happen_time + ",内容为:"+content.substring(0,200));
                    return SUCCESS;
                }
                return FAIL;
            }
            return DUPLICATE;
        }
        if (timelineDAO.update(id,title,summary,happen_time)) {
            Tool.writeFiles(id,path_timeline_content,content);
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

    public String queryFull(int page_no) {
        ArrayList<HashMap<String,Object>> list_ad = new ArrayList<>();
        ArrayList<HashMap<String,Object>> result_list_ad = new ArrayList<>();
        Collections.shuffle(list_ad);
        list_ad = adDAO.query();
        for (int i = 0;i<list_ad.size();i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap = list_ad.get(i);
            hashMap.put("isAd", "yes");
            result_list_ad.add(hashMap);
            hashMap.put("content", String.valueOf(Tool.getImage(String.valueOf(hashMap.get("id")), "/usr/local/Whale/Ad/")));
        }

        ArrayList<HashMap<String,Object>> list = new ArrayList<>();
        list = timelineDAO.queryFull(page_no);
        ArrayList<HashMap<String,Object>> result_list = new ArrayList<>();
        for (int i = 0;i < list.size();i++) {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap = list.get(i);
            hashMap.put("isAd","no");
            result_list.add(hashMap);
        }
        String ad_property = Tool.readUtilFiles("/usr/local/Whale/Util/ad_property");
        int i = Integer.parseInt(ad_property);
        for(int j = 0 ;j<i;j++) {
            result_list.add(result_list_ad.get(j));
        }
        Collections.shuffle(result_list);
        return Tool.transformFromCollection(result_list);
    }


    public String queryByType(String type,int page_no) {
        ArrayList<HashMap<String,Object>> list_ad = new ArrayList<>();
        ArrayList<HashMap<String,Object>> result_list_ad = new ArrayList<>();
        Collections.shuffle(list_ad);
        list_ad = adDAO.query();
        for (int i = 0;i<list_ad.size();i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap = list_ad.get(i);
            hashMap.put("isAd", "yes");
            result_list_ad.add(hashMap);
            hashMap.put("content", String.valueOf(Tool.getImage(String.valueOf(hashMap.get("id")), "/usr/local/Whale/Ad/")));
        }

        ArrayList<HashMap<String,Object>> list = new ArrayList<>();
        list = timelineDAO.queryByType(type,page_no);
        ArrayList<HashMap<String,Object>> result_list = new ArrayList<>();
        for (int i = 0;i < list.size();i++) {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap = list.get(i);
            hashMap.put("isAd","no");
            result_list.add(hashMap);
        }
        String ad_property = Tool.readUtilFiles("/usr/local/Whale/Util/ad_property");
        int i = Integer.parseInt(ad_property);
        for(int j = 0 ;j<i;j++) {
            result_list.add(result_list_ad.get(j));
        }
        Collections.shuffle(result_list);
        return Tool.transformFromCollection(result_list);
    }

    public String queryByImportant(int page_no) {

        ArrayList<HashMap<String,Object>> list_ad = new ArrayList<>();
        ArrayList<HashMap<String,Object>> result_list_ad = new ArrayList<>();
        Collections.shuffle(list_ad);
        list_ad = adDAO.query();
        for (int i = 0;i<list_ad.size();i++) {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap = list_ad.get(i);
            hashMap.put("isAd","yes");
            result_list_ad.add(hashMap);
            hashMap.put("content",String.valueOf(Tool.getImage(String.valueOf(hashMap.get("id")),"/usr/local/Whale/Ad/")));
        }

        ArrayList<HashMap<String,Object>> list = new ArrayList<>();
        list = timelineDAO.queryByImportant(page_no);
        ArrayList<HashMap<String,Object>> result_list = new ArrayList<>();
        for (int i = 0;i < list.size();i++) {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap = list.get(i);
            hashMap.put("isAd","no");
            result_list.add(hashMap);
        }
        String ad_property = Tool.readUtilFiles("/usr/local/Whale/Util/ad_property");
        int i = Integer.parseInt(ad_property);
        for(int j = 0 ;j<i;j++) {
            result_list.add(result_list_ad.get(j));
        }
        Collections.shuffle(result_list);
        return Tool.transformFromCollection(result_list);
    }

    /**
     * 通过事件查询时间线，按时间线发生事件倒序排列
     * @param event_id
     * @return
     */
    public String queryByEvent(long event_id,int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = timelineDAO.queryByEvent(event_id,page_no,length);
        if (arrayList.size() == 0) {
            //return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    public void initTimelineContent() {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = timelineDAO.queryFullWithoutDelete();
        for (int i = 0;i<arrayList.size();i++) {
            long id = Integer.parseInt(String.valueOf(arrayList.get(i).get("id")));
            String content =String.valueOf(arrayList.get(i).get("content"));
            Tool.writeFiles(id,path_timeline_content,content);
        }
    }

    /**
     * 通过创建人查询时间线，按创建事件倒序排列
     * @param creator
     * @param creator_type
     * @return
     */
    public String queryByCreator(long creator,int creator_type,int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = timelineDAO.queryByCreator(creator,creator_type,page_no,length);
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
    public String queryByTimeline(long timeline,long user_id,boolean loginStatus) throws IOException {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        HashMap<String,Object> map = new HashMap<>();
        arrayList = timelineDAO.queryByTimeline(timeline,1,1);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }else {
            map = arrayList.get(0);
            String content = Tool.readFiles(timeline,path_timeline_content);
            System.out.println(content);
            if (loginStatus) {
                if (timelineUserFavouriteDAO.isExist(timeline,user_id) && !timelineUserFavouriteDAO.isDeleted(timeline,user_id)) {
                    map.put("isFavourite","yes");
                }else {
                    map.put("isFavourite","no");
                }
            }else {
                map.put("isFavourite","no");
            }
            map.put("content",content);
            ArrayList<HashMap<String,Object>> list = new ArrayList<>();
            list.add(map);
            return Tool.transformFromCollection(list);
        }
    }

    public String publicSharePage(long timeline) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = timelineDAO.queryByTimeline(timeline,1,1);
        String title = String.valueOf(arrayList.get(0).get("title"));
        String summary = String.valueOf(arrayList.get(0).get("summary"));
        String content = Tool.publicShareReadFiles(timeline,path_timeline_content);
        String string = Tool.readUtilFiles("/usr/local/Whale/Util/timeline_detail_text");
        string = string.replace("${title}",title);
        string = string.replace("${summary}",summary);
        string = string.replace("${content}",content);
        return string;
    }

    public String queryNormal(int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = timelineDAO.queryNormal(page_no,length);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    public String queryRejected(int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = timelineDAO.queryRejected(page_no,length);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    public String queryUnchecked(int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = timelineDAO.queryUnchecked(page_no,length);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }

    public String queryDeleted(int page_no,int length) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = timelineDAO.queryDeleted(page_no,length);
        if (arrayList.size() == 0) {
            return QRY_RESULT_EMPTY;
        }
        return Tool.transformFromCollection(arrayList);
    }
}
