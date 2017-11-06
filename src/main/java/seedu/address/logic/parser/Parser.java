package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.SuggestibleParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface Parser<T extends Command> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws SuggestibleParseException if {@code userInput} does not conform to the
     * expected format and changes can be suggested.
     * @throws ParseException if {@code userInput} does not conform to the
     * expected format and changes cannot be suggested.
     */
    T parse(String userInput) throws SuggestibleParseException, ParseException;
}
