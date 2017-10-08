package seedu.address.logic.parser;

import seedu.address.logic.commands.ToggleTagColorCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse input for tagcolor command
 */
public class ToggleTagColorParser implements Parser<ToggleTagColorCommand> {

    @Override
    public ToggleTagColorCommand parse(String userInput) throws ParseException {
            boolean isOn;
            String cleanUserInput;
            cleanUserInput = userInput.trim();
        switch (cleanUserInput) {
            case "on":
                isOn = true;
                break;
            case "off":
                isOn = false;
                break;
            default:
                throw new ParseException("Invalid tagcolor command.");
        }
            return new ToggleTagColorCommand(isOn);
    }

}
