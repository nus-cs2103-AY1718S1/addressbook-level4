package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.StringUtil.appendCrotchets;
import static seedu.address.logic.parser.CliSyntax.POSSIBLE_SORT_ARGUMENTS;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_ADDRESS_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_ADDRESS_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_ADDRESS_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_EMAIL_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_EMAIL_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_EMAIL_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_PHONE_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_PHONE_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_PHONE_DESCENDING;

import java.util.List;

import seedu.address.logic.parser.exceptions.ParseArgsException;

/**
 * Contains utility methods for sorting SortArguments in the various sort enabled classes.
 */
public class SortUtil {

    /* Sub-message that lists out all the message usage possibilities of the sort parameters */
    public static final String MESSAGE_SORT_USAGE =
            appendCrotchets(SORT_ARGUMENT_NAME_DEFAULT.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_PHONE_DEFAULT.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_EMAIL_DEFAULT.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_ADDRESS_DEFAULT.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_NAME_DESCENDING.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_PHONE_DESCENDING.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_EMAIL_DESCENDING.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_ADDRESS_DESCENDING.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_NAME_ASCENDING.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_PHONE_ASCENDING.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_EMAIL_ASCENDING.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_ADDRESS_ASCENDING.toString());

    /**
     * Orders and filters the keywords to the correct lists to be passed into the new find command.
     */
    public static void setupArguments(String[] keywords,
                                      List<String> dataKeywordList,
                                      List<SortArgument> sortArgumentList,
                                      String errorMessage)
            throws ParseArgsException {
        for (String keyword : keywords) {
            SortArgument sortArgument = new SortArgument(keyword);
            if (!POSSIBLE_SORT_ARGUMENTS.contains(sortArgument) && sortArgumentList.isEmpty()) {
                dataKeywordList.add(keyword);
            } else if (!POSSIBLE_SORT_ARGUMENTS.contains(sortArgument) && !sortArgumentList.isEmpty()) {
                throw new ParseArgsException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, errorMessage));
            } else {
                sortArgumentList.add(sortArgument);
            }
        }
    }
}
