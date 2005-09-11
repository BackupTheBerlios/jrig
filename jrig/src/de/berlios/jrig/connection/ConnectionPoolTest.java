/*
 * $Id: ConnectionPoolTest.java,v 1.2 2005/09/11 19:15:27 oone Exp $
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
 * $Id: ConnectionPoolTest.java,v 1.2 2005/09/11 19:15:27 oone Exp $
 * ======================================================================
 *
 * Copyright (c) 2000-2004 TBCommerce Network Corp, All rights reserved.
 *
 */
package de.berlios.jrig.connection;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.TestCase;
import de.berlios.jrig.jdbc.JdbcUtil;

/**
 *
 *
 * @author <a href="mailto:jrig.admin@gmail.com">Anthony Xin Chen</a>
 * @version $Revision: 1.2 $ $Date: 2005/09/11 19:15:27 $
 */
public class ConnectionPoolTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ConnectionPoolTest.class);
    }

    private ConnectionPool pool;
    
    public ConnectionPoolTest(String method) {
        super(method);
    }

    protected void setUp() throws Exception {
        super.setUp();
        this.pool = ConnectionPool.instance(
            "org.hsqldb.jdbcDriver",
            "jdbc:hsqldb:mem:test", 
            "sa", 
            "");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        this.pool.shutdown();
    }

    public void testExerciseAll() {
        Connection conn = null; 
        
        try {
            conn = this.pool.getConnection();
        }
        catch (SQLException e) {
            fail();
        }
        finally {
            JdbcUtil.closeConnection(conn);
        }
    }
}
