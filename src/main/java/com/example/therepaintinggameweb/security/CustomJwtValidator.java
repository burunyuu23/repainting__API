package com.example.therepaintinggameweb.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CustomJwtValidator implements OAuth2TokenValidator<Jwt> {

    @Value(value = "${keycloak.jwt.auth.converter.resource-id}")
    private String resourceId;

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        // Извлекайте роли пользователя из токена

        Collection<String> resourceRoles = Optional.ofNullable(jwt.getClaim("resource_access"))
                .map(resourceAccess -> ((Map<String, Object>) resourceAccess).get(resourceId))
                .map(resource -> (Collection<String>) ((Map<String, Object>) resource).get("roles"))
                .orElse(Collections.emptyList());

        List<SimpleGrantedAuthority> roles = resourceRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        System.out.println(roles);

        // Проверьте наличие или соответствие ролей определенным правилам
        if (roles.isEmpty() || !roles.contains(new SimpleGrantedAuthority("ROLE_player"))) {
            return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token", "The token is missing required roles.", null));
        }

        return OAuth2TokenValidatorResult.success();
    }
}

