package seedu.address.logic.hints;

import static seedu.address.logic.hints.AddCommandHintTest.parseAndAssertHint;

import org.junit.Test;

import seedu.address.logic.commands.hints.DeleteCommandHint;

public class DeleteCommandHintTest {

    @Test
    public void parseTest() {
        //offer index
        DeleteCommandHint deleteCommandHint = new DeleteCommandHint("delete", "");
        parseAndAssertHint(deleteCommandHint, " 1", " index", "delete 1");

        deleteCommandHint = new DeleteCommandHint("delete ", " ");
        parseAndAssertHint(deleteCommandHint, "1", " index", "delete 1");
        //index cycle
        deleteCommandHint = new DeleteCommandHint("delete 5", " 5");
        parseAndAssertHint(deleteCommandHint, "", " index", "delete 6");

        //delete is used for select. TODO: give select command its own hint

        deleteCommandHint = new DeleteCommandHint("select", "");
        parseAndAssertHint(deleteCommandHint, " 1", " index", "select 1");

        deleteCommandHint = new DeleteCommandHint("select ", " ");
        parseAndAssertHint(deleteCommandHint, "1", " index", "select 1");

    }
}
