package seedu.address.autocomplete.parser;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.autocomplete.AutoCompleteTestUtils;

//@@author john19950730
public class AutoCompleteSetStringParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AutoCompleteSetStringParser parser = new AutoCompleteSetStringParser(Arrays.asList(
            "apple", "banana", "coriander", "disneyland", "enderman", "application"));

    @Test
    public void testSetStringParser() {
        //all match, parser is greedy
        AutoCompleteTestUtils.assertParserPossibilities("", parser,
                "apple", "banana", "coriander", "disneyland", "enderman", "application");

        //multiple matches
        AutoCompleteTestUtils.assertParserPossibilities("appl", parser,
                "apple", "application");

        //single match
        AutoCompleteTestUtils.assertParserPossibilities("dis", parser,
                "disneyland");

        //no match, does not match 'contains'
        AutoCompleteTestUtils.assertParserPossibilities("an", parser);
    }
}
