/*
 * $Id: IRandom.java,v 1.1 2005/09/16 18:17:30 oone Exp $
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

/**
 * Random generator is going to be a major part of the project effort. 
 * Although we are going to use JDK built-in java.util.Random for a start, 
 * we need to make sure we are able to switch implementation when the need 
 * arises.
 * <p>
 * In addition, uses of interface makes use of mock object in unit test possible.
 * Otherwise, random behavior is going to be hard to test. 
 * 
 *
 * @author <a href="mailto:jrig.admin@gmail.com">Anthony Xin Chen</a>
 * @version $Revision: 1.1 $ $Date: 2005/09/16 18:17:30 $
 */
public interface IRandom {
    /**
     * Returns a number in the range of lo and hi, lo <= number <= hi.
     * 
     * @param lo
     * @param hi
     * @return a number in the range of lo and hi, lo <= number <= hi.
     */
    public int getRange(int lo, int hi);

    /**
     * The chance of this function returning true is 'chanceToBeTrue' times out of 100.
     * In other words, the probability of returning true is less or equal to 'chanceToBeTrue',
     * 0 < probability <= chanceToBeTrue.
     * 
     * @param chanceToBeTrue the chance returning true. 
     * Must be between 0 (inclusie) and 100 (inclusive).
     * 
     * @return true 'chanceToBeTrue' times out of 100
     */
    public boolean getChance(int chanceToBeTrue);

    /**
     * Returns the assignment based on the density. 
     * 
     * @param density the density allocation. Density must be non-negative.
     * @return the assignment based on the density
     */
    public int getAssignment(int[] density);

    // _____________________________________
    //
    // as java.util.Random api
    // _____________________________________
    
    /**
     * Returns the next pseudorandom, uniformly distributed boolean value 
     * from this random number generator's sequence.
     * 
     * @see java.util.Random
     * @return the next pseudorandom, uniformly distributed boolean value 
     * from this random number generator's sequence.
     */
    public boolean nextBoolean();

    /**
     * Generates random bytes and places them into a user-supplied byte array. 
     * The number of random bytes produced is equal to the length of the byte array.
     * @see java.util.Random
     * 
     * @param bytes the non-null byte array in which to put the random bytes
     */
    public void nextBytes(byte[] bytes);

    /**
     * Returns the next pseudorandom, uniformly distributed double value between 
     * 0.0 and 1.0 from this random number generator's sequence.
     * 
     * @see java.util.Random
     * 
     * @return the next pseudorandom, uniformly distributed double value between 
     * 0.0 and 1.0 from this random number generator's sequence.
     */
    public double nextDouble();

    /**
     * Returns the next pseudorandom, uniformly distributed float value between 
     * 0.0 and 1.0 from this random number generator's sequence.
     * 
     * @see java.util.Random
     * 
     * @return the next pseudorandom, uniformly distributed float value between 
     * 0.0 and 1.0 from this random number generator's sequence.
     */
    public float nextFloat();

    /**
     * Returns the next pseudorandom, Gaussian ("normally") distributed double value 
     * with mean 0.0 and standard deviation 1.0 from this random number generator's 
     * sequence.
     * 
     * @see java.util.Random
     * 
     * @return the next pseudorandom, Gaussian ("normally") distributed double value 
     * with mean 0.0 and standard deviation 1.0 from this random number generator's 
     * sequence.
     */
    public double nextGaussian();

    /**
     * Returns the next pseudorandom, uniformly distributed int value from this 
     * random number generator's sequence.
     * 
     * @see java.util.Random
     * 
     * @return the next pseudorandom, uniformly distributed int value from this 
     * random number generator's sequence.
     */
    public int nextInt();

    /**
     * Returns a pseudorandom, uniformly distributed int value between 0 (inclusive) 
     * and the specified value (exclusive), drawn from this random number generator's 
     * sequence.
     * 
     * @see java.util.Random
     * 
     * @param n the bound on the random number to be returned. Must be positive.
     * 
     * @return a pseudorandom, uniformly distributed int value between 0 (inclusive) 
     * and the specified value (exclusive), drawn from this random number generator's 
     * sequence.
     */
    public int nextInt(int n);

    /**
     * Returns the next pseudorandom, uniformly distributed long value from this random 
     * number generator's sequence.
     * 
     * @see java.util.Random
     * 
     * @return the next pseudorandom, uniformly distributed long value from this random 
     * number generator's sequence.
     */
    public long nextLong();
}
