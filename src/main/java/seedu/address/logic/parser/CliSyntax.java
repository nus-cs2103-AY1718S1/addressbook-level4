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
    public static final Prefix PREFIX_DATEADDED = new Prefix("t/");

    /* Prefix definitions for Event-related commands*/
    public static final Prefix PREFIX_EVENT_MEMBER = new Prefix("m/");
    public static final Prefix PREFIX_EVENT_NAME = new Prefix("n/");
    public static final Prefix PREFIX_EVENT_TIME = new Prefix("t/");
    public static final Prefix PREFIX_EVENT_DURATION = new Prefix("d/");
    public static final Prefix PREFIX_EVENT_INDEXES = new Prefix("I/");



}
