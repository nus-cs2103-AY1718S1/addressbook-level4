package seedu.address.logic.parser.optionparser;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import java.util.Arrays;
import java.util.List;

public class FindOptionByNameTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_parseEmptyArg_throwException() throws Exception {
        thrown.expect(ParseException.class);
        new FindOptionByName("").parse();
    }

    @Test
    public void test_parseKeywords() throws Exception {
        assertParseSuccess(Arrays.asList("Bob"), new FindOptionByName("Bob").parse());
        assertParseSuccess(Arrays.asList("Bob", "Alice"), new FindOptionByName("Bob Alice").parse());
        assertParseSuccess(Arrays.asList("Bob", "Alice", "Coal"), new FindOptionByName("Bob  Alice   Coal").parse());

        assertParseFailure(Arrays.asList("Bob", "Alice"), new FindOptionByName("Alice Bob").parse());
        assertParseFailure(Arrays.asList("Bob", "Ali"), new FindOptionByName("Alice Bob").parse());
    }

    private static void assertParseSuccess(List<String> excepted, FindCommand actual) {
        Assert.assertTrue(actual.equals(new FindCommand(new NameContainsKeywordsPredicate(excepted))));
    }

    private static void assertParseFailure(List<String> excepted, FindCommand actual) {
        Assert.assertFalse(actual.equals(new FindCommand(new NameContainsKeywordsPredicate(excepted))));
    }

    @Test
    public void test_isValidOptionArgs() {
        Assert.assertTrue(new FindOptionByName("abc").isValidOptionArgs());
        Assert.assertTrue(new FindOptionByName("Alic bob").isValidOptionArgs());

        Assert.assertFalse(new FindOptionByName("-d").isValidOptionArgs());
        Assert.assertFalse(new FindOptionByName("--d").isValidOptionArgs());
    }
}
