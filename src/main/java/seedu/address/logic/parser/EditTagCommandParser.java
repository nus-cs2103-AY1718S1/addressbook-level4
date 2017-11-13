package seedu.address.logic.parser;
//@@author Esilocke

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.EditTagCommand.MESSAGE_DUPLICATE_TAGS;
import static seedu.address.logic.commands.EditTagCommand.MESSAGE_INSUFFICIENT_ARGS;
import static seedu.address.logic.commands.EditTagCommand.MESSAGE_INVALID_TAG_NAME;

import java.util.ArrayList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/** Parses input arguments and creates a new EditTagCommand object */
public class EditTagCommandParser implements Parser<EditTagCommand> {
    public static final String EDITTAG_VALIDATION_REGEX = "[\\p{Alnum}\\s]+[\\p{Alnum}]+";
    public static final int EXPECTED_NUMBER_OF_ARGS = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the EditTagCommand
     * and returns an EditTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArrayList<Tag> tags;
        String trimmed = args.trim();
        if (!args.matches(EDITTAG_VALIDATION_REGEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE));
        }

        try {
            tags = readTags(trimmed);
        } catch (IllegalValueException ive) {
            throw new ParseException(MESSAGE_INVALID_TAG_NAME);
        }

        if (tags.size() != EXPECTED_NUMBER_OF_ARGS) {
            throw new ParseException(MESSAGE_INSUFFICIENT_ARGS);
        }

        Tag toChange = tags.get(0);
        Tag newTag = tags.get(1);
        if (toChange.equals(newTag)) {
            throw new ParseException(MESSAGE_DUPLICATE_TAGS);
        }

        return new EditTagCommand(toChange, newTag);
    }
    /** Atempts to read the string and parse it into a Tag set*/
    private ArrayList<Tag> readTags(String args) throws IllegalValueException {
        String[] splittedArgs = args.split("\\s+");
        ArrayList<Tag> tagList = new ArrayList<>();
        for (String s : splittedArgs) {
            Tag newTag = new Tag(s);
            tagList.add(newTag);
        }
        return tagList;
    }
}
