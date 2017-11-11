# OscarWang114
###### /java/seedu/address/logic/commands/AddLifeInsuranceCommandTest.java
``` java
public class AddLifeInsuranceCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullLifeInsurance_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddLifeInsuranceCommand(null);
    }

    @Test
    public void execute_lifeInsuranceAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLifeInsuranceAdded modelStub = new ModelStubAcceptingLifeInsuranceAdded();
        LifeInsurance validLifeInsurance = new LifeInsuranceBuilder().build();
        CommandResult commandResult = getAddLifeInsuranceCommandForPerson(validLifeInsurance, modelStub).execute();
        ArrayList<LifeInsurance> arrayList = new ArrayList<>();
        arrayList.add(validLifeInsurance);
        assertEquals(String.format(AddLifeInsuranceCommand.MESSAGE_SUCCESS, validLifeInsurance),
                commandResult.feedbackToUser
        );
        assertEquals(arrayList, modelStub.insurancesAdded);
    }

    @Test
    public void execute_duplicateInsurance_throwsAssertionError() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateInsuranceException();
        LifeInsurance validLifeInsurance = new LifeInsuranceBuilder().build();

        thrown.expect(AssertionError.class);
        thrown.expectMessage(AddLifeInsuranceCommand.MESSAGE_DUPLICATE_INSURANCE);

        getAddLifeInsuranceCommandForPerson(validLifeInsurance, modelStub).execute();
    }

    @Test
    public void execute_duplicateContractFileName_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateContractFileNameException();
        LifeInsurance validLifeInsurance = new LifeInsuranceBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddLifeInsuranceCommand.MESSAGE_DUPLICATE_CONTRACT_FILE_NAME);

        getAddLifeInsuranceCommandForPerson(validLifeInsurance, modelStub).execute();
    }

    @Test
    public void equals() {
        LifeInsurance termLife = new LifeInsuranceBuilder().withInsuranceName("TermLife").build();
        LifeInsurance wholeLife = new LifeInsuranceBuilder().withInsuranceName("WholeLife").build();
        AddLifeInsuranceCommand addTermLifeInsuranceCommand = new AddLifeInsuranceCommand(termLife);
        AddLifeInsuranceCommand addWholeLifeInsuranceCommand = new AddLifeInsuranceCommand(wholeLife);

        // same object -> returns true
        assertTrue(addTermLifeInsuranceCommand.equals(addTermLifeInsuranceCommand));

        // same values -> returns true
        AddLifeInsuranceCommand addTermLifeInsuranceCommandCopy = new AddLifeInsuranceCommand(termLife);
        assertTrue(addTermLifeInsuranceCommand.equals(addTermLifeInsuranceCommandCopy));

        // different types -> returns false
        assertFalse(addTermLifeInsuranceCommand.equals(1));

        // null -> returns false
        assertFalse(addTermLifeInsuranceCommand.equals(null));

        // different person -> returns false
        assertFalse(addTermLifeInsuranceCommand.equals(addWholeLifeInsuranceCommand));
    }

    /**
     * Generates a new AddLifeInsuranceCommand with the details of the given life insurance.
     */
    private AddLifeInsuranceCommand getAddLifeInsuranceCommandForPerson(LifeInsurance lifeInsurance, Model model) {
        AddLifeInsuranceCommand command = new AddLifeInsuranceCommand(lifeInsurance);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addLifeInsurance(ReadOnlyInsurance insurance)
                throws DuplicateInsuranceException, DuplicateContractFileNameException {
            fail("This method should not be called.");
        }

        @Override
        public void updateLifeInsurance(ReadOnlyInsurance target, ReadOnlyInsurance editedOnlyReadInsurance) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ReadOnlyInsurance> getFilteredInsuranceList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredInsuranceList(Predicate<ReadOnlyInsurance> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteInsurance(ReadOnlyInsurance target) throws InsuranceNotFoundException {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a {@code DuplicateLifeInsuranceException} when trying to add a life insurance.
     */
    private class ModelStubThrowingDuplicateInsuranceException extends ModelStub {
        @Override
        public void addLifeInsurance(ReadOnlyInsurance insurance)
                throws DuplicateInsuranceException, DuplicateContractFileNameException {
            throw new DuplicateInsuranceException();
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return new UniquePersonList().asObservableList();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always throw a {@code DuplicateContractFileNameException} when trying to add a life insurance.
     */
    private class ModelStubThrowingDuplicateContractFileNameException extends ModelStub {
        @Override
        public void addLifeInsurance(ReadOnlyInsurance insurance)
                throws DuplicateInsuranceException, DuplicateContractFileNameException {
            throw new DuplicateContractFileNameException();
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return new UniquePersonList().asObservableList();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the life insurance being added.
     */
    private class ModelStubAcceptingLifeInsuranceAdded extends ModelStub {
        final ArrayList<LifeInsurance> insurancesAdded = new ArrayList<>();

        @Override
        public void addLifeInsurance(ReadOnlyInsurance insurance) throws
                DuplicateInsuranceException, DuplicateContractFileNameException {
            insurancesAdded.add(new LifeInsurance(insurance));
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return new UniquePersonList().asObservableList();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### /java/seedu/address/logic/parser/AddCommandParserTest.java
``` java
    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withDateOfBirth(VALID_DOB_AMY).withGender(VALID_GENDER_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DOB_DESC_AMY
                + GENDER_DESC_AMY, new AddCommand(expectedPerson));

        // missing phone prefix
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withEmptyPhone()
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withGender(VALID_GENDER_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + DOB_DESC_AMY + GENDER_DESC_AMY, new AddCommand(expectedPerson));

        // missing email prefix
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmptyEmail().withAddress(VALID_ADDRESS_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withGender(VALID_GENDER_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY
                + GENDER_DESC_AMY, new AddCommand(expectedPerson));

        // missing address prefix
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withDateOfBirth(VALID_DOB_AMY)
                .withGender(VALID_GENDER_AMY).withEmptyAddress().withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + DOB_DESC_AMY
                + GENDER_DESC_AMY, new AddCommand(expectedPerson));
