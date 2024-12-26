package matchingPairGame.gui_components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * TODO Add better description
 * JPanel containing both the Shuffle Button and Exit Button
 */
public class CommandButtons extends JPanel implements ActionListener {
    // Properties
    public JButton shuffleButton = new JButton("SHUFFLE");
    public JButton exitButton = new JButton("EXIT");
    public JButton highScoreButton = new JButton("HIGH SCORE");

    // Constructors
    /**
     * Sets horizontally the buttons and defines the action when clicking them
     */
    public CommandButtons() {

        // Set layout
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centered with spacing
        this.add(shuffleButton);
        this.add(exitButton);
        this.add(highScoreButton);

        shuffleButton.addActionListener(this);
        exitButton.addActionListener(this);
        highScoreButton.addActionListener(this);

        // Set action commands
        shuffleButton.setActionCommand("shuffle");
        exitButton.setActionCommand("exit");
        highScoreButton.setActionCommand("highScore");

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "shuffle":
                highScoreButton.setVisible(false);
                break;

            case "exit":
                shuffleButton.setVisible(true);
                highScoreButton.setVisible(true);
                break;

            case "highScore":
                shuffleButton.setVisible(false);
                highScoreButton.setVisible(false);
                break;
        }
    }
}
