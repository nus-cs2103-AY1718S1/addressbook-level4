package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RELATIONSHIP_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RELATIONSHIP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Relationship;
import seedu.address.testutil.PersonBuilder;

//@@author Ernest
/**
  * Contains integration tests (interaction with the Model) and unit tests for RelationshipCommand.
  */
public class RelationshipCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeAddRelationshipSuccess() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRelationship("Some relationship").build();

        RelationshipCommand relationshipCommand =
                prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRelationship().value);

        String expectedMessage = String.format(RelationshipCommand.MESSAGE_ADD_RELATIONSHIP_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(relationshipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeDeleteRelationshipSuccess() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRelationship(new Relationship(""));

        RelationshipCommand relationshipCommand =
                prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRelationship().toString());

        String expectedMessage = String.format(RelationshipCommand.MESSAGE_DELETE_RELATIONSHIP_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(relationshipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidIndexFilteredListEditRelationshipSuccess() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personWithRelation = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personWithRelation).withRelationship("Test").build();
        RelationshipCommand relationshipCommand = prepareCommand(INDEX_FIRST_PERSON,
                editedPerson.getRelationship().toString());

        String expectedMessage = String.format(RelationshipCommand
                .MESSAGE_ADD_RELATIONSHIP_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), personWithRelation);

        assertCommandSuccess(relationshipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidIndexFilteredListDeleteRelationshipSuccess() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personWithRelation = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personWithRelation).withRelationship("").build();
        RelationshipCommand relationshipCommand = prepareCommand(INDEX_FIRST_PERSON,
                editedPerson.getRelationship().toString());

        String expectedMessage = String.format(RelationshipCommand
                .MESSAGE_DELETE_RELATIONSHIP_SUCCESS, personWithRelation);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), personWithRelation);

        assertCommandSuccess(relationshipCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidPersonIndexUnfilteredListFailure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RelationshipCommand relationshipCommand = prepareCommand(outOfBoundIndex, VALID_RELATIONSHIP_BOB);

        assertCommandFailure(relationshipCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
      * Edit filtered list where index is larger than size of filtered list,
      * but smaller than size of address book
      */
    @Test
    public void executeInvalidPersonIndexFilteredListFailure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RelationshipCommand relationshipCommand = prepareCommand(outOfBoundIndex, VALID_RELATIONSHIP_BOB);

        assertCommandFailure(relationshipCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RelationshipCommand standardCommand =
                new RelationshipCommand(INDEX_FIRST_PERSON, new Relationship(VALID_RELATIONSHIP_AMY));

        // same values -> returns true
        RelationshipCommand commandWithSameValues =
                new RelationshipCommand(INDEX_FIRST_PERSON, new Relationship(VALID_RELATIONSHIP_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RelationshipCommand(INDEX_SECOND_PERSON,
                new Relationship(VALID_RELATIONSHIP_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RelationshipCommand(INDEX_FIRST_PERSON,
                new Relationship(VALID_RELATIONSHIP_BOB))));

        // null
        assertNotNull(standardCommand);
    }

    /**
      * Returns an {@code RelationshipCommand} with parameters {@code index} and {@code relation}
      */
    private RelationshipCommand prepareCommand(Index index, String relation) {
        RelationshipCommand relationshipCommand = new RelationshipCommand(index, new Relationship(relation));
        relationshipCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return relationshipCommand;
    }
}
