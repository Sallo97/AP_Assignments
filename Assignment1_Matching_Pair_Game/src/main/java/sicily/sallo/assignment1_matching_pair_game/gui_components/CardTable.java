package sicily.sallo.assignment1_matching_pair_game.gui_components;

import sicily.sallo.assignment1_matching_pair_game.common_enums.GameDifficulty;
import sicily.sallo.assignment1_matching_pair_game.logic_components.card.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * TODO ADD BETTER DESCRIPTION
 * Represents a Panel of n cards
 */
public class CardTable extends JPanel{
    // Properties
    ArrayList<JButton> deck;
    GameDifficulty difficulty;

    // Constructors
    /**
     * TODO Add better description
     */
    public CardTable(){
        deck = new ArrayList<>(8);
        // Create the 8 buttons
        for (int i = 0; i < 8; i++){
            JButton button = new JButton("CULO");
            deck.add(button);
            this.add(button);
        }

        // Create the layout
        this.setLayout(new GridLayout(2,4));
        this.revalidate();
        this.repaint();

    }


}
