package seedu.address.logic;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WHITELISTED_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BorrowCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.PaybackCommand;
import seedu.address.logic.commands.RepaidCommand;
import seedu.address.logic.commands.UnbanCommand;
import seedu.address.logic.commands.WhitelistCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class WhitelistSyncTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_deleteCommandOnMasterlistDeletesPersonFromWhitelist_success() throws Exception {

        ReadOnlyPerson personToBeDeleted = model.getFilteredWhitelistedPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(personToBeDeleted));

        String expectedMessage = WhitelistCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // Ensure person is deleted from masterlist
        expectedModel.deletePerson(personToBeDeleted);
        expectedModel.updateFilteredWhitelistedPersonList(PREDICATE_SHOW_ALL_WHITELISTED_PERSONS);

        DeleteCommand deleteCommand = prepareDeleteCommand(index);
        deleteCommand.execute();

        WhitelistCommand whitelistCommand = prepareWhitelistCommand();

        assertCommandSuccess(whitelistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_borrowCommandRemovesPersonFromWhitelist_success() throws Exception {

        ReadOnlyPerson borrowedPerson = model.getFilteredWhitelistedPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(borrowedPerson));
        Debt amount = new Debt("500");

        String expectedMessage = WhitelistCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // To make sure person does not exist in whitelist anymore
        borrowedPerson = expectedModel.removeWhitelistedPerson(borrowedPerson);
        expectedModel.addDebtToPerson(borrowedPerson, amount);

        BorrowCommand borrowCommand = prepareBorrowCommand(index, amount);
        borrowCommand.execute();

        WhitelistCommand whitelistCommand = prepareWhitelistCommand();

        assertCommandSuccess(whitelistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_paybackCommandRepayExactDebtAddsPersonIntoWhitelist_success() throws Exception {

        ReadOnlyPerson repayingPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Debt amount = repayingPerson.getDebt();

        String expectedMessage = WhitelistCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // To ensure person exists in whitelist
        repayingPerson = expectedModel.deductDebtFromPerson(repayingPerson, amount);
        expectedModel.addWhitelistedPerson(repayingPerson);

        PaybackCommand paybackCommand = preparePaybackCommand(INDEX_FIRST_PERSON, amount);
        paybackCommand.execute();

        WhitelistCommand whitelistCommand = prepareWhitelistCommand();

        assertCommandSuccess(whitelistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_paybackCommandRepayInexactDebtDoesNotAddPersonIntoWhitelist_success() throws Exception {

        ReadOnlyPerson repayingPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Debt amount = new Debt(0.5 * (repayingPerson.getDebt().toNumber()));

        String expectedMessage = WhitelistCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // To ensure person only gets his debt decremented. No other actions
        expectedModel.deductDebtFromPerson(repayingPerson, amount);

        PaybackCommand paybackCommand = preparePaybackCommand(INDEX_FIRST_PERSON, amount);
        paybackCommand.execute();

        WhitelistCommand whitelistCommand = prepareWhitelistCommand();

        assertCommandSuccess(whitelistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_paybackCommandRepayExactDebtDoesNotAddBlacklistedPersonIntoWhitelist_success()
            throws Exception {

        ReadOnlyPerson repayingPerson = model.getFilteredBlacklistedPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(repayingPerson));
        Debt amount = repayingPerson.getDebt();

        String expectedMessage = WhitelistCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        repayingPerson = expectedModel.deductDebtFromPerson(repayingPerson, amount);

        // To make sure person does not exist in whitelist
        expectedModel.removeWhitelistedPerson(repayingPerson);

        PaybackCommand paybackCommand = preparePaybackCommand(index, amount);
        paybackCommand.execute();

        WhitelistCommand whitelistCommand = prepareWhitelistCommand();

        assertCommandSuccess(whitelistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editCommandAddDebtRemovesPersonFromWhitelist_success() throws Exception {

        ReadOnlyPerson borrowedPerson = model.getFilteredWhitelistedPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(borrowedPerson));
        Debt amount = new Debt("500");

        Person editedPerson = new PersonBuilder(borrowedPerson).withDebt(amount.toString()).build();

        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        descriptor.setDebt(amount);

        String expectedMessage = WhitelistCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // To ensure person does not exist in whitelist
        expectedModel.updatePerson(borrowedPerson, editedPerson);
        expectedModel.removeWhitelistedPerson(editedPerson);

        EditCommand editCommand = prepareEditCommand(index, descriptor);
        editCommand.execute();

        WhitelistCommand whitelistCommand = prepareWhitelistCommand();

        assertCommandSuccess(whitelistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editCommandRepayExactDebtAddsPersonIntoWhitelist_success() throws Exception {

        ReadOnlyPerson repayingPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Debt amount = new Debt("0");

        Person editedPerson = new PersonBuilder(repayingPerson).withDebt(amount.toString()).build();

        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        descriptor.setDebt(amount);

        String expectedMessage = WhitelistCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // To ensure person exists in whitelist
        expectedModel.updatePerson(repayingPerson, editedPerson);
        expectedModel.addWhitelistedPerson(editedPerson);

        EditCommand editCommand = prepareEditCommand(INDEX_FIRST_PERSON, descriptor);
        editCommand.execute();

        WhitelistCommand whitelistCommand = prepareWhitelistCommand();

        assertCommandSuccess(whitelistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editCommandRepayInexactDebtDoesNotAddPersonIntoWhitelist_success() throws Exception {

        ReadOnlyPerson repayingPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Debt amount = new Debt(0.5 * (repayingPerson.getDebt().toNumber()));

        Person editedPerson = new PersonBuilder(repayingPerson).withDebt(amount.toString()).build();

        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        descriptor.setDebt(amount);

        String expectedMessage = WhitelistCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(repayingPerson, editedPerson);

        EditCommand editCommand = prepareEditCommand(INDEX_FIRST_PERSON, descriptor);
        editCommand.execute();

        WhitelistCommand whitelistCommand = prepareWhitelistCommand();

        assertCommandSuccess(whitelistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editCommandRemoveExactDebtDoesNotAddBlacklistedPersonIntoWhitelist_success()
            throws Exception {

        ReadOnlyPerson repayingPerson = model.getFilteredBlacklistedPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(repayingPerson));
        Debt amount = new Debt("0");

        Person editedPerson = new PersonBuilder(repayingPerson).withDebt(amount.toString()).build();
        editedPerson.setIsWhitelisted(false);

        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        descriptor.setDebt(amount);

        String expectedMessage = WhitelistCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(repayingPerson, editedPerson);

        EditCommand editCommand = prepareEditCommand(index, descriptor);
        editCommand.execute();

        WhitelistCommand whitelistCommand = prepareWhitelistCommand();

        assertCommandSuccess(whitelistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unbanCommandAddsUnBlacklistedPersonIntoWhitelistIfDebtCleared_success()
            throws Exception {

        ReadOnlyPerson unbannedPerson = model.getFilteredBlacklistedPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(unbannedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        RepaidCommand repaidCommand = prepareRepaidCommand(index);
        repaidCommand.execute();

        model.updateFilteredBlacklistedPersonList(PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS);

        UnbanCommand unbanCommand = prepareUnbanCommand(INDEX_FIRST_PERSON);
        unbanCommand.execute();

        // addWhitelistedPerson() method will set debt to zero and
        // Generates new date repaid.
        // Does not add person into whitelist as he is blacklisted
        unbannedPerson = expectedModel.addWhitelistedPerson(unbannedPerson);

        // removeBlacklistedPerson() now unbans the person
        unbannedPerson = expectedModel.removeBlacklistedPerson(unbannedPerson);

        // Person will be added to whitelisted as he is now unbanned
        expectedModel.addWhitelistedPerson(unbannedPerson);

        String expectedMessage = WhitelistCommand.MESSAGE_SUCCESS;

        WhitelistCommand whitelistCommand = prepareWhitelistCommand();

        assertCommandSuccess(whitelistCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code BorrowCommand} with the parameter {@code index} & {@code amount}.
     */
    private BorrowCommand prepareBorrowCommand(Index index, Debt amount) {
        BorrowCommand borrowCommand = new BorrowCommand(index, amount);
        borrowCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return borrowCommand;
    }

    /**
     * Returns a {@code EditCommand} with the parameter {@code index} & {@code EditPersonDescriptor}.
     */
    private EditCommand prepareEditCommand(Index index, EditCommand.EditPersonDescriptor descriptor) {
        EditCommand editCommand = new EditCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }

    /**
     * Returns a {@code WhitelistCommand}.
     */
    private WhitelistCommand prepareWhitelistCommand() {
        WhitelistCommand whitelistCommand = new WhitelistCommand();
        whitelistCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return whitelistCommand;
    }

    /**
     * Returns a {@code RepaidCommand} with the parameter {@code index}.
     */
    private RepaidCommand prepareRepaidCommand(Index index) {
        RepaidCommand repaidCommand = new RepaidCommand(index);
        repaidCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return repaidCommand;
    }

    /**
     * Returns a {@code UnbanCommand} with the parameter {@code index}.
     */
    private UnbanCommand prepareUnbanCommand(Index index) {
        UnbanCommand unbanCommand = new UnbanCommand(index);
        unbanCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unbanCommand;
    }

    /**
     * Returns a {@code PaybackCommand} with the parameter {@code index} & {@code amount}.
     */
    private PaybackCommand preparePaybackCommand(Index index, Debt amount) {
        PaybackCommand paybackCommand = new PaybackCommand(index, amount);
        paybackCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return paybackCommand;
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code payingPerson}.
     */
    private DeleteCommand prepareDeleteCommand(Index index) {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }
}
