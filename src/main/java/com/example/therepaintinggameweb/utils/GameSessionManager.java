package com.example.therepaintinggameweb.utils;
import com.example.therepaintinggameweb.entities.Game;
import com.example.therepaintinggameweb.logic.GameStatus;
import com.example.therepaintinggameweb.logic.GameWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

@Data
public class GameSessionManager {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ConcurrentHashMap<String, GameSession> sessions = new ConcurrentHashMap<>();
    private final long sessionTimeoutNoAuth = 60 * 1000; // 1 минута
    private final long sessionTimeoutAuth = 30 * 60 * 1000; // 30 минут

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public void startNewSession(String sessionId, GameWrapper session) {
        long sessionTimeout = UserUtils.isGuest() ? sessionTimeoutNoAuth : sessionTimeoutAuth;

        // Запланировать задачу на удаление сессии через sessionTimeout миллисекунд
        ScheduledFuture<?> removalTask = scheduler.schedule(() -> removeSession(sessionId), sessionTimeout, TimeUnit.MILLISECONDS);

        sessions.put(sessionId, new GameSession(session, removalTask));
    }

    public void restartSession(String sessionId) {
        GameSession session =
                sessions.get(sessionId);

        session.getRemovalTask().cancel(false);

        long sessionTimeout = UserUtils.isGuest() ? sessionTimeoutNoAuth : sessionTimeoutAuth;

        ScheduledFuture<?> removalTask = scheduler.schedule(() -> removeSession(sessionId), sessionTimeout, TimeUnit.MILLISECONDS);
        session.setRemovalTask(removalTask);
    }

    public void removeSession(String sessionId) {
        GameSession gameSession = sessions.remove(sessionId);

        gameSession.getGameWrapper().setGameStatus(GameStatus.LOSE);
    }

    public void endSession(String sessionId) {
        GameSession gameSession = sessions.remove(sessionId);

        gameSession.getRemovalTask().cancel(false);
    }

    @Data
    @AllArgsConstructor
    public class GameSession {
        private final GameWrapper gameWrapper;
        private ScheduledFuture<?> removalTask;
    }
}
