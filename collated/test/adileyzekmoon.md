# adileyzekmoon
###### \java\seedu\address\logic\commands\FilterAllCommandTest.java
``` java
    @Test
    public void equals() throws Exception {
        PersonContainsAllKeywordsPredicate firstPredicate =
                new PersonContainsAllKeywordsPredicate(Collections.singletonList("first"));
        PersonContainsAllKeywordsPredicate secondPredicate =
                new PersonContainsAllKeywordsPredicate(Collections.singletonList("second"));

        FilterAllCommand FilterAllFirstCommand = new FilterAllCommand(firstPredicate);
        FilterAllCommand FilterAllSecondCommand = new FilterAllCommand(secondPredicate);

        // same object -> returns true
        assertTrue(FilterAllFirstCommand.equals(FilterAllFirstCommand));

        // same values -> returns true
        FilterAllCommand FilterAllFirstCommandCopy = new FilterAllCommand(firstPredicate);
        assertTrue(FilterAllFirstCommand.equals(FilterAllFirstCommandCopy));

        // different types -> returns false
        assertFalse(FilterAllFirstCommand.equals(1));

        // null -> returns false
        assertFalse(FilterAllFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(FilterAllFirstCommand.equals(FilterAllSecondCommand));
    }


    @Test
    public void execute_multipleKeywords_specificPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FilterAllCommand command = prepareCommand("owesMoney friends");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }
```
###### \java\seedu\address\logic\commands\FilterCommandTest.java
``` java
    @Test
    public void equals() throws Exception {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("first"));
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("second"));

        FilterCommand FilterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand FilterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(FilterFirstCommand.equals(FilterFirstCommand));

        // same values -> returns true
        FilterCommand FilterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(FilterFirstCommand.equals(FilterFirstCommandCopy));

        // different types -> returns false
        assertFalse(FilterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(FilterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(FilterFirstCommand.equals(FilterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FilterCommand command = prepareCommand("owesMoney colleagues");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }
```
