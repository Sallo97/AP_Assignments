package sicily.sallo.assignment1_matching_pair_game.gui_components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.OptionalInt;

public class ScoreBoard extends JTable implements Serializable, PropertyChangeListener {
    // Properties
    private DefaultTableModel model;
    int entries = 10;

    String[] names = new String[entries];
    int[] score = new int[entries];
    int[] moves = new int[entries];
    String[] columnNames = {"RANK", "PLAYER'S NAME", "SCORE", "MOVES"};

    // Constructors
    public ScoreBoard() {
        // Initialize model
        model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resetArrays();
        model.addRow(columnNames);
        for (int i = 0; i < entries ; i++) {
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
        for (int i = 1; i < entries + 1; i++){
            resetArrays();
            setValueAt(names[i], i, 1);
            setValueAt(score[i], i, 2);
            setValueAt(moves[i], i, 3);
        }
    }

    private void resetArrays(){
        for (int i = 0; i < entries; i++) {
            names[i] = "DEFAULT";
            score[i] = 0;
            moves[i] = 0;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("rank")){
            int score = (Integer)evt.getNewValue();
            int moves = (Integer)evt.getOldValue();
            updateScore(score, moves);
        }

    }

    private void updateScore(int newScore, int newMove){
        // Find the index of the Rank to update (if any)
        OptionalInt idx = OptionalInt.empty();
        for (int i = 0 ; i < entries; i++) {
            if (newScore > score[i] || (newScore == score[i] && newMove < moves[i])) {
                idx = OptionalInt.of(i);
                break;
            }
        }

        // If the idx exists update it
        if (idx.isEmpty()) { return;}
        int rowIdx = idx.getAsInt() + 1;
        int oldScore = score[idx.getAsInt()]; int oldMoves = moves[idx.getAsInt()]; String oldName = names[idx.getAsInt()];
        String newName = "CULO";// TODO Get the name by the player
        moves[idx.getAsInt()] = newMove;
        names[idx.getAsInt()] = newName;
        score[idx.getAsInt()] = newScore;

        setValueAt(newName, rowIdx, 1);
        setValueAt(score[idx.getAsInt()], rowIdx, 2);
        setValueAt(moves[idx.getAsInt()], rowIdx, 3);

        int tempScore; int tempMoves; String tempName;
        // Update the values below
        for (int i = (idx.getAsInt() + 1) ; i < entries; i++){
            tempScore = score[i]; tempMoves = moves[i]; tempName = names[i];
            score[i] = oldScore; moves[i] = oldMoves; names[i] = oldName;

            setValueAt(names[i], i+1, 1);
            setValueAt(score[i], i+1, 2);
            setValueAt(moves[i], i+1, 3);

            oldScore = tempScore; oldMoves = tempMoves; oldName = tempName;
        }
    }
}
