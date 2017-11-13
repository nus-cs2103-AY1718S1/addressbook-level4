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
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    //@@author zhoukai07
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_ADD_TAG = new Prefix("+t/");
    public static final Prefix PREFIX_CLEAR_TAG = new Prefix("clearTag");
    public static final Prefix PREFIX_REM_TAG = new Prefix("-t/");
    //@@author
    public static final Prefix PREFIX_WEB_LINK = new Prefix("w/");

    //@@author bladerail
    public static final String ARG_DEFAULT = "default";
    public static final String ARG_NAME = "name";
    public static final String ARG_NAME_ALIAS = "n";
    public static final String ARG_PHONE = "phone";
    public static final String ARG_PHONE_ALIAS = "p";
    public static final String ARG_EMAIL = "email";
    public static final String ARG_EMAIL_ALIAS = "e";
    public static final String ARG_ADDRESS = "address";
    public static final String ARG_ADDRESS_ALIAS = "a";
    public static final String ARG_REMARK = "remark";
    public static final String ARG_TAG = "tag";
    public static final String ARG_WEB_LINK = "weblink";
    //@@author bladerail
}
