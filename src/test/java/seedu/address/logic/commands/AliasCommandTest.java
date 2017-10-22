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
import seedu.address.model.alias.AliasToken;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.alias.exceptions.DuplicateTokenKeywordException;
import seedu.address.model.alias.exceptions.TokenKeywordNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.AliasTokenBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AliasCommand.
 */
public class AliasCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullAliasToken_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AliasCommand(null);
    }

    @Test
    public void execute_aliasAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAliasTokenAdded modelStub = new ModelStubAcceptingAliasTokenAdded();
        AliasToken validAliasToken = new AliasTokenBuilder().build();

        CommandResult commandResult = getAliasCommandForAliasToken(validAliasToken, modelStub).execute();

        assertEquals(String.format(AliasCommand.MESSAGE_SUCCESS, validAliasToken), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAliasToken), modelStub.aliasTokensAdded);
    }

    @Test
    public void execute_duplicateAliasToken_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateAliasTokenException();
        AliasToken validAliasToken = new AliasTokenBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AliasCommand.MESSAGE_DUPLICATE_ALIAS);

        getAliasCommandForAliasToken(validAliasToken, modelStub).execute();
    }

    @Test
    public void equals() {
        AliasToken ph = new AliasTokenBuilder().withKeyword("ph").build();
        AliasToken st = new AliasTokenBuilder().withKeyword("st").build();
        AliasCommand aliasPhCommand = new AliasCommand(ph);
        AliasCommand aliasStCommand = new AliasCommand(st);

        // same object -> returns true
        assertTrue(aliasPhCommand.equals(aliasPhCommand));

        // same values -> returns true
        AliasCommand aliasPhCommandCopy = new AliasCommand(ph);
        assertTrue(aliasPhCommand.equals(aliasPhCommandCopy));

        // different types -> returns false
        assertFalse(aliasPhCommand.equals(1));

        // null -> returns false
        assertFalse(aliasPhCommand == null);

        // different AliasToken -> returns false
        assertFalse(aliasPhCommand.equals(aliasStCommand));
    }

    /**
     * Generates a new AliasCommand with the details of the given AliasToken
     */
    private AliasCommand getAliasCommandForAliasToken(AliasToken aliasToken, Model model) {
        AliasCommand command = new AliasCommand(aliasToken);
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
        public void sortList(String toSort) {
            fail("This method should not be called.");
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void pinPerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void unpinPerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void hidePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addAliasToken(ReadOnlyAliasToken target) throws DuplicateTokenKeywordException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteAliasToken(ReadOnlyAliasToken target) throws TokenKeywordNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public int getAliasTokenCount() {
            fail("This method should not be called.");
            return 0;
        }

        @Override
        public ObservableList<ReadOnlyAliasToken> getFilteredAliasTokenList() {
            fail("This method should not be called.");
            return null;
        }
    }

    /**
     * A Model stub that always throw a DuplicateTokenKeywordException when trying to add an alias.
     */
    private class ModelStubThrowingDuplicateAliasTokenException extends ModelStub {
        @Override
        public void addAliasToken(ReadOnlyAliasToken aliasToken) throws DuplicateTokenKeywordException {
            throw new DuplicateTokenKeywordException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accepts the AliasToken being added
     */
    private class ModelStubAcceptingAliasTokenAdded extends ModelStub {
        private final ArrayList<AliasToken> aliasTokensAdded = new ArrayList<>();

        @Override
        public void addAliasToken(ReadOnlyAliasToken aliasToken) throws DuplicateTokenKeywordException {
            aliasTokensAdded.add(new AliasToken(aliasToken));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
