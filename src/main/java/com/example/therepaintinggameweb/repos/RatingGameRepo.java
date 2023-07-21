package com.example.therepaintinggameweb.repos;

import com.example.therepaintinggameweb.entities.RatingGame;
import com.example.therepaintinggameweb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingGameRepo extends JpaRepository<RatingGame, String> {

    @Query("select sum(rating) from RatingGame where user.userId = :userId and isEnd = true")
    Integer sumRatingByUserId(String userId);

    @Query("select sum(rating) from RatingGame where gameId = :gameId and isEnd = true")
    Integer sumRatingByGameId(String gameId);
}
