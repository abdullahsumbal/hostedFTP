package com.hostedftp.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class DbConnector {
    private static DataSource ds;

    public static Connection getConnection() throws Exception {
        if (ds == null) {
            Context initCtx = new InitialContext();
            Context envCtx  = (Context) initCtx.lookup("java:/comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/HostedFtpDS");
        }
        return ds.getConnection();
    }
}