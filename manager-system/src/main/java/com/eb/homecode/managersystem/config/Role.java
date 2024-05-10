package com.eb.homecode.managersystem.config;

public enum Role {
    admin,

    user;

    public static boolean validate(String role) {
        for (Role value : Role.values()) {
            if (value.name().equals(role)) {
                return true;
            }
        }

        return false;
    }
}
