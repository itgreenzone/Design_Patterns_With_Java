package com.itgreenzone.patterns.structural.proxy;

import java.util.Set;

public record User(String name, Set<String> roles) {

    public boolean hasRole(String role) {
        return roles.contains(role);
    }
}
