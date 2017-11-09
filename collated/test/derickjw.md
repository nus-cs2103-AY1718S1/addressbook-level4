# derickjw
###### \java\seedu\address\logic\commands\FindByPhoneCommandTest.java
``` java
    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindByPhoneCommand command = prepareCommand("95352563 87652533 9482224");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, ELLE));
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_listCommandSuccess_noDefaultAccount() {
        String listCommand = ListCommand.COMMAND_WORD;
        String createDefaultAccountCommand = CreateDefaultAccountCommand.COMMAND_WORD;
        assertCommandSuccess(createDefaultAccountCommand, CreateDefaultAccountCommand.MESSAGE_CREATE_SUCCESS, model);
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_clearCommandSuccess_noDefaultAccount() {
        String clearCommand = ClearCommand.COMMAND_WORD;
        String createDefaultAccountCommand = CreateDefaultAccountCommand.COMMAND_WORD;
        assertCommandSuccess(createDefaultAccountCommand, CreateDefaultAccountCommand.MESSAGE_CREATE_SUCCESS, model);
        assertCommandSuccess(clearCommand, ClearCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_sortCommandSuccess_noDefaultAccount() {
        String sortCommand = SortCommand.COMMAND_WORD;
        String createDefaultAccountCommand = CreateDefaultAccountCommand.COMMAND_WORD;
        assertCommandSuccess(createDefaultAccountCommand, CreateDefaultAccountCommand.MESSAGE_CREATE_SUCCESS, model);
        assertCommandSuccess(sortCommand, SortCommand.MESSAGE_SUCCESS, model);
    }
```
###### \java\seedu\address\model\person\PhoneContainsKeywordsPredicateTest.java
``` java
    @Test
    public void testPhoneContainsKeywordsReturnsTrue() {
        // One phone number search
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("94571111"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("94571111").build()));

        // Multiple phone number searches
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("94571111", "94571112"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("94571111").build()));
    }

    @Test
    public void testPhoneDoesNotContainKeywordsReturnsFalse() {
        // Zero phone number search
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("94571111").build()));

        // Non-matching phone number
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("94571112"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("94571113").build()));

        // Keywords match name, email and address, but does not match phone
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Alice", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12346")
                .withEmail("alice@email.com").withAddress("Street").build()));
    }
}
```
