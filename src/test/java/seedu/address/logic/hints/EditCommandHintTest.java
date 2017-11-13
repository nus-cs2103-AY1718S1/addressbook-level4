package seedu.address.logic.hints;

import static seedu.address.logic.hints.AddCommandHintTest.assertHintContent;

import org.junit.Test;

import seedu.address.logic.commands.hints.EditCommandHint;

public class EditCommandHintTest {

    @Test
    public void parseTest() {
        //offer index
        EditCommandHint editCommandHint = new EditCommandHint("edit", "");
        assertHintContent(editCommandHint, " 1", " index", "edit 1");

        //offer hint
        editCommandHint = new EditCommandHint("edit 5 ", " 5 ");
        assertHintContent(editCommandHint, "n/", "name", "edit 5 n/");
        editCommandHint = new EditCommandHint("edit 1 n/nicholas p/321 e/email@e.com a/address",
                " 1 n/nicholas p/321 e/email@e.com a/address");
        assertHintContent(editCommandHint, " r/", "remark",
                "edit 1 n/nicholas p/321 e/email@e.com a/address r/");

        //index cycle
        editCommandHint = new EditCommandHint("edit 5", " 5");
        assertHintContent(editCommandHint, "", " index", "edit 6");


        //prefix completion
        editCommandHint = new EditCommandHint("edit 5 n", " 5 n");
        assertHintContent(editCommandHint, "/", "name", "edit 5 n/");
        editCommandHint = new EditCommandHint("edit 3 n/ ", " 3 n/ ");
        assertHintContent(editCommandHint, "p/", "phone", "edit 3 n/ p/");
        editCommandHint = new EditCommandHint("edit 2 p", " 2 p");
        assertHintContent(editCommandHint, "/", "phone", "edit 2 p/");
        editCommandHint = new EditCommandHint("edit 4 e", " 4 e");
        assertHintContent(editCommandHint, "/", "email", "edit 4 e/");

        //prefix cycle
        editCommandHint = new EditCommandHint("edit 1 t/", " 1 t/");
        assertHintContent(editCommandHint, "", "tag", "edit 1 n/");
        editCommandHint = new EditCommandHint("edit 3 r/", " 3 r/");
        assertHintContent(editCommandHint, "", "remark", "edit 3 t/");

        //exhausted all prefix
        editCommandHint = new EditCommandHint("edit 2 n/nicholas p/321 e/email@e.com a/address t/tag r/remark",
                " 2 n/nicholas p/321 e/email@e.com a/address t/tag r/remark");
        assertHintContent(editCommandHint, " ", "", "edit 2 n/nicholas p/321 e/email@e.com a/address t/tag r/remark ");
    }


}
