# lancehaoh
###### /java/seedu/address/commons/util/AudioUtilTest.java
``` java
    @Test
    public void checkFileNotFoundThrowsIoException() {
        boolean exceptionWasThrown = false;
        try {
            audioUtility.playClip("???????.wav");
        } catch (IOException e) {
            exceptionWasThrown = true;
        } catch (Exception err) {
            err.printStackTrace();
        }
        // IO Exception was not thrown, this is a failing case
        if (!exceptionWasThrown) {
            assertTrue(false);
        }
    }

```
###### /java/seedu/address/commons/util/AudioUtilTest.java
``` java
    @Test
    public void checkInvalidFileFormatUnsupportedAudioFileException() {
        boolean exceptionWasThrown = false;
        try {
            audioUtility.playClip("notavalidfile.png");
            assertTrue(false);
        } catch (UnsupportedAudioFileException e) {
            exceptionWasThrown = true;
        } catch (Exception err) {
            err.printStackTrace();
        }
        // IO Exception was not thrown, this is a failing case
        if (!exceptionWasThrown) {
            assertTrue(false);
        }
    }
}
```
###### /java/seedu/address/logic/commands/CommandTest.java
``` java
    @Test
    /**
     * Check for duplicate command aliases.
     */
    public void executeFindDuplicateCommandAliasesErrorIfFound() {
        Set<String> uniqueAliases = new HashSet<>();
        uniqueAliases.addAll(Command.getListOfAvailableCommandAliases());
        assertEquals(Command.getListOfAvailableCommandAliases().size(), uniqueAliases.size());
    }

```
###### /java/seedu/address/logic/commands/CommandTest.java
``` java
    @Test
    /**
     * Check if every command has defined a help string
     */
    public void executeCheckHelpForEveryCommand() {
        Map<String, String> mapOfCommandsToHelp = Command.getMapOfCommandHelp();
        List<String> listOfAvailableCommands = new ArrayList<>(Command.getMapOfCommandFormats().keySet());

        // Check if every command maps to a help string
        for (String s : listOfAvailableCommands) {
            assertTrue(mapOfCommandsToHelp.containsKey(s));
        }
    }
}
```
###### /java/seedu/address/logic/commands/DeleteTagCommandTest.java
``` java
    @Test
    /**
     * Ensure that after a tag is properly deleted from all contacts in the address book
     */
    public void executeFindDeletedTagFailure() {
        ObservableList<ReadOnlyPerson> listOfPersons = model.getFilteredPersonList();

        // Delete a sample tag
        try {
            deleteTagHelper("friends");
        } catch (CommandException ce) {
            ce.printStackTrace();
        }

        // Check if the the tag is still present in anyone in the address book
        for (ReadOnlyPerson person : listOfPersons) {
            Set<Tag> tags = person.getTags();
            for (Tag t : tags) {
                String tagName = t.toString();
                assertNotEquals(tagName, "friends");
            }
        }
    }

```
###### /java/seedu/address/logic/commands/DeleteTagCommandTest.java
``` java
    @Test
    /**
     * Check if a non-existent tag name can be properly handled
     */
    public void executeDeleteFakeTagNoActionPerformed() {
        // Delete a tag twice to simulate a non-existent tag being deleted
        try {
            deleteTagHelper("friends");
        } catch (CommandException ce) {
            ce.printStackTrace();
        }

        List<Tag> oldListOfTags = model.getAddressBook().getTagList();

        try {
            deleteTagHelper("friends");
        } catch (CommandException ce) {
            ce.printStackTrace();
        }

        List<Tag> newListOfTags = model.getAddressBook().getTagList();

        assertEquals(oldListOfTags, newListOfTags);
    }

```
###### /java/seedu/address/logic/commands/DeleteTagCommandTest.java
``` java
    @Test
    /**
     * Tests if delete tag command can be successfully autocorrected
     * up to maximum of 2 character substitutions
     *
     */
    public void executeDeleteTagCommandWrongSpellingCommandCorrected() {
        Command commandWithOneSubstitution = parseWronglySpeltDeleteTagCommand("deletatag friends");

        // Verify if command was parse to search command
        assertTrue(commandWithOneSubstitution instanceof DeleteTagCommand);

        // Check if able to execute this search command
        try {
            commandWithOneSubstitution.setData(model, new CommandHistory(), new UndoRedoStack());
            commandWithOneSubstitution.execute();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }

        Command commandWithTwoSubstitution = parseWronglySpeltDeleteTagCommand("deletetgf friends");

        // Verify if command was parse to search command
        assertTrue(commandWithTwoSubstitution instanceof DeleteTagCommand);

        // Check if able to execute this search command
        try {
            commandWithTwoSubstitution.setData(model, new CommandHistory(), new UndoRedoStack());
            commandWithTwoSubstitution.execute();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

```
###### /java/seedu/address/logic/commands/DeleteTagCommandTest.java
``` java
    /**
     * Helper method to autocorrect wrongly spelt commands
     */
    private Command parseWronglySpeltDeleteTagCommand(String userinput) {
        AddressBookParser parser = new AddressBookParser();
        Command parsedCommand = null;
        // Parse a search command with small spelling error
        try {
            parsedCommand = parser.parseCommand(userinput);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return parsedCommand;
    }

```
###### /java/seedu/address/logic/commands/DeleteTagCommandTest.java
``` java
    /**
     * @params name of a a tag
     * @return DeleteTagCommand object
     */
    private DeleteTagCommand prepareCommand(String tagName) {
        DeleteTagCommand deleteTagCommand = null;
        try {
            deleteTagCommand = new DeleteTagCommand(new Tag(tagName));
            deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleteTagCommand;
    }

    /**
     * Helper method for deleting a given tagName
     *
     * @param tagName An arbitrary string
     *
     * Executes a Delete Tag command to delete tags
     * with matching tag name from all contacts
     */
    private void deleteTagHelper(String tagName) throws CommandException {
        DeleteTagCommand deleteTagCommand = prepareCommand(tagName);
        deleteTagCommand.execute();
    }

```
###### /java/seedu/address/logic/commands/DeleteTagCommandTest.java
``` java
    @After
    /**
     * Clears the autocorrect status messages to prevent them from persisting among
     * tests
     */
    public void clearAutocorrectStatusMessage() {
        AutoCorrectCommand.clearMessageToUser();
    }
}
```
###### /java/seedu/address/logic/commands/DeleteTaskCommandTest.java
``` java
    @Test
    public void executeValidMultipleIndexUnfilteredListSuccess() throws Exception {
        ReadOnlyTask taskToDelete1 = model.getFilteredTaskList().get(INDEX_MULTIPLE_TASK.get(0).getZeroBased());
        ReadOnlyTask taskToDelete2 = model.getFilteredTaskList().get(INDEX_MULTIPLE_TASK.get(1).getZeroBased());
        DeleteTaskCommand deleteCommand = prepareCommand(INDEX_MULTIPLE_TASK);
        String expectedMessage = "";
        expectedMessage =  expectedMessage.concat("\n" + taskToDelete2.toString());
        expectedMessage = expectedMessage.concat("\n" + taskToDelete1.toString());
        expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, expectedMessage);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete1);
        expectedModel.deleteTask(taskToDelete2);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

```
###### /java/seedu/address/logic/commands/DeleteTaskCommandTest.java
``` java
    @Test
    public void executeValidSingleIndexUnfilteredListSuccess() throws Exception {

        ReadOnlyTask taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.get(0).getZeroBased());
        DeleteTaskCommand deleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

```
###### /java/seedu/address/logic/commands/DeleteTaskCommandTest.java
``` java
    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ArrayList<Index> outOfBound = new ArrayList<>(Arrays.asList(outOfBoundIndex));
        DeleteTaskCommand deleteCommand = prepareCommand(outOfBound);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

```
###### /java/seedu/address/logic/commands/DeleteTaskCommandTest.java
``` java
    @Test
    public void executeValidIndexFilteredListSuccess() throws Exception {
        showFirstTaskOnly(model);
        AutoCorrectCommand autoCorrectCommand = new AutoCorrectCommand();
        autoCorrectCommand.setMessageToUser("");

        ReadOnlyTask taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.get(0).getZeroBased());
        DeleteTaskCommand deleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);
        showNoTask(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

```
###### /java/seedu/address/logic/commands/DeleteTaskCommandTest.java
``` java
    @Test
    public void executeInvalidIndexFilteredListThrowsCommandException() {
        showFirstTaskOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_TASK.get(0);
        ArrayList<Index> outOfBound = new ArrayList<>(Arrays.asList(outOfBoundIndex));
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTaskList().size());

        DeleteTaskCommand deleteCommand = prepareCommand(outOfBound);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

```
###### /java/seedu/address/logic/commands/DeleteTaskCommandTest.java
``` java
    @Test
    public void equals() {
        DeleteTaskCommand deleteFirstCommand = new DeleteTaskCommand(INDEX_FIRST_TASK);
        DeleteTaskCommand deleteSecondCommand = new DeleteTaskCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTaskCommand deleteFirstCommandCopy = new DeleteTaskCommand(INDEX_FIRST_TASK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // different task -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

```
###### /java/seedu/address/logic/commands/DeleteTaskCommandTest.java
``` java
    /**
     * Returns a {@code DeleteTaskCommand} with the parameter {@code index}.
     */
    private DeleteTaskCommand prepareCommand(ArrayList <Index> index) {
        DeleteTaskCommand deleteCommand = new DeleteTaskCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

```
###### /java/seedu/address/logic/commands/DeleteTaskCommandTest.java
``` java
    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assert model.getFilteredTaskList().isEmpty();
    }
}
```
###### /java/seedu/address/logic/commands/ListTaskCommandTest.java
``` java
    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listTaskCommand, model, ListTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

```
###### /java/seedu/address/logic/commands/ListTaskCommandTest.java
``` java
    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstTaskOnly(model);
        assertCommandSuccess(listTaskCommand, model, ListTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### /java/seedu/address/logic/commands/MultiFilterCommandTest.java
``` java
    @Test
    /**
     * Use various comparsions to verify if equals method has been properly overriden
     */
    public void equals() {
        NameContainsKeywordsSubstrPredicate firstNamePredicate =
                new NameContainsKeywordsSubstrPredicate(Collections.singletonList("first"));
        NameContainsKeywordsSubstrPredicate secondNamePredicate =
                new NameContainsKeywordsSubstrPredicate(Collections.singletonList("second"));

        PhoneContainsKeywordsSubstrPredicate firstPhonePredicate =
                new PhoneContainsKeywordsSubstrPredicate(Collections.singletonList("999"));
        PhoneContainsKeywordsSubstrPredicate secondPhonePredicate =
                new PhoneContainsKeywordsSubstrPredicate(Collections.singletonList("998"));

        EmailContainsKeywordsSubstrPredicate firstEmailPredicate =
                new EmailContainsKeywordsSubstrPredicate(Collections.singletonList("ex@gmail.com"));
        EmailContainsKeywordsSubstrPredicate secondEmailPredicate =
                new EmailContainsKeywordsSubstrPredicate(Collections.singletonList("ed@gmail.com"));

        AddressContainsKeywordsSubstrPredicate firstAddressPredicate =
                new AddressContainsKeywordsSubstrPredicate(Collections.singletonList("example1"));
        AddressContainsKeywordsSubstrPredicate secondAddressPredicate =
                new AddressContainsKeywordsSubstrPredicate(Collections.singletonList("example2"));

        MultiFilterCommand firstFilterCommand = new MultiFilterCommand(
                firstNamePredicate,
                firstPhonePredicate,
                firstEmailPredicate,
                firstAddressPredicate
        );
        MultiFilterCommand firstFilterCommandCopy = new MultiFilterCommand(
                firstNamePredicate,
                firstPhonePredicate,
                firstEmailPredicate,
                firstAddressPredicate
        );
        MultiFilterCommand secondFilterCommand = new MultiFilterCommand(
                secondNamePredicate,
                secondPhonePredicate,
                secondEmailPredicate,
                secondAddressPredicate
        );
        MultiFilterCommand firstNullableCommand = new MultiFilterCommand(
                null,
                firstPhonePredicate,
                firstEmailPredicate,
                null

        );
        MultiFilterCommand secondNullableCommand = new MultiFilterCommand(
                null,
                secondPhonePredicate,
                secondEmailPredicate,
                null

        );

        // same object -> returns true
        assertTrue(secondFilterCommand.equals(secondFilterCommand));

        // same values -> returns true
        assertTrue(firstFilterCommand.equals(firstFilterCommandCopy));

        // different types -> returns false
        assertFalse(firstFilterCommand.equals(1));

        // null -> returns false
        assertFalse(firstFilterCommand.equals(null));

        // different person -> returns false
        assertFalse(firstFilterCommand.equals(secondFilterCommand));

        /* different values -> returns false (also shows that filter command
        /* can compare two null values
         */
        assertFalse(firstNullableCommand.equals(secondNullableCommand));
    }

