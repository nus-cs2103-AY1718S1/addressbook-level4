//@@author viviantan95
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;

import java.nio.file.Paths;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

/**
 * Parses input arguments and creates a new AddPhotoCommand object
 */
public class AddPhotoCommandParser implements Parser<AddPhotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPhotoCommand
     * and returns an AddPhotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public AddPhotoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PHOTO);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));
        }
        Person toAddPhotoPerson = new Person(SampleDataUtil.getSamplePersons()[0]);

        try {
            ParserUtil.parsePhoto(argMultimap.getValue(PREFIX_PHOTO)).ifPresent(toAddPhotoPerson::setPhoto);
        } catch (IllegalValueException e) {
            throw new ParseException(e.getMessage(), e);
        }

        if (toAddPhotoPerson.getPhoto().toString().equals("file://"
                + Paths.get("src/main/resources/images/defaultPhoto.png").toAbsolutePath().toUri().getPath())) {
            throw new ParseException(AddPhotoCommand.MESSAGE_ADDPHOTO_UNSUCCESS);
        }

        return new AddPhotoCommand(index, toAddPhotoPerson.getPhoto());
    }
}
