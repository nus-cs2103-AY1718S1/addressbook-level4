package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for Person*/
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    //@@author sebtsh
    public static final Prefix PREFIX_COMPANY = new Prefix("c/");
    public static final Prefix PREFIX_POSITION = new Prefix("po/");
    public static final Prefix PREFIX_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("pr/");
    public static final Prefix PREFIX_NOTE = new Prefix("no/");
    //@@author
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_PHOTO = new Prefix("ph/");
    public static final Prefix PREFIX_RELATIONSHIP = new Prefix("r/");

    /* Prefix definitions for Event*/
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_TIMESLOT = new Prefix("t/");
    /* Prefix definitions for Relationship*/
    public static final Prefix PREFIX_ADD_RELATIONSHIP = new Prefix("ar/");
    public static final Prefix PREFIX_DELETE_RELATIONSHIP = new Prefix("dr/");
    public static final Prefix PREFIX_EMPTY_RELATIONSHIP = new Prefix("er/");
}
