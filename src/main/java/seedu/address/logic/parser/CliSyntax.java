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
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_SEARCHCOUNT = new Prefix("Search Count:");

    //@@author Sri-vatsa
    public static final Prefix PREFIX_DATE = new Prefix("on ");
    public static final Prefix PREFIX_TIME = new Prefix("from ");
    public static final Prefix PREFIX_LOCATION = new Prefix("at ");
    public static final Prefix PREFIX_NOTES = new Prefix("about ");
    public static final Prefix PREFIX_PERSON = new Prefix("with ");

}
