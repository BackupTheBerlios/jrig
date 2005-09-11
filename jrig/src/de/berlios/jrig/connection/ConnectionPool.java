/*
 * $Id: ConnectionPool.java,v 1.2 2005/09/11 08:13:16 oone Exp $
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
 * $Id: ConnectionPool.java,v 1.2 2005/09/11 08:13:16 oone Exp $
 * ======================================================================
 *
 * Copyright (c) 2000-2004 TBCommerce Network Corp, All rights reserved.
 *
 */
package de.berlios.jrig.connection;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import de.berlios.jrig.util.ClassLoaderUtil;
import de.berlios.jrig.util.JdbcUtil;

/**
 * This is a simple Connection Pool implementation.
 *
 * @author <a href="mailto:jrig.admin@gmail.com">Anthony Xin Chen</a>
 * @version $Revision: 1.2 $ $Date: 2005/09/11 08:13:16 $
 */
public class ConnectionPool {
    
    private static final int DEFAULT_POOL_SIZE = 2;
    
    /**
     * Instantiates a connection pool.
     * 
     * @param driver JDBC driver name
     * @param url JDBC connection string
     * @param info 
     * @return
     * @throws ClassNotFoundException
     */
    public static ConnectionPool instance(String driver, String url, Properties info) 
    throws ClassNotFoundException {
        ConnectionPool pool = new ConnectionPool();
        
        pool.init(driver, url, info);
        
        return pool;
    }
    
    /**
     * Instantiates a connection pool.
     * 
     * @param driver driver JDBC driver name
     * @param url JDBC connection string
     * @param user database user
     * @param password database password
     * @return
     * @throws ClassNotFoundException
     */
    public static ConnectionPool instance(String driver, String url, String user, String password) 
    throws ClassNotFoundException {
        ConnectionPool pool = new ConnectionPool();
        
        pool.init(driver, url, user, password);
        
        return pool;
    }
    
    private String driver;
    private String url;
    
    private Properties info;
    
    private Vector connections;
    
    private ConnectionPool() {
        this.connections = new Vector(DEFAULT_POOL_SIZE);
    }
    
    private void init(
            String aDriver, 
            String aUrl, 
            Properties aInfo) throws ClassNotFoundException {
        
        this.driver = aDriver;
        this.url = aUrl;
        this.info = aInfo;
        
        initDriver();
    }
    
    private void init(
            String aDriver, 
            String aUrl, 
            String aUser, 
            String aPassword) throws ClassNotFoundException {
        
        Properties p = new Properties();
        
        p.put("user", aUser);
        p.put("password", aPassword);
        
        init(aDriver, aUrl, p);
    }
    
    private void initDriver() throws ClassNotFoundException {
        Class.forName(this.driver);
    }
    
    /**
     * Retrieves a connection from the connection pool.
     * 
     * @return
     * @throws SQLException
     */
    public synchronized Connection getConnection() throws SQLException {
        
        PoolableConnection pc = null;
        
        for (Iterator iter = this.connections.iterator(); iter.hasNext();) {
            pc = (PoolableConnection) iter.next();
            
            if (pc.lease()) {
                return pc;
            }
        }
        
        Connection conn = getRealConnection();
        
        pc = (PoolableConnection) Proxy.newProxyInstance(
            ClassLoaderUtil.getClassLoader(PoolableConnection.class), 
            new Class[] {PoolableConnection.class},
            new PoolableConnectionProxy(conn, this));
        
        pc.lease();
        
        connections.add(pc);
        
        return pc;
    } 
    
    /**
     * Retrieves an actual database connection.
     * 
     * @return
     * @throws SQLException
     */
    private Connection getRealConnection() throws SQLException {
        return DriverManager.getConnection(this.url, this.info);
    }
     
    /**
     * Return the poolable connection to the pool.
     * 
     * @param pc
     */
    public synchronized void returnConnection(PoolableConnection pc) {
 
        if (this.connections.size() > DEFAULT_POOL_SIZE) {
            Connection conn = pc.getUnderlyingConnection();
            JdbcUtil.closeConnection(conn);
            this.connections.remove(pc);
        }
        else {
            pc.expireLease();
        }
    }
    
    /**
     * Shuts down the connection pool. Close all underlying connections.
     */
    public synchronized void shutdown() {
        
        for (Iterator iter = this.connections.iterator(); iter.hasNext();) {
            PoolableConnection pc = (PoolableConnection) iter.next();
            
            Connection conn = pc.getUnderlyingConnection();
            
            JdbcUtil.closeConnection(conn);
        }
   
        this.connections.clear();
    }
}