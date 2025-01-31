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


    public static final String NOT_OWNER_MESSAGE = "You are not the owner of this restaurant.";
    public static final String DISH_NAME_REQUIRED = "Dish name is required.";
    public static final String PRICE_REQUIRED = "Price must be a positive integer greater than 0.";
    public static final String DESCRIPTION_REQUIRED = "Description is required.";
    public static final String IMAGE_URL_REQUIRED = "Image URL is required.";
    public static final String CATEGORY_REQUIRED = "Category ID is required.";
    public static final String DISH_NOT_FOUND = "Dish not found.";

    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }
}
