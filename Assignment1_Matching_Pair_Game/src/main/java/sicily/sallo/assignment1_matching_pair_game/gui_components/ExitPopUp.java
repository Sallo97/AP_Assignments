package sicily.sallo.assignment1_matching_pair_game.gui_components;

import sicily.sallo.assignment1_matching_pair_game.common_enums.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ExitPopUp extends JDialog{
    // Properties
    private String exitText = "ARE YOU SURE YOU WANT TO EXIT?";
    private String backText = "GO BACK TO THE MAIN MENU?";
    JButton yesButton = new JButton("YES");
    JButton noButton = new JButton("NO");
    JLabel textLabel = new JLabel();

    // Constructors
    public ExitPopUp(JFrame parent, GameState state) {
        // Set common
        super(parent, "QUIT GAME", true);
        this.setSize(250, 100);
        this.setResizable(false);
        this.setLayout(new FlowLayout());


        noButton.addActionListener(event -> this.dispose());
        yesButton.addActionListener((ActionListener) parent);

        this.add(textLabel);
        this.add(yesButton);
        this.add(noButton);

        // Center the dialog relative to the parent frame
        this.setLocationRelativeTo(parent);

        update(state);
    }

    // Public Methods
    public void update(GameState state){
        if (state == GameState.MENU_SELECTION){
            textLabel.setText(exitText);
            yesButton.setActionCommand("exitApp");
        } else {
            textLabel.setText(backText);
            yesButton.setActionCommand("backToMenu");
        }

        this.setVisible(true);
    }

}


