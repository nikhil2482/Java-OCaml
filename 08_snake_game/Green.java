package org.cis1200.snake;

import java.awt.*;
import java.util.Random;


public class Green extends Food {

    public static final int SIZE = 20;
    public static final int INIT_POS_X = 100;
    public static final int INIT_POS_Y = 170;


    public Green(int courtWidth, int courtHeight, Color color) {

        super(INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);



        Random rand = new Random();
        int maxNum = 340;
        int num = rand.nextInt(maxNum / 20 + 1) * 20;
        setPx(num);
        int num2 = rand.nextInt(maxNum / 20 + 1) * 20;
        setPy(num2);
    }

    /**
     * Draws the Circle on the specified Graphics object.
     *
     * @param g The Graphics object on which to draw the Circle.
     */
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(GameCourt.getGRX(), GameCourt.getGRY(), 20, 20);

    }

    // The amount to slow down the snake by
    private final int slowDownAmount = -10;



    /**
     * Stops the snake for the specified amount of time.
     *
     * @param snake The snake to slow down.
     * @param time  The amount of time to slow down the snake for.
     */
    private static void slowDownSnake(Snake snake, int slowDownAmount, long time) {


        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    /**
     * Called when the Green object is eaten by the snake.
     *
     * @param snake The snake that ate the Green object.
     * @param court The GameCourt in which the Green object was eaten.
     */

    public static void onEaten(Snake snake, GameCourt court) {
        // Slow down the snake for 10 seconds
        System.out.println("On Eaten is called");
        slowDownSnake(snake, 1000, 5000);
    }



    public static Green parseGreen(String s) {
        String[] parts = s.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        Color color = Color.GREEN; // default color if not provided
        if (parts.length > 2) {
            color = new Color(Integer.parseInt(parts[2]));
        }
        return new Green(x, y, color);
    }


    @Override
    public String toString() {
        return String.format("%d,%d", GameCourt.getGRX(), GameCourt.getGRY());
    }



}

