package sicily.sallo.assignment1_matching_pair_game.gui_components;

import sicily.sallo.assignment1_matching_pair_game.logic_components.buttons.Exit;
import sicily.sallo.assignment1_matching_pair_game.logic_components.buttons.Shuffle;

import javax.swing.*;
import java.awt.*;


/**
 * TODO Add better description
 * JPanel containing both the Shuffle Button and Exit Button
 */
public class CommandButtons extends JPanel{
    // Properties
    public JButton shuffleButton = new JButton("SHUFFLE");
    public JButton exitButton = new JButton("EXIT");

    // Constructors
    /**
     * Sets horizontally the buttons and defines the action when clicking them
     */
    public CommandButtons() {

        // Set layout
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centered with spacing
        this.add(shuffleButton);
        this.add(exitButton);

        // Set action commands
        shuffleButton.setActionCommand("shuffle");
        exitButton.setActionCommand("exit");


    }
}
