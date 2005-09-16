/*
 * $Id: AbstractGenerator.java,v 1.1 2005/09/16 18:18:02 oone Exp $
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
 * This class provides default implementation of the {@link Generator}.  
 * Standard behaviors like the get and set methods are defined here. 
 * The developer need only subclass this abstract class and define 
 * the {@link #nextData} method.
 *
 * @author <a href="mailto:jrig.admin@gmail.com">Anthony Xin Chen</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/16 18:18:02 $
 */
public abstract class AbstractGenerator implements Generator {

    private IRandom random;
    private int nullChance;

    /**
     * Standard behaviors like making sure random generator has been set and determining if 
     * returning null by throwing a dice according to nullChance value are defined.
     * <p>
     * Subclass should override the {@link #nextData} method to implement actual data generating logic.
     *  
     * @see de.berlios.jrig.generator.Generator#next()
     */
    public Object next() {
        if (getRandom() == null) {
            throw new IllegalStateException("No random generator configured");
        }
        
        if (getNullChance() > 0 && getRandom().getChance(getNullChance())) {
            return null;
        }
        
        return nextData();
    }
    
    /**
     * Subclass should override this method and implement data generating logic.
     * 
     * @return the next data.
     */
    protected abstract Object nextData();
    
    /*
     * @see de.berlios.jrig.generator.Generator#getNullChance()
     */
    public int getNullChance() {
        return this.nullChance;
    }

    /*
     * @see de.berlios.jrig.generator.Generator#setNullChance(int)
     */
    public void setNullChance(int nullChance) {
        if (nullChance < 0 || nullChance > 100) {
            throw new IllegalArgumentException(
                "Null chance must be between 0 (inclusive) and 100 (inclusive)");
        }
        
        this.nullChance = nullChance;
    }
    
    /*
     * @see de.berlios.jrig.generator.Generator#getRandom()
     */
    public IRandom getRandom() {
        return this.random;
    }
    
    /*
     * @see de.berlios.jrig.generator.Generator#setRandom(de.berlios.jrig.util.random.IRandom)
     */
    public void setRandom(IRandom random) {
        this.random = random;
    }
}
