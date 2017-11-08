//@@author Hailinx
package seedu.address.logic.parser.optionparser;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class TodoOptionDeleteOneTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private Index index = Index.fromOneBased(1);

    @Test
    public void test_parseEmptyArg_throwException() throws Exception {
        thrown.expect(ParseException.class);
        new TodoOptionDeleteOne("", index).parse();
    }

    @Test
    public void test_parseSuccess() throws Exception {
        TodoCommand todoCommand = new TodoOptionDeleteOne("5", index).parse();
        TodoCommand expectedCommand =
                new TodoCommand(TodoCommand.PREFIX_TODO_DELETE_ONE, index, null, Index.fromOneBased(5));

        Assert.assertTrue(todoCommand.equals(expectedCommand));
    }

    @Test
    public void test_parseInvalidArgs_throwException() throws Exception {
        thrown.expect(ParseException.class);
        new TodoOptionDeleteOne("a", index).parse();
    }

    @Test
    public void test_parseInvalidIndex_throwException() throws Exception {
        thrown.expect(ParseException.class);
        new TodoOptionDeleteOne("0", index).parse();
    }

}
