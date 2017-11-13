# willxujun
###### \java\seedu\address\logic\commands\EditCommandTest.java
``` java
        //clear tags, setting both tags to add and tags to remove to empty
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags().withTagsToRemove().build();
        EditCommand editCommand = prepareCommand(descriptor, indexLastPerson);

        //save index in a list for new message format
        List<Index> expectedIndices = new ArrayList<>();
        expectedIndices.add(INDEX_SEVENTH_PERSON);
        String expectedMessage = String.format(MESSAGE_EDIT_PERSON_SUCCESS, expectedIndices.toString());
```
###### \java\seedu\address\logic\commands\SearchCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 * Adapted from FindCommandTest with additional phone and tag searches.
 */
public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel;

    @Before
    public void setup() {
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        NamePhoneTagContainsKeywordsPredicate firstPredicate =
                new NamePhoneTagContainsKeywordsPredicate(Collections.singletonList("first"));
        NamePhoneTagContainsKeywordsPredicate secondPredicate =
                new NamePhoneTagContainsKeywordsPredicate(Collections.singletonList("second"));

        SearchCommand findFirstCommand = new SearchCommand(firstPredicate);
        SearchCommand findSecondCommand = new SearchCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        SearchCommand findFirstCommandCopy = new SearchCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    /**
     * No keywords given -> all persons listed
     */
    public void execute_zeroKeywords_allPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        SearchCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    @Test
    /**
     * Keywords from multiple persons -> no person found
     */
    public void execute_multipleKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        SearchCommand command = prepareCommand("Carl Daniel");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    /**
     * Name prefixes -> listed persons with all the name prefixes
     */
    public void execute_namePrefixes_found() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        SearchCommand command = prepareCommand("Elle Me");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE));
    }

    @Test
    /**
     * tag -> listed person with specified tag
     */
    public void execute_tag_listed() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        SearchCommand command = prepareCommand("owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    /**
     * phone -> listed person with specified phone
     */
    public void execute_phonePrefix_listed() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        SearchCommand command = prepareCommand("123");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(FIONA, GEORGE));
    }

    /**
     * Parses {@code userInput} into a {@code SearchCommand}.
     */
    private SearchCommand prepareCommand(String userInput) {
        SearchCommand command =
                new SearchCommand(new NamePhoneTagContainsKeywordsPredicate(
                        Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(SearchCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        //empty (unknown) email value
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(UNKNOWN_EMAIL).withAddress(VALID_ADDRESS_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + UNKNOWN_EMAIL_DESC + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));

        //empty (unknown) address value
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(UNKNOWN_ADDRESS).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + UNKNOWN_ADDRESS_DESC, new AddCommand(expectedPerson));
```
###### \java\seedu\address\logic\parser\SearchParserTest.java
``` java
public class SearchParserTest {

    private SearchParser parser = new SearchParser();

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedSearchCommand =
                new SearchCommand(new NamePhoneTagContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedSearchCommand);
    }

    @Test
    public void parse_whitespaceArgs_returnsListCommand() {
        ListCommand expectedListCommand =
                new ListCommand();
        assertParseSuccess(parser, "    ", expectedListCommand);
    }

}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTagsToRemove(String... tags) {
        try {
            descriptor.setTagsToRemove(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Set the {@code tags} to the person we are building
     */
    public PersonBuilder withTags(Set<Tag> tags) {
        CollectionUtil.elementsAreUnique(tags);
        this.person.setTags(tags);
        return this;
    }
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* Case: add a tag -> edited*/
        index = INDEX_FIRST_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TAG_DESC_FRIEND;
        ReadOnlyPerson personToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        Set<Tag> originalTags = new HashSet<>(personToEdit.getTags());
        originalTags.add(new Tag(VALID_TAG_FRIEND));
        System.out.println(personToEdit.getAsText());
        editedPerson = new PersonBuilder(personToEdit)
                .withTags(originalTags).build();
        toEdit.clear();
        toEdit.add(index);
        assertCommandSuccess(command, toEdit, editedPerson);

        /* Case: delete a tag -> edited*/
        index = INDEX_FIRST_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + RM_TAG_DESC_FRIEND;
        originalTags.remove(new Tag(VALID_TAG_FRIEND));
        editedPerson = new PersonBuilder(personToEdit)
                .withTags(originalTags).build();
        toEdit.clear();
        toEdit.add(index);
        assertCommandSuccess(command, toEdit, editedPerson);

        /* Case: add a tag then delete the same tag -> edited*/
        index = INDEX_FIRST_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TAG_DESC_FRIEND + RM_TAG_DESC_FRIEND;
        editedPerson = new PersonBuilder(personToEdit)
                .withTags(originalTags).build();
        toEdit.clear();
        toEdit.add(index);
        assertCommandSuccess(command, toEdit, editedPerson);
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find multiple persons in address book, 2 keywords -> 0 persons found because of new AND search*/
        command = FindCommand.COMMAND_WORD + " Benson Daniel";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
```
