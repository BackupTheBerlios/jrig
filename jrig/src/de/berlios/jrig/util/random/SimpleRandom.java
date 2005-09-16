/*
 * $Id: SimpleRandom.java,v 1.1 2005/09/16 18:17:30 oone Exp $
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


/**
 * A simple IRandom implementation using java.util.Random.
 *
 * @author <a href="mailto:jrig.admin@gmail.com">Anthony Xin Chen</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/16 18:17:30 $
 */
public class SimpleRandom implements IRandom {
    private Random random;
    
    public SimpleRandom(Random random) {
        this.random = random;
    }

    /*
     * @see de.berlios.jrig.util.Random.IRandom#getRange(int, int)
     */
    public int getRange(int lo, int hi) {
        if (lo > hi) {
            throw new IllegalArgumentException("lo > hi");
        }

        // get the range, casting to long to avoid overflow problems
        long range = (long) hi - (long) lo + 1;

        // compute a fraction of the range, 0 <= frac < range
        long frac = (long) (range * nextDouble());

        return (int) (frac + lo);
    }

    /*
     * @see de.berlios.jrig.util.Random.IRandom#getChance(int)
     */
    public boolean getChance(int chanceToBeTrue) {
        if (chanceToBeTrue < 0 || chanceToBeTrue > 100) {
            throw new IllegalArgumentException("chance must be between 0 (inclusive) and 100 (inclusive)");
        }

        return chanceToBeTrue >= getRange(1, 100);
    }
    
    
    /*
     * @see de.berlios.jrig.util.Random.IRandom#getAssignment(int[])
     */
    public int getAssignment(int[] density) {
        int totalDensity = 0;

        for (int i = 0; i < density.length; i++) {
            int n = density[i];
            
            if (n < 0) {
                throw new IllegalArgumentException("density must be non-negative");
            }

            totalDensity += n;
        }
        
        if (totalDensity == 0) {
            throw new IllegalArgumentException("total density must be positive");
        }

        int draw = getRange(1, totalDensity);
        
        int pt = 1;
        
        for (int i = 0; i < density.length; i++) {
            int n = density[i];
            
            // skip if density is zero
            if (n == 0) {
                continue;
            }
            
            int lo = pt;
            int hi = n + pt - 1;

            if (draw >= lo && draw <= hi) {
                return i;
            }
            
            pt = hi + 1;
        }
        
        throw new IllegalStateException("should never reach here");
    }
    
    // _____________________________________
    //
    // delegating methods
    // _____________________________________
    
    /*
     * @see de.berlios.jrig.util.Random.IRandom#nextBoolean()
     */
    public boolean nextBoolean() {
        return this.random.nextBoolean();
    }

    /*
     * @see de.berlios.jrig.util.Random.IRandom#nextBytes(byte[])
     */
    public void nextBytes(byte[] bytes) {
        this.random.nextBytes(bytes);
    }

    /*
     * @see de.berlios.jrig.util.Random.IRandom#nextDouble()
     */
    public double nextDouble() {
        return this.random.nextDouble();
    }

    /*
     * @see de.berlios.jrig.util.Random.IRandom#nextFloat()
     */
    public float nextFloat() {
        return this.random.nextFloat();
    }

    /*
     * @see de.berlios.jrig.util.Random.IRandom#nextGaussian()
     */
    public double nextGaussian() {
        return this.random.nextGaussian();
    }

    /*
     * @see de.berlios.jrig.util.Random.IRandom#nextInt()
     */
    public int nextInt() {
        return this.random.nextInt();
    }

    /*
     * @see de.berlios.jrig.util.Random.IRandom#nextInt(int)
     */
    public int nextInt(int n) {
        return this.random.nextInt(n);
    }

    /*
     * @see de.berlios.jrig.util.Random.IRandom#nextLong()
     */
    public long nextLong() {
        return this.random.nextLong();
    }
}
