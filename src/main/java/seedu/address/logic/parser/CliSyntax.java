package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
    public static final Prefix PREFIX_CONTRACT_FILE_NAME = new Prefix("c/");
    public static final Prefix PREFIX_SIGNING_DATE = new Prefix("sd/");
    public static final Prefix PREFIX_EXPIRY_DATE = new Prefix("ed/");
    public static final Prefix PREFIX_GENDER = new Prefix("g/");
    //@@author Juxarius
    public static final Set<Prefix> PREFIXES_PERSON = new HashSet<>(Arrays.asList(
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_DOB, PREFIX_GENDER));
    public static final Set<Prefix> PREFIXES_INSURANCE = new LinkedHashSet<>(Arrays.asList(
            PREFIX_NAME, PREFIX_OWNER, PREFIX_INSURED, PREFIX_BENEFICIARY,
            PREFIX_PREMIUM, PREFIX_CONTRACT_FILE_NAME, PREFIX_SIGNING_DATE, PREFIX_EXPIRY_DATE));
}
