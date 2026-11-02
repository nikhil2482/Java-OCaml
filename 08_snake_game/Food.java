package org.cis1200.snake;

import java.awt.Color;
import java.awt.*;
import java.util.Random;

/**

 A parent class for different types of food in the Snake game.
 */
public abstract class Food {
    /*
     * Current position of the object (in terms of graphics coordinates)
     *
     * Coordinates are given by the upper-left hand corner of the object. This
     * position should always be within bounds:
     * 0 <= px <= maxX 0 <= py <= maxY
     */
    private static int px;


    private static int py;



    /* Size of object, in pixels. */
    private static int width;




    private final int height;


    /*
     * Upper bounds of the area in which the object can be positioned. Maximum
     * permissible x, y positions for the upper-left hand corner of the object.
     */
    private final int maxX;
    private final int maxY;

    /**
     * Constructor
     */
    public Food(
            int px, int py, int width, int height, int courtwidth,
            int courtheight
    ) {
        this.px = px;
        this.py = py;
        this.width = width;
        this.height = height;

        // take the width and height into account when setting the bounds for
        // the upper left corner of the object.
        this.maxX = courtwidth - width;
        this.maxY = courtheight - height;

        /* Random rand = new Random();
        this.px = rand.nextInt(maxX);
        this.py = rand.nextInt(maxY); */
    }

    // **********************************************************************************
    // * GETTERS
    // **********************************************************************************
    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }


    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    // **************************************************************************
    // * SETTERS
    // **************************************************************************
    public void setPx(int px) {
        this.px = px;
        clip();
    }

    public void setPy(int py) {
        this.py = py;
        clip();
    }


    // **************************************************************************
    // * UPDATES AND OTHER METHODS
    // **************************************************************************

    /**
     * Prevents the object from going outside the bounds of the area
     * designated for the object (i.e. Object cannot go outside the active
     * area the user defines for it).
     */
    private void clip() {
        this.px = Math.min(Math.max(this.px, 0), this.maxX);
        this.py = Math.min(Math.max(this.py, 0), this.maxY);
    }

    /**
     * Removes the current Food object, creates a new one at a random position,
     * and draws the new object on the Graphics object provided.
     *
     * @param g The Graphics object on which to draw the new Food object.
     */
    public void respawn(Graphics g) {
        // Clear old object by filling with background color
        g.setColor(Color.WHITE);
        g.fillOval(getPx(), getPy(), 20, 20);

        // Create new object at random position
        Random rand = new Random();
        this.px = rand.nextInt(maxX);
        this.py = rand.nextInt(maxY);

        // Draw new object on Graphics object
        draw(g);
    }


    /**
     * Default draw method that provides how the object should be drawn in the
     * GUI. This method does not draw anything. Subclass should override this
     * method based on how their object should appear.
     *
     * @param g The <code>Graphics</code> context used for drawing the object.
     *          Remember graphics contexts that we used in OCaml, it gives the
     *          context in which the object should be drawn (a canvas, a frame,
     *          etc.)
     */
    public abstract void draw(Graphics g);

    public static int generateRandomInt(int max) {
        if (max <= 0) {
            throw new IllegalArgumentException("max must be positive");
        }
        Random rand = new Random();
        return rand.nextInt(max);
    }
}


