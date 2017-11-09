package seedu.address.model.asana;

//@@author Sri-vatsa
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

public class tokenParser implements Parser<storeAccessToken> {
    @Override
    public storeAccessToken parse(String userInput) throws ParseException {
        //TODO Parse userinput
        return new storeAccessToken(userInput);
    }
}
