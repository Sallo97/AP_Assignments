package matchingPairGame.logic_components.controller;

import java.util.OptionalInt;

/**
 * Represents a Pair of FACE_UP Cards
 */
public class Pair {
    private OptionalInt v1; // value of the first card FACE_UP
    private OptionalInt v2; // value of the second card FACE_UP

    /**
     * Constructor that initialize both cards to NONE
     */
    public Pair() {
        this.reset();
    }

    // Public Methods

    /**
     * Sets the first NONE Card to the given value.
     * If both are already NONE it throws an exception
     * @param value = the value to set the card
     */
    public void addValue(int value){
        if (v1.isEmpty()){
            v1 = OptionalInt.of(value);
        }
        else if (v2.isEmpty()){
            v2 = OptionalInt.of(value);
        } else{
            // TODO if both are already faceUP throw an exception
        }
    }

    /**
     * @return true if the cards have the same value
     *         false otherwise
     */
    public boolean areEqual(){
        return v1.equals(v2);
    }

    /**
     * set the Cards back to None
     */
    public void reset(){
        v1 = OptionalInt.empty();
        v2 = OptionalInt.empty();
    }

    /**
     * @return true if both Cards are FACE_UP
     *         false otherwise
     */
    public boolean isPairFull() {
        return v1.isPresent() && v2.isPresent();
    }

    /**
     * @return true if both Cards have value NONE
     *         false otherwise
     */
    public boolean isPairNone() {
        return v1.isEmpty() && v2.isEmpty();
    }
}