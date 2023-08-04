package com.example.therepaintinggameweb.logic;

import com.example.therepaintinggameweb.entities.Palettes;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.python.core.*;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Data
@RequiredArgsConstructor
public class GameWrapperFactory {
    private final PythonInterpreter pythonInterpreter;

    @Value("${python.game-path}")
    private String gamePath;

    public GameWrapper createGameWrapper(Color[] palettes) {
        return createGameWrapper(palettes, 12L, 22L);
    }
    public GameWrapper createGameWrapper(Color[] palettes, Long fieldSize, Long maxRounds) {
        if (fieldSize == null)
            fieldSize = 12L;
        if (maxRounds == null)
            maxRounds = 22L;

        try (
                PythonInterpreter pyInterp = new PythonInterpreter();
                InputStream initInputStream = this.getClass().getClassLoader().getResourceAsStream(gamePath)
        ) {
            pyInterp.execfile(initInputStream);
            PyClass gameClass = (PyClass) pyInterp.get("Game");

            gameSetup(gameClass, palettes, fieldSize, maxRounds);

            PyObject game = gameClass.__call__();

            PyObject convertColorsMethod = gameClass.invoke("convert_colors");
            Color[] colors = new Color[convertColorsMethod.__len__()];
            for (int i = 0; i < convertColorsMethod.__len__(); i++) {
                PyObject colorEntryObj = convertColorsMethod.__getitem__(i);

                int id = (int) colorEntryObj.__getattr__("id").__tojava__(int.class);
                String hexColor = (String) colorEntryObj.__getattr__("hex_color").__tojava__(String.class);

                colors[i] = new Color(id, hexColor);
            }

            int[] colorsCount = new int[colors.length];
            PyObject colorsCountPy = game.__getattr__("colors_count");
            for (int i = 0; i < colorsCountPy.__len__(); i++) {
                Integer colorCount = (Integer) colorsCountPy.__getitem__(i).__tojava__(Integer.class);
                colorsCount[i] = colorCount;
            }

            return new GameWrapper(game,
                    colors,
                    GameStatus.getStatus(Integer.parseInt(game.__getattr__("status").toString())),
                    Integer.parseInt(gameClass.__getattr__("MAX_ROUNDS").toString()),
                    Integer.parseInt(gameClass.__getattr__("FIELD_SIZE").toString()),
                    Integer.parseInt(game.__getattr__("round").toString()),
                    colorsCount,
                    Integer.parseInt(game.__getattr__("captured_count").toString())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void gameSetup(PyClass gameClass, Color[] palettes, Long fieldSize, Long maxRounds) {
        gameClass.__setattr__("FIELD_SIZE", Py.newInteger(fieldSize));
        gameClass.__setattr__("MAX_ROUNDS", Py.newInteger(maxRounds));

        if (palettes != null) {
            for (int i = 0; i < palettes.length; i++) {
                gameClass.invoke("set_color_by_id", Py.newInteger(i), Py.newString(palettes[i].getHexCode()));
            }
        }
    }
}
