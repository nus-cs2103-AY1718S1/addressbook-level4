# raisa2010
###### /java/seedu/address/logic/commands/AddTaskCommandIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AddTaskCommand}.
 */
public class AddTaskCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newTask_success() throws Exception {
        Task validTask = new TaskBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTask(validTask);

        assertCommandSuccess(prepareCommand(validTask, model), model,
                String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        Task taskInList = new Task(model.getAddressBook().getTaskList().get(0));
        assertCommandFailure(prepareCommand(taskInList, model), model, AddTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Generates a new {@code AddTaskCommand} which upon execution, adds {@code task} into the {@code model}.
     */
    private AddTaskCommand prepareCommand(Task task, Model model) {
        AddTaskCommand command = new AddTaskCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### /java/seedu/address/logic/commands/AddTaskCommandTest.java
``` java
public class AddTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTaskCommand(null);
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        AddTaskCommandTest.ModelStubAcceptingTaskAdded modelStub = new AddTaskCommandTest.ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = getAddTaskCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        AddTaskCommandTest.ModelStub modelStub = new AddTaskCommandTest.ModelStubThrowingDuplicateTaskException();
        Task validTask = new TaskBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        getAddTaskCommandForTask(validTask, modelStub).execute();
    }

    @Test
    public void equals() {
        Task assignment = new TaskBuilder().withDescription("Complete assignment").build();
        Task project = new TaskBuilder().withDescription("Project due").build();
        AddTaskCommand addAssignmentCommand = new AddTaskCommand(assignment);
        AddTaskCommand addProjectCommand = new AddTaskCommand(project);

        // same object -> returns true
        assertTrue(addAssignmentCommand.equals(addAssignmentCommand));

        // same values -> returns true
        AddTaskCommand addAssignmentCommandCopy = new AddTaskCommand(assignment);
        assertTrue(addAssignmentCommand.equals(addAssignmentCommandCopy));

        // different types -> returns false
        assertFalse(addAssignmentCommand.equals(1));

        // null -> returns false
        assertFalse(addAssignmentCommand.equals(null));

        // different task -> returns false
        assertFalse(addAssignmentCommand.equals(addProjectCommand));
    }

    /**
     * Generates a new AddTaskCommand with the details of the given task.
     */
    private AddTaskCommand getAddTaskCommandForTask(Task task, Model model) {
        AddTaskCommand command = new AddTaskCommand(task);
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
        public void updatePersonTags(ReadOnlyPerson target, Set<Tag> newTags)
                throws PersonNotFoundException, DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(ReadOnlyPerson person, Tag tag) throws PersonNotFoundException,
                DuplicatePersonException, TagNotFoundException {
            fail("This method must not be called.");
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
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            fail("This method should not be called");
        }

        @Override
        public void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
                throws DuplicateTaskException, TaskNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public void updateTaskTags(ReadOnlyTask target, Set<Tag> allTags)
                throws DuplicateTaskException, TaskNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public ObservableList<ReadOnlyTask> getFilteredTaskList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
            fail("This method should not be called");
        }

        @Override
        public void changeCommandMode(String mode) throws IllegalValueException {
            fail("This method should not be called");
        }

        @Override
        public CommandMode getCommandMode() {
            fail("This method should not be called ");
            return null;
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicateTaskException extends AddTaskCommandTest.ModelStub {
        @Override
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            throw new DuplicateTaskException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
```
###### /java/seedu/address/logic/commands/EditTaskCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTaskCommand.
 */
public class EditTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Task editedTask = new TaskBuilder().build();
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = prepareCommand(INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        ReadOnlyTask lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask = taskInList.withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withStartDate(VALID_STARTDATE_INTERNSHIP).withTags(VALID_TAG_URGENT).build();

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withStartDate(VALID_STARTDATE_INTERNSHIP).withTags(VALID_TAG_URGENT).build();
        EditTaskCommand editTaskCommand = prepareCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(lastTask, editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditTaskCommand editCommand = prepareCommand(INDEX_FIRST_PERSON, new EditTaskDescriptor());
        ReadOnlyTask editedTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstTaskOnly(model);

        ReadOnlyTask taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withDescription(VALID_DESCRIPTION_INTERNSHIP).build();
        EditTaskCommand editTaskCommand = prepareCommand(INDEX_FIRST_PERSON,
                new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP).build());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTaskUnfilteredList_failure() {
        Task firstTask = new Task(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()));
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(firstTask).build();
        EditTaskCommand editTaskCommand = prepareCommand(INDEX_SECOND_TASK, descriptor);

        assertCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_duplicateTaskFilteredList_failure() {
        showFirstTaskOnly(model);

        // edit person in filtered list into a duplicate in address book
        ReadOnlyTask taskInList = model.getAddressBook().getTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        EditTaskCommand editTaskCommand = prepareCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder(taskInList).build());

        assertCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .build();
        EditTaskCommand editTaskCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() {
        showFirstTaskOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditTaskCommand editTaskCommand = prepareCommand(outOfBoundIndex,
                new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP).build());

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditTaskCommand standardCommand = new EditTaskCommand(INDEX_FIRST_PERSON, DESC_INTERNSHIP);

        // same values -> returns true
        EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(DESC_INTERNSHIP);
        EditTaskCommand commandWithSameValues = new EditTaskCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_SECOND_TASK, DESC_INTERNSHIP)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST_TASK, DESC_PAPER)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditTaskCommand prepareCommand(Index index, EditTaskCommand.EditTaskDescriptor descriptor) {
        EditTaskCommand editTaskCommand = new EditTaskCommand(index, descriptor);
        editTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editTaskCommand;
    }
}
```
###### /java/seedu/address/logic/commands/EditTaskDescriptorTest.java
``` java
public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskCommand.EditTaskDescriptor descriptorWithSameValues =
                new EditTaskCommand.EditTaskDescriptor(DESC_INTERNSHIP);
        assertTrue(DESC_INTERNSHIP.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_INTERNSHIP.equals(DESC_INTERNSHIP));

        // null -> returns false
        assertFalse(DESC_INTERNSHIP.equals(null));

        // different types -> returns false
        assertFalse(DESC_INTERNSHIP.equals(5));

        // different values -> returns false
        assertFalse(DESC_INTERNSHIP.equals(DESC_PAPER));

        // different description -> returns false
        EditTaskCommand.EditTaskDescriptor editedInternship =
                new EditTaskDescriptorBuilder(DESC_INTERNSHIP).withDescription(UNQUOTED_DESCRIPTION_PAPER).build();
        assertFalse(DESC_INTERNSHIP.equals(editedInternship));

        // different start date -> returns false
        editedInternship = new EditTaskDescriptorBuilder(DESC_INTERNSHIP)
                .withStartDate(VALID_STARTDATE_GRAD_SCHOOL).build();
        assertFalse(DESC_INTERNSHIP.equals(editedInternship));

        // different deadline -> returns false
        editedInternship = new EditTaskDescriptorBuilder(DESC_INTERNSHIP).withDeadline(VALID_DEADLINE_GRAD_SCHOOL)
                .build();
        assertFalse(DESC_INTERNSHIP.equals(editedInternship));

        // different tags -> returns false
        editedInternship = new EditTaskDescriptorBuilder(DESC_INTERNSHIP).withTags(VALID_TAG_NOT_URGENT).build();
        assertFalse(DESC_INTERNSHIP.equals(editedInternship));
    }
}
```
###### /java/seedu/address/logic/commands/TagCommandTest.java
``` java
public class TagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Index[] indices;
    private Set<Tag> tags;

    @Test
    public void execute_unfilteredList_success() throws Exception {
        indices = new Index[]{INDEX_FIRST_PERSON, INDEX_SECOND_PERSON};
        tags = SampleDataUtil.getTagSet(VALID_TAG_FRIEND);
        TagCommand tagCommand = prepareCommand(indices, tags);

        //testing for change in person tags in first index
        ReadOnlyPerson personInUnfilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        tags = combineTags(personInUnfilteredList, VALID_TAG_FRIEND);
        Person editedPerson = new PersonBuilder(personInUnfilteredList).build();
        model.updatePersonTags(editedPerson, tags);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSONS_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);

        //testing for change in person tags in second index
        ReadOnlyPerson personInUnfilteredListTwo = model.getFilteredPersonList()
                .get(INDEX_SECOND_PERSON.getZeroBased());
        tags = combineTags(personInUnfilteredListTwo, VALID_TAG_FRIEND);
        Person editedPersonTwo = new PersonBuilder(personInUnfilteredListTwo).build();
        model.updatePersonTags(editedPersonTwo, tags);

        Model expectedModelTwo = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModelTwo.updatePerson(model.getFilteredPersonList().get(1), editedPersonTwo);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModelTwo);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        indices = new Index[]{INDEX_FIRST_PERSON};
        tags = SampleDataUtil.getTagSet(VALID_TAG_FRIEND);
        TagCommand tagCommand = prepareCommand(indices, tags);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        tags = combineTags(personInFilteredList, VALID_TAG_FRIEND);
        Person editedPerson = new PersonBuilder(personInFilteredList).build();
        model.updatePersonTags(editedPerson, tags);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSONS_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        indices = new Index[]{outOfBoundIndex};
        tags = SampleDataUtil.getTagSet(VALID_TAG_FRIEND);
        TagCommand tagCommand = prepareCommand(indices, tags);

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        indices = new Index[]{outOfBoundIndex};
        tags = SampleDataUtil.getTagSet(VALID_TAG_FRIEND);
        TagCommand tagCommand = prepareCommand(indices, tags);

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        indices = new Index[]{INDEX_FIRST_PERSON, INDEX_SECOND_PERSON};
        tags = SampleDataUtil.getTagSet(VALID_TAG_FRIEND);
        final TagCommand standardCommand = new TagCommand(indices, tags);

        //same values -> returns true
        Index[] copyIndices = new Index[]{INDEX_FIRST_PERSON, INDEX_SECOND_PERSON};
        Set<Tag> copyTags = SampleDataUtil.getTagSet(VALID_TAG_FRIEND);
        TagCommand commandWithSameValue = new TagCommand(copyIndices, copyTags);
        assertTrue(standardCommand.equals(commandWithSameValue));

        //same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        //null -> returns false
        assertFalse(standardCommand.equals(null));

        //differed types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        //different index -> returns false
        Index[] differentIndex = new Index[]{INDEX_FIRST_PERSON, INDEX_THIRD_PERSON};
        TagCommand commandWithDifferentIndex = new TagCommand(differentIndex, tags);
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        //different tag -> returns false
        Set<Tag> differentTag = SampleDataUtil.getTagSet(VALID_TAG_HUSBAND);
        TagCommand commandWithDifferentTag = new TagCommand(indices, differentTag);
        assertFalse(standardCommand.equals(commandWithDifferentTag));
    }

    /**
     * Returns an {@code TagCommand} with parameters {@code indices} and {@code tagList}
     */
    private TagCommand prepareCommand(Index[] indices, Set<Tag> tagList) {
        TagCommand tagCommand = new TagCommand(indices, tagList);
        tagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagCommand;
    }

    /**
     * Returns a combined set of tags from old tags of a {@code person} and {@code newTags}
     */
    private Set<Tag> combineTags(ReadOnlyPerson person, String... newTags) throws Exception {
        Set<Tag> allTags = new HashSet<>();
        for (String tag : newTags) {
            allTags = SampleDataUtil.getTagSet(tag);
        }
        allTags.addAll(person.getTags());
        return allTags;
    }
}
```
###### /java/seedu/address/logic/parser/AddTaskCommandParserTest.java
``` java
public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        Task expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withStartDate(VALID_STARTDATE_INTERNSHIP).withDeadline(VALID_DEADLINE_INTERNSHIP)
                .withTags(VALID_TAG_URGENT).build();

        // multiple start dates - last date accepted
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_GRAD_SCHOOL
                + STARTDATE_DESC_INTERNSHIP + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT,
                new AddTaskCommand(expectedTask));

        // multiple tags - all accepted
        Task expectedTaskWithMultipleTags = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withStartDate(VALID_STARTDATE_INTERNSHIP).withDeadline(VALID_DEADLINE_INTERNSHIP)
                .withTags(VALID_TAG_URGENT, VALID_TAG_GROUP).build();
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_INTERNSHIP
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT + TAG_DESC_GROUP,
                new AddTaskCommand(expectedTaskWithMultipleTags));

        // prefix repeated in description - correct prefix accepted
        Task expectedTaskWithPrefixInDesc = new TaskBuilder().withDescription(UNQUOTED_DESCRIPTION_PAPER)
                .withStartDate(VALID_STARTDATE_PAPER).withDeadline(VALID_DEADLINE_PAPER)
                .withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, DESCRIPTION_QUOTED_PAPER + STARTDATE_DESC_PAPER + DEADLINE_DESC_PAPER
                + TAG_DESC_URGENT, new AddTaskCommand(expectedTaskWithPrefixInDesc));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no start date
        Task expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL)
                .withDeadline(VALID_DEADLINE_GRAD_SCHOOL).withStartDate("").withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, VALID_DESCRIPTION_GRAD_SCHOOL + DEADLINE_DESC_GRAD_SCHOOL
                + TAG_DESC_URGENT, new AddTaskCommand(expectedTask));

        // no deadline
        expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL).withDeadline("")
                .withStartDate(VALID_STARTDATE_GRAD_SCHOOL).withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, VALID_DESCRIPTION_GRAD_SCHOOL + STARTDATE_DESC_GRAD_SCHOOL
                + TAG_DESC_URGENT, new AddTaskCommand(expectedTask));

        // no tags
        expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL)
                .withDeadline(VALID_DEADLINE_GRAD_SCHOOL).withStartDate(VALID_STARTDATE_GRAD_SCHOOL).withTags().build();
        assertParseSuccess(parser, VALID_DESCRIPTION_GRAD_SCHOOL + STARTDATE_DESC_GRAD_SCHOOL
                + DEADLINE_DESC_GRAD_SCHOOL, new AddTaskCommand(expectedTask));

        // no deadline, deadline prefix in description with quotes - no deadline accepted
        expectedTask = new TaskBuilder().withDescription(UNQUOTED_DESCRIPTION_PAPER)
                .withStartDate(VALID_STARTDATE_PAPER).withDeadline("").withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, DESCRIPTION_QUOTED_PAPER + STARTDATE_DESC_PAPER + TAG_DESC_URGENT,
                new AddTaskCommand(expectedTask));

        // no start date and deadline
        expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP).withStartDate("")
                .withDeadline("")
                .withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP + TAG_DESC_URGENT,
                new AddTaskCommand(expectedTask));

        // no start date and deadline, deadline prefix in description with quotes - no deadline accepted
        expectedTask = new TaskBuilder().withDescription(UNQUOTED_DESCRIPTION_PAPER).withStartDate("")
                .withDeadline("").withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, DESCRIPTION_QUOTED_PAPER + TAG_DESC_URGENT,
                new AddTaskCommand(expectedTask));

        // no optional field
        expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP).withStartDate("")
                .withDeadline("")
                .withTags().build();
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP, new AddTaskCommand(expectedTask));

        // no optional field, deadline prefix in description with quotes - no deadline accepted
        expectedTask = new TaskBuilder().withDescription(UNQUOTED_DESCRIPTION_PAPER).withStartDate("")
                .withDeadline("").withTags().build();
        assertParseSuccess(parser, DESCRIPTION_QUOTED_PAPER, new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing description
        assertParseFailure(parser, STARTDATE_DESC_PAPER + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT,
                expectedMessage);

        // all missing values
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser, INVALID_DESCRIPTION + STARTDATE_DESC_INTERNSHIP
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // invalid start date
        assertParseFailure(parser, VALID_DESCRIPTION_INTERNSHIP + INVALID_STARTDATE_DESC
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT, TaskDates.MESSAGE_DATE_CONSTRAINTS);

        // invalid start date - after deadline
        assertParseFailure(parser, VALID_DESCRIPTION_INTERNSHIP + INVALID_STARTDATE_INTERNSHIP_DESC
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT, TaskDates.MESSAGE_DATE_CONSTRAINTS);

        // invalid deadline - invalid format
        assertParseFailure(parser, VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_INTERNSHIP
                + INVALID_DEADLINE_DESC + TAG_DESC_URGENT, TaskDates.MESSAGE_DATE_CONSTRAINTS);

        // invalid deadline - multiple deadline prefixes
        assertParseFailure(parser, VALID_DESCRIPTION_INTERNSHIP + DEADLINE_DESC_INTERNSHIP
                + DEADLINE_DESC_GRAD_SCHOOL + TAG_DESC_URGENT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        // invalid tag
        assertParseFailure(parser, VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_INTERNSHIP
                + DEADLINE_DESC_INTERNSHIP + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_DESCRIPTION + INVALID_STARTDATE_DESC + DEADLINE_DESC_INTERNSHIP
                + TAG_DESC_URGENT, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
    }
}
```
###### /java/seedu/address/logic/parser/EditTaskCommandParserTest.java
``` java
public class EditTaskCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_STARTDATE_INTERNSHIP, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() throws Exception {
        // negative index
        assertParseFailure(parser, "-5" + UNQUOTED_DESCRIPTION_PAPER, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_DESCRIPTION_INTERNSHIP, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        // invalid start date
        assertParseFailure(parser, "1" + INVALID_STARTDATE_DESC, TaskDates.MESSAGE_DATE_CONSTRAINTS);
        // invalid deadline
        assertParseFailure(parser, "1" + INVALID_DEADLINE_DESC, TaskDates.MESSAGE_DATE_CONSTRAINTS);
        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // invalid start date followed by valid deadline
        assertParseFailure(parser, "1" + INVALID_STARTDATE_DESC + VALID_DEADLINE_INTERNSHIP,
                TaskDates.MESSAGE_DATE_CONSTRAINTS);

        // valid start date followed by invalid start date. The test case for invalid start date followed by valid start
        // date is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + STARTDATE_DESC_INTERNSHIP + INVALID_STARTDATE_DESC,
                TaskDates.MESSAGE_DATE_CONSTRAINTS);

        // invalid description containing deadline prefix, without quotes
        assertParseFailure(parser, "1" + UNQUOTED_DESCRIPTION_PAPER + STARTDATE_DESC_PAPER + TAG_DESC_URGENT,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION + INVALID_STARTDATE_DESC
                        + VALID_DEADLINE_INTERNSHIP + VALID_TAG_NOT_URGENT,
                Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_INTERNSHIP
                + TAG_DESC_URGENT + DEADLINE_DESC_INTERNSHIP + TAG_DESC_GROUP;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_INTERNSHIP).withStartDate(VALID_STARTDATE_INTERNSHIP)
                .withDeadline(VALID_DEADLINE_INTERNSHIP).withTags(VALID_TAG_URGENT, VALID_TAG_GROUP).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + STARTDATE_DESC_INTERNSHIP + DEADLINE_DESC_GRAD_SCHOOL;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withStartDate(VALID_STARTDATE_INTERNSHIP).withDeadline(VALID_DEADLINE_GRAD_SCHOOL).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // description
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + VALID_DESCRIPTION_GRAD_SCHOOL;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL)
                .build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start date
        userInput = targetIndex.getOneBased() + STARTDATE_DESC_GRAD_SCHOOL;
        descriptor = new EditTaskDescriptorBuilder().withStartDate(VALID_STARTDATE_GRAD_SCHOOL).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // deadline
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_GRAD_SCHOOL;
        descriptor = new EditTaskDescriptorBuilder().withDeadline(VALID_DEADLINE_GRAD_SCHOOL).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_NOT_URGENT;
        descriptor = new EditTaskDescriptorBuilder().withTags(VALID_TAG_NOT_URGENT).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + INVALID_STARTDATE_DESC + STARTDATE_DESC_INTERNSHIP;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withStartDate(VALID_STARTDATE_INTERNSHIP)
                .build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_INTERNSHIP + INVALID_STARTDATE_DESC
                + TAG_DESC_NOT_URGENT + STARTDATE_DESC_INTERNSHIP;
        descriptor = new EditTaskDescriptorBuilder().withStartDate(VALID_STARTDATE_INTERNSHIP)
                .withDeadline(VALID_DEADLINE_INTERNSHIP).withTags(VALID_TAG_NOT_URGENT).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTags().build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### /java/seedu/address/model/task/TaskDatesTest.java
``` java
public class TaskDatesTest {

    @Test
    public void isValid() {
        // invalid dates
        assertFalse(TaskDates.isDateValid("13-03-2019")); // invalid month in dashed format
        assertFalse(TaskDates.isDateValid("13.03.2019")); // invalid month in dotted format
        assertFalse(TaskDates.isDateValid("13/03/2019")); // invalid month in slashed format
        assertFalse(TaskDates.isDateValid("13.3.19")); // invalid month in contracted form
        assertFalse(TaskDates.isDateValid("02-30-19")); // invalid number of days in February
        assertFalse(TaskDates.isDateValid("3-32-19")); // invalid number of days in month

        // valid dates
        assertTrue(TaskDates.isDateValid("")); // empty string
        assertTrue(TaskDates.isDateValid(" ")); // string with only whitespace
        assertTrue(TaskDates.isDateValid("12.03.2014")); // valid date in dotted format
        assertTrue(TaskDates.isDateValid("12/3/14")); // valid date in slashed format
        assertTrue(TaskDates.isDateValid("12-3-2014")); // valid date in dashed format

    }
}
```
###### /java/seedu/address/model/UniqueTaskListTest.java
``` java
public class UniqueTaskListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTaskList.asObservableList().remove(0);
    }
}
```
###### /java/seedu/address/testutil/AddressBookBuilder.java
``` java
    /**
     * Adds a new {@code Task} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withTask(ReadOnlyTask task) {
        try {
            addressBook.addTask(task);
        } catch (DuplicateTaskException dte) {
            throw new IllegalArgumentException("task is expected to be unique");
        }
        return this;
    }

```
###### /java/seedu/address/testutil/TaskBuilder.java
``` java
/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_DESCRIPTION = "CS2103T assignment due";
    public static final String DEFAULT_STARTDATE = "Tue, Oct 24, '17";
    public static final String DEFAULT_DEADLINE = "Thu, Oct 26, '17";
    public static final Suffix DEFAULT_RECUR_INTERVAL = SUFFIX_NO_RECUR_INTERVAL;
    public static final String DEFAULT_TAG = "urgent";

    private Task task;

    public TaskBuilder() {
        try {
            Description defaultDescription = new Description(DEFAULT_DESCRIPTION);
            StartDate defaultStartDate = new StartDate(DEFAULT_STARTDATE, DEFAULT_RECUR_INTERVAL);
            Deadline defaultDeadline = new Deadline(DEFAULT_DEADLINE, DEFAULT_RECUR_INTERVAL);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAG);
            this.task = new Task(defaultDescription, defaultStartDate, defaultDeadline, defaultTags);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default task's values are invalid.");
        }
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(ReadOnlyTask taskToCopy) {
        this.task = new Task(taskToCopy);
    }

    /**
     * Sets the {@code Description} of the {@code Task} that is being built.
     */
    public TaskBuilder withDescription(String description) {
        try {
            task.setDescription(new Description(description));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("description is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code Task} that is being built.
     */
    public TaskBuilder withStartDate(String startDate) {
        this.task.setStartDate(new StartDate(startDate, DEFAULT_RECUR_INTERVAL));
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Task} that is being built.
     */
    public TaskBuilder withDeadline(String deadline) {
        this.task.setDeadline(new Deadline(deadline, DEFAULT_RECUR_INTERVAL));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withTags(String ... tags) {
        try {
            this.task.setTags(SampleDataUtil.getTagSet(tags));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public Task build() {
        return this.task;
    }
}
```
###### /java/seedu/address/testutil/TaskUtil.java
``` java
/**
 * A Utility class for Task.
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code task}.
     */
    public static String getAddTaskCommand(ReadOnlyTask task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(ReadOnlyTask task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getDescription().taskDescription + " ");
        sb.append(PREFIX_STARTDATE + task.getStartDate().date.toString() + " ");
        sb.append(PREFIX_DEADLINE_BY + task.getDeadline().date.toString() + " ");
        task.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
```
###### /java/systemtests/AddTaskCommandSystemTest.java
``` java
public class AddTaskCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {

        /* Case: change the current command mode to task manager -> success */
        Model expectedModel = getModel();
        String commandMode = ChangeModeCommand.COMMAND_WORD + " tm";
        String expectedResultMessage = String.format(MESSAGE_CHANGE_MODE_SUCCESS, "TaskManager");
        assertCommandSuccess(commandMode, expectedModel, expectedResultMessage);

        Model model = getModel();

        /* Case: add a task without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added */
        ReadOnlyTask toAdd = INTERNSHIP;
        String command = "   " + AddTaskCommand.COMMAND_WORD + "  " + VALID_DESCRIPTION_INTERNSHIP + "  "
                + STARTDATE_DESC_INTERNSHIP + " " + DEADLINE_DESC_INTERNSHIP + " " + TAG_DESC_URGENT;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Internship to the list -> Internship deleted */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Internship to the list -> Internship added again */
        command = RedoCommand.COMMAND_WORD;
        model.addTask(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate task -> rejected */
        command = "   " + AddTaskCommand.COMMAND_WORD + "  " + VALID_DESCRIPTION_INTERNSHIP + "  "
                + STARTDATE_DESC_INTERNSHIP + " " + DEADLINE_DESC_INTERNSHIP + " ";
        assertCommandFailure(command, AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: add a task with all fields same as another task in the address book except description -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL)
                .withStartDate(VALID_STARTDATE_INTERNSHIP).withDeadline(VALID_DEADLINE_INTERNSHIP)
                .withTags(VALID_TAG_URGENT).build();
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_GRAD_SCHOOL + STARTDATE_DESC_INTERNSHIP
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the address book except start date -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withStartDate(VALID_STARTDATE_GRAD_SCHOOL).withDeadline(VALID_DEADLINE_INTERNSHIP)
                .withTags(VALID_TAG_URGENT).build();
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_GRAD_SCHOOL
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the address book except deadline -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withStartDate(VALID_STARTDATE_INTERNSHIP).withDeadline(VALID_DEADLINE_GRAD_SCHOOL)
                .withTags(VALID_TAG_URGENT).build();
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_INTERNSHIP
                + DEADLINE_DESC_GRAD_SCHOOL + TAG_DESC_URGENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getTaskList().size() == 0;
        assertCommandSuccess(GRAD_SCHOOL);

        /* Case: add a task, missing task dates -> added */
        assertCommandSuccess(BUY_PRESENTS);

        /* Case: add a task, missing start date -> added */
        assertCommandSuccess(SUBMISSION);

        /* Case: add a task, missing deadline -> added */
        assertCommandSuccess(GYM);

        /* Case: missing description -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " " + STARTDATE_DESC_INTERNSHIP + DEADLINE_DESC_INTERNSHIP;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + TaskUtil.getTaskDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid description -> rejected */
        command = AddTaskCommand.COMMAND_WORD + INVALID_DESCRIPTION + STARTDATE_DESC_INTERNSHIP
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT;
        assertCommandFailure(command, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        /* Case: invalid unquoted description containing deadline prefix -> rejected for invalid deadline */
        command = AddTaskCommand.COMMAND_WORD + " " + UNQUOTED_DESCRIPTION_PAPER + STARTDATE_DESC_PAPER;
        assertCommandFailure(command, TaskDates.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid start date -> rejected */
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP + INVALID_STARTDATE_DESC
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT;
        assertCommandFailure(command, TaskDates.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid deadline -> rejected */
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_INTERNSHIP
                + INVALID_DEADLINE_DESC + TAG_DESC_URGENT;
        assertCommandFailure(command, TaskDates.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_INTERNSHIP
                + DEADLINE_DESC_INTERNSHIP + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }
```
###### /java/systemtests/EditTaskCommandSystemTest.java
``` java
public class EditTaskCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model expectedModel = getModel();
        String commandMode = ChangeModeCommand.COMMAND_WORD + " tm";
        String expectedResultMessage = String.format(MESSAGE_CHANGE_MODE_SUCCESS, "TaskManager");
        assertCommandSuccess(commandMode, expectedModel, expectedResultMessage);

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_TASK;
        String command = " " + EditTaskCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + VALID_DESCRIPTION_INTERNSHIP + "  " + STARTDATE_DESC_INTERNSHIP + " " + DEADLINE_DESC_INTERNSHIP
                + "  " + TAG_DESC_URGENT + " ";
        Task editedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withStartDate(VALID_STARTDATE_INTERNSHIP).withDeadline(VALID_DEADLINE_INTERNSHIP)
                .withTags(VALID_TAG_URGENT).build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: undo editing the last task in the list -> last task restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: redo editing the last person in the list -> last person edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        expectedModel.updateTask(
                getModel().getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased()), editedTask);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: edit a task with new values same as existing values -> edited */
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + VALID_DESCRIPTION_INTERNSHIP
                + STARTDATE_DESC_INTERNSHIP + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT;
        assertCommandSuccess(command, index, INTERNSHIP);

        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_TASK;
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + TAG_DESC_NOT_URGENT;
        ReadOnlyTask taskToEdit = getModel().getFilteredTaskList().get(index.getZeroBased());
        editedTask = new TaskBuilder(taskToEdit).withTags(VALID_TAG_NOT_URGENT).build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_TASK;
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        editedTask = new TaskBuilder(taskToEdit).withTags().build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: clear start dates -> cleared */
        index = INDEX_FIRST_TASK;
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_STARTDATE.getPrefix()
                + TAG_DESC_URGENT;
        editedTask = new TaskBuilder(taskToEdit).withStartDate("").build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: clear deadlines -> cleared */
        index = INDEX_FIRST_TASK;
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_DEADLINE_BY.getPrefix()
                + STARTDATE_DESC_INTERNSHIP;
        editedTask = new TaskBuilder(taskToEdit).withDeadline("").build();
        assertCommandSuccess(command, index, editedTask);

        // TODO: 1/11/17 Look into how changing of task card order affect this code, especially null date @Raisa
        /* Case: deadline prefix inside quoted description -> only description edited */
        /*
        index = INDEX_FIRST_TASK;
        ReadOnlyTask taskToEdit2 = new TaskBuilder(QUIZ).build();
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + " " + DESCRIPTION_QUOTED_PAPER
                + DEADLINE_DESC_INTERNSHIP;
        editedTask = new TaskBuilder(taskToEdit2).withDescription(UNQUOTED_DESCRIPTION_PAPER).build();
        assertCommandSuccess(command, index, editedTask);
        */

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered task list, edit index within bounds of address book and task list -> edited */
        showTasksWithDescription(KEYWORD_MATCHING_FINISH);
        index = INDEX_FIRST_TASK;
        assertTrue(index.getZeroBased() < getModel().getFilteredTaskList().size());
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + " " + VALID_DESCRIPTION_INTERNSHIP;
        taskToEdit = getModel().getFilteredTaskList().get(index.getZeroBased());
        editedTask = new TaskBuilder(taskToEdit).withDescription(VALID_DESCRIPTION_INTERNSHIP).build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: filtered task list, edit index within bounds of task manager but out of bounds of task list
         * -> rejected
         */
        showTasksWithDescription(KEYWORD_MATCHING_FINISH);
        int invalidIndex = getModel().getAddressBook().getTaskList().size();
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + invalidIndex + VALID_DESCRIPTION_INTERNSHIP,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a person card is selected -------------------------- */

        /* Case: selects first card in the task list, edit a task -> edited, card selection remains unchanged
         */
        showAllTasks();
        index = INDEX_FIRST_TASK;
        selectTask(index);
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + VALID_DESCRIPTION_INTERNSHIP
                + STARTDATE_DESC_INTERNSHIP + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT;
        assertCommandSuccess(command, index, INTERNSHIP, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " 0" + VALID_DESCRIPTION_INTERNSHIP,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " -1" + VALID_DESCRIPTION_INTERNSHIP,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredTaskList().size() + 1;
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + invalidIndex + VALID_DESCRIPTION_INTERNSHIP,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased(),
                EditTaskCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid description -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                        + INVALID_DESCRIPTION, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        /* Case: invalid start date -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                + INVALID_STARTDATE_DESC, TaskDates.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid deadline -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                        + INVALID_DEADLINE_DESC, TaskDates.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                        + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a task with new values same as another task's values -> rejected */
        executeCommand(TaskUtil.getAddTaskCommand(GRAD_SCHOOL));
        assertTrue(getModel().getAddressBook().getTaskList().contains(GRAD_SCHOOL));
        index = INDEX_FIRST_TASK;
        assertFalse(getModel().getFilteredTaskList().get(index.getZeroBased()).equals(GRAD_SCHOOL));
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + VALID_DESCRIPTION_GRAD_SCHOOL
                + STARTDATE_DESC_GRAD_SCHOOL + DEADLINE_DESC_GRAD_SCHOOL + TAG_DESC_URGENT;
        assertCommandFailure(command, EditTaskCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: edit a task with new values same as another task's values but with different tags -> rejected */
        index = INDEX_SECOND_TASK;
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + VALID_DESCRIPTION_INTERNSHIP
                + STARTDATE_DESC_INTERNSHIP + DEADLINE_DESC_INTERNSHIP + TAG_DESC_NOT_URGENT;
        assertCommandFailure(command, EditTaskCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: description contains deadline prefix but without quotes -> rejected */
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + UNQUOTED_DESCRIPTION_PAPER;
        assertCommandFailure(command, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                EditTaskCommand.MESSAGE_USAGE));
    }

```
