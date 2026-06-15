package edu.dadaev.greenpoint.enumerated;

public enum Role {
    USER, MODERATOR;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
