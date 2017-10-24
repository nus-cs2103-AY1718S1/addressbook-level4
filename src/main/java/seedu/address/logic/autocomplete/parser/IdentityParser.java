package seedu.address.logic.autocomplete.parser;

import java.util.Arrays;
import java.util.List;

/** Used exclusively to act as an identity function. */
public class IdentityParser implements AutoCompleteParser {

    public List<String> parseForPossibilities(String stub) {
        return Arrays.asList(new String[] {stub});
    }

}
