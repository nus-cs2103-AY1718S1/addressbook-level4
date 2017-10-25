package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

import java.util.Arrays;

/**
 * @author Sri-vatsa
 * Deletes all tags identified from the address book.
 */
public class DeleteTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteTag";
    public static final String COMMAND_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a particular tag from everyone.\n"
            + "Parameters: Tag1(text) Tag2(text)\n"
            + "Example: " + COMMAND_WORD + " friends" + " family";

    public static final String MESSAGE_SUCCESS = "Tag(s) successfully deleted";
    public static final String MESSAGE_NO_TAGS_DELETED = "Tag(s) not in address book; Nothing to delete";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + " TAG 1" + " TAG2" + " ...";

    private final String[] mTagsArgs;
    private Tag[] mTagsToDelete;

    public DeleteTagCommand(String[] tag) throws NullPointerException {
        if(tag == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        mTagsArgs = tag;
    }

    /***
     * @author Sri-vatsa
     * Helper method that converts array of arguments (string type) to array of tags (Tag class)
     * @param tag array of arguments in String
     * @throws IllegalValueException
     */
    private Tag[] stringToTag (String[] tag) throws IllegalValueException {
        int numOfArgs = tag.length;
        Tag[] tagsToDelete = new Tag[numOfArgs];

        try {
            for (int i = 0; i < numOfArgs; i++) {
                tagsToDelete[i] = new Tag(tag[i]);
            }
        } catch (IllegalValueException ive) {
            throw new IllegalValueException("Illegal tag value.");
        } catch (IndexOutOfBoundsException ibe) {
            throw new IndexOutOfBoundsException("Accessing tags that do not exist.");
        }
        return tagsToDelete;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        boolean hasOneOrMoreDeletion = false;
        try {
            mTagsToDelete = stringToTag(mTagsArgs);
            hasOneOrMoreDeletion = model.deleteTag(mTagsToDelete);

        } catch (IllegalValueException ive) {
            assert false : "The tag is not a proper value";
        } catch (PersonNotFoundException pnfe) {
            assert  false: "The person associated with the tag cannot be missing";
        }

        if(hasOneOrMoreDeletion) {
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            return new CommandResult(String.format(MESSAGE_NO_TAGS_DELETED));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTagCommand // instanceof handles nulls
                && Arrays.equals(this.mTagsArgs,((DeleteTagCommand) other).mTagsArgs)); // state check
    }

}
