# hthjthtrh
###### /java/seedu/address/logic/commands/DeleteGroupCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;

/**
 * Deletes a group, depending on the existence of the group
 */
public class DeleteGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the group identified by the group name.\n"
            + "Parameters: GROUP_NAME\n"
            + "Example: " + COMMAND_WORD + " TestGroup";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %s.";

    public static final String MESSAGE_NONEXISTENT_GROUP = "The group '%s' does not exist.";

    private final String groupName;

    private Group grpToDelete;

    public DeleteGroupCommand(String grpName) {
        this.groupName = grpName.trim();
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        if (groupExists()) {
            model.deleteGroup(grpToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, groupName));
        } else {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, String.format(MESSAGE_NONEXISTENT_GROUP, groupName));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && this.groupName.equals(((DeleteGroupCommand) other).groupName)); // state check
    }


    /**
     * Checks if the group to be deleted exists
     */
    private boolean groupExists() {
        List<Group> groupList = model.getAddressBook().getGroupList();
        for (Group grp : groupList) {
            if (grp.getGrpName().equals(this.groupName)) {
                grpToDelete = grp;
                return true;
            }
        }
        return false;
    }
}
```
###### /java/seedu/address/logic/commands/EditGroupCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.predicates.GroupContainsPersonPredicate;

/**
 * Edits the group, either 1.change group name, 2.adds a person to the group or 3.deletes a person from the group
 */
public class EditGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": edits the group. Supports three kinds of operations: 1. change group name 2. add a person 3. delete"
            + " a person\n"
            + "Parameters: GROUP_NAME grpName NEW_GROUP_NAME\n"
            + "OR: GROUP_NAME add INDEX\n"
            + "OR: GROUP_NAME delete INDEX\n"
            + "Examples: " + COMMAND_WORD + " SmartOnes grpName SuperSmartOnes\n"
            + COMMAND_WORD + " SmartOnes add 3\n"
            + COMMAND_WORD + " SmartOnes delete 4";

    public static final String MESSAGE_ADD_PERSON_SUCCESS = "Added person to group '%s':\n'%s'";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted person from group '%s':\n'%s'";

    public static final String MESSAGE_CHANGE_NAME_SUCCESS = "Name of group '%s' is changed to '%s'";

    public static final String MESSAGE_GROUP_NONEXISTENT = "This group does not exist!\n";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person is already in the group";

    public static final String MESSAGE_DUPLICATE_GROUP =
            "A group by the name of '%s' already exists in the addressbook!";

    private String grpName;
    private String operation;
    private String detail;
    private Predicate predicate;

    public EditGroupCommand(String grpName, String operation, String detail) {
        this.grpName = grpName;
        this.operation = operation;
        this.detail = detail;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<Group> grpList = model.getAddressBook().getGroupList();

        // find target group
        Group targetGrp = null;
        for (int i = 0; i < grpList.size(); i++) {
            if (grpList.get(i).getGrpName().equals(grpName)) {
                targetGrp = grpList.get(i);
                break;
            }
        }

        if (targetGrp == null) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_GROUP_NONEXISTENT);
        }

        // use 'detail' differently according to operation type
        Index idx = null;
        if (operation.equals("add") || operation.equals("delete")) {
            try {
                idx = ParserUtil.parseIndex(detail);
            } catch (IllegalValueException e) {
                throw new AssertionError("detail should be an integer at this stage");
            }
        }

        // operation is change grpName
        if (idx == null) {
            try {
                model.setGrpName(targetGrp, detail);
            } catch (DuplicateGroupException e) {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE, String.format(MESSAGE_DUPLICATE_GROUP,
                        detail));
            }
            return new CommandResult(String.format(MESSAGE_CHANGE_NAME_SUCCESS, grpName, detail));
        } else {
            ReadOnlyPerson targetPerson;
            Person copiedPerson;
            if (operation.equals("add")) { //add operation

                List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
                if (idx.getZeroBased() >= lastShownList.size() || idx.getOneBased() <= 0) {
                    throw new CommandException(MESSAGE_EXECUTION_FAILURE,
                            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
                targetPerson = lastShownList.get(idx.getZeroBased());
                copiedPerson = new Person(targetPerson);

                try {
                    model.addPersonToGroup(targetGrp, copiedPerson);
                    predicate = new GroupContainsPersonPredicate(targetGrp);
                    model.updateFilteredPersonList(predicate);
                    return new CommandResult(String.format(MESSAGE_ADD_PERSON_SUCCESS, grpName,
                            copiedPerson.toString()));
                } catch (DuplicatePersonException e) {
                    throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_DUPLICATE_PERSON);
                }
            } else { //delete operation

                List<ReadOnlyPerson> grpPersonList = targetGrp.getPersonList();
                if (idx.getZeroBased() >= grpPersonList.size()) {
                    throw new CommandException(MESSAGE_EXECUTION_FAILURE,
                            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
                targetPerson = grpPersonList.get(idx.getZeroBased());
                copiedPerson = new Person(targetPerson);

                try {
                    model.removePersonFromGroup(targetGrp, copiedPerson);
                    predicate = new GroupContainsPersonPredicate(targetGrp);
                    model.updateFilteredPersonList(predicate);
                    return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, grpName,
                            copiedPerson.toString()));
                } catch (PersonNotFoundException e) {
                    assert false : "The target person cannot be missing";
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof EditGroupCommand) {
            EditGroupCommand temp = (EditGroupCommand) other;
            return (this.grpName.equals(temp.grpName) && this.operation.equals(temp.operation)
                && this.detail.equals(temp.detail));
        }

        return false;
    }
}
```
###### /java/seedu/address/logic/commands/GroupingCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class GroupingCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "createGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a group for the list of person based on group name(non-integer) "
            + "and index numbers provided\n"
            + "Parameters: GROUP_NAME INDEX [INDEX]...\n"
            + "Example: " + COMMAND_WORD + " SmartOnes 1 4 2";

    public static final String MESSAGE_GROUPING_PERSON_SUCCESS = "Created group '%s' for people:\n";

    public static final String MESSAGE_DUPLICATE_GROUP_NAME = "This group already exists!\n";

    private List<Index> targetIdxs;
    private String groupName;

    public GroupingCommand(String groupName, List<Index> targetIndex) {
        this.groupName = groupName;
        this.targetIdxs = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<Integer> executableIdx = new ArrayList<>();
        Boolean hasExecutableIdx = false;

        for (Index idx : targetIdxs) {
            int intIdx = idx.getZeroBased();
            if (intIdx < lastShownList.size()) {
                executableIdx.add(intIdx);
                hasExecutableIdx = true;
            }
        }

        if (!hasExecutableIdx) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, Messages.MESSAGE_INVALID_PERSON_INDEX_ALL);
        }

        List<ReadOnlyPerson> personToGroup = new ArrayList<>();
        executableIdx.forEach(idx -> personToGroup.add(lastShownList.get(idx)));

        try {
            model.createGroup(groupName, personToGroup);
        } catch (DuplicateGroupException e) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_DUPLICATE_GROUP_NAME);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(getSb(groupName, personToGroup));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupingCommand // instanceof handles nulls
                && this.groupName.equals(((GroupingCommand) other).groupName)); // state check
    }

    /**
     * Return a String
     * @param persons to be deleted
     * @return a String with all details listed
     */
    public static String getSb(String grpName, List<ReadOnlyPerson> persons) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(MESSAGE_GROUPING_PERSON_SUCCESS, grpName));

        appendPersonList(sb, persons);

        return sb.toString();
    }
}
```
###### /java/seedu/address/logic/commands/ListGroupsCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;

/**
 * Shows all groups in the addressbook
 * a temporary implementation
 */
public class ListGroupsCommand extends Command {

    public static final String COMMAND_WORD = "listGroups";

    public static final String MESSAGE_SUCCESS = "Listing all groups:\n";

    public static final String MESSAGE_EMPTY_GROUP_LIST = "There is no groups yet.";

    @Override
    public CommandResult execute() throws CommandException {
        List<Group> groupList = model.getAddressBook().getGroupList();
        if (groupList.size() == 0) {
            return new CommandResult(MESSAGE_EMPTY_GROUP_LIST);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_SUCCESS);

        int grpListSize = model.getAddressBook().getGroupList().size();

        for (int i = 1; i <= grpListSize; i++) {
            sb.append(i + ". ");
            sb.append(model.getAddressBook().getGroupList().get(i - 1).getGrpName());
            if (i != grpListSize) {
                sb.append("\n");
            }
        }
        return new CommandResult(sb.toString());
    }
}
```
###### /java/seedu/address/logic/commands/UndoCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undo the previous {@code UndoableCommand}.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes undoable commands specified by the number of steps.\n"
            + "Parameters: [STEPS]\n"
            + "Eample: to undo 3 undoable commands:\n"
            + COMMAND_WORD + " 3\n"
            + "Alternate usage: use keyword \"all\" to undo all commands in current session\n"
            + "Eample: " + COMMAND_WORD + " all";


    // default undo one command
    private int steps = 1;

    public UndoCommand(){}

    public UndoCommand(int steps) {
        this.steps = steps;
    }


    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        int occurence = 0;
        while (steps > 0) {
            if (!undoRedoStack.canUndo()) {
                if (occurence == 0) {
                    throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_FAILURE);
                } else {
                    return new CommandResult(MESSAGE_SUCCESS);
                }
            }
            undoRedoStack.popUndo().undo();
            steps--;
            occurence++;
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
}
```
###### /java/seedu/address/logic/commands/ViewGroupCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.UndoableCommand.appendPersonList;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.predicates.GroupContainsPersonPredicate;

/**
 * Lists all person within the group
 */
public class ViewGroupCommand extends Command {

    public static final String COMMAND_WORD = "viewGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": list all persons in the specified group(by group name or index)\n"
            + "Parameters: GROUP_NAME          OR          INDEX\n"
            + "Example: " + COMMAND_WORD + " SmartOnes";

    public static final String MESSAGE_GROUPING_PERSON_SUCCESS = "Listing %d person(s) in the group '%s'";

    public static final String MESSAGE_GROUP_NONEXISTENT = "This group does not exist!\n";

    private Index index = null;
    private String grpName = null;
    private Predicate predicate;

    public ViewGroupCommand(Index idx) {
        this.index = idx;
    }

    public ViewGroupCommand(String grpName) {
        this.grpName = grpName;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Group> grpList = model.getAddressBook().getGroupList();
        Group grpToView;

        if (this.index != null) {
            try {
                grpToView = grpList.get(index.getZeroBased());

                predicate = new GroupContainsPersonPredicate(grpToView);
                model.updateFilteredPersonList(predicate);
                return new CommandResult(String.format(MESSAGE_GROUPING_PERSON_SUCCESS,
                        grpToView.getPersonList().size(), grpToView.getGrpName()));
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
            }
        } else { //either index or grpName should be non-null
            for (int i = 0; i < grpList.size(); i++) {
                if (grpList.get(i).getGrpName().equals(this.grpName)) {
                    grpToView = grpList.get(i);
                    predicate = new GroupContainsPersonPredicate(grpToView);
                    model.updateFilteredPersonList(predicate);
                    return new CommandResult(String.format(MESSAGE_GROUPING_PERSON_SUCCESS,
                            grpToView.getPersonList().size(), grpToView.getGrpName()));
                }
            }
        }
        throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_GROUP_NONEXISTENT);
    }

    /**
     * creates and returns a string to represent the list of person in the group
     * @param grpToView the target group
     * @return string to represent list of person
     */
    private String personListAsString(Group grpToView) {
        List<ReadOnlyPerson> personList = grpToView.getPersonList();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(MESSAGE_GROUPING_PERSON_SUCCESS, grpToView.getGrpName()));

        appendPersonList(sb, personList);

        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ViewGroupCommand) {
            if (this.index == null) {
                return this.grpName.equals(((ViewGroupCommand) other).grpName);
            } else {
                return this.index.equals(((ViewGroupCommand) other).index);
            }
        }
        return false;
    }
}
```
###### /java/seedu/address/logic/parser/DeleteCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_VALUE_ARGUMENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        args = args.trim();
        if (args.equals("")) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        }
        List<String> indexStrs = Arrays.asList(args.split(" "));
        //eliminate duplicates
        HashSet<Integer> indexIntsSet = new HashSet<>();

        for (String indexStr : indexStrs) {
            try {
                indexIntsSet.add(ParserUtil.parseInt(indexStr));
            } catch (IllegalValueException e) {
                throw new ParseException(MESSAGE_INVALID_VALUE_ARGUMENT, DeleteCommand.MESSAGE_USAGE);
            }
        }
        List<Integer> indexInts = new ArrayList<>(indexIntsSet);
        Collections.sort(indexInts);
        ArrayList<Index> indexes = new ArrayList<>();
        for (int i = 0; i < indexInts.size(); i++) {
            indexes.add(Index.fromOneBased(indexInts.get(i)));
        }
        return new DeleteCommand(indexes);
    }

}
```
###### /java/seedu/address/logic/parser/DeleteGroupCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments in the context of DeleteGroupCommand returns the command
 */
public class DeleteGroupCommandParser implements Parser<DeleteGroupCommand> {
    @Override
    public DeleteGroupCommand parse(String userInput) throws ParseException {
        userInput = userInput.trim();
        List<String> argList = Arrays.asList(userInput.split(" "));

        if (userInput.equals("") || argList.size() != 1) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE);
        }

        try {
            ParserUtil.parseInt(argList.get(0));
        } catch (IllegalValueException e) {
            // non-integer, a possible valid group name
            return new DeleteGroupCommand(argList.get(0));
        }

        throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE);
    }
}
```
###### /java/seedu/address/logic/parser/EditGroupCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditGroupCommand object
 */
