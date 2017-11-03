# bladerail
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
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
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;
    private String filterType;

    @Test
    public void execute_sortByNameSuccess() {
        model = new ModelManager(getUnsortedTypicalAddressBook(), new UserPrefs(), new UserPerson());
        expectedModel = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs(), new UserPerson());
        filterType = ARG_NAME;

        sortCommand = prepareCommand(filterType);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SUCCESS, filterType), expectedModel);

    }

    @Test
    public void execute_sortByDefaultSortsByNameSuccess() {
        model = new ModelManager(getUnsortedTypicalAddressBook(), new UserPrefs(), new UserPerson());
        expectedModel = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs(), new UserPerson());
        filterType = ARG_DEFAULT;

        sortCommand = prepareCommand(filterType);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SUCCESS, filterType), expectedModel);

    }

    @Test
    public void execute_sortByEmailSuccess() {
        model = new ModelManager(getUnsortedTypicalAddressBook(), new UserPrefs(), new UserPerson());
        List<ReadOnlyPerson> sortedList = Arrays.asList(new Person(ALICE),
                new Person(GEORGE), new Person(DANIEL), new Person(CARL),
                new Person(BENSON), new Person(FIONA), new Person(ELLE));
        expectedModel = createExpectedModel(sortedList);
        filterType = ARG_EMAIL;

        sortCommand = prepareCommand(filterType);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SUCCESS, filterType), expectedModel);

    }

    @Test
    public void execute_sortByPhoneSuccess() {
        model = new ModelManager(getUnsortedTypicalAddressBook(), new UserPrefs(), new UserPerson());
        List<ReadOnlyPerson> sortedList = Arrays.asList(new Person(ALICE),
                new Person(DANIEL), new Person(ELLE), new Person(FIONA), new Person(GEORGE),
                new Person(CARL), new Person(BENSON));
        expectedModel = createExpectedModel(sortedList);
        filterType = ARG_PHONE;

        sortCommand = prepareCommand(filterType);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SUCCESS, filterType), expectedModel);

    }

    @Test
    public void execute_sortByAddressSuccess() {
        model = new ModelManager(getUnsortedTypicalAddressBook(), new UserPrefs(), new UserPerson());
        List<ReadOnlyPerson> sortedList = Arrays.asList(new Person(DANIEL),
                new Person(ALICE), new Person(BENSON), new Person(GEORGE), new Person(FIONA),
                new Person(ELLE), new Person(CARL));
        expectedModel = createExpectedModel(sortedList);
        showFirstPersonOnly(model);
        filterType = ARG_ADDRESS;

        sortCommand = prepareCommand(filterType);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SUCCESS, filterType), expectedModel);

    }

    @Test
    public void equals() {
        filterType = "";

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
}
```
###### \java\seedu\address\logic\commands\UpdateUserCommandTest.java
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
    public void execute_noFieldSpecified_success() {
        UpdateUserCommand updateUserCommand = prepareCommand(new EditPersonDescriptor());
        ReadOnlyPerson editedPerson = model.getUserPerson();

        String expectedMessage = String.format(UpdateUserCommand.MESSAGE_UPDATE_USER_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new UserPerson(editedPerson));

        assertCommandSuccess(updateUserCommand, model, expectedMessage, expectedModel);
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
###### \java\seedu\address\logic\parser\UpdateUserCommandParserTest.java
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
    }
}
```
###### \java\seedu\address\testutil\TypicalPersons.java
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
###### \java\seedu\address\testutil\TypicalUserPerson.java
``` java
/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalUserPerson {

    public static final ReadOnlyPerson BOB = new PersonBuilder().withName("Bob the Builder")
            .withAddress("456 Rochor Ave 3").withEmail("bob@builder.com")
            .withPhone("84712836")
            .withTags("").build();

    private TypicalUserPerson() {} // prevents instantiation

    public static ReadOnlyPerson getTypicalUserPerson() {
        return BOB;
    }

}
```
