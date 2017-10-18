package seedu.address.logic.parser;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.ParserUtil.generateHint;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.kordamp.ikonli.feather.Feather;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String LIST_COMMAND_ALIAS = "show";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseName(null);
    }

    @Test
    public void parseName_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseName(Optional.of(INVALID_NAME));
    }

    @Test
    public void parseName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseName(Optional.empty()).isPresent());
    }

    @Test
    public void parseName_validValue_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        Optional<Name> actualName = ParserUtil.parseName(Optional.of(VALID_NAME));

        assertEquals(expectedName, actualName.get());
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parsePhone(null);
    }

    @Test
    public void parsePhone_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parsePhone(Optional.of(INVALID_PHONE));
    }

    @Test
    public void parsePhone_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePhone(Optional.empty()).isPresent());
    }

    @Test
    public void parsePhone_validValue_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        Optional<Phone> actualPhone = ParserUtil.parsePhone(Optional.of(VALID_PHONE));

        assertEquals(expectedPhone, actualPhone.get());
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseAddress(null);
    }

    @Test
    public void parseAddress_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseAddress(Optional.of(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseAddress(Optional.empty()).isPresent());
    }

    @Test
    public void parseAddress_validValue_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        Optional<Address> actualAddress = ParserUtil.parseAddress(Optional.of(VALID_ADDRESS));

        assertEquals(expectedAddress, actualAddress.get());
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseEmail(null);
    }

    @Test
    public void parseEmail_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseEmail(Optional.of(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEmail(Optional.empty()).isPresent());
    }

    @Test
    public void parseEmail_validValue_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        Optional<Email> actualEmail = ParserUtil.parseEmail(Optional.of(VALID_EMAIL));

        assertEquals(expectedEmail, actualEmail.get());
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseCommand_validCommand_returnsCommand() throws Exception {
        assertEquals(ParserUtil.parseCommand(AddCommand.COMMAND_WORD), AddCommand.COMMAND_WORD);
        assertEquals(ParserUtil.parseCommand(ListCommand.COMMAND_WORD), ListCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_invalidCommand_returnsNull() throws Exception {
        assertNull(ParserUtil.parseCommand(""));
        assertNull(ParserUtil.parseCommand("invalid-command"));
    }

    @Test
    public void parseIconCode_validCommand_returnsCorrectIcon() throws Exception {
        assertEquals(ParserUtil.parseIconCode(AddCommand.COMMAND_WORD), Feather.FTH_PLUS);
        assertEquals(ParserUtil.parseIconCode(ListCommand.COMMAND_WORD), Feather.FTH_PAPER);
    }

    @Test
    public void parseIconCode_invalidCommand_returnsEmptyOptional() throws Exception {
        assertNull(ParserUtil.parseIconCode(""));
        assertNull(ParserUtil.parseIconCode("invalid-command"));
    }

    @Test
    public void parseCommandAndArguments_validCommandAndArgument_returnsCommandAndArgument() throws Exception {
        String[] commandAndArguments = {AddCommand.COMMAND_WORD, " arguments"};
        assertArrayEquals(ParserUtil.parseCommandAndArguments(AddCommand.COMMAND_WORD + " arguments"),
                commandAndArguments);
    }

    @Test
    public void parseCommandAndArguments_validCommandNoArgument_returnsCommandAndEmptyArgument() throws Exception {
        String[] commandAndArguments = {AddCommand.COMMAND_WORD, ""};
        assertArrayEquals(ParserUtil.parseCommandAndArguments(AddCommand.COMMAND_WORD), commandAndArguments);
    }

    @Test
    public void parseCommandAndArguments_emptyString_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseCommandAndArguments("");
    }

    @Test
    public void generate_add_hint() {
        assertHintEquals("add", " n/NAME");
        assertHintEquals("add ", "n/NAME");
        assertHintEquals("add n", "/NAME");
        assertHintEquals("add n/", "NAME");
        assertHintEquals("add n/name", " p/PHONE");
        assertHintEquals("add n/name ", "p/PHONE");
        assertHintEquals("add n/name p", "/PHONE");
        assertHintEquals("add n/name p/", "PHONE");
        assertHintEquals("add n/name p/123", " e/EMAIL");
        assertHintEquals("add n/name p/notValid", " e/EMAIL");
        assertHintEquals("add n/name p/123 ", "e/EMAIL");
        assertHintEquals("add n/name p/123 e", "/EMAIL");
        assertHintEquals("add n/name p/123 e/", "EMAIL");
        assertHintEquals("add n/name p/123 e/e@e.com", " a/ADDRESS");
        assertHintEquals("add n/name p/123 e/notValid", " a/ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com" , " a/ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com a" , "/ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com a/" , "ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com a/address" , " t/TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address " , "t/TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t" , "/TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/" , "TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag" , "");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag    " , "");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag    bla bla" , "");

        assertHintEquals("add p/phone", " n/NAME");
        assertHintEquals("add p/phone n", "/NAME");
        assertHintEquals("add p/phone t", "/TAG");

        //TODO: change this functionality
        assertHintEquals("add t/tag t", " n/NAME");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag p" , "");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag p/" , "PHONE");
    }

    @Test
    public void generate_edit_hint() {
        assertHintEquals("edit", " index");
        assertHintEquals("edit ", "index");
        assertHintEquals("edit 12", " prefix/KEYWORD");
        assertHintEquals("edit 12 ", "prefix/KEYWORD");

        assertHintEquals("edit 12 p", "/PHONE");
        assertHintEquals("edit 12 p/", "PHONE");

        assertHintEquals("edit 12 n", "/NAME");
        assertHintEquals("edit 12 n/", "NAME");

        assertHintEquals("edit 12 e", "/EMAIL");
        assertHintEquals("edit 12 e/", "EMAIL");

        assertHintEquals("edit 12 a", "/ADDRESS");
        assertHintEquals("edit 12 a/", "ADDRESS");

        assertHintEquals("edit 12 t", "/TAG");
        assertHintEquals("edit 12 t/", "TAG");


        assertHintEquals("edit 12 p/123", " prefix/KEYWORD");
        assertHintEquals("edit 12 p/123 ", "prefix/KEYWORD");

        //TODO: change this functionality
        assertHintEquals("edit p/123", " index");
        assertHintEquals("edit p/123 ", "index");
        assertHintEquals("edit p/123 1", " index");
        assertHintEquals("edit p/123 1 ", "index");
    }

    @Test
    public void generate_find_hint() {
        assertHintEquals("find", " prefix/KEYWORD");
        assertHintEquals("find ", "prefix/KEYWORD");
        assertHintEquals("find", " prefix/KEYWORD");

        assertHintEquals("find n", "/NAME");
        assertHintEquals("find n/", "NAME");
        assertHintEquals("find n/1", " prefix/KEYWORD");

        assertHintEquals("find p", "/PHONE");
        assertHintEquals("find p/", "PHONE");
        assertHintEquals("find p/1", " prefix/KEYWORD");

        assertHintEquals("find a", "/ADDRESS");
        assertHintEquals("find a/", "ADDRESS");
        assertHintEquals("find a/1", " prefix/KEYWORD");

        assertHintEquals("find t", "/TAG");
        assertHintEquals("find t/", "TAG");
        assertHintEquals("find t/1", " prefix/KEYWORD");

        assertHintEquals("find e", "/EMAIL");
        assertHintEquals("find e/", "EMAIL");
        assertHintEquals("find e/1", " prefix/KEYWORD");
    }

    @Test
    public void generate_select_hint() {
        assertHintEquals("select", " index");
        assertHintEquals("select ", "index");
        assertHintEquals("select 1", "");
        assertHintEquals("select bla 1", " index");
    }

    @Test
    public void generate_delete_hint() {
        assertHintEquals("delete", " index");
        assertHintEquals("delete ", "index");
        assertHintEquals("delete 1", "");
        assertHintEquals("delete bla 1", " index");
    }

    @Test
    public void generate_standard_hint() {
        assertHintEquals("history", " show command history");
        assertHintEquals("exit", " exits the app");
        assertHintEquals("clear", " clears address book");
        assertHintEquals("help", " shows user guide");
        assertHintEquals("undo", " undo command");
        assertHintEquals("redo", " redo command");
        assertHintEquals("unknown", " type help for guide");

        //TODO: to change
        assertHintEquals("alias", " creates an alias");
    }

    public void assertHintEquals(String userInput, String expected) {
        assertEquals(expected, generateHint(userInput));
    }
}
