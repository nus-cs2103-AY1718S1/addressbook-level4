# RSJunior37
###### \java\guitests\AddressBookGuiTest.java
``` java
    protected ProfilePanelHandle getProfilePanel() {
        return mainWindowHandle.getProfilePanelHandle();
    }
```
###### \java\guitests\guihandles\InsuranceProfilePanelHandle.java
``` java
/**
 * A handler for the {@code InsuranceProfilePanel} of the UI.
 */
public class InsuranceProfilePanelHandle extends NodeHandle<Node> {

    public static final String INSURNACE_PROFILE_PANEL_ID = "#insuranceProfilePanel";
    private static final String OWNER_FIELD_ID = "#owner";
    private static final String INSURED_FIELD_ID = "#insured";
    private static final String BENEFICIARY_FIELD_ID = "#beneficiary";
    private static final String CONTRACT_NAME_ID = "#contractFileName";
    private static final String PREMIUM_FIELD_ID = "#premium";
    private static final String SIGNING_DATE_ID = "#signingDate";
    private static final String EXPIRY_DATE_ID = "#expiryDate";

    private final Label ownerLabel;
    private final Label insuredLabel;
    private final Label beneficiaryLabel;
    private final Label contractFileNameLabel;
    private final Label premiumLabel;
    private final Label signingDateLabel;
    private final Label expiryDateLabel;

    public InsuranceProfilePanelHandle(Node profilePanelNode) {
        super(profilePanelNode);

        this.ownerLabel = getChildNode(OWNER_FIELD_ID);
        this.insuredLabel = getChildNode(INSURED_FIELD_ID);
        this.beneficiaryLabel = getChildNode(BENEFICIARY_FIELD_ID);
        this.contractFileNameLabel = getChildNode(CONTRACT_NAME_ID);
        this.premiumLabel = getChildNode(PREMIUM_FIELD_ID);
        this.signingDateLabel = getChildNode(SIGNING_DATE_ID);
        this.expiryDateLabel = getChildNode(EXPIRY_DATE_ID);

    }

    public String getOwner() {
        return ownerLabel.getText();
    }

    public String getInsured() {
        return insuredLabel.getText();
    }

    public String getBeneficiary() {
        return beneficiaryLabel.getText();
    }

    public String getContractPathId() {
        return contractFileNameLabel.getText();
    }

    public String getPremium() {
        return premiumLabel.getText();
    }

    public String getSigningDateId() {
        return signingDateLabel.getText();
    }

    public String getExpiryDateId() {
        return expiryDateLabel.getText();
    }
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
        NameContainsPartialKeywordsPredicate firstPredicate =
                new NameContainsPartialKeywordsPredicate(Collections.singletonList("first"));
        NameContainsPartialKeywordsPredicate secondPredicate =
                new NameContainsPartialKeywordsPredicate(Collections.singletonList("second"));

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
    public void execute_multiplePartialKeywords_allPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PartialFindCommand command = prepareCommand("Ca ll Fio");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_multipleFullKeywords_allPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PartialFindCommand command = prepareCommand("Kurz Fiona");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, FIONA));
    }



    /**
     * Parses {@code userInput} into a {@code PartialFindCommand}.
     */
    private PartialFindCommand prepareCommand(String userInput) {
        PartialFindCommand command =
                new PartialFindCommand(new NameContainsPartialKeywordsPredicate(
                        Arrays.asList(userInput.split("\\s+"))));
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

###### \java\seedu\address\testutil\InsuranceBuilder.java : Obsolete, some adjustment made to fit changes in final version and renamed into LifeInsuranceBuilder by OscarWang114
``` java
/**
 * A utility class to help with building LifeInsurance objects.
 */
public class InsuranceBuilder {

    public static final String DEFAULT_INSURANCE_NAME = "Sample Insurance";
    public static final String DEFAULT_OWNER = "Alice Pauline";
    public static final String DEFAULT_BENEFICIARY = "Benson Meier";
    public static final String DEFAULT_INSURED = "Carl Kurz";
    public static final String DEFAULT_CONTRACT_PATH = "sample.pdf";
    public static final Double DEFAULT_PREMIUM = 1234.0;
    public static final String DEFAULT_SIGNING_DATE = "01 Nov 2000";
    public static final String DEFAULT_EXPIRY_DATE = "02 Dec 2011";

    private LifeInsurance insurance;

    public InsuranceBuilder() {
        try {
            LocalDate defaultSigningDate = new DateParser().parse(DEFAULT_SIGNING_DATE);
            LocalDate defaultExpiryDate = new DateParser().parse(DEFAULT_EXPIRY_DATE);
            this.insurance = new LifeInsurance(DEFAULT_INSURANCE_NAME, DEFAULT_OWNER,
                    DEFAULT_INSURED, DEFAULT_BENEFICIARY, DEFAULT_PREMIUM, DEFAULT_CONTRACT_PATH,
                    defaultSigningDate, defaultExpiryDate);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default insurance's values are invalid.");
        }
    }

