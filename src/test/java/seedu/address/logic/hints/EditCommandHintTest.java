package seedu.address.logic.hints;

import static seedu.address.logic.hints.AddCommandHintTest.parseAndAssertHint;

import org.junit.Test;

import seedu.address.logic.commands.hints.EditCommandHint;

public class EditCommandHintTest {

    @Test
    public void parseTest() {
        //offer index
        EditCommandHint editCommandHint = new EditCommandHint("edit", "");
        parseAndAssertHint(editCommandHint, " 1", " index", "edit 1");

        //offer hint
        editCommandHint = new EditCommandHint("edit 5 ", " 5 ");
        parseAndAssertHint(editCommandHint, "n/", "name", "edit 5 n/");
        editCommandHint = new EditCommandHint("edit 1 n/nicholas p/321 e/email@e.com a/address",
                " 1 n/nicholas p/321 e/email@e.com a/address");
        parseAndAssertHint(editCommandHint, " t/", "tag",
                "edit 1 n/nicholas p/321 e/email@e.com a/address t/");

        //index cycle
        editCommandHint = new EditCommandHint("edit 5", " 5");
        parseAndAssertHint(editCommandHint, "", " index", "edit 6");


        //prefix completion
        editCommandHint = new EditCommandHint("edit 5 n", " 5 n");
        parseAndAssertHint(editCommandHint, "/", "name", "edit 5 n/");
        editCommandHint = new EditCommandHint("edit 3 n/ ", " 3 n/ ");
        parseAndAssertHint(editCommandHint, "p/", "phone", "edit 3 n/ p/");
        editCommandHint = new EditCommandHint("edit 2 p", " 2 p");
        parseAndAssertHint(editCommandHint, "/", "phone", "edit 2 p/");
        editCommandHint = new EditCommandHint("edit 4 e", " 4 e");
        parseAndAssertHint(editCommandHint, "/", "email", "edit 4 e/");

        //prefix cycle
        editCommandHint = new EditCommandHint("edit 1 t/", " 1 t/");
        parseAndAssertHint(editCommandHint, "", "tag", "edit 1 n/");
        editCommandHint = new EditCommandHint("edit 3 a/", " 3 a/");
        parseAndAssertHint(editCommandHint, "", "address", "edit 3 t/");

        //exhausted all prefix
        editCommandHint = new EditCommandHint("edit 2 n/nicholas p/321 e/email@e.com a/address t/tag",
                " 2 n/nicholas p/321 e/email@e.com a/address t/tag");
        parseAndAssertHint(editCommandHint, " ", "", "edit 2 n/nicholas p/321 e/email@e.com a/address t/tag ");
    }


}
