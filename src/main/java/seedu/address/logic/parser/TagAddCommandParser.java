package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TagAddCommand;
import seedu.address.logic.commands.TagAddCommand.TagAddDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

//@@author ZhangH795

/**
 * Parses input arguments and creates a new TagAddCommand object
 */
public class TagAddCommandParser implements Parser<TagAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagAddCommand
     * and returns an TagAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagAddCommand parse(String args) throws ParseException {
        requireNonNull(args);

        int defaultLastNumberIndex = -1;
        int arrayIndexOffset = 1;
        int nextArrayIndex = 1;
        int completeNumOfArgs = 2;
        String newTag = "";

        int lastIndex = defaultLastNumberIndex;
        String[] argsArray;
        ArrayList<Index> index = new ArrayList<>();
        if (args.isEmpty() || (argsArray = args.trim().split(" ")).length < completeNumOfArgs) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAddCommand.MESSAGE_USAGE));
        }
        try {
            for (int i = 0; i < argsArray.length; i++) {
                if (argsArray[i].matches("\\d+")) {
                    index.add(ParserUtil.parseIndex(argsArray[i]));
                    lastIndex = i;
                } else {
                    break;
                }
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAddCommand.MESSAGE_USAGE));
        }

        if (lastIndex == defaultLastNumberIndex || lastIndex == (argsArray.length - arrayIndexOffset)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAddCommand.MESSAGE_USAGE));
        }
        HashSet<String> tagSet = new HashSet<>();
        for (int i = lastIndex + nextArrayIndex; i < argsArray.length; i++) {
            newTag = newTag.concat(argsArray[i] + " ");
        }
        newTag = newTag.trim();
        tagSet.add(newTag);
        TagAddDescriptor tagAddDescriptor = new TagAddDescriptor();
        try {
            parseTagsForEdit(tagSet).ifPresent(tagAddDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!tagAddDescriptor.isAnyFieldEdited()) {
            throw new ParseException(TagAddCommand.MESSAGE_NOT_EDITED);
        }

        return new TagAddCommand(index, tagAddDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        int singleElementArraySize = 1;
        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == singleElementArraySize && tags.contains("")
                    ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
