package seedu.address.model.asana;

//@@author Sri-vatsa
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse accesstoken input from user
 */
public class TokenParser implements Parser<StoreAccessToken> {
    @Override
    public StoreAccessToken parse(String userInput) throws ParseException {
        //TODO Parse userinput
        return new StoreAccessToken(userInput);
    }
}
