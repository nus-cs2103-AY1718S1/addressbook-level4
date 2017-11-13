# khooroko
###### \java\guitests\guihandles\InfoPanelHandle.java
``` java
/**
 * A handler for the {@code InfoPanel} of the UI.
 */
public class InfoPanelHandle extends NodeHandle<Node> {
    public static final String INFO_PANEL_ID = "#infoPanelPlaceholder";

    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String HANDPHONE_FIELD_ID = "#handphone";
    private static final String HOME_PHONE_FIELD_ID = "#homePhone";
    private static final String OFFICE_PHONE_FIELD_ID = "#officePhone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String POSTAL_CODE_FIELD_ID = "#postalCode";
    private static final String CLUSTER_FIELD_ID = "#cluster";
    private static final String DEBT_FIELD_ID = "#debt";
    private static final String TOTAL_DEBT_FIELD_ID = "#totalDebt";
    private static final String INTEREST_FIELD_ID = "#interest";
    private static final String DATE_BORROW_FIELD_ID = "#dateBorrow";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String DATE_REPAID_FIELD_ID = "#dateRepaid";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String ADDRESS_FIELD_FIELD_ID = "#addressField";
    private static final String HANDPHONE_FIELD_FIELD_ID = "#handphoneField";
    private static final String HOME_PHONE_FIELD_FIELD_ID = "#homePhoneField";
    private static final String OFFICE_PHONE_FIELD_FIELD_ID = "#officePhoneField";
    private static final String EMAIL_FIELD_FIELD_ID = "#emailField";
    private static final String POSTAL_CODE_FIELD_FIELD_ID = "#postalCodeField";
    private static final String CLUSTER_FIELD_FIELD_ID = "#clusterField";
    private static final String DEBT_FIELD_FIELD_ID = "#debtField";
    private static final String TOTAL_DEBT_FIELD_FIELD_ID = "#totalDebtField";
    private static final String INTEREST_FIELD_FIELD_ID = "#interestField";
    private static final String DATE_BORROW_FIELD_FIELD_ID = "#dateBorrowField";
    private static final String DEADLINE_FIELD_FIELD_ID = "#deadlineField";
    private static final String DATE_REPAID_FIELD_FIELD_ID = "#dateRepaidField";

    private final Label nameLabel;
    private final Label addressLabel;
    private final Label handphoneLabel;
    private final Label homePhoneLabel;
    private final Label officePhoneLabel;
    private final Label emailLabel;
    private final Label postalCodeLabel;
    private final Label clusterLabel;
    private final Label debtLabel;
    private final Label totalDebtLabel;
    private final Label interestLabel;
    private final Label dateBorrowLabel;
    private final Label deadlineLabel;
    private final Label dateRepaidLabel;
    private final List<Label> tagLabels;
    private final Text addressText;
    private final Text handphoneText;
    private final Text homePhoneText;
    private final Text officePhoneText;
    private final Text emailText;
    private final Text postalCodeText;
    private final Text clusterText;
    private final Text debtText;
    private final Text totalDebtText;
    private final Text interestText;
    private final Text dateBorrowText;
    private final Text deadlineText;
    private final Text dateRepaidText;

    private String lastRememberedName;
    private String lastRememberedAddress;
    private String lastRememberedHandphone;
    private String lastRememberedHomePhone;
    private String lastRememberedOfficePhone;
    private String lastRememberedEmail;
    private String lastRememberedPostalCode;
    private String lastRememberedCluster;
    private String lastRememberedDebt;
    private String lastRememberedTotalDebt;
    private String lastRememberedInterest;
    private String lastRememberedDateBorrow;
    private String lastRememberedDeadline;
    private String lastRememberedDateRepaid;
    private List<String> lastRememberedTags;

    private NearbyPersonListPanelHandle nearbyPersonListPanel;

    public InfoPanelHandle(Node infoPanelNode) {
        super(infoPanelNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.handphoneLabel = getChildNode(HANDPHONE_FIELD_ID);
        this.homePhoneLabel = getChildNode(HOME_PHONE_FIELD_ID);
        this.officePhoneLabel = getChildNode(OFFICE_PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.postalCodeLabel = getChildNode(POSTAL_CODE_FIELD_ID);
        this.clusterLabel = getChildNode(CLUSTER_FIELD_ID);
        this.debtLabel = getChildNode(DEBT_FIELD_ID);
        this.totalDebtLabel = getChildNode(TOTAL_DEBT_FIELD_ID);
        this.interestLabel = getChildNode(INTEREST_FIELD_ID);
        this.dateBorrowLabel = getChildNode(DATE_BORROW_FIELD_ID);
        this.deadlineLabel = getChildNode(DEADLINE_FIELD_ID);
        this.dateRepaidLabel = getChildNode(DATE_REPAID_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

        this.addressText = getChildNode(ADDRESS_FIELD_FIELD_ID);
        this.handphoneText = getChildNode(HANDPHONE_FIELD_FIELD_ID);
        this.homePhoneText = getChildNode(HOME_PHONE_FIELD_FIELD_ID);
        this.officePhoneText = getChildNode(OFFICE_PHONE_FIELD_FIELD_ID);
        this.emailText = getChildNode(EMAIL_FIELD_FIELD_ID);
        this.postalCodeText = getChildNode(POSTAL_CODE_FIELD_FIELD_ID);
        this.clusterText = getChildNode(CLUSTER_FIELD_FIELD_ID);
        this.debtText = getChildNode(DEBT_FIELD_FIELD_ID);
        this.totalDebtText = getChildNode(TOTAL_DEBT_FIELD_FIELD_ID);
        this.interestText = getChildNode(INTEREST_FIELD_FIELD_ID);
        this.dateBorrowText = getChildNode(DATE_BORROW_FIELD_FIELD_ID);
        this.deadlineText = getChildNode(DEADLINE_FIELD_FIELD_ID);
        this.dateRepaidText = getChildNode(DATE_REPAID_FIELD_FIELD_ID);

        Platform.runLater(() -> {
            nearbyPersonListPanel = new NearbyPersonListPanelHandle(getChildNode(NearbyPersonListPanelHandle
                    .NEARBY_PERSON_LIST_VIEW_ID));
        });
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getHandphone() {
        return handphoneLabel.getText();
    }

    public String getHomePhone() {
        return homePhoneLabel.getText();
    }

    public String getOfficePhone() {
        return officePhoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getDebt() {
        return debtLabel.getText();
    }

    public String getTotalDebt() {
        return totalDebtLabel.getText();
    }

    public String getInterest() {
        return interestLabel.getText();
    }

    public String getDateBorrow() {
        return dateBorrowLabel.getText();
    }

    public String getDateRepaid() {
        return dateRepaidLabel.getText();
    }

    public String getPostalCode() {
        return  postalCodeLabel.getText();
    }

    public String getCluster() {
        return clusterLabel.getText();
    }

    public String getDeadline() {
        return deadlineLabel.getText();
    }

    public String getAddressField() {
        return addressText.getText();
    }

    public String getHandphoneField() {
        return handphoneText.getText();
    }

    public String getHomePhoneField() {
        return homePhoneText.getText();
    }

    public String getOfficePhoneField() {
        return officePhoneText.getText();
    }

    public String getEmailField() {
        return emailText.getText();
    }

    public String getPostalCodeField() {
        return postalCodeText.getText();
    }

    public String getClusterField() {
        return clusterText.getText();
    }

    public String getDebtField() {
        return debtText.getText();
    }

    public String getTotalDebtField() {
        return totalDebtText.getText();
    }

    public String getInterestField() {
        return interestText.getText();
    }

    public String getDateBorrowField () {
        return dateBorrowText.getText();
    }

    public String getDeadlineField() {
        return deadlineText.getText();
    }

    public String getDateRepaidField () {
        return dateRepaidText.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public NearbyPersonListPanelHandle getNearbyPersonListPanel() {
        return nearbyPersonListPanel;
    }

    /**
     * Remember the details of the person that was last selected
     */
    public void rememberSelectedPersonDetails() {
        lastRememberedAddress = getAddress();
        lastRememberedDebt = getDebt();
        lastRememberedTotalDebt = getTotalDebt();
        lastRememberedInterest = getInterest();
        lastRememberedEmail = getEmail();
        lastRememberedName = getName();
        lastRememberedHandphone = getHandphone();
        lastRememberedHomePhone = getHomePhone();
        lastRememberedOfficePhone = getOfficePhone();
        lastRememberedPostalCode = getPostalCode();
        lastRememberedCluster = getCluster();
        lastRememberedTags = getTags();
        lastRememberedDateBorrow = getDateBorrow();
        lastRememberedDeadline = getDeadline();
        lastRememberedDateRepaid = getDateRepaid();
    }

    /**
     * Returns true if the selected {@code Person} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonDetails()} call.
     */
    public boolean isSelectedPersonChanged() {
        return !getName().equals(lastRememberedName)
                || !getAddress().equals(lastRememberedAddress)
                || !getHandphone().equals(lastRememberedHandphone)
                || !getHomePhone().equals(lastRememberedHomePhone)
                || !getOfficePhone().equals(lastRememberedOfficePhone)
                || !getDebt().equals(lastRememberedDebt)
                || !getTotalDebt().equals(lastRememberedTotalDebt)
                || !getInterest().equals(lastRememberedInterest)
                || !getEmail().equals(lastRememberedEmail)
                || !getPostalCode().equals(lastRememberedPostalCode)
                || !getCluster().equals(lastRememberedCluster)
                || !getDateBorrow().equals(lastRememberedDateBorrow)
                || !getDeadline().equals(lastRememberedDeadline)
                || !getDateRepaid().equals(lastRememberedDateRepaid)
                || !getTags().equals(lastRememberedTags);
    }

}
```
###### \java\guitests\guihandles\NearbyPersonListPanelHandle.java
``` java
/**
 * Provides a handle for {@code NearbyPersonListPanel} containing the list of {@code PersonCard}.
 */
public class NearbyPersonListPanelHandle extends NodeHandle<ListView<PersonCard>> {
    public static final String NEARBY_PERSON_LIST_VIEW_ID = "#nearbyPersonListView";

    private Optional<PersonCard> lastRememberedSelectedPersonCard;

    public NearbyPersonListPanelHandle(ListView<PersonCard> nearbyPersonListPanelNode) {
        super(nearbyPersonListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PersonCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public PersonCardHandle getHandleToSelectedCard() {
        List<PersonCard> personList = getRootNode().getSelectionModel().getSelectedItems();

        if (personList.size() != 1) {
            throw new AssertionError("Person list size expected 1.");
        }

        return new PersonCardHandle(personList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<PersonCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public void navigateToCard(ReadOnlyPerson person) {
        List<PersonCard> cards = getRootNode().getItems();
        Optional<PersonCard> matchingCard = cards.stream().filter(card -> card.person.equals(person)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Person does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the person card handle of a person associated with the {@code index} in the list.
     */
    public PersonCardHandle getPersonCardHandle(int index) {
        return getPersonCardHandle(getRootNode().getItems().get(index).person);
    }

    /**
     * Returns the {@code PersonCardHandle} of the specified {@code person} in the list.
     */
    public PersonCardHandle getPersonCardHandle(ReadOnlyPerson person) {
        Optional<PersonCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.person.equals(person))
                .map(card -> new PersonCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Person does not exist."));
    }

    /**
     * Selects the {@code PersonCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code PersonCard} in the list.
     */
    public void rememberSelectedPersonCard() {
        List<PersonCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPersonCard = Optional.empty();
        } else {
            lastRememberedSelectedPersonCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<PersonCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPersonCard.isPresent();
        } else {
            return !lastRememberedSelectedPersonCard.isPresent()
                    || !lastRememberedSelectedPersonCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
```
###### \java\guitests\NearbySelectionIntegrationTest.java
``` java
public class NearbySelectionIntegrationTest extends AddressBookGuiTest {

    @Test
    public void handleNearbyPersonNotInCurrentListEvent() {
        NearbyPersonNotInCurrentListEvent nearbyPersonNotInCurrentListEventStub =
                new NearbyPersonNotInCurrentListEvent(new PersonCard(CARL, 2));
        PersonListPanelHandle originalPersonListPanelHandle = mainWindowHandle.getPersonListPanel();
        InfoPanelHandle originalInfoPanelHandle = mainWindowHandle.getInfoPanel();
        postNow(nearbyPersonNotInCurrentListEventStub);
        mainWindowHandle.updateChangeInList();
        PersonCardHandle expectedCard = mainWindowHandle.getPersonListPanel()
                .getPersonCardHandle(INDEX_THIRD_PERSON.getZeroBased());
        PersonCardHandle selectedCard = mainWindowHandle.getPersonListPanel().getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
        assertNotSame(originalPersonListPanelHandle, mainWindowHandle.getPersonListPanel());
        assertNotSame(originalInfoPanelHandle, mainWindowHandle.getInfoPanel());
    }

    @Test
    public void handleNearbyPersonPanelSelectionChangeEvent() {
        JumpToListRequestEvent jumpToListRequestEventStub = new JumpToListRequestEvent(INDEX_SECOND_PERSON);
        NearbyPersonPanelSelectionChangedEvent nearbyPersonPanelSelectionChangedEventStub =
                new NearbyPersonPanelSelectionChangedEvent(new PersonCard(CARL, 2));
        postNow(jumpToListRequestEventStub); // Benson is selected
        postNow(nearbyPersonPanelSelectionChangedEventStub); // Carl should be selected
        mainWindowHandle.updateChangeInList();
        assertCardEquals(mainWindowHandle.getPersonListPanel().getPersonCardHandle(CARL),
                mainWindowHandle.getPersonListPanel().getHandleToSelectedCard());
        assertInfoDisplaysPerson(nearbyPersonPanelSelectionChangedEventStub.getNewSelection().person,
                mainWindowHandle.getInfoPanel());
    }

}
```
###### \java\seedu\address\logic\commands\BanCommandTest.java
``` java
    @Test
    public void execute_noIndexPersonSelected_success() {
        try {
            model.updateSelectedPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
            ReadOnlyPerson personToBan = model.getSelectedPerson();
            BanCommand banCommand = prepareCommand();

            String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                    + String.format(BanCommand.MESSAGE_BAN_PERSON_SUCCESS, personToBan.getName());

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.addBlacklistedPerson(personToBan);

            assertCommandSuccess(banCommand, model, expectedMessage, expectedModel);
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    @Test
    public void execute_noIndexNoSelection_failure() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_NO_PERSON_SELECTED);
        BanCommand banCommand = prepareCommand();
        fail(UNEXPECTED_EXECTION);
    }

```
###### \java\seedu\address\logic\commands\BorrowCommandTest.java
``` java
    @Test
    public void execute_successfulBorrowing_withoutIndex() {
        model.updateSelectedPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        ReadOnlyPerson personWhoBorrowed = model.getSelectedPerson();
        String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(BorrowCommand.MESSAGE_BORROW_SUCCESS,
                personWhoBorrowed.getName().toString(), VALID_DEBT_FIGURE);
        try {
            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.addDebtToPerson(personWhoBorrowed, new Debt(VALID_DEBT_FIGURE));

            BorrowCommand borrowCommand = prepareCommand(new Debt(VALID_DEBT_FIGURE));
            assertCommandSuccess(borrowCommand, model, expectedMessage, expectedModel);
        } catch (IllegalValueException | PersonNotFoundException | CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_noIndexNoSelection_failure() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_NO_PERSON_SELECTED);
        prepareCommand(new Debt(VALID_DEBT_FIGURE));
        fail(UNEXPECTED_EXECTION);
    }

```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
    @Test
    public void execute_noIndexPersonSelected_success() {
        try {
            model.updateSelectedPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
            ReadOnlyPerson personToDelete = model.getSelectedPerson();
            DeleteCommand deleteCommand = prepareCommand();

            String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                    personToDelete.getName());

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.deletePerson(personToDelete);

            assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
        } catch (PersonNotFoundException | CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_noIndexNoSelection_failure() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_NO_PERSON_SELECTED);
        prepareCommand();
        fail(UNEXPECTED_EXECTION);
    }

