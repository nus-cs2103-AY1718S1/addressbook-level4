package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix name strings */
    public static final String PREFIX_NAME_STRING = "n/";
    public static final String PREFIX_PHONE_STRING = "p/";
    public static final String PREFIX_EMAIL_STRING = "e/";
    public static final String PREFIX_ADDRESS_STRING = "a/";
    public static final String PREFIX_BIRTHDAY_STRING = "b/";
    public static final String PREFIX_FACEBOOK_STRING = "f/";
    public static final String PREFIX_TAG_STRING = "t/";
    public static final String PREFIX_REMARK_STRING = "r/";
    public static final String PREFIX_USERNAME_STRING = "usr/";
    public static final String PREFIX_PASSWORD_STRING = "pwd/";


    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix(PREFIX_NAME_STRING);
    public static final Prefix PREFIX_PHONE = new Prefix(PREFIX_PHONE_STRING);
    public static final Prefix PREFIX_EMAIL = new Prefix(PREFIX_EMAIL_STRING);
    public static final Prefix PREFIX_ADDRESS = new Prefix(PREFIX_ADDRESS_STRING);
    public static final Prefix PREFIX_BIRTHDAY = new Prefix(PREFIX_BIRTHDAY_STRING);
    public static final Prefix PREFIX_FACEBOOK = new Prefix(PREFIX_FACEBOOK_STRING);
    public static final Prefix PREFIX_TAG = new Prefix(PREFIX_TAG_STRING);
    public static final Prefix PREFIX_REMARK = new Prefix(PREFIX_REMARK_STRING);
    public static final Prefix PREFIX_NAME_FOR_SORTING = new Prefix("name");
    public static final Prefix PREFIX_PHONE_FOR_SORTING = new Prefix ("phone");
    public static final Prefix PREFIX_USERNAME = new Prefix(PREFIX_USERNAME_STRING);
    public static final Prefix PREFIX_PASSWORD = new Prefix(PREFIX_PASSWORD_STRING);

}
