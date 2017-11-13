package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    //@@author April0616
    public static final String VALID_REGEX_DELETE_ONE_PERSON = "-?\\d+";

    public static final String DELETE_MULTIPLE_PERSON_COMMA_VALIDATION_REGEX =
            "(-?\\d\\s*?,\\s*?-?\\d?)+";

    public static final String DELETE_MULTIPLE_PERSON_WHITESPACE_VALIDATION_REGEX =
            "(-?\\d\\s*?-?\\d?)+";
    //@@author nbriannl
    /**
     * Parses the arguments of the delete command.
     * @return an DeleteCommand object for execution
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        String preamble = argMultimap.getPreamble();
        if (preamble.equals("")) {
            // there exists 't/'
            DeleteCommand deleteCommandForTag = parseForTags(argMultimap);
            if (deleteCommandForTag != null) {
                return deleteCommandForTag;
            }
        } else {
            DeleteCommand deleteCommandForPerson = parseForPersonIndexes(args, preamble);
            if (deleteCommandForPerson != null) {
                return deleteCommandForPerson;
            }
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    //@@author nbriannl
    /**
     * Parses the {@code Tags} contained within {@code argMultimap} to return
     * a DeleteCommand object that executes a delete for Tags.
     * @throws ParseException if the values mapped as a tag does not conform as a valid tag
     * @see #parse(String)
     */
    private DeleteCommand parseForTags (ArgumentMultimap argMultimap) throws ParseException {
        try {
            if (arePrefixesPresent(argMultimap, PREFIX_TAG)) {
                Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
                return new DeleteCommand(tagList);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        return null;
    }

    //@@author April0616
    /**
     * Parses the indexes contained within {@code argMultimap} to return
     * a DeleteCommand object that executes a delete for Persons.
     * @throws ParseException if the values mapped as the persons do not conform as valid person indexes
     * @see #parse(String)
     */
    private DeleteCommand parseForPersonIndexes (String args, String preamble) throws ParseException {
        if (preamble.matches(VALID_REGEX_DELETE_ONE_PERSON)) {
            // code block for delete for a person
            try {
                Index index = ParserUtil.parseIndex(args);
                return new DeleteCommand(index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        } else if (preamble.matches(DELETE_MULTIPLE_PERSON_COMMA_VALIDATION_REGEX)) {
            //code block for delete multiple persons, input string separated by comma
            try {
                ArrayList<Index> deletePersons = ParserUtil.parseIndexes(args, ",");
                return new DeleteCommand(deletePersons);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        } else if (preamble.matches(DELETE_MULTIPLE_PERSON_WHITESPACE_VALIDATION_REGEX)) {
            //code block for delete multiple persons, input indexes separated by whitespace
            try {
                ArrayList<Index> deletePersons = ParserUtil.parseIndexes(args, " ");
                return new DeleteCommand(deletePersons);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    //@@author nbriannl-reused
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
