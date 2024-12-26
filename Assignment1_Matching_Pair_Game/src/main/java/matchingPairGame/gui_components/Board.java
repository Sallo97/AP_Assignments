package matchingPairGame.gui_components;

import matchingPairGame.common_enums.GameState;
import matchingPairGame.logic_components.challenge.Challenge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author sallo
 */
public class Board extends JFrame implements Serializable, ActionListener {
    // Properties
    JLabel gameTitle = new JLabel("MEMORY MATCHING GAME", SwingConstants.CENTER);
    DifficultyChooser difficultyChooser = new DifficultyChooser();
    CommandButtons commandButtons = new CommandButtons();
    CardTable cardTable = new CardTable();
    GameState currentState = GameState.MENU_SELECTION;
    ExitPopUp exitPopUp;
    PlayerSelector playerSelector = new PlayerSelector();
    ScoreBoard scoreBoard;
    JLabel highScoreTitle = new JLabel("BEST 10 PLAYERS", SwingConstants.CENTER);
    private String defaultDirectory = "./score.ser";

    // Constructors
    /**
     * Creates new form Board
     */
    public Board() {
        // loading Score
        loadScore();

        // Setting Components
        this.setTitle("Memory Matching Game");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Become Listener of Shuffle and Exit Button
        commandButtons.exitButton.addActionListener(this);
        commandButtons.shuffleButton.addActionListener(this);
        commandButtons.shuffleButton.addActionListener(cardTable.infoTab.counter);
        commandButtons.highScoreButton.addActionListener(this);


        // Let controller be a listener of you
        this.addPropertyChangeListener(cardTable.infoTab.challenge);
        this.addPropertyChangeListener(cardTable.infoTab.controller);
        this.addPropertyChangeListener(cardTable.infoTab.counter);
        cardTable.infoTab.controller.addPropertyChangeListener(cardTable.infoTab.counter);
        cardTable.infoTab.controller.addPropertyChangeListener(scoreBoard);
        cardTable.infoTab.controller.addPropertyChangeListener(cardTable.infoTab.challenge);

        this.addPropertyChangeListener(scoreBoard);
        cardTable.infoTab.counter.addPropertyChangeListener(cardTable.infoTab.controller);


        // Setting layout
        this.setSize(600, 400);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setMenuLayout();

        exitPopUp = new ExitPopUp(this);

    }

    /**
     * TODO Add better description
     * Load score to the disk
     */
    private void loadScore(){
        try{
            FileInputStream fis = new FileInputStream(defaultDirectory);
            ObjectInputStream ois = new ObjectInputStream(fis);
            scoreBoard = (ScoreBoard) ois.readObject();
            fis.close();

        } catch (ClassNotFoundException | IOException e) {
            scoreBoard = new ScoreBoard();
        }
    }

    // Public Methods
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Board().setVisible(true));
    }

    /**
     * TODO ADD Better description
     * Change the state and tell Controller about it
     */
    public void setState(GameState newState){
        GameState oldState = this.currentState;
        this.currentState = newState;
        firePropertyChange("board", oldState, newState);
        firePropertyChange("difficulty", null, this.difficultyChooser.gameDifficulty());
    }

    /**
     * SHUFFLE Button Clicked -> Start the game
     * EXIT Button Clicked -> Exit the game
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "exit":
                // Ask the player if he wants to exit the game
                exitPopUp.update(this, currentState);
                break;

            case "shuffle":
                startGame();
                break;

            case "highScore":
                setState(GameState.SCORE_BOARD);
                setScoreLayout();
                break;

            case "popupExit":
                if (currentState == GameState.MENU_SELECTION) {
                    this.dispose();
                } else {
                    setState(GameState.MENU_SELECTION);
                    setMenuLayout();
                }
                break;
        }

    }

    // Private Methods
    /**
     * Start the game following the inputted settings
     */
    private void startGame() {
        // Set the state to MENU
        setState(GameState.MENU_SELECTION);

        // Set the number of Players
        firePropertyChange("numOfPlayer", null, playerSelector.getNumOfPlayers());

        // Get the n of cards
        int numOfPairs = difficultyChooser.gameDifficulty().getNumOfPairs();

        int numOfCards = difficultyChooser.gameDifficulty().getNumOfCards();

        // Create the values of n cards
        int[] values = randomDeck(numOfPairs, numOfCards);
        cardTable.update(numOfCards, values);

        // Set the board accordingly
        setGameLayout();

        // Set the state to IN-GAME
        setState(GameState.IN_GAME);
    }

    /**
     * Removes all custom GUI Components
     */
    private void clearLayout(){
        this.remove(gameTitle);
        this.remove(difficultyChooser);
        this.remove(playerSelector);
        this.remove(commandButtons);
        this.remove(cardTable);
        this.remove(scoreBoard);
        this.remove(highScoreTitle);
    }


    /**
     * Sets the layout for the HighScore Board
     */
    private void setScoreLayout() {
        clearLayout();
        this.add(highScoreTitle);
        this.add(scoreBoard);
        this.add(commandButtons);
        this.revalidate();
        this.repaint();
    }

    /**
     * Sets the layout for the main menu
     */
    private void setMenuLayout() {
        clearLayout();
        add(gameTitle, BorderLayout.NORTH);
        add(difficultyChooser);
        add(playerSelector);
        add(commandButtons, BorderLayout.SOUTH);

        this.revalidate();
        this.repaint();
    }

    /**
     * Sets the layout for the Game board
     */
    private void setGameLayout(){
        clearLayout();
        this.add(cardTable);
        this.add(commandButtons);

        this.revalidate();
        this.repaint();
    }

    /**
     * Generates a random sequence of numOfCards elems
     * s.t. each value in [1, nPairs] occurs exactly
     * two times in the sequence.
     * @param nPairs number of pairs in the game
     * @param numOfCards number of cards in total
     *
     * @return the sequence as an array of integers
     */
    private int[] randomDeck(int nPairs, int numOfCards) {
        int[] deck = new int[numOfCards];

        // Construct the base array
        for (int i = 0; i < nPairs; i++) {
            int pos = i * 2;
            deck[pos+1] = deck[pos] = i + 1;
        }

        // Generate the random sequence
        Random rand = new Random();
        for (int i = 0; i < numOfCards; i++) {
            int j = rand.nextInt(i, numOfCards);
            int temp = deck[j];
            deck[j] = deck[i];
            deck[i] = temp;
        }
        return deck;
    }
}