public class EditGroupCommandParser implements Parser<EditGroupCommand> {

    private static final Set<String> validOp = new HashSet<>(Arrays.asList("grpName", "add", "delete"));

    /**
     * Parses the given {@code String} of arguments in the context of the EditGroupCommand
     * and returns an ViewGroupCommand for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditGroupCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        userInput = userInput.trim();

        List<String> argsList = Arrays.asList(userInput.split(" "));

        if (argsList.size() != 3 || userInput.equals("")) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
        }

        String grpName;
        String operation;
        String detail;

        // parseing
        try {
            grpName = argsList.get(0);
            operation = argsList.get(1);

            if (isInteger(grpName) || isInteger(operation)) {
                throw new Exception();
            }
            if (!validOp.contains(operation)) {
                throw new Exception();
            }

            detail = argsList.get(2);
            // if operation is add or delete, detail should be an index
            if (operation.equals("add") || operation.equals("delete")) {
                if (!isInteger(detail)) {
                    throw new Exception();
                }
            } else {
                // operation is to change name, need to enforce the rule that group name is not an integer
                if (isInteger(detail)) {
                    throw new Exception();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
        } catch (Exception e) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
        }

        return new EditGroupCommand(grpName, operation, detail);
    }

    /**
     * trues to parse input into an integer
     * @param input
     * @return true if input is integer
     */
    private boolean isInteger(String input) {
        boolean isInt;
        try {
            ParserUtil.parseInt(input);
            isInt = true;
        } catch (IllegalValueException e) {
            isInt = false;
        }
        return isInt;
    }
}
```
###### /java/seedu/address/logic/parser/GroupingCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.GroupingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the input argument and creates a new GroupingCommand object
 */
public class GroupingCommandParser implements Parser<GroupingCommand> {

    public static final String MESSAGE_INCORRECT_GROUPNAME_FORMAT = "Group name cannot be a integer!";

    /**
     * Parses the given {@code String} of arguments in the context of the GroupingCommand
     * and returns an GroupingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public GroupingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.trim();
        List<String> argsList = Arrays.asList(args.split(" "));

        String grpName;
        List<String> indStrList;

        boolean isInteger;
        if (argsList.size() >= 2) {
            // check if group name is an integer
            try {
                ParserUtil.parseInt(argsList.get(0));
                isInteger = true;
            } catch (IllegalValueException e) {
                isInteger = false;
            }

            // if group name is integer, alert user
            if (isInteger) {
                throw new ParseException(MESSAGE_INCORRECT_GROUPNAME_FORMAT, GroupingCommand.MESSAGE_USAGE);
            }
            grpName = argsList.get(0);
            indStrList = argsList.subList(1, argsList.size());
        } else {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, GroupingCommand.MESSAGE_USAGE);
        }

        // using hashset to eliminate any duplicates
        Set<Integer> indexIntsSet = new HashSet<>();

        for (String indexStr : indStrList) {
            try {
                indexIntsSet.add(ParserUtil.parseInt(indexStr));
            } catch (IllegalValueException e) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, GroupingCommand.MESSAGE_USAGE);
            }
        }

        List<Index> indexList = new ArrayList<>();

        indexIntsSet.forEach(idx -> indexList.add(Index.fromOneBased(idx)));

        return new GroupingCommand(grpName, indexList);
    }
}
```
###### /java/seedu/address/logic/parser/UndoCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UndoCommand object
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UndoCommand
     * and returns an UndoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UndoCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            return new UndoCommand();
        } else if (args.equals(" all")) {
            return new UndoCommand(Integer.MAX_VALUE);
        } else {
            try {
                int steps = ParserUtil.parseInt(args);
                return new UndoCommand(steps);
            } catch (IllegalValueException ive) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE);
            }
        }
    }
}
```
###### /java/seedu/address/logic/parser/ViewGroupCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ViewGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewGroupCommand object
 */
public class ViewGroupCommandParser implements Parser<ViewGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewGroupCommand
     * and returns an ViewGroupCommand for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewGroupCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        userInput = userInput.trim();
        List<String> argsList = Arrays.asList(userInput.split(" "));

        if (argsList.size() > 1 || argsList.get(0).equals("")) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, ViewGroupCommand.MESSAGE_USAGE);
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(userInput);
            return new ViewGroupCommand(index);
        } catch (IllegalValueException e) {
            // argument is not an index
            return new ViewGroupCommand(userInput);
        }
    }
}
```
###### /java/seedu/address/MainApp.java
``` java
    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s backup address book and
     * {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelWithBackup(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readBackupAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Backup file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
            storage.saveAddressBook(initialData);
        } catch (DataConversionException e) {
            logger.warning("Backup file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the backup file. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        }
        return new ModelManager(initialData, userPrefs);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    public void setGroups(List<? extends Group> groups) throws DuplicateGroupException, DuplicatePersonException {
        this.groups.setGroups(groups);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Creates and adds the group into groups
     * @param groupName name of the group
     * @param personToGroup person in the group
     * @throws DuplicateGroupException
     */
    public void addGroup(String groupName, List<ReadOnlyPerson> personToGroup)
            throws DuplicateGroupException {
        Group newGroup = new Group(groupName);
        personToGroup.forEach(person -> {
            try {
                newGroup.add(new Person(person));
            } catch (DuplicatePersonException e) {
                throw new AssertionError("Shouldn't exist duplicate person");
            }
        });
        groups.add(newGroup);
    }

    /**
     * adds the grp to the grp list
     * @param grp
     * @throws DuplicateGroupException
     */
    public void addGroup(Group grp) throws DuplicateGroupException {
        Group newGroup;
        try {
            newGroup = new Group(grp);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("Shouldn't exist duplicate person");
        }
        groups.add(newGroup);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Deletes or updates the group, if the group contains personToEdit
     * @param personToEdit original person to be updated
     * @param editedPerson the person to update to. If null, it is a deletion
     */
    public void checkPersonInGroupList(ReadOnlyPerson personToEdit, Person editedPerson, Class commandClass) {
        if (this.groups.asObservableList().size() == 0) {
            return;
        }

        if (commandClass.equals(FavoriteCommand.class)) {
            this.groups.forEach(group -> {
                if (group.contains(personToEdit)) {
                    try {
                        group.favorite(personToEdit);
                    } catch (PersonNotFoundException pnfe) {
                        throw new AssertionError("The target person cannot be missing");
                    }
                }
            });
        } else if (commandClass.equals(DeleteCommand.class)) {
            this.groups.forEach(group -> {
                if (group.contains(personToEdit)) {
                    try {
                        group.remove(personToEdit);
                    } catch (PersonNotFoundException pnfe) {
                        throw new AssertionError("The target person cannot be missing");
                    }
                }
            });
        } else {
            this.groups.forEach(group -> {
                if (group.contains(personToEdit)) {
                    try {
                        group.setPerson(personToEdit, editedPerson);
                    } catch (DuplicatePersonException dpe) {
                        throw new AssertionError("Shouldn't have duplicate person if"
                                + " update person is successful");
                    } catch (PersonNotFoundException pnfe) {
                        throw new AssertionError("The target person cannot be missing");
                    }
                }
            });
        }
    }

    public void removeGroup(Group grpToDelete) {
        groups.removeGroup(grpToDelete);
    }

    public void setGrpName(Group targetGrp, String newName) throws DuplicateGroupException {
        this.groups.setGrpName(targetGrp, newName);
    }
```
###### /java/seedu/address/model/group/DuplicateGroupException.java
``` java
package seedu.address.model.group;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Group objects.
 */
public class DuplicateGroupException extends DuplicateDataException {

    public DuplicateGroupException() {
        super("Operation would result in duplicate groups");
    }
}
```
###### /java/seedu/address/model/group/Group.java
``` java
package seedu.address.model.group;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Represents a Group in the address book
 */

public class Group extends UniquePersonList {

    private String grpName;

    public Group(String groupName) {
        this.grpName = groupName;
    }

    public Group(Group grp) throws DuplicatePersonException {
        setGrpName(grp.getGrpName());
        setPersons(grp.getPersonList());
    }

    public String getGrpName() {
        return grpName;
    }

    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }

    public ObservableList<ReadOnlyPerson> getPersonList() {
        return this.asObservableList();
    }

}
```
###### /java/seedu/address/model/group/UniqueGroupList.java
``` java
package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A list of groups that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();

    /**
     * Adds a group to the list.
     */
    public void add(Group grp) throws DuplicateGroupException {
        requireNonNull(grp);
        if (contains(grp)) {
            throw new DuplicateGroupException();
        }
        internalList.add(grp);
        sort();
    }

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        for (Group group : internalList) {
            if (group.getGrpName().equals(toCheck.getGrpName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * sorts the list of groups based on alphabetical order
     */
    public void sort() {
        internalList.sort(Comparator.comparing(Group::getGrpName));
    }


    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Group> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    public void setGroups(List<? extends Group> groups) throws DuplicateGroupException, DuplicatePersonException {
        final UniqueGroupList replacement = new UniqueGroupList();
        for (final Group grp : groups) {
            replacement.add(new Group(grp));
        }
        this.internalList.setAll(replacement.internalList);
        sort();
    }

    public void removeGroup(Group grpToDelete) {
        internalList.remove(grpToDelete);
    }

    /**
     * sets the group to the new name
     * @param targetGrp group to change name
     * @param newName new name to change to
     * @throws DuplicateGroupException if a group of newName already exists in the group list
     */
    public void setGrpName(Group targetGrp, String newName) throws DuplicateGroupException {
        for (Group grp : internalList) {
            if (grp.getGrpName().equals(newName)) {
                throw new DuplicateGroupException();
            }
        }

        targetGrp.setGrpName(newName);
    }
}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void createGroup(String groupName, List<ReadOnlyPerson> personToGroup)
            throws DuplicateGroupException {
        addressBook.addGroup(groupName, personToGroup);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void propagateToGroup(ReadOnlyPerson personToEdit, Person editedPerson, Class commandClass) {
        requireNonNull(personToEdit);

        addressBook.checkPersonInGroupList(personToEdit, editedPerson, commandClass);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteGroup(Group grpToDelete) {
        requireNonNull(grpToDelete);

        addressBook.removeGroup(grpToDelete);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void setGrpName(Group targetGrp, String newName) throws DuplicateGroupException {
        requireAllNonNull(targetGrp, newName);

        addressBook.setGrpName(targetGrp, newName);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPersonToGroup(Group targetGrp, ReadOnlyPerson targetPerson)
            throws DuplicatePersonException {
        requireAllNonNull(targetGrp, targetPerson);

        targetGrp.add(targetPerson);

        indicateAddressBookChanged();
    }

    @Override
    public synchronized void removePersonFromGroup(Group targetGrp, ReadOnlyPerson targetPerson)
            throws PersonNotFoundException {
        requireAllNonNull(targetGrp, targetPerson);

        targetGrp.remove(targetPerson);

        indicateAddressBookChanged();
    }
```
###### /java/seedu/address/model/person/predicates/GroupContainsPersonPredicate.java
``` java
package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.group.Group;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a readOnlyPerson is contained by the group
 */
public class GroupContainsPersonPredicate implements Predicate<ReadOnlyPerson> {

    private Group group;

    public GroupContainsPersonPredicate(Group grp) {
        this.group = grp;
    }

    @Override
    public boolean test(ReadOnlyPerson readOnlyPerson) {
        return group.contains(readOnlyPerson);
    }
}
```
###### /java/seedu/address/storage/StorageManager.java
``` java
    public Optional<ReadOnlyAddressBook> readBackupAddressBook() throws DataConversionException, IOException {
        return readAddressBook(backupAddressbook.getAddressBookFilePath());
    }
```
###### /java/seedu/address/storage/StorageManager.java
``` java


    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

```
###### /java/seedu/address/storage/StorageManager.java
``` java
    private void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        String backupPath = backupAddressbook.getAddressBookFilePath();
        logger.fine("Backing up data to: " + backupPath);
        saveAddressBook(addressBook, backupPath);
    }
```
###### /java/seedu/address/storage/XmlAdaptedGroup.java
``` java
package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * JAXB-friendly version of the Group.
 */
public class XmlAdaptedGroup {

    @XmlElement(required = true)
    private String groupName;
    @XmlElement
    private List<XmlAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedGroup.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGroup() {}

    /**
     * Converts a given Group into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedGroup(Group source) {
        groupName = source.getGrpName();
        persons.addAll(source.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this jaxb-friendly adapted group object into the model's Group object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Group toModelType() throws IllegalValueException {
        Group grp =  new Group(this.groupName);
        this.persons.forEach(person -> {
            try {
                grp.add(person.toModelType());
            } catch (DuplicatePersonException e) {
                throw new AssertionError("Shouldn't exist duplicate person");
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        });
        return grp;
    }
}
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<Group> getGroupList() {
        final ObservableList<Group> groups = this.groups.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(groups);
    }
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Opens an alert dialogue to inform user of the error
     * @param e exception due to parsing / execution
     */
    private void alertUser(Exception e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        FxViewUtil.setStageIcon(alertStage, "/images/warning_sign.png");

        if (e.getClass().equals(CommandException.class)) {
            alert.setHeaderText(((CommandException) e).getExceptionHeader());
        } else {
            alert.setHeaderText(((ParseException) e).getExceptionHeader());
        }
        alert.setContentText(e.getMessage());

        alert.setResizable(true);
        alert.showAndWait();
    }
```
