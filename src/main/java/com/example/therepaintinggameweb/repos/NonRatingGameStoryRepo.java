package com.example.therepaintinggameweb.repos;

import com.example.therepaintinggameweb.entities.NonRatingGameStory;
import com.example.therepaintinggameweb.entities.GameStoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NonRatingGameStoryRepo extends JpaRepository<NonRatingGameStory, GameStoryId> {

    @Query("SELECT gs FROM NonRatingGameStory gs WHERE gs.id.gameId = :gameId")
    List<NonRatingGameStory> findByGameId(String gameId);

}
