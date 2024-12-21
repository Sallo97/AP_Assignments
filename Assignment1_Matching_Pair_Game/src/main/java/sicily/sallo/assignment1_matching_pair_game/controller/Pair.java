package sicily.sallo.assignment1_matching_pair_game.controller;

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
        if (v1.isNone()){
            v1 = OptionalInt.some(value);
        }
        else if (v2.isNone()){
            v2 = OptionalInt.some(value);
        }
        // TODO if both are already faceUP throw an exception
    }

    /**
     * @return true if the cards have the same value
     *         false otherwise
     */
    public boolean areEqual(){
        if(v1.isSome() && v2.isSome()){
            return v1.unwrap() == v2.unwrap();
        }
        return false;
    }

    /**
     * set the Cards back to None
     */
    public void reset(){
        v1 = OptionalInt.NONE;
        v2 = OptionalInt.NONE;
    }

    /**
     * @return true if both Cards are FACE_UP
     *         false otherwise
     */
    public boolean isPairFull() {
        return v1.isSome() && v2.isSome();
    }

    /**
     * @return true if both Cards have value NONE
     *         false otherwise
     */
    public boolean isPairNone() {
        return v1.isNone() && v2.isNone();
    }



}