```
###### /java/seedu/address/logic/commands/MultiFilterCommandTest.java
``` java
    @Test
    /**
     * Verifies that multiple values in a field will be treated as a single string
     * and used to match contacts in address book
     */
    public void execute_multipleValuesInField_singlePersonFound() {
        MultiFilterCommand command = prepareCommand("Benson Meier", null, null, null);

        List<ReadOnlyPerson> expectedList = new ArrayList<>(model.getAddressBook().getPersonList())
                .stream()
                .filter(p -> p.getName().toString().toLowerCase().contains("Benson Meier".toLowerCase()))
                .collect(Collectors.toList());
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedList.size());
        assertCommandSuccess(command, expectedMessage, expectedList);
    }

```
###### /java/seedu/address/logic/commands/MultiFilterCommandTest.java
``` java
    @Test
    /**
     * Verifies if comparison is case-insensitive
     */
    public void execute_caseInsensitive_multiplePersonsFound() {
        for (char i = 'a'; i <= 'z'; i++) {
            final String lowercaseStr = i + "";
            final String uppercaseStr = (i + "").toUpperCase();

            // Query address book using lowercase name
            List<ReadOnlyPerson> expectedListLowercase =
                    new ArrayList<>(model.getAddressBook().getPersonList())
                    .stream()
                    .filter(p -> p.getName().toString().toLowerCase().contains(lowercaseStr))
                    .collect(Collectors.toList());

            String expectedMessageLowercase = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                    expectedListLowercase.size());
            MultiFilterCommand command = prepareCommand(lowercaseStr, null, null, null);
            assertCommandSuccess(command, expectedMessageLowercase, expectedListLowercase);

            // Query address book using lowercase name
            List<ReadOnlyPerson> expectedListUppercase =
                    new ArrayList<>(model.getAddressBook().getPersonList())
                            .stream()
                            .filter(p -> p.getName().toString().toUpperCase().contains(uppercaseStr))
                            .collect(Collectors.toList());

            // Query address book using uppercase name
            String expectedMessageUppercase = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                    expectedListUppercase.size());
            command = prepareCommand(uppercaseStr, null, null, null);
            assertCommandSuccess(command, expectedMessageUppercase, expectedListUppercase);

            assertEquals(expectedListLowercase, expectedListUppercase);
        }
    }

