package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashMap;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.WebCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author hansiang93
/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class WebCommandParser implements Parser<WebCommand> {
    public static final HashMap<String, String> WEBSITES_MAP;

    static {
        WEBSITES_MAP = new HashMap<String, String>();
        WEBSITES_MAP.put("facebook", "facebook");
        WEBSITES_MAP.put("maps", "mapsView");
        WEBSITES_MAP.put("search", "searchView");
        WEBSITES_MAP.put("instagram", "instagram");
        WEBSITES_MAP.put("twitter", "twitter");
        WEBSITES_MAP.put("personal", "others");
    }

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public WebCommand parse(String args) throws ParseException {

        try {
            String websiteName = ParserUtil.parseWebname(args).trim().toLowerCase();
            String websiteToShow = WEBSITES_MAP.get(websiteName);
            if (websiteToShow == null) {
                throw new IllegalValueException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            return new WebCommand(websiteToShow);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, WebCommand.MESSAGE_USAGE));
        }
    }
}
