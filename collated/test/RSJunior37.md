# RSJunior37
###### \java\guitests\AddressBookGuiTest.java
``` java
    protected ProfilePanelHandle getProfilePanel() {
        return mainWindowHandle.getProfilePanelHandle();
    }
```
###### \java\guitests\guihandles\MainWindowHandle.java
``` java
    public ProfilePanelHandle getProfilePanelHandle() {
        return profilePanelHandle;
    }

    public InsuranceListPanelHandle getInsuranceListPanelHandle() {
        return insuranceListPanelHandle;
    }
```
###### \java\guitests\guihandles\ProfilePanelHandle.java
``` java
/**
 * A handler for the {@code ProfilePanel} of the UI.
 */
public class ProfilePanelHandle extends NodeHandle<Node> {

    public static final String PROFILE_ID = "#profilePanel";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String DOB_FIELD_ID = "#dob";
    private static final String GENDER_FIELD_ID = "#gender";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";

    private final Label nameLabel;
    private final Label addressLabel;
    private final Label dobLabel;
    private final Label genderLabel;
    private final Label phoneLabel;
    private final Label emailLabel;

    public ProfilePanelHandle(Node profilePanelNode) {
        super(profilePanelNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.dobLabel = getChildNode(DOB_FIELD_ID);
        this.genderLabel = getChildNode(GENDER_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);

    }
    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getDateOfBirth() {
        return dobLabel.getText();
    }

    public String getGender() {
        return genderLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

}
```
###### \java\guitests\HelpWindowTest.java
``` java
        getProfilePanel().click();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

```
###### \java\seedu\address\logic\commands\PartialFindCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class PartialFindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameStartsWithKeywordsPredicate firstPredicate =
                new NameStartsWithKeywordsPredicate(Collections.singletonList("first"));
        NameStartsWithKeywordsPredicate secondPredicate =
                new NameStartsWithKeywordsPredicate(Collections.singletonList("second"));

        PartialFindCommand findFirstCommand = new PartialFindCommand(firstPredicate);
        PartialFindCommand findSecondCommand = new PartialFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        PartialFindCommand findFirstCommandCopy = new PartialFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PartialFindCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PartialFindCommand command = prepareCommand("Ca Ell Fio");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_multipleKeywords_onlyReturnFirstNameMatch() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PartialFindCommand command = prepareCommand("Ku Mey Fi");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(FIONA));
    }



    /**
     * Parses {@code userInput} into a {@code PartialFindCommand}.
     */
    private PartialFindCommand prepareCommand(String userInput) {
        PartialFindCommand command =
                new PartialFindCommand(new NameStartsWithKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(PartialFindCommand command,
                                      String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\ui\ProfilePanelTest.java
``` java
public class ProfilePanelTest extends GuiUnitTest {
    private PersonNameClickedEvent personNameClickedEventStub;

    private ProfilePanel profilePanel;
    private ProfilePanelHandle profilePanelHandle;

    @Before
    public void setUp() {
        InsurancePerson insurancePerson = new InsurancePerson(ALICE);
        personNameClickedEventStub = new PersonNameClickedEvent(insurancePerson);

        guiRobot.interact(() -> profilePanel = new ProfilePanel());
        uiPartRule.setUiPart(profilePanel);

        profilePanelHandle = new ProfilePanelHandle(profilePanel.getRoot());

    }

    @Test
    public void display() throws Exception {
        // default profile page
        final String expectedDefaultName = ProfilePanel.DEFAULT_MESSAGE;

        assertEquals(expectedDefaultName, profilePanelHandle.getName());
        assertNull(profilePanelHandle.getEmail());
        assertNull(profilePanelHandle.getPhone());
        assertNull(profilePanelHandle.getDateOfBirth());
        assertNull(profilePanelHandle.getAddress());

        // select Stub Person
        postNow(personNameClickedEventStub);

        ReadOnlyPerson expectedSelectedPerson = ALICE;

        assertEquals(expectedSelectedPerson.getName().toString(), profilePanelHandle.getName());
        assertEquals(expectedSelectedPerson.getEmail().toString(), profilePanelHandle.getEmail());
        assertEquals(expectedSelectedPerson.getPhone().toString(), profilePanelHandle.getPhone());
        assertEquals(expectedSelectedPerson.getDateOfBirth().toString(), profilePanelHandle.getDateOfBirth());
        assertEquals(expectedSelectedPerson.getAddress().toString(), profilePanelHandle.getAddress());

    }
}
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        try {
            assertEquals("", getCommandBox().getInput());
            assertEquals("", getResultDisplay().getText());
            assertListMatching(getPersonListPanel(), getModel().getFilteredPersonList());
            assertEquals("./" + testApp.getStorageSaveLocation(), getStatusBarFooter().getSaveLocation());
            assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());

            assertEquals(ProfilePanel.DEFAULT_MESSAGE, getProfilePanelHandle().getName());
            assertNull(getProfilePanelHandle().getDateOfBirth());

        } catch (Exception e) {
            throw new AssertionError("Starting state is wrong.", e);
        }
    }
```
