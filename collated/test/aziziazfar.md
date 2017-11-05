# aziziazfar
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson person1 = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson person2 = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        ReadOnlyPerson person3 = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());

        List<ReadOnlyPerson> personsToDelete = new ArrayList<>();
        personsToDelete.add(person1);
        personsToDelete.add(person2);
        personsToDelete.add(person3);

        List<Index> indicesToDelete = new ArrayList<>();
        indicesToDelete.add(INDEX_FIRST_PERSON);
        indicesToDelete.add(INDEX_SECOND_PERSON);
        indicesToDelete.add(INDEX_THIRD_PERSON);
        DeleteCommand deleteCommand = prepareCommandMulti(indicesToDelete);

        StringBuilder builder = new StringBuilder();
        for (ReadOnlyPerson toAppend: personsToDelete) {
            builder.append("\n" + toAppend.toString());
        }

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, builder);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        for (int i = 0; i < indicesToDelete.size(); i++) {
            ReadOnlyPerson personToDelete = model.getFilteredPersonList().get((indicesToDelete.size() - i - 1));
            expectedModel.deletePerson(personToDelete);
        }
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }
```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommandMulti(List<Index> indices) {
        DeleteCommand deleteCommand = new DeleteCommand(indices);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }
```
