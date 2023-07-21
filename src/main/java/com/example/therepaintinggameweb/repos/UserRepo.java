package com.example.therepaintinggameweb.repos;

import com.example.therepaintinggameweb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {

}
