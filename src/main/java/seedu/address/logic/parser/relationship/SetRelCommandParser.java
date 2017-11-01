//@@author huiyiiih
package seedu.address.logic.parser.relationship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPTY_RELATIONSHIP;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.relationship.SetRelCommand.EditPerson;
import seedu.address.logic.commands.relationship.SetRelCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.relationship.Relationship;

/**
 * Parses all input arguments and create a new AddRelCommand object
 */
public class SetRelCommandParser implements Parser<SetRelCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddRelCommand
     * and returns an AddRelCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetRelCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ADD_RELATIONSHIP,
            PREFIX_DELETE_RELATIONSHIP, PREFIX_EMPTY_RELATIONSHIP);
        Index indexOne;
        Index indexTwo;
        String value;
        String[] indexes;

        value = argMultimap.getPreamble();
        try {
            indexes = value.split("\\s+");
            indexOne = ParserUtil.parseIndex(indexes[0]);
            indexTwo = ParserUtil.parseIndex(indexes[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRelCommand.MESSAGE_USAGE));
        }
        EditPerson editPerson = new EditPerson();
        try {
            parseRelForEdit(argMultimap.getAllValues(PREFIX_ADD_RELATIONSHIP)).ifPresent
                (editPerson::setToAdd);
            parseRelForEdit(argMultimap.getAllValues(PREFIX_DELETE_RELATIONSHIP)).ifPresent
                (editPerson::setToRemove);
            editPerson.setClearRels(argMultimap.containsPrefix(PREFIX_EMPTY_RELATIONSHIP));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        if (!editPerson.isAnyFieldEdited()) {
            throw new ParseException(SetRelCommand.MESSAGE_NOT_EDITED);
        }

        return new SetRelCommand(indexOne, indexTwo, editPerson);
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
        return Optional.of(ParserUtil.parseRel(relSet));
    }
}
//@@author

