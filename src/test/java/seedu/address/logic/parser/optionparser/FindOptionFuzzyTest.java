//@@author Hailinx
package seedu.address.logic.parser.optionparser;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FuzzySearchPredicate;

public class FindOptionFuzzyTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_parseEmptyArg_throwException() throws Exception {
        thrown.expect(ParseException.class);
        new FindOptionFuzzy("").parse();
    }

    @Test
    public void test_parseKeywords() throws Exception {
        assertParseSuccess("Ac", new FindOptionFuzzy("Ac").parse());

        assertParseFailure("Ac", new FindOptionFuzzy("A").parse());
    }

    private static void assertParseSuccess(String excepted, FindCommand actual) {
        Assert.assertTrue(actual.equals(new FindCommand(new FuzzySearchPredicate(excepted))));
    }

    private static void assertParseFailure(String excepted, FindCommand actual) {
        Assert.assertFalse(actual.equals(new FindCommand(new FuzzySearchPredicate(excepted))));
    }
}
