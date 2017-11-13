package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FACEBOOK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.PersonUtil.getPersonDetails;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;

//@@author HanYaodong
public class AutoCompleteTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void autoCompleteAdd_missingOneField_addMissingPrefix() {
        // missing phone
        String command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + BIRTHDAY_DESC_AMY + FACEBOOK_DESC_AMY + TAG_DESC_FRIEND;
        String expected = AddCommand.COMMAND_WORD + NAME_DESC_AMY + " " + PREFIX_PHONE + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + FACEBOOK_DESC_AMY + TAG_DESC_FRIEND;
        assertAutoComplete(command, expected);

        // missing address
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + BIRTHDAY_DESC_AMY
                + FACEBOOK_DESC_AMY + TAG_DESC_FRIEND;
        expected = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + " " + PREFIX_ADDRESS
                + BIRTHDAY_DESC_AMY + FACEBOOK_DESC_AMY + TAG_DESC_FRIEND;
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteAdd_missingMultipleFields_addMissingPrefixes() {
        // missing name and email
        String command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY
                + FACEBOOK_DESC_AMY + TAG_DESC_FRIEND;
        String expected = AddCommand.COMMAND_WORD + " " + PREFIX_NAME + PHONE_DESC_AMY + " " + PREFIX_EMAIL
                + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + FACEBOOK_DESC_AMY + TAG_DESC_FRIEND;
        assertAutoComplete(command, expected);

        // all fields are missing
        command = AddCommand.COMMAND_WORD;
        expected = AddCommand.COMMAND_WORD + " " + PREFIX_NAME + " " + PREFIX_PHONE + " " + PREFIX_EMAIL
                + " " + PREFIX_ADDRESS + " " + PREFIX_BIRTHDAY + " " + PREFIX_FACEBOOK + " " + PREFIX_TAG;
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteAdd_invalidFields_addMissingPrefixes() {
        // invalid phone
        String command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + " " + PREFIX_PHONE + "abc" + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + FACEBOOK_DESC_AMY + TAG_DESC_FRIEND;
        String expected = AddCommand.COMMAND_WORD + NAME_DESC_AMY + " " + PREFIX_PHONE + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + FACEBOOK_DESC_AMY + TAG_DESC_FRIEND;
        assertAutoComplete(command, expected);

        // two name field -> choose the first one
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + FACEBOOK_DESC_AMY + TAG_DESC_FRIEND;
        expected = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + BIRTHDAY_DESC_AMY + FACEBOOK_DESC_AMY + TAG_DESC_FRIEND;
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteAdd_allValidFields_noChange() {
        String command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + BIRTHDAY_DESC_AMY + FACEBOOK_DESC_AMY + TAG_DESC_FRIEND;
        assertAutoComplete(command, command);
    }

    @Test
    public void autoCompleteDelete_invalidIndex_trimNonDigitChar() {
        String command = "delete 1a";
        String expected = "delete 1";
        assertAutoComplete(command, expected);

        command = "delete aa1bb   ";
        assertAutoComplete(command, expected);

        command = "delete a  1  b";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteDelete_validIndex_noChange() {
        String command = "delete 1";
        assertAutoComplete(command, command);
    }

    @Test
    public void autoCompleteEdit_invalidIndex_trimNonDigitChar() {
        String command = "edit 11a";
        String expected = "edit 11 ";
        assertAutoComplete(command, expected);

        command = "edit a1,1a ";
        assertAutoComplete(command, expected);

        command = "    edit   a1a 1   a    n/Some Name";
        expected = "edit 11 n/Some Name";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteEdit_firstIndexInvalidField_autoFillFields() {
        ReadOnlyPerson firstPerson = getTypicalPersons().get(INDEX_FIRST_PERSON.getZeroBased());
        String expected = EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
            + " " + getPersonDetails(firstPerson).trim() + " ";

        // empty field
        String command = "edit 1 n/";
        assertAutoComplete(command, expected);

        // invalid field
        command = "edit  1  p/a";
        assertAutoComplete(command, expected);

        // multiple invalid fields
        command = "edit  1 p/a e/aa";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteEdit_firstIndexValidFields_autoFillTheRestFields() throws Exception {
        ReadOnlyPerson firstPerson = getTypicalPersons().get(INDEX_FIRST_PERSON.getZeroBased());

        String validName = "Valid Name";
        String command = "edit 1 " + PREFIX_NAME_STRING + validName;
        Person expectedPerson = new Person(firstPerson);
        expectedPerson.setName(new Name(validName));
        String expected = EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
            + " " + getPersonDetails(expectedPerson).trim() + " ";
        assertAutoComplete(command, expected);

        String validPhone = "1234567";
        command = "edit 1 " + PREFIX_PHONE_STRING + validPhone;
        expectedPerson = new Person(firstPerson);
        expectedPerson.setPhone(new Phone(validPhone));
        expected = EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
            + " " + getPersonDetails(expectedPerson).trim() + " ";
        assertAutoComplete(command, expected);

        // index out of boundary -> leave the args
        assertEquals(command, AutoComplete.autoComplete(command, new ArrayList<>()));
    }

    @Test
    public void autoCompleteExport_noArg_trimSpaces() {
        String command = "export    ";
        String expected = "export ";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteExport_invalidIndexes_trimNonDigitChars() {
        String command = "export 1a 2a,, 3*.;   SomeFile.xml ";
        String expected = "export 1, 2, 3; SomeFile.xml";
        assertAutoComplete(command, expected);

        command = "export a1-3-10a,4 6aa ,,  ; SomeFile.xml";
        expected = "export 1-3, 4, 6; SomeFile.xml";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteExport_noDelimiter_trimNonDigitChars() {
        String command = "export 1, 2, 3 SomeFile.xml ";
        String expected = "export 1-3; SomeFile.xml";
        assertAutoComplete(command, expected);

        command = "export 1, 2, 3,SomeFile.xml ";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteExport_continuousIndexes_wrapContinuousIndexes() {
        String command = "export 1, 2, 3, 4; SomeFile.xml ";
        String expected = "export 1-4; SomeFile.xml";
        assertAutoComplete(command, expected);

        command = "export 1-3, 4; SomeFile.xml ";
        assertAutoComplete(command, expected);

        command = "export 3-1, 4; SomeFile.xml ";
        assertAutoComplete(command, expected);

        command = "export 1,2,3,6,10,12,11; SomeFile.xml ";
        expected = "export 1-3, 6, 10-12; SomeFile.xml";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteImport_anyArgument_trimWhitespaces() {
        String command = "import  someFile.xml  ";
        String expected = "import someFile.xml";
        assertAutoComplete(command, expected);

        command = "import     ";
        expected = "import ";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteLogin_invalidPrefix_ignoreInvalidField() {
        String command = "login n/name usr/admin pwd/password";
        String expected = "login usr/admin pwd/password";
        assertAutoComplete(command, expected);

        command = "login n/name pwd/password";
        expected = "login usr/ pwd/password";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteLogin_noField_autoFillFields() {
        String command = "login usr/admin ";
        String expected = "login usr/admin pwd/";
        assertAutoComplete(command, expected);

        command = "login pwd/password";
        expected = "login usr/ pwd/password";
        assertAutoComplete(command, expected);

        command = "login  ";
        expected = "login usr/ pwd/";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteRemark_invalidIndex_trimNonDigitChars() {
        String command = "remark aa1,1a";
        String expected = "remark 11 ";
        assertAutoComplete(command, expected);

        command = "   remark   a1, 1  a r/Some remark";
        expected = "remark 11 r/Some remark";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteRemark_firstIndexEmptyField_autoFillRemarkField() {
        List<ReadOnlyPerson> personList = new ArrayList<>();
        personList.add(new Person(getTypicalPersons().get(0)));

        String validRemark = "valid remark";
        String command = "remark 1 r/";
        personList.get(INDEX_FIRST_PERSON.getZeroBased()).setRemark(new Remark(validRemark));
        String expected = command + validRemark;
        assertAutoComplete(command, expected, personList);
    }

    @Test
    public void autoCompleteSelect_invalidIndex_trimNonDigitChar() {
        String command = "select 1a";
        String expected = "select 1";
        assertAutoComplete(command, expected);

        command = "select aa1bb   ";
        assertAutoComplete(command, expected);

        command = "select a  1  b";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteSelect_validIndex_noChange() {
        String command = "select 1";
        assertAutoComplete(command, command);
    }

    @Test
    public void autoCompleteSort_containMultiplePrefix_onlyReturnName() {
        String command = "sort name phone  ";
        String expected = "sort name";
        assertAutoComplete(command, expected);

        command = "sort reverse phone ";
        expected = "sort phone reverse";
        assertAutoComplete(command, expected);

        command = "sort name reverse phone ";
        expected = "sort name reverse";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteUnknownCommand_prefixMatch_autoCompleteCommand() {
        String command = "ad   n/Some Name  ";
        String expected = "add n/Some Name";
        assertAutoComplete(command, expected);

        command = "cle  ";
        expected = "clear ";
        assertAutoComplete(command, expected);

        command = "de  1  ";
        expected = "delete 1";
        assertAutoComplete(command, expected);

        command = "ed 1 n/Some Name  ";
        expected = "edit 1 n/Some Name";
        assertAutoComplete(command, expected);

        command = "exi  ";
        expected = "exit ";
        assertAutoComplete(command, expected);

        command = "exp   1, 2; m.xml   ";
        expected = "export 1, 2; m.xml";
        assertAutoComplete(command, expected);

        command = "fi x ";
        expected = "find x";
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteUnknownCommand_multiplePrefixMatches_autoCompleteCommand() {
        // exit and export
        String command = "ex";
        String expected = getUnknownCommandPrompt(ExitCommand.COMMAND_WORD, ExportCommand.COMMAND_WORD);
        assertAutoComplete(command, expected);

        // search and select
        command = "se";
        expected = getUnknownCommandPrompt(SearchCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD);
        assertAutoComplete(command, expected);
    }

    @Test
    public void autoCompleteUnknownCommand_fuzzyMatches_autoCompleteCommand() {
        // single hit
        String command = "adda ";
        String expected = getUnknownCommandPrompt(AddCommand.COMMAND_WORD);
        assertAutoComplete(command, expected);

        // multiple hits
        command = "port";
        expected = getUnknownCommandPrompt(ExportCommand.COMMAND_WORD, ImportCommand.COMMAND_WORD,
                                           SortCommand.COMMAND_WORD);
        assertAutoComplete(command, expected);

        // no hit
        command = "zzzzz";
        assertAutoComplete(command, command);
    }

    @Test
    public void autoCompleteUnknownCommand_emptyInput_emptyReturn() {
        String command = "     ";
        assertAutoComplete(command, "");
    }

    /**
     * Asserts if the auto-complete of {@code command} equals to {@code expectedResult}.
     */
    private void assertAutoComplete(String command, String expectedResult) {
        String result = AutoComplete.autoComplete(command, getTypicalPersons());
        assertEquals(result, expectedResult);
    }

    /**
     * @see #assertAutoComplete(String, String).
     */
    private void assertAutoComplete(String command, String expectedResult, List<ReadOnlyPerson> personList) {
        String result = AutoComplete.autoComplete(command, personList);
        assertEquals(result, expectedResult);
    }

    /**
     * Returns the prompt String for suggestions.
     */
    private String getUnknownCommandPrompt(String... suggestions) {
        return "Do you mean: " + Arrays.stream(suggestions).collect(Collectors.joining(" or ")) + " ?";
    }
}
