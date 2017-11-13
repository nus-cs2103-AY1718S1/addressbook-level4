//@@author Hailinx
package seedu.address.logic.parser.optionparser;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class TodoOptionDeleteAllTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private Index index = Index.fromOneBased(1);

    @Test
    public void test_parseEmptyArg_success() throws Exception {
        TodoCommand todoCommand = new TodoOptionDeleteAll(" ", index).parse();
        TodoCommand expectedCommand = new TodoCommand(TodoCommand.PREFIX_TODO_DELETE_ALL, index, null, null);

        Assert.assertTrue(todoCommand.equals(expectedCommand));
    }

    @Test
    public void test_parseNotEmptyArg_throwsException() throws Exception {
        thrown.expect(ParseException.class);
        new TodoOptionDeleteAll("1", index).parse();
    }

}
