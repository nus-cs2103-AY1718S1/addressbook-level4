package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SetUniqueKeyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author Sri-vatsa
/**
 * Parses the user inputted access code, which is given by Asana upon {@code setupAsana}
 */
public class SetUniqueKeyCommandParser implements Parser<SetUniqueKeyCommand>{
    @Override
    public SetUniqueKeyCommand parse(String userInput) throws ParseException {
        String accessCode;
        try {
            accessCode = ParserUtil.parseAccessCode(userInput);
        } catch (IllegalValueException ive) {
            throw new ParseException("Please make sure the access code you have copied follows the format:\n"
            + "DIGIT/ALPHANUMERICS");
        }

        return new SetUniqueKeyCommand(accessCode);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetUniqueKeyCommand); // instanceof handles nulls
    }

}
