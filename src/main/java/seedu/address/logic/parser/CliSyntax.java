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
    public static final Prefix PREFIX_HEADER = new Prefix("h/");
    public static final Prefix PREFIX_DESC = new Prefix("de/");
    public static final Prefix PREFIX_EVENT_DATE = new Prefix("d/");
    public static final Prefix PREFIX_IMAGE_URL = new Prefix("u/");
    public static final Prefix PREFIX_EMAIL_BODY = new Prefix("body/");
    public static final Prefix PREFIX_EMAIL_SUBJECT = new Prefix("subject/");
    public static final Prefix PREFIX_EMAIL_TO = new Prefix("to/");
    public static final Prefix PREFIX_REMOVE_INDEX = new Prefix("rm/");
}
