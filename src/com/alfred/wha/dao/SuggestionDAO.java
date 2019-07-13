package com.alfred.wha.dao;

import com.alfred.wha.util.SQLHelper;
import com.alfred.wha.util.Tool;

import java.sql.SQLException;

public class SuggestionDAO extends DAO{

    private SQLHelper helper = new SQLHelper();

    public SuggestionDAO() {

    }


    public boolean add(String content) throws SQLException {
        return helper.update("INSERT INTO suggestion (content,create_time) VALUES ('" + content + "','" + Tool.getTime() + "')");
    }

}
