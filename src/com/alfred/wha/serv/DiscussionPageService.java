package com.alfred.wha.serv;

import com.alfred.wha.dao.DiscussionPageDAO;
import com.alfred.wha.dao.DiscussionPageUserFavouriteDAO;
import com.alfred.wha.dao.DiscussionTimelineUserFavouriteDAO;
import com.alfred.wha.util.Tool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DiscussionPageService extends Service{

    private DiscussionPageDAO discussionPageDAO;
    private DiscussionPageUserFavouriteDAO discussionPageUserFavouriteDAO;

    public DiscussionPageService() {
        super();
        discussionPageDAO =new DiscussionPageDAO();
        discussionPageUserFavouriteDAO = new DiscussionPageUserFavouriteDAO();
    }

    public String add(long page_id,long parent_discussion_id,long event_id,long user_id,int user_type,int source,String summary,int anonymous) {
        String str = Tool.readUtilFiles("/usr/local/Whale/Util/dirtyword");
        str = str.replace("\n",",");
        String[] strs = str.split(",");
        for (int i = 0;i<strs.length;i++) {
            if (summary.toLowerCase().contains(strs[i])) {
                return FAIL;
            }
        }
        if (parent_discussion_id != 0) {
            ArrayList<HashMap<String,Object>> list = new ArrayList<>();
            list = discussionPageDAO.queryByDiscussionId(parent_discussion_id);
            if (Tool.getLongFromArrayList(list,"user_id") == user_id&&Tool.getLongFromArrayList(list,"user_type") == user_type) {
                return FAIL;
            }
        }
        if (discussionPageDAO.add(page_id,parent_discussion_id,event_id,user_id,user_type,source,summary,anonymous)) {
            return SUCCESS;
        }
        return FAIL;
    }

    public String queryByPage(long page_id,long user_id,int page_no,boolean login_status) {
        ArrayList<HashMap<String,Object>> list = new ArrayList<>();
        list = discussionPageDAO.queryByPage(page_id,page_no);
        ArrayList<HashMap<String,Object>> result_list = new ArrayList<>();
        if (!login_status) {
            result_list = new ArrayList<>();
            for (HashMap<String, Object> aList : list) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap = aList;
                hashMap.put("isFavourite", "no");
                result_list.add(hashMap);
            }
        }else {
            if (list.size() > 0) {
                StringBuilder builder = new StringBuilder();
                for (HashMap<String, Object> aList : list) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap = aList;
                    builder.append(hashMap.get("id")).append(",");
                }
                String str = builder.toString();
                str = str.substring(0,str.length()-1);
                ArrayList<HashMap<String,Object>> list_favourite_list = new ArrayList<>();
                list_favourite_list = discussionPageUserFavouriteDAO.queryByDiscussionIds(user_id,str);
                for (int i = 0;i<list.size();i++) {
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap = list.get(i);
                    if (list_favourite_list.size()>0) {
                        for (int j = 0;j < list_favourite_list.size();j++) {
                            HashMap<String,Object> hashMap_2 = new HashMap<>();
                            hashMap_2 = list_favourite_list.get(j);
                            if (String.valueOf(hashMap.get("id")).equals(String.valueOf(hashMap_2.get("discussion_page_id")))) {
                                hashMap.put("isFavourite","yes");
                            }
                        }
                        if (!hashMap.containsKey("isFavourite")) {
                            hashMap.put("isFavourite","no");
                        }
                    }else {
                        hashMap.put("isFavourite","no");
                    }
                    result_list.add(hashMap);
                }
            }

        }
        for (int i = 0;i < result_list.size();i++) {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap = result_list.get(i);
            //处理标题
            if (Integer.parseInt(String.valueOf(hashMap.get("parent_discussion_id"))) != 0) {
                String nick_name = String.valueOf(hashMap.get("nick_name"));
                String replier_nick_name = String.valueOf(hashMap.get("replier_nick_name"));
                hashMap.put("replier_nick_name",replier_nick_name + " 回复 " +nick_name);
                result_list.set(i,hashMap);
            }
        }
        return Tool.transformFromCollection(list);
        
    }

    public String queryBySource(int source,int page_no) {
        return Tool.transformFromCollection(discussionPageDAO.queryBySource(source,page_no));
    }

    public String report(long discussion_id,int type) {
        try {
            if (discussionPageDAO.addReport(discussion_id,type)) {
                return SUCCESS;
            }else {
                return FAIL;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return FAIL;
        }

    }
}
