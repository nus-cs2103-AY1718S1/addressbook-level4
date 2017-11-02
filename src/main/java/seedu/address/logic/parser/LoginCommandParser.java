package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Password;
import seedu.address.logic.Username;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jelneo
/**
 * Parses input arguments and creates a new LoginCommand object
 */
public class LoginCommandParser implements Parser<LoginCommand> {

    public static final String ONE_OR_MORE_SPACES_REGEX = "\\s+";

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        try {
            String[] argsList = trimmedArgs.split(ONE_OR_MORE_SPACES_REGEX);

            // if incorrect number of arguments are supplied, throw ArrayOutOfBoundsException
            if (argsList.length != 2) {
                throw new ArrayIndexOutOfBoundsException();
            }
            Username username = ParserUtil.parseUsername(argsList[0]);
            Password password = ParserUtil.parsePassword(argsList[1]);
            return new LoginCommand(username, password);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        } catch (ArrayIndexOutOfBoundsException aiofoe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }
    }
}
