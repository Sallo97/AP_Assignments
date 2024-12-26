package matchingPairGame.common_enums;

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
    EASY(150, 200, 4),
    MEDIUM(100, 150, 8),
    HARD(50, 100, 16);

    private final Size size;
    private final int numOfPairs;
    /**
     * Constructs a GameDifficulty with an associated size:
     */
    GameDifficulty(int width, int height, int numOfPairs) {
        size = new Size(width, height);
        this.numOfPairs = numOfPairs;
    }

    /**
     * @return the size associated width to the state
     */
    public Size getSize(){
        return size;
    }

    /**
     * @return the number of pairs to find in this game setting
     */
    public int getNumOfPairs(){
        return numOfPairs;
    }

    public int getNumOfCards(){
        return numOfPairs * 2;
    }
}
