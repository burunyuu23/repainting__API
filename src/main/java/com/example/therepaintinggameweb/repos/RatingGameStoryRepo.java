package com.example.therepaintinggameweb.repos;

import com.example.therepaintinggameweb.entities.GameStoryId;
import com.example.therepaintinggameweb.entities.RatingGameStory;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RatingGameStoryRepo extends JpaRepository<RatingGameStory, GameStoryId> {

    @Query("select sum(ratingChange) from RatingGameStory where id.gameId = :gameId")
    Integer sumRatingByGameId(String gameId);
}
