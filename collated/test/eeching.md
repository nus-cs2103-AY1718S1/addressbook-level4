# eeching
###### \java\seedu\address\logic\commands\PhoneCommandTest.java
``` java
public class PhoneCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_personAcceptedByModel_addPhoneSuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).build();

        PhoneCommand phoneCommand = prepareCommand(INDEX_FIRST_PERSON, "add", new Phone("2333"));

        String expectedMessage = "Phone number 2333 has been added.\n"
                + "The updated phone list now has 3 phone numbers.\n"
                + "The primary phone number is 85355255.\n";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(phoneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personAcceptedByModel_removePhoneSuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).build();

        PhoneCommand phoneCommand = prepareCommand(INDEX_FIRST_PERSON, "remove", new Phone("25555"));

        String expectedMessage = "Phone number 25555 has been removed.\n"
                + "The updated phone list now has 2 phone numbers.\n"
                + "The primary phone number is 85355255.\n";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(phoneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePhone_throwsCommandException() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).build();

        PhoneCommand phoneCommand = prepareCommand(INDEX_FIRST_PERSON, "add", new Phone("24444"));

        String expectedMessage = "Phone number to be added already exists in the list.\n";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(phoneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_phoneNotFound_throwsCommandException() throws Exception  {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).build();

        PhoneCommand phoneCommand = prepareCommand(INDEX_FIRST_PERSON, "remove", new Phone("2333"));

        String expectedMessage = "Phone number to be removed is not found in the list.\n";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(phoneCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_personAcceptedByModel_invalidCommandSuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).build();

        PhoneCommand phoneCommand = prepareCommand(INDEX_FIRST_PERSON, "IHaveNoIdeaWhatToType", new Phone("000"));

        String expectedMessage = "Command is invalid, please check again.\n";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);
        assertCommandSuccess(phoneCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code CustomCommand} with the parameters {@code index + CustomFieldName + CustomFieldValue}.
     */
    private PhoneCommand prepareCommand(Index index, String action, Phone phone) {
        PhoneCommand command = new PhoneCommand(index, action, phone);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\parser\PhoneCommandParserTest.java
``` java
public class PhoneCommandParserTest {

    private PhoneCommandParser parser = new PhoneCommandParser();

    @Test
    public void parse_validArgsWithIndex_returnsPhoneCommand () throws IllegalValueException {
        PhoneCommand expectedPhoneCommand = new PhoneCommand(INDEX_FIRST_PERSON, "add", new Phone("233333"));
        assertParseSuccess(parser, "1 add 233333", expectedPhoneCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 1 \n \t add \n \t 233333 \t ", expectedPhoneCommand);
    }

    @Test
    public void parse_validArgsWithName_returnsPhoneCommand () throws IllegalValueException {
        PhoneCommand expectedPhoneCommand = new PhoneCommand("Alice Pauline", "add", new Phone("233333"));
        assertParseSuccess(parser, "byName add 233333 Alice Pauline", expectedPhoneCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException () {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhoneCommand.MESSAGE_USAGE));
    }

}
```
###### \java\seedu\address\model\person\UniquePhoneListTest.java
``` java
public class UniquePhoneListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Logger logger = LogsCenter.getLogger(UniquePhoneListTest.class);

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonList.asObservableList().remove(0);
    }

    @Test
    public void isUniqueAfterAdd() {
        UniquePhoneList list = new UniquePhoneList();
        boolean isUnique = false;
        try {
            list.add(new Phone("23333"));
            list.add(new Phone("23333")); //this is an illegal value due to duplication
        } catch (IllegalValueException e) {
            isUnique = true;
        }

        assertTrue("No duplicate Phone number", isUnique);
    }

    @Test
    public void detectUnfoundPhoneForRemove() {
        UniquePhoneList list = new UniquePhoneList();
        boolean detected = false;
        try {
            list.add(new Phone("23333"));
            list.add(new Phone("23334"));
            list.remove(new Phone("2333")); //this is an exception since it does not exist in the list
        } catch (IllegalValueException e) {
            throw new AssertionError("Illegal phone number");
        } catch (PhoneNotFoundException e) {
            detected = true;
        }

        assertTrue("Detect unfound phone number", detected);
    }

    @Test
    public void getCorrectSizeAfterAddition() {
        UniquePhoneList list = new UniquePhoneList();
        boolean correctSize = false;
        try {
            list.add(new Phone("23333"));
            list.add(new Phone("23334"));
            list.add(new Phone("23335"));
            list.add(new Phone("23333")); //duplicate should not be added, hence the size is still 3
        } catch (IllegalValueException e) {
            logger.warning("duplicate number added");
        }
        if (list.getSize() == 3) {
            correctSize = true;
        }
        assertTrue("The size of the phoneList after adding numbers is correct", correctSize);
    }

    @Test
    public void getCorrectSizeAfterRemoval() {
        UniquePhoneList list = new UniquePhoneList();
        boolean correctSize = false;
        try {
            list.add(new Phone("23333"));
            list.add(new Phone("23334"));
            list.remove(new Phone("23333")); //now the size reduce to 1
            list.remove(new Phone("2333")); // size is still 1 since this phone is not in the list
        } catch (IllegalValueException e) {
            logger.warning("duplicate number added");
        } catch (PhoneNotFoundException e) {
            logger.warning("phone not found");
        }
        if (list.getSize() == 1) {
            correctSize = true;
        }
        assertTrue("The size of the phoneList after removing numbers is correct", correctSize);
    }

    @Test
    public void isEqual() { //to test the sequence of addition/ removal does not matter
        UniquePhoneList list1 = new UniquePhoneList();
        UniquePhoneList list2 = new UniquePhoneList();
        boolean isEqual;
        try {
            list1.add(new Phone("23333"));
            list1.add(new Phone("23334"));
            list1.add(new Phone("23335"));
            list1.remove(new Phone("23333"));
            list2.add(new Phone("23334"));
            list2.add(new Phone("23335"));
            list2.add(new Phone("23333"));
            list2.remove(new Phone("23333"));
        } catch (IllegalValueException e) {
            logger.warning("duplicate number added");
        } catch (PhoneNotFoundException e) {
            logger.warning("phone number not found");
        }
        isEqual = list1.equals(list2);
        assertTrue("The two lists are equal", isEqual);
    }

}
```
