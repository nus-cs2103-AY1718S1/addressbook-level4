# Pengyuz
###### /java/seedu/address/logic/commands/BinclearCommandTest.java
``` java
public class BinclearCommandTest {

    @Test
    public void execute_emptyRecyclebin_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, BinclearCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalRecyclbin(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, BinclearCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Generates a new {@code ClearCommand} which upon execution, clears the contents in {@code model}.
     */
    private BinclearCommand prepareCommand(Model model) {
        BinclearCommand command = new BinclearCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### /java/seedu/address/logic/commands/BindeleteCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class BindeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalRecyclbin(), new UserPrefs());
    private ArrayList<Index> personsToDelete1 = new ArrayList<>();
    private ArrayList<ReadOnlyPerson> persontodelete = new ArrayList<>();


    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getRecycleBinPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        persontodelete.add(personToDelete);

        personsToDelete1.add(INDEX_FIRST_PERSON);

        BindeleteCommand bindeleteCommand1 = prepareCommand(personsToDelete1);

        String expectedMessage1 = bindeleteCommand1.MESSAGE_SUCCESS;

        ModelManager expectedModel1 = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());

        expectedModel1.deleteBinPerson(persontodelete);

        assertCommandSuccess(bindeleteCommand1, model, expectedMessage1, expectedModel1);
    }

    @Test
    public void execute_validIndexUnfilteredList_success2() throws Exception {
        ReadOnlyPerson personToDelete = model.getRecycleBinPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson secondToDelete = model.getRecycleBinPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        persontodelete.clear();
        persontodelete.add(personToDelete);
        persontodelete.add(secondToDelete);

        personsToDelete1.clear();
        personsToDelete1.add(INDEX_FIRST_PERSON);
        personsToDelete1.add(INDEX_SECOND_PERSON);

        BindeleteCommand bindeleteCommand = prepareCommand(personsToDelete1);

        String expectedMessage1 = BindeleteCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel1 = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());

        expectedModel1.deleteBinPerson(persontodelete);

        assertCommandSuccess(bindeleteCommand, model, expectedMessage1, expectedModel1);
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getRecycleBinPersonList().size() + 1);
        personsToDelete1.clear();
        personsToDelete1.add(outOfBoundIndex);
        BindeleteCommand bindeleteCommand = prepareCommand(personsToDelete1);

        assertCommandFailure(bindeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstBinPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getRecycleBinPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        personsToDelete1.clear();
        personsToDelete1.add(INDEX_FIRST_PERSON);
        persontodelete.clear();
        persontodelete.add(personToDelete);
        BindeleteCommand bindeleteCommand = prepareCommand(personsToDelete1);

        String expectedMessage = String.format(BindeleteCommand.MESSAGE_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.deleteBinPerson(persontodelete);
        showNoBinPerson(expectedModel);

        assertCommandSuccess(bindeleteCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstBinPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        personsToDelete1.clear();
        personsToDelete1.add(INDEX_SECOND_PERSON);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecycleBin().getPersonList().size());

        BindeleteCommand bindeleteCommand = prepareCommand(personsToDelete1);

        assertCommandFailure(bindeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }



    @Test
    public void equals() {
        ArrayList<Index> first = new ArrayList<>();
        ArrayList<Index> second = new ArrayList<>();
        first.add(INDEX_FIRST_PERSON);
        second.add(INDEX_SECOND_PERSON);
        BindeleteCommand bindeleteFirstCommand = new BindeleteCommand(first);
        BindeleteCommand bindeleteSecondCommand = new BindeleteCommand(second);

        // same object -> returns true
        assertTrue(bindeleteFirstCommand.equals(bindeleteFirstCommand));

        // same values -> returns true
        BindeleteCommand deleteFirstCommandCopy = new BindeleteCommand(first);
        assertTrue(bindeleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(bindeleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(bindeleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(bindeleteFirstCommand.equals(bindeleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private BindeleteCommand prepareCommand(ArrayList<Index> indexes) {

        BindeleteCommand bindeleteCommand = new BindeleteCommand(indexes);
        bindeleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return bindeleteCommand;
    }


    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }

    private void showNoBinPerson(Model model) {
        model.updateFilteredBinList(p-> false);
        assert model.getRecycleBinPersonList().isEmpty();
    }

}
```
###### /java/seedu/address/logic/commands/BinrestoreCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class BinrestoreCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalRecyclbin(), new UserPrefs());
    private ArrayList<Index> personsToRestore = new ArrayList<>();
    private ArrayList<ReadOnlyPerson> persontorestore = new ArrayList<>();


    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getRecycleBinPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        persontorestore.add(personToDelete);

        personsToRestore.add(INDEX_FIRST_PERSON);

        BinrestoreCommand binrestoreCommand1 = prepareCommand(personsToRestore);

        String expectedMessage1 = binrestoreCommand1.MESSAGE_SUCCESS;

        ModelManager expectedModel1 = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());

        expectedModel1.restorePerson(persontorestore);

        assertCommandSuccess(binrestoreCommand1, model, expectedMessage1, expectedModel1);
    }

    @Test
    public void execute_validIndexUnfilteredList_success2() throws Exception {
        ReadOnlyPerson personToRestore = model.getRecycleBinPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson secondToRestore = model.getRecycleBinPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        persontorestore.clear();
        persontorestore.add(personToRestore);
        persontorestore.add(secondToRestore);

        personsToRestore.clear();
        personsToRestore.add(INDEX_FIRST_PERSON);
        personsToRestore.add(INDEX_SECOND_PERSON);

        BinrestoreCommand binrestoreCommand = prepareCommand(personsToRestore);

        String expectedMessage1 = BinrestoreCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel1 = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());

        expectedModel1.restorePerson(persontorestore);

        assertCommandSuccess(binrestoreCommand, model, expectedMessage1, expectedModel1);
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getRecycleBinPersonList().size() + 1);
        personsToRestore.clear();
        personsToRestore.add(outOfBoundIndex);
        BinrestoreCommand binrestoreCommand = prepareCommand(personsToRestore);

        assertCommandFailure(binrestoreCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstBinPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getRecycleBinPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        personsToRestore.clear();
        personsToRestore.add(INDEX_FIRST_PERSON);
        persontorestore.clear();
        persontorestore.add(personToDelete);
        BinrestoreCommand binrestoreCommand = prepareCommand(personsToRestore);

        String expectedMessage = String.format(BinrestoreCommand.MESSAGE_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.restorePerson(persontorestore);
        showNoBinPerson(expectedModel);

        assertCommandSuccess(binrestoreCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstBinPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        personsToRestore.clear();
        personsToRestore.add(INDEX_SECOND_PERSON);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecycleBin().getPersonList().size());

        BinrestoreCommand binrestoreCommand = prepareCommand(personsToRestore);

        assertCommandFailure(binrestoreCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }



    @Test
    public void equals() {
        ArrayList<Index> first = new ArrayList<>();
        ArrayList<Index> second = new ArrayList<>();
        first.add(INDEX_FIRST_PERSON);
        second.add(INDEX_SECOND_PERSON);
        BinrestoreCommand binrestoreFirstCommand = new BinrestoreCommand(first);
        BinrestoreCommand binrestoreSecondCommand = new BinrestoreCommand(second);

        // same object -> returns true
        assertTrue(binrestoreFirstCommand.equals(binrestoreFirstCommand));

        // same values -> returns true
        BinrestoreCommand binrestoreFirstCommandCopy = new BinrestoreCommand(first);
        assertTrue(binrestoreFirstCommand.equals(binrestoreFirstCommandCopy));

        // different types -> returns false
        assertFalse(binrestoreFirstCommand.equals(1));

        // null -> returns false
        assertFalse(binrestoreFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(binrestoreFirstCommand.equals(binrestoreSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private BinrestoreCommand prepareCommand(ArrayList<Index> indexes) {

        BinrestoreCommand binrestoreCommand = new BinrestoreCommand(indexes);
        binrestoreCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return binrestoreCommand;
    }


    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }

    private void showNoBinPerson(Model model) {
        model.updateFilteredBinList(p-> false);
        assert model.getRecycleBinPersonList().isEmpty();
    }

}
```
###### /java/seedu/address/logic/commands/DeleteCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private static final ReadOnlyPerson DUPLICATE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("124, Jurong West Ave 7, #08-112").withEmail("alicee@example.com")
            .withPhone("85333333")
            .withTags("workmate").build();
    private Model model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());
    private ArrayList<Index> personsToDelete1 = new ArrayList<>();

    @Test
    public void excute_duplicatePerson_sucess() throws Exception {

        String duplicate = "Alice Pauline";
        model.addPerson(DUPLICATE);

        List<String> duplicatePerson = Arrays.asList(duplicate);
        NameContainsKeywordsPredicate updatedpredicate = new NameContainsKeywordsPredicate(duplicatePerson);

        DeleteCommand deleteCommand = prepareCommand(duplicate);

        String expectedMessage = "Duplicate persons exist, please choose one to delete.";

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new AddressBook(), new UserPrefs());

        expectedModel.updateFilteredPersonList(updatedpredicate);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);

        model.deletePerson(DUPLICATE);

    }


    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        personsToDelete1.add(INDEX_FIRST_PERSON);

        ArrayList<ReadOnlyPerson> deletelist = new ArrayList<>();

        deletelist.add(personToDelete);

        DeleteCommand deleteCommand1 = prepareCommand(personsToDelete1);

        String expectedMessage1 = DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;

        ModelManager expectedModel1 = new ModelManager(model.getAddressBook(), new AddressBook(), new UserPrefs());

        expectedModel1.deletePerson(deletelist);

        assertCommandSuccess(deleteCommand1, model, expectedMessage1, expectedModel1);
    }

    @Test
    public void execute_validIndexUnfilteredList_success2() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson secondToDelete = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        personsToDelete1.clear();
        personsToDelete1.add(INDEX_FIRST_PERSON);
        personsToDelete1.add(INDEX_SECOND_PERSON);

        DeleteCommand deleteCommand1 = prepareCommand(personsToDelete1);

        String expectedMessage1 = DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;

        ModelManager expectedModel1 = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());

        ArrayList<ReadOnlyPerson> personlist = new ArrayList<>();
        personlist.add(personToDelete);
        personlist.add(secondToDelete);

        expectedModel1.deletePerson(personlist);

        assertCommandSuccess(deleteCommand1, model, expectedMessage1, expectedModel1);
    }

    @Test
    public void execute_validNameUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        personsToDelete1.clear();

        ArrayList<ReadOnlyPerson> deletelist = new ArrayList<>();

        deletelist.add(personToDelete);

        String deleteName = personToDelete.getName().fullName;

        DeleteCommand deleteCommand1 = prepareCommand(deleteName);

        String expectedMessage1 = DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;

        ModelManager expectedModel1 = new ModelManager(model.getAddressBook(), new AddressBook(), new UserPrefs());

        expectedModel1.deletePerson(deletelist);

        assertCommandSuccess(deleteCommand1, model, expectedMessage1, expectedModel1);
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        personsToDelete1.clear();
        personsToDelete1.add(outOfBoundIndex);
        DeleteCommand deleteCommand = prepareCommand(personsToDelete1);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        personsToDelete1.clear();

        personsToDelete1.add(INDEX_FIRST_PERSON);

        ArrayList<ReadOnlyPerson> deletelist = new ArrayList<>();

        deletelist.add(personToDelete);

        DeleteCommand deleteCommand = prepareCommand(personsToDelete1);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new AddressBook(), new UserPrefs());

        expectedModel.deletePerson(deletelist);

        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validNameFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        personsToDelete1.clear();

        ArrayList<ReadOnlyPerson> deletelist = new ArrayList<>();

        deletelist.add(personToDelete);

        String deleteName = personToDelete.getName().fullName;

        DeleteCommand deleteCommand = prepareCommand(deleteName);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new AddressBook(), new UserPrefs());
        expectedModel.deletePerson(deletelist);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        personsToDelete1.clear();
        personsToDelete1.add(INDEX_SECOND_PERSON);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(personsToDelete1);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        personsToDelete1.clear();
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        String name = personToDelete.getName().fullName;

        showFirstPersonOnly(model);


        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(name);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        ArrayList<Index> first = new ArrayList<>();
        ArrayList<Index> second = new ArrayList<>();
        first.add(INDEX_FIRST_PERSON);
        second.add(INDEX_SECOND_PERSON);
        DeleteCommand deleteFirstCommand = new DeleteCommand(first);
        DeleteCommand deleteSecondCommand = new DeleteCommand(second);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(first);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(ArrayList<Index> indexes) {

        DeleteCommand deleteCommand = new DeleteCommand(indexes);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    private DeleteCommand prepareCommand(String name) {
        DeleteCommand deleteCommand = new DeleteCommand(name);
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

}
```
###### /java/seedu/address/logic/commands/HelpCommandTest.java
``` java

public class HelpCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        CommandResult result = new HelpCommand().execute();
        assertEquals(SHOWING_HELP_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowHelpRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void excutesuccess() {
        CommandResult result = new HelpCommand("add").execute();
        assertEquals(AddCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("clear").execute();
        assertEquals(ClearCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("delete").execute();
        assertEquals(DeleteCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("edit").execute();
        assertEquals(EditCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("exit").execute();
        assertEquals(ExitCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("find").execute();
        assertEquals(FindCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("history").execute();
        assertEquals(HistoryCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("list").execute();
        assertEquals(ListCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("redo").execute();
        assertEquals(RedoCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("select").execute();
        assertEquals(SelectCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("sort").execute();
        assertEquals(SortCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("tagadd").execute();
        assertEquals(TagAddCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("tagremove").execute();
        assertEquals(TagRemoveCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("undo").execute();
        assertEquals(UndoCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("bin-fresh").execute();
        assertEquals(BinclearCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("bin-delete").execute();
        assertEquals(BindeleteCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("bin-restore").execute();
        assertEquals(BinrestoreCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("export").execute();
        assertEquals(ExportCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("tagfind").execute();
        assertEquals(TagFindCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("birthdayadd").execute();
        assertEquals(BirthdayAddCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("birthdayremove").execute();
        assertEquals(BirthdayRemoveCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("mapshow").execute();
        assertEquals(MapShowCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("maproute").execute();
        assertEquals(MapRouteCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("scheduleremove").execute();
        assertEquals(ScheduleRemoveCommand.MESSAGE_USAGE, result.feedbackToUser);

        result =  new HelpCommand("theme").execute();
        assertEquals(SwitchThemeCommand.MESSAGE_USAGE, result.feedbackToUser);
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_create() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getCreateCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_put() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getPutCommand(person));
        assertEquals(new AddCommand(person), command);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_remove() throws Exception {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD_2 + " I/" + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(todelete), command);
    }

    @Test
    public void parseCommand_minus() throws Exception {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD_3 + " I/" + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(todelete), command);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_search() throws Exception {
        List<String> keywords = Arrays.asList("n/", "foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD_2 + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_get() throws Exception {
        List<String> keywords = Arrays.asList("n/", "foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD_3 + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_binclear() throws Exception {
        assertTrue(parser.parseCommand(BinclearCommand.COMMAND_WORD) instanceof BinclearCommand);
        assertTrue(parser.parseCommand(BinclearCommand.COMMAND_WORD + " 3") instanceof BinclearCommand);
    }

    @Test
    public void parseCommand_bindelete() throws Exception {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        BindeleteCommand command = (BindeleteCommand) parser.parseCommand(
                BindeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new BindeleteCommand(todelete), command);
    }

    @Test
    public void parseCommand_bindresotre() throws Exception {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        BinrestoreCommand command = (BinrestoreCommand) parser.parseCommand(
                BinrestoreCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new BinrestoreCommand(todelete), command);
    }
```
###### /java/seedu/address/logic/parser/BindeleteCommandPaserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the BindeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class BindeleteCommandPaserTest {

    private BindeleteCommandParser parser = new BindeleteCommandParser();

    @Test
    public void parse_validArgs_returnsBindeleteCommand() {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "1", new BindeleteCommand(todelete));
    }

    @Test
    public void parse_twovalidArgs_returnsBindeleteCommand() {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        todelete.add(INDEX_SECOND_PERSON);
        assertParseSuccess(parser, "1 2", new BindeleteCommand(todelete));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "I/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, BindeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, BindeleteCommand.MESSAGE_USAGE));
    }

}
```
###### /java/seedu/address/logic/parser/BinrestoreCommandPaserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the BindeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class BinrestoreCommandPaserTest {

    private BinrestoreCommandParser parser = new BinrestoreCommandParser();

    @Test
    public void parse_validArgs_returnsBindresotreCommand() {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "1", new BinrestoreCommand(todelete));
    }

    @Test
    public void parse_twovalidArgs_returnsBindrestoreCommand() {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        todelete.add(INDEX_SECOND_PERSON);
        assertParseSuccess(parser, "1 2", new BinrestoreCommand(todelete));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "I/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BinrestoreCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, BinrestoreCommand.MESSAGE_USAGE));
    }

}
```
###### /java/seedu/address/logic/parser/DeleteCommandParserTest.java
``` java

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "I/1", new DeleteCommand(todelete));
    }

    @Test
    public void parse_validArgs_returnsDeleteCommand1() {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        todelete.add(INDEX_SECOND_PERSON);
        assertParseSuccess(parser, "I/1 2", new DeleteCommand(todelete));
    }

    @Test
    public void parse_validArgs_returnsDeleteCommand2() {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "n/Alice Pauline", new DeleteCommand("Alice Pauline"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "I/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidstartArgs_throwsParseException() {
        assertParseFailure(parser, "aI/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException1() {
        assertParseFailure(parser, "n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_invalidArgs_throwsParseException2() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/HelpCommandParserTest.java
``` java

public class HelpCommandParserTest {
    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parsesuccess() {
        assertParseSuccess(parser, AddCommand.COMMAND_WORD, new HelpCommand("add"));

        assertParseSuccess(parser, AddCommand.COMMAND_WORD_2, new HelpCommand("add"));

        assertParseSuccess(parser, AddCommand.COMMAND_WORD_3, new HelpCommand("add"));

        assertParseSuccess(parser, ClearCommand.COMMAND_WORD, new HelpCommand("clear"));

        assertParseSuccess(parser, DeleteCommand.COMMAND_WORD, new HelpCommand("delete"));

        assertParseSuccess(parser, DeleteCommand.COMMAND_WORD_2, new HelpCommand("delete"));

        assertParseSuccess(parser, DeleteCommand.COMMAND_WORD_3, new HelpCommand("delete"));

        assertParseSuccess(parser, EditCommand.COMMAND_WORD, new HelpCommand("edit"));

        assertParseSuccess(parser, EditCommand.COMMAND_WORD_2, new HelpCommand("edit"));

        assertParseSuccess(parser, EditCommand.COMMAND_WORD_3, new HelpCommand("edit"));

        assertParseSuccess(parser, ExitCommand.COMMAND_WORD, new HelpCommand("exit"));

        assertParseSuccess(parser, FindCommand.COMMAND_WORD, new HelpCommand("find"));

        assertParseSuccess(parser, FindCommand.COMMAND_WORD_2, new HelpCommand("find"));

        assertParseSuccess(parser, FindCommand.COMMAND_WORD_3, new HelpCommand("find"));

        assertParseSuccess(parser, HistoryCommand.COMMAND_WORD, new HelpCommand("history"));

        assertParseSuccess(parser, HistoryCommand.COMMAND_WORD_2, new HelpCommand("history"));

        assertParseSuccess(parser, ListCommand.COMMAND_WORD, new HelpCommand("list"));

        assertParseSuccess(parser, ListCommand.COMMAND_WORD_2, new HelpCommand("list"));

        assertParseSuccess(parser, ListCommand.COMMAND_WORD_3, new HelpCommand("list"));

        assertParseSuccess(parser, RedoCommand.COMMAND_WORD, new HelpCommand("redo"));

        assertParseSuccess(parser, SelectCommand.COMMAND_WORD, new HelpCommand("select"));

        assertParseSuccess(parser, SelectCommand.COMMAND_WORD_2, new HelpCommand("select"));

        assertParseSuccess(parser, SortCommand.COMMAND_WORD, new HelpCommand("sort"));

        assertParseSuccess(parser, TagAddCommand.COMMAND_WORD, new HelpCommand("tagadd"));

        assertParseSuccess(parser, TagRemoveCommand.COMMAND_WORD, new HelpCommand("tagremove"));

        assertParseSuccess(parser, TagFindCommand.COMMAND_WORD, new HelpCommand("tagfind"));

        assertParseSuccess(parser, BirthdayAddCommand.COMMAND_WORD, new HelpCommand("birthdayadd"));

        assertParseSuccess(parser, BirthdayRemoveCommand.COMMAND_WORD, new HelpCommand("birthdayremove"));

        assertParseSuccess(parser, MapShowCommand.COMMAND_WORD, new HelpCommand("mapshow"));

        assertParseSuccess(parser, MapRouteCommand.COMMAND_WORD, new HelpCommand("maproute"));

        assertParseSuccess(parser, ScheduleAddCommand.COMMAND_WORD, new HelpCommand("scheduleadd"));

        assertParseSuccess(parser, ScheduleRemoveCommand.COMMAND_WORD, new HelpCommand("scheduleremove"));

        assertParseSuccess(parser, BinclearCommand.COMMAND_WORD, new HelpCommand("bin-fresh"));

        assertParseSuccess(parser, BindeleteCommand.COMMAND_WORD, new HelpCommand("bin-delete"));

        assertParseSuccess(parser, BinrestoreCommand.COMMAND_WORD, new HelpCommand("bin-restore"));

        assertParseSuccess(parser, SwitchThemeCommand.COMMAND_WORD, new HelpCommand("theme"));

        assertParseSuccess(parser, ExportCommand.COMMAND_WORD, new HelpCommand("export"));

        assertParseSuccess(parser, UndoCommand.COMMAND_WORD, new HelpCommand("undo"));

    }

}
```
###### /java/seedu/address/testutil/TypicalRecycleBin.java
``` java
/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalRecycleBin {

    public static final ReadOnlyPerson ALICERE = new PersonBuilder().withName("Alicere Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255")
            .withTags("friends").build();
    public static final ReadOnlyPerson BENSONRE = new PersonBuilder().withName("Bensonre Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson CARLRE = new PersonBuilder().withName("Carlre Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final ReadOnlyPerson DANIELRE = new PersonBuilder().withName("Danielre Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").build();
    public static final ReadOnlyPerson ELLERE = new PersonBuilder().withName("Ellere Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final ReadOnlyPerson FIONARE = new PersonBuilder().withName("Fionare Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final ReadOnlyPerson GEORGERE = new PersonBuilder().withName("Georgere Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final ReadOnlyPerson HOONRE = new PersonBuilder().withName("Hoonre Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final ReadOnlyPerson IDARE = new PersonBuilder().withName("Idare Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMYRE = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final ReadOnlyPerson BOBRE = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalRecycleBin() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalRecyclbin() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyPerson> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICERE, BENSONRE, CARLRE, DANIELRE, ELLERE, FIONARE, GEORGERE));
    }
}
```
