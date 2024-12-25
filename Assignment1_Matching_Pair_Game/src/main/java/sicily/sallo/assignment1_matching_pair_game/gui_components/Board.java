package sicily.sallo.assignment1_matching_pair_game.gui_components;

import sicily.sallo.assignment1_matching_pair_game.common_enums.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    // Constructors
    /**
     * Creates new form Board
     */
    public Board() {
        // Setting Components
        this.setTitle("Memory Matching Game");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Become Listener of Shuffle and Exit Button
        commandButtons.exitButton.addActionListener(this);
        commandButtons.shuffleButton.addActionListener(this);
        commandButtons.shuffleButton.addActionListener(cardTable.infoTab.counter);

        // Let controller be a listener of you
        this.addPropertyChangeListener(cardTable.infoTab.controller);
        this.addPropertyChangeListener(cardTable.infoTab.counter);
        cardTable.infoTab.controller.addPropertyChangeListener(cardTable.infoTab.counter);
        cardTable.infoTab.counter.addPropertyChangeListener(cardTable.infoTab.controller);

        // Setting layout
        this.setSize(600, 400);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setMenuLayout();

        exitPopUp = new ExitPopUp(this);
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

        // Set the state to INGAME
        setState(GameState.IN_GAME);
    }

    /**
     * Sets the layout for the main menu
     */
    private void setMenuLayout() {
        this.remove(cardTable);
        this.remove(commandButtons);
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
        this.remove(gameTitle);
        this.remove(difficultyChooser);
        this.remove(playerSelector);
        this.remove(commandButtons);
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
