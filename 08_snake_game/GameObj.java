package org.cis1200.snake;


import java.awt.Graphics;
/**
 * An object in the game.
 *
 * Game objects exist in the game court. They have a position, velocity, size
 * and bounds. Their velocity controls how they move; their position should
 * always be within their bounds.
 */
public abstract class GameObj {
    /*
     * Current position of the object (in terms of graphics coordinates)
     *
     * Coordinates are given by the upper-left hand corner of the object. This
     * position should always be within bounds:
     * 0 <= px <= maxX 0 <= py <= maxY
     */
    private static int px;

    public static int getOpx() {
        return px;
    }

    public static void setOpx(int value) {
        px = value;
    }
    private static int py;

    public static int getOpy() {
        return py;
    }

    public static void setOpy(int value) {
        py = value;
    }

    /* Size of object, in pixels. */
    private final int width;


    private final int height;

    /* Velocity: number of pixels to move every time move() is called. */
    private static int vx;

    public static int getOvx() {
        return vx;
    }

    public static void setOvx(int value) {
        vx = value;
    }
    private static int vy;

    public static int getOvy() {
        return vy;
    }

    public static void setOvy(int value) {
        vy = value;
    }

    /*
     * Upper bounds of the area in which the object can be positioned. Maximum
     * permissible x, y positions for the upper-left hand corner of the object.
     */
    private final int maxX;
    private final int maxY;
    private boolean[][] body;

    /**
     * Constructor
     */
    public GameObj(
            int vx, int vy, int px, int py, int width, int height, int courtwidth,
            int courtheight
    ) {
        this.vx = vx;
        this.vy = vy;
        this.px = px;
        this.py = py;
        this.width = width;
        this.height = height;

        // take the width and height into account when setting the bounds for
        // the upper left corner of the object.
        this.maxX = courtwidth - width;
        this.maxY = courtheight - height;
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

    public int getVx() {
        return this.vx;
    }

    public int getVy() {
        return this.vy;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    /**
     * Returns the 2D boolean array representing the snake's body.
     *
     * @return the 2D boolean array representing the snake's body
     */



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

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public void setBody(boolean[][] body) {
        this.body = body;
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
     * Determine whether this game object is currently intersecting another
     * object.
     *
     * Intersection is determined by comparing bounding boxes. If the bounding
     * boxes overlap, then an intersection is considered to occur.
     *
     * @param food The other object
     * @return Whether this object intersects the other object.
     */
    public boolean intersects(Food food) {

        return (this.getPx() < food.getPx() + food.getWidth() &&
                this.getPx() + this.getWidth() > food.getPx() &&
                this.getPy() < food.getPy() + food.getHeight() &&
                this.getPy() + this.getHeight() > food.getPy());
    }


    /**
     * Determine whether this game object will intersect another in the next
     * time step, assuming that both objects continue with their current
     * velocity.
     *
     * Intersection is determined by comparing bounding boxes. If the bounding
     * boxes (for the next time step) overlap, then an intersection is
     * considered to occur.
     *
     * @param that The other object
     * @return Whether an intersection will occur.
     */
    public boolean willIntersect(Food that) {
        int thisNextX = this.px + this.vx;
        int thisNextY = this.py + this.vy;
        int thatNextX = that.getPx();
        int thatNextY = that.getPy();

        return (thisNextX + this.width >= thatNextX
                && thisNextY + this.height >= thatNextY
                && thatNextX + that.getWidth() >= thisNextX
                && thatNextY + that.getHeight() >= thisNextY);
    }

    /**
     * Update the velocity of the object in response to hitting an obstacle in
     * the given direction. If the direction is null, this method has no effect
     * on the object.
     *
     * @param d The direction in which this object hit an obstacle
     */




    /**
     * Determine whether the game object will hit another object in the next
     * time step. If so, return the direction of the other object in relation to
     * this game object.
     *
     * As you read through the code, it might be useful to draw things out and
     * visualize the unit circle.
     *
     * @param that The other object
     * @return Direction of impending object after collision, or null if no
     *         collision.
     */
    public org.cis1200.snake.Direction hitObj(Food that) {
        if (this.willIntersect(that)) {
            /*
             * Note that this.px + halfThiswidth = position of the rightmost side of the
             * object,
             * and this.py + halfThisheight = position of the top side of the object.
             * The reason why we are getting these measures is to be able to calculate the
             * above.
             */
            double halfThiswidth = (double) this.width / 2;
            double halfThatwidth = (double) that.getWidth() / 2;
            double halfThisheight = (double) this.height / 2;
            double halfThatheight = (double) that.getHeight() / 2;
            final double PI_OVER_4 = Math.PI / 4;

            /*
             * dx represents the horizontal distance between "this" and "that".
             * dy represents the vertical distance between "this" and "that".
             * We are getting these measures because we are trying to build a triangle
             * in order to calculate an angle (more specifically, the angle between
             * adjacent and hypotenuse through arc-cosine).
             */
            double dx = that.getPx() + halfThatwidth - (this.px + halfThiswidth);
            double dy = that.getPy() + halfThatheight - (this.py + halfThisheight);
            double theta = Math.acos(dx / (Math.sqrt(dx * dx + dy * dy)));

            /*
             * As you read through the following, it will be useful to visualize
             * the unit circle
             * Link: https://etc.usf.edu/clipart/43200/43205/unit-circle13_43205.htm
             */
            if (theta <= PI_OVER_4) {
                /*
                 * For example, if theta is >= 0 and <= pi/4, after collision
                 * "that" must be going right
                 */
                return org.cis1200.snake.Direction.RIGHT;
            } else if (theta <= Math.PI - PI_OVER_4) {
                /*
                 * Remember that the coordinate system for GUIs is switched; this means
                 * since (0,0) is in the top left corner, if "this" is above "that", it will
                 * have
                 * a lower y-value, and dy will therefore be positive. In this case, since
                 * "that"
                 * is below "this", "that" will be moving downards post-collision.
                 */
                if (dy > 0) {
                    return org.cis1200.snake.Direction.DOWN;
                } else {
                    return org.cis1200.snake.Direction.UP;
                }
            } else {
                return Direction.LEFT;
            }
        } else {
            return null;
        }
    }








    public abstract void draw(Graphics g);
}