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
    public static final Prefix PREFIX_BIRTHDAY = new Prefix("b/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_SOCIAL = new Prefix("s/");
    public static final Prefix PREFIX_MAIL_RECEPIENT = new Prefix("to/");
    public static final Prefix PREFIX_MAIL_MESSAGE = new Prefix("message/");
    public static final Prefix PREFIX_MAIL_TITLE = new Prefix("title/");
    public static final Prefix PREFIX_SMS_TEXT = new Prefix("text/");
    public static final Prefix PREFIX_ACCOUNT_SID = new Prefix("id/");
    public static final Prefix PREFIX_AUTH_TOKEN = new Prefix("auth/");
    public static final Prefix PREFIX_SENDING_NUMBER = new Prefix("num/");

}
