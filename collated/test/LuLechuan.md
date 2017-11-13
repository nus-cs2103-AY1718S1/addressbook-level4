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
###### \java\seedu\address\logic\commands\UploadPhotoCommandTest.java
``` java
public class UploadPhotoCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_personAcceptedByModel_updatePhotoSuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withPhoto(
                System.getProperty("user.dir") + "/docs/images/wolf.jpg").build();

        Photo photo = new Photo(System.getProperty("user.dir") + "/docs/images/wolf.jpg");
        UploadPhotoCommand uploadPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, photo);

        String expectedMessage = String.format(UploadPhotoCommand.MESSAGE_UPDATE_PERSON_PHOTO_SUCCESS, updatedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(uploadPhotoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personAcceptedByModel_deleteIconPhoto() throws IllegalValueException, PersonNotFoundException {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withPhoto(null).build();

        Photo photo = new Photo(null);
        UploadPhotoCommand uploadPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, photo);

        String expectedMessage = String.format(UploadPhotoCommand.MESSAGE_UPDATE_PERSON_PHOTO_SUCCESS, updatedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(uploadPhotoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws IllegalValueException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Photo photo = new Photo(System.getProperty("user.dir") + "/docs/images/wolf.jpg");
        UploadPhotoCommand uploadPhotoCommand = prepareCommand(outOfBoundIndex, photo);

        assertCommandFailure(uploadPhotoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Returns a {@code CustomCommand} with the parameters {@code index + CustomFieldName + CustomFieldValue}.
     */
    private UploadPhotoCommand prepareCommand(Index index, Photo photo) {
        UploadPhotoCommand command = new UploadPhotoCommand(index, photo);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\parser\CustomCommandParserTest.java
``` java
public class CustomCommandParserTest {

    private CustomCommandParser parser = new CustomCommandParser();

    @Test
    public void parse_validArgs_returnsCustomCommand() throws IllegalValueException {
        assertParseSuccess(parser, "1 NickName Ah",
                new CustomCommand(INDEX_FIRST_PERSON, new CustomField("NickName", "Ah")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\DeleteByNameCommandParserTest.java
``` java
public class DeleteByNameCommandParserTest {

    private DeleteByNameCommandParser parser = new DeleteByNameCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteByNameCommand() {
        assertParseSuccess(parser, "John Doe", new DeleteByNameCommand("John Doe"));
    }
}
```
###### \java\seedu\address\logic\parser\UploadPhotoCommandParserTest.java
``` java
public class UploadPhotoCommandParserTest {

    private UploadPhotoCommandParser parser = new UploadPhotoCommandParser();

    @Test
    public void parse_validArgs_returnsUploadPhotoCommand() throws IllegalValueException {
        assertParseSuccess(parser, "1", new UploadPhotoCommand(INDEX_FIRST_PERSON, new Photo()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, UploadPhotoCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\customField\CustomFieldTest.java
``` java
public class CustomFieldTest {

    @Test
    public void isValidCustomField() {
        // valid custom field
        assertTrue(CustomField.isValidCustomField("NickName"));
    }

    @Test
    public void isInvalidCustomField() {
        // invalid custom fields
        assertFalse(CustomField.isValidCustomField("")); // empty string
        assertFalse(CustomField.isValidCustomField(" ")); // spaces only
    }
}
```
###### \java\seedu\address\model\person\PhotoTest.java
``` java
public class PhotoTest {

    @Test
    public void isKnownPhoto() {
        // valid photo path
        assertTrue(!Photo.isUnknownPath(System.getProperty("user.dir")
                + "/docs/images/default_photo.png")); // existed path
    }

    @Test
    public void isUnknownPhoto() {
        // invalid photo path
        assertFalse(!Photo.isUnknownPath("")); // empty string
        assertFalse(!Photo.isUnknownPath("doesNotExist.jpg")); // path does not exist
    }
}
```
###### \java\seedu\address\model\person\UniqueCustomFieldListTest.java
``` java
public class UniqueCustomFieldListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Logger logger = LogsCenter.getLogger(UniqueCustomFieldListTest.class);

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueCustomFieldList uniqueCustomFieldList = new UniqueCustomFieldList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueCustomFieldList.asObservableList().remove(0);
    }

    @Test
    public void getCorrectSizeAfterAddition() {
        UniqueCustomFieldList list = new UniqueCustomFieldList();
        boolean correctSize = false;
        try {
            list.add(new CustomField("NickName", "Ah"));
            list.add(new CustomField("Age", "21"));
            list.add(new CustomField("Birthday", "29/02/1996"));
            list.add(new CustomField("Age", "22"));
        } catch (IllegalValueException e) {
            logger.warning("Input value is invalid");
        }
        if (list.getSize() == 3) {
            correctSize = true;
        }
        assertTrue("The size of the phoneList after adding numbers is correct", correctSize);
    }

    @Test
    public void getCorrectSizeAfterRemoval() {
        UniqueCustomFieldList list = new UniqueCustomFieldList();
        boolean correctSize = false;
        try {
            list.add(new CustomField("NichName", "Ah"));
            list.add(new CustomField("Age", "21"));
            list.add(new CustomField("Age", ""));  // Removes custom field "Age"
        } catch (IllegalValueException e) {
            logger.warning("Input value are invalid");
        }
        if (list.getSize() == 1) {
            correctSize = true;
        }
        assertTrue("The size of the custom field list after removing values is correct", correctSize);
    }

    @Test
    public void isEqual() { //to test content in the list are equal even if the elements are added in different order
        UniqueCustomFieldList list1 = new UniqueCustomFieldList();
        UniqueCustomFieldList list2 = new UniqueCustomFieldList();
        boolean isEqual;
        try {
            list1.add(new CustomField("NickName", "Ah"));
            list1.add(new CustomField("Age", "21"));
            list1.add(new CustomField("Birthday", "29/02/1996"));
            list1.add(new CustomField("Age", ""));  // Removes custom field "Age"
            list2.add(new CustomField("Birthday", "29/02/1996"));
            list2.add(new CustomField("Age", "21"));
            list2.add(new CustomField("NickName", "Ah"));
            list2.add(new CustomField("Age", ""));  // Removes custom field "Age"
        } catch (IllegalValueException e) {
            logger.warning("Input value is invalid");
        }
        isEqual = list1.equalsOrderInsensitive(list2);
        assertTrue("The two lists are equal", isEqual);
    }

}
```
