package com.stdio.esm.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class EsmAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public EsmAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public EsmAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
