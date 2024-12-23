/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sicily.sallo.assignment1_matching_pair_game.gui_components;
import sicily.sallo.assignment1_matching_pair_game.logic_components.buttons.*;

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

        // Setting layout
        this.setSize(600, 400);
        this.setLayout(new BorderLayout());
        setMenuLayout();
    }

    // Public Methods
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Board().setVisible(true);
            }
        });
    }

    /**
     * TODO Add better description
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "exit":
                dispose();
                break;
            case "shuffle":
                startGame();
                break;
        }

    }

    // Private Methods

    private void startGame() {

        // Get the n of cards
        int numOfPairs = difficultyChooser.gameDifficulty().getNumOfPairs();
        int numOfCards = difficultyChooser.gameDifficulty().getNumOfCards();

        // Create the values of n cards
        int[] values = randomDeck(numOfPairs, numOfCards);
        cardTable.update(numOfCards);

        setGameLayout();
    }

    /**
     * TODO Add better description
     */
    private void setMenuLayout() {
        //this.remove(cardTable);
        add(gameTitle, BorderLayout.NORTH);
        add(difficultyChooser, BorderLayout.CENTER);
        add(commandButtons, BorderLayout.SOUTH);
    }

    /**
     * TODO Add better description
     */
    private void setGameLayout(){
        this.remove(gameTitle);
        this.remove(difficultyChooser);
        this.add(cardTable, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    /**
     * TODO Add better description
     * random sampling of a deck of n pairs
     * @param nPairs
     * @return
     */
    private int[] randomDeck(int nPairs, int numOfCards) {
        int[] deck = new int[numOfCards];
        // Construct the random Deck
        Random rand = new Random();
        for (int i = 0; i < numOfCards; i++) {
            // Get a random position
            int j = rand.nextInt(0, nPairs);
            // Get the logical value associated
            int iVal = i/2 + 1;
            int jVal = j/2 + 1;
            deck[i] = iVal;
            deck[j] = jVal;
        }
        return deck;
    }
}
