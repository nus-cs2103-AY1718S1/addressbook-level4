# sunarjo-denny
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.user.UserCreds;
import seedu.address.model.user.UserPrefs;

public class SortCommandTest {
    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserCreds());
        model.getUserCreds().validateCurrentSession(); // validate user
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());
        expectedModel.getUserCreds().validateCurrentSession(); // validate user

        sortCommand = new SortCommand(SortCommand.PREFIX_NAME, false);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_defaultSortCommand_unfilteredList() {
        assertCommandSuccess(sortCommand, model, String.format(sortCommand.MESSAGE_SUCCESS, SortCommand.PREFIX_NAME,
                "ascending"), expectedModel);
    }

    @Test
    public void execute_defaultSortCommand_filteredList() {
        showFirstPersonOnly(model);
        assertCommandSuccess(sortCommand, model, String.format(sortCommand.MESSAGE_SUCCESS, SortCommand.PREFIX_NAME,
                "ascending"), expectedModel);
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

/**
 * To test that sort command parses correctly
 */
public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "address",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "email",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {
    @Test
    public void isValidBirthday() {
        // invalid birthday
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("01/01/1995")); // DD/MM/YYYY format
        assertFalse(Birthday.isValidBirthday("01-01-1995")); // DD-MM-YYYY format
        assertFalse(Birthday.isValidBirthday("95/01/01")); // YY/MM/DD format

        // valid birthday
        assertTrue(Birthday.isValidBirthday("1995/10/10")); // YYYY/MM/DD
    }
}
```
