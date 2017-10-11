package seedu.address.logic.parser;

import seedu.address.logic.commands.ToggleTagColorCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.ArrayList;

/**
 * Parse input for tagcolor command
 */
public class ToggleTagColorParser implements Parser<ToggleTagColorCommand> {

    @Override
    public ToggleTagColorCommand parse(String userInput) throws ParseException {
        boolean isOn;
        String cleanUserInput;
        cleanUserInput = userInput.trim();
        String[] args = cleanUserInput.split("\\s+");
        if (args.length > 2)
            throw new  ParseException("Invalid tagcolor command.");

        switch (args[0]) {
        case "on":
            isOn = true;
            break;
        case "off":
            isOn = false;
            break;
        default:
           return new ToggleTagColorCommand(true, args[0], args[1]);
        }
        return new ToggleTagColorCommand(isOn,"","");
    }

}
