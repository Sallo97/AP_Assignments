package matchingPairGame.gui_components;

import matchingPairGame.logic_components.controller.Controller;
import matchingPairGame.logic_components.counter.Counter;
import javax.swing.*;
import java.awt.*;

/**
 * JPanel which contains the labels Counter and Controller
 */
public class InformationTab extends JPanel {
    // Properties
    public Controller controller = new Controller();
    public Counter counter = new Counter();

    // Constructor
    public InformationTab() {
        // Set Layout
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10)); // Centered with spacing
        this.add(controller);
        this.add(counter);
    }
}