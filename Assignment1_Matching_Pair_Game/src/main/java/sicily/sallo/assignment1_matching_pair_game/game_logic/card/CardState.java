/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sicily.sallo.assignment1_matching_pair_game.game_logic.card;

import java.awt.Color;

/**
 * Defines the various states a Card can have during gameplay, along with the 
 * color associated with each state.
 * The possible states are:
 * - EXCLUDED: The card has been claimed by a player and is no longer available 
 *             in the game.
 * - FACE_DOWN: The card is unselected and its value remains hidden from the 
 *              player.
 * - FACE_UP: The card is selected and its value is revealed to the player.
 * @author Salvatore Salerno
 */
public enum CardState {
    EXCLUDED(Color.RED), 
    FACE_DOWN(Color.GREEN), 
    FACE_UP(Color.WHITE); 

    private final Color color;

    /**
     * Constructs a CardState with an associated background color:
     */
    CardState(Color color) {
        this.color = color;
    }
    /**
     * @return the color associated to the state
     */
    public Color getColor() {
        return this.color;
    }
}
