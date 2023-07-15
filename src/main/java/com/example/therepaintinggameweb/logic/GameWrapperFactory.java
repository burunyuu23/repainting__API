package com.example.therepaintinggameweb.logic;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.python.core.PyArray;
import org.python.core.PyClass;
import org.python.core.PyList;
import org.python.core.PyObject;
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

    public GameWrapper createGameWrapper() {
        try (PythonInterpreter pyInterp = new PythonInterpreter();
             InputStream initInputStream = this.getClass().getClassLoader().getResourceAsStream(gamePath)
        ) {
            pyInterp.exec("print('Hello!')");
            System.out.println(initInputStream);
            System.out.println(gamePath);
            pyInterp.execfile(initInputStream);
            PyClass gameClass = (PyClass) pyInterp.get("Game");
            PyObject game = gameClass.__call__();

            PyObject convertColorsMethod = gameClass.invoke("convert_colors");

            Color[] colors = new Color[convertColorsMethod.__len__()];
            for (int i = 0; i < convertColorsMethod.__len__(); i++) {
                PyObject colorEntryObj = convertColorsMethod.__getitem__(i);
                System.out.println(colorEntryObj);
                int id = (int) colorEntryObj.__getattr__("id").__tojava__(int.class);
                String hexColor = (String) colorEntryObj.__getattr__("hex_color").__tojava__(String.class);
                colors[i] = new Color(id, hexColor);
            }

            System.out.println(Integer.parseInt(game.__getattr__("status").toString()));
            System.out.println(Integer.parseInt(gameClass.__getattr__("MAX_ROUNDS").toString()));

            System.out.println(game.invoke("__str__"));
            return new GameWrapper(game,
                    colors,
                    GameStatus.getStatus(Integer.parseInt(game.__getattr__("status").toString())),
                    Integer.parseInt(gameClass.__getattr__("MAX_ROUNDS").toString()),
                    Integer.parseInt(gameClass.__getattr__("FIELD_SIZE").toString()),
                    Integer.parseInt(game.__getattr__("round").toString())
                    );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