```
###### /java/seedu/address/testutil/LifeInsuranceBuilder.java
``` java
/**
 * A utility class to help with building LifeInsurance objects.
 */
public class LifeInsuranceBuilder {

    public static final String DEFAULT_UUID = "6e5a4761-f578-4311-8ccc-d3f258c9e461";
    public static final String DEFAULT_NAME = "Term Life";
    public static final String DEFAULT_OWNER = "Alex Yeoh";
    public static final String DEFAULT_INSURED = "Bernice Yu";
    public static final String DEFAULT_BENEFICIARY = "John Doe";
    public static final String DEFAULT_PREMIUM = "600";
    public static final String DEFAULT_SIGNGING_DATE = "01 11 2017";
    public static final String DEFAULT_EXPIRY_DATE = "01 11 2037";
    public static final String DEFAULT_CONTRACT_FILE_NAME = "AlexYeoh-TermLife";

    private LifeInsurance lifeInsurance;

    public LifeInsuranceBuilder() {
        try {
            UUID defaultId = UUID.fromString(DEFAULT_UUID);
            InsuranceName defaultName = new InsuranceName(DEFAULT_NAME);
            InsurancePerson defaultOwner = new InsurancePerson(DEFAULT_OWNER);
            InsurancePerson defaultInsured = new InsurancePerson(DEFAULT_INSURED);
            InsurancePerson defaultBeneficiary = new InsurancePerson(DEFAULT_BENEFICIARY);
            Premium defaultPremium = new Premium(DEFAULT_PREMIUM);
            DateParser dateParser = new DateParser();
            LocalDate defaultSigningDate = dateParser.parse(DEFAULT_SIGNGING_DATE);
            LocalDate defaultExpiryDate = dateParser.parse(DEFAULT_EXPIRY_DATE);
            ContractFileName contractFileName = new ContractFileName(DEFAULT_CONTRACT_FILE_NAME);
            this.lifeInsurance = new LifeInsurance(defaultId, defaultName, defaultOwner, defaultInsured,
                    defaultBeneficiary, defaultPremium, contractFileName, defaultSigningDate, defaultExpiryDate);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default life insurance's values are invalid.");
        }
    }

    /**
     * Initializes the LifeInsuranceBuilder with the data of {@code lifeInsuranceToCopy}.
     */
    public LifeInsuranceBuilder(ReadOnlyInsurance lifeInsuranceToCopy) {
        this.lifeInsurance = new LifeInsurance(lifeInsuranceToCopy);
    }

