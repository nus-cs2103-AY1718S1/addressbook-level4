package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

public class TagCommandParser implements Parser<TagCommand> {

    public static final String SEPARATOR_REGEX = ",";

    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Index[] parsedIndices;
        Set<Tag> tagList;

        try {
            String[] splitIndices = splitIndices(argMultimap);
            int numberOfIndices = splitIndices.length;
            parsedIndices = new Index[numberOfIndices];
            for (int i = 0; i < numberOfIndices; i++) {
                parsedIndices[i] = ParserUtil.parseIndex(splitIndices[i]);
            }
            tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }
        return new TagCommand(parsedIndices, tagList);
    }

    private String[] splitIndices(ArgumentMultimap argMultimap) {
        return argMultimap.getPreamble().split(SEPARATOR_REGEX);
    }
}
