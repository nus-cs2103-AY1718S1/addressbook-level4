package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_GROUP = new Prefix("g/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    //@@author syy94
    public static final Prefix PREFIX_ADD_TAG = new Prefix("+t/");
    public static final Prefix PREFIX_REMOVE_TAG = new Prefix("-t/");
    public static final Prefix PREFIX_CUSTOM_FIELD = new Prefix("c/");
    public static final Prefix PREFIX_CLEAR_TAG = new Prefix("clearTag/");
    public static final Prefix PREFIX_PASS = new Prefix("pwd/");
    public static final Prefix PREFIX_NEW_PASS = new Prefix("new/");
    public static final Prefix PREFIX_CLEAR_PASS = new Prefix("clearPwd/");
    //@@author
}
