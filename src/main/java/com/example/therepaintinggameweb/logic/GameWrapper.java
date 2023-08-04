package com.example.therepaintinggameweb.logic;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyType;

@Data
@AllArgsConstructor
public class GameWrapper {
    private final PyObject game;

    private final Color[] colors;
    private GameStatus gameStatus;
    private final int maxRounds;
    private final int fieldSize;
    private int currentRound;
    private int[] colorsCount;
    private int capturedCount;

    public CellWrapper[][] getMap() {
        CellWrapper[][] map = new CellWrapper[this.fieldSize][this.fieldSize];
        PyObject mapFromGame = game.__getattr__("map");
        for (int i = 0; i < mapFromGame.__len__(); i++) {
            PyObject line = mapFromGame.__getitem__(i);
            for (int j = 0; j < line.__len__(); j++) {
                PyObject cell = line.__getitem__(j);
                CellWrapper cellWrapper = new CellWrapper(
                        (Boolean) cell.__getattr__("is_captured").__tojava__(Boolean.class),
                        (Integer) cell.__getattr__("value").__tojava__(Integer.class));
                map[i][j] = cellWrapper;
            }
        }
        return map;
    }

    public int[] getColorsCount() {
        PyObject colorsCountPy = game.__getattr__("colors_count");
        for (int i = 0; i < colorsCountPy.__len__(); i++) {
            Integer colorCount = (Integer) colorsCountPy.__getitem__(i).__tojava__(Integer.class);
            colorsCount[i] = colorCount;
        }

        return colorsCount;
    }

    public int getCapturedCount() {
        capturedCount = Integer.parseInt(game.__getattr__("captured_count").toString());

        return capturedCount;
    }

    public void step(int colorId) {
        PyObject stepMethod = game.__getattr__("step");
        PyObject stepColorId = Py.newInteger(colorId);
        stepMethod.__call__(new PyObject[]{stepColorId});
    }

    public boolean colorRepeated(int colorId) {
        PyObject colorRepeatedMethod = game.__getattr__("color_repeated");
        PyObject color = Py.newInteger(colorId);
        return (boolean) colorRepeatedMethod.__call__(new PyObject[]{color}).__tojava__(boolean.class);
    }

    public void restart(){
        game.invoke("restart");
    }

    public void updateState() {
        this.getColorsCount();
        this.getCapturedCount();
        this.setGameStatus(GameStatus.getStatus(Integer.parseInt(game.__getattr__("status").toString())));
        this.setCurrentRound(Integer.parseInt(game.__getattr__("round").toString()));
    }

    @Override
    public String toString() {
        getMap();
        return String.format("STATUS: %s, maxRound: %d, fieldSize: %d map:%s",
                gameStatus, maxRounds, fieldSize, game.invoke("__str__").toString());
    }

//    public void changeColor(int id, String hexColor){
//        colors[id].setHexCode(hexColor);
//    }


}
