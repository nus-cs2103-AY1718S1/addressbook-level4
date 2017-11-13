package seedu.address.ui.testutil;

import static org.junit.Assert.fail;

import javafx.collections.ObservableList;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * This class gives a stub of Logic for use in UI components that depend on Logic.
 */
public class LogicStub implements Logic {

    // @@author donjar

    private ObservableList<ReadOnlyPerson> persons;

    public LogicStub() {
        this.persons = null;
    }

    public LogicStub(ObservableList<ReadOnlyPerson> persons) {
        this.persons = persons;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public ObservableList<ReadOnlyPerson> getAllPersonList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        if (persons == null) {
            fail("This method should not be called.");
        }

        return persons;
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public int getFontSizeChange() {
        return 0;
    }
}