```
###### /java/seedu/address/logic/commands/MultiFilterCommandTest.java
``` java
    @Test
    /**
     * Verifies that a null predicate will not be used to filter persons
     * i.e. if all fields are null, all persons in the address book are returned
     */
    public void execute_multipleKeywords_allPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                model.getAddressBook().getPersonList().size());
        MultiFilterCommand command = prepareCommand(null, null, null, null);
        assertCommandSuccess(command, expectedMessage,
                model.getAddressBook().getPersonList());
    }

```
###### /java/seedu/address/logic/commands/MultiFilterCommandTest.java
``` java
    @Test
    /**
     * Checks if able to perform a substring filtering on names
     * Match is case-insensitive
     */
    public void execute_matchNameSubstring_multiplePersonsFound() {
        MultiFilterCommand command = prepareCommand("on", null, null, null);

        List<ReadOnlyPerson> expectedList = new ArrayList<>(model.getAddressBook().getPersonList())
                .stream()
                .filter(p -> p.getName().toString().toLowerCase().contains("on"))
                .collect(Collectors.toList());
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedList.size());
        assertCommandSuccess(command, expectedMessage, expectedList);
    }

```
###### /java/seedu/address/logic/commands/MultiFilterCommandTest.java
``` java
    @Test
    /**
     * Checks if able to perform a substring filtering on email
     * Match is case-insensitive
     */
    public void execute_matchEmailSubstring_multiplePersonsFound() {
        MultiFilterCommand command = prepareCommand(null, null, "ne", null);
        List<ReadOnlyPerson> expectedList = new ArrayList<>(model.getAddressBook().getPersonList())
                .stream()
                .filter(p -> p.getEmail().toString().toLowerCase().contains("ne"))
                .collect(Collectors.toList());
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedList.size());
        assertCommandSuccess(command, expectedMessage, expectedList);
    }