    /**
     * Initializes the InsuranceBuilder with the data of {@code insuranceToCopy}.
     */
    public InsuranceBuilder(ReadOnlyInsurance insuranceToCopy) {
        this.insurance = new LifeInsurance(insuranceToCopy);
    }

    /**
     * Sets the Insurance Name of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withInsuranceName(String insuranceName) {
        this.insurance.setInsuranceName(insuranceName);
        return this;
    }

    /**
     * Sets the Owner of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withOwner(Person owner) {
        this.insurance.setOwner(owner);
        return this;
    }

    /**
     * Sets the Beneficiary of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withBeneficiary(Person beneficiary) {
        this.insurance.setBeneficiary(beneficiary);
        return this;
    }

    /**
     * Sets the Insured of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withInsured(Person insured) {
        this.insurance.setInsured(insured);
        return this;
    }

    /**
     * Sets the Premium of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withPremium(double premium) {
        this.insurance.setPremium(premium);
        return this;
    }

    /**
     * Sets the Contract Path of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withContractName(String contractFileName) {
        this.insurance.setContractName(contractFileName);
        return this;
    }

    /**
     * Sets the Signing Date of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withSigningDate(String signingDate) {
        try {
            this.insurance.setSigningDateString(signingDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Invalid signing date");
        }
        return this;
    }

    /**
     * Sets the Expiry Date of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withExpiryDate(String expiryDate) {
        try {
            this.insurance.setExpiryDateString(expiryDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Invalid expiry date");
        }
        return this;
    }

    public LifeInsurance build() {
        return this.insurance;
    }

}
```
###### \java\seedu\address\testutil\TypicalInsurances.java
``` java
/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalInsurances {
    public static final ReadOnlyInsurance COMMON_INSURANCE =
            new LifeInsuranceBuilder().withInsuranceName("Common Insurance")
            .withOwner(new Person(ALICE))
            .withBeneficiary(new Person(BENSON))
            .withInsured(new Person(CARL))
            .withPremium("123.45")
            .withContractFileName("common.pdf")
            .withSigningDate("01 Jan 2017")
            .withExpiryDate("31 Dec 2020").build();

    private TypicalInsurances() {} // prevents instantiation
}
```
###### \java\seedu\address\ui\InsuranceProfilePanelTest.java
``` java
public class InsuranceProfilePanelTest extends GuiUnitTest {
    private InsurancePanelSelectionChangedEvent insuranceClickedEventStub;

    private InsuranceProfilePanel insuranceProfilePanel;
    private InsuranceProfilePanelHandle insuranceProfilePanelHandle;

    @Before
    public void setUp() {
        ReadOnlyInsurance insurance = COMMON_INSURANCE;
        insuranceClickedEventStub = new InsurancePanelSelectionChangedEvent(insurance);

        guiRobot.interact(() -> insuranceProfilePanel = new InsuranceProfilePanel());
        uiPartRule.setUiPart(insuranceProfilePanel);

        insuranceProfilePanelHandle = new InsuranceProfilePanelHandle(insuranceProfilePanel.getRoot());

    }

    @Test
    public void display() throws Exception {

        assertNull(insuranceProfilePanelHandle.getOwner());
        assertNull(insuranceProfilePanelHandle.getBeneficiary());
        assertNull(insuranceProfilePanelHandle.getInsured());
        assertNull(insuranceProfilePanelHandle.getPremium());
        assertNull(insuranceProfilePanelHandle.getContractPathId());
        assertNull(insuranceProfilePanelHandle.getSigningDateId());
        assertNull(insuranceProfilePanelHandle.getExpiryDateId());

        // select Stub Person
        postNow(insuranceClickedEventStub);

        ReadOnlyPerson expectedOwner = ALICE;
        ReadOnlyPerson expectedBeneficiary = BENSON;
        ReadOnlyPerson expectedInsured = CARL;

        Double expectedPremiumDouble = 123.45;
        String expectedPremium = new String("S$ " + String.format("%.2f", expectedPremiumDouble));
        String expectedContractPath = "common.pdf";
        String expectedSigningDate = "01 Jan 2017";
        String expectedExpiryDate = "31 Dec 2020";

        assertEquals(expectedOwner.getName().toString(), insuranceProfilePanelHandle.getOwner());
        assertEquals(expectedBeneficiary.getName().toString(), insuranceProfilePanelHandle.getBeneficiary());
        assertEquals(expectedInsured.getName().toString(), insuranceProfilePanelHandle.getInsured());
        assertEquals(expectedContractPath, insuranceProfilePanelHandle.getContractPathId());
        assertEquals(expectedPremium, insuranceProfilePanelHandle.getPremium());
        assertEquals(expectedSigningDate, insuranceProfilePanelHandle.getSigningDateId());
        assertEquals(expectedExpiryDate, insuranceProfilePanelHandle.getExpiryDateId());
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
