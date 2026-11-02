package org.cis1200.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class RunSnakeGame implements Runnable {



    public void run() {

        System.out.println("RunSnakeGame");
        final JFrame frame = new JFrame("Snake Game");
        frame.setLocation(340, 300);

        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        final JPanel score_panel = new JPanel();
        frame.add(score_panel, BorderLayout.EAST);
        final JLabel score = new JLabel("Score: 0");
        score_panel.add(score);

        final GameCourt court = new GameCourt(status, score);
        frame.add(court, BorderLayout.CENTER);

        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> court.reset());
        control_panel.add(reset);

        final JButton save_game = new JButton("Save Game");
        save_game.addActionListener(e -> court.saveGame("current_state.txt"));
        control_panel.add(save_game);


        final JButton load_game = new JButton("Load Saved Game");
        load_game.addActionListener(e ->
            court.loadGame("current_state.txt")
        );
        control_panel.add(load_game);


        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new JFrame for the instructions pop-up window
                JFrame instructionsFrame = new JFrame("Instructions");
                instructionsFrame.setLocation(500, 300);

                // Create a JPanel for the instructions text
                JPanel instructionsPanel = new JPanel();
                instructionsPanel.setLayout(new BoxLayout(instructionsPanel, BoxLayout.Y_AXIS));

                // Add the instructions text to the JPanel
                instructionsPanel.add(new JLabel("Welcome to Snake Game!"));
                instructionsPanel.add(new JLabel("To play, use the arrow keys to " +
                        "move the snake around the board."));
                instructionsPanel.add(new JLabel("Your goal is to eat as many apples as possible " +
                        "without running into yourself or the walls. " +
                        "Note that the apple will increase your length 1. More tha 5 = you win!"));
                instructionsPanel.add(new JLabel(" the orange and green will stop your " +
                        "snake for 5 seconds, "
                        ));
                instructionsPanel.add(new JLabel("If you run into yourself or the walls," +
                        " the game is over! :("));
                instructionsPanel.add(new JLabel("You can save your game at any time by " +
                        "clicking the 'Save Game' button."));
                instructionsPanel.add(new JLabel("To load a saved game, " +
                        "click the 'Load Saved Game' button " +
                        "and select the saved game file."));
                instructionsPanel.add(new JLabel("Good luck and have fun!"));

                // Add the instructions panel to the instructions frame
                instructionsFrame.add(instructionsPanel);

                // Set the size and visibility of the instructions frame
                instructionsFrame.pack();
                instructionsFrame.setVisible(true);
            }
        });
        control_panel.add(instructions);




        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        court.reset();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new RunSnakeGame());
    }
}
