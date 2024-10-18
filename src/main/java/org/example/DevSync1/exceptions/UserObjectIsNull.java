package org.example.DevSync1.exceptions;

public class UserObjectIsNull extends RuntimeException {
    public UserObjectIsNull() {
        super("User object is null or contains empty/null attributes");
    }
}
