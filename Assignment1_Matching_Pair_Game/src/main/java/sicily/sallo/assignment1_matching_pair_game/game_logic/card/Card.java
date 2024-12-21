/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sicily.sallo.assignment1_matching_pair_game.game_logic.card;

// Imports
import javax.swing.JButton;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a card in the game. It is implemented as an extension of JButton.
 * Key Features:
 * - The card's appearance dynamically updates based on its state, including 
 *   background color and displayed text.
 * - Supports property change listeners (`PropertyChangeSupport`) to notify 
 *   observers when the card's value or state changes.
 * - Includes vetoable change listeners (`VetoableChangeSupport`) to enforce 
 *   validation or constraints before allowing state or value modifications.
 * Additionally, the card has a default size (`width` and `height`) to ensure 
 * consistent presentation in the game interface.
 * 
 * @author Salvatore Salerno
 */
public class Card extends JButton implements Serializable {

    // Properties
    private int width; private int height;
    private int value; // (bound + constrained)
    private CardState state; // (bound + contrained)
    private final PropertyChangeSupport mPcs = new PropertyChangeSupport(this);
    private final VetoableChangeSupport mVcs = new VetoableChangeSupport(this);

    // Constructors
    /**
     * Default constructor for creating a new Card instance.
     * This constructor is primarily defined to support the Serializable interface.
     * The card is initialized in the FACE_DOWN state, its appearance is updated,
     * and a mouse click event listener is attached to handle user interactions.
     */
    public Card() {
        super();

        // Set property
        this.state = CardState.FACE_DOWN;
        this.changeAppearance();

        // Set Mouse Event when clicked
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt){
                cardClicked();
            }
        });
    }

    /**
     * Creates a new Card instance with the specified value and game difficulty.
     * The card is initially set to the FACE_DOWN state and its appearance is updated accordingly.
     * The size of the card is dynamically adjusted based on the provided game difficulty.
     * Additionally, a mouse click event listener is attached to handle user interactions.
     *
     * @param value      the numeric value assigned to the card
     * @param difficulty the game difficulty level, which determines the card's size
     */
    public Card(int value, GameDifficulty difficulty) {
        super();
        
        // Set property
        this.value = value;
        this.state = CardState.FACE_DOWN;
        this.changeAppearance();

        //Set the size dynamically depending on the difficulty
        this.setSize(difficulty);
        
        // Set Mouse Event when clicked
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt){
                cardClicked();
            }
        });
    }
    
    // Public methods
    /**
     * @return the value of the card.
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Updates the card's value to the specified new value, provided all registered 
     * vetoable change listeners approve the change.
     *
     * @param newVal: the new value to set for the card.
     * 
     * @throws PropertyVetoException if any vetoable listener rejects the change.
     */
    public void setValue(int newVal) throws PropertyVetoException {
        int oldVal = value;
        mVcs.fireVetoableChange("value", oldVal, newVal);        
        value = newVal;
        mPcs.firePropertyChange("value", oldVal, newVal);
    }
    
    /**
     * @return the state of the card.
     */
    public CardState getState() {
        return state;
    }
    
    /**
     * Updates the card's state to the specified new state, modifying its 
     * background color accordingly. The change is subject to approval by all 
     * registered vetoable listeners.
     * 
     * @param newState: the new state to set for the card.
     * 
     * @throws PropertyVetoException if any vetoable listener rejects the state change.
     */
    public void setState(CardState newState) throws PropertyVetoException {
        CardState oldState = state;
        mVcs.fireVetoableChange("state", oldState, newState);
        state = newState;
        this.changeAppearance();
        mPcs.firePropertyChange("state", oldState, newState);

    }

    public int getWidth(){
        return width;
    }

    public void setWidth(int newWidth){
        width = newWidth;
    }

    public int getHeight(){
        return height;
    }

    public void setHeight(int newHeight){
        height = newHeight;
    }
    
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }
    
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }
    
    @Override
    public void addVetoableChangeListener(VetoableChangeListener listener) {
        mVcs.addVetoableChangeListener(listener);
    }
    
    @Override
    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        mVcs.removeVetoableChangeListener(listener);
    }

    // Private methods
    /**
     * Checks the GameDifficulty and set the size of the Card accordingly
     */
    private void setSize(GameDifficulty difficulty) {
        int newWidth = difficulty.getWidth();
        int newHeight = difficulty.getHeight();

        this.setSize(newWidth, newHeight);
    }

    /**
     * Updates the card's appearance based on its current state. 
     * Adjusts the background color and the text displayed on the card.
     * Depending on the state of the card does the following:
     * - `FACE_UP` -> the card's numeric value is displayed as text.
     * - `FACE_DOWN` or `EXCLUDED` -> the text is hidden (displayed as "?").
     */
    private void changeAppearance(){
        // Set background color
        this.setBackground(state.getColor());
        // Set the text of the card logic
        if (Objects.requireNonNull(state) == CardState.FACE_UP) {
            this.setText(String.valueOf(this.value));
        } else {// Both EXCLUDED and FACE_DOWN have the value hidden
            this.setText("?");
        }
    }
    
    /**
     * Handles the logic triggered when the card is clicked by the user.
     * Depending on the state of the card does the following:
     * - `FACE_DOWN` -> attempts to change its state to `FACE_UP`.
     * - `FACE_UP` or `EXCLUDED` -> it  attempts to change its state to `FACE_DOWN`.
     */
    private void cardClicked() {
        CardState newState;
        if (Objects.requireNonNull(state) == CardState.FACE_DOWN) {
            newState = CardState.FACE_UP;
            // Tells the registered objs the value of the card

        } else {
            newState = CardState.FACE_DOWN;
        }
        try{
            this.setState(newState);
        }catch (PropertyVetoException e) { }
    }
}
