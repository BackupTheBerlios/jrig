/*
 * $Id: PoolableConnection.java,v 1.1 2005/09/11 07:51:50 oone Exp $
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
 * $Id: PoolableConnection.java,v 1.1 2005/09/11 07:51:50 oone Exp $
 * ======================================================================
 *
 * Copyright (c) 2000-2004 TBCommerce Network Corp, All rights reserved.
 *
 */
package de.berlios.jrig.connection;

import java.sql.Connection;

/**
 * Poolable Connection.
 *
 * @author <a href="mailto:jrig.admin@gmail.com">Anthony Xin Chen</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/11 07:51:50 $
 */
public interface PoolableConnection extends Connection {
    
    /**
     * Lease the connection from the connection pool.
     * 
     * @return true if lease successfully, false otherwise
     */
    public boolean lease();

    /**
     * The connection is no longer being leased.
     */
    public void expireLease();

    /**
     * @return true if in use
     */
    public boolean inUse();

    /**
     * Check if the connection works alright.
     * 
     * @return true if in working order. false otherwise.
     */
    public boolean testConnection();   
    
    /**
     * @return the underlying jdbc connection
     */
    public Connection getUnderlyingConnection();
}
