package org.app.restaurant.constatnts;

public enum RoleType {

    USER("USER"),
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR"),
    GUEST("GUEST");

    private final String roleName;

    RoleType(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
