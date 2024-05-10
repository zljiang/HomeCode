package com.eb.homecode.managersystem.model;

import java.util.Objects;

public class UserInfo {
    private Integer userId;
    private String accountName;
    private String role;

    public UserInfo() {
    }

    public UserInfo(Integer userId, String accountName, String role) {
        this.userId = userId;
        this.accountName = accountName;
        this.role = role;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return userId + "|" + accountName + "|" + role + System.lineSeparator();
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, accountName, role);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof UserInfo)) {
            return false;
        }

        UserInfo other = (UserInfo) obj;
        return this.userId.equals(other.userId)
                && this.accountName.equals(other.accountName)
                && this.role.equals(other.role);
    }
}
