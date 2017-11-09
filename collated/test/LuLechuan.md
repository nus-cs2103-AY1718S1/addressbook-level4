# LuLechuan
###### \java\seedu\address\logic\commands\CustomCommandTest.java
``` java
public class CustomCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_personAcceptedByModel_updateCustomFieldSuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withCustomFields("Birthday 29/02/1996").build();

        CustomField customField = new CustomField("Birthday", "29/02/1996");
        CustomCommand customCommand = prepareCommand(INDEX_FIRST_PERSON, customField);

        String expectedMessage = String.format(CustomCommand.MESSAGE_UPDATE_PERSON_CUSTOM_FIELD_SUCCESS, updatedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(customCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code CustomCommand} with the parameters {@code index + CustomFieldName + CustomFieldValue}.
     */
    private CustomCommand prepareCommand(Index index, CustomField customField) {
        CustomCommand command = new CustomCommand(index, customField);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\DeleteByNameCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteByNameCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteByNameCommand deleteByNameCommand = prepareCommand(personToDelete.getName().toString());

        String expectedMessage =
                String.format(DeleteByNameCommand.MESSAGE_DELETE_PERSON_BY_NAME_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteByNameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() throws Exception {
        String personNotFound = "";
        DeleteByNameCommand deleteByNameCommand = prepareCommand(personNotFound);

        assertCommandFailure(deleteByNameCommand, model, Messages.MESSAGE_UNFOUND_PERSON_NAME);
    }

    @Test
    public void execute_validNameFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteByNameCommand deleteByNameCommand = prepareCommand(personToDelete.getName().toString());

        String expectedMessage =
                String.format(DeleteByNameCommand.MESSAGE_DELETE_PERSON_BY_NAME_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteByNameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        String personNotFound = "";

        DeleteByNameCommand deleteByNameCommand = prepareCommand(personNotFound);

        assertCommandFailure(deleteByNameCommand, model, Messages.MESSAGE_UNFOUND_PERSON_NAME);
    }

    /**
     * Returns a {@code DeleteByNameCommand} with the parameter {@code name}.
     */
    private DeleteByNameCommand prepareCommand(String name) {
        DeleteByNameCommand deleteByNameCommand = new DeleteByNameCommand(name);
        deleteByNameCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteByNameCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
```
