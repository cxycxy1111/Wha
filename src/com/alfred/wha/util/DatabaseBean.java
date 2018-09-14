package com.alfred.wha.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Whale?useUnicode=true&characterEncoding=utf8&useOldAliasMetadataBehavior=true&useSSL=true";
    private static final String USER = "root";
    private static final String PASSWORD = "cxycxy11";

    private static DataSource ds;

    /**
     * 初始化连接池代码块
     */
    static {
        try {
            ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setDriverClass(JDBC_DRIVER);
            cpds.setJdbcUrl(JDBC_URL);
            cpds.setUser(USER);
            cpds.setPassword(PASSWORD);
            cpds.setInitialPoolSize(5);
            cpds.setMaxPoolSize(500);
            cpds.setAcquireIncrement(3);
            cpds.setAcquireRetryAttempts(60);
            cpds.setAcquireRetryDelay(1000);
            cpds.setAutoCommitOnClose(false);
            cpds.setCheckoutTimeout(3000);
            cpds.setIdleConnectionTestPeriod(120);
            cpds.setMaxIdleTime(600);
            cpds.setMaxStatements(0);
            cpds.setTestConnectionOnCheckin(false);
            cpds.setMaxStatements(8);
            cpds.setMaxStatementsPerConnection(5);
            cpds.setUnreturnedConnectionTimeout(3);
            cpds.setMaxConnectionAge(20);
            ds = cpds;
        }catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDs() {
        return ds;
    }

    /**
     * 获取数据库连接对象
     *
     * @return 数据连接对象
     * @throws SQLException
     */
    public static synchronized Connection getConnection() throws SQLException {
        final Connection conn = ds.getConnection();
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return conn;
    }

}
