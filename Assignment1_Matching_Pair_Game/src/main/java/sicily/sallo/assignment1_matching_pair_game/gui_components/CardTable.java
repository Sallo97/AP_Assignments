package sicily.sallo.assignment1_matching_pair_game.gui_components;

import sicily.sallo.assignment1_matching_pair_game.common_enums.GameDifficulty;
import sicily.sallo.assignment1_matching_pair_game.logic_components.card.Card;
import sicily.sallo.assignment1_matching_pair_game.logic_components.card.OLDCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * TODO ADD BETTER DESCRIPTION
 * Represents a Panel of n cards
 */
public class CardTable extends JPanel{
    // Properties
    static final int maxCards = GameDifficulty.HARD.getNumOfCards();
    ArrayList<Card> deck = new ArrayList<>();
    GameDifficulty difficulty;
    int nCards = maxCards;

    /**
     * TODO Add better description
     * Creates an array of 32 Cards (usually the maximum)
     */
    public CardTable(){
        update(maxCards);
    }
    // Constructors
    /**
     * TODO Add better description
     */
    public CardTable(int nCards){
        update(nCards);

    }

    // Private methods
    public void update(int newCards){
        this.nCards = newCards;
        deck.ensureCapacity(nCards);

        // Add missing buttons
        for (int i = deck.size(); i < newCards; i++) {
            Card card = new Card();
            deck.add(card);
            add(card);
        }

        // Remove excess buttons
        while (deck.size() > newCards) {
            Card card = deck.get(deck.size() - 1);
            remove(card);
            deck.remove(card);
        }

        // Set Layout
        setLayout();
    }

    public void update(int newCards, int[] newVal){
        update(newCards);

        // Set values of the cards
        for (int i = 0; i < newCards; i++) {
            deck.get(i).setValue(newVal[i]);
        }
    }

    private void setLayout(){
        // Get the number of rows and columns
        int rows = Math.min(nCards/4, 4);
        int cols = (int) Math.ceil((double) nCards / rows);

        // Create the layout
        this.setLayout(new GridLayout(rows,cols));
        this.revalidate();
        this.repaint();
    }
}
