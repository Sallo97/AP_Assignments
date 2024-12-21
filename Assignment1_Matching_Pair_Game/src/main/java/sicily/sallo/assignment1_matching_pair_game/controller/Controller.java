package sicily.sallo.assignment1_matching_pair_game.controller;

import sicily.sallo.assignment1_matching_pair_game.game_logic.card.Card;
import sicily.sallo.assignment1_matching_pair_game.game_logic.card.CardState;

import javax.swing.*;
import java.beans.*;
import java.io.Serializable;
import java.util.Objects;

// Classes
public class Controller extends JLabel implements Serializable, PropertyChangeListener, VetoableChangeListener {
    // Properties
    private int matchedPairs; // number of matched pairs during a game
    private Pair pair; // represents the current pair of cards considered

    // Constructors
    /**
     * Default constructor, initializes `matchedPairs` at 0
     */
    public Controller() {
        super(); //TODO VEDI SE PUOI LEVARLO

        // Set property
        matchedPairs = 0;
        pair = new Pair();
    }

    // Public Methods

    /**
     * TODO Add better description
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CardState state = (CardState) evt.getNewValue();
        if(Objects.requireNonNull(state) == CardState.FACE_UP) {
            // Sets the new value to the pair (TODO FIND MORE ELEGANT SOLUTION)
            Card c = (Card)evt.getSource();
            pair.addValue(c.getValue());
            // If the pair is full check if their values matches
            if(pair.isPairFull()){
                checkMatch();
            }
        }
    }

    /**
     * TODO ADD BETTER DESCRIPTION
     * @param evt a {@code PropertyChangeEvent} object describing the
     *                event source and the property that has changed.
     * @throws PropertyVetoException
     */
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        CardState state = (CardState) evt.getNewValue();
        if(Objects.requireNonNull(state) == CardState.EXCLUDED) {
            throw new PropertyVetoException("An EXCLUDED Card cannot change its state", evt);
        }
        else if (Objects.requireNonNull(state) == CardState.FACE_UP && !pair.isPairNone()) {
            // If the pair is reset (both NONE) then it can change, otherwise no
            throw new PropertyVetoException("Only when the pair as been checked the card con change its state", evt);
        }
        else if (pair.isPairFull()) {
            //If there are already two Cards FACE_UP it cannot be this also
            throw new PropertyVetoException("There are already two Cards in the state FACE_UP", evt);
        }
    }

    // Private Methods

    /**
     * TODO ADD BETTER DESCRIPTION
     */
    private void checkMatch() {
        CardState newState = CardState.FACE_DOWN;

        // Wait for Half a Second
        try{
            Thread.sleep(500);
        } catch (InterruptedException e) { /*TODO*/ }

        // Check if the pair match
        if(pair.areEqual()) {
            matchedPairs++;
            newState = CardState.EXCLUDED;
        }

        // Reset the pair
        pair.reset();

        // Set state of the cards to newState
        this.firePropertyChange("state", CardState.FACE_UP, newState);
    }


}
