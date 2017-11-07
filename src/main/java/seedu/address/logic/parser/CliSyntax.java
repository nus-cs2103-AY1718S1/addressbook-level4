package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.List;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix String Definitions */
    public static final String PREFIX_EMPTY_STRING = "";
    public static final String PREFIX_NAME_STRING = "n/";
    public static final String PREFIX_PHONE_STRING = "p/";
    public static final String PREFIX_EMAIL_STRING = "e/";
    public static final String PREFIX_ADDRESS_STRING = "a/";
    public static final String PREFIX_TAG_STRING = "t/";
    public static final String PREFIX_REMARK_STRING = "r/";
    public static final String PREFIX_SHARE_STRING = "s/";
    public static final String PREFIX_AVATAR_STRING = "i/";

    /* Prefix definitions */
    public static final Prefix PREFIX_EMPTY = new Prefix(PREFIX_EMPTY_STRING);
    public static final Prefix PREFIX_NAME = new Prefix(PREFIX_NAME_STRING);
    public static final Prefix PREFIX_PHONE = new Prefix(PREFIX_PHONE_STRING);
    public static final Prefix PREFIX_EMAIL = new Prefix(PREFIX_EMAIL_STRING);
    public static final Prefix PREFIX_ADDRESS = new Prefix(PREFIX_ADDRESS_STRING);
    public static final Prefix PREFIX_TAG = new Prefix(PREFIX_TAG_STRING);
    public static final Prefix PREFIX_REMARK = new Prefix(PREFIX_REMARK_STRING);
    public static final Prefix PREFIX_SHARE = new Prefix(PREFIX_SHARE_STRING);
    public static final Prefix PREFIX_AVATAR = new Prefix(PREFIX_AVATAR_STRING);

    public static final List<Prefix> LIST_OF_PREFIXES =
            Arrays.asList(
                    PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                    PREFIX_TAG, PREFIX_REMARK, PREFIX_AVATAR, PREFIX_EMPTY);
}
