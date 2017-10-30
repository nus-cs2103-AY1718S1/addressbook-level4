# kenpaxtonlim
###### \java\seedu\address\logic\commands\AddRemoveTagsCommandTest.java
``` java
public class AddRemoveTagsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addTags_success() throws Exception {
        Person editedPerson = new PersonBuilder(
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND, "friends").build();

        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);

        AddRemoveTagsCommand addTagsCommand = prepareCommandAdd(INDEX_FIRST_PERSON, ParserUtil.parseTags(tagsList));

        String expectedMessage = String.format(AddRemoveTagsCommand.MESSAGE_ADD_TAGS_SUCCESS,
                editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeTags_success() throws Exception {
        Person editedPerson = new PersonBuilder(
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())).withTags().build();

        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add("friends");

        AddRemoveTagsCommand removeTagsCommand = prepareCommandRemove(INDEX_FIRST_PERSON,
                ParserUtil.parseTags(tagsList));

        String expectedMessage = String.format(AddRemoveTagsCommand.MESSAGE_REMOVE_TAGS_SUCCESS,
                editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(removeTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        AddRemoveTagsCommand addRemoveTagsCommand = prepareCommandAdd(outOfBoundIndex, ParserUtil.parseTags(tagsList));

        assertCommandFailure(addRemoveTagsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        AddRemoveTagsCommand addRemoveTagsCommand = prepareCommandAdd(outOfBoundIndex, ParserUtil.parseTags(tagsList));

        assertCommandFailure(addRemoveTagsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        Set<Tag> tags = ParserUtil.parseTags(tagsList);
        final AddRemoveTagsCommand standardCommand = new AddRemoveTagsCommand(true, INDEX_FIRST_PERSON, tags);

        // Returns true with itself
        assertTrue(standardCommand.equals(standardCommand));

        // Returns true with same values
        AddRemoveTagsCommand commandWithSameValues = new AddRemoveTagsCommand(true, INDEX_FIRST_PERSON, tags);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Return false with different type
        AddRemoveTagsCommand commandWithDifferentType = new AddRemoveTagsCommand(false, INDEX_FIRST_PERSON, tags);
        assertFalse(standardCommand.equals(commandWithDifferentType));

        // Returns false with null
        assertFalse(standardCommand.equals(null));

        // Returns false with different command types
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Returns false with different indexes
        AddRemoveTagsCommand commandWithDifferentIndex = new AddRemoveTagsCommand(true, INDEX_SECOND_PERSON,
                ParserUtil.parseTags(tagsList));
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // Returns false with different tags
        ArrayList<String> differentTagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        AddRemoveTagsCommand commandWithDifferentTags = new AddRemoveTagsCommand(true, INDEX_FIRST_PERSON,
                ParserUtil.parseTags(differentTagsList));
        assertFalse(standardCommand.equals(commandWithDifferentTags));
    }

    /**
     * Returns an add {@code AddRemoveTagsCommand} with parameters {@code index} and {@code tags}.
     */
    private AddRemoveTagsCommand prepareCommandAdd(Index index, Set<Tag> tags) {
        AddRemoveTagsCommand addRemoveTagsCommand = new AddRemoveTagsCommand(true, index, tags);
        addRemoveTagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addRemoveTagsCommand;
    }

    /**
     * Returns a remove {@code AddRemoveTagsCommand} with parameters {@code index} and {@code tags}.
     */
    private AddRemoveTagsCommand prepareCommandRemove(Index index, Set<Tag> tags) {
        AddRemoveTagsCommand addRemoveTagsCommand = new AddRemoveTagsCommand(false, index, tags);
        addRemoveTagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addRemoveTagsCommand;
    }
}
```
###### \java\seedu\address\logic\commands\SocialMediaCommandTest.java
``` java
public class SocialMediaCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexValidType_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON, SocialMediaCommand.TYPE_FACEBOOK);
        assertExecutionSuccess(INDEX_THIRD_PERSON, SocialMediaCommand.TYPE_TWITTER);
        assertExecutionSuccess(lastPersonIndex, SocialMediaCommand.TYPE_INSTAGRAM);
    }

    @Test
    public void execute_invalidIndexValidType_success() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, SocialMediaCommand.TYPE_FACEBOOK,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexInvalidType_success() {
        assertExecutionFailure(INDEX_FIRST_PERSON, "abc", SocialMediaCommand.MESSAGE_INVALID_TYPE);
    }

    @Test
    public void execute_invalidIndexinvalidType_success() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, "abc",
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SocialMediaCommand command = new SocialMediaCommand(INDEX_FIRST_PERSON, SocialMediaCommand.TYPE_FACEBOOK);

        // same object -> returns true
        assertTrue(command.equals(command));

        // same values -> returns true
        SocialMediaCommand firstCommandCopy = new SocialMediaCommand(INDEX_FIRST_PERSON,
                SocialMediaCommand.TYPE_FACEBOOK);
        assertTrue(command.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(command.equals(1));

        // null -> returns false
        assertFalse(command.equals(null));

        // different person, same social media type -> returns false
        SocialMediaCommand differentPersonCommand = new SocialMediaCommand(INDEX_SECOND_PERSON,
                SocialMediaCommand.TYPE_FACEBOOK);
        assertFalse(command.equals(differentPersonCommand));

        // same person, different social media typ -> return false
        SocialMediaCommand differentTypeCommand = new SocialMediaCommand(INDEX_FIRST_PERSON,
                SocialMediaCommand.TYPE_TWITTER);
        assertFalse(command.equals(differentTypeCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index, String type) {
        SocialMediaCommand command = prepareCommand(index, type);

        try {
            CommandResult commandResult = command.execute();
            assertEquals(SocialMediaCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        String url = "";
        switch (type) {
        case SocialMediaCommand.TYPE_FACEBOOK:
            url = SocialMediaCommand.URL_FACEBOOK
                    + model.getFilteredPersonList().get(index.getZeroBased()).getSocialMedia().facebook;
            break;
        case SocialMediaCommand.TYPE_TWITTER:
            url = SocialMediaCommand.URL_TWITTER
                    + model.getFilteredPersonList().get(index.getZeroBased()).getSocialMedia().twitter;
            break;
        case SocialMediaCommand.TYPE_INSTAGRAM:
            url = SocialMediaCommand.URL_INSTAGRAM
                    + model.getFilteredPersonList().get(index.getZeroBased()).getSocialMedia().instagram;
            break;
        default:
            throw new AssertionError();
        }

        ChangeBrowserPanelUrlEvent lastEvent =
                (ChangeBrowserPanelUrlEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(url, lastEvent.url);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String type, String expectedMessage) {
        SocialMediaCommand command = prepareCommand(index, type);

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SocialMediaCommand} with parameters {@code index} and {@code type}.
     */
    private SocialMediaCommand prepareCommand(Index index, String type) {
        SocialMediaCommand command = new SocialMediaCommand(index, type);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\parser\AddRemoveTagsCommandParserTest.java
``` java
public class AddRemoveTagsCommandParserTest {
    private AddRemoveTagsCommandParser parser = new AddRemoveTagsCommandParser();

