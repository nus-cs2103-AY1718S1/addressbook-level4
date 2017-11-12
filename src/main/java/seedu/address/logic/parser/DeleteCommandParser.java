package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.AppUtil.PanelChoice;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    //@@author Juxarius
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            String[] inputs = args.split("\\s");
            String indexString = Arrays.stream(inputs).filter(s -> s.matches("\\d+"))
                    .findFirst().orElseThrow(() -> new IllegalValueException("No index found!"));
            Optional<String> panelString = Arrays.stream(inputs).filter(s -> s.matches("\\p{Alpha}+")).findFirst();
            PanelChoice panelChoice;
            if (inputs.length < 2) {
                throw new IllegalValueException("Too little input arguments!");
            } else if (inputs.length > 2 && panelString.isPresent()) {
                panelChoice = ParserUtil.parsePanelChoice(panelString.get().toLowerCase());
            } else {
                panelChoice = PanelChoice.PERSON;
            }
            Index index = ParserUtil.parseIndex(indexString);
            return new DeleteCommand(index, panelChoice);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
