# chairz
###### /java/seedu/address/logic/commands/DeleteCommandTest.java
``` java
    @Test
    public void executeValidMultipleIndexUnfilteredListSuccess() throws Exception {
        ReadOnlyPerson personToDelete1 = model.getFilteredPersonList().get(INDEX_MULTIPLE_PERSON.get(0).getZeroBased());
        ReadOnlyPerson personToDelete2 = model.getFilteredPersonList().get(INDEX_MULTIPLE_PERSON.get(1).getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_MULTIPLE_PERSON);
        String expectedMessage = "";
        expectedMessage =  expectedMessage.concat("\n" + personToDelete2.toString());
        expectedMessage = expectedMessage.concat("\n" + personToDelete1.toString());
        expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, expectedMessage);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete1);
        expectedModel.deletePerson(personToDelete2);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidSingleIndexUnfilteredListSuccess() throws Exception {

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.get(0).getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> outOfBound = new ArrayList<>(Arrays.asList(outOfBoundIndex));
        DeleteCommand deleteCommand = prepareCommand(outOfBound);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.get(0).getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON.get(0);
        ArrayList<Index> outOfBound = new ArrayList<>(Arrays.asList(outOfBoundIndex));
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(outOfBound);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(ArrayList <Index> index) {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
```
###### /java/seedu/address/model/person/Phone2Test.java
``` java
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Phone2.isValidPhone("")); // empty string
        assertFalse(Phone2.isValidPhone(" ")); // spaces only
        assertFalse(Phone2.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone2.isValidPhone("phone")); // non-numeric
        assertFalse(Phone2.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone2.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Phone2.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone2.isValidPhone("93121534"));
        assertTrue(Phone2.isValidPhone("124293842033123")); // long phone numbers
        assertTrue(Phone2.isValidPhone("-")); //2nd Phone number is not available
    }
}
```
###### /java/seedu/address/model/task/DescriptionTest.java
``` java
    @Test
    public void isValidDescription() {
        // invalid description
        assertFalse(Description.isValidDescription("")); // empty string

        // valid description
        assertTrue(Description.isValidDescription(" ")); // spaces only
        assertTrue(Description.isValidDescription("location")); // alphabets only
        assertTrue(Description.isValidDescription("507020")); // numbers only
        assertTrue(Description.isValidDescription("159W Jalan Loyang Besar")); // alphanumeric characters
        assertTrue(Description.isValidDescription("Prepare a birthday cake! :)")); // with non-alphanumeric characters

    }
}
```
###### /java/seedu/address/model/task/NameTest.java
``` java
    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("birthday*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("birthday")); // alphabets only
        assertTrue(Name.isValidName("21")); // numbers only
        assertTrue(Name.isValidName("21st birthday")); // alphanumeric characters
        assertTrue(Name.isValidName("21ST Birthday")); // with capital letters
        assertTrue(Name.isValidName("21st Birthday Party is the best party")); // long names
    }
}
```
###### /java/seedu/address/model/task/PriorityTest.java
``` java
    @Test
    public void isValidPriority() {
        // invalid description
        assertFalse(Priority.isValidPriority("5")); // number < 0
        assertFalse(Priority.isValidPriority("-5")); // number > 2


        // valid description
        assertTrue(Priority.isValidPriority("2")); // Priority 2


    }
}
```
