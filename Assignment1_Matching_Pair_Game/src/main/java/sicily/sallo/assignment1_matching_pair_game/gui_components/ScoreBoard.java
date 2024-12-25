package sicily.sallo.assignment1_matching_pair_game.gui_components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

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

        // Set all cells uneditable


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
            int newMove = (Integer)evt.getOldValue();
            int newScore = (Integer)evt.getNewValue();

            // Check if the score is a new High Score
            for(int i = 1; i < entries + 1; i++){
                if(newScore > score[i] || newScore == score[i] && newMove < moves[i] ){
                    // TODO POPUP TO GET NAME
                    // UPDATE VALUE
                    //setValueAt(names[i], i, 1); TODO GET IT FROM POPUP
                    setValueAt(newScore, i, 2);
                    setValueAt(newMove, i, 3);
                    break;
                }

            }
        }

    }
}
