package com.example.therepaintinggameweb.utils;

import com.example.therepaintinggameweb.entities.User;
import com.example.therepaintinggameweb.repos.UserRepo;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;


public class UserUtils {
    private static UserRepo userRepo;

    public static boolean isGuest() {
        return getCurrentUserId().equals("anonymousUser");
    }

    public static String getCurrentUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication().getPrincipal() != "anonymousUser") {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) context.getAuthentication();
            Jwt jwt = jwtAuthenticationToken.getToken();
            return jwt.getSubject();
        }
        return context.getAuthentication().getName();
    }

    public static User addAndGetCurrentUser() {
        User user;
        if (userRepo.findById(UserUtils.getCurrentUserId()).isEmpty()) {
            user = new User();
            user.setUserId(UserUtils.getCurrentUserId());
            user.setImageUrl("https://i.ibb.co/w04Prt6/c1f64245afb2.gif");
        } else {
            user = userRepo.findById(UserUtils.getCurrentUserId()).get();
        }
        return user;
    }

    public static void setUserRepo(UserRepo userRepo) {
        UserUtils.userRepo = userRepo;
    }
}
