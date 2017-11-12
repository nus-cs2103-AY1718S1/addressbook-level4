package seedu.address.logic.parser.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.person.AddAvatarCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.util.ParserUtil;
import seedu.address.model.person.Avatar;

//@@author yunpengn
/**
 * Parses input arguments and creates a new {@link AddAvatarCommand} object.
 */
public class AddAvatarCommandParser implements Parser<AddAvatarCommand> {
    /* Regular expressions for validation. ArgumentMultiMap not applicable here. */
    private static final Pattern COMMAND_FORMAT = Pattern.compile("(?<index>\\S+)(?<url>.+)");

    /**
     * Parses the given {@code String} of arguments in the context of the {@link AddAvatarCommand}
     * and returns an {@link AddAvatarCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddAvatarCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Defensive programming here to use trim again.
        final Matcher matcher = COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAvatarCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(matcher.group("index").trim());
            Avatar avatar = new Avatar(matcher.group("url").trim());
            return new AddAvatarCommand(index, avatar);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ive.getMessage()));
        }
    }
}
