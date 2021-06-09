package dev.bazza.deltacore.system.models.roles;

public abstract class UserRole {

    @Override
    public String toString() {
        return (this instanceof OnlineRole) ? "Online" : "Offline";
    }
}
