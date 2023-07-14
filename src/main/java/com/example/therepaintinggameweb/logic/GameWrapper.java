package com.example.therepaintinggameweb.logic;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.python.core.PyObject;

@Data
@AllArgsConstructor
public class GameWrapper {
    private final PyObject game;
    private final Color[] colors;
    private GameStatus gameStatus;
    private final int maxRounds;
    private final int fieldSize;

    public void init(){

    }

    @Override
    public String toString() {
        return game.invoke("__str__").toString();
    }

//    public void changeColor(int id, String hexColor){
//        colors[id].setHexCode(hexColor);
//    }


}
