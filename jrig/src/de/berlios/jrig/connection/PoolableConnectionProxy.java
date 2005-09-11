/*
 * $Id: PoolableConnectionProxy.java,v 1.1 2005/09/11 07:51:50 oone Exp $
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
 * $Id: PoolableConnectionProxy.java,v 1.1 2005/09/11 07:51:50 oone Exp $
 * ======================================================================
 *
 * Copyright (c) 2000-2004 TBCommerce Network Corp, All rights reserved.
 *
 */
package de.berlios.jrig.connection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This is the proxy implementation of the PoolableConnection.
 * <p>
 * Use of dynamic proxy is a cleaner way to handle the difference in 
 * java.sql.Connection API between 1.3, 1.4 and future version.
 * <p>
 * The performance setback is neglectable comparing to the cost of performing 
 * database operation.
 *
 * @author <a href="mailto:jrig.admin@gmail.com">Anthony Xin Chen</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/11 07:51:50 $
 */
public class PoolableConnectionProxy implements InvocationHandler {

    private Connection conn;
    private ConnectionPool pool;
    
    private boolean inuse;
    
    public PoolableConnectionProxy(Connection conn, ConnectionPool pool) {
        this.conn = conn;
        this.pool = pool;
    }
    
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        
        String methodName = method.getName();
        
        if ("lease".equals(methodName)) {
            return lease();
        }
        else if ("expireLease".equals(methodName)) {
            return expireLease();
        }
        else if ("inUse".equals(methodName)) {
            return inUse();
        }
        else if ("testConnection".equals(methodName)) {
            return testConnection();
        }
        else if ("getUnderlyingConnection".equals(methodName)) {
            return getUnderlyingConnection();
        }
        else if ("close".equals(methodName)) {
            return close(proxy);
        }
        else {
            return delegateToConnection(method, args);
        }
    }
    
    private synchronized Boolean lease() {
        if (this.inuse) {
            return Boolean.FALSE;
        }
        else {
            this.inuse = true;
            return Boolean.TRUE;
        }
    }
    
    private synchronized Class expireLease() {
        this.inuse = false;
        return Void.TYPE;
    }
    
    private synchronized Boolean inUse() {
        return this.inuse ? Boolean.TRUE : Boolean.FALSE;
    }
   
    /**
     * Check if connection is working alright.
     * 
     * @return
     */
    private Boolean testConnection() {
        try {
            this.conn.getMetaData();
        }
        catch (SQLException e) {
            return Boolean.FALSE;
        }
        
        return Boolean.TRUE;
    }
    
    /**
     * Instead of close the connection, 
     * we simply return the connection to the connection pool.
     * 
     * @param proxy
     * @return
     */
    private Class close(Object proxy) {
        this.pool.returnConnection((PoolableConnection) proxy);
        return Void.TYPE;
    }
    
    private Connection getUnderlyingConnection() {
        return this.conn;
    }
    
    private Object delegateToConnection(Method method, Object[] args) 
    throws Throwable {
        Object ret = null;
        
        try {
            ret = method.invoke(this.conn, args);
        }
        catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
        
        return ret;
    }
}
