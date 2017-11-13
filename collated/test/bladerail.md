# bladerail
###### /java/guitests/guihandles/MainMenuHandle.java
``` java
    /**
     * Opens the {@code UserprofileWindow} using the menu bar in {@code MainWindow}.
     */
    public void openUserProfileWindowUsingMenu() {
        clickOnMenuItemsSequentially("File", "UserProfile");
    }

```
###### /java/guitests/guihandles/MainMenuHandle.java
``` java
    /**
     * Opens the {@code UserProfileWindow} by pressing the shortcut key associated
     * with the menu bar in {@code MainWindow}.
     */
    public void openUserProfileWindowUsingAccelerator() {
        guiRobot.push(KeyCode.F2);
    }

```
###### /java/guitests/guihandles/UserProfileWindowHandle.java
``` java
/**
 * Provides a handle for the UserProfileWindow
 */
public class UserProfileWindowHandle extends StageHandle {
    public static final String USERPROFILE_WINDOW_TITLE = "User Profile";

    private static final String NAME_FIELD_ID = "#nameTextField";
    private static final String PHONE_FIELD_ID = "#phoneTextField";
    private static final String ADDRESS_FIELD_ID = "#addressTextField";
    private static final String EMAIL_FIELD_ID = "#emailTextField";
    private static final String WEBLINK_FIELD_ID = "#webLinkTextField";
    private static final String okButton_ID = "#okButton";
    private static final String cancelButton_ID = "#cancelButton";
    private static final String statusLabel_ID = "#statusLabel";

    private final TextField nameTextField;
    private final TextField phoneTextField;
    private final TextField addressTextField;
    private final TextField emailTextField;
    private final TextField webLinkTextField;
    private final Button okButton;
    private final Button cancelButton;
    private final Label statusLabel;

    public UserProfileWindowHandle(Stage userProfileWindowStage) {
        super(userProfileWindowStage);

        this.nameTextField = getChildNode(NAME_FIELD_ID);
        this.phoneTextField = getChildNode(PHONE_FIELD_ID);
        this.addressTextField = getChildNode(ADDRESS_FIELD_ID);
        this.emailTextField = getChildNode(EMAIL_FIELD_ID);
        this.webLinkTextField = getChildNode(WEBLINK_FIELD_ID);
        this.okButton = getChildNode(okButton_ID);
        this.cancelButton = getChildNode(cancelButton_ID);
        this.statusLabel = getChildNode(statusLabel_ID);
    }

    /**
     * Returns true if the UserProfile window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(USERPROFILE_WINDOW_TITLE);
    }

    /**
     * Closes the {@code UserProfileWindow} by pressing the shortcut key associated
     * with the cancel button in {@code UserProfileWindow}.
     */
    public void closeUserProfileWindowUsingCancelAccelerator() {
        guiRobot.push(KeyCode.ESCAPE);
    }

    /**
     * Closes the {@code UserProfileWindow} by pressing the shortcut key associated
     * with the ok button in {@code UserProfileWindow}.
     */
    public void closeUserProfileWindowUsingOkAccelerator() {
        guiRobot.push(KeyCode.ENTER);
    }

    public String getName() {
        return nameTextField.getText();
    }

    public String getPhone() {
        return phoneTextField.getText();
    }

    public String getAddress() {
        return addressTextField.getText();
    }

    public String getEmail() {
        return emailTextField.getText();
    }

    public String getWebLink() {
        return webLinkTextField.getText();
    }

    public TextField getAddressTextField() {
        return addressTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getPhoneTextField() {
        return phoneTextField;
    }

    public TextField getWebLinkTextField() {
        return webLinkTextField;
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    /**
     * Click the ok button
     */
    public void clickOk() {
        Platform.runLater(() -> {
            okButton.fire();
        });
    }

    /**
     * Click the cancel button
     */
    public void clickCancel() {
        Platform.runLater(() -> cancelButton.fire());
    }
}
```
###### /java/guitests/UserProfileWindowTest.java
``` java
public class UserProfileWindowTest extends AddressBookGuiTest {

    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openUserProfileWindow() {
        //use accelerator to open and close as per normal
        getCommandBox().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseDefault();

        getResultDisplay().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseDefault();

        getPersonListPanel().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseDefault();

        getBrowserPanel().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowNotOpen();

        //use menu button
        getMainMenu().openUserProfileWindowUsingMenu();
        assertUserProfileWindowOpenThenCloseDefault();
    }

    @Test
    public void closeUserProfileWindow() {
        //use accelerator to open and close with accelerator
        getCommandBox().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseAccelerator();

        getResultDisplay().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseAccelerator();

        getPersonListPanel().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseAccelerator();

        getBrowserPanel().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowNotOpen();

        //use menu button to open, close with accelerator
        getMainMenu().openUserProfileWindowUsingMenu();
        assertUserProfileWindowOpenThenCloseAccelerator();
    }

    @Test
    public void closeUserProfileWindowByClickingOk() {
        getMainMenu().openUserProfileWindowUsingMenu();
        assertUserProfileWindowOpenThenCloseOk();
        assertUserProfileWindowNotOpen();

        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseOk();
        assertUserProfileWindowNotOpen();
    }

    @Test
    public void closeUserProfileWindowByClickingCancel() {
        getMainMenu().openUserProfileWindowUsingMenu();
        assertUserProfileWindowOpenThenCloseCancel();
        assertUserProfileWindowNotOpen();

        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseCancel();
        assertUserProfileWindowNotOpen();
    }

    /**
     * Asserts that the help window is open, then closes it using the default close method
     */
    private void assertUserProfileWindowOpenThenCloseDefault() {
        assertTrue(ERROR_MESSAGE, UserProfileWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new UserProfileWindowHandle(
                guiRobot.getStage(UserProfileWindowHandle.USERPROFILE_WINDOW_TITLE)
        ).close();
        mainWindowHandle.focus();
    }

    /**
     * Asserts that the UserProfile window is open, then closes it using the accelerator
     */
    private void assertUserProfileWindowOpenThenCloseAccelerator() {
        assertTrue(ERROR_MESSAGE, UserProfileWindowHandle.isWindowPresent());

        new UserProfileWindowHandle(
                guiRobot.getStage(UserProfileWindowHandle.USERPROFILE_WINDOW_TITLE)
        ).closeUserProfileWindowUsingCancelAccelerator();
        mainWindowHandle.focus();
    }

    /**
     * Asserts that the help window is open, then closes it using the ok button
     */
    private void assertUserProfileWindowOpenThenCloseOk() {
        assertTrue(ERROR_MESSAGE, UserProfileWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new UserProfileWindowHandle(
                guiRobot.getStage(UserProfileWindowHandle.USERPROFILE_WINDOW_TITLE)
        ).clickOk();
        mainWindowHandle.focus();
    }


    /**
     * Asserts that the help window is open, then closes it using the ok button
     */
    private void assertUserProfileWindowOpenThenCloseCancel() {
        assertTrue(ERROR_MESSAGE, UserProfileWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new UserProfileWindowHandle(
                guiRobot.getStage(UserProfileWindowHandle.USERPROFILE_WINDOW_TITLE)
        ).clickCancel();
        mainWindowHandle.focus();
    }

    /**
     * Asserts that the UserProfile window isn't open.
     */
    private void assertUserProfileWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, UserProfileWindowHandle.isWindowPresent());
    }
}
```
###### /java/seedu/address/logic/commands/RemarkCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code RemarkCommand}.
 */
