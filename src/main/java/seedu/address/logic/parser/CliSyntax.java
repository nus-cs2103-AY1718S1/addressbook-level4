package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewRolodexCommand;
import seedu.address.logic.commands.OpenRolodexCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_SUBJECT = new Prefix("s/");

    /* Prefix set */
    public static final Set<Prefix> POSSIBLE_PREFIXES =
            new HashSet<>(Arrays.asList(
                    PREFIX_NAME,
                    PREFIX_PHONE,
                    PREFIX_EMAIL,
                    PREFIX_ADDRESS,
                    PREFIX_REMARK,
                    PREFIX_TAG
            ));

    /* Postfix definitions */
    public static final Postfix POSTFIX_ASCENDING = new Postfix("asc");
    public static final Postfix POSTFIX_DESCENDING = new Postfix("desc");

    /* SortArgument definitions */
    public static final SortArgument SORT_ARGUMENT_NAME_DEFAULT =
            new SortArgument(PREFIX_NAME.toString());
    public static final SortArgument SORT_ARGUMENT_PHONE_DEFAULT =
            new SortArgument(PREFIX_PHONE.toString());
    public static final SortArgument SORT_ARGUMENT_EMAIL_DEFAULT =
            new SortArgument(PREFIX_EMAIL.toString());
    public static final SortArgument SORT_ARGUMENT_ADDRESS_DEFAULT =
            new SortArgument(PREFIX_ADDRESS.toString());
    public static final SortArgument SORT_ARGUMENT_REMARK_DEFAULT =
            new SortArgument(PREFIX_REMARK.toString());

    public static final SortArgument SORT_ARGUMENT_NAME_ASCENDING =
            new SortArgument(PREFIX_NAME.concat(POSTFIX_ASCENDING));
    public static final SortArgument SORT_ARGUMENT_PHONE_ASCENDING =
            new SortArgument(PREFIX_PHONE.concat(POSTFIX_ASCENDING));
    public static final SortArgument SORT_ARGUMENT_EMAIL_ASCENDING =
            new SortArgument(PREFIX_EMAIL.concat(POSTFIX_ASCENDING));
    public static final SortArgument SORT_ARGUMENT_ADDRESS_ASCENDING =
            new SortArgument(PREFIX_ADDRESS.concat(POSTFIX_ASCENDING));
    public static final SortArgument SORT_ARGUMENT_REMARK_ASCENDING =
            new SortArgument(PREFIX_REMARK.concat(POSTFIX_ASCENDING));

    public static final SortArgument SORT_ARGUMENT_NAME_DESCENDING =
            new SortArgument(PREFIX_NAME.concat(POSTFIX_DESCENDING));
    public static final SortArgument SORT_ARGUMENT_PHONE_DESCENDING =
            new SortArgument(PREFIX_PHONE.concat(POSTFIX_DESCENDING));
    public static final SortArgument SORT_ARGUMENT_EMAIL_DESCENDING =
            new SortArgument(PREFIX_EMAIL.concat(POSTFIX_DESCENDING));
    public static final SortArgument SORT_ARGUMENT_ADDRESS_DESCENDING =
            new SortArgument(PREFIX_ADDRESS.concat(POSTFIX_DESCENDING));
    public static final SortArgument SORT_ARGUMENT_REMARK_DESCENDING =
            new SortArgument(PREFIX_REMARK.concat(POSTFIX_DESCENDING));

    /* SortArgument set */
    public static final Set<SortArgument> POSSIBLE_SORT_ARGUMENTS =
            new HashSet<>(Arrays.asList(
                    SORT_ARGUMENT_NAME_DEFAULT,
                    SORT_ARGUMENT_PHONE_DEFAULT,
                    SORT_ARGUMENT_EMAIL_DEFAULT,
                    SORT_ARGUMENT_ADDRESS_DEFAULT,
                    SORT_ARGUMENT_REMARK_DEFAULT,
                    SORT_ARGUMENT_NAME_DESCENDING,
                    SORT_ARGUMENT_PHONE_DESCENDING,
                    SORT_ARGUMENT_EMAIL_DESCENDING,
                    SORT_ARGUMENT_ADDRESS_DESCENDING,
                    SORT_ARGUMENT_REMARK_DESCENDING,
                    SORT_ARGUMENT_NAME_ASCENDING,
                    SORT_ARGUMENT_PHONE_ASCENDING,
                    SORT_ARGUMENT_EMAIL_ASCENDING,
                    SORT_ARGUMENT_ADDRESS_ASCENDING,
                    SORT_ARGUMENT_REMARK_ASCENDING));

    /* Command abbreviations set */
    public static final Set<String> POSSIBLE_COMMAND_WORDS = ImmutableSet.<String>builder()
        .addAll(AddCommand.COMMAND_WORD_ABBREVIATIONS)
        .addAll(ClearCommand.COMMAND_WORD_ABBREVIATIONS)
        .addAll(DeleteCommand.COMMAND_WORD_ABBREVIATIONS)
        .addAll(EditCommand.COMMAND_WORD_ABBREVIATIONS)
        .addAll(EmailCommand.COMMAND_WORD_ABBREVIATIONS)
        .addAll(ExitCommand.COMMAND_WORD_ABBREVIATIONS)
        .addAll(FindCommand.COMMAND_WORD_ABBREVIATIONS)
        .addAll(HelpCommand.COMMAND_WORD_ABBREVIATIONS)
        .addAll(HistoryCommand.COMMAND_WORD_ABBREVIATIONS)
        .addAll(ListCommand.COMMAND_WORD_ABBREVIATIONS)
        .addAll(NewRolodexCommand.COMMAND_WORD_ABBREVIATIONS)
        .addAll(OpenRolodexCommand.COMMAND_WORD_ABBREVIATIONS)
        .addAll(RedoCommand.COMMAND_WORD_ABBREVIATIONS)
        .addAll(RemarkCommand.COMMAND_WORD_ABBREVIATIONS)
        .addAll(SelectCommand.COMMAND_WORD_ABBREVIATIONS)
        .addAll(UndoCommand.COMMAND_WORD_ABBREVIATIONS)
        .build();
}
