package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddRelationshipCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.relationship.RelationshipDirection;

import java.util.Arrays;
import java.util.List;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class AddRelationshipCommandParser implements Parser<AddRelationshipCommand> {

    @Override
    public AddRelationshipCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE));
        }

        List<String> listOfArgs = Arrays.asList(trimmedArgs.split(" "));

        if (listOfArgs.size() != 3) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE));
        }

        try {
            Index firstIndex = ParserUtil.parseIndex(listOfArgs.get(0));
            Index secondIndex = ParserUtil.parseIndex(listOfArgs.get(1));
            RelationshipDirection direction = ParserUtil.parseDirection(listOfArgs.get(2));
            return new AddRelationshipCommand(firstIndex, secondIndex, direction);
        } catch (IllegalValueException ive){
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE));
        }
    }


}
