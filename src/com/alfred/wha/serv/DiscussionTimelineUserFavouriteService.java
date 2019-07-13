package com.alfred.wha.serv;

import com.alfred.wha.dao.DiscussionTimelineDAO;
import com.alfred.wha.dao.DiscussionTimelineUserFavouriteDAO;
import com.alfred.wha.dao.PageDAO;
import com.alfred.wha.dao.PageUserFavouriteDAO;
import com.alfred.wha.util.Tool;

public class DiscussionTimelineUserFavouriteService extends Service{

    private DiscussionTimelineUserFavouriteDAO discussionTimelineUserFavouriteDAO;
    private DiscussionTimelineDAO discussionTimelineDAO;

    public DiscussionTimelineUserFavouriteService() {
        super();
        discussionTimelineUserFavouriteDAO = new DiscussionTimelineUserFavouriteDAO();
        discussionTimelineDAO = new DiscussionTimelineDAO();
    }

    public String addFavouriteTimeline(long user_id,long discussion_id,String operate) {
        if (operate.equals("add")) {
            if (discussionTimelineUserFavouriteDAO.isExist(discussion_id,user_id)) {
                if (discussionTimelineUserFavouriteDAO.isDeleted(discussion_id,user_id)) {
                    if (discussionTimelineUserFavouriteDAO.updateFavourite(discussion_id,user_id)) {
                        discussionTimelineDAO.upvotePage(discussion_id);
                        return SUCCESS;
                    }else {
                        return FAIL;
                    }
                }else {
                    return SUCCESS;
                }
            }else {
                if (discussionTimelineUserFavouriteDAO.addFavourite(discussion_id,user_id)) {
                    discussionTimelineDAO.upvotePage(discussion_id);
                    return SUCCESS;
                }else {
                    return FAIL;
                }
            }
        }else {
            return this.deleteFavouriteTimeline(user_id,discussion_id);
        }

    }

    private String deleteFavouriteTimeline(long user_id,long discussion_id) {
        if (discussionTimelineUserFavouriteDAO.isExist(discussion_id,user_id)) {
            if (discussionTimelineUserFavouriteDAO.isDeleted(discussion_id,user_id)) {
                return SUCCESS;
            }else {
                if (discussionTimelineUserFavouriteDAO.deleteFavourite(discussion_id,user_id)) {
                    discussionTimelineDAO.downvotePage(discussion_id);
                    return SUCCESS;
                }else {
                    return FAIL;
                }
            }
        }else {
            if (discussionTimelineUserFavouriteDAO.deleteFavourite(discussion_id,user_id)) {
                discussionTimelineDAO.downvotePage(discussion_id);
                return SUCCESS;
            }else {
                return FAIL;
            }
        }
    }

    public String queryFavouriteTimelineDiscussionList(long user_id) {
        return Tool.transformFromCollection(discussionTimelineUserFavouriteDAO.queryFavouriteListByUserId(user_id));
    }
}
