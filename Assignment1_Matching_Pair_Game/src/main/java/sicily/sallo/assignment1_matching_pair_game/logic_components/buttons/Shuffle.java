package sicily.sallo.assignment1_matching_pair_game.logic_components.buttons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.*;

/**
 * TODO Add Better description
 */
public class Shuffle extends JButton implements Serializable {

    /*
     * TODO Add better description
     */
    public Shuffle () {
        setSize(50, 50);
        setText("SHUFFLE");

        // Set Mouse Event when clicked
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt){
                setText("Shuffle Deck (Restart Game)");
                fireActionPerformed(new ActionEvent(this, 0, "shuffle"));
            }
        });
    }

     // Private Methods



}
