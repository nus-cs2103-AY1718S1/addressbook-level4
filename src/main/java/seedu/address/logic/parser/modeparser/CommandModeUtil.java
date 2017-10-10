package seedu.address.logic.parser.modeparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains utility methods used for parsing mode prefix and mode arguments.
 */
public class CommandModeUtil {

    // All modes start with prefix "-"
    public static final String PREFIX_MODE_INDICATOR = "-";
    // No mode means that prefix is empty
    public static final String DEFAULT_MODE_PREFIX = "";

    private static final Pattern MODE_PATTERN = Pattern.compile("^(-\\w+)");

    public static String getModePrefix(String args) {
        try {
            Matcher matcher = MODE_PATTERN.matcher(args);
            if (matcher.find()) {
                return matcher.group(0);
            }
        } catch (Exception e) {
        }
        return DEFAULT_MODE_PREFIX;
    }

    public static String getModeArgs(String modePrefix, String args) {
        return args.replace(modePrefix, "").trim();
    }

}
