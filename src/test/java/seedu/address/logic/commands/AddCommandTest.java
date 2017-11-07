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
import seedu.address.model.place.Place;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.model.place.exceptions.DuplicatePlaceException;
import seedu.address.model.place.exceptions.PlaceNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PlaceBuilder;

public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPlace_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_placeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPlaceAdded modelStub = new ModelStubAcceptingPlaceAdded();
        Place validPlace = new PlaceBuilder().build();

        CommandResult commandResult = getAddCommandForPlace(validPlace, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPlace), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validPlace), modelStub.placesAdded);
    }

    @Test
    public void execute_duplicatePlace_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePlaceException();
        Place validPlace = new PlaceBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PLACE);

        getAddCommandForPlace(validPlace, modelStub).execute();
    }

    @Test
    public void equals() {
        Place alice = new PlaceBuilder().withName("Alice").build();
        Place bob = new PlaceBuilder().withName("Bob").build();
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

        // different place -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given place.
     */
    private AddCommand getAddCommandForPlace(Place place, Model model) {
        AddCommand command = new AddCommand(place);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPlace(ReadOnlyPlace place) throws DuplicatePlaceException {
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
        public void deletePlace(ReadOnlyPlace target) throws PlaceNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePlace(ReadOnlyPlace target, ReadOnlyPlace editedPlace)
                throws DuplicatePlaceException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPlace> getFilteredPlaceList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPlaceList(Predicate<ReadOnlyPlace> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void removeAllTags(Tag tagName) {
            fail("This method should not be called.");
        }

        @Override
        public void addTag(ReadOnlyPlace place, Tag tagname) {
            fail(" This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePlaceException when trying to add a place.
     */
    private class ModelStubThrowingDuplicatePlaceException extends ModelStub {
        @Override
        public void addPlace(ReadOnlyPlace place) throws DuplicatePlaceException {
            throw new DuplicatePlaceException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the place being added.
     */
    private class ModelStubAcceptingPlaceAdded extends ModelStub {
        final ArrayList<Place> placesAdded = new ArrayList<>();

        @Override
        public void addPlace(ReadOnlyPlace place) throws DuplicatePlaceException {
            placesAdded.add(new Place(place));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
