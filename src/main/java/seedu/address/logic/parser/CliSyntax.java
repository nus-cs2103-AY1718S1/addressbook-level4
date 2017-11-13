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
    //@@author srishag
    public static final Prefix PREFIX_BIRTHDAY = new Prefix("b/");
    //@@author
    public static final Prefix PREFIX_FACEBOOKADDRESS = new Prefix("f/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    //@@author srishag
    public static final Prefix PREFIX_EMAIL_SUBJECT = new Prefix("es/");
    public static final Prefix PREFIX_EMAIL_BODY = new Prefix("eb/");
    //@@author
    public static final Prefix PREFIX_GOOGLEID = new Prefix("c/");
    //@@author srishag
    public static final Prefix PREFIX_HEADER = new Prefix("th/");
    public static final Prefix PREFIX_DESC = new Prefix("td/");
    public static final Prefix PREFIX_DEADLINE = new Prefix("tdl/");
    //@@author

}
