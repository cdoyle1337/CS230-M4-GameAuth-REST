package com.gamingroom.gameauth.auth;

import java.security.Principal;
import java.util.Set;
 
/**
 * In security, the principal object represents the user for which credentials have been 
 * supplied with the request. It implements java.security.Principal interface.
 */
public class GameUser implements Principal {
    private String name = "";
 
    private final Set<String> roles;
 
    public GameUser(String name) {
        this.name = name;
        this.roles = null;
    }
 
    public GameUser(String name, Set<String> roles) {
        this.name = name;
        this.roles = roles;
    }
 
    public String getName() {
        return name;
    }
 
    public int getId() {
        return (int) (Math.random() * 100);
    }
 
    public Set<String> getRoles() {
        return roles;
    }
}