package com.example.therepaintinggameweb.repos;

import com.example.therepaintinggameweb.entities.Game;
import com.example.therepaintinggameweb.entities.NonRatingGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NonRatingGameRepo extends JpaRepository<NonRatingGame, String> {
    Page<NonRatingGame> findAllByUserUserId(PageRequest pageRequest, String userId);
}
