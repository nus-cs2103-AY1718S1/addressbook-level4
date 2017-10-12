package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DetagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parse the given input and creates a DetagCommand object
 */
public class DetagCommandParser implements Parser<DetagCommand> {

    public static final String INDEX_SEPARATOR_REGEX = ",";

    /**
     * Parse the given {@code String} of arguments in the context of the DetagCommand
     * @param userInput
     * @return DetagCommand object for execution
     * @throws ParseException
     */
    @Override
    public DetagCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_TAG);

        Index[] parsedIndices;
        Set<Tag> tagList;
        Tag tag;

        try {
            String[] splitIndices = splitIndices(argMultimap);
            int numberOfIndices = splitIndices.length;
            parsedIndices = new Index[numberOfIndices];
            for (int i = 0; i < numberOfIndices; i++) {
                parsedIndices[i] = ParserUtil.parseIndex(splitIndices[i]);
            }
            tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            tag = tagList.iterator().next();
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DetagCommand.MESSAGE_USAGE));
        }

        return new DetagCommand(parsedIndices, tag);
    }

    private String[] splitIndices(ArgumentMultimap argMultimap) {
        return argMultimap.getPreamble().split(INDEX_SEPARATOR_REGEX);
    }
}
