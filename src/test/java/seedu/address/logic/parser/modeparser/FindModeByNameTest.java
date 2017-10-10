package seedu.address.logic.parser.modeparser;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import java.util.Arrays;
import java.util.List;

public class FindModeByNameTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_parseEmptyArg_throwException() throws Exception {
        thrown.expect(ParseException.class);
        new FindModeByName("").parse();
    }

    @Test
    public void test_parseKeywords() throws Exception {
        assertParseSuccess(Arrays.asList("Bob"), new FindModeByName("Bob").parse());
        assertParseSuccess(Arrays.asList("Bob", "Alice"), new FindModeByName("Bob Alice").parse());
        assertParseSuccess(Arrays.asList("Bob", "Alice", "Coal"), new FindModeByName("Bob  Alice   Coal").parse());

        assertParseFailure(Arrays.asList("Bob", "Alice"), new FindModeByName("Alice Bob").parse());
        assertParseFailure(Arrays.asList("Bob", "Ali"), new FindModeByName("Alice Bob").parse());
    }

    private static void assertParseSuccess(List<String> excepted, FindCommand actual) {
        Assert.assertTrue(actual.equals(new FindCommand(new NameContainsKeywordsPredicate(excepted))));
    }

    private static void assertParseFailure(List<String> excepted, FindCommand actual) {
        Assert.assertFalse(actual.equals(new FindCommand(new NameContainsKeywordsPredicate(excepted))));
    }

    @Test
    public void test_isValidModeArgs() {
        Assert.assertTrue(new FindModeByName("abc").isValidModeArgs());
        Assert.assertTrue(new FindModeByName("Alic bob").isValidModeArgs());

        Assert.assertFalse(new FindModeByName("-d").isValidModeArgs());
        Assert.assertFalse(new FindModeByName("--d").isValidModeArgs());
    }
}