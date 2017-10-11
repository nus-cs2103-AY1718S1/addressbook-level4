package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeDeleteRemarkSuccess() throws Exception {
        Person newRemark = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Likes to code").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, newRemark.getRemark().remark);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), newRemark);

        String expectedSuccessMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK, newRemark);

        assertCommandSuccess(remarkCommand, model, expectedSuccessMessage, expectedModel);
    }

    @Test
    public void executeAddRemarkSuccess() throws Exception {
        Person newRemark = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, newRemark.getRemark().remark);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), newRemark);

        String expectedSuccessMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK, newRemark);

        assertCommandSuccess(remarkCommand, model, expectedSuccessMessage, expectedModel);
    }


    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
