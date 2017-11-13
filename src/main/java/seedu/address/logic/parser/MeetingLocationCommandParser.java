package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MeetingLocationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author YuchenHe98
/**
 * Parses input arguments and creates a new ChangePasswordCommand object
 */
public class MeetingLocationCommandParser implements Parser<MeetingLocationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ArrangeCommand
     * and returns an ArrangeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public MeetingLocationCommand parse(String args) throws ParseException {

        try {
            String[] listOfPerson = args.trim().split("\\s+");
            int[] listOfIndex = new int[listOfPerson.length];
            for (int i = 0; i < listOfPerson.length; i++) {
                try {
                    listOfIndex[i] = Integer.parseInt(listOfPerson[i]);
                } catch (NumberFormatException e) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT
                            + MeetingLocationCommand.MESSAGE_USAGE));
                }
                if (listOfIndex[i] <= 0) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT
                            + MeetingLocationCommand.MESSAGE_USAGE));
                }
            }
            return new MeetingLocationCommand(listOfIndex);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MeetingLocationCommand.MESSAGE_USAGE));
        }
    }
}

