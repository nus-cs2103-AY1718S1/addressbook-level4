package seedu.address.logic.hints;

import static seedu.address.logic.hints.AddCommandHintTest.assertHintContent;

import org.junit.Test;

import seedu.address.logic.commands.hints.CommandHint;

public class CommandHintTest {

    @Test
    public void commandHintTest() {
        CommandHint commandHint = new CommandHint("a", "a");
        assertHintContent(
                commandHint,
                "dd ",
                "adds a person",
                "add ");

        commandHint = new CommandHint("ad", "ad");
        assertHintContent(
                commandHint,
                "d ",
                "adds a person",
                "add ");

        commandHint = new CommandHint(" ad ", "ad");
        assertHintContent(
                commandHint,
                "d ",
                "adds a person",
                "add ");

        commandHint = new CommandHint("find", "find");
        assertHintContent(
                commandHint,
                " ",
                "finds a person",
                "find ");

        commandHint = new CommandHint("j", "j");
        assertHintContent(
                commandHint,
                "",
                " type help for user guide",
                "j");

        commandHint = new CommandHint("edit", "edit");
        assertHintContent(
                commandHint,
                " ",
                "edits a person",
                "edit ");

        commandHint = new CommandHint("select", "select");
        assertHintContent(
                commandHint,
                " ",
                "selects a person",
                "select ");

        commandHint = new CommandHint("share", "share");
        assertHintContent(
                commandHint,
                " ",
                "shares a contact via email",
                "share ");

        commandHint = new CommandHint("clear", "clear");
        assertHintContent(
                commandHint,
                " ",
                "clears all contacts",
                "clear ");

        commandHint = new CommandHint("history", "history");
        assertHintContent(
                commandHint,
                " ",
                "shows command history",
                "history ");

        commandHint = new CommandHint("exit", "exit");
        assertHintContent(
                commandHint,
                " ",
                "exits the application",
                "exit ");

        commandHint = new CommandHint("undo", "undo");
        assertHintContent(
                commandHint,
                " ",
                "undo previous command",
                "undo ");

        commandHint = new CommandHint("redo", "redo");
        assertHintContent(
                commandHint,
                " ",
                "redo command",
                "redo ");

        commandHint = new CommandHint("help", "help");
        assertHintContent(
                commandHint,
                " ",
                "shows user guide",
                "help ");

        commandHint = new CommandHint("music", "music");
        assertHintContent(
                commandHint,
                " ",
                "plays music",
                "music ");

        commandHint = new CommandHint("radio", "radio");
        assertHintContent(
                commandHint,
                " ",
                "plays the radio",
                "radio ");

        commandHint = new CommandHint("alias", "alias");
        assertHintContent(
                commandHint,
                " ",
                "sets or show alias",
                "alias ");

        commandHint = new CommandHint("unalias", "unalias");
        assertHintContent(
                commandHint,
                " ",
                "removes alias",
                "unalias ");

        commandHint = new CommandHint("unknown", "unknown");
        assertHintContent(
                commandHint,
                "",
                " type help for user guide",
                "unknown");
    }


}
