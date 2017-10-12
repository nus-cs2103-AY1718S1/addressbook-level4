package seedu.address.logic.parser;

import seedu.address.logic.commands.ToggleTagColorCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse input for tagcolor command
 */
public class ToggleTagColorParser implements Parser<ToggleTagColorCommand> {

    private static final String RANDOM_KEY_WORD = "random";
    private static final String OFF_KEY_WORD = "off";

    private static final String MESSAGE_INVALID_COMMAND = "Invalid tagcolor command."
            + "\n"
            + "tc: Shorthand equivalent for tagcolor."
            + "\n"
            + "tagcolor: TagColor sets color for AddressBook"
            + "\n"
            + "Parameters: tagcolor random/off || tagcolor TAGNAME COLOR";

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
            throw new ParseException(MESSAGE_INVALID_COMMAND);
        }
    }

    /**
     * Returns the Random Key Word
     */
    public String getRandomKeyWord() {
        return RANDOM_KEY_WORD;
    }

    /**
     * Returns the Off Key Word
     */
    public String getOffKeyWord() {
        return OFF_KEY_WORD;
    }
}
