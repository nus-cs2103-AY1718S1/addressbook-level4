package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.ResponseList;
import facebook4j.User;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FacebookAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favorite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.social.SocialInfo;
import seedu.address.model.tag.Tag;

//@@author alexfoodw
/**
 * Parses the given {@code String} of arguments in the context of the FacebookAddCommand
 * and returns an FacebookAddCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class FacebookAddCommandParser implements Parser<FacebookAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FacebookAddCommand
     * and returns an FacebookAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FacebookAddCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FacebookAddCommand.MESSAGE_USAGE));
        }

        return new FacebookAddCommand(trimmedArgs);
    }

}
