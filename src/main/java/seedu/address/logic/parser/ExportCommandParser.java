//@@author quangtdn
package seedu.address.logic.parser;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Created by nguyenminhquang on 11/7/17.
 */


/**
 * Parses input arguments and creates a new FindPhoneCommand object
 */
public class ExportCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the FindPhoneCommand
     * and returns an FindPhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ExportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (args.equals("") || args.isEmpty()) {
            return new ExportCommand("exportFile.txt");
        } else {
            return new ExportCommand(args);
        }
    }
}
//@@author
