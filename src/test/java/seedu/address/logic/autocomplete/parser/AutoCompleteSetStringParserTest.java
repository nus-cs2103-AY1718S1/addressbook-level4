package seedu.address.logic.autocomplete.parser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author john19950730
public class AutoCompleteSetStringParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AutoCompleteSetStringParser parser = new AutoCompleteSetStringParser(Arrays.asList(
            new String[] {"apple", "banana", "coriander", "disneyland", "enderman", "application"}
    ));

    @Test
    public void testSetStringParser() {
        //all match, parser is greedy
        assertEquals(parser.parseForPossibilities(""),
                Arrays.asList(
                        new String[] {"apple", "banana", "coriander", "disneyland", "enderman", "application", ""}));

        //multiple matches
        assertEquals(parser.parseForPossibilities("appl"),
                Arrays.asList(new String[] {"apple", "application", "appl"}));

        //single match
        assertEquals(parser.parseForPossibilities("dis"),
                Arrays.asList(new String[] {"disneyland", "dis"}));

        //no match, does not match 'contains'
        assertEquals(parser.parseForPossibilities("an"),
                Arrays.asList(new String[] {"an"}));
    }
}
