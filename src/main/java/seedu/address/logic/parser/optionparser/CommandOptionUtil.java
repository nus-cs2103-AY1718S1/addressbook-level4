package seedu.address.logic.parser.optionparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains utility methods used for parsing mode prefix and mode arguments.
 */
public class CommandOptionUtil {

    // All modes start with prefix "-"
    public static final String PREFIX_OPTION_INDICATOR = "-";
    // No mode means that prefix is empty
    public static final String DEFAULT_OPTION_PREFIX = "";

    private static final Pattern OPTION_PATTERN = Pattern.compile("^(-\\w+)");

    public static String getOptionPrefix(String args) {
        try {
            Matcher matcher = OPTION_PATTERN.matcher(args);
            if (matcher.find()) {
                return matcher.group(0);
            }
        } catch (Exception e) {
        }
        return DEFAULT_OPTION_PREFIX;
    }

    public static String getOptionArgs(String optionPrefix, String args) {
        return args.replace(optionPrefix, "").trim();
    }

}
