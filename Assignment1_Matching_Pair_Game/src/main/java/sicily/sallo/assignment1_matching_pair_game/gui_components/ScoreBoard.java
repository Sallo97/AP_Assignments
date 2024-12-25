package sicily.sallo.assignment1_matching_pair_game.gui_components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ScoreBoard extends JTable {
    // Properties
    private DefaultTableModel model;
    int entries = 10;

    String[] names = new String[entries];
    int[] score = new int[entries];
    int[] moves = new int[entries];
    String[] columnNames = {"NUM", "PLAYER'S NAME", "SCORE", "MOVES"};

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
            setValueAt(names[i], i, 0);
            setValueAt(score[i], i, 1);
            setValueAt(moves[i], i, 2);
        }
    }

    private void resetArrays(){
        for (int i = 0; i < entries; i++) {
            names[i] = "DEFAULT";
            score[i] = 0;
            moves[i] = 0;
        }
    }
}
