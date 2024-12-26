package sicily.sallo.assignment1_matching_pair_game.logic_components.controller;

import sicily.sallo.assignment1_matching_pair_game.common_enums.GameDifficulty;
import sicily.sallo.assignment1_matching_pair_game.common_enums.GameState;
import sicily.sallo.assignment1_matching_pair_game.gui_components.ScoreBoard;
import sicily.sallo.assignment1_matching_pair_game.logic_components.card.Card;
import sicily.sallo.assignment1_matching_pair_game.logic_components.card.CardState;
import javax.swing.*;
import java.beans.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

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
    // PROPERTIES
    // Multiplayer Logic
    private ArrayList<Integer> scores =  new ArrayList<>(); // number of matched pairs during a game
    private int numPlayers = 1;
    private int currentPlayer = 0;

    // Game Logic
    GameDifficulty currentDifficulty = GameDifficulty.EASY;
    int foundPairs = 0;
    private Pair pair; // represents the current pair of cards considered
    GameState gameState = GameState.IN_GAME;

    // Timers
    private final Timer matchTimer;
    private final Timer nextPlayerTimer;
    int time = 500; // in milliseconds

    // Constructors
    /**
     * Default constructor.
     * Initializes the `Controller` and sets up the game state:
     * - Resets the matched pairs count to 0.
     * - Configures a timer to delay the match-checking logic by 5 seconds.
     */
    public Controller() {
        matchTimer = new Timer(time, e -> checkMatch());
        matchTimer.setRepeats(false);

        nextPlayerTimer = new Timer(time, e -> setNextPlayer());
        nextPlayerTimer.setRepeats(false);
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
        String propertyName = evt.getPropertyName();

        switch (propertyName) {
            case "state":
                if (evt.getNewValue() == CardState.FACE_UP) {
                    Card card = (Card) evt.getSource();
                    pair.addValue(card.getValue());
                    // If the pair is full, check if their values match
                    if (pair.isPairFull()) {
                        // Calls checkMatch after half a second
                        matchTimer.start();
                    }
                }
                break;

            case "board":
                this.gameState = (GameState) evt.getNewValue();
                if (gameState == GameState.IN_GAME) {
                    this.reset();
                }
                break;

            case "difficulty":
                this.currentDifficulty = (GameDifficulty) evt.getNewValue();
                break;

            case "numOfPlayer":
                this.numPlayers = (Integer) evt.getNewValue();
                break;

            case "winner":
                ArrayList<Integer> winners = (ArrayList<Integer>) evt.getNewValue();
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < winners.size(); i++) {
                    result.append(winners.get(i));
                    if (i < winners.size() - 1) { // Add a space unless it's the last element
                        result.append(" ");
                    }
                }
                printWinner(result.toString());
                break;

            case "moves":
                endingGame((ArrayList<Integer>) evt.getNewValue());
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
            scores.set(currentPlayer, scores.get(currentPlayer) + 1);
            newState = CardState.EXCLUDED;
            foundPairs++;
            updateText();
        }

        // Reset the pair
        pair.reset();

        // Set state of the cards to newState
        this.firePropertyChange("state", CardState.FACE_UP, newState);

        if (foundPairs != this.currentDifficulty.getNumOfPairs()){
            // Set next player
            nextPlayerTimer.start();
        } else {
            firePropertyChange("moves", null, null);
        }
    }

    private void endingGame(ArrayList<Integer> moves){

        // Winner logic
        findWinner();

        // For each player get its score and moves and pass it to ScoreBoard
        for (int i = 0; i < numPlayers; i++) {
            System.out.println(moves.get(i) + " " + scores.get(i));
            firePropertyChange("rank", moves.get(i) , scores.get(i));
        }
    }

    /**
     * Determines the winner of the game based on the scores and (if necessary) the number of moves.
     * If a single player has the highest score, that player is declared the winner.
     * In case of a tie, the method finds the player(s) with the minimum number of moves.
     * Only if we found more than one player with the same maximum score and minimum moves, then it is a tie.
     */
    private void findWinner() {
        // Find the player(s) with the maximum score
        ArrayList<Integer> winners = new ArrayList<>();
        int max = this.scores.get(0);

        for (int i = 0; i < this.scores.size(); i++) {
            int current = scores.get(i);
            if (current > max) {
                max = current; // Update max value
                winners.clear(); // Clear previous indices
                winners.add(i); // Add the current index
            } else if (current == max) {
                winners.add(i); // Add the index of the duplicate max value
            }
        }

        // Tell Counter that the game ended
        firePropertyChange("ended", null, null);

        if (winners.size() > 1){
            // If there is a TIE call Counter to get who has the minimum number of moves
            firePropertyChange("findWinner", null, winners);
        } else {
            // Print the Winner (The Winner is the one at position 0 in indices)
            this.setText("PLAYER " + (winners.get(0) + 1) + " WON! | ");
        }
    }

    /**
     * Update the label's text showing the winner(s)
     */
    private void printWinner(String winnerName){
        this.setText("PLAYER(s) " + winnerName + " WON! | ");
    }

    /**
     * Update the players turn and shows its score
     */
    private void setNextPlayer(){
        currentPlayer = (currentPlayer + 1) % numPlayers;
        updateText();
    }

    /**
     * Resets the game state to its initial configuration:
     * - Sets the matched pairs count to 0.
     * - Clears the current pair being evaluated.
     */
    private void reset() {
        // Initialize elements
        for (int i = 0; i < numPlayers; i++) {
            if (i >= scores.size()) {
                scores.add(0); // Add a new score of 0 for players not yet in the list
            } else {
                scores.set(i, 0); // Set the score to 0 for existing players
            }
        }

        System.out.println("INITIALIZED score array with size = " + scores.size());

        // Set current player to 0
        currentPlayer = 0;
        foundPairs = 0;
        pair = new Pair();
        updateText();
    }

    private void updateText() {
        this.setText("PLAYER'S "+ (currentPlayer + 1) + " TURN | PAIRS FOUND:" + scores.get(currentPlayer) + " | ");
    }

}
