package com.goldfinger.authentication.models;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "role")
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
