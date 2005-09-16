/*
 * $Id: Generator.java,v 1.1 2005/09/16 18:18:02 oone Exp $
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
package de.berlios.jrig.generator;

import de.berlios.jrig.util.random.IRandom;

/**
 * This represents data generator.
 *
 * @author <a href="mailto:jrig.admin@gmail.com">Anthony Xin Chen</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/16 18:18:02 $
 */
public interface Generator {
    
    /**
     * Returns the next data.
     * 
     * @return the next data.
     */
    public Object next();
    
    /**
     * Sets the chance of {@link #next()} returning null. 
     * 
     * @param nullChance the chance of {@link #next()} returning null. 
     * Must be between 0 (inclusive) and 100 (inclusive).
     */
    public void setNullChance(int nullChance);
    
    /**
     * Returns the chance of {@link #next()} returning null. 
     * 
     * @return the chance of {@link #next()} returning null. 
     */
    public int getNullChance();
    
    /**
     * Sets the random generator for use.
     * 
     * @param random the random generator
     */
    public void setRandom(IRandom random);
    
    /**
     * Returns the random generator.
     * 
     * @return the random generator
     */
    public IRandom getRandom();
}
