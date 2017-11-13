# nbriannl
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void showMapOf(ReadOnlyPerson person) {
            fail("This method should not be called.");
        }

```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void showDirectionsTo(ReadOnlyPerson target, Address address) {
            fail("This method should not be called.");
        }

```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void deleteTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }

```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void editTag(Tag oldTag, Tag newTag) throws DuplicatePersonException,
                PersonNotFoundException, TagNotFoundException {
            fail("This method should not be called.");
        }

```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void checkMasterTagListHasAllTagsUsed () {
            fail("This method should not be called.");
        }

        @Override
        public HashMap<String, String> getThemeMap () {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void setCurrentTheme(String theme) {
            fail("This method should not be called.");
        }

        @Override
        /** Returns the current theme in use by the app */
        public String getCurrentTheme() {
            fail("This method should not be called.");
            return null;
        }

```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
    @Test
    public void execute_validTagUnfilteredList_success() throws Exception {
        Set<Tag> tagsToDelete = Stream.of(new Tag("friends")).collect(Collectors.toSet());
        DeleteCommand deleteCommand = prepareCommand(tagsToDelete);

        String expectedMessage = DeleteCommand.MESSAGE_DELETE_TAG_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(new Tag("friends"));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
    @Test
    public void execute_invalidTagUnfilteredList_throwsCommandException() throws Exception {
        Set<Tag> wrongTag = Stream.of(new Tag("nonexistent")).collect(Collectors.toSet());
        DeleteCommand deleteCommand = prepareCommand(wrongTag);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TAG_PROVIDED);

        Set<Tag> someWrongTags = Stream.of(new Tag("wrongtag"), new Tag("friends")).collect(Collectors.toSet());
        DeleteCommand deleteCommand2 = prepareCommand(someWrongTags);
        assertCommandFailure(deleteCommand2, model, Messages.MESSAGE_INVALID_TAG_PROVIDED);
    }

```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
    @Test
    public void execute_validTagFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        Set<Tag> tagsToDelete = Stream.of(new Tag("friends")).collect(Collectors.toSet());
        DeleteCommand deleteCommand = prepareCommand(tagsToDelete);

        String expectedMessage = DeleteCommand.MESSAGE_DELETE_TAG_SUCCESS;

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.deleteTag(new Tag("friends"));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
    @Test
    public void execute_invalidTagFilteredList_throwsCommandException() throws Exception {
        showFirstPersonOnly(model);

        Set<Tag> wrongTag = Stream.of(new Tag("nonexistent")).collect(Collectors.toSet());
        DeleteCommand deleteCommand = prepareCommand(wrongTag);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TAG_PROVIDED);

        Set<Tag> someWrongTags = Stream.of(new Tag("wrongtag"), new Tag("friends")).collect(Collectors.toSet());
        DeleteCommand deleteCommand2 = prepareCommand(someWrongTags);
        assertCommandFailure(deleteCommand2, model, Messages.MESSAGE_INVALID_TAG_PROVIDED);
    }

```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
    @Test
    public void equals() throws Exception {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand == null);

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        Set<Tag> firstSet = Stream.of(new Tag("word")).collect(Collectors.toSet());
        Set<Tag> secondSet = Stream.of(new Tag("other")).collect(Collectors.toSet());
        Set<Tag> thirdSet = Stream.of(new Tag("multiple"), new Tag("words")).collect(Collectors.toSet());

        DeleteCommand deleteFirstCommandTags = new DeleteCommand(firstSet);
        DeleteCommand deleteSecondCommandTags = new DeleteCommand(secondSet);
        DeleteCommand deleteThirdCommandTags = new DeleteCommand(thirdSet);

        // same object -> returns true
        assertTrue(deleteFirstCommandTags.equals(deleteFirstCommandTags));

        // same values -> returns true
        DeleteCommand deleteFirstCommandTagCopy = new DeleteCommand(firstSet);
        assertTrue(deleteFirstCommandTags.equals(deleteFirstCommandTagCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommandTags.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommandTags == null);

        // different tag -> returns false
        assertFalse(deleteFirstCommandTags.equals(deleteSecondCommandTags));
        assertFalse(deleteFirstCommandTags.equals(deleteThirdCommandTags));
    }

```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
    /**
     * Returns a {@code DeleteCommand} with the parameter {@code tagSet}.
     */
    private DeleteCommand prepareCommand(Set<Tag> tagSet) {
        DeleteCommand deleteCommand = new DeleteCommand(tagSet);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

```
###### \java\seedu\address\logic\commands\EditCommandTest.java
``` java
    @Test
    public void executeForTags_unfilteredList_success() throws Exception {
        Tag oldTag = new Tag("friends");
        Tag newTag = new Tag("enemy");
        EditCommand editCommand = prepareCommand(oldTag, newTag);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TAG_SUCCESS, oldTag);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.editTag(oldTag, newTag);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\EditCommandTest.java
``` java
    @Test
    public void executeForTags_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        Tag oldTag = new Tag("friends");
        Tag newTag = new Tag("enemy");
        EditCommand editCommand = prepareCommand(oldTag, newTag);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TAG_SUCCESS, oldTag);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.editTag(oldTag, newTag);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\EditCommandTest.java
``` java
    @Test
    public void executeForTags_invalidTagUnfilteredList_failure() throws Exception {
        Tag invalidTag = new Tag("idontevenexistlolololol");
        Tag newTag = new Tag("enemy");
        EditCommand editCommand = prepareCommand(invalidTag, newTag);

        assertCommandFailure(editCommand, model, MESSAGE_NONEXISTENT_TAG);
    }

    @Test
    public void executeForTags_invalidTagFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Tag invalidTag = new Tag("idontevenexistlolololol");
        Tag newTag = new Tag("enemy");
        EditCommand editCommand = prepareCommand(invalidTag, newTag);

        assertCommandFailure(editCommand, model, MESSAGE_NONEXISTENT_TAG);
    }

