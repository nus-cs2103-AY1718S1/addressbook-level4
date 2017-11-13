# thehelpfulbees
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java

    @Test
    public void execute_searchByTag_noPersonFound() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand("t\\worstEnemy");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_searchByTag_onePersonFound() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("t\\owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_searchByTag_multiplePeopleFound() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        FindCommand command = prepareCommand("t\\friends");
        assertCommandSuccess(command, expectedMessage,
                Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) {

        //this is work that the parser will do
        String[] nameKeywords = userInput.split("\\s+");
        Predicate<ReadOnlyPerson> searchPredicate;
        if (nameKeywords.length == 1 && nameKeywords[0].startsWith("t\\")) {
            searchPredicate  = new TagsContainKeywordPredicate(nameKeywords[0].substring(2));
        } else {
            searchPredicate = new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        }

        FindCommand command = new FindCommand(searchPredicate);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    //@@

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList)
            throws Exception {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\model\person\NumTimesSearchedTest.java
``` java

public class NumTimesSearchedTest {

    @Test
    public void isValidValue() {
        // invalid name
        assertFalse(NumTimesSearched.isValidValue((-2)));
        assertFalse(NumTimesSearched.isValidValue((-1)));
        assertFalse(NumTimesSearched.isValidValue((-1000)));

        // valid name
        assertTrue(NumTimesSearched.isValidValue((0)));
        assertTrue(NumTimesSearched.isValidValue((1)));
        assertTrue(NumTimesSearched.isValidValue((2)));
        assertTrue(NumTimesSearched.isValidValue((200)));
    }

}
```
###### \java\seedu\address\model\person\TagsContainKeywordPredicateTest.java
``` java

public class TagsContainKeywordPredicateTest {

    @Test
    public void test_tagsContainKeyword_returnsTrue() {
        // One tag
        TagsContainKeywordPredicate predicate = new TagsContainKeywordPredicate("first");
        assertTrue(predicate.test(new PersonBuilder().withTags("first").build()));

        // Multiple tags
        predicate = new TagsContainKeywordPredicate("first");
        assertTrue(predicate.test(new PersonBuilder().withTags("first", "second").build()));

    }

    @Test
    public void test_tagsDoNotContainKeyword_returnsFalse() {
        // One tag
        TagsContainKeywordPredicate predicate = new TagsContainKeywordPredicate("wrong");
        assertFalse(predicate.test(new PersonBuilder().withTags("first").build()));

        // Multiple tags
        predicate = new TagsContainKeywordPredicate("wrong");
        assertFalse(predicate.test(new PersonBuilder().withTags("first", "second").build()));

        // Keyword is inside one of the tags but doesn't match
        predicate = new TagsContainKeywordPredicate("wrong");
        assertFalse(predicate.test(new PersonBuilder().withTags("thisiswrong").build()));

        // Mixed-case tags
        predicate = new TagsContainKeywordPredicate("wrong");
        assertFalse(predicate.test(new PersonBuilder().withTags("Wrong").build()));
    }

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";


        TagsContainKeywordPredicate firstPredicate = new TagsContainKeywordPredicate(firstPredicateKeyword);
        TagsContainKeywordPredicate secondPredicate = new TagsContainKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsContainKeywordPredicate firstPredicateCopy = new TagsContainKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

}
```
