/*
 * $Id: Version.java,v 1.1 2005/09/10 20:46:57 oone Exp $
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
package de.berlios.jrig;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import de.berlios.jrig.util.PropertyUtil;

/**
 *
 *
 * @author <a href="mailto:jrig.admin@gmail.com">Anthony Xin Chen</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/10 20:46:57 $
 */
public final class Version {
    
    /**
     * major version 
     */
    public static final String MAJOR;
    
    /**
     * minor version
     */
    public static final String MINOR;
    
    /**
     * revision
     */
    public static final String REVISION;
    
    /**
     * project status
     */
    public static final String STATUS;

    private static final String VERBOSE_FORMAT = 
        "JRig ver {0}.{1}.{2}-{3}";
    
    static {
        Properties p = null;
        try {
            p = PropertyUtil.load("de/berlios/jrig/version.properties");
        }
        catch (IOException ioe) {
            throw new IllegalStateException("Unable to load version.properties!");
        }
        
        MAJOR = p.getProperty("version.major");
        MINOR = p.getProperty("version.minor");
        REVISION = p.getProperty("version.revision");
        STATUS = p.getProperty("version.status");
    }
    
    private Version() {
        // seal off access
    }
    
    public static String verbosePrint() {
        Object[] args = new Object[] {
                MAJOR,
                MINOR,
                REVISION,
                STATUS,
        };
        return MessageFormat.format(VERBOSE_FORMAT, args);
    }
}
