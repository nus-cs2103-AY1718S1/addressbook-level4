package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsPhonePredicate;

/**
 * Created by nguyenminhquang on 10/10/17.
 */
public class FindPhoneCommandParser {

    /**
     * Created by nguyenminhquang on 10/10/17.
     */
    public FindPhoneCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
        }

        String[] phoneNumbers = trimmedArgs.split("\\s+");

        return new FindPhoneCommand(new NameContainsPhonePredicate(Arrays.asList(phoneNumbers)));
    }
}
