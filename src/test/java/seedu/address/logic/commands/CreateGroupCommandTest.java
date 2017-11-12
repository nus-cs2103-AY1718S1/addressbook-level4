package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CreateGroupCommand.MESSAGE_DUPLICATE_GROUP;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.storage.Storage;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.TypicalStorage;

//@@author eldonng
public class CreateGroupCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Storage storage = new TypicalStorage().setUp();

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingGroupAdded modelStub = new ModelStubAcceptingGroupAdded();
        Group validGroup = new GroupBuilder().build();

        CommandResult commandResult = getAddCommandForGroup(validGroup, modelStub).execute();

        assertEquals(String.format(CreateGroupCommand.MESSAGE_SUCCESS, validGroup.getGroupName().fullName,
                validGroup.getGroupMembers().size()), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validGroup), modelStub.groupsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateGroupException();
        Group validGroup = new GroupBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_DUPLICATE_GROUP);

        getAddCommandForGroup(validGroup, modelStub).execute();
    }
    @Test
    public void equals() throws IllegalValueException {

        GroupName groupName1 = new GroupName("Group 1");
        GroupName groupName2 = new GroupName("Group 2");
        List<Index> groupList = new ArrayList<>();
        CreateGroupCommand addGroup1Command = new CreateGroupCommand(groupName1, groupList);
        CreateGroupCommand addGroup2Command = new CreateGroupCommand(groupName2, groupList);

        // same object -> returns true
        assertTrue(addGroup1Command.equals(addGroup1Command));

        // same values -> returns true
        CreateGroupCommand addGroup1CommandCopy = new CreateGroupCommand(groupName1, groupList);
        assertTrue(addGroup1Command.equals(addGroup1CommandCopy));

        // different types -> returns false
        assertFalse(addGroup1Command.equals(1));

        // null -> returns false
        assertFalse(addGroup1Command.equals(null));

        // different person -> returns false
        assertFalse(addGroup1Command.equals(addGroup2Command));
    }

    /**
     * Generates a new CreateGroupCommand with the details of the given group.
     */
    private CreateGroupCommand getAddCommandForGroup(Group group, Model model) {
        CreateGroupCommand command = new CreateGroupCommand(group.getGroupName(),
                        new ArrayList<>(Arrays.asList(Index.fromOneBased(1))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), storage);
        return command;
    }

    /**
     * A Model stub that always throw a DuplicateGroupException when trying to add a group.
     */
    private class ModelStubThrowingDuplicateGroupException extends ModelStub {
        @Override
        public void addGroup(ReadOnlyGroup group) throws DuplicateGroupException {
            throw new DuplicateGroupException();
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return FXCollections.observableList(new ArrayList<>(Arrays.asList(ALICE)));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the group being added.
     */
    private class ModelStubAcceptingGroupAdded extends ModelStub {
        final ArrayList<Group> groupsAdded = new ArrayList<>();

        @Override
        public void addGroup(ReadOnlyGroup group) throws DuplicateGroupException {
            groupsAdded.add(new Group(group));
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return FXCollections.observableList(new ArrayList<>(Arrays.asList(ALICE)));
        }

        @Override
        public void updateFilteredGroupList(Predicate<ReadOnlyGroup> predicate) {
            assert true;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}


