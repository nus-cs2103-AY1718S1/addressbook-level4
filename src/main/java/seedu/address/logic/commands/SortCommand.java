package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts Address Book contacts according to specified field.\n"
            + "Parameters: TYPE (must be 'name', 'phone', or 'email')\n"
            + "Example: " + COMMAND_WORD + " name";

    private String sortType;

    public String MESSAGE_SUCCESS = "Sorted all persons by ";

    public SortCommand(String type){
        this.sortType = type;
    }

    @Override
    public CommandResult execute() {
        //sort method calls goes here
        switch (sortType){
            case "name":
                model.sort(sortType);//insert sort method call here
                break;

            case "phone":
                //insert sort method call here
                break;

            case "email":
                //insert sort method call here
                break;
        }

        //because "sorted by phone" sounds weird
        if(sortType.equals("phone")) {sortType = sortType + " number"; }
        //lists all contacts after sorting
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS + sortType);
    }
}
