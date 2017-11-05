package seedu.address.logic.parser.util;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {
    /* Prefix definitions for pre-loaded properties. */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_DATE_TIME = new Prefix("dt/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMINDER = new Prefix("r/");

    //@@author yunpengn
    /* Prefix definitions for adding a new customize property. */
    public static final Prefix PREFIX_SHORT_NAME = new Prefix("s/");
    public static final Prefix PREFIX_FULL_NAME = new Prefix("f/");
    public static final Prefix PREFIX_MESSAGE = new Prefix("m/");
    public static final Prefix PREFIX_REGEX = new Prefix("r/");
    //@@author
}
