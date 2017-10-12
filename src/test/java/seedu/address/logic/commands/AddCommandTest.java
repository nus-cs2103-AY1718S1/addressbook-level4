package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.parcel.Parcel;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.parcel.exceptions.DuplicateParcelException;
import seedu.address.model.parcel.exceptions.ParcelNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Parcel validParcel = new PersonBuilder().build();

        CommandResult commandResult = getAddCommandForPerson(validParcel, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validParcel), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validParcel), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePersonException();
        Parcel validParcel = new PersonBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PARCEL);

        getAddCommandForPerson(validParcel, modelStub).execute();
    }

    @Test
    public void equals() {
        Parcel alice = new PersonBuilder().withName("Alice").build();
        Parcel bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different parcel -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given parcel.
     */
    private AddCommand getAddCommandForPerson(Parcel parcel, Model model) {
        AddCommand command = new AddCommand(parcel);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addParcel(ReadOnlyParcel parcel) throws DuplicateParcelException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteParcel(ReadOnlyParcel target) throws ParcelNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateParcel(ReadOnlyParcel target, ReadOnlyParcel editedParcel)
                throws DuplicateParcelException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyParcel> getFilteredParcelList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredParcelList(Predicate<ReadOnlyParcel> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateParcelException when trying to add a parcel.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public void addParcel(ReadOnlyParcel parcel) throws DuplicateParcelException {
            throw new DuplicateParcelException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the parcel being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Parcel> personsAdded = new ArrayList<>();

        @Override
        public void addParcel(ReadOnlyParcel parcel) throws DuplicateParcelException {
            personsAdded.add(new Parcel(parcel));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
