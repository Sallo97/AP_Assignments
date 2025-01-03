package matchingPairGame.gui_components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.OptionalInt;

public class ScoreBoard extends JTable implements Serializable, PropertyChangeListener {
    private static final int ENTRIES = 10;
    private static final String DEFAULT_NAME = "AP24/25";
    private static final String DEFAULT_FILE = "./score.ser";

    String[] names = new String[ENTRIES];
    int[] score = new int[ENTRIES];
    int[] moves = new int[ENTRIES];
    private static final String[] COLUMN_NAMES = {"RANK", "PLAYER'S NAME", "SCORE", "MOVES"};

    // Constructors
    public ScoreBoard() {
        // Initialize model
        // Properties
        DefaultTableModel model = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resetArrays();
        model.addRow(COLUMN_NAMES);
        for (int i = 0; i < ENTRIES; i++) {
            model.addRow(new Object[]{i+1, names[i], score[i], moves[i]});
        }

        // Create JTable using the model
        setModel(model);
        setFillsViewportHeight(true);
        getTableHeader().setReorderingAllowed(false);
    }

    // Public Methods
    private void resetScore(){
        resetArrays();
        // Reset names/scores/moves
        for (int i = 1; i < ENTRIES + 1; i++){
            resetArrays();
            setValueAt(names[i], i, 1);
            setValueAt(score[i], i, 2);
            setValueAt(moves[i], i, 3);
        }
    }

    private void resetArrays(){
        for (int i = 0; i < ENTRIES; i++) {
            names[i] = DEFAULT_NAME;
            score[i] = 0;
            moves[i] = 0;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("rank")){
            int[] received = (int[]) evt.getNewValue();
            int numPlayer = received[0];
            int score = received[1];
            int moves = received[2];
            updateScore(numPlayer, score, moves);
        } else if(evt.getPropertyName().equals("ended")){
            saveScore();
        }
    }

    /**
     * TODO Add better description
     * Saves score to the disk
     */
    private void saveScore() {
        try{
            FileOutputStream fos = new FileOutputStream(DEFAULT_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            fos.close();
        }catch(IOException e){ /*TODO*/}
    }

    private void updateScore(int numPlayer, int newScore, int newMove){
        // Find the index of the Rank to update (if any)
        OptionalInt idx = OptionalInt.empty();
        for (int i = 0; i < ENTRIES; i++) {
            if (newScore > score[i] || (newScore == score[i] && newMove < moves[i])) {
                idx = OptionalInt.of(i);
                break;
            }
        }

        if (idx.isEmpty()) {
            return;
        }

        // If the idx exists update it
        String newName = getNameFromUser(numPlayer);
        int currentScore = newScore;
        int currentMoves = newMove;
        String currentName = newName;

        // Shift entries and update scores and moves
        for (int i = idx.getAsInt(); i < ENTRIES; i++) {
            // Swap current values with the next row
            int tempScore = score[i];
            int tempMoves = moves[i];
            String tempName = names[i];

            score[i] = currentScore;
            moves[i] = currentMoves;
            names[i] = currentName;

            setValueAt(currentName, i + 1, 1);
            setValueAt(currentScore, i + 1, 2);
            setValueAt(currentMoves, i + 1, 3);

            // Update for the next iteration
            currentScore = tempScore;
            currentMoves = tempMoves;
            currentName = tempName;
        }
    }

    /**
     * Gets the name to put in the high score entry by asking the user
     * @return the name given by the user
     */
    private String getNameFromUser(int numPlayer){

        String name = JOptionPane.showInputDialog(this,
                "PLAYER "+ numPlayer + " GOT AN HIGH SCORE\nPLEASE ENTER YOUR NAME:",
                "GOT AN HIGH SCORE",
                JOptionPane.PLAIN_MESSAGE);
        // Return the entered name, or a default value if canceled
        return (name != null && !name.trim().isEmpty()) ? name.trim() : DEFAULT_NAME;
    }
}
