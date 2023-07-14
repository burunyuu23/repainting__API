package com.example.therepaintinggameweb.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.python.core.*;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@Data
public class GameService {

    private final PythonInterpreter pythonInterpreter;

    public String getGame() {
        String result = "";

        try (PythonInterpreter pyInterp = new PythonInterpreter();
             InputStream initInputStream = this.getClass().getClassLoader().getResourceAsStream("python/game.py")
        ) {
            pyInterp.exec("print('Hello Python World!')");
            pyInterp.execfile(initInputStream);
            PyClass gameClass = (PyClass) pyInterp.get("Game");
            PyObject game = gameClass.__call__();
            pyInterp.exec("game = Game()");
            pyInterp.exec("print(game.map)");
            result = String.valueOf(game.__str__());
            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
