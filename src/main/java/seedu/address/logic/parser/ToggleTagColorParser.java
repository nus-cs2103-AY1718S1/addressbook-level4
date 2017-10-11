package seedu.address.logic.parser;

import seedu.address.logic.commands.ToggleTagColorCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse input for tagcolor command
 */
public class ToggleTagColorParser implements Parser<ToggleTagColorCommand> {

    private static final String RANDOM_KEY_WORD = "random";
    private static final String OFF_KEY_WORD = "off";
    @Override
    public ToggleTagColorCommand parse(String userInput) throws ParseException {
        boolean isOn;
        String cleanUserInput;
        cleanUserInput = userInput.trim();
        String[] args = cleanUserInput.split("\\s+");
        try {
            switch (args[0]) {
            case RANDOM_KEY_WORD:
                isOn = true;
                break;
            case OFF_KEY_WORD:
                isOn = false;
                break;
            default:
                return new ToggleTagColorCommand(true, args[0], args[1]);
            }
            return new ToggleTagColorCommand(isOn, "", "");
        } catch (ArrayIndexOutOfBoundsException exp) {
            throw new ParseException("Invalid tagcolor command.");
        }
    }
}
