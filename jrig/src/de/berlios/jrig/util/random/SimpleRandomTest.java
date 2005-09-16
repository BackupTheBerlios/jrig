/*
 * $Id: SimpleRandomTest.java,v 1.1 2005/09/16 18:17:30 oone Exp $
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
package de.berlios.jrig.util.random;

import java.util.Random;

import junit.framework.TestCase;

/**
 *
 *
 * @author <a href="mailto:jrig.admin@gmail.com">Anthony Xin Chen</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/16 18:17:30 $
 */
public class SimpleRandomTest extends TestCase {

    private SimpleRandom random = new SimpleRandom(new Random());
    
    public static void main(String[] args) {
        junit.textui.TestRunner.run(SimpleRandomTest.class);
    }
    
    // _____________________________________
    //
    // SimpleRandom#getRange
    // _____________________________________
    
    public void testGetRangeWithHiLowerThanLo() {
        try {
            random.getRange(5, 3);
        }
        catch (IllegalArgumentException ex) {
            assertTrue(true);
            return;
        }
        
        fail();
    }

    public void testGetRangeWithHiEqualToLo() {
        int i = 10;
        
        int r = random.getRange(i, i);
        
        assertEquals(i, r);
    }
    
    public void testGetRangeByStress() {
        int hi = 10;
        int lo = -10;
        
        int repeat = (hi - lo) * 100;
        
        for (int i = 0; i < repeat; i++) {
            int r = random.getRange(lo, hi);
            assertTrue(r >= lo && r <= hi);
        }
    }
    
    // _____________________________________
    //
    // SimpleRandom#getChance
    // _____________________________________
    
    public void testGetChanceWithNegative() {
        try {
            random.getChance(-1);
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        
        fail();
    }
    
    public void testGetChanceWithZero() {
        boolean r = random.getChance(0);
        assertFalse(r);
    }
    
    public void testGetChanceWithMoreThanOneHundred() {
        try {
            random.getChance(101);
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        
        fail();
    }
    
    public void testGetChanceWithOneHundred() {
        boolean r = random.getChance(100);
        assertTrue(r);
    }
    
    // _____________________________________
    //
    // SimpleRandom#getAssignment
    // _____________________________________
    
    public void testAssignmentWithNegativeDensity() {
        int[] density = { 10, -10, };
                
        try {
            random.getAssignment(density);
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        
        fail();
    }
    
    public void testAssignmentWithZeroTotalDensity() {
        int[] density = {0};
        try {
            random.getAssignment(density);
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        
        fail();
    }
    
    public void testAssignmentWithZeroDensity() {
        int[] density = {0, 1, 1, 0, 1};
        
        int r = random.getAssignment(density);
        assertTrue(r != 0 && r != 3);
    }
    
    public void testAssignmentByStress() {
        int[] density = {10, 0, 0, 10, 10, 20, 0};
        
        int repeat = 100;
        
        for (int i = 0; i < repeat; i++) {
            int r = random.getAssignment(density);
            assertTrue(r == 0 || r == 3 || r == 4 || r == 5);
        }
    }
}
