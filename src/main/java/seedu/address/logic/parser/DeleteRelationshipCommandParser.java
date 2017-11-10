package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteRelationshipCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author wenmogu
/**
 * This is a argument parser for DeleteRelationshipCommand
 */
public class DeleteRelationshipCommandParser implements Parser<DeleteRelationshipCommand> {
    @Override
    public DeleteRelationshipCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRelationshipCommand.MESSAGE_USAGE));
        }

        List<String> listOfArgs = Arrays.asList(trimmedArgs.split(" "));

        if (listOfArgs.size() != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRelationshipCommand.MESSAGE_USAGE));
        }

        String firstIndexString = listOfArgs.get(0);
        String secondIndexString = listOfArgs.get(1);

        if (firstIndexString.equals(secondIndexString)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRelationshipCommand.MESSAGE_USAGE));
        }

        if (firstIndexString.equals("0") || secondIndexString.equals("0")) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                            DeleteRelationshipCommand.MESSAGE_USAGE));
        }

        try {
            Index firstIndex = ParserUtil.parseIndex(listOfArgs.get(0));
            Index secondIndex = ParserUtil.parseIndex(listOfArgs.get(1));
            return new DeleteRelationshipCommand(firstIndex, secondIndex);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRelationshipCommand.MESSAGE_USAGE));
        }
    }


}
