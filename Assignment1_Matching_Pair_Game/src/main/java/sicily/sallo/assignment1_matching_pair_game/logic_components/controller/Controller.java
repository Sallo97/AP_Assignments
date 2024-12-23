package sicily.sallo.assignment1_matching_pair_game.logic_components.controller;

import sicily.sallo.assignment1_matching_pair_game.logic_components.card.Card;
import sicily.sallo.assignment1_matching_pair_game.logic_components.card.OLDCard;
import sicily.sallo.assignment1_matching_pair_game.logic_components.card.CardState;

import javax.swing.*;
import java.beans.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Controller handles the main game logic.
 * It listen to all Cards and when a Card flips FACE_UP, it records its value.
 * At most two cards can be FACE_UP at the same time.
 * When the second Card is FACE_UP, The Controller checks if the two matches.
 * If so tells that Card instances to become EXCLUDED, otherwise return FACE_DOWN
 * The Controller is a VetoableChangelistener to each Card. It forbids the change of state
 * if it is triggered by clicking on the card.
 * It also shows the number of pairs found so far by the player
 */
public class Controller extends JLabel implements Serializable, PropertyChangeListener, VetoableChangeListener {
    // Properties
    private int matchedPairs; // number of matched pairs during a game
    private Pair pair; // represents the current pair of cards considered

    // Constructors
    /**
     * Default constructor, initializes `matchedPairs` at 0
     */
    public Controller() {
        this.reset();
    }

    // Public Methods
    /**
     * TODO Add better description
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

//        String propertyName  = evt.getPropertyName();
//        if(propertyName.equals("state") &&
//                (CardState)evt.getNewValue() == CardState.FACE_UP) {
//            // Sets the new value to the pair (TODO FIND MORE ELEGANT SOLUTION)
//            CardState state = (CardState) evt.getNewValue();
//            OLDCard c = (OLDCard)evt.getSource();
//            pair.addValue(c.getValue());
//            // If the pair is full check if their values matches
//            if(pair.isPairFull()){
//                checkMatch();
//            }
//        } else if(propertyName.equals("reset")){
//            this.reset();
//        }
    }

    /**
     * The Controller forbids cards to change their state if they are FACE_UP
     * and have been clicked again (so they cannot turn FACE_DOWN)
     * @param evt a {@code PropertyChangeEvent} object describing the
     *                event source and the property that has changed.
     * @throws PropertyVetoException
     */
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        System.out.println("Inside vetoableChange with command " + evt.getPropertyName());
        String property = evt.getPropertyName();

        switch (property) {
            case "value":
                break;
            case "state":
                CardState oldState = (CardState) evt.getOldValue();
                CardState newState = (CardState) evt.getNewValue();
                System.out.println("Inside state case with oldValue " + oldState + " and newValue " + newState);

                // A Card cannot change its state if its EXCLUDED
                if(oldState == CardState.EXCLUDED) {
                    throw new PropertyVetoException("An EXCLUDED Card cannot change its state", evt);
                }
                // A Card cannot change its state if its FACE_UP and the Pair is *NOT* NONE
                else if (oldState == CardState.FACE_UP && !pair.isPairNone()) {
                    throw new PropertyVetoException("Only when the pair as been checked the card con change its state", evt);
                }
                // If a card with state FACE_DOWN want to become FACE_UP but there are already to cards
                // FACE_UP it cannot change its state
                else if (newState == CardState.FACE_UP && pair.isPairFull()) {
                    throw new PropertyVetoException("There are already two Cards in the state FACE_UP", evt);
                }
                // If there are no problem we get the card's value
                else {
                    Card card = (Card) evt.getSource();
                    pair.addValue(card.getValue());
                }
                break;
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
            this.setMatchedPairs(matchedPairs++);
            newState = CardState.EXCLUDED;
        }

        // Reset the pair
        pair.reset();

        // Set state of the cards to newState
        this.firePropertyChange("state", CardState.FACE_UP, newState);
    }

    /**
     * Set the new Value and change the value displayed
     * @param newVal = the new value
     */
    private void setMatchedPairs(int newVal) {
        this.matchedPairs = newVal;
        this.setText("PAIRS FOUND = " + this.matchedPairs);
    }

    /**
     * Resets the class
     */
    private void reset() {
        // Set property
        this.setMatchedPairs(0);
        pair = new Pair();
    }

}
