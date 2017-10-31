# Ernest
###### \java\seedu\address\logic\commands\ListByBloodtypeCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class ListByBloodtypeCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        BloodtypeContainsKeywordPredicate firstPredicate =
                new BloodtypeContainsKeywordPredicate(Collections.singletonList("A"));
        BloodtypeContainsKeywordPredicate secondPredicate =
                new BloodtypeContainsKeywordPredicate(Collections.singletonList("AB"));

        ListByBloodtypeCommand findFirstCommand = new ListByBloodtypeCommand(firstPredicate);
        ListByBloodtypeCommand findSecondCommand = new ListByBloodtypeCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        ListByBloodtypeCommand findFirstCommandCopy = new ListByBloodtypeCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null
        assertNotNull(findFirstCommand);

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void executeZeroKeywordsNoPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        ListByBloodtypeCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void executeMultipleKeywordsMultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        ListByBloodtypeCommand command = prepareCommand("O+ A- B-");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, DANIEL));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private ListByBloodtypeCommand prepareCommand(String userInput) {
        ListByBloodtypeCommand command = new ListByBloodtypeCommand(new BloodtypeContainsKeywordPredicate(
                Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ListByBloodtypeCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommandListByBloodtype() throws Exception {
        List<String> keyword = Arrays.asList("A+", "ab", "O-");
        ListByBloodtypeCommand command = (ListByBloodtypeCommand) parser.parseCommand(
                ListByBloodtypeCommand.COMMAND_WORD + " "
                        + keyword.stream().collect(Collectors.joining(" ")));
        assertEquals(new ListByBloodtypeCommand(new BloodtypeContainsKeywordPredicate(keyword)), command);
    }
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseBloodTypeNullThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseBloodType(null);
    }

    @Test
    public void parseBloodTypeInvalidValueThrowsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseBloodType(Optional.of(INVALID_BLOODTYPE));
    }

    @Test
    public void parseBloodTypeOptionalEmptyReturnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseBloodType(Optional.empty()).isPresent());
    }

    @Test
    public void parseBloodTypeValidValueReturnsBloodType() throws Exception {
        Bloodtype expectedBloodType = new Bloodtype(VALID_BLOODTYPE);
        Optional<Bloodtype> actualBloodType = ParserUtil.parseBloodType(Optional.of(VALID_BLOODTYPE));

        assertEquals(expectedBloodType, actualBloodType.get());
    }
```
###### \java\seedu\address\model\person\BloodtypeContainsKeywordPredicateTest.java
``` java
public class BloodtypeContainsKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("AB");
        List<String> secondPredicateKeywordList = Collections.singletonList("O-");

        BloodtypeContainsKeywordPredicate firstPredicate =
                new BloodtypeContainsKeywordPredicate(firstPredicateKeywordList);
        BloodtypeContainsKeywordPredicate secondPredicate =
                new BloodtypeContainsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BloodtypeContainsKeywordPredicate firstPredicateCopy =
                new BloodtypeContainsKeywordPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertNotNull(firstPredicate);

        // different blood type -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testBloodtypeContainsKeywordsReturnsTrue() {
        // One keyword
        BloodtypeContainsKeywordPredicate predicate =
                new BloodtypeContainsKeywordPredicate(Collections.singletonList("AB"));
        assertTrue(predicate.test(new PersonBuilder().withBloodType("AB").build()));

        // Mixed-case keywords
        predicate = new BloodtypeContainsKeywordPredicate(Collections.singletonList("aB"));
        assertTrue(predicate.test(new PersonBuilder().withBloodType("AB").build()));
    }

    @Test
    public void testBloodtypeDoesNotContainKeywordReturnsFalse() {
        // No keyword
        BloodtypeContainsKeywordPredicate predicate = new BloodtypeContainsKeywordPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withBloodType("AB").build()));

        // Non-matching keyword
        predicate = new BloodtypeContainsKeywordPredicate(Collections.singletonList("O"));
        assertFalse(predicate.test(new PersonBuilder().withBloodType("AB").build()));
    }
}
```
###### \java\seedu\address\model\person\BloodtypeTest.java
``` java
public class BloodtypeTest {

    @Test
    public void isValidBloodType() {
        // invalid blood types
        assertFalse(Bloodtype.isValidBloodType("")); // empty string
        assertFalse(Bloodtype.isValidBloodType(" ")); // spaces only
        assertFalse(Bloodtype.isValidBloodType("L")); // not one of twelve valid inputs
        assertFalse(Bloodtype.isValidBloodType("cat")); // not one of twelve valid inputs
        assertFalse(Bloodtype.isValidBloodType("$%")); // not one of twelve valid inputs
        assertFalse(Bloodtype.isValidBloodType("ABCDE")); // more than three characters
        assertFalse(Bloodtype.isValidBloodType("+")); // "+" or "-" alone
        assertFalse(Bloodtype.isValidBloodType("-")); // "+" or "-" alone
        assertFalse(Bloodtype.isValidBloodType("B++"));
        assertFalse(Bloodtype.isValidBloodType("+B"));
        assertFalse(Bloodtype.isValidBloodType("+F"));
        assertFalse(Bloodtype.isValidBloodType("N"));
        assertFalse(Bloodtype.isValidBloodType("?"));
        assertFalse(Bloodtype.isValidBloodType("BB"));
        assertFalse(Bloodtype.isValidBloodType("BA"));
        assertFalse(Bloodtype.isValidBloodType("A B"));
        assertFalse(Bloodtype.isValidBloodType("A +"));
        assertFalse(Bloodtype.isValidBloodType("A+ "));
        assertFalse(Bloodtype.isValidBloodType(" A+"));

        // valid blood types
        assertTrue(Bloodtype.isValidBloodType("AB")); // all caps
        assertTrue(Bloodtype.isValidBloodType("ab")); // all small letters
        assertTrue(Bloodtype.isValidBloodType("aB")); // case insensitive
        assertTrue(Bloodtype.isValidBloodType("O")); // one character
        assertTrue(Bloodtype.isValidBloodType("B+")); // inclusive of + or - symbol

        //Placeholder blood type if no input detected
        assertTrue(Bloodtype.isValidBloodType(NON_COMPULSORY_BLOODTYPE));

        //Potential weird cases which could be improved on in the future
        assertTrue(Bloodtype.isValidBloodType(NON_COMPULSORY_BLOODTYPE + "+"));
        assertTrue(Bloodtype.isValidBloodType("O+"));
        assertTrue(Bloodtype.isValidBloodType("AB+"));
        assertTrue(Bloodtype.isValidBloodType("ab+"));
        assertTrue(Bloodtype.isValidBloodType("aB+"));


    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Bloodtype} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withBloodType(String bloodType) {
        try {
            ParserUtil.parseBloodType(Optional.of(bloodType)).ifPresent(descriptor::setBloodType);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("bloodtype is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Bloodtype} of the {@code Person} that we are building.
     */
    public PersonBuilder withBloodType(String bloodType) {
        try {
            this.person.setBloodType(new Bloodtype(bloodType));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("bloodType is expected to be unique.");
        }
        return this;
    }
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid bloodtype -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + INVALID_BLOODTYPE_DESC;
        assertCommandFailure(command, Bloodtype.MESSAGE_BLOODTYPE_CONSTRAINTS);
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* Case: invalid bloodtype -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_BLOODTYPE_DESC,
                Bloodtype.MESSAGE_BLOODTYPE_CONSTRAINTS);
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find bloodtype of person in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getBloodType().type;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
```
