package org.cis1200;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Use this file to test your implementation of Pixel.
 * 
 * We will manually grade this file to give you feedback
 * about the completeness of your test cases.
 */

public class MyPixelTest {

    /*
     * Remember, UNIT tests should ideally have one point of failure. Below we
     * give you two examples of unit tests for the Pixel constructor, one that
     * takes in three ints as arguments and one that takes in an array. We use
     * the getRed(), getGreen(), and getBlue() methods to check that the values
     * were set correctly. These two tests do not comprehensively test all of
     * Pixel so you must add your own.
     * 
     * You might want to look into assertEquals, assertTrue, assertFalse, and
     * assertArrayEquals at the following:
     * http://junit.sourceforge.net/javadoc/org/junit/Assert.html
     *
     * Note, if you want to add global variables so that you can reuse Pixels
     * in multiple tests, feel free to do so.
     */

    @Test
    public void testConstructInBounds() {
        Pixel p = new Pixel(40, 50, 60);
        assertEquals(40, p.getRed());
        assertEquals(50, p.getGreen());
        assertEquals(60, p.getBlue());
    }

    @Test
    public void testConstructArrayLongerThan3() {
        int[] arr = { 10, 20, 30, 40 };
        Pixel p = new Pixel(arr);
        assertEquals(10, p.getRed());
        assertEquals(20, p.getGreen());
        assertEquals(30, p.getBlue());
    }

    /* ADD YOUR OWN TESTS BELOW */

    /* Test for all constructors equaling 0 */
    @Test
    public void testConstructAllZero() {
        Pixel p = new Pixel(0, 0, 0);
        assertEquals(0, p.getRed());
        assertEquals(0, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    /* Test for all constructors equaling max values */
    @Test
    public void testConstructMaxValues() {
        Pixel p = new Pixel(255, 255, 255);
        assertEquals(255, p.getRed());
        assertEquals(255, p.getGreen());
        assertEquals(255, p.getBlue());
    }

    /* Test for all constructors being negatively out of bounds */
    @Test
    public void testConstructNegativeValues() {
        Pixel p = new Pixel(-10, -20, -30);
        assertEquals(0, p.getRed());
        assertEquals(0, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    /* Test for all constructors being more than 255 */
    @Test
    public void testConstructValuesGreaterThan255() {
        Pixel p = new Pixel(300, 400, 500);
        assertEquals(255, p.getRed());
        assertEquals(255, p.getGreen());
        assertEquals(255, p.getBlue());
    }

    /* Test for all constructors all being equal */
    @Test
    public void testConstructArrayAllEqual() {
        int[] arr = { 50, 50, 50 };
        Pixel p = new Pixel(arr);
        assertEquals(50, p.getRed());
        assertEquals(50, p.getGreen());
        assertEquals(50, p.getBlue());
    }

    /* Test for all 2 constructors being out of range */
    @Test
    public void testConstructArrayOutOfRange() {
        int[] arr = { 100, 256, -50 };
        Pixel p = new Pixel(arr);
        assertEquals(100, p.getRed());
        assertEquals(255, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    /* Test for a constructor to not be an integer */
    @Test
    public void testConstructArrayNonInteger() {
        int[] arr = {10, 20, (int) 30.5 };
        Pixel p = new Pixel(arr);
        assertEquals(10, p.getRed());
        assertEquals(20, p.getGreen());
        assertEquals(30, p.getBlue());
    }

    /* Test for length of constructors being less than 3 */
    public void testConstructArrayShorterThan3() {
        int[] arr = { 10, 20};
        Pixel p = new Pixel(arr);
        assertEquals(10, p.getRed());
        assertEquals(20, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    /* Test for length of constructors being more than 4 */
    @Test
    public void testConstructArrayLongerThan3B() {
        int[] arr = { 10, 20, 30, 40, 50 };
        Pixel p = new Pixel(arr);
        assertEquals(10, p.getRed());
        assertEquals(20, p.getGreen());
        assertEquals(30, p.getBlue());
    }

    /* Test for length of constructors being all negative and same values */
    @Test
    public void testConstructAllNegativeValues() {
        Pixel p = new Pixel(-50, -50, -50);
        assertEquals(0, p.getRed());
        assertEquals(0, p.getGreen());
        assertEquals(0, p.getBlue());
    }

}
