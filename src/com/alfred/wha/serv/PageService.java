package com.alfred.wha.serv;

import com.alfred.wha.dao.PageDAO;
import com.alfred.wha.dao.PageUserFavouriteDAO;
import com.alfred.wha.util.Tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PageService extends Service{

    private PageDAO pageDAO = new PageDAO();
    private PageUserFavouriteDAO pageUserFavouriteDAO = new PageUserFavouriteDAO();
    private static String path = "";

    public PageService () {
        pageDAO = new PageDAO();
        pageUserFavouriteDAO = new PageUserFavouriteDAO();
    }

    /**
     * 新增
     * @param event_id
     * @param page_title
     * @param page_summary
     * @param content
     * @param status
     * @param user_id
     * @param user_type
     * @param author
     * @param source
     * @param happen_time
     * @return
     */
    public String addPage(long event_id,String page_title,String page_summary,String content,int status,long user_id,int user_type,String author,String source,String happen_time,String url) {
        if (pageDAO.add(event_id,page_title,page_summary,status,user_id,user_type,author,source,happen_time,url)) {
            int id = pageDAO.queryMaxId();
            Tool.writeFiles(id,path_page_content,content);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    public String deletePage(long id) {
        if (pageDAO.delete(id)) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 更新
     * @param id
     * @param page_title
     * @param page_summary
     * @param page_content
     * @param author
     * @param source
     * @return
     */
    public String updatePage(long id,String page_title,String page_summary,String page_content,String author,String source,String url) {
        if (pageDAO.update(id,page_title,page_summary,author,source,url)) {
            Tool.writeFiles(id,path_page_content,page_content);
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 通过文章ID查询
     * @param id
     * @return
     */
    public String queryDetail(long id,long user_id,boolean loginStatus) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = pageDAO.queryByPage(id,1,1);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.putAll(arrayList.get(0));
        String content = Tool.readFiles(id,path_page_content);
        hashMap.put("content",content);
        if (loginStatus) {
            if (pageUserFavouriteDAO.isExist(id,user_id) && !pageUserFavouriteDAO.isDeleted(id,user_id)) {
                hashMap.put("isFavourite","yes");
            }else {
                hashMap.put("isFavourite","no");
            }
        }else {
            hashMap.put("isFavourite","no");
        }
        ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        list.add(hashMap);
        return Tool.transformFromCollection(list);
    }


    /**
     * 通过事件ID查询
     * @return
     */
    public String queryByEvent(long event_id,int page_no) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = pageDAO.queryByEvent(event_id,page_no,20);
        return Tool.transformFromCollection(arrayList);
    }


    /**
     * 通过类型查询
     * @param type
     * @return
     */
    public String queryByType(String type,int page_no) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = pageDAO.queryByType(type,page_no,20);
        return Tool.transformFromCollection(arrayList);
    }

    /**
     * 通过类型查询
     * @return
     */
    public String query(int page_no) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = pageDAO.query(page_no,20);
        Collections.shuffle(arrayList);
        return Tool.transformFromCollection(arrayList);
    }

    public String queryByCreatorId(long user_id,int user_type,int page_no) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = pageDAO.queryByCreator(user_id,user_type,page_no,20);
        return Tool.transformFromCollection(arrayList);
    }

    public String publicSharePage(long pageId) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = pageDAO.queryByPage(pageId,1,1);
        String title = String.valueOf(arrayList.get(0).get("page_title"));
        String summary = String.valueOf(arrayList.get(0).get("page_summary"));
        String content = Tool.publicShareReadFiles(pageId,path_page_content);
        String string = Tool.readUtilFiles("/usr/local/Whale/Util/page_detail_text");
        string = string.replace("${title}",title);
        string = string.replace("${summary}",summary);
        string = string.replace("${content}",content);
        return string;
    }

}
