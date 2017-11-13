# sofarsophie
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public ObservableList<ReadOnlyPerson> sortFilteredPersonList(
                ObservableList<ReadOnlyPerson> unsortedList, Prefix prefix) {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void removeTag(Tag tag) {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
        PersonContainsKeywordsPredicate.FindFields fieldsToFind = new FindFieldsBuilder().withName(splitName[0])
                .build();
        model.updateFilteredPersonList(new PersonContainsKeywordsPredicate(fieldsToFind));
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
        PersonContainsKeywordsPredicate.FindFields firstField = new FindFieldsBuilder().withName("first").build();
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(firstField);
        PersonContainsKeywordsPredicate.FindFields secondField = new FindFieldsBuilder().withName("second").build();
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(secondField);
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    @Test
    public void execute_zeroKeywords_noPersonFound() {
        FindFields emptyFields = new FindFieldsBuilder().build();
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand(emptyFields);
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_randomCaseKeyword_onePersonFound() {
        FindFields wrongCaseFields = new FindFieldsBuilder().withGroups("saVinGs").build();
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 6);
        FindCommand command = prepareCommand(wrongCaseFields);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, FIONA, GEORGE));
    }

    @Test
    public void execute_partialMatchKeyword_threePersonsFound() {
        FindFields partialKeywordFields = new FindFieldsBuilder().withAddress("street").build();
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand(partialKeywordFields);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, GEORGE));
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        FindFields threeKeywordsFields = new FindFieldsBuilder().withName("Kurz").withPhone("9482427")
                .withAddress("michegan ave").build();
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand(threeKeywordsFields);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Creates a new {@code FindCommand} with the parameter {@code fieldsToFind}.
     */
    private FindCommand prepareCommand(FindFields fieldsToFind) {
        FindCommand command =
                new FindCommand(new PersonContainsKeywordsPredicate(fieldsToFind));
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortByName_success() throws Exception {
        Prefix prefix = PREFIX_ADDRESS;
        SortCommand sortCommand = prepareCommand(model, prefix);
        String expectedMessage = SortCommand.MESSAGE_SORT_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sortFilteredPersonList(model.getFilteredPersonList(), prefix);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortTwoPersonsList_success() throws Exception {
        Prefix prefix = PREFIX_NAME;
        AddressBook ab = new AddressBookBuilder().withPerson(getTypicalPersons().get(1))
                .withPerson(getTypicalPersons().get(0)).build();
        Model modelWithTwoPersons = new ModelManager(ab, new UserPrefs());
        SortCommand sortCommand = prepareCommand(modelWithTwoPersons, prefix);
        String expectedMessage = SortCommand.MESSAGE_SORT_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(modelWithTwoPersons.getAddressBook()), new UserPrefs());
        expectedModel.sortFilteredPersonList(model.getFilteredPersonList(), prefix);

        assertCommandSuccess(sortCommand, modelWithTwoPersons, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortEmptyList_throwsCommandException() {
        Prefix prefix = PREFIX_EMAIL;
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        String expectedMessage = Messages.MESSAGE_EMPTY_PERSON_LIST;
        assertCommandFailure(prepareCommand(emptyModel, prefix), model, expectedMessage);
    }

    /**
     * Generates a new {@code SortCommand} with the Model and prefix given.
     */
    private SortCommand prepareCommand(Model model, Prefix prefix) {
        SortCommand sortCommand = new SortCommand(prefix);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
    @Test
    public void parse_multiWhiteSpaceValidArgs_returnsFindCommand() {
        FindFields fieldsToFind = new FindFieldsBuilder().withName("Alice").build();
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(fieldsToFind));
        // Multiple whitespaces before and after keyword
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + "     " + PREFIX_NAME + " Alice      \t",
                expectedFindCommand);
    }

    @Test
    public void parse_validDifferentFieldArgs_returnsFindCommand() {
        FindFields twoDifferentFields = new FindFieldsBuilder().withName("Alice")
                .withEmail("alice@example.com").build();
        // Two keywords from different fields
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(twoDifferentFields));
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "Alice"
                + " " + PREFIX_EMAIL + "alice@example.com", expectedFindCommand);
    }

    @Test
    public void parse_validSameFieldArgs_returnsFindCommand() {
        FindFields twoSameFields = new FindFieldsBuilder().withPhone("99999999", "89898989").build();
        // Two keywords from the same field
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(twoSameFields));
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + " " + PREFIX_PHONE + "99999999"
                + " " + PREFIX_PHONE + "89898989", expectedFindCommand);
    }
```
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
        // Wrong prefix
        assertParseFailure(parser, FindCommand.COMMAND_WORD + " y/" + "Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_noArgs_returnSortCommand() {
        assertParseSuccess(parser, "", new SortCommand());
    }


    @Test
    public void parse_validArg_returnsSortCommand() {
        assertParseSuccess(parser, PREFIX_ADDRESS.toString(), new SortCommand(PREFIX_ADDRESS));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        // Non-prefix argument
        assertParseFailure(parser, "name",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // Two same prefixes repeated
        assertParseFailure(parser, PREFIX_ADDRESS.toString() + PREFIX_ADDRESS.toString(),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArgs_throwsParseException() {
        assertParseFailure(parser, PREFIX_EMAIL.toString() + PREFIX_GROUP.toString(),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

}
```
###### \java\seedu\address\model\person\PersonContainsKeywordsPredicateTest.java
``` java
        // Keywords match phone, email and address, but does not match name
        PersonContainsKeywordsPredicate.FindFields differentMatchFields = new FindFieldsBuilder().withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main").withAddress("Street").build();
        predicate =
                new PersonContainsKeywordsPredicate(differentMatchFields);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
```
###### \java\seedu\address\testutil\FindFieldsBuilder.java
``` java
/**
 * A utility class to help with building FindFields objects.
 */
public class FindFieldsBuilder {

    private FindFields fieldsToFind;

    public FindFieldsBuilder() {
        fieldsToFind = new FindFields();
    }

    public FindFieldsBuilder(FindFields fieldsToFind) {
        this.fieldsToFind = fieldsToFind;
    }

    /**
     * Sets the {@code nameKeywords} list of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withName(String... names) {
        try {
            fieldsToFind.setNameKeywords(ParserUtil.parseNames(Arrays.asList(names)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("invalid name.");
        }
        return this;
    }

    /**
     * Sets the {@code phoneKeywords} list of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withPhone(String... phones) {
        try {
            fieldsToFind.setPhoneKeywords(ParserUtil.parsePhones(Arrays.asList(phones)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("invalid phone.");
        }
        return this;
    }

    /**
     * Sets the {@code emailKeywords} list of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withEmail(String emails) {
        try {
            fieldsToFind.setEmailKeywords(ParserUtil.parseEmails(Arrays.asList(emails)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("invalid email!");
        }
        return this;
    }

    /**
     * Sets the {@code addressKeywords} list of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withAddress(String... addresses) {
        try {
            fieldsToFind.setAddressKeywords(ParserUtil.parseAddresses(Arrays.asList(addresses)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("invalid address.");
        }
        return this;
    }

    /**
     * Sets the {@code groupKeywords} list of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withGroups(String... groups) {
        try {
            fieldsToFind.setGroupKeywords(ParserUtil.parseGroup(Arrays.asList(groups)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("invalid groups.");
        }
        return this;
    }

    /**
     * Sets the {@code tagKeywords} list of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withTags(String... tags) {
        fieldsToFind.setTagKeywords(Arrays.asList(tags));
        return this;
    }

    public FindFields build() {
        return fieldsToFind;
    }
}
```