```
###### /java/seedu/address/logic/commands/MultiFilterCommandTest.java
``` java
    @Test
    /**
     * Checks if able to perform a multi-filter search (by name and email)
     * Match is case-insensitive
     */
    public void execute_multiFilterSearch_multiplePersonsFound() {
        MultiFilterCommand command = prepareCommand("a", null, "ali", null);
        List<ReadOnlyPerson> expectedList = new ArrayList<>(model.getAddressBook().getPersonList())
                .stream()
                .filter(p -> p.getName().toString().toLowerCase().contains("a"))
                .filter(p -> p.getEmail().toString().toLowerCase().contains("ali"))
                .collect(Collectors.toList());
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedList.size());
        assertCommandSuccess(command, expectedMessage, expectedList);
    }

```
###### /java/seedu/address/logic/commands/MultiFilterCommandTest.java
``` java
    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private MultiFilterCommand prepareCommand(String name, String phone, String email, String address) {
        MultiFilterCommand command = new MultiFilterCommand(
                name == null ? null : new NameContainsKeywordsSubstrPredicate(
                        Arrays.asList(name)),
                phone == null ? null : new PhoneContainsKeywordsSubstrPredicate(
                        Arrays.asList(phone)),
                email == null ? null : new EmailContainsKeywordsSubstrPredicate(
                        Arrays.asList(email)),
                address == null ? null : new AddressContainsKeywordsSubstrPredicate(
                        Arrays.asList(address))
        );

        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

```
###### /java/seedu/address/logic/commands/MultiFilterCommandTest.java
``` java
    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(
            MultiFilterCommand command,
            String expectedMessage,
            List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### /java/seedu/address/logic/commands/SearchCommandTest.java
``` java
    @Test
    /**
     * Tests if search command can be successfully autocorrected
     * up to maximum of 2 character substitutions
     */
    public void executeSearchCommandWrongSpellingCommandCorrected() {
        Command commandWithOneSubstitution = parseWronglySpeltSearchCommand("searcf friends");

        // Verify if command was parse to search command
        assertTrue(commandWithOneSubstitution instanceof SearchCommand);

        // Check if able to execute this search command
        try {
            commandWithOneSubstitution.setData(model, new CommandHistory(), new UndoRedoStack());
            commandWithOneSubstitution.execute();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }

        Command commandWithTwoSubstitution = parseWronglySpeltSearchCommand("searf friends");

        // Verify if command was parse to search command
        assertTrue(commandWithTwoSubstitution instanceof SearchCommand);

        // Check if able to execute this search command
        try {
            commandWithTwoSubstitution.setData(model, new CommandHistory(), new UndoRedoStack());
            commandWithTwoSubstitution.execute();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

```
###### /java/seedu/address/logic/commands/SearchCommandTest.java
``` java
    /**
     * Helper method to autocorrect wrongly spelt commands
     */
    private Command parseWronglySpeltSearchCommand(String userinput) {
        AddressBookParser parser = new AddressBookParser();
        Command parsedCommand = null;
        // Parse a search command with small spelling error
        try {
            parsedCommand = parser.parseCommand(userinput);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return parsedCommand;
    }

```
###### /java/seedu/address/logic/commands/SearchCommandTest.java
``` java
    @After
    /**
     * Clears the autocorrect status messages to prevent them from persisting among
     * tests
     */
    public void clearAutocorrectStatusMessage() {
        AutoCorrectCommand.clearMessageToUser();
    }

}
```
###### /java/seedu/address/logic/parser/AutoCorrectCommandTest.java
``` java
    @After
    /**
     * Clears the autocorrect status messages to prevent them from persisting among
     * tests
     */
    public void clearAutocorrectStatusMessage() {
        AutoCorrectCommand.clearMessageToUser();
    }
}
```
###### /java/seedu/address/logic/parser/MultiFilterCommandParserTest.java
``` java
    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // multiple emails - last email accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // name prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + " " + PREFIX_NAME + ""
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                new MultiFilterCommand(
                        null,
                        new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                        new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                        new AddressContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getAddress().toString()))
                ));

        //  phone prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB
                        + " " + PREFIX_PHONE + "" + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                        null,
                        new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                        new AddressContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getAddress().toString()))
                ));

        // email prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB
                        + PHONE_DESC_BOB + " " + PREFIX_EMAIL + "" + ADDRESS_DESC_BOB,
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                        new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                        null,
                        new AddressContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getAddress().toString()))
                ));


        // address prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + " " + PREFIX_ADDRESS + "",
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                        new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                        new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                        null
                ));

        // test if able to parse data person with data of arbitrary length and characters
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + " " + PREFIX_NAME + "a"
                        + " " + PREFIX_PHONE + "9" + " " + PREFIX_EMAIL + "ex" + " " + PREFIX_ADDRESS + "a",
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(Arrays.asList("a")),
                        new PhoneContainsKeywordsSubstrPredicate(Arrays.asList("9")),
                        new EmailContainsKeywordsSubstrPredicate(Arrays.asList("ex")),
                        new AddressContainsKeywordsSubstrPredicate(Arrays.asList("a"))
                ));
    }

```
###### /java/seedu/address/logic/parser/MultiFilterCommandParserTest.java
``` java
    @Test
    /**
     * Tests if parser can handle cases where certain fields are not entered by the user
     */
    public void parse_partialFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND).build();

        // name is not entered
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, new MultiFilterCommand(
                null,
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // phone number is not entered
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                null,
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // email is not entered
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                null,
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // address is not entered
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                        new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                        new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                        null
                ));

        // name prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + " " + PREFIX_NAME + ""
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                new MultiFilterCommand(
                        null,
                        new PhoneContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getPhone().toString())),
                        new EmailContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getEmail().toString())),
                        new AddressContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getAddress().toString()))
                ));

        //  phone prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB
                        + " " + PREFIX_PHONE + "" + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getName().toString())),
                        null,
                        new EmailContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getEmail().toString())),
                        new AddressContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getAddress().toString()))
                ));

        // email prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB
                        + PHONE_DESC_BOB + " " + PREFIX_EMAIL + "" + ADDRESS_DESC_BOB,
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getName().toString())),
                        new PhoneContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getPhone().toString())),
                        null,
                        new AddressContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getAddress().toString()))
                ));


        // address prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + " " + PREFIX_ADDRESS + "",
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                        new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                        new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                        null
                ));

        // all fields are empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD,
                new MultiFilterCommand(null, null,
                        null, null));
    }

```
###### /java/seedu/address/logic/parser/MultiFilterCommandParserTest.java
``` java
    @Test
    /**
     * Tests if fields can be parsed properly when they are entered in an arbitrary order
     */
    public void parse_rearrangedFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + PHONE_DESC_BOB, new MultiFilterCommand(
                null,
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + ADDRESS_DESC_BOB
                + NAME_DESC_BOB + EMAIL_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                null,
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // multiple emails - last email accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + NAME_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                null,
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + EMAIL_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB,
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                        new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                        new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                        null
                ));
    }
}
```
