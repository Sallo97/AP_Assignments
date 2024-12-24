package sicily.sallo.assignment1_matching_pair_game.gui_components;

import javax.swing.*;
import java.awt.*;

public class ExitPopUp extends JDialog{
    // Properties

    // Constructors
    public ExitPopUp(JFrame parent) {
        // Create a custom dialog
        JDialog dialog = new JDialog(parent, "QUIT GAME", true); // `true` makes it modal
        dialog.setSize(250, 100);
        dialog.setResizable(false);
        dialog.setLayout(new FlowLayout());

        JLabel label = new JLabel("ARE YOU SURE YOU WANT TO EXIT?");
        JButton yesButton = new JButton("YES");
        JButton noButton = new JButton("NO");


        // Add an ActionListener to close the dialog
        yesButton.addActionListener(event -> dialog.getOwner().dispose());
        noButton.addActionListener(event -> dialog.dispose());

        dialog.add(label);
        dialog.add(yesButton);
        dialog.add(noButton);

        // Center the dialog relative to the parent frame
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    // Public Methods


}


