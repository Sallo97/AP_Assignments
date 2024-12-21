package sicily.sallo.assignment1_matching_pair_game.game_logic.shuffle;

import sicily.sallo.assignment1_matching_pair_game.game_logic.GameDifficulty;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.*;

/**
 * TODO Add Better description
 */
public class Shuffle extends JButton implements Serializable, PropertyChangeListener {
    // Properties
    public Optional<GameDifficulty> difficulty;
    public OptionalInt numOfPlayers;

    /*
     * TODO Add better description
     */
    public Shuffle () {
        difficulty = Optional.empty();
        numOfPlayers = OptionalInt.empty();

        // Set Mouse Event when clicked
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt){
                buttonClicked();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Check if its the difficulty or the numOfPlayers
        switch(evt.getPropertyName()) {
            case "difficulty":
                setDifficulty((GameDifficulty) evt.getNewValue());
                break;

            case "numOfPlayers":
                setNumOfPlayers((int) evt.getNewValue());
        }
    }

    public void setDifficulty(GameDifficulty newDifficulty){
        this.difficulty = Optional.ofNullable(newDifficulty);
    }

    public void setNumOfPlayers(int newNumOfPlayers){
        numOfPlayers = OptionalInt.of(newNumOfPlayers);
    }

    /**
     * TODO Add better description
     */
     public void buttonClicked () {
         // Create the array of the values for the cards
         int [] deck = this.randomDeck(numOfPlayers.getAsInt()); //TODO Add check to numOfPlayers

         // Send an event "reset" with as value the array of the new values for the Cards
         this.firePropertyChange("reset", null, deck);
     }

     // Private Methods

    /**
     * TODO Add better description
     * random sampling of a deck of n pairs
     * @param nPairs
     * @return
     */
    private int[] randomDeck(int nPairs) {
        int nPos = nPairs / 2;
        int[] deck = new int[nPos];
        // Construct the random Deck
        Random rand = new Random();
        for (int i = 0; i < nPos; i++) {
            // Get a random position
            int j = rand.nextInt(0, nPairs);
            // Get the logical value associated
            int iVal = i/2 + 1;
            int jVal = j/2 + 1;
            deck[i] = iVal;
            deck[j] = jVal;
        }
        return deck;
    }

}
