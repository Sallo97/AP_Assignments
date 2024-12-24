package sicily.sallo.assignment1_matching_pair_game.logic_components.controller;

import sicily.sallo.assignment1_matching_pair_game.common_enums.GameState;
import sicily.sallo.assignment1_matching_pair_game.logic_components.card.Card;
import sicily.sallo.assignment1_matching_pair_game.logic_components.card.CardState;
import javax.swing.*;
import java.beans.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The `Controller` class manages the core game logic for a game.
 * It listens to changes all active `Card` objects and enforces the following rules:
 * - At most two cards can be face-up (`FACE_UP`) simultaneously.
 * - Cards that are excluded (`EXCLUDED`) cannot change state unless explicitly shuffled.
 * - The game checks for matches when exactly two cards are face-up, applying appropriate transitions:
 *   - If the cards match, they are marked as excluded (`EXCLUDED`).
 *   - If the cards do not match, they are flipped back to face-down (`FACE_DOWN`).
 */
public class Controller extends JLabel implements Serializable, PropertyChangeListener, VetoableChangeListener {
    // Properties
    private ArrayList<Integer> matchedPairs =  new ArrayList<>(); // number of matched pairs during a game
    private int numPlayers = 1;
    private int currentPlayer = 0;
    private Pair pair; // represents the current pair of cards considered
    private Timer timer;
    int time = 500; // in milliseconds
    GameState gameState = GameState.IN_GAME;

    // Constructors
    /**
     * Default constructor.
     * Initializes the `Controller` and sets up the game state:
     * - Resets the matched pairs count to 0.
     * - Configures a timer to delay the match-checking logic by 5 seconds.
     */
    public Controller() {
        timer = new Timer(time, e -> checkMatch());
        timer.setRepeats(false);
        this.reset();
    }

    // Public Methods
    /**
     * Observes property changes in `Card` objects:
     * - When a card's state changes to `FACE_UP`, it records the card's value in the `pair`.
     * - If two cards are face-up, calls the `checkMatch` function after 5 milliseconds.
     *
     * @param evt A `PropertyChangeEvent` describing the event source and the changed property.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName  = evt.getPropertyName();
        // If a Card has flipped to FACE_UP, then save its value in the pair
        if(propertyName.equals("state") &&
                evt.getNewValue() == CardState.FACE_UP) {
            Card card = (Card) evt.getSource();
            pair.addValue(card.getValue());
            // If the pair is full check if their values matches
            if(pair.isPairFull()){
                // Calls checkMatch after half a second
                timer.start();
            }
        }
        else if(propertyName.equals("board")){
            this.gameState = (GameState) evt.getNewValue();
            this.reset();
        }
        else if(propertyName.equals("numOfPlayer")){
            this.numPlayers = (Integer) evt.getNewValue();
        }

    }

    /**
     * Vetoes invalid state changes for `Card` objects.
     * Rules:
     * - Cards in `EXCLUDED` state cannot change state unless explicitly shuffled.
     * - Cards in `FACE_UP` state cannot change state if the pair is not yet resolved.
     * - Cards in`FACE_DOWN` state cannot flip to `FACE_UP` if two cards are already face-up.
     *
     * @param evt A `PropertyChangeEvent` describing the attempted state transition.
     * @throws PropertyVetoException if the attempted state change violates game rules.
     */
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        String property = evt.getPropertyName();

        switch (property) {
            case "value":
                break;
            case "state":
                CardState oldState = (CardState) evt.getOldValue();
                CardState newState = (CardState) evt.getNewValue();

                if(gameState == GameState.IN_GAME) {
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
                }
                break;
        }
    }

    // Private Methods
    /**
     * Checks if the two face-up cards match.
     * - If the cards match, their state changes to `EXCLUDED`.
     * - If the cards do not match, their state reverts to `FACE_DOWN`.
     * The pair is then reset for the next round.
     */
    private void checkMatch() {
        CardState newState = CardState.FACE_DOWN;

        // Check if the pair match
        if(pair.areEqual()) {
            // Update current player score
            matchedPairs.set(currentPlayer, matchedPairs.get(currentPlayer) + 1);
            newState = CardState.EXCLUDED;
            setText();
        }

        // Reset the pair
        pair.reset();

        // Set state of the cards to newState
        this.firePropertyChange("state", CardState.FACE_UP, newState);

        // Set next player
        setNextPlayer();
    }

    /**
     * Update the players turn and shows its score
     */
    private void setNextPlayer(){
        currentPlayer = (currentPlayer + 1) % numPlayers;
        setText();
    }


    /**
     * Resets the game state to its initial configuration:
     * - Sets the matched pairs count to 0.
     * - Clears the current pair being evaluated.
     */
    private void reset() {
        // Initialize elements
        int oldSize = matchedPairs.size();
        for (int i = matchedPairs.size(); i < numPlayers; i++){
            matchedPairs.add(i, 0);
        }

        // Set the score of all remaining players to 0
        for (int i = 0; i < numPlayers - oldSize; i++) {
            matchedPairs.set(i, 0);
        }

        // Set current player to 0
        currentPlayer = 0;
        pair = new Pair();
        setText();
    }

    private void setText() {
        this.setText("PLAYER'S "+ (currentPlayer + 1) + " TURN | PAIRS FOUND:" + matchedPairs.get(currentPlayer));
    }

}
