package org.cis1200.snake;

import java.awt.*;
import java.util.Random;

/**
 * A circular food apple object for the Snake game.
 */
public class Apple extends Food {

    public static final int SIZE = 20;
    public static final int INIT_POS_X = 170;
    public static final int INIT_POS_Y = 170;



    public Apple(int courtWidth, int courtHeight, Color color) {

        super(INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);

        // Create random points to put the object in within bounds
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
        g.setColor(Color.RED);
        g.fillOval(GameCourt.getAPX(), GameCourt.getAPY(), 20, 20);
    }

    public static Apple parseApple(String s) {
        String[] parts = s.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        Color color = Color.RED; // default color if not provided
        if (parts.length > 2) {
            color = new Color(Integer.parseInt(parts[2]));
        }
        return new Apple(x, y, color);
    }


    @Override
    public String toString() {
        return String.format("%d,%d", GameCourt.getAPX(), GameCourt.getAPY());
    }




}
