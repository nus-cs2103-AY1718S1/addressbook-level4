package seedu.address.logic.parser.parserutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ParserUtil.isParsableEmail;
import static seedu.address.logic.parser.ParserUtil.parseEmail;
import static seedu.address.logic.parser.ParserUtil.parseFirstEmail;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstEmail;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;

public class ParserUtilEmailSuggestionTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void isParsableFirstEmailAssertFalse() {
        assertFalse(isParsableEmail("aloha")); // characters only
        assertFalse(isParsableEmail("+(&%*$^&*")); // symbols only
        assertFalse(isParsableEmail("123")); // numbers
        assertFalse(isParsableEmail("prefix/abc+@gm.com")); // invalid characters before @
        assertFalse(isParsableEmail("add me e/abc+@gmab.com")); // invalid characters
        assertFalse(isParsableEmail("@email")); // missing local part
        assertFalse(isParsableEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(isParsableEmail("peterjack@")); // missing domain name
    }

    @Test
    public void isParsableFirstEmailAssertTrue() {
        assertTrue(isParsableEmail("e/PeterJack_1190@example.com/postfix"));
        assertTrue(isParsableEmail("addemail/a@b/postfix")); // minimal
        assertTrue(isParsableEmail("test@localhost/postfix")); // alphabets only
        assertTrue(isParsableEmail("123@145/postfix")); // numeric local part and domain name
        assertTrue(isParsableEmail("e/a1@example1.com")); // mixture of alphanumeric and dot characters
        assertTrue(isParsableEmail("_user_@_e_x_a_m_p_l_e_.com_")); // underscores
        assertTrue(isParsableEmail("add peter_jack@very_very_very_long_example.com something")); // long domain name
        assertTrue(isParsableEmail("email if.you.dream.it_you.can.do.it@example.com")); // long local part
    }

    @Test
    public void parseFirstEmailSlashedPrefixReturnsStandardEmail() throws IllegalValueException {
        assertFirstEmail("a1@example1.com", "e/a1@example1.com");
    }

    @Test
    public void parseFirstEmailSlashedPrefixPostfixReturnsStandardEmail() throws IllegalValueException {
        assertFirstEmail("PeterJack_1190@example.com", "e/PeterJack_1190@example.com/postfix");
    }

    @Test
    public void parseFirstEmailMinimalPrefixPostfixReturnsMinimalEmail() throws IllegalValueException {
        assertFirstEmail("a@b", "addemail/a@b/postfix");
    }

    @Test
    public void parseFirstEmailNumberEmailPostfixReturnsNumberEmail() throws IllegalValueException {
        assertFirstEmail("123@145", "123@145/postfix");
    }

    @Test
    public void parseFirstEmailUnderscoreEmailPostfixReturnsUnderscoreEmail() throws IllegalValueException {
        assertFirstEmail("_user_@_e_x_a_m_p_l_e_.com_", "_user_@_e_x_a_m_p_l_e_.com_");
    }

    @Test
    public void parseFirstEmailGibberishAddCommandReturnsEmail() throws IllegalValueException {
        assertFirstEmail("peter_jack@very_very_very_long_example.com",
                "add peter_jack@very_very_very_long_example.com something");
    }

    @Test
    public void parseFirstEmailGibberishEmailCommandReturnsEmail() throws IllegalValueException {
        assertFirstEmail("if.you.dream.it_you.can.do.it@example.com",
                "email if.you.dream.it_you.can.do.it@example.com");
    }

    /**
     * Attempts to parse a email String and assert the expected value and validity of the email.
     */
    public void assertFirstEmail(String expected, String input) throws IllegalValueException {
        assertEquals(expected, parseFirstEmail(input));
        Optional possibleEmail = parseEmail(Optional.of(parseFirstEmail(input)));
        assertTrue(possibleEmail.isPresent() && possibleEmail.get() instanceof Email);
    }

    @Test
    public void parseFirstEmailCharactersOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("aloha");
    }

    @Test
    public void parseFirstEmailSymbolsOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("+(&%*$^&*");
    }

    @Test
    public void parseFirstEmailNumbersThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("123");
    }

    @Test
    public void parseFirstEmailInvalidCharactersBeforeAtThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("prefix/abc+@gm.com");
    }

    @Test
    public void parseFirstEmailInvalidCharactersThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("add me e/abc+@gmab.com");
    }

    @Test
    public void parseFirstEmailMissingLocalThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("@email");
    }

    @Test
    public void parseFirstEmailMissingAtThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("peterjackexample.com");
    }

    @Test
    public void parseFirstEmailMissingDomainThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("peterjack@");
    }

    @Test
    public void parseRemoveFirstEmailRemovesFirstEmail() {
        assertEquals("delete second def@ghi", parseRemoveFirstEmail("delete abc@def.com second def@ghi"));
        assertEquals("de", parseRemoveFirstEmail("de abcdef@g"));
        assertEquals("", parseRemoveFirstEmail("ab@de"));
        assertEquals("Not_an_email+@abn.de", parseRemoveFirstEmail("Not_an_email+@abn.de"));
        assertEquals("", parseRemoveFirstEmail("a@abn.de"));
        assertEquals("nothing here", parseRemoveFirstEmail("nothing here"));
    }
}
