package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONFIDENCE_ESTIMATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddRelationshipCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.relationship.ConfidenceEstimate;
import seedu.address.model.relationship.RelationshipDirection;

//@@author wenmogu
/**
 * This is a argument parser for AddRelationshipCommand
 */
public class AddRelationshipCommandParser implements Parser<AddRelationshipCommand> {

    @Override
    public AddRelationshipCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_CONFIDENCE_ESTIMATE);

        List<String> listOfArgs = Arrays.asList(trimmedArgs.split(" "));

        if (listOfArgs.size() < 3) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE));
        }

        String firstIndexString = listOfArgs.get(0);
        String secondIndexString = listOfArgs.get(1);


        if (firstIndexString.equals(secondIndexString)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE));
        }

        if (firstIndexString.equals("0") || secondIndexString.equals("0")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, AddRelationshipCommand.MESSAGE_USAGE));
        }

        try {
            Index firstIndex = ParserUtil.parseIndex(listOfArgs.get(0));
            Index secondIndex = ParserUtil.parseIndex(listOfArgs.get(1));
            RelationshipDirection direction = ParserUtil.parseDirection(listOfArgs.get(2));

            Name name = ParserUtil.parseRelationshipName(argMultimap.getValue(PREFIX_NAME)).get();
            ConfidenceEstimate confidenceEstimate =
                    ParserUtil.parseConfidenceEstimate(argMultimap.getValue(PREFIX_CONFIDENCE_ESTIMATE)).get();
            return new AddRelationshipCommand(firstIndex, secondIndex, direction, name, confidenceEstimate);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE));
        }
    }


}
