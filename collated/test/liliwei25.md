# liliwei25
###### \java\guitests\guihandles\MapWindowHandle.java
``` java
/**
 * A handler for the {@code MapWindow} of the UI.
 */
public class MapWindowHandle extends StageHandle {
    private static final String BROWSER_ID = "#map";

    private boolean isWebViewLoaded;

    public MapWindowHandle(Stage mapWindowStage) {
        super(mapWindowStage);
        WebView webView = getChildNode(BROWSER_ID);
        WebEngine engine = webView.getEngine();
        new GuiRobot().interact(() -> engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                isWebViewLoaded = false;
            } else if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
            }
        }));
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(BROWSER_ID));
    }

    /**
     * Returns true if the browser is done loading a page, or if this browser has yet to load any page.
     */
    public boolean isLoaded() {
        return isWebViewLoaded;
    }
}
```
###### \java\guitests\guihandles\PersonInfoPanelHandle.java
``` java
/**
 * A handler for the {@code InfoPanel} of the UI.
 */
public class PersonInfoPanelHandle extends NodeHandle<Node> {
    public static final String INFO_PANEL_ID = "#personInfoPanel";

    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String BIRTHDAY_FIELD_ID = "#birthday";
    private static final String REMARK_FIELD_ID = "#remark";
    private static final String TAGS_FIELD_ID = "#tags";


    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label birthdayLabel;
    private final Label remarkLabel;
    private final List<Label> tagLabels;


    public PersonInfoPanelHandle(Node cardNode) {
        super(cardNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);
        this.remarkLabel = getChildNode(REMARK_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getBirthday() {
        return birthdayLabel.getText();
    }

    public String getRemark() {
        return remarkLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
```
###### \java\seedu\address\logic\commands\BirthdayCommandTest.java
``` java
/**
 * Test BirthdayCommand
 */
public class BirthdayCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        BirthdayCommand birthdayCommand = prepareCommand(INDEX_FIRST_PERSON, new Birthday(VALID_BIRTHDAY_AMY));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person editedPerson = getEditedPerson(personToEdit);
        expectedModel.updatePerson(personToEdit, editedPerson);

        String expectedMessage = String.format(BirthdayCommand.MESSAGE_BIRTHDAY_PERSON_SUCCESS, editedPerson);

        assertCommandSuccess(birthdayCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Creates a new {@code Person} with a valid birthday
     */
    private Person getEditedPerson(ReadOnlyPerson personToEdit) throws IllegalValueException {
        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getRemark(), new Birthday(VALID_BIRTHDAY_AMY),
                    personToEdit.getTags(), personToEdit.getPicture(), personToEdit.getFavourite());
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        BirthdayCommand birthdayCommand = prepareCommand(outOfBoundIndex, BIRTHDAY_BOB);

        assertCommandFailure(birthdayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        BirthdayCommand birthdayCommand = prepareCommand(outOfBoundIndex, BIRTHDAY_BOB);

        assertCommandFailure(birthdayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicatePerson_failure() throws Exception {
        BirthdayCommand birthdayCommand = prepareCommandForDuplicateException(INDEX_FIRST_PERSON, BIRTHDAY_ALICE);

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_DUPLICATE_PERSON);

        birthdayCommand.execute();
    }

    @Test
    public void execute_missingPerson_failure() throws Exception {
        BirthdayCommand birthdayCommand = prepareCommandForNotFoundException(INDEX_FIRST_PERSON, BIRTHDAY_ALICE);

        thrown.expect(AssertionError.class);
        thrown.expectMessage(MESSAGE_MISSING_PERSON);

        birthdayCommand.execute();
    }

    @Test
    public void equals() {
        final BirthdayCommand standardCommand = new BirthdayCommand(INDEX_FIRST_PERSON, BIRTHDAY_AMY);

        // same values -> returns true
        BirthdayCommand commandWithSameValues = new BirthdayCommand(INDEX_FIRST_PERSON, BIRTHDAY_AMY);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new BirthdayCommand(INDEX_SECOND_PERSON, BIRTHDAY_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new BirthdayCommand(INDEX_FIRST_PERSON, BIRTHDAY_BOB)));
    }

    /**
     * Returns an {@code BirthdayCommand} with parameters {@code index} and {@code birthday}
     */
    private BirthdayCommand prepareCommand(Index index, Birthday birthday) {
        BirthdayCommand birthdayCommand = new BirthdayCommand(index, birthday);
        birthdayCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return birthdayCommand;
    }

    /**
     * Returns an {@code BirthdayCommand} with parameters {@code index} and {@code birthday}
     * to test {@code DuplicatePersonException}
     */
    private BirthdayCommand prepareCommandForDuplicateException(Index index, Birthday birthday) {
        BirthdayCommand birthdayCommand = new BirthdayCommand(index, birthday);
        birthdayCommand.setData(new ModelStubThrowingDuplicatePersonException(), new CommandHistory(),
                new UndoRedoStack());
        return birthdayCommand;
    }

    /**
     * Returns an {@code BirthdayCommand} with parameters {@code index} and {@code birthday}
     * to test {@code PersonNotFoundException}
     */
    private BirthdayCommand prepareCommandForNotFoundException(Index index, Birthday birthday) {
        BirthdayCommand birthdayCommand = new BirthdayCommand(index, birthday);
        birthdayCommand.setData(new ModelStubThrowingPersonNotFoundException(), new CommandHistory(),
                new UndoRedoStack());
        return birthdayCommand;
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to edit birthday.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always throw a {@code PersonNotFoundException} when trying to update person.
     */
    private class ModelStubThrowingPersonNotFoundException extends ModelStub {
        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {
    private static final String COMMA = ", ";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredListMultiple_success() throws Exception {
        ReadOnlyPerson firstPersonToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson secondPersonToDelete = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                secondPersonToDelete.getName() + COMMA + firstPersonToDelete.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(firstPersonToDelete);
        expectedModel.deletePerson(secondPersonToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_missingPerson_failure() throws Exception {
        DeleteCommand deleteCommand = prepareCommandForNotFoundException(INDEX_FIRST_PERSON);

        thrown.expect(AssertionError.class);
        thrown.expectMessage(MESSAGE_MISSING_PERSON);

        deleteCommand.execute();
    }

    @Test
    public void equals() {
        Index[] firstPersonArray = new Index[] {INDEX_FIRST_PERSON};
        Index[] secondPersonArray = new Index[] {INDEX_SECOND_PERSON};

        DeleteCommand deleteFirstCommand = new DeleteCommand(firstPersonArray);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondPersonArray);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(firstPersonArray);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(Index... index) {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }

    /**
     * Returns an {@code DeleteCommand} with parameters {@code index}
     * to test {@code PersonNotFoundException}
     */
    private DeleteCommand prepareCommandForNotFoundException(Index index) {
        DeleteCommand deleteCommand = new DeleteCommand(new Index[]{index});
        deleteCommand.setData(new ModelStubThrowingPersonNotFoundException(), new CommandHistory(),
                new UndoRedoStack());
        return deleteCommand;
    }

    /**
     * A Model stub that always throw a {@code PersonNotFoundException} when trying to delete person.
     */
    private class ModelStubThrowingPersonNotFoundException extends ModelStub {
        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \java\seedu\address\logic\commands\ImageCommandTest.java
``` java
public class ImageCommandTest {
    private static final boolean REMOVE = true;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ImageCommand imageCommand = prepareCommand(INDEX_FIRST_PERSON, !REMOVE);

