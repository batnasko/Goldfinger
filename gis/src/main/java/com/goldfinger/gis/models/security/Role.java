package com.goldfinger.gis.models.security;


import org.springframework.security.core.GrantedAuthority;


public class Role implements GrantedAuthority {

    private long id;
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public Role(){

    }

    public Role(String authority){
        this.authority = authority;
    }

    public Role(long id){
        this.id = id;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
