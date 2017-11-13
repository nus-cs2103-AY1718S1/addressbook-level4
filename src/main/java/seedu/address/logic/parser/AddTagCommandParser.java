package seedu.address.logic.parser;

//@@author wangyiming1019
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class AddTagCommandParser extends AddCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns a AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        try {
            String tagName = argMultimap.getValue(PREFIX_TAG).orElse("");
            Tag toAdd = new Tag(tagName);
            String indexes = argMultimap.getPreamble();

            if (indexes.trim().isEmpty()) {
                return new AddTagCommand(toAdd);
            }
            ArrayList<Index> indexList = toArrayList(indexes);

            return new AddTagCommand(toAdd, indexList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns an ArrayList of the indexes in the given {@code String}
     */
    protected static ArrayList<Index> toArrayList(String indexes) throws IllegalValueException {
        ArrayList<Index> indexList = new ArrayList<Index>();
        String[] indexArray = indexes.split(" ");
        for (String s: indexArray) {
            if (!s.isEmpty()) {
                indexList.add(ParserUtil.parseIndex(s));
            }
        }
        return indexList;
    }
}
