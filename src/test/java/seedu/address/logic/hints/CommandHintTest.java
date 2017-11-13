package seedu.address.logic.hints;

import static seedu.address.logic.hints.AddCommandHintTest.parseAndAssertHint;

import org.junit.Test;

import seedu.address.logic.commands.hints.CommandHint;

public class CommandHintTest {

    @Test
    public void commandHintTest() {
        CommandHint commandHint = new CommandHint("a", "a");
        parseAndAssertHint(
                commandHint,
                "dd ",
                "adds a person",
                "add ");

        commandHint = new CommandHint("ad", "ad");
        parseAndAssertHint(
                commandHint,
                "d ",
                "adds a person",
                "add ");

        commandHint = new CommandHint(" ad ", "ad");
        parseAndAssertHint(
                commandHint,
                "d ",
                "adds a person",
                "add ");

        commandHint = new CommandHint("find", "find");
        parseAndAssertHint(
                commandHint,
                " ",
                "finds a person",
                "find ");

        commandHint = new CommandHint("j", "j");
        parseAndAssertHint(
                commandHint,
                "",
                " type help for user guide",
                "j");

        commandHint = new CommandHint("edit", "edit");
        parseAndAssertHint(
                commandHint,
                " ",
                "edits a person",
                "edit ");

        commandHint = new CommandHint("select", "select");
        parseAndAssertHint(
                commandHint,
                " ",
                "selects a person",
                "select ");

        commandHint = new CommandHint("share", "share");
        parseAndAssertHint(
                commandHint,
                " ",
                "shares a contact via email",
                "share ");

        commandHint = new CommandHint("clear", "clear");
        parseAndAssertHint(
                commandHint,
                " ",
                "clears all contacts",
                "clear ");

        commandHint = new CommandHint("history", "history");
        parseAndAssertHint(
                commandHint,
                " ",
                "shows command history",
                "history ");

        commandHint = new CommandHint("exit", "exit");
        parseAndAssertHint(
                commandHint,
                " ",
                "exits the application",
                "exit ");

        commandHint = new CommandHint("undo", "undo");
        parseAndAssertHint(
                commandHint,
                " ",
                "undo previous command",
                "undo ");

        commandHint = new CommandHint("redo", "redo");
        parseAndAssertHint(
                commandHint,
                " ",
                "redo command",
                "redo ");

        commandHint = new CommandHint("help", "help");
        parseAndAssertHint(
                commandHint,
                " ",
                "shows user guide",
                "help ");

        commandHint = new CommandHint("music", "music");
        parseAndAssertHint(
                commandHint,
                " ",
                "plays music",
                "music ");

        commandHint = new CommandHint("radio", "radio");
        parseAndAssertHint(
                commandHint,
                " ",
                "plays the radio",
                "radio ");

        commandHint = new CommandHint("alias", "alias");
        parseAndAssertHint(
                commandHint,
                " ",
                "sets or show alias",
                "alias ");

        commandHint = new CommandHint("unalias", "unalias");
        parseAndAssertHint(
                commandHint,
                " ",
                "removes alias",
                "unalias ");

        commandHint = new CommandHint("unknown", "unknown");
        parseAndAssertHint(
                commandHint,
                "",
                " type help for user guide",
                "unknown");
    }


}
