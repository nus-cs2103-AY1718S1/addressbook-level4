package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
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
import seedu.address.model.insurance.LifeInsurance;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.insurance.exceptions.DuplicateContractFileNameException;
import seedu.address.model.insurance.exceptions.DuplicateInsuranceException;
import seedu.address.model.insurance.exceptions.InsuranceNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.LifeInsuranceBuilder;

//@@author OscarWang114
public class AddLifeInsuranceCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullLifeInsurance_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddLifeInsuranceCommand(null);
    }

    @Test
    public void execute_lifeInsuranceAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLifeInsuranceAdded modelStub = new ModelStubAcceptingLifeInsuranceAdded();
        LifeInsurance validLifeInsurance = new LifeInsuranceBuilder().build();
        CommandResult commandResult = getAddLifeInsuranceCommandForPerson(validLifeInsurance, modelStub).execute();
        ArrayList<LifeInsurance> arrayList = new ArrayList<>();
        arrayList.add(validLifeInsurance);
        assertEquals(String.format(AddLifeInsuranceCommand.MESSAGE_SUCCESS, validLifeInsurance),
                commandResult.feedbackToUser
        );
        assertEquals(arrayList, modelStub.insurancesAdded);
    }

    @Test
    public void execute_duplicateInsurance_throwsAssertionError() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateInsuranceException();
        LifeInsurance validLifeInsurance = new LifeInsuranceBuilder().build();

        thrown.expect(AssertionError.class);
        thrown.expectMessage(AddLifeInsuranceCommand.MESSAGE_DUPLICATE_INSURANCE);

        getAddLifeInsuranceCommandForPerson(validLifeInsurance, modelStub).execute();
    }

    @Test
    public void execute_duplicateContractFileName_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateContractFileNameException();
        LifeInsurance validLifeInsurance = new LifeInsuranceBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddLifeInsuranceCommand.MESSAGE_DUPLICATE_CONTRACT_FILE_NAME);

        getAddLifeInsuranceCommandForPerson(validLifeInsurance, modelStub).execute();
    }

    @Test
    public void equals() {
        LifeInsurance termLife = new LifeInsuranceBuilder().withInsuranceName("TermLife").build();
        LifeInsurance wholeLife = new LifeInsuranceBuilder().withInsuranceName("WholeLife").build();
        AddLifeInsuranceCommand addTermLifeInsuranceCommand = new AddLifeInsuranceCommand(termLife);
        AddLifeInsuranceCommand addWholeLifeInsuranceCommand = new AddLifeInsuranceCommand(wholeLife);

        // same object -> returns true
        assertTrue(addTermLifeInsuranceCommand.equals(addTermLifeInsuranceCommand));

        // same values -> returns true
        AddLifeInsuranceCommand addTermLifeInsuranceCommandCopy = new AddLifeInsuranceCommand(termLife);
        assertTrue(addTermLifeInsuranceCommand.equals(addTermLifeInsuranceCommandCopy));

        // different types -> returns false
        assertFalse(addTermLifeInsuranceCommand.equals(1));

        // null -> returns false
        assertFalse(addTermLifeInsuranceCommand.equals(null));

        // different person -> returns false
        assertFalse(addTermLifeInsuranceCommand.equals(addWholeLifeInsuranceCommand));
    }

    /**
     * Generates a new AddLifeInsuranceCommand with the details of the given life insurance.
     */
    private AddLifeInsuranceCommand getAddLifeInsuranceCommandForPerson(LifeInsurance lifeInsurance, Model model) {
        AddLifeInsuranceCommand command = new AddLifeInsuranceCommand(lifeInsurance);
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
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addLifeInsurance(ReadOnlyInsurance insurance)
                throws DuplicateInsuranceException, DuplicateContractFileNameException {
            fail("This method should not be called.");
        }

        @Override
        public void updateLifeInsurance(ReadOnlyInsurance target, ReadOnlyInsurance editedOnlyReadInsurance) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ReadOnlyInsurance> getFilteredInsuranceList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredInsuranceList(Predicate<ReadOnlyInsurance> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteInsurance(ReadOnlyInsurance target) throws InsuranceNotFoundException {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a {@code DuplicateLifeInsuranceException} when trying to add a life insurance.
     */
    private class ModelStubThrowingDuplicateInsuranceException extends ModelStub {
        @Override
        public void addLifeInsurance(ReadOnlyInsurance insurance)
                throws DuplicateInsuranceException, DuplicateContractFileNameException {
            throw new DuplicateInsuranceException();
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return new UniquePersonList().asObservableList();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always throw a {@code DuplicateContractFileNameException} when trying to add a life insurance.
     */
    private class ModelStubThrowingDuplicateContractFileNameException extends ModelStub {
        @Override
        public void addLifeInsurance(ReadOnlyInsurance insurance)
                throws DuplicateInsuranceException, DuplicateContractFileNameException {
            throw new DuplicateContractFileNameException();
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return new UniquePersonList().asObservableList();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the life insurance being added.
     */
    private class ModelStubAcceptingLifeInsuranceAdded extends ModelStub {
        final ArrayList<LifeInsurance> insurancesAdded = new ArrayList<>();

        @Override
        public void addLifeInsurance(ReadOnlyInsurance insurance) throws
                DuplicateInsuranceException, DuplicateContractFileNameException {
            insurancesAdded.add(new LifeInsurance(insurance));
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return new UniquePersonList().asObservableList();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
