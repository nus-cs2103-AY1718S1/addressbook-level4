package seedu.address.logic.hints;

import static seedu.address.logic.hints.AddCommandHintTest.parseAndAssertHint;

import org.junit.Test;

import seedu.address.logic.commands.hints.FindCommandHint;

public class FindCommandHintTest {

    @Test
    public void parseTest() {
        //offer hint
        FindCommandHint findCommandHint = new FindCommandHint("find", "");
        parseAndAssertHint(findCommandHint, " n/", "name", "find n/");
        findCommandHint = new FindCommandHint("find n/ ", " n/ ");
        parseAndAssertHint(findCommandHint, "p/", "phone", "find n/ p/");
        findCommandHint = new FindCommandHint("find n/nicholas p/321 e/email@e.com a/address",
                " n/nicholas p/321 e/email@e.com a/address");
        parseAndAssertHint(findCommandHint, " r/", "remark",
                "find n/nicholas p/321 e/email@e.com a/address r/");
        //prefix completion
        findCommandHint = new FindCommandHint("find n", " n");
        parseAndAssertHint(findCommandHint, "/", "name", "find n/");

        findCommandHint = new FindCommandHint("find p", " p");
        parseAndAssertHint(findCommandHint, "/", "phone", "find p/");
        findCommandHint = new FindCommandHint("find e", " e");
        parseAndAssertHint(findCommandHint, "/", "email", "find e/");

        //prefix cycle
        findCommandHint = new FindCommandHint("find r/", " r/");
        parseAndAssertHint(findCommandHint, "", "remark", "find t/");
        findCommandHint = new FindCommandHint("find a/", " a/");
        parseAndAssertHint(findCommandHint, "", "address", "find r/");

        //exhausted all prefix
        findCommandHint = new FindCommandHint("find n/nicholas p/321 e/email@e.com a/address t/tag r/remark",
                " n/nicholas p/321 e/email@e.com a/address t/tag r/remark");
        parseAndAssertHint(findCommandHint, " ", "", "find n/nicholas p/321 e/email@e.com a/address t/tag r/remark ");
    }
}
