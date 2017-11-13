package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.UnrecognisedParameterException;

//@@author quanle1994

/**
 * Order the list according to a parameter
 */
public class OrderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "order";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Order the contacts of Address Book based on one/multiple parameter(s).\n"
            + "To order GROUP with other parameters, GROUP must be the first parameter. \n"
            + "Parameters:  NAME, ADDRESS, BIRTHDAY, TAG, GROUP\n"
            + "Example: " + COMMAND_WORD + " BIRTHDAY NAME";

    public static final String MESSAGE_ORDER_SUCCESS = "Address Book has been ordered by ";
    public static final String MESSAGE_ORDER_WRONG_PARAMETER = "The parameter can only contain Name, Address, Birthday,"
            + " Tag";

    private String orderParameter;

    public OrderCommand(String orderParameter) {
        this.orderParameter = orderParameter;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.orderList(orderParameter);
        } catch (UnrecognisedParameterException upe) {
            throw new CommandException(MESSAGE_ORDER_WRONG_PARAMETER);
        }
        return new CommandResult(MESSAGE_ORDER_SUCCESS + orderParameter);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderCommand // instanceof handles nulls
                && this.orderParameter.equals(((OrderCommand) other).orderParameter)); // state check
    }
}
