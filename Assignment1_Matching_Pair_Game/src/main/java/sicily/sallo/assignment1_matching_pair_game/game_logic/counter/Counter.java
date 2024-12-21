package sicily.sallo.assignment1_matching_pair_game.game_logic.counter;

import sicily.sallo.assignment1_matching_pair_game.game_logic.card.CardState;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

/**
 * Display and update the total number of times in a game the cards have been turned
 * face up in a matching pair game.
 */
public class Counter extends JLabel implements Serializable, PropertyChangeListener {
    // Properties
    private int totFlips; // Total number of flips done

    // Constructor
    /**
     * Initializes a new Counter instance with the counter set to 0.
     * Displays the counter value on the JLabel.
     */
    public Counter() {
        this.reset();
    }

    // Public Methods

    /**
     * Listens for property change events and increments the counter
     * whenever the "state" property of a card changes to FACE_UP.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Check if the property is a state and is FACE_UP
        if (evt.getPropertyName().equals("state") &&
                evt.getNewValue() == CardState.FACE_UP) {
            setCounter(this.totFlips++);
        }
    }


    // Private Methods

    /**
     * Updates the counter value and refreshes the text displayed on the JLabel.
     *
     * @param newVal The new counter value to set.
     */
    private void setCounter(int newVal) {
        totFlips = newVal;
        setText("Counter:" + totFlips);
    }

    /**
     * Reset the counter to 0
     */
    private void reset() {
        setCounter(0);
    }
}
