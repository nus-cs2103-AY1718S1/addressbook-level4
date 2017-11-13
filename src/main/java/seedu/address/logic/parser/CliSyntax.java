package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL_ADDRESS = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_BIRTHDATE = new Prefix("b/");
    public static final Prefix PREFIX_SORT = new Prefix("s/");
    public static final Prefix PREFIX_PHOTO = new Prefix("dp/");
    public static final Prefix PREFIX_EMAIL_MESSAGE = new Prefix("em/");
    public static final Prefix PREFIX_EMAIL_SUBJECT = new Prefix("es/");
    public static final Prefix PREFIX_EMAIL_LOGIN = new Prefix("el/");
    public static final Prefix PREFIX_EMAIL_TASK = new Prefix("et/");
    public static final Prefix PREFIX_USERID = new Prefix("insta/");

    /* Sort Options */
    public static final String SORT_ADDRESS = "address";
    public static final String SORT_EMAIL = "email";
    public static final String SORT_NAME = "name";
    public static final String SORT_TAG = "tag";
}
