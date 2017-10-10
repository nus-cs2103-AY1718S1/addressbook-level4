package seedu.address.logic.parser.modeparser;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FuzzySearchPredicate;

public class FindModeFuzzyTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_parseEmptyArg_throwException() throws Exception {
        thrown.expect(ParseException.class);
        new FindModeFuzzy("").parse();
    }

    @Test
    public void test_parseKeywords() throws Exception {
        assertParseSuccess("Ac", new FindModeFuzzy("Ac").parse());

        assertParseFailure("Ac", new FindModeFuzzy("A").parse());
    }

    private static void assertParseSuccess(String excepted, FindCommand actual) {
        Assert.assertTrue(actual.equals(new FindCommand(new FuzzySearchPredicate(excepted))));
    }

    private static void assertParseFailure(String excepted, FindCommand actual) {
        Assert.assertFalse(actual.equals(new FindCommand(new FuzzySearchPredicate(excepted))));
    }
}