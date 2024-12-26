package matchingPairGame.gui_components;

import matchingPairGame.logic_components.card.Card;
import matchingPairGame.common_enums.GameDifficulty;
import matchingPairGame.logic_components.card.CardState;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * CardTable is a JPanel that represents a table of playing cards for a game.
 * It dynamically manages the layout and the number of cards displayed based on
 * the game's current difficulty setting.
 * It contains also the Counter and Controller
 */
public class CardTable extends JPanel{
    // Properties
    static final int maxCards = GameDifficulty.HARD.getNumOfCards();
    int nCards = maxCards;
    ArrayList<Card> deck = new ArrayList<>();
    public InformationTab infoTab = new InformationTab();
    JPanel cardsPanel = new JPanel();

    // Constructors
    /**
     * Default constructor that initializes the table with the default
     * number of cards (`maxCards`).
     */
    public CardTable(){
        update(nCards);
    }

    // Private methods
    /**
     * Updates the table showing the requested number of cards.
     * @param newCards The new number of cards to display on the table.
     */
    public void update(int newCards){
        this.nCards = newCards;
        deck.ensureCapacity(nCards);

        // Add missing buttons
        for (int i = deck.size(); i < newCards; i++) {
            Card card = new Card();
            card.addVetoableChangeListener(infoTab.controller);
            card.addPropertyChangeListener(infoTab.controller);
            card.addPropertyChangeListener(infoTab.counter);
            infoTab.controller.addPropertyChangeListener(card);
            deck.add(card);
            cardsPanel.add(card);
        }

        // Remove excess buttons
        while (deck.size() > newCards) {
            Card card = deck.get(deck.size() - 1);
            cardsPanel.remove(card);
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
    public void update(int newCards, int[] newVal){
        update(newCards);

        // Set values of the cards
        for (int i = 0; i < newCards; i++) {
            deck.get(i).setState(CardState.FACE_DOWN);
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
        cardsPanel.setLayout(new GridLayout(rows, cols));
        this.setLayout(new BorderLayout());
        this.add(infoTab, BorderLayout.NORTH);
        this.add(cardsPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}
