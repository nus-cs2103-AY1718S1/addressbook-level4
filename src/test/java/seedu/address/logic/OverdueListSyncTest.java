package seedu.address.logic;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OVERDUE_PERSONS;
import static seedu.address.model.util.DateUtil.formatDate;
import static seedu.address.testutil.TypicalPersons.getSizeOfTypicalOverdueListPersons;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.OverdueListCommand;
import seedu.address.logic.commands.PaybackCommand;
import seedu.address.logic.commands.RepaidCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DateRepaid;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

//@@author lawwman
public class OverdueListSyncTest {

    private Model model;
    private final String expectedMessage = ListObserver.OVERDUELIST_NAME_DISPLAY_FORMAT
            + OverdueListCommand.MESSAGE_SUCCESS;

    @Before
    public void setUp() {
        //assumes that the typicalAddressBook has at least 1 person in the overdue list.
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ListObserver.init(model);
    }

    @Test
    public void execute_deleteCommandOnMasterListDeletesPersonFromOverdueList_success() throws Exception {
        int numberOfOverduePersons = getSizeOfTypicalOverdueListPersons();
        assertEquals(numberOfOverduePersons, model
                .updateFilteredOverduePersonList(PREDICATE_SHOW_ALL_OVERDUE_PERSONS));
        ReadOnlyPerson personInOverdueList = model.getFilteredOverduePersonList().get(0);
        Index personToTestIdx = Index.fromZeroBased(model.getFilteredPersonList().indexOf(personInOverdueList));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.deletePerson(personInOverdueList);
        expectedModel.updateFilteredOverduePersonList(PREDICATE_SHOW_ALL_OVERDUE_PERSONS);
        expectedModel.setCurrentListName("overduelist");

        DeleteCommand deleteCommand = prepareDeleteCommand(personToTestIdx);
        deleteCommand.execute();

        OverdueListCommand overdueListCommand = prepareOverdueListCommand();
        model.setCurrentListName("overduelist");

        assertCommandSuccess(overdueListCommand, model, expectedMessage, expectedModel);
        assertEquals(numberOfOverduePersons - 1,
                model.updateFilteredOverduePersonList(PREDICATE_SHOW_ALL_OVERDUE_PERSONS));
    }

    @Test
    public void execute_editCommandOnMasterListRemovesPersonFromOverdueList_success() throws Exception {
        int numberOfOverduePersons = getSizeOfTypicalOverdueListPersons();
        assertEquals(numberOfOverduePersons, model
                .updateFilteredOverduePersonList(PREDICATE_SHOW_ALL_OVERDUE_PERSONS));

        Person personInOverdueList = (Person) model.getFilteredOverduePersonList().get(0);
        Index personToTestIdx = Index.fromZeroBased(model.getFilteredPersonList().indexOf(personInOverdueList));

        Person editedPerson = new PersonBuilder(personInOverdueList)
                .withDeadline(prepareFutureDeadlineInput()).build();

        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        editedPerson.setHasOverdueDebt(false);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personInOverdueList, editedPerson);
        expectedModel.setCurrentListName("overduelist");

        EditCommand editCommand = prepareEditCommand(personToTestIdx, descriptor);
        editCommand.execute();

        OverdueListCommand overdueListCommand = prepareOverdueListCommand();
        model.setCurrentListName("overduelist");

