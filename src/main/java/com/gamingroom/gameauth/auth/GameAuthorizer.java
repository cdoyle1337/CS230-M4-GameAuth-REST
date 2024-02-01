package com.gamingroom.gameauth.auth;

import io.dropwizard.auth.Authorizer;

/**
 * Authorizer class is responsible for matching the roles and deciding whether 
 * the user is allowed to perform a certain action or not.
 */
public class GameAuthorizer implements Authorizer<GameUser> 
{
    @Override
    public boolean authorize(GameUser user, String role) {
    	
        // authorize method based on BasicAuth Security Example
    	return user.getRoles() != null && user.getRoles().contains(role);
    	
    }
}