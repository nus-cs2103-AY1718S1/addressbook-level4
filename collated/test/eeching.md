# eeching
###### \java\seedu\address\logic\commands\PhoneCommandTest.java
``` java
public class PhoneCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_personAcceptedByModel_updatePhoneListSuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withPhoneList("2333").build();

        PhoneCommand phoneCommand = prepareCommand(INDEX_FIRST_PERSON, "add", new Phone("2333"));

        String expectedMessage = "Phone number 2333 has been added, the updated phone list now has 2 phone numbers, "
                + "and the primary phone number is 85355255";

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
