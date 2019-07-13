package com.alfred.wha.serv;

import com.alfred.wha.dao.TimelineUserFavouriteDAO;
import com.alfred.wha.util.Tool;

public class TimelineUserFavouriteService extends Service{

    private TimelineUserFavouriteDAO timelineUserFavouriteDAO;

    public TimelineUserFavouriteService() {
        super();
        timelineUserFavouriteDAO = new TimelineUserFavouriteDAO();
    }

    public String addFavouriteTimeline(long user_id,long timeline_id) {
        if (timelineUserFavouriteDAO.isExist(timeline_id,user_id)) {
            if (timelineUserFavouriteDAO.isDeleted(timeline_id,user_id)) {
                if (timelineUserFavouriteDAO.updateFavourite(timeline_id,user_id)) {
                    return SUCCESS;
                }else {
                    return FAIL;
                }
            }else {
                return SUCCESS;
            }
        }else {
            if (timelineUserFavouriteDAO.addFavourite(timeline_id,user_id)) {
                return SUCCESS;
            }else {
                return FAIL;
            }
        }
    }

    public String deleteFavouriteTimeline(long user_id,long timeline_id) {
        if (timelineUserFavouriteDAO.isExist(timeline_id,user_id)) {
            if (timelineUserFavouriteDAO.isDeleted(timeline_id,user_id)) {
                return SUCCESS;
            }else {
                if (timelineUserFavouriteDAO.deleteFavourite(timeline_id,user_id)) {
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
        return Tool.transformFromCollection(timelineUserFavouriteDAO.queryFavouriteListByUserId(user_id,page_no));
    }
}
