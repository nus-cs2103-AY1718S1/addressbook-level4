package seedu.address.logic.parser;

import java.util.HashMap;

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
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Prefix mappings */
    public static final HashMap<String, Prefix> PREFIX_MAPPING;
    static {
        PREFIX_MAPPING = new HashMap<>();
        PREFIX_MAPPING.put("n/", PREFIX_NAME);
        PREFIX_MAPPING.put("a/", PREFIX_ADDRESS);
        PREFIX_MAPPING.put("e/", PREFIX_EMAIL);
        PREFIX_MAPPING.put("p/", PREFIX_PHONE);
        PREFIX_MAPPING.put("t/", PREFIX_TAG);
    }

}