public class RemarkCommandTest {

    private Model model = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs(), new UserPerson());

    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Some remark").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new UserPerson());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemark_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new UserPerson());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withRemark("Some remark").build();
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new UserPerson());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));

        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
```
###### /java/seedu/address/logic/commands/ShareCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code ShareCommand}.
 */
public class ShareCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private ShareCommand shareCommand;

    @Test
    public void execute_showsAddCommandCorrectly() {
        model = new ModelManager(new AddressBook(), new UserPrefs(), new UserPerson());

        shareCommand = new ShareCommand();
        shareCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        String expectedResult = String.format(ShareCommand.MESSAGE_SUCCESS,
                ShareCommand.addCommandBuilder(
                        SampleUserPersonUtil.getDefaultSamplePerson()));

        assertCommandSuccess(shareCommand, model, expectedResult, model);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof CopyToClipboardRequestEvent);
    }

    @Test
    public void execute_addsCorrectUserPerson() {
        model = new ModelManager(new AddressBook(), new UserPrefs(), new UserPerson());
        model.updateUserPerson(WILLIAM);

        shareCommand = new ShareCommand();
        shareCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        String addCommandWilliam = ShareCommand.addCommandBuilder(WILLIAM);
        String expectedResult = String.format(ShareCommand.MESSAGE_SUCCESS, addCommandWilliam);
        assertCommandSuccess(shareCommand, model, expectedResult, model);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof CopyToClipboardRequestEvent);
    }
}

```
###### /java/seedu/address/logic/commands/SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Test
    public void execute_sortByNameSuccess() {
        List<ReadOnlyPerson> sortedListByName = getSortedTypicalPersons();
        sortUnsortedAddressBookByFilterType(ARG_NAME, sortedListByName);
    }

    @Test
    public void execute_sortByDefaultSortsByNameSuccess() {
        List<ReadOnlyPerson> sortedListByName = getSortedTypicalPersons();
        sortUnsortedAddressBookByFilterType(ARG_DEFAULT, sortedListByName);
    }

    @Test
    public void execute_sortByEmailSuccess() {
        List<ReadOnlyPerson> sortedList = Arrays.asList(new Person(ALICE),
                new Person(GEORGE), new Person(DANIEL), new Person(CARL),
                new Person(BENSON), new Person(FIONA), new Person(ELLE));
        sortUnsortedAddressBookByFilterType(ARG_EMAIL, sortedList);

    }

    @Test
    public void execute_sortByPhoneSuccess() {
        List<ReadOnlyPerson> sortedList = Arrays.asList(new Person(ALICE),
                new Person(DANIEL), new Person(ELLE), new Person(FIONA), new Person(GEORGE),
                new Person(CARL), new Person(BENSON));
        sortUnsortedAddressBookByFilterType(ARG_PHONE, sortedList);

    }

    @Test
    public void execute_sortByAddressSuccess() {
        List<ReadOnlyPerson> sortedList = Arrays.asList(new Person(DANIEL),
                new Person(ALICE), new Person(BENSON), new Person(GEORGE), new Person(FIONA),
                new Person(ELLE), new Person(CARL));
        sortUnsortedAddressBookByFilterType(ARG_ADDRESS, sortedList);

    }

    @Test
    public void execute_sortCommandSortsLastShownList() {
        List<ReadOnlyPerson> sortedList = Arrays.asList(new Person(ALICE),
                new Person(DANIEL), new Person(ELLE), new Person(FIONA), new Person(GEORGE),
                new Person(CARL), new Person(BENSON));
        model = new ModelManager(getUnsortedTypicalAddressBook(), new UserPrefs(), new UserPerson());
        expectedModel = createExpectedModel(sortedList);


        Predicate<ReadOnlyPerson> predicate = new ContainsKeywordsPredicate(Arrays.asList("Street"));
        model.updateFilteredPersonList(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        String filterType = ARG_PHONE;

        sortCommand = prepareCommand(filterType);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SUCCESS, filterType), expectedModel);
    }

    @Test
    public void equals() {
        String filterType = "";

        final SortCommand standardCommand = new SortCommand(filterType);

        // same filterTypes -> returns true
        SortCommand commandWithSameValues = new SortCommand(filterType);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different filterTypes -> returns false
        assertFalse(standardCommand.equals(new SortCommand("default")));

    }

    /**
     * Returns a {@code SortCommand} with parameters {@code filterType}.
     */
    private SortCommand prepareCommand(String filterType) {
        SortCommand sortCommand = new SortCommand(filterType);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }

    /**
     * Returns an expectedModel with a new addressBook created using the sortedList
     * @param sortedList A sortedlist of {@code ReadOnlyPerson}
     * @return
     */
    private Model createExpectedModel(List<ReadOnlyPerson> sortedList) {
        try {
            AddressBook sortedAddressBook = new AddressBook();
            sortedAddressBook.setPersons(sortedList);
            return new ModelManager(sortedAddressBook, new UserPrefs(), new UserPerson());
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
    }

    /**
     * Sorts the unsortedAddressBook by indicated filter type and matches it with the expected list order
     * @param filterType A filtertype
     * @param sortedList A sorted list of {@code ReadOnlyPerson}
     */
    private void sortUnsortedAddressBookByFilterType(String filterType, List<ReadOnlyPerson> sortedList) {
        model = new ModelManager(getUnsortedTypicalAddressBook(), new UserPrefs(), new UserPerson());
        expectedModel = createExpectedModel(sortedList);

        sortCommand = prepareCommand(filterType);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SUCCESS, filterType), expectedModel);
    }
}
```
###### /java/seedu/address/logic/commands/UpdateUserCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for UpdateUserCommand.
 */
public class UpdateUserCommandTest {

    private Model model = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs(), new UserPerson());

    @Test
    public void execute_allFieldsSpecified_success() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        UpdateUserCommand updateUserCommand = prepareCommand(descriptor);

        String expectedMessage = String.format(UpdateUserCommand.MESSAGE_UPDATE_USER_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new UserPerson());
        expectedModel.updateUserPerson(editedPerson);

        assertCommandSuccess(updateUserCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() throws Exception {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        UpdateUserCommand updateUserCommand = prepareCommand(descriptor);

        UserPerson editedPerson = new UserPerson();
        editedPerson.setName(new Name(VALID_NAME_BOB));
        editedPerson.setPhone(new Phone(VALID_PHONE_BOB));

        String expectedMessage = String.format(UpdateUserCommand.MESSAGE_UPDATE_USER_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs(),
                new UserPerson(editedPerson));

        assertCommandSuccess(updateUserCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_failure() {
        UpdateUserCommand updateUserCommand = prepareCommand(new EditPersonDescriptor());

        String expectedMessage = String.format(UpdateUserCommand.MESSAGE_NOT_UPDATED);

        assertCommandFailure(updateUserCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final UpdateUserCommand standardCommand = new UpdateUserCommand(DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        UpdateUserCommand commandWithSameValues = new UpdateUserCommand(copyDescriptor);

        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateUserCommand(DESC_BOB)));
    }

    /**
     * Returns an {@code UserCommand} with parameters {@code descriptor}
     */
    private UpdateUserCommand prepareCommand(EditPersonDescriptor descriptor) {
        UpdateUserCommand updateUserCommand = new UpdateUserCommand(descriptor);
        updateUserCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return updateUserCommand;
    }
}
```
###### /java/seedu/address/logic/parser/SortCommandParserTest.java
``` java
public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_valid_arguments() throws Exception {

        // FilterType: Name
        assertValidFilterType(ARG_NAME_ALIAS, ARG_NAME);
        assertValidFilterType(ARG_NAME, ARG_NAME);

        // FilterType: Email
        assertValidFilterType(ARG_EMAIL, ARG_EMAIL);
        assertValidFilterType(ARG_EMAIL_ALIAS, ARG_EMAIL);

        // FilterType: Phone
        assertValidFilterType(ARG_PHONE, ARG_PHONE);
        assertValidFilterType(ARG_PHONE_ALIAS, ARG_PHONE);

        // FilterType: Address
        assertValidFilterType(ARG_ADDRESS, ARG_ADDRESS);
        assertValidFilterType(ARG_ADDRESS_ALIAS, ARG_ADDRESS);

        // FilterType: Default.
        assertValidFilterType(ARG_DEFAULT, ARG_DEFAULT);

        // No filterType argument: Should set to default
        assertValidFilterType("", ARG_DEFAULT);

        // filterType can be in any case:
        assertValidFilterType("nAMe", ARG_NAME);
        assertValidFilterType("NaME", ARG_NAME);
        assertValidFilterType("eMaIl", ARG_EMAIL);
        assertValidFilterType("PHone", ARG_PHONE);
        assertValidFilterType("aDDress", ARG_ADDRESS);
        assertValidFilterType("PHONE", ARG_PHONE);

    }

    @Test
    public void parse_invalid_arguments() throws Exception {
        String filterType = "abc";
        String userInput = SortCommand.COMMAND_WORD + " " + filterType;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);

        filterType = "olosw";
        userInput = SortCommand.COMMAND_WORD + " " + filterType;
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    /**
     * Asserts if the input filterType returns a SortCommand with the expected filterType
     * @param inputFilterType
     * @param expectedFilterType
     */
    public void assertValidFilterType(String inputFilterType, String expectedFilterType) {
        String userInput = SortCommand.COMMAND_WORD + " " + inputFilterType;
        SortCommand expectedCommand = new SortCommand(expectedFilterType);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### /java/seedu/address/logic/parser/UpdateUserCommandParserTest.java
``` java
public class UpdateUserCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateUserCommand.MESSAGE_USAGE);

    private UpdateUserCommandParser parser = new UpdateUserCommandParser();

    @Test
    public void parse_missingParts_failure() {

        // no field specified
        assertParseFailure(parser, "", UpdateUserCommand.MESSAGE_NOT_UPDATED);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // Incorrect parameters
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, INVALID_EMAIL_DESC, Email.MESSAGE_EMAIL_CONSTRAINTS); // invalid email
        assertParseFailure(parser, INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS); // invalid address

        // invalid phone followed by valid email
        assertParseFailure(parser, INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = PHONE_DESC_BOB
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .build();
        UpdateUserCommand expectedCommand = new UpdateUserCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        UpdateUserCommand expectedCommand = new UpdateUserCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        String userInput = NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        UpdateUserCommand expectedCommand = new UpdateUserCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new UpdateUserCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new UpdateUserCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new UpdateUserCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // weblink
        userInput = WEB_LINK_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withWebLinks(VALID_WEB_LINK_AMY).build();
        expectedCommand = new UpdateUserCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### /java/seedu/address/model/person/UserPersonTest.java
``` java
public class UserPersonTest {


    private UserPerson userPerson;

    @Test
    public void modifyUserPerson_returnsCorrectUserPerson() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs(), new UserPerson());
        userPerson = model.getUserPerson();
        model.updateUserPerson(JAMES);

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(),
                getTypicalUserPerson());
        assertEquals(model, expectedModel);
    }

    @Test
    public void equals() {
        userPerson = new UserPerson();
        UserPerson expectedUserPerson = getTypicalUserPerson();
        assertFalse(userPerson.equals(expectedUserPerson));

        userPerson = new UserPerson(JAMES);
        assertTrue(userPerson.equals(expectedUserPerson));

        userPerson = new UserPerson(WILLIAM);
        assertFalse(userPerson.equals(expectedUserPerson));
    }
}
```
###### /java/seedu/address/storage/StorageManagerTest.java
``` java
    @Test
    public void handleUserProfileChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"),
                new XmlUserProfileStorageExceptionThrowingStub("dummy"));
        storage.handleUserPersonChangedEvent(new UserPersonChangedEvent(
                new UserPerson(getTypicalUserPerson())));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }
```
###### /java/seedu/address/storage/XmlUserProfileStorageTest.java
``` java
public class XmlUserProfileStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlUserProfileStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readUserProfile_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readUserProfile(null);
    }

    private java.util.Optional<UserPerson> readUserProfile(String filePath) throws Exception {
        return new XmlUserProfileStorage(filePath).readUserProfile(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readUserProfile("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readUserProfile("NotXmlFormatUserProfile.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveUserProfile_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempUserProfile.xml";
        UserPerson original = getTypicalUserPerson();
        XmlUserProfileStorage xmlUserProfileStorage = new XmlUserProfileStorage(filePath);

        //Save in new file and read back
        xmlUserProfileStorage.saveUserPerson(original, filePath);
        UserPerson readBack = xmlUserProfileStorage.readUserProfile(filePath).get();
        assertEquals(original, new UserPerson(readBack));

        //Modify data, overwrite exiting file, and read back
        original.update(new Person(HOON));
        xmlUserProfileStorage.saveUserPerson(original, filePath);
        readBack = xmlUserProfileStorage.readUserProfile(filePath).get();
        assertEquals(original, new UserPerson(readBack));

        //Save and read without specifying file path
        original.update(new Person(IDA));
        xmlUserProfileStorage.saveUserPerson(original); //file path not specified
        readBack = xmlUserProfileStorage.readUserProfile().get(); //file path not specified
        assertEquals(original, new UserPerson(readBack));

    }

    @Test
    public void saveUserProfile_nullUserProfile_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUserPerson(null, "SomeFile.xml");
    }

    /**
     * Saves {@code UserProfile} at the specified {@code filePath}.
     */
    private void saveUserPerson(UserPerson userPerson, String filePath) {
        try {
            new XmlUserProfileStorage(filePath).saveUserPerson(userPerson, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveUserPerson_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveUserPerson(new UserPerson(), null);
    }


}
```
###### /java/seedu/address/TestApp.java
``` java
    public static final String SAVE_LOCATION_FOR_TESTING_USERPROFILE = TestUtil
            .getFilePathInSandboxFolder("sampleUserProfile.xml");
```
###### /java/seedu/address/TestApp.java
``` java
    protected String saveFileLocationUserProfile = SAVE_LOCATION_FOR_TESTING_USERPROFILE;
```
###### /java/seedu/address/TestApp.java
``` java
        userPrefs.setUserProfileFilePath(saveFileLocationUserProfile);
```
###### /java/seedu/address/TestApp.java
``` java
    @Override
    protected UserPerson initUserPerson(UserProfileStorage storage) {
        UserPerson userPerson = super.initUserPerson(storage);
        userPerson.setName(SampleUserPersonUtil.getDefaultSamplePerson().getName());
        userPerson.setPhone(SampleUserPersonUtil.getDefaultSamplePerson().getPhone());
        userPerson.setEmail(SampleUserPersonUtil.getDefaultSamplePerson().getEmail());
        userPerson.setAddress(SampleUserPersonUtil.getDefaultSamplePerson().getAddress());
        userPerson.setWebLinks(SampleUserPersonUtil.getDefaultSamplePerson().getWebLinks());

        return userPerson;

    }
```
###### /java/seedu/address/testutil/TypicalPersons.java
``` java
    /**
     * Returns an {@code AddressBook} with all the typical persons in sorted order.
     */
    public static AddressBook getSortedTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getSortedTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons in unsorted order.
     */
    public static AddressBook getUnsortedTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getUnsortedTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyPerson> getUnsortedTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, DANIEL, CARL, GEORGE, FIONA, ELLE));
    }

    public static List<ReadOnlyPerson> getSortedTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
```
###### /java/seedu/address/testutil/TypicalUserPerson.java
``` java
/**
 * A utility class containing a list of {@code UserPerson} objects to be used in tests.
 */
public class TypicalUserPerson {

    public static final EditCommand.EditPersonDescriptor DESC_JAMES;
    public static final EditCommand.EditPersonDescriptor DESC_WILLIAM;

    public static final ReadOnlyPerson JAMES = new PersonBuilder().withName("James Wong")
            .withAddress("456 Rochor Ave 3").withEmail("james@gmail.com")
            .withPhone("84712836")
            .withWebLinks("jameswong@facebook.com").build();

    public static final ReadOnlyPerson WILLIAM = new PersonBuilder().withName("William Sim")
            .withAddress("112 Clementi Ave 4").withEmail("william@hotmail.com")
            .withPhone("91332588")
            .withWebLinks("williamsim@facebook.com").build();

    private TypicalUserPerson() {} // prevents instantiation

    public static UserPerson getTypicalUserPerson() {
        return new UserPerson(JAMES);
    }

    static {
        DESC_JAMES = new EditPersonDescriptorBuilder(JAMES).build();
        DESC_WILLIAM = new EditPersonDescriptorBuilder(WILLIAM).build();
    }
}
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertUserProfileWindowEquals(UserProfileWindowHandle userProfileWindowHandle,
                                                     UserPerson userPerson) {
        assertEquals(userProfileWindowHandle.getName(), userPerson.getName().toString());
        assertEquals(userProfileWindowHandle.getPhone(), userPerson.getPhone().toString());
        assertEquals(userProfileWindowHandle.getAddress(), userPerson.getAddress().toString());
        assertEquals(userProfileWindowHandle.getEmail(), userPerson.getEmailAsText());
        assertEquals(userProfileWindowHandle.getWebLink(), userPerson.getWebLinksAsText());
    }

    public static void assertUserProfileWindowStatusLabelEquals(
            UserProfileWindowHandle userProfileWindowHandle, String text) {
        assertEquals(text, userProfileWindowHandle.getStatusLabel().getText());
    }
```
###### /java/seedu/address/ui/UserProfileWindowTest.java
``` java
public class UserProfileWindowTest extends GuiUnitTest {

    private Model model = new ModelManager();


    private UserPerson userPerson = model.getUserPerson();
    private UserProfileWindow userProfileWindow;
    private UserProfileWindowHandle userProfileWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> userProfileWindow = new UserProfileWindow(model.getUserPerson()));
        Stage userProfileWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(
                userProfileWindow.getRoot().getScene()));
        FxToolkit.showStage();
        userProfileWindowHandle = new UserProfileWindowHandle(userProfileWindowStage);
    }

    @Test
    public void display() {
        assertUserProfileWindowEquals(userProfileWindowHandle, userPerson);
    }

    @Test
    public void updatedDisplayIsCorrect() throws Exception {
        userProfileWindowHandle.clickOk();
        userPerson.update(WILLIAM);
        setUp();

        assertUserProfileWindowEquals(userProfileWindowHandle, userPerson);
    }

    @Test
    public void updateUserPersonSuccess() throws Exception {
        userPerson = new UserPerson();
        UserPerson james = getTypicalUserPerson();
        setNameTextField(james.getName().toString());
        setAddressTextField(james.getAddress().toString());
        setPhoneTextField(james.getPhone().toString());
        setEmailTextField(james.getEmailAsText());
        setWebLinkTextField(james.getWebLinksAsText());

        userProfileWindowHandle.clickOk();
        setUp();

        assertUserProfileWindowEquals(userProfileWindowHandle, james);
    }

    @Test
    public void updateUserPersonInvalidField() {
        userPerson = new UserPerson();
        UserPerson william = new UserPerson(WILLIAM);
        setNameTextField("");
        setAddressTextField("");
        setPhoneTextField("");
        setEmailTextField("");
        setWebLinkTextField("");

        // Invalid name
        userProfileWindowHandle.clickOk();
        guiRobot.sleep(250);
        assertUserProfileWindowStatusLabelEquals(userProfileWindowHandle,
                MESSAGE_NAME_CONSTRAINTS);

        // Invalid Email
        setNameTextField(william.getName().toString());
        userProfileWindowHandle.clickOk();
        guiRobot.sleep(250);
        assertUserProfileWindowStatusLabelEquals(userProfileWindowHandle,
                MESSAGE_EMAIL_CONSTRAINTS);

        // Invalid Phone
        setEmailTextField(william.getEmailAsText());
        setPhoneTextField("TTT");
        userProfileWindowHandle.clickOk();
        guiRobot.sleep(250);
        assertEquals(MESSAGE_PHONE_CONSTRAINTS,
                userProfileWindowHandle.getStatusLabel().getText());

        // Invalid Email again
        setEmailTextField("abc");
        userProfileWindowHandle.clickOk();
        guiRobot.sleep(250);
        assertUserProfileWindowStatusLabelEquals(userProfileWindowHandle,
                MESSAGE_EMAIL_CONSTRAINTS);

        // Address is always valid
        setEmailTextField(william.getEmailAsText());
        setAddressTextField(william.getAddress().toString());
        userProfileWindowHandle.clickOk();

        guiRobot.sleep(250);
        assertUserProfileWindowStatusLabelEquals(userProfileWindowHandle,
                MESSAGE_PHONE_CONSTRAINTS);

        // All values are now correct
        setPhoneTextField(william.getPhone().toString());
        setWebLinkTextField(william.getWebLinksAsText());
        userProfileWindowHandle.clickOk();
        guiRobot.sleep(250);

        try {
            setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Verify profile is now correctly saved
        assertUserProfileWindowEquals(userProfileWindowHandle, william);
    }

    @Test
    public void cancelButtonDoesNotUpdate() throws Exception {
        userPerson = new UserPerson();
        UserPerson william = new UserPerson(WILLIAM);
        setNameTextField(william.getName().toString());
        setAddressTextField(william.getAddress().toString());
        setPhoneTextField(william.getPhone().toString());
        setEmailTextField(william.getEmailAsText());
        setWebLinkTextField(william.getWebLinksAsText());

        userProfileWindowHandle.clickCancel();
        setUp();

        assertUserProfileWindowEquals(userProfileWindowHandle, userPerson);
    }

    /**
     * Sets Name Text Field in UserProfileHandle
     * @param text
     */
    private void setNameTextField(String text) {
        userProfileWindowHandle.getNameTextField().setText(text);
    }

    /**
     * Sets Address Text Field in UserProfileHandle
     * @param text
     */
    private void setAddressTextField(String text) {
        userProfileWindowHandle.getAddressTextField().setText(text);
    }

    /**
     * Sets Phone Text Field in UserProfileHandle
     * @param text
     */
    private void setPhoneTextField(String text) {
        userProfileWindowHandle.getPhoneTextField().setText(text);
    }

    /**
     * Sets Email Text Field in UserProfileHandle
     * @param text
     */
    private void setEmailTextField(String text) {
        userProfileWindowHandle.getEmailTextField().setText(text);
    }

    /**
     * Sets WebLink Text Field in UserProfileHandle
     * @param text
     */
    private void setWebLinkTextField(String text) {
        userProfileWindowHandle.getWebLinkTextField().setText(text);
    }
}
```