```
###### \java\seedu\address\logic\commands\EditCommandTest.java
``` java
    /**
     * Returns an {@code EditCommand} for tag with parameters {@code oldTag} and {@code newTag}
     */
    private EditCommand prepareCommand(Tag oldTag, Tag newTag) {
        EditCommand editCommand = new EditCommand(oldTag, newTag);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }
}
```
###### \java\seedu\address\logic\commands\GMapsCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code GMapsCommand}.
 */
public class GMapsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToFind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        GMapsCommand gMapsCommand = prepareCommand(INDEX_FIRST_PERSON, null);

        String expectedMessage = String.format(GMapsCommand.MESSAGE_SELECT_PERSON_SUCCESS,
                personToFind.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.showMapOf(personToFind);

        assertCommandSuccess(gMapsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        GMapsCommand gMapsCommand = prepareCommand(outOfBoundIndex, null);

        assertCommandFailure(gMapsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToFind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        GMapsCommand gMapsCommand = prepareCommand(INDEX_FIRST_PERSON, null);

        String expectedMessage = String.format(GMapsCommand.MESSAGE_SELECT_PERSON_SUCCESS,
                personToFind.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.showMapOf(personToFind);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(gMapsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        GMapsCommand gMapsCommand = prepareCommand(outOfBoundIndex, null);

        assertCommandFailure(gMapsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexValidAddressUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToFind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Address address = new Address("NUS");
        GMapsCommand gMapsCommand = prepareCommand(INDEX_FIRST_PERSON, address);

        String expectedMessage = String.format(GMapsCommand.MESSAGE_DIRECTIONS_TO_PERSON_SUCCESS,
                personToFind.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.showDirectionsTo(personToFind, address);

        assertCommandSuccess(gMapsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexValidAddressFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToFind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Address address = new Address("NUS");
        GMapsCommand gMapsCommand = prepareCommand(INDEX_FIRST_PERSON, address);

        String expectedMessage = String.format(GMapsCommand.MESSAGE_DIRECTIONS_TO_PERSON_SUCCESS,
                personToFind.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.showDirectionsTo(personToFind, address);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(gMapsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        Address address1 = new Address("NUS");
        Address address2 = new Address("NTU");

        GMapsCommand commandWithIndex = new GMapsCommand(INDEX_FIRST_PERSON, null);
        GMapsCommand commandWithDiffIndex = new GMapsCommand(INDEX_SECOND_PERSON, null);
        GMapsCommand commandWithIndexAndAddress = new GMapsCommand(INDEX_FIRST_PERSON, address1);
        GMapsCommand commandWithIndexAndDiffAddress = new GMapsCommand(INDEX_FIRST_PERSON, address2);
        GMapsCommand commandWithDiffIndexAndDiffAddress = new GMapsCommand(INDEX_SECOND_PERSON, address2);

        // same object -> returns true
        assertTrue(commandWithIndex.equals(commandWithIndex));

        // same values -> returns true
        GMapsCommand commandWithIndexCopy = new GMapsCommand(INDEX_FIRST_PERSON, null);
        assertTrue(commandWithIndex.equals(commandWithIndexCopy));

        // different types -> returns false
        assertFalse(commandWithIndex.equals(1));

        // null -> returns false
        assertFalse(commandWithIndex == null);

        // different index or address -> returns false
        assertFalse(commandWithIndex.equals(commandWithDiffIndex));
        assertFalse(commandWithIndex.equals(commandWithIndexAndAddress));
        assertFalse(commandWithIndex.equals(commandWithIndexAndDiffAddress));
        assertFalse(commandWithIndex.equals(commandWithDiffIndexAndDiffAddress));
    }

    /**
     * Returns a {@code GMapsCommand} with the parameter {@code index}, and {@code address}.
     */
    private GMapsCommand prepareCommand(Index index, Address address) {
        GMapsCommand gmapsCommand = new GMapsCommand(index , address);
        gmapsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return gmapsCommand;
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
###### \java\seedu\address\logic\parser\DeleteCommandParserTest.java
``` java
    @Test
    public void parse_validArgsTag_returnsDeleteCommand() throws Exception {
        Set<Tag> expectedTagSet = Stream.of(new Tag("tag")).collect(Collectors.toSet());
        assertParseSuccess(parser, " t/tag", new DeleteCommand(expectedTagSet));
        Set<Tag> expectedTagSet2 = Stream.of(new Tag("tag"), new Tag("tags")).collect(Collectors.toSet());
        assertParseSuccess(parser, " t/tag t/tags", new DeleteCommand(expectedTagSet2));
    }

