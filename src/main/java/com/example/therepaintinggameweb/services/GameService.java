package com.example.therepaintinggameweb.services;

import com.example.therepaintinggameweb.logic.GameWrapper;
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

    private final GameWrapper gameWrapper;

    public String getGame() {
        return gameWrapper.toString();
    }

    public String stepGame(int colorId) {
        return "";
    }
}
