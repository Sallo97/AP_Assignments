package sicily.sallo.assignment1_matching_pair_game.logic_components.buttons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class Exit extends JButton implements Serializable {

    /**
     * TODO Add better description
     * If it is pressed the application closes
     * it is triggered by generating a `exit` event
     */
    public Exit() {
        setSize(50, 50);
        String text = "EXIT";
        setText(text);

        // Set Mouse Event when clicked
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt){
                fireActionPerformed(new ActionEvent(this, 1, "exit"));
            }
        });
    }
}
