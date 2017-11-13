# karrui
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
    @Test
    public void execute_person_homepageGeneratedSuccessful() {
        Person validPerson = new PersonBuilder().build();
        String defaultHomepage  = GOOGLE_SEARCH_URL_PREFIX + validPerson.getName()
                .fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX;
        String customHomepage = "http://www.google.com";
        Person validPersonWithHomepage = new PersonBuilder().withHomepage(customHomepage).build();

        assertEquals(defaultHomepage, validPerson.getHomepage().value);
        assertEquals(customHomepage, validPersonWithHomepage.getHomepage().value);
    }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void sortContactList(boolean isDescendingSort) {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    // publicly hosted test avatar image by imgur
    public static final String VALID_AVATAR_IMAGE_URL_ONE = "https://i.imgur.com/o4VkosH.png";
    public static final String VALID_AVATAR_IMAGE_URL_TWO = "https://i.imgur.com/xPHOeWL.png";

    public static final String VALID_AVATAR_FILEPATH = "src/test/resources/avatarTest/testAvatar.png";

    public static final String INVALID_AVATAR_URL_MISSING_FILETYPE = "https://i.imgur.com/xPHOeWL"; // missing filetype
    public static final String INVALID_AVATAR_URL_MISSING_HTTP = "i.imgur.com/xPHOeWL.png"; // missing http header
    public static final String INVALID_AVATAR_URL_LARGE_IMAGE = "https://i.imgur.com/EqZj35k.jpg"; // large image
```
###### \java\seedu\address\logic\commands\EditPersonDescriptorTest.java
``` java
        // different homepage -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withHomepage(VALID_HOMEPAGE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
```
###### \java\seedu\address\logic\commands\RecentCommandTest.java
``` java
public class RecentCommandTest {
    private RecentCommand recentCommand;
    private FindHistory findHistory;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Before
    public void setUp() {
        findHistory = new FindHistory();
        recentCommand = new RecentCommand();
        recentCommand.setData(model, new CommandHistory(), findHistory, new UndoRedoStack());
    }

    @Test
    public void execute() {
        String expectedMessage = RecentCommand.MESSAGE_SUCCESS;
        assertCommandResult(recentCommand, RecentCommand.MESSAGE_NO_RECENT);    // nothing in findHistory yet
        findHistory.add(ALICE);
        assertCommandSuccess(Collections.singletonList(ALICE)); // backing list should be equals to argument
        assertCommandResult(recentCommand, expectedMessage); // success message
    }

    /**
     * Asserts that the result message from the execution of {@code recentCommand} equals to {@code expectedMessage}
     */
    private void assertCommandResult(RecentCommand recentCommand, String expectedMessage) {
        assertEquals(expectedMessage, recentCommand.execute().feedbackToUser);
    }

    /**
     * Asserts that
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        recentCommand.execute();
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\SetAvatarCommandTest.java
``` java
public class SetAvatarCommandTest {
    private Model model;
    private ArrayList<String> filesCreated = new ArrayList<>();

    @Before
    public void setup() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @After
    public void cleanup() {
        for (String path : filesCreated) {
            ProcessImageUtil.removeImageFromStorage(path);
        }
    }

    @Test
    public void execute_removeAvatar_success() throws Exception {
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAvatar(VALID_AVATAR_IMAGE_URL_ONE).build();
        model.updatePerson(firstPerson, editedPerson);
        SetAvatarCommand setAvatarCommand = prepareCommand(INDEX_FIRST_PERSON, "");

        String expectedMessage = String.format(SetAvatarCommand.MESSAGE_REMOVE_AVATAR_SUCCESS, editedPerson);
        assertEquals(setAvatarCommand.execute().feedbackToUser, expectedMessage);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(setAvatarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeAvatarWithHomepage_success() throws Exception {
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAvatar(VALID_AVATAR_IMAGE_URL_ONE).withBooleanHomepageManuallySet(true).build();
        model.updatePerson(firstPerson, editedPerson);
        SetAvatarCommand setAvatarCommand = prepareCommand(INDEX_FIRST_PERSON, "");

        String expectedMessage = String.format(SetAvatarCommand.MESSAGE_REMOVE_AVATAR_SUCCESS, editedPerson);
        assertEquals(setAvatarCommand.execute().feedbackToUser, expectedMessage);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(setAvatarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_setAvatar_success() throws Exception {
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAvatar(VALID_AVATAR_IMAGE_URL_ONE).build();
        model.updatePerson(firstPerson, editedPerson);
        SetAvatarCommand setAvatarCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_AVATAR_IMAGE_URL_TWO);

        String expectedMessage = String.format(SetAvatarCommand.MESSAGE_SET_AVATAR_SUCCESS, editedPerson);
        assertEquals(setAvatarCommand.execute().feedbackToUser, expectedMessage);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(setAvatarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size()  +  1);
        SetAvatarCommand setAvatarCommand = prepareCommand(outOfBoundIndex, VALID_AVATAR_IMAGE_URL_ONE);

        assertCommandFailure(setAvatarCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        SetAvatarCommand setAvatarCommand = prepareCommand(outOfBoundIndex, VALID_AVATAR_IMAGE_URL_ONE);

        assertCommandFailure(setAvatarCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() throws IllegalValueException {
        final SetAvatarCommand standardCommand = new SetAvatarCommand(
                INDEX_FIRST_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_ONE));
        filesCreated.add(standardCommand.getAvatar().path);

        // same values -> returns true (checks if initialURL is equal)
        SetAvatarCommand commandWithSameValues = new SetAvatarCommand(
                INDEX_FIRST_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_ONE));
        filesCreated.add(commandWithSameValues.getAvatar().path);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        SetAvatarCommand commandWithDifferentIndex = new SetAvatarCommand(
                INDEX_SECOND_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_ONE));
        filesCreated.add(commandWithDifferentIndex.getAvatar().path);
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // different descriptor  -> returns false
        SetAvatarCommand commandWithDifferentDescriptor = new SetAvatarCommand(
                INDEX_FIRST_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_TWO));
        filesCreated.add(commandWithDifferentDescriptor.getAvatar().path);
        assertFalse(standardCommand.equals(commandWithDifferentDescriptor));
    }

    /**
     +    * Returns a {@code SetAvatarCommand}.
     */
    private SetAvatarCommand prepareCommand(Index index, String path) throws IllegalValueException {
        SetAvatarCommand setAvatarCommand = new SetAvatarCommand(index, new Avatar(path));
        setAvatarCommand.setData(model, new CommandHistory(), new FindHistory(), new UndoRedoStack());
        filesCreated.add(setAvatarCommand.getAvatar().path);
        return setAvatarCommand;
    }
}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    public static final boolean DESCENDING = true;
    public static final boolean ASCENDING = false;

    private Model model;

    @Test
    public void execute_decendingSort_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SortCommand sortCommand = prepareCommand(DESCENDING);
        Model expectedModel = new ModelManager(getTypicalDescendingOrderAddressBook(), new UserPrefs());

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_ascendingSort_success() {
        model = new ModelManager(getTypicalDescendingOrderAddressBook(), new UserPrefs());
        SortCommand sortCommand = prepareCommand(ASCENDING);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    private SortCommand prepareCommand(boolean isDescendingSort) {
        SortCommand sortCommand = new SortCommand(isDescendingSort);
        sortCommand.setData(model, new CommandHistory(), new FindHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
```
###### \java\seedu\address\logic\FindHistoryTest.java
``` java
public class FindHistoryTest {
    private final Person alice = new PersonBuilder().build();
    private final Person bob = new PersonBuilder().withName("Bob").build();
    private final Person charlie = new PersonBuilder().withName("Charlie").build();
    private FindHistory findHistory;

    @Before
    public void setUp() {
        findHistory = new FindHistory();
    }

    @Test
    public void add() {
        findHistory.add(alice);
        assertEquals(Collections.singletonList(alice), findHistory.getHistory());
    }

    @Test
    public void set() {
        findHistory.add(alice);
        findHistory.set(alice, bob);
        assertEquals(Collections.singletonList(bob), findHistory.getHistory());
    }

    @Test
    public void deletePerson() {
        findHistory.add(alice);
        findHistory.deletePerson(alice);
        assertEquals(Collections.emptyList(), findHistory.getHistory());
    }

    @Test
    public void resetData() {
        LinkedList<ReadOnlyPerson> newData = new LinkedList<>();
        newData.add(alice);
        newData.add(bob);

        findHistory.add(charlie);
        assertEquals(Collections.singletonList(charlie), findHistory.getHistory());
        findHistory.resetData(newData);
        assertEquals(Arrays.asList(alice, bob), findHistory.getHistory());
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // all optional fields missing
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withAddress(
                EMPTY_STRING).withEmail(EMPTY_STRING).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY,
                new AddCommand(expectedPerson));

        // address missing
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withAddress(
                EMPTY_STRING).withEmail(VALID_EMAIL_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY, new AddCommand(expectedPerson));

        // email missing
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withAddress(
                VALID_ADDRESS_AMY).withEmail(EMPTY_STRING).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid homepage
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + INVALID_HOMEPAGE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                 Homepage.MESSAGE_HOMEPAGE_CONSTRAINTS);

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_recent() throws Exception {
        assertTrue(parser.parseCommand(RecentCommand.COMMAND_WORD) instanceof RecentCommand);
        assertTrue(parser.parseCommand(RecentCommand.COMMAND_ALIAS) instanceof RecentCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " a") instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " d") instanceof SortCommand);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_setAvatar() throws Exception {
        //setup
        ArrayList<String> filesCreated = new ArrayList<>();

        final Avatar avatar = new Avatar(VALID_AVATAR_IMAGE_URL_ONE);
        filesCreated.add(avatar.path);

        SetAvatarCommand command = (SetAvatarCommand) parser.parseCommand(SetAvatarCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_AVATAR + " " + avatar.initialUrl);
        filesCreated.add(command.getAvatar().path);

        SetAvatarCommand aliasCommand = (SetAvatarCommand) parser.parseCommand(SetAvatarCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_AVATAR + " " + avatar.initialUrl);
        filesCreated.add(aliasCommand.getAvatar().path);

        assertEquals(new SetAvatarCommand(INDEX_FIRST_PERSON, avatar), command);
        assertEquals(new SetAvatarCommand(INDEX_FIRST_PERSON, avatar), aliasCommand);

        // cleanup
        for (String path : filesCreated) {
            ProcessImageUtil.removeImageFromStorage(path);
        }
    }

```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        // homepage
        userInput = targetIndex.getOneBased() + HOMEPAGE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withHomepage(VALID_HOMEPAGE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
    @Test
    public void parse_resetHomepage_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + HOMEPAGE_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withHomepage("").build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
        Homepage expectedHomepage = new Homepage(VALID_HOMEPAGE);
        ArrayList<String> homepageList = ParserUtil.parseAllDetail(Arrays.asList(
                VALID_HOMEPAGE), FindCommand.COMMAND_WORD_HOMEPAGE);
        assertEquals(expectedHomepage.value, homepageList.toString().replaceAll(
                "['\\[\\]']", ""));

```
###### \java\seedu\address\logic\parser\SetAvatarCommandParserTest.java
``` java
public class SetAvatarCommandParserTest {
    private SetAvatarCommandParser parser = new SetAvatarCommandParser();
    private ArrayList<String> filesCreated = new ArrayList<>();

    @After
    public void cleanup() {
        for (String path : filesCreated) {
            ProcessImageUtil.removeImageFromStorage(path);
        }
    }

    @Test
    public void parse_noParameters_success() throws Exception {
        final Avatar avatarNoParameters = new Avatar("");

        Index targetIndex = INDEX_FIRST_PERSON;
        String userInputEmptyParameter = targetIndex.getOneBased() + " " + PREFIX_AVATAR.toString()
                + " " + avatarNoParameters.path;

        SetAvatarCommand expectedCommandNoParameters = new SetAvatarCommand(INDEX_FIRST_PERSON, avatarNoParameters);
        assertParseSuccess(parser, userInputEmptyParameter, expectedCommandNoParameters);
    }

    @Test
    public void parse_withParameters_success() throws Exception {
        final Avatar avatarWithParameters = new Avatar(VALID_AVATAR_IMAGE_URL_ONE);
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInputWithParameters = targetIndex.getOneBased() + " " + PREFIX_AVATAR.toString()
                + " " + avatarWithParameters.initialUrl;

        SetAvatarCommand expectedCommandWithParameters = new SetAvatarCommand(INDEX_FIRST_PERSON, avatarWithParameters);
        filesCreated.add(expectedCommandWithParameters.getAvatar().path);
        SetAvatarCommand command = parser.parse(userInputWithParameters);
        filesCreated.add(command.getAvatar().path);
        assertEquals(expectedCommandWithParameters, command);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetAvatarCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, SetAvatarCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertParseSuccess(parser, "a", new SortCommand(ASCENDING));
        assertParseSuccess(parser, "d", new SortCommand(DESCENDING));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "z", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\person\AvatarTest.java
``` java
public class AvatarTest {
    @Test
    public void isValidAvatar() {
        // invalid avatars
        assertFalse(Avatar.isValidPath("abcde")); // not an image path
        assertFalse(Avatar.isValidPath(" ")); // spaces only
        assertFalse(Avatar.isValidPath(INVALID_AVATAR_URL_MISSING_HTTP)); //no "http://" header
        assertFalse(Avatar.isValidPath(INVALID_AVATAR_URL_MISSING_FILETYPE)); // no filetype trailer
        assertFalse(Avatar.isValidPath("https://hopefullydoesnotexist.com/doesnotexist.png")); // image does not exist
        assertFalse(Avatar.isValidPath(INVALID_AVATAR_URL_LARGE_IMAGE)); // image is too large

        // valid avatars
        assertTrue(Avatar.isValidPath(VALID_AVATAR_IMAGE_URL_ONE)); // valid URL
        assertTrue(Avatar.isValidPath(VALID_AVATAR_FILEPATH));  // valid filepath
        assertTrue(Avatar.isValidPath("")); //nothing, default avatar
    }
}
```
###### \java\seedu\address\model\person\HomepageTest.java
``` java
public class HomepageTest {

    @Test
    public void isValidHomepage() {
        // invalid homepages
        assertFalse(Homepage.isValidHomepage("")); // empty string
        assertFalse(Homepage.isValidHomepage(" ")); // spaces only
        assertFalse(Homepage.isValidHomepage("www.google.com")); // no "http://" prefix

        // valid homepages
        assertTrue(Homepage.isValidHomepage("http://www.google.com"));
        assertTrue(Homepage.isValidHomepage("http://w")); // one character
    }
}
```
###### \java\seedu\address\model\person\PersonContainsRecentPredicateTest.java
``` java
public class PersonContainsRecentPredicateTest {
    private final Person alice = new PersonBuilder().build();
    private final Person bob = new PersonBuilder().withName("Bob").build();

    @Test
    public void equals() {
        List<ReadOnlyPerson> firstPredicatePersonList = Collections.singletonList(alice);
        List<ReadOnlyPerson> secondPredicatePersonList = Arrays.asList(alice, bob);

        PersonContainsRecentPredicate firstPredicate = new PersonContainsRecentPredicate(firstPredicatePersonList);
        PersonContainsRecentPredicate secondPredicate = new PersonContainsRecentPredicate(secondPredicatePersonList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsRecentPredicate firstPredicateCopy = new PersonContainsRecentPredicate(firstPredicatePersonList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_returnsTrue() {
        PersonContainsRecentPredicate predicate = new PersonContainsRecentPredicate(Collections.singletonList(alice));
        assertTrue(predicate.test(new PersonBuilder().build()));
    }

    @Test
    public void test_returnsFalse() {
        PersonContainsRecentPredicate predicate = new PersonContainsRecentPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().build()));
    }
}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Homepage} of the {@code Person} that we are building.
     */
    public PersonBuilder withHomepage(String homepage) {
        try {
            this.person.setHomepage(new Homepage(homepage));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("homepage is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Avatar} of the {@code Person} that we are building.
     */
    public PersonBuilder withAvatar(String avatarPath) {
        try {
            this.person.setAvatar(new Avatar(avatarPath));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("avatar is expected to be unique");
        }
        return this;
    }

    /**
     * Sets the {@code isHomepageManuallySet} boolean of the {@code Person} that we are building.
     */
    public PersonBuilder withBooleanHomepageManuallySet(boolean isHomepageManuallySet) {
        this.person.setBooleanIsHomepageManuallySet(isHomepageManuallySet);
        return this;
    }

```
###### \java\seedu\address\ui\PersonCardTest.java
``` java
    @Test
    public void avatarCheck() {
        Person personWithNoAvatar = new PersonBuilder().withAvatar("").build();
        PersonCard personCard = new PersonCard(personWithNoAvatar, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithNoAvatar, 1);

        Person personWithAvatar = null;
        try {
            personWithAvatar = new PersonBuilder().withAvatar(VALID_AVATAR_IMAGE_URL_ONE).build();
            personCard = new PersonCard(personWithAvatar, 1);
            uiPartRule.setUiPart(personCard);
            assertCardDisplay(personCard, personWithAvatar, 1);
        } finally {
            if (personWithAvatar != null) {
                ProcessImageUtil.removeImageFromStorage(personWithAvatar.getAvatar().path); // cleanup
            }
        }
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, ReadOnlyPerson expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, personCardHandle);
    }
}
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: missing email -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(EMPTY_STRING)
                .withAddress(VALID_ADDRESS_AMY).withTags().build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandSuccess(command, toAdd);

        /* Case: missing address -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(EMPTY_STRING).withTags().build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY;
        assertCommandSuccess(command, toAdd);
```
