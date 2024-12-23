package sicily.sallo.assignment1_matching_pair_game.logic_components.card;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Objects;

public class Card extends JButton {
    // Properties
    private int value;
    private CardState state;

    // Constructors

    public Card() {
        super();
        this.value = 0;
        this.state = CardState.FACE_DOWN;
        this.setText("CULO");
        getState();

    }

    private void getState() {

        // Set Color
        this.setBackground(state.getColor());
        this.setOpaque(true);
        this.setContentAreaFilled(true);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set Text
        if (Objects.requireNonNull(state) == CardState.FACE_UP) {
            this.setText(String.valueOf(this.value));
        } else {
            // Both EXCLUDED and FACE_DOWN have the value hidden
            this.setText("?");
        }
    }
}
