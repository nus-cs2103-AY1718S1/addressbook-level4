package seedu.address.logic.commands;

import static java.util.stream.Collectors.toCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.CommandTest;
import seedu.address.logic.Password;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.Username;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest extends CommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() {
        try {
            ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
            Person validPerson = new PersonBuilder().build();

            CommandResult commandResult = getAddCommandForPerson(validPerson, modelStub).execute();
            String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS, validPerson.getName());
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePersonException();
        Person validPerson = new PersonBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);

        getAddCommandForPerson(validPerson, modelStub).execute();
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
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

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private AddCommand getAddCommandForPerson(Person person, Model model) {
        AddCommand command = new AddCommand(person);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        //@@author jaivigneshvenugopal
        @Override
        public ReadOnlyPerson addBlacklistedPerson(ReadOnlyPerson person) {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyPerson addWhitelistedPerson(ReadOnlyPerson person) {
            fail("This method should not be called.");
            return null;
        }
        //@@author

        @Override
        public ReadOnlyPerson addOverdueDebtPerson(ReadOnlyPerson person) {
            fail("This method should not be called.");
            return null;
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

        //@@author jaivigneshvenugopal
        @Override
        public String getCurrentListName() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void setCurrentListName(String currentList) {
            fail("This method should not be called.");
        }
        //@@author

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        //@@author jaivigneshvenugopal
        @Override
        public ReadOnlyPerson removeBlacklistedPerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyPerson removeWhitelistedPerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
            return null;
        }
        //@@author

        @Override
        public ReadOnlyPerson removeOverdueDebtPerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyPerson getSelectedPerson() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ReadOnlyPerson> getAllPersons() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        //@@author jaivigneshvenugopal
        @Override
        public ObservableList<ReadOnlyPerson> getFilteredBlacklistedPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredWhitelistedPersonList() {
            return null;
        }
        //@@author

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredOverduePersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public int updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
            return 0;
        }

        //@@author jaivigneshvenugopal
        @Override
        public int updateFilteredBlacklistedPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
            return 0;
        }

        @Override
        public int updateFilteredWhitelistedPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
            return 0;
        }
        //@@author

        @Override
        public int updateFilteredOverduePersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
            return 0;
        }

        @Override
        public void updateSelectedPerson(ReadOnlyPerson person) {
            fail("This method should not be called.");
        }

        @Override
        public void deselectPerson() {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getNearbyPersons() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void sortBy(String string) {
            fail("This method should not be called.");
        }

        @Override
        public void changeListTo(String string) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyPerson addDebtToPerson(ReadOnlyPerson target, Debt amount) throws PersonNotFoundException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyPerson deductDebtFromPerson(ReadOnlyPerson target, Debt amount) throws PersonNotFoundException,
                IllegalValueException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public void authenticateUser(Username username, Password password) {
            fail("This method should not be called.");
        }

        @Override
        public void logout() {
            fail("This method should not be called.");
        }

        @Override
        public void updateDebtFromInterest(ReadOnlyPerson person, int differenceInMonths) {
            fail("This method should not be called");
        }

        @Override
        public void setProfilePicsPath(String path) {
            fail("This method should not be called");
        }

        @Override
        public ReadOnlyPerson addProfilePicture(ReadOnlyPerson person) {
            fail("This method should not be called");
            return null;
        }

        @Override
        public ReadOnlyPerson removeProfilePicture(ReadOnlyPerson person) {
            fail("This method should not be called");
            return null;
        }

        @Override
        public ReadOnlyPerson resetDeadlineForPerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called");
            return null;
        }

    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {

        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyPerson getSelectedPerson() {
            return null;
        }

        //@@author jaivigneshvenugopal
        @Override
        public String getCurrentListName() {
            return "list";
        }
        //@@author

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            List<ReadOnlyPerson> list = Arrays.asList(ALICE);
            return list.stream().collect(toCollection(FXCollections::observableArrayList));
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            personsAdded.add(new Person(person));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyPerson getSelectedPerson() {
            return null;
        }

        //@@author jaivigneshvenugopal
        @Override
        public String getCurrentListName() {
            return "list";
        }
        //@@author

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return personsAdded.stream().collect(toCollection(FXCollections::observableArrayList));
        }
    }

}
