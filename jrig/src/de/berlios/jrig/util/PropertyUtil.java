/*
 * $Id: PropertyUtil.java,v 1.1 2005/09/10 20:46:57 oone Exp $
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
package de.berlios.jrig.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A utility to handle properties.
 *
 * @author <a href="mailto:jrig.admin@gmail.com">Anthony Xin Chen</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/10 20:46:57 $
 */
public final class PropertyUtil {
    
    private PropertyUtil() {
        // seal off access
    }
    
    public static Properties load(String name) throws IOException {
        ClassLoader cl = ClassLoaderUtil.getClassLoader();
        InputStream in = cl.getResourceAsStream(name);
        
        Properties p = new Properties();
        p.load(in);
        
        return p;
    }
}
