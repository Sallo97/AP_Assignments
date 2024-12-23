package sicily.sallo.assignment1_matching_pair_game.gui_components;

import sicily.sallo.assignment1_matching_pair_game.logic_components.controller.Controller;
import sicily.sallo.assignment1_matching_pair_game.logic_components.counter.Counter;
import javax.swing.*;
import java.awt.*;

/**
 * JPanel which contains the labels Counter and Controller
 */
public class InformationTab extends JPanel {
    // Properties
    private Controller controller;
    private Counter counter;

    // Constructor
    public InformationTab(Controller controller, Counter counter) {
        // Set properties
        this.controller = controller;
        this.counter = counter;

        // Set Layout
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Centered with spacing
        this.add(controller);
        this.add(counter);
    }
}