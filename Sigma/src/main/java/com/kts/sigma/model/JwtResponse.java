package com.kts.sigma.model;

import java.io.Serializable;
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final String username;

    public JwtResponse(String token, String username) {
        this.jwttoken = token;
        this.username = username;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public String getUsername() {
        return username;
    }


}
