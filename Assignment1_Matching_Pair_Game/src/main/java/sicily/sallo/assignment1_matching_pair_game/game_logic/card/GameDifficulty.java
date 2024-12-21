package sicily.sallo.assignment1_matching_pair_game.game_logic.card;

/**
 * Defines the various difficulty setting of a Game, which amounts to the number of cards diplayed,
 * along with the size of the cards associated with each state (width, height).
 * The possible choices are:
 * - EASY: There are 8 Cards with a total of 4 pairs to find
 * - NORMAL: There are 16 Cards with a total of 8 pairs to find
 * - FACE_UP: There are 32 Cards with a total of 16 pairs to find
 * @author Salvatore Salerno
 */
public enum GameDifficulty {
    EASY(150, 200),
    NORMAL(100, 150),
    HARD(50, 100);

    private final int width;
    private final int height;

    /**
     * Constructs a GameDifficulty with an associated size:
     */
    GameDifficulty(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @return the size associated width to the state
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the size associated height to the state
     */
    public int getHeight() {
        return height;
    }
}
