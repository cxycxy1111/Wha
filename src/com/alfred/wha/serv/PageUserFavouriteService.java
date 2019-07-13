package com.alfred.wha.serv;

import com.alfred.wha.dao.PageDAO;
import com.alfred.wha.dao.PageUserFavouriteDAO;
import com.alfred.wha.dao.TimelineUserFavouriteDAO;
import com.alfred.wha.util.Tool;

public class PageUserFavouriteService extends Service{

    private PageUserFavouriteDAO pageUserFavouriteDAO;
    private PageDAO pageDAO;

    public PageUserFavouriteService() {
        super();
        pageUserFavouriteDAO = new PageUserFavouriteDAO();
        pageDAO = new PageDAO();
    }

    public String addFavouriteTimeline(long user_id,long page_id) {
        if (pageUserFavouriteDAO.isExist(page_id,user_id)) {
            if (pageUserFavouriteDAO.isDeleted(page_id,user_id)) {
                if (pageUserFavouriteDAO.updateFavourite(page_id,user_id)) {
                    pageDAO.upvotePage(page_id);
                    return SUCCESS;
                }else {
                    return FAIL;
                }
            }else {
                return SUCCESS;
            }
        }else {
            if (pageUserFavouriteDAO.addFavourite(page_id,user_id)) {
                pageDAO.upvotePage(page_id);
                return SUCCESS;
            }else {
                return FAIL;
            }
        }
    }

    public String deleteFavouriteTimeline(long user_id,long page_id) {
        if (pageUserFavouriteDAO.isExist(page_id,user_id)) {
            if (pageUserFavouriteDAO.isDeleted(page_id,user_id)) {
                pageDAO.downvotePage(page_id);
                return SUCCESS;
            }else {
                if (pageUserFavouriteDAO.deleteFavourite(page_id,user_id)) {
                    pageDAO.downvotePage(page_id);
                    return SUCCESS;
                }else {
                    return FAIL;
                }
            }
        }else {
            return FAIL;
        }
    }

    public String queryFavouriteTimelineList(long user_id,int page_no) {
        return Tool.transformFromCollection(pageUserFavouriteDAO.queryFavouriteListByUserId(user_id,page_no));
    }
}
