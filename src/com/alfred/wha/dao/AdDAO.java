package com.alfred.wha.dao;

import com.alfred.wha.util.SQLHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class AdDAO extends DAO{

    private SQLHelper helper = new SQLHelper();

    public AdDAO() {

    }

    public ArrayList<HashMap<String,Object>> query() {
        return helper.query("SELECT * FROM ad WHERE del=0");
    }

}
