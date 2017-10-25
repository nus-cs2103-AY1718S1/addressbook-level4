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
    public static final Prefix PREFIX_DELTAG = new Prefix("dt/");
    public static final Prefix PREFIX_DOB = new Prefix("d/");
    public static final Prefix PREFIX_OWNER = new Prefix("o/");
    public static final Prefix PREFIX_INSURED = new Prefix("i/");
    public static final Prefix PREFIX_BENEFICIARY = new Prefix("b/");
    public static final Prefix PREFIX_PREMIUM = new Prefix("pr/");
    public static final Prefix PREFIX_CONTRACT = new Prefix("c/");
    public static final Prefix PREFIX_SIGNING_DATE = new Prefix("sd/");
    public static final Prefix PREFIX_EXPIRY_DATE = new Prefix("ed/");
    public static final Prefix PREFIX_GENDER = new Prefix("g/");
}
