package seedu.address.logic.hints;

import static seedu.address.logic.hints.AddCommandHintTest.assertHintContent;

import org.junit.Test;

import seedu.address.logic.commands.hints.ShareCommandHint;

public class ShareCommandHintTest {

    @Test
    public void shareCommandHint() {
        ShareCommandHint shareCommandHint = new ShareCommandHint("share", "");
        assertHintContent(
                shareCommandHint,
                " 1",
                " index",
                "share 1");

        shareCommandHint = new ShareCommandHint("share ", "");
        assertHintContent(
                shareCommandHint,
                "1",
                " index",
                "share 1");

        shareCommandHint = new ShareCommandHint("share 1", " 1");
        assertHintContent(
                shareCommandHint,
                "",
                " index",
                "share 2");

        shareCommandHint = new ShareCommandHint("share 1 ", " 1");
        assertHintContent(
                shareCommandHint,
                "s/",
                "email or index",
                "share 1 s/");

        shareCommandHint = new ShareCommandHint("share 1 s", " 1 s");
        assertHintContent(
                shareCommandHint,
                "/",
                "email or index",
                "share 1 s/");

        shareCommandHint = new ShareCommandHint("share 1 s/", " 1 s/");
        assertHintContent(
                shareCommandHint,
                "",
                "email or index",
                "share 1 s/");

        shareCommandHint = new ShareCommandHint("share 1 s/s", " 1 s/s");
        assertHintContent(
                shareCommandHint,
                " ",
                "next email or index",
                "share 1 s/s ");

        shareCommandHint = new ShareCommandHint("share 1 s/s ", " 1 s/s ");
        assertHintContent(
                shareCommandHint,
                "",
                "next email or index",
                "share 1 s/s ");

        shareCommandHint = new ShareCommandHint("share 1 s/s s", " 1 s/s s");
        assertHintContent(
                shareCommandHint,
                " ",
                "next email or index",
                "share 1 s/s s ");
    }

}
