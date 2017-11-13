package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE_URL;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UpdateProfilePicCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProfilePic;
import seedu.address.model.util.SampleDataUtil;

//@@author soonwj
/**
 * Parses input arguments and creates a new UpdateProfilePicCommand object
 */
public class UpdateProfilePicCommandParser implements Parser<UpdateProfilePicCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UpdateProfilePicCommand
     * and returns an UpdateProfilePicCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public UpdateProfilePicCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_IMAGE_URL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdateProfilePicCommand.MESSAGE_USAGE));
        }

        Person updatedPerson = new Person(SampleDataUtil.getSamplePersons()[0]);

        try {
            ParserUtil.parseImageUrl(argMultimap.getValue(PREFIX_IMAGE_URL)).ifPresent(updatedPerson::setProfilePic);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (updatedPerson.getProfilePic().toString().compareTo(ProfilePic.DEFAULT_URL) == 0) {
            throw new ParseException(UpdateProfilePicCommand.MESSAGE_NOT_UPDATED);
        }

        return new UpdateProfilePicCommand(index, updatedPerson.getProfilePic());
    }
}
