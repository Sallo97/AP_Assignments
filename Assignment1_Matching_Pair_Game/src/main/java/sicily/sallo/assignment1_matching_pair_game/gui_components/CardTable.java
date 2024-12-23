package sicily.sallo.assignment1_matching_pair_game.gui_components;

import sicily.sallo.assignment1_matching_pair_game.common_enums.GameDifficulty;
import sicily.sallo.assignment1_matching_pair_game.logic_components.card.Card;
import sicily.sallo.assignment1_matching_pair_game.logic_components.controller.Controller;
import sicily.sallo.assignment1_matching_pair_game.logic_components.counter.Counter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * CardTable is a JPanel that represents a table of playing cards for a game.
 * It dynamically manages the layout and the number of cards displayed based on
 * the game's current difficulty setting.
 */
public class CardTable extends JPanel{
    // Properties
    static final int maxCards = GameDifficulty.HARD.getNumOfCards();
    int nCards = maxCards;
    ArrayList<Card> deck = new ArrayList<>();

    // Constructors
    /**
     * Default constructor that initializes the table with the default
     * number of cards (`maxCards`).
     */
    public CardTable(Controller controller, Counter counter){
        update(controller, counter, nCards);
    }

    // Private methods
    /**
     * Updates the table showing the requested number of cards.
     * @param newCards The new number of cards to display on the table.
     */
    public void update(Controller controller, Counter counter, int newCards){
        this.nCards = newCards;
        deck.ensureCapacity(nCards);

        // Add missing buttons
        for (int i = deck.size(); i < newCards; i++) {
            //TODO Let Controller be a Listener to all cards
            Card card = new Card();
            card.addVetoableChangeListener(controller);
            card.addPropertyChangeListener(controller);
            card.addPropertyChangeListener(counter);
            controller.addPropertyChangeListener(card);
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

    /**
     * Updates the table to show the specified number of cards.
     * Additionally, sets the values of each card according to the array `newVal`.
     * @param newCards The new number of cards to display.
     * @param newVal An array of integers representing the values for each card.
     */
    public void update(Controller controller, Counter counter, int newCards, int[] newVal){
        update(controller, counter, newCards);

        // Set values of the cards
        for (int i = 0; i < newCards; i++) {
            deck.get(i).setValue(newVal[i]);
        }
    }

    /**
     * Sets the layout of the CardTable based on the current number of cards.
     * The cards are arranged in a grid with a dynamic number of rows and columns.
     */
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
