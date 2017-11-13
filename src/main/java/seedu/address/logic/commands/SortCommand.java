package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

//@@author charlesgoh
/**
 * Sorts all persons in address book by any field. Sorting can be done in ascending or descending order
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "so";

    public static final ArrayList<String> ACCEPTED_LIST_PARAMETERS = new ArrayList<>(Arrays.asList(
            "person", "task"));

    public static final ArrayList<String> ACCEPTED_FIELD_PARAMETERS = new ArrayList<>(Arrays.asList(
            "name", "phone", "email", "address", "priority", "deadline"));

    public static final ArrayList<String> ACCEPTED_ORDER_PARAMETERS = new ArrayList<>(Arrays.asList(
            "asc", "desc"));

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons or all tasks by chosen "
                + "field [NAME/PHONE/EMAIL/ADDRESS -- PRIORITY/DEADLINE] by [ASC/DESC] order. Case insensitive\n"
                + "Parameters: KEYWORD [LIST] [FIELD] [ORDER]\n"
                + "Example: " + COMMAND_WORD + " person email desc\n"
                + "Example 2: " + COMMAND_WORD + " task deadline desc\n";

    public static final String MESSAGE_INVALID_INPUT = "Invalid Input.\n"
            + "Accepted Person Field Values: NAME, PHONE, EMAIL, ADDRESS \n"
            + "Accepted Task Field Values: DEADLINE, PRIORITY\n"
            + "Accepted Order Values: ASC, DESC";

    public static final String MESSAGE_SUCCESS_PERSONS = "All persons in address book successfully sorted";
    public static final String MESSAGE_SUCCESS_TASKS = "All tasks in address book successfully sorted";

    private final String list;
    private final String field;
    private final String order;

    public SortCommand(String list, String field, String order) {
        this.field = field;
        this.order = order;
        this.list = list;
    }

    /**
     * Checks if argument command has the same parameter properties
     * @param commandB
     */
    public boolean sameCommandAs(SortCommand commandB) {
        return commandB.getList().equals(this.list) && commandB.getField()
                .equals(this.field) && commandB.getOrder().equals(this.order);
    }

    public String getField() {
        return this.field;
    }

    public String getOrder() {
        return this.order;
    }

    public String getList() {
        return this.list;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        if (this.list.equals(ACCEPTED_LIST_PARAMETERS.get(0))) {
            model.sortPersons(getField(), getOrder());
            return new CommandResult(MESSAGE_SUCCESS_PERSONS);
        } else {
            model.sortTasks(getField(), getOrder());
            return new CommandResult(MESSAGE_SUCCESS_TASKS);
        }
    }

}
