package sicily.sallo.assignment1_matching_pair_game.logic_components.card;

import sicily.sallo.assignment1_matching_pair_game.common_enums.GameDifficulty;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.util.Objects;

/**
 * Represents a card in the game. It is implemented as an extension of JButton.
 * Key Features:
 * - The card's appearance dynamically updates based on its state, including
 *   background color and displayed text.
 * - Supports property change listeners (`PropertyChangeSupport`) to notify
 *   observers when the card's value or state changes.
 * - Includes vetoable change listeners (`VetoableChangeSupport`) to enforce
 *   validation or constraints before allowing state or value modifications.
 * Additionally, the card has a default size (`width` and `height`) to ensure
 * consistent presentation in the game interface.
 *
 * @author Salvatore Salerno
 */
public class Card extends JButton {
    // Properties
    private int value = 0; // (bound + constrained)
    private CardState state; // (bound + constrained)

    // Constructors
    /**
     * Default constructor for creating a new Card instance.
     * This constructor is primarily defined to support the Serializable interface.
     * The card is initialized in the FACE_DOWN state, its appearance is updated,
     * and a mouse click event listener is attached to handle user interactions.
     */
    public Card() {
        // At the start a Card is always FACE_DOWN
        this.state = CardState.FACE_DOWN;

        // Set Mouse Event when clicked
        this.addActionListener(e -> cardClicked());

        // Set graphic of the card
        changeAppearance();
    }

    /**
     * Creates a new Card instance with the specified value and game difficulty.
     * The card is initially set to the FACE_DOWN state and its appearance is updated accordingly.
     * The size of the card is dynamically adjusted based on the provided game difficulty.
     * Additionally, a mouse click event listener is attached to handle user interactions.
     *
     * @param value      the numeric value assigned to the card
     */
    public Card(int value) {
        // Call default constructor
        this();

        // set `value` accordingly
        this.value = value;
    }

    // Public Methods



    /**
     * @return the value of the card.
     */
    public int getValue() {
        return value;
    }

    /**
     * Updates the card's value to the specified new value, provided all registered
     * vetoable change listeners approve the change.
     *
     * @param newVal: the new value to set for the card.
     *
     */
    public void setValue(int newVal) {
        int oldVal = value;
        try{
            this.fireVetoableChange("value", oldVal, newVal);
            value = newVal;
            this.firePropertyChange("value", oldVal, newVal);
        } catch (PropertyVetoException e) { /*TODO*/}
    }

    /**
     * Updates the card's state to the specified new state, modifying its
     * background color accordingly. The change is subject to approval by all
     * registered vetoable listeners.
     *
     * @param newState: the new state to set for the card.
     */
    public void setState(CardState newState)  {
        CardState oldState = state;
        try{
            this.fireVetoableChange("state", oldState, newState);
            state = newState;
            this.changeAppearance();
            this.firePropertyChange("state", oldState, newState);
        } catch(PropertyVetoException e){
            System.err.println("Cannot change state!");
        }
    }

    // Private Methods
    /**
     * Updates the card's appearance based on its current state.
     * Adjusts the background color and the text displayed on the card.
     * Depending on the state of the card does the following:
     * - `FACE_UP` -> the card's numeric value is displayed as text.
     * - `FACE_DOWN` or `EXCLUDED` -> the text is hidden (displayed as "?").
     */
    private void changeAppearance() {
        // Set Background Color
        this.setBackground(state.getColor());
        this.setOpaque(true);
        this.setContentAreaFilled(true);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set Text
        if (Objects.requireNonNull(state) == CardState.FACE_UP) {
            this.setText(String.valueOf(this.value));
        } else {
            // Both EXCLUDED and FACE_DOWN have the value hidden
            this.setText("?");
        }
    }

    /**
     * Handles the logic triggered when the card is clicked by the user.
     * Depending on the state of the card does the following:
     * - `FACE_DOWN` -> attempts to change its state to `FACE_UP`.
     * - `FACE_UP` or `EXCLUDED` -> it  attempts to change its state to `FACE_DOWN`.
     */
    private void cardClicked() {
        CardState newState;
        if (Objects.requireNonNull(state) == CardState.FACE_DOWN) {
            newState = CardState.FACE_UP;
        } else {
            newState = CardState.FACE_DOWN;
        }
        this.setState(newState);
    }
}
