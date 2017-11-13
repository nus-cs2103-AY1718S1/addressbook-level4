package seedu.address.logic.parser;

//@@author Linus
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE_URL;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddAvatarCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

/**
 * Parses input arguments and creates a new AddAvatarCommand object
 */
public class AddAvatarCommandParser implements Parser<AddAvatarCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddAvatarCommand
     * and returns an AddAvatarCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public AddAvatarCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_IMAGE_URL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAvatarCommand.MESSAGE_USAGE));
        }

        Person updatedPerson = new Person(SampleDataUtil.getSamplePersons()[0]);

        try {
            ParserUtil.parseImageUrl(argMultimap.getValue(PREFIX_IMAGE_URL)).ifPresent(updatedPerson::setAvatarPic);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new AddAvatarCommand(index, updatedPerson.getAvatarPic());
    }
}
