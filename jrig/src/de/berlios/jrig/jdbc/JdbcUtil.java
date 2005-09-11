/*
 * $Id: JdbcUtil.java,v 1.1 2005/09/11 19:15:27 oone Exp $
 * ======================================================================
 *
 * JRig - Java Relational Information Generator
 *
 * Copyright (C) 2005 Anthony Xin Chen, All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
/*
 * $Id: JdbcUtil.java,v 1.1 2005/09/11 19:15:27 oone Exp $
 * ======================================================================
 *
 * Copyright (c) 2000-2004 TBCommerce Network Corp, All rights reserved.
 *
 */
package de.berlios.jrig.jdbc;

import java.sql.*;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Common JDBC Operations.
 *
 * @author <a href="mailto:jrig.admin@gmail.com">Anthony Xin Chen</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/11 19:15:27 $
 */
public class JdbcUtil {
    
    static final Logger LOG = Logger.getLogger(JdbcUtil.class);
    
    private JdbcUtil() {
        // seal off access
    }
    
    public static Connection getConnection(String url, Properties info) 
    throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, info);
        }
        catch (SQLException ex) {
            LOG.error(printSqlException(ex));
            throw ex;
        }
        
        return conn;
    }
    
    public static void closeResultSet(ResultSet rs) {
        if (rs == null) {
            return;
        }
        
        try {
            rs.close();
        }
        catch (SQLException ex) {
            LOG.warn(printSqlException(ex));
        }
    }
    
    public static void closeStatement(Statement statement) {
        if (statement == null) {
            return;
        }
        
        try {
            statement.close();
        }
        catch (SQLException ex) {
            LOG.warn(printSqlException(ex));
        }
    }
    
    public static void closeConnection(Connection connection) {
        if (connection == null) {
            return;
        }
        
        try {
            connection.close();
        }
        catch (SQLException ex) {
            LOG.warn(printSqlException(ex));
        }
    }
    
    public static void close(ResultSet rs, Statement statement, Connection connection) {
        closeResultSet(rs);
        closeStatement(statement);
        closeConnection(connection);
    }
    
    public static String printSqlException(SQLException sqlex) {
        StringBuffer sb = new StringBuffer();
        
        sb.append("SQLException:\n");
        
        while (sqlex != null) {
            
            sb.append(sqlex.getMessage() + "\n");
            sb.append("SQL State: " + sqlex.getSQLState() + "\n");
            sb.append("Vendor Error Code: " + sqlex.getErrorCode() + "\n");
            
            sqlex = sqlex.getNextException();
        }
        
        return sb.toString();
    }
    
    public static String printSqlWarning(SQLWarning warn) {
        if (warn == null) {
            return null;
        }
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("SQLWarning:\n");
        
        while (warn != null) {
            
            sb.append(warn.getMessage() + "\n");
            sb.append("SQL State: " + warn.getSQLState() + "\n");
            sb.append("Vendor Error Code: " + warn.getErrorCode() + "\n");
            
            warn = warn.getNextWarning();
        }
        
        return sb.toString();
    }
}
