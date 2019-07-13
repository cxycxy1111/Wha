package com.alfred.wha.serv;

import com.alfred.wha.dao.SuggestionDAO;

import java.sql.SQLException;

public class SuggestionService extends Service{

    public SuggestionDAO suggestionDAO;

    public SuggestionService() {
        suggestionDAO = new SuggestionDAO();
    }

    public String add(String content) throws SQLException {
        if (content.length() > 0) {
            String c = content.replace("'","''");

            if (suggestionDAO.add(c)) {
                return SUCCESS;
            }else {
                return FAIL;
            }
        }else {
            return DUPLICATE;
        }

    }

}
