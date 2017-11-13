package seedu.address.logic.commands;

//@@author wangyiming1019
import java.util.ArrayList;
import java.util.Collections;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * List all tags in the address book to the user
 */
public class TagListCommand extends Command {
    public static final String COMMAND_WORD = "showtag";
    public static final String COMMAND_ALIAS = "stag";

    public static final String MESSAGE_FAILURE = "There is no tag!";
    public static final String MESSAGE_SUCCESS = "All the tags are here: ";

    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(displayTags());
    }

    /**
     * display all the tags to user
     * @return String displayTags
     */
    private String displayTags() {

        String displayTags;
        ArrayList<Tag> tagList = getAllTagsInAddressBook();

        if (tagList.isEmpty()) {
            displayTags = MESSAGE_FAILURE;
        } else {
            displayTags = MESSAGE_SUCCESS + convertTagToString(tagList);
        }
        return displayTags;
    }

    /**
     * get all the tags in the address book without duplication
     * @return allTagList
     */
    private ArrayList<Tag> getAllTagsInAddressBook() {
        ArrayList<Tag> allTagList = new ArrayList<Tag>();
        for (ReadOnlyPerson p : model.getAddressBook().getPersonList()) {
            for (Tag tag : p.getTags()) {
                if (!allTagList.contains(tag)) {
                    allTagList.add(tag);
                }
            }
        }
        return allTagList;
    }

    /**
     * convert a list of tags to a String
     * @return String tags
     */
    private String convertTagToString(ArrayList<Tag> tagList) {
        ArrayList<String> tagNameList = new ArrayList<String>();
        for (Tag tag : tagList) {
            tagNameList.add(tag.getTagName());
        }
        Collections.sort(tagNameList);
        StringBuilder tagNameString = new StringBuilder();
        for (String tagName : tagNameList) {
            tagNameString.append("<").append(tagName).append("> ");
        }
        return tagNameString.toString().trim();
    }
}
