# soonwj
###### \java\seedu\address\logic\commands\DeleteProfilePicCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteProfilePicCommand}.
 */
public class DeleteProfilePicCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteProfilePicCommand deleteProfilePicCommand = prepareCommand(INDEX_FIRST_PERSON);

        Person deletedProfilePicPerson = new Person(personToDelete);
        deletedProfilePicPerson.setProfilePic(new ProfilePic());

        String expectedMessage = String.format(DeleteProfilePicCommand.MESSAGE_DELETE_PROFILE_PIC_SUCCESS,
                personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToDelete, deletedProfilePicPerson);

        assertCommandSuccess(deleteProfilePicCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteProfilePicCommand deleteProfilePicCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteProfilePicCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteProfilePicCommand deleteProfilePicCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteProfilePicCommand.MESSAGE_DELETE_PROFILE_PIC_SUCCESS,
                personToDelete);

        Person deletedProfilePicPerson = new Person(personToDelete);
        deletedProfilePicPerson.setProfilePic(new ProfilePic());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToDelete, deletedProfilePicPerson);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(deleteProfilePicCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteProfilePicCommand deleteProfilePicCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteProfilePicCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteProfilePicCommand deleteFirstCommand = new DeleteProfilePicCommand(INDEX_FIRST_PERSON);
        DeleteProfilePicCommand deleteSecondCommand = new DeleteProfilePicCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteProfilePicCommand deleteFirstCommandCopy = new DeleteProfilePicCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteProfilePicCommand} with the parameter {@code index}.
     */
    private DeleteProfilePicCommand prepareCommand(Index index) {
        DeleteProfilePicCommand deleteProfilePicCommand = new DeleteProfilePicCommand(index);
        deleteProfilePicCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteProfilePicCommand;
    }
}
```
###### \java\seedu\address\logic\commands\UpdateProfilePicCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for UpdateProfilePicCommand.
 */
public class UpdateProfilePicCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_webUrlUnfilteredList_success() throws Exception {
        ReadOnlyPerson updatedProfilePicPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedProfilePicturePerson = new Person(updatedProfilePicPerson);
        ProfilePic profilePic = new ProfilePic(VALID_WEB_IMAGE_URL);
        updatedProfilePicturePerson.setProfilePic(profilePic);
        UpdateProfilePicCommand updateProfilePicCommand = prepareCommand(INDEX_FIRST_PERSON, profilePic);

        String expectedMessage = String.format(UpdateProfilePicCommand.MESSAGE_UPDATE_PROFILE_PIC_SUCCESS,
                updatedProfilePicPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedProfilePicturePerson);

        assertCommandSuccess(updateProfilePicCommand, model, expectedMessage, expectedModel);

        Files.delete(Paths.get(urlToPath(model.getFilteredPersonList().get(0).getProfilePic().toString())));
    }

    @Test
    public void execute_localUrlUnfilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson updatedProfilePicPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedProfilePicturePerson = new Person(updatedProfilePicPerson);
        ProfilePic profilePic = new ProfilePic(VALID_LOCAL_IMAGE_URL);
        updatedProfilePicturePerson.setProfilePic(profilePic);
        UpdateProfilePicCommand updateProfilePicCommand = prepareCommand(INDEX_FIRST_PERSON, profilePic);

        String expectedMessage = String.format(UpdateProfilePicCommand.MESSAGE_UPDATE_PROFILE_PIC_SUCCESS,
                updatedProfilePicturePerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedProfilePicturePerson);

        assertCommandSuccess(updateProfilePicCommand, model, expectedMessage, expectedModel);

        Files.delete(Paths.get(urlToPath(model.getFilteredPersonList().get(0).getProfilePic().toString())));
    }

    @Test
    public void execute_webUrlFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson updatedProfilePicPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedProfilePicturePerson = new Person(updatedProfilePicPerson);
        ProfilePic profilePic = new ProfilePic(VALID_WEB_IMAGE_URL);
        updatedProfilePicturePerson.setProfilePic(profilePic);
        UpdateProfilePicCommand updateProfilePicCommand = prepareCommand(INDEX_FIRST_PERSON, profilePic);

        String expectedMessage = String.format(UpdateProfilePicCommand.MESSAGE_UPDATE_PROFILE_PIC_SUCCESS,
                updatedProfilePicPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedProfilePicturePerson);

        assertCommandSuccess(updateProfilePicCommand, model, expectedMessage, expectedModel);

        Files.delete(Paths.get(urlToPath(model.getFilteredPersonList().get(0).getProfilePic().toString())));
    }

    @Test
    public void execute_localUrlFilteredList_success() throws Exception {
        ReadOnlyPerson updatedProfilePicPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedProfilePicturePerson = new Person(updatedProfilePicPerson);
        ProfilePic profilePic = new ProfilePic(VALID_LOCAL_IMAGE_URL);
        updatedProfilePicturePerson.setProfilePic(profilePic);
        UpdateProfilePicCommand updateProfilePicCommand = prepareCommand(INDEX_FIRST_PERSON, profilePic);

        String expectedMessage = String.format(UpdateProfilePicCommand.MESSAGE_UPDATE_PROFILE_PIC_SUCCESS,
                updatedProfilePicPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedProfilePicturePerson);

        assertCommandSuccess(updateProfilePicCommand, model, expectedMessage, expectedModel);

        Files.delete(Paths.get(urlToPath(model.getFilteredPersonList().get(0).getProfilePic().toString())));
    }

    @Test
    public void execute_filteredListInvalidIndex_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UpdateProfilePicCommand updateProfilePicCommand = prepareCommand(outOfBoundIndex, new ProfilePic());

        assertCommandFailure(updateProfilePicCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        final UpdateProfilePicCommand standardCommand = new UpdateProfilePicCommand(INDEX_FIRST_PERSON,
                new ProfilePic(VALID_LOCAL_IMAGE_URL));

        // same values -> returns true
        UpdateProfilePicCommand commandWithSameValues = new UpdateProfilePicCommand(INDEX_FIRST_PERSON,
                new ProfilePic(VALID_LOCAL_IMAGE_URL));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateProfilePicCommand(INDEX_SECOND_PERSON,
                new ProfilePic(VALID_LOCAL_IMAGE_URL))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateProfilePicCommand(INDEX_FIRST_PERSON,
                new ProfilePic(VALID_WEB_IMAGE_URL))));
    }

    /**
     * Returns an {@code UpdateProfilePicCommand} with parameters {@code index} and {@code profilePic}
     */
    private UpdateProfilePicCommand prepareCommand(Index index, ProfilePic profilePic) {
        UpdateProfilePicCommand updateProfilePicCommand = new UpdateProfilePicCommand(index, profilePic);
        updateProfilePicCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return updateProfilePicCommand;
    }

    private String urlToPath(String url) {
        return url.substring(url.indexOf("ProfilePics"));
    }
}
```
###### \java\seedu\address\logic\parser\DeleteProfilePicCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteProfilePicCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteProfilePicCommandParserTest {
    private DeleteProfilePicCommandParser parser = new DeleteProfilePicCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteProfilePicCommand() {
        assertParseSuccess(parser, "1", new DeleteProfilePicCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteProfilePicCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\UpdateProfilePicCommandParserTest.java
``` java
public class UpdateProfilePicCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateProfilePicCommand.MESSAGE_USAGE);

    private UpdateProfilePicCommandParser parser = new UpdateProfilePicCommandParser();

    @Test
    public void parse_missingParts_failure() {
        //no index specified
        assertParseFailure(parser, URL_DESC_LOCAL, MESSAGE_INVALID_FORMAT);

        //no url specified
        assertParseFailure(parser, "1", UpdateProfilePicCommand.MESSAGE_NOT_UPDATED);

        //no index or url specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + URL_DESC_LOCAL, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + URL_DESC_LOCAL, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_URL_DESC, ProfilePic.MESSAGE_PROFILE_PIC_CONSTRAINTS); // invalid URL
    }

    @Test
    public void parse_validLocalUrl_success() throws Exception {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + URL_DESC_LOCAL;

        UpdateProfilePicCommand expectedCommand = new UpdateProfilePicCommand(targetIndex,
                new ProfilePic(VALID_LOCAL_IMAGE_URL));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validWebUrl_success() throws Exception {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + URL_DESC_WEB;

        UpdateProfilePicCommand expectedCommand = new UpdateProfilePicCommand(targetIndex,
                new ProfilePic(VALID_WEB_IMAGE_URL));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() throws Exception {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + URL_DESC_LOCAL + URL_DESC_WEB;

        UpdateProfilePicCommand expectedCommand = new UpdateProfilePicCommand(targetIndex,
                new ProfilePic(VALID_WEB_IMAGE_URL));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() throws Exception {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_URL_DESC + URL_DESC_WEB;

        UpdateProfilePicCommand expectedCommand = new UpdateProfilePicCommand(targetIndex,
                new ProfilePic(VALID_WEB_IMAGE_URL));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\model\person\ProfilePicTest.java
``` java
public class ProfilePicTest {
    @Test
    public void isValidUrl() {
        // invalid URL
        assertFalse(ProfilePic.isValidUrl("")); // empty string
        assertFalse(ProfilePic.isValidUrl("images/fail.png")); // malformed URL

        // valid addresses
        assertTrue(ProfilePic.isValidUrl(VALID_WEB_IMAGE_URL)); //web URL
        assertTrue(ProfilePic.isValidUrl(VALID_LOCAL_IMAGE_URL)); //local URL
    }
}
```
