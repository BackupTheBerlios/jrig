/*
 * $Id: RandomGenerator.java,v 1.1 2005/09/12 07:27:45 oone Exp $
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

import java.util.Random;

/**
 *
 *
 * @author <a href="mailto:jrig.admin@gmail.com">Anthony Xin Chen</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/12 07:27:45 $
 */
public class RandomGenerator {
    public static RandomGenerator instance(Random random) {
        return new RandomGenerator(random);
    }
    
    private Random random;
    
    private RandomGenerator(Random random) {
        this.random = random;
    }

    /**
     * get random numbers, lo <= number <= hi
     * 
     * @param lo
     * @param hi
     * @return
     */
    public int getRange(int lo, int hi) {
        if (lo > hi) {
            throw new IllegalArgumentException("lo > hi");
        }

        // get the range, casting to long to
        // avoid overflow problems

        long range = (long) hi - (long) lo + 1;

        // compute a fraction of the range, 
        // 0 <= frac < range

        long frac = (long) (range * nextDouble());

        // add the fraction to the lo value and
        // return the sum

        return (int) (frac + lo);
    }

    /**
     * return true perc % of the time
     * 
     * @param perc
     * @return
     */
    public boolean getPerc(int perc) {

        // error checking

        if (perc < 0 || perc > 100) {
            throw new IllegalArgumentException("bad perc");
        }

        return perc >= getRange(1, 100);
    }
    
    
    public int getDist(int[] distribution) {
        int total = 0;
        int negative = 0;
        
        for (int i = 0; i < distribution.length; i++) {
            int n = distribution[i];
            
            if (n >= 0) {
                total += n;
            }
            else {
                negative += 1;
            }
        }
        
        if (total > 100 && negative > 1) {
            throw new IllegalArgumentException("can not have negative distribution when total positive distribution exceeds 100");
        }
        
        
        int negDist = 0;
        
        if (negative != 0) {
            negDist = (100 - total) / negative;
        }
        
        int[][] dist = new int[distribution.length][2];
        
        int pt = 1;
        
        for (int i = 0; i < distribution.length; i++) {
            int start = pt;
            int end = 0;
            if (distribution[i] < 0) {
                end = negDist + pt - 1;
            }
            else {
                end = distribution[i] + pt - 1;
            }
            dist[i][0] = start;
            dist[i][1] = end;
            pt = end + 1;
        }
        
        dist[distribution.length][1] = 100;
        
        int r = getRange(1, 100);
        
        for (int i = 0; i < dist.length; i++) {
            if (r >= dist[i][0] && r <= dist[i][1]) {
                return i;
            }
        }
        
        return dist.length;
    }
    
    // _____________________________________
    //
    // delegating methods
    // _____________________________________
    
    public boolean nextBoolean() {
        return this.random.nextBoolean();
    }

    public void nextBytes(byte[] bytes) {
        this.random.nextBytes(bytes);
    }

    public double nextDouble() {
        return this.random.nextDouble();
    }

    public float nextFloat() {
        return this.random.nextFloat();
    }

    public double nextGaussian() {
        return this.random.nextGaussian();
    }

    public int nextInt() {
        return this.random.nextInt();
    }

    public int nextInt(int n) {
        return this.random.nextInt(n);
    }

    public long nextLong() {
        return this.random.nextLong();
    }
}
