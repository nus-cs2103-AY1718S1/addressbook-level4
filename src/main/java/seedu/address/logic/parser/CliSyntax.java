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
    public static final Prefix PREFIX_COLOR = new Prefix("c/");
    public static final Prefix PREFIX_DOB = new Prefix("b/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_IMAGE = new Prefix("f/");
    public static final Prefix PREFIX_FONT_SIZE = new Prefix("");
    public static final Prefix PREFIX_USERNAME = new Prefix("u/");
    public static final Prefix PREFIX_REMINDER_DUE_DATE = new Prefix("d/");
    public static final Prefix PREFIX_REMINDER_PRIORITY = new Prefix("p/");
    public static final Prefix PREFIX_REMINDER_DETAILS = new Prefix("g/");
    public static final Prefix PREFIX_SUBJECT = new Prefix("s/");
}
