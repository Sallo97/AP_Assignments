package matchingPairGame.logic_components.counter;

import matchingPairGame.logic_components.card.CardState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Display and update the total number of times in a game the cards have been turned
 * face up in a matching pair game.
 */
public class Counter extends JLabel implements Serializable, ActionListener, PropertyChangeListener {
    // Properties
    private final ArrayList<Integer> moves = new ArrayList<>(); // Total number of flips done
    private int numOfPlayers = 1;
    private int currentPlayer = 0;
    private int turn = 0;
    private Timer timerNextPlayer;
    boolean end = false;
    // Constructor
    /**
     * Initializes a new Counter instance with the counter set to 0.
     * Displays the counter value on the JLabel.
     */
    public Counter() {
        timerNextPlayer = new Timer(500, e -> setNextPlayer());
        timerNextPlayer.setRepeats(false);

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
        String propertyName = evt.getPropertyName();

        switch (propertyName) {
            case "state":
                if (evt.getNewValue() == CardState.FACE_UP) {
                    moves.set(currentPlayer, moves.get(currentPlayer) + 1);
                    if (turn == 1) {
                        setText();
                        timerNextPlayer.start();
                        turn = 0;
                    } else {
                        setText();
                        turn++;
                    }
                }
                break;

            case "reset":
                this.reset();
                break;

            case "numOfPlayer":
                this.numOfPlayers = (Integer) evt.getNewValue();
                this.reset();
                break;

            case "ended":
                // set Text appropriately
                end = true;
                setText();
                break;

            case "moves":
                firePropertyChange("moves", null, moves);
                break;
        }
    }

    // Private Methods

    /**
     * TODO ADD BETTER DESCRIPTION
     */
    private void setNextPlayer(){
        currentPlayer = (currentPlayer + 1) % numOfPlayers;
        setText();
    }

    /**
     * Reset the counter to 0
     */
    private void reset() {
        // Initialize elements
        for (int i = 0; i < numOfPlayers; i++) {
            if (i >= moves.size()) {
                moves.add(0); // Add a new score of 0 for players not yet in the list
            } else {
                moves.set(i, 0); // Set the score to 0 for existing players
            }
        }

        // Set current player to 0
        currentPlayer = 0;
        turn = 0;
        end = false;
        setText();
    }

    private void setText() {
        if (end){
            setText("RE-MATCH BY PRESSING SHUFFLE | GO BACK TO MENU BY PRESSING EXIT");
        } else {
            this.setText("COUNTER = " + moves.get(currentPlayer));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "shuffle":
                this.reset();
                break;
        }
    }


}
