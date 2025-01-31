package com.pragma.powerup.application.constants;

public class ValidationConstants {

    // First Name
    public static final String FIRST_NAME_REQUIRED = "The firstName is required";

    // Last Name
    public static final String LAST_NAME_REQUIRED = "The lastName is required";

    // Document Number
    public static final String DOCUMENT_NUMBER_REQUIRED = "The documentNumber is required";
    public static final String DOCUMENT_NUMBER_POSITIVE = "The documentNumber must be a positive number";

    // Phone
    public static final String PHONE_REQUIRED = "The phone is required";
    public static final String PHONE_MAX_LENGTH = "The phone must have a maximum of 13 characters";
    public static final String PHONE_INVALID_FORMAT = "The phone must have a valid format and include +";

    // Birth Date
    public static final String BIRTH_DATE_PAST = "The birthDate must be in the past";

    // Email
    public static final String EMAIL_REQUIRED = "The email is required";
    public static final String EMAIL_INVALID_FORMAT = "The email must have a valid format";

    // Password
    public static final String PASSWORD_REQUIRED = "The password is required";
    public static final String PASSWORD_MIN_LENGTH = "The password must have a minimum of 8 characters";

    // Restaurant

    public static final String NAME_REQUIRED = "The restaurant name is required";
    public static final String NAME_INVALID_FORMAT = "The restaurant name cannot contain only numbers";

    public static final String ADDRESS_REQUIRED = "The restaurant address is required";

    public static final String OWNER_ID_REQUIRED = "The owner ID is required";

    public static final String LOGO_URL_REQUIRED = "The restaurant logo URL is required";

    public static final String NIT_REQUIRED = "The NIT (Tax Identification Number) is required";
    public static final String NIT_INVALID_FORMAT = "The NIT must contain only numeric values";

    public static final String NUMERIC_REGEX = "^[0-9]+$";
    public static final String PHONE_REGEX = "^[+]?[0-9]{1,13}$";
    public static final String NAME_REGEX = "^(?![0-9]+$).+$";



    public static final String NAME_DISH_REQUIRED = "Dish name is required.";
    public static final String NAME_DISH_MAX_LENGTH = "Dish name cannot exceed 100 characters.";
    public static final String CATEGORY_ID_REQUIRED = "Category ID is required.";
    public static final String DESCRIPTION_REQUIRED = "Description is required.";
    public static final String DESCRIPTION_MAX_LENGTH = "Description cannot exceed 255 characters.";
    public static final String PRICE_REQUIRED = "Price is required.";
    public static final String PRICE_POSITIVE = "Price must be a positive integer greater than 0.";
    public static final String IMAGE_URL_REQUIRED = "Image URL is required.";
    public static final String RESTAURANT_ID_REQUIRED = "Restaurant ID is required.";
    public static final String EMPLOYEE_ID_REQUIRED = "Employee ID is required.";

    private ValidationConstants() {
        throw new IllegalStateException("Utility class");
    }
}
