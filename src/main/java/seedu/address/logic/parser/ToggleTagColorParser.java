package seedu.address.logic.parser;

import seedu.address.logic.commands.ToggleTagColorCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ToggleTagColorParser implements Parser<ToggleTagColorCommand> {

    @Override
    public ToggleTagColorCommand parse(String userInput) throws ParseException {
            boolean isOn;
            userInput = userInput.trim();
        switch (userInput) {
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
