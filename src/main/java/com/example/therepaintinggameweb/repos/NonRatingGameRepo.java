package com.example.therepaintinggameweb.repos;

import com.example.therepaintinggameweb.entities.NonRatingGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NonRatingGameRepo extends JpaRepository<NonRatingGame, String> {
}
