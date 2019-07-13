package com.alfred.wha.serv;

import com.alfred.wha.dao.DiscussionPageDAO;
import com.alfred.wha.dao.DiscussionPageUserFavouriteDAO;
import com.alfred.wha.dao.DiscussionTimelineDAO;
import com.alfred.wha.dao.DiscussionTimelineUserFavouriteDAO;
import com.alfred.wha.util.Tool;

public class DiscussionPageUserFavouriteService extends Service{

    private DiscussionPageUserFavouriteDAO discussionPageUserFavouriteDAO;
    private DiscussionPageDAO discussionPageDAO;

    public DiscussionPageUserFavouriteService() {
        super();
        discussionPageUserFavouriteDAO = new DiscussionPageUserFavouriteDAO();
        discussionPageDAO = new DiscussionPageDAO();
    }

    public String addFavouritePage(long user_id,long discussion_id,String operate) {
        if (operate.equals("add")) {
            if (discussionPageUserFavouriteDAO.isExist(discussion_id,user_id)) {
                if (discussionPageUserFavouriteDAO.isDeleted(discussion_id,user_id)) {
                    if (discussionPageUserFavouriteDAO.updateFavourite(discussion_id,user_id)) {
                        discussionPageDAO.upvoteFavourite(discussion_id);
                        return SUCCESS;
                    }else {
                        return FAIL;
                    }
                }else {
                    return SUCCESS;
                }
            }else {
                if (discussionPageUserFavouriteDAO.addFavourite(discussion_id,user_id)) {
                    discussionPageDAO.upvoteFavourite(discussion_id);
                    return SUCCESS;
                }else {
                    return FAIL;
                }
            }
        }else {
            return this.deleteFavouritePage(user_id,discussion_id);
        }

    }

    private String deleteFavouritePage(long user_id,long discussion_id) {
        if (discussionPageUserFavouriteDAO.isExist(discussion_id,user_id)) {
            if (discussionPageUserFavouriteDAO.isDeleted(discussion_id,user_id)) {
                return SUCCESS;
            }else {
                if (discussionPageUserFavouriteDAO.deleteFavourite(discussion_id,user_id)) {
                    discussionPageDAO.downvoteFavourite(discussion_id);
                    return SUCCESS;
                }else {
                    return FAIL;
                }
            }
        }else {
            if (discussionPageUserFavouriteDAO.deleteFavourite(discussion_id,user_id)){
                discussionPageDAO.downvoteFavourite(discussion_id);
                return SUCCESS;
            }else {
                return FAIL;
            }
        }
    }

    public String queryFavouritePageDiscussionList(long user_id) {
        return Tool.transformFromCollection(discussionPageUserFavouriteDAO.queryFavouriteListByUserId(user_id));
    }
}
