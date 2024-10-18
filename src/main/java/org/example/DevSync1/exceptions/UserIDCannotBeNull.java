package org.example.DevSync1.exceptions;

public class UserIDCannotBeNull extends RuntimeException {
    public UserIDCannotBeNull() {
        super("User ID cannot be null. Please provide a valid User ID for deletion.");
    }
}
