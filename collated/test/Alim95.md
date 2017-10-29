# Alim95
###### \java\guitests\HelpOverlayTest.java
``` java
public class HelpOverlayTest extends AddressBookGuiTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openHelpOverlay() {
        //use accelerator
        getCommandBox().click();
        getMainMenu().openHelpOverlayUsingAccelerator();
        assertHelpOverlayOpen();
        getMainMenu().closeHelpOverlayUsingAccelerator();

        getSortMenu().click();
        getMainMenu().openHelpOverlayUsingAccelerator();
        assertHelpOverlayOpen();
        getCommandBox().click();
        getMainMenu().closeHelpOverlayUsingAccelerator();

        getSearchField().click();
        getMainMenu().openHelpOverlayUsingAccelerator();
        assertHelpOverlayOpen();
        getMainMenu().closeHelpOverlayUsingAccelerator();

        getResultDisplay().click();
        getMainMenu().openHelpOverlayUsingAccelerator();
        assertHelpOverlayOpen();
        getMainMenu().closeHelpOverlayUsingAccelerator();

        getPersonListPanel().click();
        getMainMenu().openHelpOverlayUsingAccelerator();
        assertHelpOverlayNotOpen();

        getBrowserPanel().click();
        getMainMenu().openHelpOverlayUsingAccelerator();
        assertHelpOverlayNotOpen();
    }


    /**
     * Asserts that the help overlay is open, and closes it after checking.
     */
    private void assertHelpOverlayOpen() {
        assertTrue(ERROR_MESSAGE, mainWindowHandle.getHelpOverlay().isOverlayPresent());
        guiRobot.pauseForHuman();
    }

    /**
     * Asserts that the help overlay is open, and closes it after checking.
     */
    private void assertHelpOverlayNotOpen() {
        assertFalse(ERROR_MESSAGE, mainWindowHandle.getHelpOverlay().isOverlayPresent());
        guiRobot.pauseForHuman();
    }
}
```
###### \java\guitests\SearchBoxTest.java
``` java

public class SearchBoxTest extends AddressBookGuiTest {

    @Test
    public void useSearchField() {

        /* Case: find "alice" in search field -> 1 person found */
        getSearchField().click();
        getSearchField().run("alice");
        assertEquals(mainWindowHandle.getPersonListPanel().getListSize(), 1);

        /* Case: find "AlIce" in search field -> 1 person found */
        getSearchField().click();
        getSearchField().run("AlIce");
        assertEquals(mainWindowHandle.getPersonListPanel().getListSize(), 1);

        /* Case: find "a" in search field -> 5 person found */
        getSearchField().click();
        getSearchField().run("a");
        assertEquals(mainWindowHandle.getPersonListPanel().getListSize(), 5);

        /* Case: find no person in search field -> displays full list */
        getSearchField().click();
        getSearchField().run("");
        assertEquals(mainWindowHandle.getPersonListPanel().getListSize(), 7);

        /* Case: find "Meier" in search field -> 2 person found */
        getSearchField().click();
        getSearchField().run("Meier");
        assertEquals(mainWindowHandle.getPersonListPanel().getListSize(), 2);
    }
}
```
###### \java\guitests\SortMenuTest.java
``` java

public class SortMenuTest extends AddressBookGuiTest {

    @Test
    public void useSortMenu() {

        /* Case: sort by name -> list has been sorted alphabetically by name */
        getSortMenu().click();
        getSortMenu().run("name");
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name");
        assertSortNameSuccess(expectedMessage);

        /* Case: sort by phone -> list has been sorted numerically by phone number */
        getSortMenu().click();
        getSortMenu().run("phone");
        expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "phone");
        assertSortPhoneSuccess(expectedMessage);

        /* Case: sort by email -> list has been sorted alphabetically by email */
        getSortMenu().click();
        getSortMenu().run("email");
        expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "email");
        assertSortEmailSuccess(expectedMessage);

        /* Case: sort by address -> list has been sorted alphabetically by address */
        getSortMenu().click();
        getSortMenu().run("address");
        expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "address");
        assertSortAddressSuccess(expectedMessage);
    }

    /**
     * Asserts that sorting by name is successful and the result display box is equals to {@code expectedMessage},
     * and the person list panel is updated accordingly
     */
    private void assertSortNameSuccess(String expectedMessage) {
        assertEquals(getResultDisplay().getText(), expectedMessage);
        assertEquals(getPersonListPanel().getPersonCardHandle(0).getName(), "Alice Pauline");
        assertEquals(getPersonListPanel().getPersonCardHandle(1).getName(), "Benson Meier");
        assertEquals(getPersonListPanel().getPersonCardHandle(2).getName(), "Carl Kurz");
        assertEquals(getPersonListPanel().getPersonCardHandle(3).getName(), "Daniel Meier");
        assertEquals(getPersonListPanel().getPersonCardHandle(4).getName(), "Elle Meyer");
        assertEquals(getPersonListPanel().getPersonCardHandle(5).getName(), "Fiona Kunz");
        assertEquals(getPersonListPanel().getPersonCardHandle(6).getName(), "George Best");
    }

    /**
     * Asserts that sorting by phone is successful and the result display box is equals to {@code expectedMessage},
     * and the person list panel is updated accordingly
     */
    private void assertSortPhoneSuccess(String expectedMessage) {
        assertEquals(getResultDisplay().getText(), expectedMessage);
        assertEquals(getPersonListPanel().getPersonCardHandle(0).getName(), "Alice Pauline");
        assertEquals(getPersonListPanel().getPersonCardHandle(1).getName(), "Daniel Meier");
        assertEquals(getPersonListPanel().getPersonCardHandle(2).getName(), "Elle Meyer");
        assertEquals(getPersonListPanel().getPersonCardHandle(3).getName(), "Fiona Kunz");
        assertEquals(getPersonListPanel().getPersonCardHandle(4).getName(), "George Best");
        assertEquals(getPersonListPanel().getPersonCardHandle(5).getName(), "Carl Kurz");
        assertEquals(getPersonListPanel().getPersonCardHandle(6).getName(), "Benson Meier");
    }

    /**
     * Asserts that sorting by email is successful and the result display box is equals to {@code expectedMessage},
     * and the person list panel is updated accordingly
     */
    private void assertSortEmailSuccess(String expectedMessage) {
        assertEquals(getResultDisplay().getText(), expectedMessage);
        assertEquals(getPersonListPanel().getPersonCardHandle(0).getName(), "Alice Pauline");
        assertEquals(getPersonListPanel().getPersonCardHandle(1).getName(), "George Best");
        assertEquals(getPersonListPanel().getPersonCardHandle(2).getName(), "Daniel Meier");
        assertEquals(getPersonListPanel().getPersonCardHandle(3).getName(), "Carl Kurz");
        assertEquals(getPersonListPanel().getPersonCardHandle(4).getName(), "Benson Meier");
        assertEquals(getPersonListPanel().getPersonCardHandle(5).getName(), "Fiona Kunz");
        assertEquals(getPersonListPanel().getPersonCardHandle(6).getName(), "Elle Meyer");
    }

    /**
     * Asserts that sorting by address is successful and the result display box is equals to {@code expectedMessage},
     * and the person list panel is updated accordingly
     */
    private void assertSortAddressSuccess(String expectedMessage) {
        assertEquals(getResultDisplay().getText(), expectedMessage);
        assertEquals(getPersonListPanel().getPersonCardHandle(0).getName(), "Daniel Meier");
        assertEquals(getPersonListPanel().getPersonCardHandle(1).getName(), "Alice Pauline");
        assertEquals(getPersonListPanel().getPersonCardHandle(2).getName(), "Benson Meier");
        assertEquals(getPersonListPanel().getPersonCardHandle(3).getName(), "George Best");
        assertEquals(getPersonListPanel().getPersonCardHandle(4).getName(), "Fiona Kunz");
        assertEquals(getPersonListPanel().getPersonCardHandle(5).getName(), "Elle Meyer");
        assertEquals(getPersonListPanel().getPersonCardHandle(6).getName(), "Carl Kurz");
    }
}
```
###### \java\seedu\address\logic\commands\ListPinCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListPinCommand.
 */
