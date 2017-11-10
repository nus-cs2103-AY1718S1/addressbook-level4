package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.AppUtil.PanelChoice;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    //@@author Juxarius
    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * Complicated code is due to the intention of giving the user the freedom to place arguments in any format
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
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
            return new SelectCommand(index, panelChoice);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
    }
}
