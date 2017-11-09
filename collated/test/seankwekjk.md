# seankwekjk
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class RemarkCommandParserTest {

    private static final String REMARK_TEXT = "text";

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "0 " + PREFIX_REMARK + REMARK_TEXT,
                String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemarkCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\ui\BrowserPanelTest.java
``` java
        // associated address of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + CARL.getAddress().value.replaceAll(" ", "+").replaceAll(",", "%2C"));

        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());

        // associated social media page of a person
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 1));
        BrowserPanel.setBrowserMode();
        postNow(selectionChangedEventStub);
        URL expectedSocialUrl = new URL(SOCIAL_MEDIA_URL_PREFIX + ALICE.getSocialMedia() + "/");

        assertEquals(expectedSocialUrl, browserPanelHandle.getLoadedUrl());
        BrowserPanel.setBrowserMode();
    }
}
```
###### \java\systemtests\RemarkCommandSystemTest.java
``` java
public class RemarkCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void remark() throws Exception {
        /* Case: follows expected syntax -> remark edited*/
        Index index = INDEX_FIRST_PERSON;
        String command = RemarkCommand.COMMAND_WORD + " " + index.getOneBased() + " " + REMARK_DESC_AMY;
        Remark remark = new Remark(VALID_REMARK_AMY);
        assertCommandSuccess(command, index, remark, RemarkCommand.REMARK_EDIT_SUCCESS);

        /* Case: clears remark -> remark edited*/
        command = RemarkCommand.COMMAND_WORD + " " + index.getOneBased() + " r/";
        Remark emptyRemark = new Remark("");
        assertCommandSuccess(command, index, emptyRemark, RemarkCommand.REMARK_CLEAR_SUCCESS);

        /* Case: filtered person list, edit index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = RemarkCommand.COMMAND_WORD + " " + invalidIndex + " " + REMARK_DESC_AMY;
        assertCommandFailure(command, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid index (0) -> rejected */
        command = RemarkCommand.COMMAND_WORD + " 0" + REMARK_DESC_AMY;
        assertCommandFailure(command, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes @code command
     * 1. Asserts that @code remark is equal to the updated remark @code toCheck
     * 2. Asserts that @code expectedMessage is the expected type of message result
     */
    private void assertCommandSuccess(String command, Index index, Remark remark, String expectedMessage) {
        executeCommand(command);
        ReadOnlyPerson personToCheck = getModel().getFilteredPersonList().get(index.getZeroBased());
        Remark toCheck = personToCheck.getRemark();
        assertTrue(remark.toString().equals(toCheck.toString()));
        assertEquals(expectedMessage, getResultDisplay().getText());
    }

    /**
     * Executes @code command
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
    }
}
```
###### \java\systemtests\ToggleCommandSystemTest.java
``` java
public class ToggleCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void toggle() throws Exception {
        /* Case: toggles to social media*/
        String command = ToggleCommand.COMMAND_WORD;
        String result = ToggleCommand.TOGGLE_SUCCESS + "Display Social Media";
        assertCommandSuccess(command, result);

        /* Case: toggles to maps*/
        command = ToggleCommand.COMMAND_WORD;
        result = ToggleCommand.TOGGLE_SUCCESS + "Display Address";
        assertCommandSuccess(command, result);
    }

    /**
     * Executes @code command
     * 1. Asserts that @code remark is equal to the updated remark @code toCheck
     * 2. Asserts that @code expectedMessage is the expected type of message result
     */
    private void assertCommandSuccess(String command, String expectedMessage) {
        executeCommand(command);
        assertEquals(expectedMessage, getResultDisplay().getText());
    }
}
```
