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
import seedu.address.logic.commands.TagRemoveCommand.TagRemoveDescriptor;
import seedu.address.logic.commands.TagRemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

//@@author ZhangH795

/**
 * Parses input arguments and creates a new TagRemoveCommand object
 */
public class TagRemoveCommandParser implements Parser<TagRemoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagRemoveCommand
     * and returns an TagRemoveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagRemoveCommand parse(String args) throws ParseException {
        requireNonNull(args);

        int defaultLastNumberIndex = -1;
        int arrayIndexOffset = 1;
        int nextArrayIndex = 1;

        String newTag = "";
        int lastIndex = defaultLastNumberIndex;
        ArrayList<Index> index = new ArrayList<>();
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagRemoveCommand.MESSAGE_USAGE));
        }

        String[] argsArray = args.trim().split(" ");
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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagRemoveCommand.MESSAGE_USAGE));
        }
        if (lastIndex == argsArray.length - arrayIndexOffset) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagRemoveCommand.MESSAGE_USAGE));
        }
        for (int i = lastIndex + nextArrayIndex; i < argsArray.length; i++) {
            newTag = newTag.concat(" " + argsArray[i]);
        }

        HashSet<String> tagSet = new HashSet<>();
        newTag = newTag.trim();
        tagSet.add(newTag);
        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        try {
            parseTagsForEdit(tagSet).ifPresent(tagRemoveDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        if (!tagRemoveDescriptor.isAnyFieldEdited()) {
            throw new ParseException(TagRemoveCommand.MESSAGE_NOT_EDITED);
        }

        return new TagRemoveCommand(index, tagRemoveDescriptor);
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