```
###### \java\seedu\address\logic\parser\DeleteCommandParserTest.java
``` java
    @Test
    public void parse_invalidArgsTag_failure() throws Exception {
        assertParseFailure(parser, " t/*",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " t/tag t/*",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " t/* t/*",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

```
###### \java\seedu\address\logic\parser\DeleteCommandParserTest.java
``` java
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0, -1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1, -2, -3", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
    }

}
```
###### \java\seedu\address\logic\parser\GMapsCommandParserTest.java
``` java
public class GMapsCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, GMapsCommand.MESSAGE_USAGE);

    private GMapsCommandParser parser = new GMapsCommandParser();


    @Test
    public void parse_invalidIndex_failure() {
        // wrong index
        assertParseFailure(parser, "-1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndexValidAddress_failure() {
        // wrong index
        assertParseFailure(parser, "-1 a/NUS", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndexInvalidAddress_failure() {
        // wrong index
        assertParseFailure(parser, "-1 a/", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, "a/", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidAddress_failure() {
        // no index specified
        assertParseFailure(parser, "1 a/ ", Address.MESSAGE_ADDRESS_CONSTRAINTS);
    }


    @Test
    public void parse_validValueWithoutAddress_success() throws Exception {
        GMapsCommand expectedCommand = new GMapsCommand(INDEX_FIRST_PERSON, null);
        assertParseSuccess(parser, " 1", expectedCommand);
    }

    @Test
    public void parse_validValueWithAddress_success() throws Exception {
        Address address = new Address("NUS");
        GMapsCommand expectedCommand = new GMapsCommand(INDEX_FIRST_PERSON, address);
        assertParseSuccess(parser, " 1 a/NUS", expectedCommand);
    }
}
```
###### \java\seedu\address\ui\BrowserPanelTest.java
``` java
    @Before
    public void setUp() throws Exception {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));
        personAddressDisplayMapEventStub = new PersonAddressDisplayMapEvent(new Person(ALICE));
        personAddressDisplayDirectionsEventStub = new PersonAddressDisplayDirectionsEvent(new Person(ALICE),
                new Address("Blk 123 Yishun 61"));

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person, should not be loaded
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertNotEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());

        postNow(personAddressDisplayMapEventStub);
        URL expectedGMapsUrl = new URL(
                "https://www.google.com.sg/maps/search/123,%20Jurong%20West%20Ave%206,%20?dg=dbrw&newdg=1");
        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedGMapsUrl, browserPanelHandle.getLoadedUrl());

        postNow(personAddressDisplayDirectionsEventStub);
        URL expectedGMapsDirectionsUrl = new URL(
                "https://www.google.com.sg/maps/dir/Blk%20123%20Yishun%2061/123,"
                        + "%20Jurong%20West%20Ave%206,%20#08-111");
        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedGMapsDirectionsUrl, browserPanelHandle.getLoadedUrl());

    }
}
```
