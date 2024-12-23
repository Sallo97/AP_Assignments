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

    //TODO Base constructor adds 32Cards
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

        System.out.println(deck.size());
        // Add missing buttons if there are fewer than newCards
        for (int i = deck.size(); i < newCards; i++) {
            Card card = new Card();
            deck.add(card);
        }

        // Remove excess buttons if there are more than newCards
        while (deck.size() > newCards) {
            deck.remove(deck.size() - 1);
        }

        setLayout();
    }

    private void setLayout(){
        // set the exact number of buttons
        this.removeAll();
        for (int i = 0; i < nCards; i++){
            add(deck.get(i));
        }

        // Get the number of rows and columns
        int rows = Math.min(nCards/4, 4);
        int cols = (int) Math.ceil((double) nCards / rows);

        // Create the layout
        this.setLayout(new GridLayout(rows,cols));
        this.revalidate();
        this.repaint();
    }


}
