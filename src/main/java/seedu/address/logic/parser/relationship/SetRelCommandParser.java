//@@author huiyiiih
package seedu.address.logic.parser.relationship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_RELATIONSHIP;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.relationship.SetRelCommand.EditPerson;
import seedu.address.logic.commands.relationship.SetRelCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.relationship.Relationship;

/**
 * Parses all input arguments and create a new AddRelCommand object
 */
public class SetRelCommandParser implements Parser<SetRelCommand> {

    public static final String ONE_RELATIONSHIP_ALLOWED = "Only one relationship allowed when adding or deleting "
        + "relationship between two persons.";
    public static final String NULL_RELATION_INPUT = "Relationship entered should not be empty.";
    public static final String SAME_INDEX_ERROR = "Index of the two persons must be different.";
    /**
     * Parses the given {@code String} of arguments in the context of the SetRelCommand
     * and returns an SetRelCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetRelCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ADD_RELATIONSHIP,
            PREFIX_DELETE_RELATIONSHIP, PREFIX_CLEAR_RELATIONSHIP);
        Index indexOne;
        Index indexTwo;
        boolean addPrefixPresent = false;
        if (!areAnyPrefixesPresent(argMultimap, PREFIX_ADD_RELATIONSHIP, PREFIX_DELETE_RELATIONSHIP,
            PREFIX_CLEAR_RELATIONSHIP)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRelCommand.MESSAGE_USAGE));
        }
        try {
            indexOne = ParserUtil.parseIndex(argMultimap.getPreamble().split("\\s+")[0]);
            indexTwo = ParserUtil.parseIndex(argMultimap.getPreamble().split("\\s+")[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRelCommand.MESSAGE_USAGE));
        }
        if (indexOne.equals(indexTwo)) {
            throw new ParseException(SAME_INDEX_ERROR);
        }
        if (!argMultimap.containsOnePrefix(PREFIX_ADD_RELATIONSHIP)
            || !argMultimap.containsOnePrefix(PREFIX_DELETE_RELATIONSHIP)) {
            throw new ParseException(String.format(ONE_RELATIONSHIP_ALLOWED, SetRelCommand.MESSAGE_USAGE));
        }
        if (areAnyPrefixesPresent(argMultimap, PREFIX_ADD_RELATIONSHIP)) {
            addPrefixPresent = true;
            String addRelString = argMultimap.getValue(PREFIX_ADD_RELATIONSHIP).get();
            requireNonNull(addRelString);
            if (addRelString.length() == 0) {
                throw new ParseException(NULL_RELATION_INPUT);
            }
        }
        if (areAnyPrefixesPresent(argMultimap, PREFIX_DELETE_RELATIONSHIP)) {
            String addRelString = argMultimap.getValue(PREFIX_DELETE_RELATIONSHIP).get();
            requireNonNull(addRelString);
            if (addRelString.length() == 0) {
                throw new ParseException(NULL_RELATION_INPUT);
            }
        }
        EditPerson editPerson = new EditPerson();
        try {
            parseRelForEdit(argMultimap.getAllValues(PREFIX_ADD_RELATIONSHIP)).ifPresent
                (editPerson::setToAdd);
            parseRelForEdit(argMultimap.getAllValues(PREFIX_DELETE_RELATIONSHIP)).ifPresent
                (editPerson::setToDelete);
            editPerson.setClearRels(argMultimap.containsPrefix(PREFIX_CLEAR_RELATIONSHIP));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new SetRelCommand(indexOne, indexTwo, editPerson, addPrefixPresent);
    }
    /**
     * Parses {@code Collection<String> relationships} into a {@code Set<Relationship>}
     * if {@code relationships} is non-empty.
     * If {@code relationship} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Relationship>} containing zero relationships.
     */
    private Optional<Set<Relationship>> parseRelForEdit(Collection<String> rel) throws IllegalValueException {
        assert rel != null;
        if (rel.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> relSet = rel.size() == 1 && rel.contains("") ? Collections.emptySet() : rel;
        return Optional.of(ParserUtil.parseRels(relSet));
    }

    /**
     * Checks if any of the prefixes are present
     * return true if the certain prefix is present and false if the certain prefix is not present
     * @param argumentMultimap
     * @param prefixes
     * @return true || false
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
//@@author

