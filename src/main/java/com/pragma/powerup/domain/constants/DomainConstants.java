package com.pragma.powerup.domain.constants;

public class DomainConstants {

    public static final String RESTAURANT_ALREADY_EXISTS = "Restaurant already exists";
    public static final String USER_NOT_OWNER = "User is not the owner";

    public static final String NAME_INVALID_FORMAT = "The restaurant name cannot contain only numbers.";
    public static final String NIT_INVALID_FORMAT = "The NIT must contain only numeric values.";
    public static final String PHONE_INVALID_FORMAT = "Invalid phone format. Example: +573005698325";

    public static final String NUMERIC_REGEX = "^[0-9]+$";
    public static final String PHONE_REGEX = "^[+]?[0-9]{1,13}$";
    public static final String NAME_REGEX = "^(?![0-9]+$).+$";

    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }
}
