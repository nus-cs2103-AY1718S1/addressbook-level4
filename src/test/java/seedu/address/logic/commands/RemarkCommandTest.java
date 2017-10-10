package seedu.address.logic.commands;

import static org.junit.Assert.*;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import com.sun.org.apache.regexp.internal.RE;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RemarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    RemarkCommand remarkCommand = new RemarkCommand();
}