        assertCommandSuccess(overdueListCommand, model, expectedMessage, expectedModel);
        assertEquals(numberOfOverduePersons - 1,
                model.updateFilteredOverduePersonList(PREDICATE_SHOW_ALL_OVERDUE_PERSONS));
    }

    @Test
    public void execute_repaidCommandOnMasterListRemovesPersonFromOverdueList_success() throws Exception {
        int numberOfOverduePersons = getSizeOfTypicalOverdueListPersons();
        assertEquals(numberOfOverduePersons, model
                .updateFilteredOverduePersonList(PREDICATE_SHOW_ALL_OVERDUE_PERSONS));

        Person personInOverdueList = (Person) model.getFilteredOverduePersonList().get(0);
        Index index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(personInOverdueList));

        Person expectedPerson = new PersonBuilder(personInOverdueList).withDebt("0.00")
                .withDeadline(Deadline.NO_DEADLINE_SET).build();
        expectedPerson.setDateRepaid(new DateRepaid(formatDate(new Date())));
        expectedPerson.setHasOverdueDebt(false);
        expectedPerson.setIsWhitelisted(true);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personInOverdueList, expectedPerson);
        expectedModel.setCurrentListName("overduelist");

        RepaidCommand repaidCommand = prepareRepaidCommand(index);
        repaidCommand.execute();
        model.getFilteredOverduePersonList();

        OverdueListCommand overdueListCommand = prepareOverdueListCommand();
        model.setCurrentListName("overduelist");

        assertCommandSuccess(overdueListCommand, model, expectedMessage, expectedModel);
        assertEquals(numberOfOverduePersons - 1,
                model.updateFilteredOverduePersonList(PREDICATE_SHOW_ALL_OVERDUE_PERSONS));
    }

    @Test
    public void execute_paybackCommandOnMasterListRemovesPersonFromOverdueList_success() throws Exception {
        int numberOfOverduePersons = getSizeOfTypicalOverdueListPersons();
        assertEquals(numberOfOverduePersons, model
                .updateFilteredOverduePersonList(PREDICATE_SHOW_ALL_OVERDUE_PERSONS));

        Person personInOverdueList = (Person) model.getFilteredOverduePersonList().get(0);
        Index index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(personInOverdueList));

        Person expectedPerson = new PersonBuilder(personInOverdueList).withDebt("0.00")
                .withDeadline(Deadline.NO_DEADLINE_SET).build();
        expectedPerson.setDateRepaid(new DateRepaid(formatDate(new Date())));
        expectedPerson.setHasOverdueDebt(false);
        expectedPerson.setIsWhitelisted(true);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personInOverdueList, expectedPerson);
        expectedModel.setCurrentListName("overduelist");

        PaybackCommand paybackCommand = preparePaybackCommand(index, personInOverdueList.getDebt());
        paybackCommand.execute();
        model.getFilteredOverduePersonList();

        OverdueListCommand overdueListCommand = prepareOverdueListCommand();
        model.setCurrentListName("overduelist");

        assertCommandSuccess(overdueListCommand, model, expectedMessage, expectedModel);
        assertEquals(numberOfOverduePersons - 1,
                model.updateFilteredOverduePersonList(PREDICATE_SHOW_ALL_OVERDUE_PERSONS));
    }

    /**
     * @return {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareDeleteCommand(Index index) throws CommandException {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    /**
     * @return {@code OverdueListCommand}.
     */
    private OverdueListCommand prepareOverdueListCommand() {
        OverdueListCommand overdueListCommand = new OverdueListCommand();
        overdueListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return overdueListCommand;
    }

    /**
     * @return {@code EditCommand} with the parameter {@code index} & {@code EditPersonDescriptor}.
     */
    private EditCommand prepareEditCommand(Index index, EditCommand.EditPersonDescriptor descriptor) throws
            CommandException {
        EditCommand editCommand = new EditCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }

    /**
     * Returns a {@code PaybackCommand} with the parameter {@code index} & {@code amount}.
     */
    private PaybackCommand preparePaybackCommand(Index index, Debt amount) throws CommandException {
        PaybackCommand paybackCommand = new PaybackCommand(index, amount);
        paybackCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return paybackCommand;
    }

    /**
     * Returns a {@code RepaidCommand} with the parameter {@code index}.
     */
    private RepaidCommand prepareRepaidCommand(Index index) throws CommandException {
        RepaidCommand repaidCommand = new RepaidCommand(index);
        repaidCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return repaidCommand;
    }

    /**
     * @return a String that represents a user's input for deadline.
     */
    private String prepareFutureDeadlineInput() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, +1);
        SimpleDateFormat ft = new SimpleDateFormat("dd'-'MM'-'yyyy");
        String deadlineInput = ft.format(cal.getTime());
        return deadlineInput;
    }
}
