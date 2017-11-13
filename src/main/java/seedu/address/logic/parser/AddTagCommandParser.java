package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

//@@author nadhira15
/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns an AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        try {
            requireNonNull(args);
        } catch (NullPointerException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        String trimmedArgs = args.trim();
        Index index;

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        String[] arguments = trimmedArgs.split("\\s+");
        List<String> argsList = Arrays.asList(arguments);

        // when there's only 1 arguments (can be index or anything), throw exception
        if (arguments.length == 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(arguments[0]);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        List<String> tagList = argsList.subList(1, argsList.size());
        Set<Tag> tagToAdd;

        try {
            tagToAdd = parseTagToAdd(tagList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new AddTagCommand(index, tagToAdd);
    }

    /**
     * Parses the given {@code Tag} in the context of the AddTagCommand
     * and returns a set of tag object for execution.
     * @throws IllegalValueException if the user input does not conform the expected format
     */
    public Set<Tag> parseTagToAdd(List<String> tagToAdd) throws IllegalValueException {
        assert tagToAdd != null;
        return ParserUtil.parseTags(tagToAdd);
    }

}
