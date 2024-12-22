package sicily.sallo.assignment1_matching_pair_game.logic_components.buttons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;

/**
 * TODO Add Better description
 */
public class Shuffle extends JButton {

    /*
     * TODO Add better description
     */
    public Shuffle () {
        setSize(50, 50);
        setText("Shuffle Deck (Start New Game)");

        // Set Mouse Event when clicked
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt){
                setText("Shuffle Deck (Restart Game)");
                fireActionPerformed(new ActionEvent(this, 0, "shuffle"));
            }
        });
    }

     // Private Methods

    /**
     * TODO Add better description
     * random sampling of a deck of n pairs
     * @param nPairs
     * @return
     */
    public int[] randomDeck(int nPairs) {
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
