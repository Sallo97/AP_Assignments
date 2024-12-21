package sicily.sallo.assignment1_matching_pair_game.controller;

/**
 * Represents an optional integer value, similar to Rust's Option type.
 * It can either hold a value (SOME) or indicate the absence of a value (NONE).
 * In the game is used for storing the value of a Card in the Controller bean.
 */
public enum OptionalInt {
    NONE,
    SOME;

    private int value;

    /**
     * Creates an instance representing the absence of a value.
     *
     * @return the NONE instance of OptionalInt
     */
    public static OptionalInt none() {
        return NONE;
    }

    /**
     * Creates an instance holding a specific integer value.
     *
     * @param value the integer value to wrap in an OptionalInt
     * @return the SOME instance of OptionalInt with the specified value
     */
    public static OptionalInt some(int value) {
        OptionalInt optionalInt = SOME;
        optionalInt.value = value;
        return optionalInt;
    }

    /**
     * Checks whether this instance contains a value.
     *
     * @return true if this instance is SOME, false otherwise
     */
    public boolean isSome() {
        return this == SOME;
    }

    /**
     * Checks whether this instance represents the absence of a value.
     *
     * @return true if this instance is NONE, false otherwise
     */
    public boolean isNone() {
        return this == NONE;
    }

    /**
     * Retrieves the contained integer value.
     *
     * @return the integer value if this instance is SOME
     * @throws IllegalStateException if this instance is NONE
     */
    public int unwrap() {
        if (this == NONE) {
            throw new IllegalStateException("Called unwrap() on None");
        }
        return value;
    }
}