    @Test
    public void parse_argsSpecified_success() throws Exception {
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        Set<Tag> tags = ParserUtil.parseTags(tagsList);

        String userInputAdd = " add " + INDEX_FIRST_PERSON.getOneBased() + " " + VALID_TAG_HUSBAND
                + " " + VALID_TAG_FRIEND;
        AddRemoveTagsCommand expectedCommandAdd = new AddRemoveTagsCommand(true, INDEX_FIRST_PERSON, tags);

        assertParseSuccess(parser, userInputAdd, expectedCommandAdd);

        String userInputRemove = " remove " + INDEX_FIRST_PERSON.getOneBased() + " " + VALID_TAG_HUSBAND
                + " " + VALID_TAG_FRIEND;
        AddRemoveTagsCommand expectedCommandRemove = new AddRemoveTagsCommand(false, INDEX_FIRST_PERSON, tags);

        assertParseSuccess(parser, userInputRemove, expectedCommandRemove);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemoveTagsCommand.MESSAGE_USAGE);

        assertParseFailure(parser, AddRemoveTagsCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_add_alias() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommandAlias(person));
        assertEquals(new AddCommand(person), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_clear_alias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_delete_alias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_edit_alias() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_addtags() throws Exception {
        Person person = new PersonBuilder().build();
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        Set<Tag> tags = ParserUtil.parseTags(tagsList);
        AddRemoveTagsCommand command = (AddRemoveTagsCommand) parser.parseCommand(
                AddRemoveTagsCommand.COMMAND_WORD + " add " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + VALID_TAG_HUSBAND + " " + VALID_TAG_FRIEND);
        assertEquals(new AddRemoveTagsCommand(true, INDEX_FIRST_PERSON, tags), command);
    }

    @Test
    public void parseCommand_addtags_alias() throws Exception {
        Person person = new PersonBuilder().build();
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        Set<Tag> tags = ParserUtil.parseTags(tagsList);
        AddRemoveTagsCommand command = (AddRemoveTagsCommand) parser.parseCommand(
                AddRemoveTagsCommand.COMMAND_ALIAS + " add " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + VALID_TAG_HUSBAND + " " + VALID_TAG_FRIEND);
        assertEquals(new AddRemoveTagsCommand(true, INDEX_FIRST_PERSON, tags), command);
    }

    @Test
    public void parseCommand_removetags() throws Exception {
        Person person = new PersonBuilder().build();
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        Set<Tag> tags = ParserUtil.parseTags(tagsList);
        AddRemoveTagsCommand command = (AddRemoveTagsCommand) parser.parseCommand(
                AddRemoveTagsCommand.COMMAND_WORD + " remove " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + VALID_TAG_HUSBAND + " " + VALID_TAG_FRIEND);
        assertEquals(new AddRemoveTagsCommand(false, INDEX_FIRST_PERSON, tags), command);
    }

    @Test
    public void parseCommand_removetags_alias() throws Exception {
        Person person = new PersonBuilder().build();
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        Set<Tag> tags = ParserUtil.parseTags(tagsList);
        AddRemoveTagsCommand command = (AddRemoveTagsCommand) parser.parseCommand(
                AddRemoveTagsCommand.COMMAND_ALIAS + " remove " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + VALID_TAG_HUSBAND + " " + VALID_TAG_FRIEND);
        assertEquals(new AddRemoveTagsCommand(false, INDEX_FIRST_PERSON, tags), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_exit_alias() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS + " 3") instanceof ExitCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_find_alias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_help_alias() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS + " 3") instanceof HelpCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_history_alias() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_list_alias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_select_alias() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_socialmedia() throws Exception {
        SocialMediaCommand command = (SocialMediaCommand) parser.parseCommand(
                SocialMediaCommand.COMMAND_WORD + " "
                        + SocialMediaCommand.TYPE_FACEBOOK + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SocialMediaCommand(INDEX_FIRST_PERSON, SocialMediaCommand.TYPE_FACEBOOK), command);
    }

    @Test
    public void parseCommand_socialmedia_alias() throws Exception {
        SocialMediaCommand command = (SocialMediaCommand) parser.parseCommand(
                SocialMediaCommand.COMMAND_ALIAS + " "
                        + SocialMediaCommand.TYPE_FACEBOOK + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SocialMediaCommand(INDEX_FIRST_PERSON, SocialMediaCommand.TYPE_FACEBOOK), command);
    }
```
###### \java\seedu\address\logic\parser\SocialMediaCommandParserTest.java
``` java
public class SocialMediaCommandParserTest {
    private SocialMediaCommandParser parser = new SocialMediaCommandParser();

    @Test
    public void parse_argsSpecified_success() throws Exception {
        String userInputFacebook = " " + SocialMediaCommand.TYPE_FACEBOOK + " " + INDEX_FIRST_PERSON.getOneBased();
        SocialMediaCommand expectedCommandFacebook = new SocialMediaCommand(INDEX_FIRST_PERSON,
                SocialMediaCommand.TYPE_FACEBOOK);

        assertParseSuccess(parser, userInputFacebook, expectedCommandFacebook);

        String userInputTwitter = " " + SocialMediaCommand.TYPE_TWITTER + " " + INDEX_FIRST_PERSON.getOneBased();
        SocialMediaCommand expectedCommandTwitter = new SocialMediaCommand(INDEX_FIRST_PERSON,
                SocialMediaCommand.TYPE_TWITTER);

        assertParseSuccess(parser, userInputTwitter, expectedCommandTwitter);

        String userInputInstagram = " " + SocialMediaCommand.TYPE_INSTAGRAM + " " + INDEX_FIRST_PERSON.getOneBased();
        SocialMediaCommand expectedCommandInstagram = new SocialMediaCommand(INDEX_FIRST_PERSON,
                SocialMediaCommand.TYPE_INSTAGRAM);

        assertParseSuccess(parser, userInputInstagram, expectedCommandInstagram);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE);

        assertParseFailure(parser, SocialMediaCommand.COMMAND_WORD, expectedMessage);
    }

    @Test
    public void parse_noIndexSpecified_failure() throws Exception {
        String userInput = " " + SocialMediaCommand.TYPE_INSTAGRAM;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_noTypeSpecified_failure() throws Exception {
        String userInput = " " + INDEX_FIRST_PERSON.getOneBased();
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code SocialMedia} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withSocialMedia(String facebook, String twitter, String instagram) {
        try {
            descriptor.setSocialMedia(ParserUtil.parseSocialMedia(
                    Optional.of(facebook), Optional.of(twitter), Optional.of(instagram)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("social media usernames is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code SocialMedia} of the {@code Person} that we are building.
     */
    public PersonBuilder withSocialMedia(String facebook, String twitter, String instagram) {
        try {
            this.person.setSocialMedia(new SocialMedia(facebook, twitter, instagram));
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("usernames is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonUtil.java
``` java
    /**
     * Returns an add command string using alias for adding the {@code person}.
     */
    public static String getAddCommandAlias(ReadOnlyPerson person) {
        return AddCommand.COMMAND_ALIAS + " " + getPersonDetails(person);
    }
```
###### \java\systemtests\SocialMediaCommandSystemTest.java
``` java
public class SocialMediaCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void socialmedia() {
        /* Case: show facebook of first person in list -> shown */
        String command = SocialMediaCommand.COMMAND_WORD + " " + SocialMediaCommand.TYPE_FACEBOOK
                + " " + INDEX_FIRST_PERSON.getOneBased();
        assertCommandSuccess(command, INDEX_FIRST_PERSON, SocialMediaCommand.TYPE_FACEBOOK);

        /* Case: show twitter of first person in list -> shown */
        command = SocialMediaCommand.COMMAND_WORD + " " + SocialMediaCommand.TYPE_TWITTER
                + " " + INDEX_FIRST_PERSON.getOneBased();
        assertCommandSuccess(command, INDEX_FIRST_PERSON, SocialMediaCommand.TYPE_TWITTER);

        /* Case: show instagram of first person in list -> shown */
        command = SocialMediaCommand.COMMAND_WORD + " " + SocialMediaCommand.TYPE_INSTAGRAM
                + " " + INDEX_FIRST_PERSON.getOneBased();
        assertCommandSuccess(command, INDEX_FIRST_PERSON, SocialMediaCommand.TYPE_INSTAGRAM);

        /* Case: undo previous showing of social media -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo showing instagram of first person in list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredPersonList().size() + 1;
        command = SocialMediaCommand.COMMAND_WORD + " " + SocialMediaCommand.TYPE_INSTAGRAM + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid social media type -> rejected */
        command = SocialMediaCommand.COMMAND_WORD + " " + "abc" + " " + invalidIndex;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE));
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing select command with the {@code expectedSelectedCardIndex}
     * of the selected person, and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar remain unchanged. The resulting
     * browser url and selected card will be verified if the current selected card and the card at
     * {@code expectedSelectedCardIndex} are different.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index index, String type) {
        Model expectedModel = getModel();
        String expectedResultMessage = SocialMediaCommand.MESSAGE_SUCCESS;

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        String url = "";
        switch (type) {
        case SocialMediaCommand.TYPE_FACEBOOK:
            url = SocialMediaCommand.URL_FACEBOOK
                    + expectedModel.getFilteredPersonList().get(index.getZeroBased()).getSocialMedia().facebook;
            break;
        case SocialMediaCommand.TYPE_TWITTER:
            url = SocialMediaCommand.URL_TWITTER
                    + expectedModel.getFilteredPersonList().get(index.getZeroBased()).getSocialMedia().twitter;
            break;
        case SocialMediaCommand.TYPE_INSTAGRAM:
            url = SocialMediaCommand.URL_INSTAGRAM
                    + expectedModel.getFilteredPersonList().get(index.getZeroBased()).getSocialMedia().instagram;
            break;
        default:
            throw new AssertionError();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
