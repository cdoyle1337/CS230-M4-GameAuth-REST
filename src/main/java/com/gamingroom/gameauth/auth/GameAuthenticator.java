package com.gamingroom.gameauth.auth;


import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
 
import java.util.Map;
import java.util.Optional;
import java.util.Set;
 
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
 

/**
 * Authenticator class is responsible for verifying username/password credentials included in Basic 
 * Auth header. In enterprise applications, you may fetch the user’s password from the database, and 
 * if it matches then you set the user roles into a principal object. In dropwizard, you will need to 
 * implement io.dropwizard.auth.Authenticator interface to put your application logic.
 */
public class GameAuthenticator implements Authenticator<BasicCredentials, GameUser> 
{
		
	private static final Map<String, Set<String>> VALID_USERS = ImmutableMap.of(
        "guest", ImmutableSet.of(),
        "user", ImmutableSet.of("USER"),
        "admin", ImmutableSet.of("ADMIN", "USER")
    );
 
    @Override
    public Optional<GameUser> authenticate(BasicCredentials credentials) 
    		throws AuthenticationException 
    {
        if (VALID_USERS.containsKey(credentials.getUsername()) 
        		&& "password".equals(credentials.getPassword())) 
        {
            // authorize method based on BasicAuth Security Example for new GameUser
        	return Optional.of(new GameUser(credentials.getUsername(), 
        			VALID_USERS.get(credentials.getUsername())));

        }
        return Optional.empty();
    }
}
