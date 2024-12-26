package matchingPairGame.logic_components.challenge;


import matchingPairGame.common_enums.GameDifficulty;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.*;

public class Challenge extends JLabel implements Serializable, PropertyChangeListener {
    // Properties
    HashMap<GameDifficulty, OptionalInt> currentBest = new HashMap<>();
    GameDifficulty currentDifficulty;
    private static final String MSG = "| CURRENT BEST MOVES : ";

    // Constructors
    public Challenge() { }

    // Public Methods
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "difficulty":
                currentDifficulty = (GameDifficulty) evt.getNewValue();
                if (!currentBest.containsKey(currentDifficulty)){
                    currentBest.put(currentDifficulty, OptionalInt.empty());
                }
                break;

            case "numOfPlayer":
                setText();
                break;

            case "ended":
                ArrayList<Integer> moves = (ArrayList<Integer>) evt.getNewValue();
                int min = Collections.min(moves);

                if (currentBest.get(currentDifficulty).isPresent()) {
                    if (currentBest.get(currentDifficulty).getAsInt() >= min) {
                        currentBest.put(currentDifficulty, OptionalInt.of(min));
                    }
                } else{
                    currentBest.put(currentDifficulty, OptionalInt.of(min));
                }
                clearText();
                break;
        }
    }

    private void setText(){
        StringBuilder toPrint = new StringBuilder(MSG);
        if(currentBest.get(currentDifficulty).isEmpty()){
            toPrint.append(0);
        }
        else {
            toPrint.append(currentBest.get(currentDifficulty).getAsInt());
        }
        setText(toPrint.toString());
    }

    private void clearText(){
        setText(" ");
    }
}
