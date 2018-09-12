package com.alfred.wha.util;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class SQLHelper {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSSSS");
    Calendar Cld = Calendar.getInstance();
    int YY = Cld.get(Calendar.YEAR) ;
    int MM = Cld.get(Calendar.MONTH)+1;
    int DD = Cld.get(Calendar.DATE);
    int HH = Cld.get(Calendar.HOUR_OF_DAY);
    int mm = Cld.get(Calendar.MINUTE);
    int SS = Cld.get(Calendar.SECOND);
    int MI = Cld.get(Calendar.MILLISECOND);
    String str = YY + "/" + MM + "/" + DD + "-" + HH + ":" + mm + ":" + SS + ":" + MI;

    public SQLHelper() {

    }

    /**
     * 数据查询
     * @param sql 语句
     * @return 返回结果集List<Object>
     */
    public ArrayList<HashMap<String,Object>> query(String sql) {
        System.out.println(sdf.format(new Date()) +  ",SQL:" + sql);
        if(sql.equals("") || sql == null){
            return null;
        }
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
        try {
            conn = DatabaseBean.getConnection();
            try {
                ps = conn.prepareStatement(sql);
                try {
                    rs = ps.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    // 可以得到有多少列
                    int columnNum = rsmd.getColumnCount();
                    // 将数据封装到list中
                    while (rs.next()) {
                        HashMap<String,Object> map = new HashMap<>();
                        for (int i = 0; i < columnNum; i++) {
                            map.put(rsmd.getColumnName(i+1), rs.getObject(i+1));
                        }
                        list.add(map);
                    }
                }catch (Exception e) {
                    rs.close();
                }
            }catch (Exception e) {
                ps.close();
            }
        } catch (Exception e) {
            try {
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 用于键名可能会重复的查询
     * @param sql
     * @return
     */
    public ArrayList<IdentityHashMap<String,Object>> linkquery(String sql) {
        System.out.println(sdf.format(new Date()) +  ",SQL:" + sql);
        if(sql.equals("") || sql == null){
            return null;
        }
        ArrayList<IdentityHashMap<String, Object>> list = new ArrayList<IdentityHashMap<String,Object>>();
        try {
            conn = DatabaseBean.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            // 可以得到有多少列
            int columnNum = rsmd.getColumnCount();
            // 将数据封装到list中
            while (rs.next()) {
                IdentityHashMap<String,Object> map = new IdentityHashMap<>();
                for (int i = 0; i < columnNum; i++) {
                    map.put(rsmd.getColumnName(i+1), rs.getObject(i+1));
                }
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 插入、修改数据操作
     * @param sql 语句
     * @return boolean 成功返回true，失败返回false
     */
    public boolean update(String sql) throws SQLException {
        System.out.println(sdf.format(new Date()) +  ",SQL:" + sql);
        boolean b = false;
        if(sql.equals("") || sql == null){
            return b;
        }
        try {
            conn = DatabaseBean.getConnection();
            ps = conn.prepareStatement(sql);
            int i = ps.executeUpdate();
            if (i == 1) {
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
        return b;
    }

}