    /**
     * Sets the {@code UUID} of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withId(String id) {
        try {
            this.lifeInsurance.setId(UUID.fromString(id));
        } catch (IllegalArgumentException ive) {
            throw new IllegalArgumentException("id is expected to be unique");
        }
        return this;
    }

    /**
     * Sets the {@code InsuranceName} of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withInsuranceName(String name) {
        try {
            this.lifeInsurance.setInsuranceName(new InsuranceName(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code InsurancePerson} owner of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withOwner(String owner) {
        try {
            this.lifeInsurance.setOwner(new InsurancePerson(owner));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("owner is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code InsurancePerson} owner of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withOwner(ReadOnlyPerson owner) {
        this.lifeInsurance.setOwner(new InsurancePerson(owner));
        return this;
    }

    /**
     * Sets the {@code InsurancePerson} insured of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withInsured(String insured) {
        try {
            this.lifeInsurance.setInsured(new InsurancePerson(insured));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("insured is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code InsurancePerson} insured of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withInsured(ReadOnlyPerson insured) {
        this.lifeInsurance.setInsured(new InsurancePerson(insured));
        return this;
    }

    /**
     * Sets the {@code InsurancePerson} beneficiary of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withBeneficiary(String beneficiary) {
        try {
            this.lifeInsurance.setBeneficiary(new InsurancePerson(beneficiary));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("beneficiary is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code InsurancePerson} beneficiary of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withBeneficiary(ReadOnlyPerson beneficiary) {
        this.lifeInsurance.setBeneficiary(new InsurancePerson(beneficiary));
        return this;
    }

    /**
     * Sets the {@code Premium} of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withPremium(String premium) {
        try {
            this.lifeInsurance.setPremium(new Premium(premium));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("premium is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code LocalDate} signingDate of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withSigningDate(String date) {
        DateParser dateParser = new DateParser();
        try {
            this.lifeInsurance.setSigningDate(dateParser.parse(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("signing date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code LocalDate} expiryDate of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withExpiryDate(String date) {
        DateParser dateParser = new DateParser();
        try {
            this.lifeInsurance.setExpiryDate(dateParser.parse(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("expiry date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code ContractFileName} of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withContractFileName(String contractFileName) {
        try {
            this.lifeInsurance.setContractFileName(new ContractFileName(contractFileName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("contract file name is expected to be unique.");
        }
        return this;
    }

    public LifeInsurance build() {
        return this.lifeInsurance;
    }

}
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmptyAddress() {
        this.person.setAddress(new Address());
        return this;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets an empty {@code Phone} for the {@code Person} that we are building.
     */
    public PersonBuilder withEmptyPhone() {
        this.person.setPhone(new Phone());
        return this;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets an empty {@code Email} for the {@code Person} that we are building.
     */
    public PersonBuilder withEmptyEmail() {
        this.person.setEmail(new Email());
        return this;
    }
```
###### /java/seedu/address/testutil/TypicalPersons.java
``` java

    public static final ReadOnlyPerson AMY_NO_PHONE = new PersonBuilder().withName(VALID_NAME_AMY).withEmptyPhone()
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withDateOfBirth(VALID_DOB_AMY).withGender(VALID_GENDER_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final ReadOnlyPerson AMY_NO_EMAIL = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmptyEmail().withAddress(VALID_ADDRESS_AMY)
            .withDateOfBirth(VALID_DOB_AMY).withTags(VALID_TAG_FRIEND)
            .withGender(VALID_GENDER_AMY).build();
    public static final ReadOnlyPerson AMY_NO_ADDRESS = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withEmptyAddress()
            .withDateOfBirth(VALID_DOB_AMY).withTags(VALID_TAG_FRIEND)
            .withGender(VALID_GENDER_AMY).build();

```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        /* Case: missing phone -> added */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY
                + GENDER_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, AMY_NO_PHONE);

        /* Case: missing email -> added */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + ADDRESS_DESC_AMY + DOB_DESC_AMY
                + GENDER_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, AMY_NO_EMAIL);

        /* Case: missing address -> added */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + DOB_DESC_AMY
                + GENDER_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, AMY_NO_ADDRESS);
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + GENDER_DESC_AMY + DOB_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
```
