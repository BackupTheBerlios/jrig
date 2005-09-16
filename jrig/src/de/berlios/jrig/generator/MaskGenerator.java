/*
 * $Id: MaskGenerator.java,v 1.1 2005/09/16 18:18:02 oone Exp $
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

import java.util.Arrays;

/**
 * a [a..z]
 * A [A..Z]
 * n [0..9]
 * N [1..9]
 * w [a..z0..9]
 * W [a..zA..Z0..9]
 * {n} repeat
 *
 * @author <a href="mailto:jrig.admin@gmail.com">Anthony Xin Chen</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/16 18:18:02 $
 */
public class MaskGenerator extends AbstractGenerator {
    
    private String mask;
    
    public MaskGenerator() {
        
    }
    
    public MaskGenerator(String mask) {
        this.mask = mask;
    }
    
    public String getMask() {
        return this.mask;
    }
    
    public void setMask(String mask) {
        if (invalidMask(mask)) {
            throw new IllegalArgumentException("String literal \\ not properly escaped");
        }
        
        this.mask = mask;
    }
    
    private boolean invalidMask(String mask) {
        if (mask == null) {
            return false; 
        }
        
        
        
        return false;
    }
    
    protected Object nextData() {
        return null;
    }
    
    // _____________________________________
    //
    // inner class
    // _____________________________________
    
    class MaskTokenizer {
        private char[] mask;
        private int pt;
        
        public MaskTokenizer(String mask) {
            this.mask = mask.toCharArray();
        }
        
        public boolean hasNext() {
            return pt < mask.length;
        }
    }
    
    interface Token {
        public String generate();
    }
    
    class ConstantToken {
        private String value;
        
        public ConstantToken(String value) {
            this.value = value;
        }
        
        public String generate() {
            return this.value;
        }
    }
    
    class MaskSignToken {
        private String sign;
        private String options;
        
        public MaskSignToken(String sign) {
            this.sign = sign;
            
            if ("a".equals(sign)) {
                this.options = "abcdefghijklmnopqrstuvwxyz";
            }
            else if ("A".equals(sign)) {
                this.options = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            }
            else if ("n".equals(sign)) {
                this.options = "0123456789";
            }
            else if ("N".equals(sign)) {
                this.options = "123456789";
            }
            else if ("w".equals(sign)) {
                this.options = "abcdefghijklmnopqrstuvwxyz123456789";
            }
            else if ("W".equals(sign)) {
                this.options = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
            }
            else {
                throw new IllegalArgumentException("Invalid sign: " + sign);
            }
        }
        
        public String generate() {
            int len = this.options.length();
            
            int r = getRandom().getRange(0, len - 1);
            
            return this.options.substring(r, r + 1);
        }
    }
}

