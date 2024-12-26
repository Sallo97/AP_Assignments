package matchingPairGame.gui_components;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PlayerSelector extends JPanel {
    // Properties
    private final JRadioButton onePlayer;
    private final String oneString = "One";

    private final JRadioButton twoPlayer;
    private final String twoString = "Two";

    private final JRadioButton threePlayer;
    private final String threeString = "Three";

    private final JRadioButton fourPlayer;
    private final String fourString = "Four";

    private final JLabel menuLabel;
    private int numOfPlayers = 1;

    // Constructors
    /**
     * TODO Add better description
     */
    public PlayerSelector() {
        // Set the buttons
        onePlayer = new JRadioButton(oneString);
        onePlayer.setSelected(true);
        onePlayer.addActionListener(this::setButton);

        twoPlayer = new JRadioButton(twoString);
        twoPlayer.addActionListener(this::setButton);

        threePlayer = new JRadioButton(threeString);
        threePlayer.addActionListener(this::setButton);

        fourPlayer = new JRadioButton(fourString);
        fourPlayer.addActionListener(this::setButton);

        menuLabel = new JLabel("SELECT NUMBER OF PLAYERS");

        setLayout();
    }
    // Public Methods
    /**
     * @return the numer of players selected by the user
     */
    public int getNumOfPlayers() {
        return this.numOfPlayers;
    }

    // Private Methods
    private void setButton(ActionEvent e) {
        String command = e.getActionCommand();

        // Update number of players
        numOfPlayers = switch (command){
            case oneString -> 1;
            case twoString -> 2;
            case threeString -> 3;
            case fourString -> 4;
            default -> throw new IllegalArgumentException("Invalid selection");
        };

        // Update the button states
        onePlayer.setSelected(command.equals(oneString));
        twoPlayer.setSelected(command.equals(twoString));
        threePlayer.setSelected(command.equals(threeString));
        fourPlayer.setSelected(command.equals(fourString));
    }

    /**
     * Configure the layout accordingly
     */
    private void setLayout(){
        // Set up the layout using GroupLayout
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        // Automatically set gaps between components
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Define the horizontal group
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(menuLabel)
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addComponent(onePlayer)
                                        .addComponent(twoPlayer)
                                        .addComponent(threePlayer)
                                        .addComponent(fourPlayer)
                        )
        );

        // Define the vertical group
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(menuLabel)
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(onePlayer)
                                        .addComponent(twoPlayer)
                                        .addComponent(threePlayer)
                                        .addComponent(fourPlayer)
                        )
        );
    }

}
