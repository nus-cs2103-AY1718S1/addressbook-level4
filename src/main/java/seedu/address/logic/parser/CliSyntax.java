package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_PERSON = new Prefix("person/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_TAG_FULL = new Prefix("tag/");
    public static final Prefix PREFIX_AVATAR = new Prefix("v/");

    public static final Prefix PREFIX_NAME_PRIVATE = new Prefix("pn/");
    public static final Prefix PREFIX_PHONE_PRIVATE = new Prefix("pp/");
    public static final Prefix PREFIX_EMAIL_PRIVATE = new Prefix("pe/");
    public static final Prefix PREFIX_ADDRESS_PRIVATE = new Prefix("pa/");
    public static final Prefix PREFIX_REMARK_PRIVATE = new Prefix("pr/");
    public static final Prefix PREFIX_TAG_PRIVATE = new Prefix("pt/");
    public static final Prefix PREFIX_AVATAR_PRIVATE = new Prefix("pv/");

    public static final Prefix PREFIX_TASK = new Prefix("task/");
    public static final Prefix PREFIX_DEADLINE = new Prefix("by/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("p/");
    public static final Prefix PREFIX_TARGET = new Prefix("to/");
    public static final Prefix PREFIX_FROM = new Prefix("from/");
    public static final Prefix PREFIX_STATE = new Prefix("done/");

    public static final Prefix PREFIX_NAVIGATE_FROM_PERSON = new Prefix("fp/");
    public static final Prefix PREFIX_NAVIGATE_FROM_TASK = new Prefix("ft/");
    public static final Prefix PREFIX_NAVIGATE_FROM_ADDRESS = new Prefix("fa/");
    public static final Prefix PREFIX_NAVIGATE_TO_PERSON = new Prefix("tp/");
    public static final Prefix PREFIX_NAVIGATE_TO_TASK = new Prefix("tt/");
    public static final Prefix PREFIX_NAVIGATE_TO_ADDRESS = new Prefix("ta/");

    public static final Prefix PREFIX_PASSWORD = new Prefix("pw/");
    public static final Prefix PREFIX_NEW_PASSWORD = new Prefix("np/");
    public static final Prefix PREFIX_CONFIRM_PASSWORD = new Prefix("cfp/");
}
