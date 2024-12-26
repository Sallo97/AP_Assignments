package matchingPairGame.gui_components;

import matchingPairGame.common_enums.GameDifficulty;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * A GUI for selecting the difficulty level (easy, medium, or hard) in the game.
 *
 * @author sallo
 */
public class DifficultyChooser extends JPanel {
    // Properties
    private final JRadioButton easyButton;
    private final String easyString = "easy";

    private final JRadioButton mediumButton;
    private final String mediumString = "medium";

    private final JRadioButton hardButton;
    private final String hardString = "hard";

    private final JLabel menuLabel = new JLabel();
    private GameDifficulty difficulty = GameDifficulty.EASY;

    // Constructors
    /**
     * Sets the default difficulty to "easy" and arranges the components
     * with an appropriate layout.
     */
    public DifficultyChooser() {

        // Set the buttons
        easyButton = new JRadioButton();
        easyButton.setText(easyString);
        easyButton.setSelected(true);
        easyButton.addActionListener(this::setButton);

        mediumButton = new JRadioButton();
        mediumButton.setText(mediumString);
        mediumButton.addActionListener(this::setButton);

        hardButton = new JRadioButton();
        hardButton.setText(hardString);
        hardButton.addActionListener(this::setButton);

        String menuString = "SELECT DIFFICULTY";
        menuLabel.setText(menuString);

        // Set layout
        setLayout();
    }

    // Public Methods

    /**
     * @return the game difficulty setting selected by the user
     */
    public GameDifficulty gameDifficulty() {
        return this.difficulty;
    }

    // Private Methods
    /**
     * Handles the selection logic for the difficulty buttons. Ensures only one button
     * is selected at a time and updates the selected state based on the button clicked.
     *
     * @param e the ActionEvent triggered by clicking a radio button
     */
    private void setButton(ActionEvent e) {
        String command = e.getActionCommand();

        // Update difficulty based on the action command
        difficulty = switch (command) {
            case easyString -> GameDifficulty.EASY;
            case mediumString -> GameDifficulty.MEDIUM;
            case hardString -> GameDifficulty.HARD;
            default -> throw new IllegalArgumentException("Invalid difficulty");
        };

        // Update the button states
        easyButton.setSelected(command.equals(easyString));
        mediumButton.setSelected(command.equals(mediumString));
        hardButton.setSelected(command.equals(hardString));
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
                                        .addComponent(easyButton)
                                        .addComponent(mediumButton)
                                        .addComponent(hardButton)
                        )
        );

        // Define the vertical group
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(menuLabel)
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(easyButton)
                                        .addComponent(mediumButton)
                                        .addComponent(hardButton)
                        )
        );
    }
}