        String expectedMessage = String.format(ImageCommand.MESSAGE_IMAGE_SUCCESS, person);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.changeImage(person);

        assertCommandSuccess(imageCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilterListRemoveImage_success() throws Exception {
        ReadOnlyPerson person = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        ImageCommand imageCommand = prepareCommand(INDEX_SECOND_PERSON, REMOVE);

        String expectedMessage = String.format(ImageCommand.MESSAGE_IMAGE_SUCCESS, person);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        ReadOnlyPerson personToEdit = expectedModel.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person editedPerson = getDefaultPerson(personToEdit);
        expectedModel.updatePerson(personToEdit, editedPerson);

        assertCommandSuccess(imageCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ImageCommand imageCommand = prepareCommand(outOfBoundIndex, !REMOVE);

        assertCommandFailure(imageCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ImageCommand imageCommand = prepareCommand(outOfBoundIndex, REMOVE);

        assertCommandFailure(imageCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicatePerson_failure() throws Exception {
        ImageCommand imageCommand = prepareCommandForDuplicateException(INDEX_FIRST_PERSON, REMOVE);

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_DUPLICATE_PERSON);

        imageCommand.execute();
    }

    @Test
    public void execute_missingPerson_failure() throws Exception {
        ImageCommand imageCommand = prepareCommandForNotFoundException(INDEX_FIRST_PERSON);

        thrown.expect(AssertionError.class);
        thrown.expectMessage(MESSAGE_MISSING_PERSON);

        imageCommand.execute();
    }

    @Test
    public void equals() {
        ImageCommand imageFirstCommand = new ImageCommand(INDEX_FIRST_PERSON, !REMOVE);
        ImageCommand imageSecondCommand = new ImageCommand(INDEX_SECOND_PERSON, !REMOVE);
        ImageCommand imageFirstRemoveCommand = new ImageCommand(INDEX_FIRST_PERSON, REMOVE);

        // same object -> returns true
        assertTrue(imageFirstCommand.equals(imageFirstCommand));

        // same values -> returns true
        ImageCommand imageFirstCommandCopy = new ImageCommand(INDEX_FIRST_PERSON, !REMOVE);
        assertTrue(imageFirstCommand.equals(imageFirstCommandCopy));

        // different types -> returns false
        assertFalse(imageFirstCommand.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(imageFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(imageFirstCommand.equals(imageSecondCommand));

        // different mode (remove/edit) -> returns false
        assertFalse(imageFirstCommand.equals(imageFirstRemoveCommand));
    }

    /**
     * Creates and returns new person with default picture
     */
    private Person getDefaultPerson(ReadOnlyPerson personToEdit) {
        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), personToEdit.getBirthday(),
                personToEdit.getTags(), new ProfilePicture(DEFAULT), personToEdit.getFavourite());
    }

    /**
     * Returns a {@code ImageCommand} with the parameter {@code index} and {@code remove}.
     */
    private ImageCommand prepareCommand(Index index, boolean remove) {
        ImageCommand imageCommand = new ImageCommand(index, remove);
        imageCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return imageCommand;
    }

    /**
     * Returns an {@code ImageCommand} with parameters {@code index} and {@code remove}
     * to test {@code DuplicatePersonException}
     */
    private ImageCommand prepareCommandForDuplicateException(Index index, boolean remove) {
        ImageCommand imageCommand = new ImageCommand(index, remove);
        imageCommand.setData(new ModelStubThrowingDuplicatePersonException(), new CommandHistory(),
                new UndoRedoStack());
        return imageCommand;
    }

    /**
     * Returns an {@code ImageCommand} with parameters {@code index} and {@code remove}
     * to test {@code PersonNotFoundException}
     */
    private ImageCommand prepareCommandForNotFoundException(Index index) {
        ImageCommand imageCommand = new ImageCommand(index, !REMOVE);
        imageCommand.setData(new ModelStubThrowingPersonNotFoundException(), new CommandHistory(),
                new UndoRedoStack());
        return imageCommand;
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to edit image.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always throw a {@code PersonNotFoundException} when trying to change image.
     */
    private class ModelStubThrowingPersonNotFoundException extends ModelStub {
        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public void changeImage(ReadOnlyPerson target) throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \java\seedu\address\logic\commands\MapCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code MapCommand}.
 */
public class MapCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToMap = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        MapCommand mapCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(MapCommand.MESSAGE_MAP_SHOWN_SUCCESS, personToMap);
        assertEquals(mapCommand.executeUndoableCommand().feedbackToUser, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MapCommand mapCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(mapCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MapCommand mapCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(mapCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_missingPerson_failure() throws Exception {
        MapCommand mapCommand = prepareCommandForNotFoundException(INDEX_FIRST_PERSON);

        thrown.expect(AssertionError.class);
        thrown.expectMessage(MESSAGE_MISSING_PERSON);

        mapCommand.execute();
    }

    @Test
    public void equals() {
        MapCommand mapFirstCommand = new MapCommand(INDEX_FIRST_PERSON);
        MapCommand mapSecondCommand = new MapCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(mapFirstCommand.equals(mapFirstCommand));

        // same values -> returns true
        MapCommand mapFirstCommandCopy = new MapCommand(INDEX_FIRST_PERSON);
        assertTrue(mapFirstCommand.equals(mapFirstCommandCopy));

        // different types -> returns false
        assertFalse(mapFirstCommand.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(mapFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(mapFirstCommand.equals(mapSecondCommand));
    }

    /**
     * Returns a {@code MapCommand} with the parameter {@code index}.
     */
    private MapCommand prepareCommand(Index index) {
        MapCommand mapCommand = new MapCommand(index);
        mapCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return mapCommand;
    }

    /**
     * Returns an {@code MapCommand} with parameters {@code index}
     * to test {@code PersonNotFoundException}
     */
    private MapCommand prepareCommandForNotFoundException(Index index) {
        MapCommand mapCommand = new MapCommand(index);
        mapCommand.setData(new ModelStubThrowingPersonNotFoundException(), new CommandHistory(),
                new UndoRedoStack());
        return mapCommand;
    }

    /**
     * A Model stub that always throw a {@code PersonNotFoundException} when trying to change image.
     */
    private class ModelStubThrowingPersonNotFoundException extends ModelStub {
        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public void mapPerson(ReadOnlyPerson target) throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \java\seedu\address\logic\commands\RemoveTagCommandTest.java
``` java
public class RemoveTagCommandTest {
    private static final String INVALID_TAG = "a";
    private static final int FIRST_TAG = 1;
    private static final String ALL = "all";
    private static final String INDEX = "1";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validTagRemoveAll_success() throws Exception {
        Tag firstTag = model.getAddressBook().getTagList().get(FIRST_TAG);
        RemoveTagCommand removeTagCommand = prepareDeleteAllCommand(firstTag);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, firstTag, FROM, ALL);

        assertEquals(removeTagCommand.executeUndoableCommand().feedbackToUser, expectedMessage);
    }

    @Test
    public void execute_validTagRemove_success() throws Exception {
        Tag firstTag = model.getAddressBook().getTagList().get(FIRST_TAG);
        RemoveTagCommand removeTagCommand = prepareDeleteTagCommand(FIRST_INDEX, firstTag);

        String name =
                model.getFilteredPersonList().get(Index.fromOneBased(FIRST_TAG).getZeroBased()).getName().fullName;
        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, firstTag, FROM, name);

        assertEquals(removeTagCommand.executeUndoableCommand().feedbackToUser, expectedMessage);
    }

    @Test
    public void execute_notFoundTag_failure() throws Exception {
        RemoveTagCommand removeTagCommand = prepareDeleteAllCommand(new Tag(INVALID_TAG));

        assertCommandFailure(removeTagCommand, model, RemoveTagCommand.MESSAGE_TAG_NOT_FOUND);
    }

    @Test
    public void execute_duplicatePerson_failure() throws Exception {
        Tag firstTag = model.getAddressBook().getTagList().get(FIRST_TAG);
        RemoveTagCommand removeTagCommand = prepareCommandForDuplicateException(firstTag);

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_DUPLICATE_PERSON);

        removeTagCommand.execute();
    }

    @Test
    public void execute_missingPerson_failure() throws Exception {
        Tag firstTag = model.getAddressBook().getTagList().get(FIRST_TAG);
        RemoveTagCommand removeTagCommand = prepareCommandForNotFoundException(firstTag);

        thrown.expect(AssertionError.class);
        thrown.expectMessage(MESSAGE_MISSING_PERSON);

        removeTagCommand.execute();
    }

    @Test
    public void equals() throws Exception {
        final RemoveTagCommand standardCommand = new RemoveTagCommand(ALL, new Tag(VALID_TAG_FRIEND));

        // same values -> returns true
        RemoveTagCommand commandWithSameValues = new RemoveTagCommand(ALL, new Tag(VALID_TAG_FRIEND));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemoveTagCommand(ALL, new Tag(VALID_TAG_HUSBAND))));

        // different index - > returns false
        assertFalse(standardCommand.equals(new RemoveTagCommand(INDEX, new Tag(VALID_TAG_FRIEND))));
    }

    /**
     * Returns a {@code RemoveTagCommand} with {@code index} and tag to delete tag from person of index
     */
    private RemoveTagCommand prepareDeleteTagCommand(String index, Tag target) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(index, target);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        return removeTagCommand;
    }

    /**
     * Returns {@code RemoveTagCommand} with {@code Tag} that deletes tag from all persons
     */
    private RemoveTagCommand prepareDeleteAllCommand(Tag target) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(ALL, target);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeTagCommand;
    }

    /**
     * Returns an {@code RemoveTagCommand} with parameters {@code tag}
     * to test {@code DuplicatePersonException}
     */
    private RemoveTagCommand prepareCommandForDuplicateException(Tag tag) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(ALL, tag);
        removeTagCommand.setData(new ModelStubThrowingDuplicatePersonException(), new CommandHistory(),
                new UndoRedoStack());
        return removeTagCommand;
    }

    /**
     * Returns an {@code RemoveTagCommand} with parameters {@code tag}
     * to test {@code PersonNotFoundException}
     */
    private RemoveTagCommand prepareCommandForNotFoundException(Tag tag) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(ALL, tag);
        removeTagCommand.setData(new ModelStubThrowingPersonNotFoundException(), new CommandHistory(),
                new UndoRedoStack());
        return removeTagCommand;
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to update model.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always throw a {@code PersonNotFoundException} when trying to update model.
     */
    private class ModelStubThrowingPersonNotFoundException extends ModelStub {
        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson) throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \java\seedu\address\logic\parser\BirthdayCommandParserTest.java
``` java
/**
 * Test BirthdayCommandParser
 */
public class BirthdayCommandParserTest {

    private static final String INPUT_INVALID_INDEX = " -1 12-12-2012";
    private static final String INPUT_INVALID_DATE = " 1 0000";
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final int ONE_DAY_LATER = 1;
    private static final String INDEX_ONE = " 1 ";
    private static final String INPUT_MISSING_INDEX = "12-12-2012";
    private static final String INPUT_MISSING_BIRTHDAY = " 1 ";
    private static final String INPUT_CORRECT_FORMAT = " 1 " + "30-03-2002";
    private BirthdayCommandParser parser = new BirthdayCommandParser();

    @Test
    public void parse_validArgs_returnsBirthdayCommand() {
        assertParseSuccess(parser, INPUT_CORRECT_FORMAT, new BirthdayCommand(INDEX_FIRST_PERSON, BIRTHDAY_BOB));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no birthday input
        assertParseFailure(parser, INPUT_MISSING_BIRTHDAY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE));

        // invalid input
        assertParseFailure(parser, INPUT_MISSING_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidValue_failure() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        // invalid date
        assertParseFailure(parser, INPUT_INVALID_DATE, Birthday.MESSAGE_WRONG_DATE);

        // invalid birthday (input date after current date)
        assertParseFailure(parser, INDEX_ONE + LocalDate.now().plusDays(ONE_DAY_LATER).format(formatter),
                String.format(MESSAGE_LATE_DATE, LocalDate.now().format(formatter)));

        // invalid index
        assertParseFailure(parser, INPUT_INVALID_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);
    }
}
```
###### \java\seedu\address\logic\parser\ImageCommandParserTest.java
``` java
public class ImageCommandParserTest {

    private static final String VALID_INPUT = "1";
    private static final String VALID_INPUT_REMOVE = "1 remove";
    private static final String INVALID_INDEX_INPUT = "a";
    private static final String INVALID_TYPE_INPUT = "1 edit";
    private static final boolean REMOVE = true;

    private ImageCommandParser parser = new ImageCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        // edit image
        assertParseSuccess(parser, VALID_INPUT, new ImageCommand(INDEX_FIRST_PERSON, !REMOVE));

        // remove image
        assertParseSuccess(parser, VALID_INPUT_REMOVE, new ImageCommand(INDEX_FIRST_PERSON, REMOVE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_INDEX_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImageCommand.MESSAGE_USAGE));

        assertParseFailure(parser, INVALID_TYPE_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImageCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\MapCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the MapCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the MapCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class MapCommandParserTest {

    private static final String VALID_INPUT = "1";
    private static final String INVALID_INPUT = "a";
    private MapCommandParser parser = new MapCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, VALID_INPUT, new MapCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\RemoveTagCommandParserTest.java
``` java
public class RemoveTagCommandParserTest {
    private static final String VALID_INPUT = "test";
    private static final String VALID_FIRST_INPUT = "1 test";
    private static final String INVALID_INPUT = "";
    public static final String FIRST_INDEX = "1";
    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveTagCommand() throws Exception {
        // remove tag from all person
        assertParseSuccess(parser, VALID_INPUT, new RemoveTagCommand(RemoveTagCommand.ALL, new Tag(VALID_INPUT)));

        // remove tag from first person
        assertParseSuccess(parser, VALID_FIRST_INPUT, new RemoveTagCommand(FIRST_INDEX, new Tag(VALID_INPUT)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
/**
 * Test Birthday class
 */
public class BirthdayTest {

    private static final String INVALID_YEAR = "12-12-0000";
    private static final String INVALID_MONTH = "12-24-1995";
    private static final String INVALID_DAY = "50-12-1995";
    private static final String INVALID_FORMAT = "1";
    private static final String INVALID_DATE = "a";
    private static final String VALID_DATE = "01-01-2001";
    private static final String VALID_LEAP_YEAR = "29-02-2016";
    private static final String VALID_NON_LEAP_YEAR = "28-02-2017";
    private static final int ONE_DAY = 1;

    @Test
    public void isValidBirthday() {
        // invalid birthday
        assertFalse(Birthday.isValidBirthday(INVALID_DATE)); // non-integer
        assertFalse(Birthday.isValidBirthday(INVALID_FORMAT)); // incorrect format
        assertFalse(Birthday.isValidBirthday(INVALID_DAY)); // incorrect day
        assertFalse(Birthday.isValidBirthday(INVALID_MONTH)); // incorrect month
        assertFalse(Birthday.isValidBirthday(INVALID_YEAR)); // incorrect year

        // valid birthday
        assertTrue(Birthday.isValidBirthday(VALID_DATE)); // valid date
        assertTrue(Birthday.isValidBirthday(VALID_LEAP_YEAR)); // test valid leap year
        assertTrue(Birthday.isValidBirthday(VALID_NON_LEAP_YEAR)); // test valid non-leap year
    }

    @Test
    public void isDateCorrect() {

        // dates after current date
        assertFalse(Birthday.isDateCorrect(LocalDate.now().plusDays(ONE_DAY)));

        // current date
        assertTrue(Birthday.isDateCorrect(LocalDate.now()));

        // before current date
        assertTrue(Birthday.isDateCorrect(LocalDate.now().minusDays(ONE_DAY)));
    }
}
```
###### \java\seedu\address\testutil\ModelStub.java
``` java
/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        fail("This method should not be called.");
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        fail("This method should not be called.");
    }

    @Override
    public void updateListToShowAll() {
        fail("Thi method should not be called.");
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
    public void sortPerson(String target) {
        fail("This method should not be called.");
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException , PersonNotFoundException {
        fail("This method should not be called.");
    }

    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        fail("This method should not be called.");
    }

    @Override
    public void removeTag(Tag tag) {
        fail("This method should not be called.");
    }

    @Override
    public void mapPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        fail("This method should not be called.");
    }

    @Override
    public void changeImage(ReadOnlyPerson target) throws PersonNotFoundException {
        fail("This method should not be called.");
    }
}
```
###### \java\seedu\address\ui\MapWindowTest.java
``` java
public class MapWindowTest extends GuiUnitTest {
    private static final String GOOGLE_MAPS_URL_PREFIX = "https://www.google.com.sg/maps/search/";
    private static final String GOOGLE_SEARCH_URL_SUFFIX = "/data=!4m2!2m1!4b1?dg=dbrw&newdg=1";
    private MapWindow mapWindow;
    private MapWindowHandle mapWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> mapWindow = new MapWindow(ALICE));
        Stage mapWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(mapWindow.getRoot().getScene()));
        FxToolkit.showStage();
        mapWindowHandle = new MapWindowHandle(mapWindowStage);
    }

    @Test
    public void display() throws Exception {
        URL expectedHelpPage = new URL(GOOGLE_MAPS_URL_PREFIX
                + ALICE.getAddress().getMapableAddress().trim().replaceAll(SPACE, PLUS) + GOOGLE_SEARCH_URL_SUFFIX);
        waitUntilMapBrowserLoaded(mapWindowHandle);
        assertEquals(expectedHelpPage, mapWindowHandle.getLoadedUrl());
    }
}
```
###### \java\seedu\address\ui\PersonInfoPanelTest.java
``` java
public class PersonInfoPanelTest extends GuiUnitTest {

    private static final String DIFFERENT_NAME = "differentName";

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags().build();
        PersonInfoPanel personInfoPanel = new PersonInfoPanel(personWithNoTags);
        uiPartRule.setUiPart(personInfoPanel);
        assertPersonInfoDisplay(personInfoPanel, personWithNoTags);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        personInfoPanel = new PersonInfoPanel(personWithTags);
        uiPartRule.setUiPart(personInfoPanel);
        assertPersonInfoDisplay(personInfoPanel, personWithTags);

        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithTags.setName(ALICE.getName());
            personWithTags.setAddress(ALICE.getAddress());
            personWithTags.setEmail(ALICE.getEmail());
            personWithTags.setPhone(ALICE.getPhone());
            personWithTags.setBirthday(ALICE.getBirthday());
            personWithTags.setRemark(ALICE.getRemark());
            personWithTags.setTags(ALICE.getTags());
        });
        assertPersonInfoDisplay(personInfoPanel, personWithTags);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PersonInfoPanel personInfoPanel = new PersonInfoPanel(person);

        // same person -> returns true
        PersonInfoPanel copy = new PersonInfoPanel(person);
        assertTrue(personInfoPanel.equals(copy));

        // same object -> returns true
        assertTrue(personInfoPanel.equals(personInfoPanel));

        // null -> returns false
        assertFalse(personInfoPanel.equals(null));

        // different types -> returns false
        assertFalse(personInfoPanel.equals(0));

        // different person -> returns false
        Person differentPerson = new PersonBuilder().withName(DIFFERENT_NAME).build();
        assertFalse(personInfoPanel.equals(new PersonInfoPanel(differentPerson)));
    }

    /**
     * Asserts that {@code personInfoPanel} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertPersonInfoDisplay(PersonInfoPanel personInfoPanel, ReadOnlyPerson expectedPerson) {
        guiRobot.pauseForHuman();

        PersonInfoPanelHandle personInfoPanelHandle = new PersonInfoPanelHandle(personInfoPanel.getRoot());

        // verify person details are displayed correctly
        assertInfoPanelDisplaysPerson(expectedPerson, personInfoPanelHandle);
    }
}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualPanel} displays the details of {@code expectedPerson}
     *
     * @param expectedPerson expected details to be shown
     * @param actualPanel actual details shown
     */
    public static void assertInfoPanelDisplaysPerson(ReadOnlyPerson expectedPerson, PersonInfoPanelHandle actualPanel) {
        assertEquals(expectedPerson.getName().fullName, actualPanel.getName());
        assertEquals(expectedPerson.getAddress().value, actualPanel.getAddress());
        assertEquals(expectedPerson.getPhone().value, actualPanel.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualPanel.getEmail());
        assertEquals(expectedPerson.getBirthday().value, actualPanel.getBirthday());
        assertEquals(expectedPerson.getRemark().value, actualPanel.getRemark());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualPanel.getTags());
    }
```
