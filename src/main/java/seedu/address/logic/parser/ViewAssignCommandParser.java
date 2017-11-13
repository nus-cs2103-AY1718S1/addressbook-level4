package seedu.address.logic.parser;
//@@author Esilocke
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ViewAssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new ViewAssignCommand object
 */
public class ViewAssignCommandParser implements Parser<ViewAssignCommand> {
    /**
     * Parses input arguments and creates a new DeletePersonCommand object in the context of DeletePersonCommand.
     */
    public ViewAssignCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewAssignCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewAssignCommand.MESSAGE_USAGE));
        }
    }
}