```
###### \java\seedu\address\logic\commands\EditCommandTest.java
``` java
    @Test
    public void execute_noIndexPersonSelected_success() {
        try {
            Person editedPerson = new PersonBuilder().build();
            EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
            model.updateSelectedPerson(model.getFilteredPersonList().get(0));
            EditCommand editCommand = prepareCommand(descriptor);

            String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                    + String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson.getName());

            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

            assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        } catch (DuplicatePersonException | PersonNotFoundException | CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_noIndexNoSelection_failure() throws Exception {
        ReadOnlyPerson personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_NO_PERSON_SELECTED);
        prepareCommand(new EditPersonDescriptorBuilder(personInList).build());
        fail(UNEXPECTED_EXECTION);
    }

```
###### \java\seedu\address\logic\commands\NearbyCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code NearbyCommand}.
 */
public class NearbyCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ListObserver.init(model);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.updateSelectedPerson(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()));
        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_SECOND_PERSON);
    }

    @Test
    public void execute_noPersonSelected_failure() {
        assertExecutionFailure(INDEX_FIRST_PERSON, Messages.MESSAGE_NO_PERSON_SELECTED);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        model.updateSelectedPerson(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()));
        Index outOfBoundsIndex = Index.fromOneBased(model.getNearbyPersons().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, String.format(NearbyCommand.MESSAGE_INVALID_NEARBY_INDEX,
                model.getNearbyPersons().size()));
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        model.updateSelectedPerson(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()));

        assertExecutionSuccess(INDEX_SECOND_PERSON);
    }

    @Test
    public void equals() {
        NearbyCommand nearbyFirstCommand = new NearbyCommand(INDEX_FIRST_PERSON);
        NearbyCommand nearbySecondCommand = new NearbyCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(nearbyFirstCommand.equals(nearbyFirstCommand));

        // same values -> returns true
        NearbyCommand nearbyFirstCommandCopy = new NearbyCommand(INDEX_FIRST_PERSON);
        assertTrue(nearbyFirstCommand.equals(nearbyFirstCommandCopy));

        // different types -> returns false
        assertFalse(nearbyFirstCommand.equals(1));

        // null -> returns false
        assertFalse(nearbyFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(nearbyFirstCommand.equals(nearbySecondCommand));
    }

    /**
     * Executes a {@code NearbyCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        NearbyCommand nearbyCommand = prepareCommand(index);

        try {
            CommandResult commandResult = nearbyCommand.execute();
            assertEquals(String.format(ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                    + NearbyCommand.MESSAGE_NEARBY_PERSON_SUCCESS,
                    model.getNearbyPersons().indexOf(model.getSelectedPerson()) + 1),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToNearbyListRequestEvent lastEvent =
                (JumpToNearbyListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        if (index != null) {
            assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
        }
        assertEquals(model.getNearbyPersons().indexOf(model.getSelectedPerson()),
                lastEvent.targetIndex);
    }

    /**
     * Executes a {@code NearbyCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        NearbyCommand nearbyCommand = prepareCommand(index);

        try {
            nearbyCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code NearbyCommand} with parameters {@code index}.
     */
    private NearbyCommand prepareCommand(Index index) {
        NearbyCommand nearbyCommand = new NearbyCommand(index);
        nearbyCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return nearbyCommand;
    }
}
```
###### \java\seedu\address\logic\commands\PaybackCommandTest.java
``` java
    @Test
    public void execute_noIndexPayback_success() {
        model.updateSelectedPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        ReadOnlyPerson personWhoPayback = model.getSelectedPerson();
        String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(PaybackCommand.MESSAGE_PAYBACK_SUCCESS,
                personWhoPayback.getName().toString(), VALID_DEBT_FIGURE);
        try {
            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.deductDebtFromPerson(personWhoPayback, new Debt(VALID_DEBT_FIGURE));

            PaybackCommand paybackCommand = prepareCommand(null, new Debt(VALID_DEBT_FIGURE));
            assertCommandSuccess(paybackCommand, model, expectedMessage, expectedModel);
        } catch (IllegalValueException | PersonNotFoundException | CommandException e) {
            e.printStackTrace();
        }
    }

```
###### \java\seedu\address\logic\commands\RepaidCommandTest.java
``` java
    @Test
    public void execute_noIndexPersonSelected_success() throws Exception {
        model.updateSelectedPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        ReadOnlyPerson personToRepaid = model.getSelectedPerson();
        RepaidCommand repaidCommand = prepareCommand();

        String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(RepaidCommand.MESSAGE_REPAID_PERSON_SUCCESS, personToRepaid.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addWhitelistedPerson(personToRepaid);

        assertCommandSuccess(repaidCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noIndexNoSelection_failure() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_NO_PERSON_SELECTED);
        prepareCommand();
        fail(UNEXPECTED_EXECTION);
    }

```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private static final String ORDERING_DEFAULT = "";
    private static final String ORDERING_NAME = "name";
    private static final String ORDERING_DEBT = "debt";
    private static final String ORDERING_CLUSTER = "cluster";
    private static final String ORDERING_DEADLINE = "deadline";
    private static final String INVALID_ORDERING = "height";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Before
    public void setUp() {
        ListObserver.init(model);
    }

    @Test
    public void execute_allOrderings_success() throws Exception {
        SortCommand sortCommand;
        String expectedMessage;

        Model expectedModel = model;

        expectedModel.sortBy(ORDERING_NAME);
        sortCommand = prepareCommand(ORDERING_DEFAULT);
        expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(SortCommand.MESSAGE_SUCCESS, ORDERING_NAME);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        expectedModel.sortBy(ORDERING_NAME);
        sortCommand = prepareCommand(ORDERING_NAME);
        expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(SortCommand.MESSAGE_SUCCESS, ORDERING_NAME);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        expectedModel.sortBy(ORDERING_DEBT);
        sortCommand = prepareCommand(ORDERING_DEBT);
        expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(SortCommand.MESSAGE_SUCCESS, ORDERING_DEBT);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        expectedModel.sortBy(ORDERING_CLUSTER);
        sortCommand = prepareCommand(ORDERING_CLUSTER);
        expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(SortCommand.MESSAGE_SUCCESS, ORDERING_CLUSTER);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        expectedModel.sortBy(ORDERING_DEADLINE);
        sortCommand = prepareCommand(ORDERING_DEADLINE);
        expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(SortCommand.MESSAGE_SUCCESS, ORDERING_DEADLINE);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_internalInvalidOrdering_throwsInvalidArgumentException() {
        Model expectedModel = model;

        thrown.expect(IllegalArgumentException.class);
        expectedModel.sortBy(INVALID_ORDERING);
    }

    @Test
    public void equals() {
        SortCommand sortFirstCommand = new SortCommand(ORDERING_NAME);
        SortCommand sortSecondCommand = new SortCommand(ORDERING_DEBT);

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // same values -> returns true
        SortCommand sortFirstCommandCopy = new SortCommand(ORDERING_NAME);
        assertTrue(sortFirstCommand.equals(sortFirstCommandCopy));

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(sortFirstCommand.equals(sortSecondCommand));
    }

    private SortCommand prepareCommand(String order) {
        SortCommand sortCommand = new SortCommand(order);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
```
###### \java\seedu\address\logic\commands\ThemeCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code ThemeCommand}.
 */
public class ThemeCommandTest extends CommandTest {

    @Test
    public void execute_changeTheme_success() {
        String expectedMessage = ThemeCommand.MESSAGE_SUCCESS;
        ThemeCommand themeCommand = new ThemeCommand();
        assertCommandSuccess(themeCommand, expectedMessage);
    }

    @Test
    public void equals() {
        ThemeCommand themeFirstCommand = new ThemeCommand();
        ThemeCommand themeSecondCommand = new ThemeCommand();

        // same object
        assertTrue(themeFirstCommand.equals(themeFirstCommand));

        // same value
        assertTrue(themeFirstCommand.equals(themeSecondCommand));
    }

    private void assertCommandSuccess(ThemeCommand command, String expectedMessage) {
        // TODO: assert that the theme has actually changed
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

}
```
###### \java\seedu\address\logic\commands\UnbanCommandTest.java
``` java
    @Test
    public void execute_noIndexPersonSelected_success() {
        try {
            model.updateSelectedPerson(model.getFilteredBlacklistedPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
            ReadOnlyPerson personToUnban = model.getSelectedPerson();
            UnbanCommand unbanCommand = prepareCommand();

            String expectedMessage = ListObserver.BLACKLIST_NAME_DISPLAY_FORMAT
                    + String.format(UnbanCommand.MESSAGE_UNBAN_PERSON_SUCCESS, personToUnban.getName());

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.removeBlacklistedPerson(personToUnban);

            assertCommandSuccess(unbanCommand, model, expectedMessage, expectedModel);
        } catch (PersonNotFoundException | CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_noIndexNoSelection_failure() {
        try {
            UnbanCommand unbanCommand = prepareCommand();
            assertCommandFailure(unbanCommand, model, Messages.MESSAGE_NO_PERSON_SELECTED);
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

```
###### \java\seedu\address\logic\CommandTest.java
``` java
public class CommandTest {
    protected static final String UNEXPECTED_EXECTION = "Execution should not get to this line";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    protected Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Before
    public void setUp() {
        ListObserver.init(model);
    }

    /**
     * Selects the first person in the current list.
     */
    protected void selectFirstPerson() {
        model.updateSelectedPerson(ListObserver.getCurrentFilteredList().get(INDEX_FIRST_PERSON.getZeroBased()));
    }
}
```
###### \java\seedu\address\logic\parser\NearbyCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class NearbyCommandParserTest {

    private NearbyCommandParser parser = new NearbyCommandParser();

    @Test
    public void parse_validArgs_returnsNearbyCommand() throws Exception {
        assertParseSuccess(parser, "1", new NearbyCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, NearbyCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
public class SortCommandParserTest {

    private static final String ORDERING_DEFAULT = "";
    private static final String ORDERING_NAME = "name";
    private static final String ORDERING_DEBT = "debt";
    private static final String ORDERING_CLUSTER = "cluster";
    private static final String ORDERING_DEADLINE = "deadline";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_invalidOrdering_failure() {

        // number
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // invalid string
        assertParseFailure(parser, "weight", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validOrdering_success() throws Exception {
        SortCommand expectedCommand;

        expectedCommand = new SortCommand(ORDERING_DEFAULT);
        assertParseSuccess(parser, ORDERING_DEFAULT, expectedCommand);

        expectedCommand = new SortCommand(ORDERING_NAME);
        assertParseSuccess(parser, ORDERING_NAME, expectedCommand);

        expectedCommand = new SortCommand(ORDERING_DEBT);
        assertParseSuccess(parser, ORDERING_DEBT, expectedCommand);

        expectedCommand = new SortCommand(ORDERING_CLUSTER);
        assertParseSuccess(parser, ORDERING_CLUSTER, expectedCommand);

        expectedCommand = new SortCommand(ORDERING_DEADLINE);
        assertParseSuccess(parser, ORDERING_DEADLINE, expectedCommand);
    }
}
```
###### \java\seedu\address\model\ClusterUtilTest.java
``` java
public class ClusterUtilTest {

    private static final String SAMPLE_POSTAL_LAST_4_DIGITS = "1234";

    @Test
    public void allDistrictsTest() {

        int i = 99;
        while (i > 82) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_UNKNOWN);
            i--;
        }
        while (i > 81) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_19);
            i--;
        }
        while (i > 80) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_17);
            i--;
        }
        while (i > 78) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_28);
            i--;
        }
        while (i > 76) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_26);
            i--;
        }
        while (i > 74) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_27);
            i--;
        }
        while (i > 73) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_UNKNOWN);
            i--;
        }
        while (i > 71) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_25);
            i--;
        }
        while (i > 68) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_24);
            i--;
        }
        while (i > 64) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_23);
            i--;
        }
        while (i > 59) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_22);
            i--;
        }
        while (i > 57) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_21);
            i--;
        }
        while (i > 55) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_20);
            i--;
        }
        while (i > 52) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_19);
            i--;
        }
        while (i > 50) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_18);
            i--;
        }
        while (i > 48) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_17);
            i--;
        }
        while (i > 45) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_16);
            i--;
        }
        while (i > 41) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_15);
            i--;
        }
        while (i > 37) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_14);
            i--;
        }
        while (i > 33) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_13);
            i--;
        }
        while (i > 30) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_12);
            i--;
        }
        while (i > 27) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_11);
            i--;
        }
        while (i > 23) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_10);
            i--;
        }
        while (i > 21) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_09);
            i--;
        }
        while (i > 19) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_08);
            i--;
        }
        while (i > 17) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_07);
            i--;
        }
        while (i > 16) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_06);
            i--;
        }
        while (i > 13) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_03);
            i--;
        }
        while (i > 10) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_05);
            i--;
        }
        while (i > 9) {
            assertEquals(getCluster("" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_04);
            i--;
        }
        while (i > 8) {
            assertEquals(getCluster("0" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_04);
            i--;
        }
        while (i > 6) {
            assertEquals(getCluster("0" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_02);
            i--;
        }
        while (i > 0) {
            assertEquals(getCluster("0" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_01);
            i--;
        }
        assertEquals(getCluster("0" + i + SAMPLE_POSTAL_LAST_4_DIGITS), CLUSTER_POSTAL_DISTRICT_UNKNOWN);
    }
}
```
###### \java\seedu\address\model\person\PostalCodeTest.java
``` java
public class PostalCodeTest {

    @Test
    public void isValidPostalCode() {
        // invalid postal codes
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("91")); // less than 6 numbers
        assertFalse(PostalCode.isValidPostalCode("9999999")); // more than 6 numbers
        assertFalse(PostalCode.isValidPostalCode("postalcode")); // non-numeric
        assertFalse(PostalCode.isValidPostalCode("90p041")); // alphabets within digits
        assertFalse(PostalCode.isValidPostalCode("9312 34")); // spaces within digits

        // valid postal codes
        assertTrue(PostalCode.isValidPostalCode("000000"));
    }
}
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void backupAddressBookReadSave() throws Exception {
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        storageManager.backupAddressBook();
        ReadOnlyAddressBook retrieved = storageManager.readBackupAddressBook().get();
        assertEquals(original.toString(), new AddressBook(retrieved).toString());
    }

```
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    @Test
    public void readAndSaveBackupAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        AddressBook original = getTypicalAddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);

        //Save in new backup and read back
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        xmlAddressBookStorage.backupAddressBook();
        ReadOnlyAddressBook readBack = xmlAddressBookStorage.readBackupAddressBook().get();
        assertEquals(original.toString(), new AddressBook(readBack).toString());

        //Modify data, overwrite exiting backup file, and read back
        original.addPerson(new Person(HOON));
        original.removePerson(new Person(ALICE));
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        xmlAddressBookStorage.backupAddressBook();
        readBack = xmlAddressBookStorage.readBackupAddressBook().get();
        assertEquals(original.toString(), new AddressBook(readBack).toString());
    }

    @Test
    public void backupAddressBook_notXmlFormat_backupNotSaved () throws Exception {
        String filePath = testFolder.getRoot().getPath() + "NotXmlFormatAddressBook.xmm";
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        xmlAddressBookStorage.backupAddressBook();
        assertEquals(Optional.empty(), xmlAddressBookStorage.readBackupAddressBook());
    }

    @Test
    public void getBestAvailableAddressBook_allInOrder_nonOptimal() throws Exception {
        AddressBook original = getTypicalAddressBook();
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        xmlAddressBookStorage.backupAddressBook();
        original.addPerson(new Person(HOON));
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        File mainAddressBook;
        File backupAddressBook;
        // At this stage, main address book has one more person (HOON) than backup address book

        // Scenario 1: Main data file not found, use backup
        mainAddressBook = new File(xmlAddressBookStorage.getAddressBookFilePath());
        mainAddressBook.delete();
        assertEquals(new AddressBook(xmlAddressBookStorage.readBackupAddressBook().get()),
                new AddressBook(xmlAddressBookStorage.getBestAvailableAddressBook()));
        xmlAddressBookStorage.saveAddressBook(original, filePath);


        // Scenario 2: Main and backup data files both do not exist, use sample address book
        mainAddressBook = new File(xmlAddressBookStorage.getAddressBookFilePath());
        mainAddressBook.delete();
        backupAddressBook = new File(xmlAddressBookStorage.getBackupAddressBookFilePath());
        backupAddressBook.delete();
        assertEquals(new AddressBook(SampleDataUtil.getSampleAddressBook()),
                xmlAddressBookStorage.getBestAvailableAddressBook());
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        xmlAddressBookStorage.backupAddressBook();
    }

```
###### \java\seedu\address\ui\InfoPanelTest.java
``` java
public class InfoPanelTest extends GuiUnitTest {
    private static final String MESSAGE_EMPTY_STRING = "";

    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private InfoPanel infoPanel;
    private InfoPanelHandle infoPanelHandle;

    private Logic logicStub;

    @Before
    public void setUp() {
        logicStub = new LogicManager(new ModelManager());
        ListObserver.init(new ModelManager());
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> infoPanel = new InfoPanel(logicStub));
        uiPartRule.setUiPart(infoPanel);

        infoPanelHandle = new InfoPanelHandle(infoPanel.getRoot());
    }

    @Test
    public void display() throws Exception {

        infoPanelHandle.rememberSelectedPersonDetails();

        // associated info of a person
        postNow(selectionChangedEventStub);
        assertInfoDisplay(infoPanel, ALICE);
        assertTrue(infoPanelHandle.isSelectedPersonChanged());
        infoPanelHandle.rememberSelectedPersonDetails();

        // asserts that no change is registered when same person is clicked
        postNow(selectionChangedEventStub);
        assertInfoDisplay(infoPanel, ALICE);
        assertFalse(infoPanelHandle.isSelectedPersonChanged());

        // associated info of next person
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(BENSON, 1));
        postNow(selectionChangedEventStub);
        assertTrue(infoPanelHandle.isSelectedPersonChanged());
        assertInfoDisplay(infoPanel, BENSON);
    }

    @Test
    public void equals() {
        infoPanel = new InfoPanel(logicStub);

        // test .equals() method for two same objects
        assertTrue(infoPanel.equals(infoPanel));

        // test .equals() method for an object of different type
        assertFalse(infoPanel.equals(infoPanelHandle));

        InfoPanel expectedInfoPanel = new InfoPanel(logicStub);

        assertTrue(infoPanel.equals(expectedInfoPanel));

        infoPanel.loadPersonInfo(ALICE);
        assertFalse(infoPanel.equals(expectedInfoPanel));

        expectedInfoPanel.loadPersonInfo(ALICE);
        assertTrue(infoPanel.equals(expectedInfoPanel));
    }

    /**
     * Asserts that {@code infoPanel} displays the details of {@code expectedPerson} correctly.
     */
    private void assertInfoDisplay(InfoPanel infoPanel, ReadOnlyPerson expectedPerson) {
        guiRobot.pauseForHuman();

        InfoPanelHandle personInfoHandle = new InfoPanelHandle(infoPanel.getRoot());

        // verify person details are displayed correctly
        assertInfoDisplaysPerson(expectedPerson, personInfoHandle);
    }
}
```
###### \java\seedu\address\ui\NearbyPersonListPanelTest.java
``` java
public class NearbyPersonListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private static final JumpToNearbyListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToNearbyListRequestEvent(INDEX_SECOND_PERSON);
    private NearbyPersonListPanelHandle nearbyPersonListPanelHandle;

    @Before
    public void setUp() {
        NearbyPersonListPanel nearbyPersonListPanel = new NearbyPersonListPanel(TYPICAL_PERSONS, BENSON);
        uiPartRule.setUiPart(nearbyPersonListPanel);
        nearbyPersonListPanelHandle = new NearbyPersonListPanelHandle(getChildNode(nearbyPersonListPanel.getRoot(),
                NearbyPersonListPanelHandle.NEARBY_PERSON_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        ReadOnlyPerson expectedPerson;
        PersonCardHandle actualCard;

        nearbyPersonListPanelHandle.navigateToCard(BENSON);
        expectedPerson = BENSON;
        actualCard = nearbyPersonListPanelHandle.getPersonCardHandle(0);

        assertCardDisplaysPerson(expectedPerson, actualCard);
        assertEquals(Integer.toString(1) + ". ", actualCard.getId());

        nearbyPersonListPanelHandle.navigateToCard(CARL);
        expectedPerson = BENSON;
        actualCard = nearbyPersonListPanelHandle.getPersonCardHandle(0);

        assertCardDisplaysPerson(expectedPerson, actualCard);
        assertEquals(Integer.toString(1) + ". ", actualCard.getId());
    }

    @Test
    public void handleJumpNearbyToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        PersonCardHandle expectedCard =
                nearbyPersonListPanelHandle.getPersonCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        PersonCardHandle selectedCard = nearbyPersonListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualInfo} displays the details of {@code expectedPerson}.
     */
    public static void assertInfoDisplaysPerson(ReadOnlyPerson expectedPerson, InfoPanelHandle actualInfo) {
        assertEquals(expectedPerson.getName().fullName, actualInfo.getName());
        assertEquals(expectedPerson.getHandphone().value, actualInfo.getHandphone());
        assertEquals(expectedPerson.getHomePhone().value, actualInfo.getHomePhone());
        assertEquals(expectedPerson.getOfficePhone().value, actualInfo.getOfficePhone());
        assertEquals(expectedPerson.getEmail().value, actualInfo.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualInfo.getAddress());
        assertEquals(expectedPerson.getPostalCode().value, actualInfo.getPostalCode());
        assertEquals(expectedPerson.getCluster().value, actualInfo.getCluster());
        assertEquals(expectedPerson.getInterest().value, actualInfo.getInterest());
        assertEquals(expectedPerson.getDebt().toString(), actualInfo.getDebt());
        assertEquals(expectedPerson.getTotalDebt().toString(), actualInfo.getTotalDebt());
        assertEquals(expectedPerson.getDateBorrow().value, actualInfo.getDateBorrow());
        assertEquals(expectedPerson.getDeadline().valueToDisplay, actualInfo.getDeadline());
        assertEquals(expectedPerson.getDateRepaid().value, actualInfo.getDateRepaid());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualInfo.getTags());
    }

```
