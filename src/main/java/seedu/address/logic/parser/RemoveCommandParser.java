package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;


public class RemoveCommandParser implements Parser<RemoveCommand> {

    public RemoveCommand parse(String args) throws ParseException {
        Tag toRemove;

        try{
            toRemove = new Tag(args);
            return new RemoveCommand(toRemove);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        }

    }

}