public class ListPinCommandTest {

    private Model model;
    private Model expectedModel;
    private ListPinCommand listPinCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listPinCommand = new ListPinCommand();
        listPinCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ONLY_PINNED);
    }

    @Test
    public void execute_listIsNotFiltered_showsPinnedList() {
        assertCommandSuccess(listPinCommand, model, ListPinCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsPinnedList() {
        showFirstPersonOnly(model);
        assertCommandSuccess(listPinCommand, model, ListPinCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### \java\seedu\address\logic\commands\PinUnpinCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code PinCommand}
 * and {@code UnpinCommand}.
 */
public class PinUnpinCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToPin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PinCommand pinCommand = preparePinCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_PERSON_SUCCESS, personToPin);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.pinPerson(personToPin);

        assertCommandSuccess(pinCommand, model, expectedMessage, expectedModel);

        ReadOnlyPerson personToUnpin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnpinCommand unpinCommand = prepareUnpinCommand(INDEX_FIRST_PERSON);

        expectedMessage = String.format(UnpinCommand.MESSAGE_UNPIN_PERSON_SUCCESS, personToPin);

        expectedModel.unpinPerson(personToUnpin);

        assertCommandSuccess(unpinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredListPin_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PinCommand pinCommand = preparePinCommand(outOfBoundIndex);

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredListUnpin_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnpinCommand unpinCommand = prepareUnpinCommand(outOfBoundIndex);

        assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personAlreadyPinned_throwsCommandException() throws Exception {
        ReadOnlyPerson personToPin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.pinPerson(personToPin);
        PinCommand pinCommand = preparePinCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_PERSON_ALREADY_PINNED);
    }

    @Test
    public void execute_personAlreadyUnpinned_throwsCommandException() throws Exception {
        ReadOnlyPerson personToUnpin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.unpinPerson(personToUnpin);
        UnpinCommand unpinCommand = prepareUnpinCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(unpinCommand, model, Messages.MESSAGE_PERSON_ALREADY_UNPINNED);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToPin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PinCommand pinCommand = preparePinCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_PERSON_SUCCESS, personToPin);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.pinPerson(personToPin);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(pinCommand, model, expectedMessage, expectedModel);

        showFirstPersonOnly(model);

        ReadOnlyPerson personToUnpin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnpinCommand unpinCommand = prepareUnpinCommand(INDEX_FIRST_PERSON);

        expectedMessage = String.format(UnpinCommand.MESSAGE_UNPIN_PERSON_SUCCESS, personToUnpin);

        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.unpinPerson(personToPin);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(unpinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        PinCommand pinCommand = preparePinCommand(outOfBoundIndex);

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        PinCommand pinFirstCommand = new PinCommand(INDEX_FIRST_PERSON);
        PinCommand pinSecondCommand = new PinCommand(INDEX_SECOND_PERSON);
        UnpinCommand unpinFirstCommand = new UnpinCommand(INDEX_FIRST_PERSON);
        UnpinCommand unpinSecondCommand = new UnpinCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(pinFirstCommand.equals(pinFirstCommand));
        assertTrue(unpinFirstCommand.equals(unpinFirstCommand));

        // same values -> returns true
        PinCommand pinFirstCommandCopy = new PinCommand(INDEX_FIRST_PERSON);
        assertTrue(pinFirstCommand.equals(pinFirstCommandCopy));
        UnpinCommand unpinFirstCommandCopy = new UnpinCommand(INDEX_FIRST_PERSON);
        assertTrue(unpinFirstCommand.equals(unpinFirstCommandCopy));

        // different types -> returns false
        assertFalse(pinFirstCommand.equals(1));
        assertFalse(unpinFirstCommand.equals(1));

        // null -> returns false
        assertFalse(pinFirstCommand.equals(null));
        assertFalse(unpinFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(pinFirstCommand.equals(pinSecondCommand));
        assertFalse(unpinFirstCommand.equals(unpinSecondCommand));
    }

    /**
     * Returns a {@code PinCommand} with the parameter {@code index}.
     */
    private PinCommand preparePinCommand(Index index) {
        PinCommand pinCommand = new PinCommand(index);
        pinCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return pinCommand;
    }

    /**
     * Returns a {@code UnpinCommand} with the parameter {@code index}.
     */
    private UnpinCommand prepareUnpinCommand(Index index) {
        UnpinCommand unpinCommand = new UnpinCommand(index);
        unpinCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unpinCommand;
    }
}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java

public class SortCommandTest {

    private static final String TO_SORT_NAME = "name";
    private static final String TO_SORT_PHONE = "phone";
    private static final String TO_SORT_EMAIL = "email";
    private static final String TO_SORT_ADDRESS = "address";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeSortByNameSuccess() {
        SortCommand sortCommand = prepareCommand(TO_SORT_NAME);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, TO_SORT_NAME);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortList(TO_SORT_NAME);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeSortByPhoneSuccess() {
        SortCommand sortCommand = prepareCommand(TO_SORT_PHONE);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, TO_SORT_PHONE);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortList(TO_SORT_PHONE);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeSortByEmailSuccess() {
        SortCommand sortCommand = prepareCommand(TO_SORT_EMAIL);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, TO_SORT_EMAIL);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortList(TO_SORT_EMAIL);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeSortByAddressSuccess() {
        SortCommand sortCommand = prepareCommand(TO_SORT_ADDRESS);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, TO_SORT_ADDRESS);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortList(TO_SORT_ADDRESS);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the AddressBook accordingly.
     */
    private SortCommand prepareCommand(String toSort) {
        SortCommand command = new SortCommand(toSort);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\parser\PinCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the PinCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the PinCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class PinCommandParserTest {

    private PinCommandParser parser = new PinCommandParser();

    @Test
    public void parse_validArgs_returnsPinCommand() {
        assertParseSuccess(parser, "1", new PinCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseInvalidArgThrowsParseException() {
        assertParseFailure(parser, "home address",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsSortCommand() {
        // no leading and trailing whitespaces
        assertParseSuccess(parser, "phone", new SortCommand("phone"));

        // leading and trailing whitespaces
        assertParseSuccess(parser, "   phone   ", new SortCommand("phone"));

        // upper and lower cases
        assertParseSuccess(parser, "pHoNe", new SortCommand("phone"));

        // sort for name
        assertParseSuccess(parser, "name", new SortCommand("name"));

        // sort for email
        assertParseSuccess(parser, "email", new SortCommand("email"));

        // sort for address
        assertParseSuccess(parser, "address", new SortCommand("address"));
    }
}
```
###### \java\seedu\address\logic\parser\UnpinCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the UnpinCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the UnpinCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class UnpinCommandParserTest {

    private UnpinCommandParser parser = new UnpinCommandParser();

    @Test
    public void parse_validArgs_returnsPinCommand() {
        assertParseSuccess(parser, "1", new UnpinCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\ui\CommandBoxTest.java
``` java
    @Test
    public void commandBoxHighlight() {
        commandBox.highlight();
        assertEquals(commandBoxHandle.getStyle(), "-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    @Test
    public void handleUnhighlight() {
        commandBox.unhighlight();
        assertEquals(commandBoxHandle.getStyle(),
                "-fx-border-color: #383838 #383838 #ffffff #383838; -fx-border-width: 1");
    }
```
###### \java\seedu\address\ui\PersonListPanelTest.java
``` java
    @Test
    public void personListPanelHighlight() {
        personListPanel.highlight();
        assertEquals(personListPanelHandle.getStyle(), "-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    @Test
    public void personListPanelUnhighlight() {
        personListPanel.unhighlight();
        assertEquals(personListPanelHandle.getStyle(), "");
    }
}
```
###### \java\seedu\address\ui\ResultDisplayTest.java
``` java
    @Test
    public void resultDisplayHighlight() {
        resultDisplay.highlight();
        assertEquals(resultDisplayHandle.getStyle(), "-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    @Test
    public void resultDisplayUnhighlight() {
        resultDisplay.unhighlight();
        assertEquals(resultDisplayHandle.getStyle(), "");
    }
}
```
###### \java\seedu\address\ui\SortFindPanelTest.java
``` java
    @Test
    public void sortMenuHighlight() {
        sortFindPanel.highlightSortMenu();
        assertEquals(sortMenuHandle.getStyle(), "-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    @Test
    public void searchBoxHighlight() {
        sortFindPanel.highlightSearchBox();
        assertEquals(searchBoxHandle.getStyle(), "-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    @Test
    public void sortFindPanelUnhighlight() {
        sortFindPanel.unhighlight();
        assertEquals(sortMenuHandle.getStyle(), "");
        assertEquals(searchBoxHandle.getStyle(), "");
    }
}
```
###### \java\systemtests\PinUnpinCommandSystemTest.java
``` java
public class PinUnpinCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_PIN_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE);
    private static final String MESSAGE_INVALID_UNPIN_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UnpinCommand.MESSAGE_USAGE);

    @Test
    public void pinUnpin() {
        /* ----------------- Performing pin operation while an unfiltered list is being shown -------------------- */

        /* Case: pin the first person in the list, command with leading spaces and trailing spaces -> pinned */
        Model expectedModel = getModel();
        String command = "     " + PinCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        ReadOnlyPerson pinnedPerson = pinPerson(expectedModel, INDEX_FIRST_PERSON);
        String expectedResultMessage = String.format(MESSAGE_PIN_PERSON_SUCCESS, pinnedPerson);
        assertPinCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: pin the last person in the list -> pinned */
        Model modelBeforePinningLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforePinningLast);
        assertPinCommandSuccess(lastPersonIndex);

        /* ----------------- Performing unpin operation while an unfiltered list is being shown -------------------- */

        /* Case: unpin the first person in the list,
         command with leading spaces and trailing spaces -> unpinned */
        expectedModel = getModel();
        command = "     " + UnpinCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        ReadOnlyPerson unpinnedPerson = unpinPerson(expectedModel, INDEX_FIRST_PERSON);
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, unpinnedPerson);
        assertUnpinCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: unpin the last person in the list -> unpinned */
        Model modelBeforeUnpinningLast = getModel();
        lastPersonIndex = getLastIndex(modelBeforeUnpinningLast);
        assertUnpinCommandSuccess(lastPersonIndex);

        /* ------------------ Performing pin operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, pin index within bounds of address book and person list -> pinned */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        assertPinCommandSuccess(index);

        /* Case: filtered person list, pin index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = PinCommand.COMMAND_WORD + " " + invalidIndex;
        assertPinCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: filtered person list, pin person already pinned
         * -> rejected
         */
        command = PinCommand.COMMAND_WORD + " 1";
        assertPinCommandFailure(command, Messages.MESSAGE_PERSON_ALREADY_PINNED);

        /* ------------------ Performing unpin operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, pin index within bounds of address book and person list -> pinned */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        assertUnpinCommandSuccess(index);

        /* Case: filtered person list, pin index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        command = UnpinCommand.COMMAND_WORD + " " + invalidIndex;
        assertUnpinCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: filtered person list, unpin person not pinned
         * -> rejected
         */
        command = UnpinCommand.COMMAND_WORD + " 1";
        assertPinCommandFailure(command, Messages.MESSAGE_PERSON_ALREADY_UNPINNED);

        /* ---------------------------- Performing invalid pin and unpin operation ------------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = PinCommand.COMMAND_WORD + " 0";
        assertPinCommandFailure(command, MESSAGE_INVALID_PIN_COMMAND_FORMAT);
        command = UnpinCommand.COMMAND_WORD + " 0";
        assertUnpinCommandFailure(command, MESSAGE_INVALID_UNPIN_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = PinCommand.COMMAND_WORD + " -1";
        assertPinCommandFailure(command, MESSAGE_INVALID_PIN_COMMAND_FORMAT);
        command = UnpinCommand.COMMAND_WORD + " -1";
        assertUnpinCommandFailure(command, MESSAGE_INVALID_UNPIN_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = PinCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertPinCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        command = UnpinCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertUnpinCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertPinCommandFailure(PinCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_PIN_COMMAND_FORMAT);
        assertUnpinCommandFailure(UnpinCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_UNPIN_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertPinCommandFailure(PinCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_PIN_COMMAND_FORMAT);
        assertUnpinCommandFailure(UnpinCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_UNPIN_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertPinCommandFailure("PiN 1", MESSAGE_UNKNOWN_COMMAND);
        assertPinCommandFailure("uNPiN 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Pins the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     *
     * @return the pinned person
     */
    private ReadOnlyPerson pinPerson(Model model, Index index) {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ReadOnlyPerson targetPerson = lastShownList.get(index.getZeroBased());
        try {
            model.pinPerson(targetPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        }
        return targetPerson;
    }

    /**
     * Pins the person at {@code toPin} by creating a default {@code PinCommand} using {@code toPin} and
     * performs the same verification as {@code assertPinCommandSuccess(String, Model, String)}.
     *
     * @see PinUnpinCommandSystemTest#assertPinCommandSuccess(String, Model, String)
     */
    private void assertPinCommandSuccess(Index toPin) {
        Model expectedModel = getModel();
        ReadOnlyPerson pinnedPerson = pinPerson(expectedModel, toPin);
        String expectedResultMessage = String.format(MESSAGE_PIN_PERSON_SUCCESS, pinnedPerson);

        assertPinCommandSuccess(
                PinCommand.COMMAND_WORD + " " + toPin.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertPinCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertPinCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertPinCommandSuccess(String, Model, String)}
     * except that the browser url and selected card are expected to update accordingly depending
     * on the card at {@code expectedSelectedCardIndex}.
     *
     * @see PinUnpinCommandSystemTest#assertPinCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertPinCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                         Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertPinCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Unpins the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     *
     * @return the unpinned person
     */
    private ReadOnlyPerson unpinPerson(Model model, Index index) {
        ReadOnlyPerson targetPerson = getUnpinPerson(model, index);
        try {
            model.unpinPerson(targetPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        }
        return targetPerson;
    }

    /**
     * Unpins the person at {@code toUnpin} by creating a default {@code UnpinCommand} using {@code toUnpin} and
     * performs the same verification as {@code assertUnpinCommandSuccess(String, Model, String)}.
     *
     * @see PinUnpinCommandSystemTest#assertUnpinCommandSuccess(String, Model, String)
     */
    private void assertUnpinCommandSuccess(Index toUnpin) {
        Model expectedModel = getModel();
        ReadOnlyPerson unpinnedPerson = unpinPerson(expectedModel, toUnpin);
        String expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, unpinnedPerson);

        assertUnpinCommandSuccess(
                UnpinCommand.COMMAND_WORD + " " + toUnpin.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertUnpinCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertUnpinCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertUnpinCommandSuccess(String, Model, String)}
     * except that the browser url and selected card are expected to update accordingly depending
     * on the card at {@code expectedSelectedCardIndex}.
     *
     * @see PinUnpinCommandSystemTest#assertUnpinCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertUnpinCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                           Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertUnpinCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\SortCommandSystemTest.java
``` java
public class SortCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_SORT_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
    private static final String SORT_NAME_WORD = "name";
    private static final String SORT_PHONE_WORD = "phone";
    private static final String SORT_EMAIL_WORD = "email";
    private static final String SORT_ADDRESS_WORD = "address";

    @Test
    public void sort() {
        Model model = getModel();

        /* Case: sort by phone -> list will be sorted numerically by phone number */
        String command = SortCommand.COMMAND_WORD + " " + SORT_PHONE_WORD;
        String expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, SORT_PHONE_WORD);
        model.sortList(SORT_PHONE_WORD);
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: sort by name -> list will be sorted alphabetically by name */
        command = SortCommand.COMMAND_WORD + " " + SORT_NAME_WORD;
        expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, SORT_NAME_WORD);
        model.sortList(SORT_NAME_WORD);
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: sort by email -> list will be sorted alphabetically by email */
        command = SortCommand.COMMAND_WORD + " " + SORT_EMAIL_WORD;
        expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, SORT_EMAIL_WORD);
        model.sortList(SORT_EMAIL_WORD);
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: sort by phone -> list will be sorted alphabetically by address */
        Model modelBeforeSortPhone = getModel();
        command = SortCommand.COMMAND_WORD + " " + SORT_ADDRESS_WORD;
        expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, SORT_ADDRESS_WORD);
        model.sortList(SORT_ADDRESS_WORD);
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: undo the command sort phone -> list will be sorted alphabetically by email */
        Model modelBeforeUndo = getModel();
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, modelBeforeSortPhone);

        /* Case: redo sorting the list by address -> list will be sorted alphabetically by address  */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, modelBeforeUndo);

        /* Case: keyword is wrong -> rejected */
        assertCommandFailure("sort home address", MESSAGE_INVALID_SORT_COMMAND_FORMAT);

    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing sort command
     * and the model related components equal to the current model.
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
