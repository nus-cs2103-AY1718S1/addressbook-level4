package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashMap;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.WebCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class WebCommandParser implements Parser<WebCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public WebCommand parse(String args) throws ParseException {
        HashMap<String, String> websitesMap = new HashMap<String, String>();

        websitesMap.put("facebook", "facebook");
        websitesMap.put("maps", "mapsView");
        websitesMap.put("search", "searchView");
        websitesMap.put("insta", "instagram");
        websitesMap.put("linkedin", "linkedin");
        websitesMap.put("personal", "others");

        try {
            String websiteName = ParserUtil.parseWebname(args).trim();
            return new WebCommand(websitesMap.get(websiteName));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, WebCommand.MESSAGE_USAGE));
        }
    }
}
