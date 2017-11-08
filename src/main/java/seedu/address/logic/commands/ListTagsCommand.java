//@@author PokkaKiyo
package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * List all tags in the addressbook to the user
 */
public class ListTagsCommand extends Command {
    public static final String COMMAND_WORD = "listtags";
    public static final String COMMAND_WORD_ALIAS1 = "lt";
    public static final String COMMAND_WORD_ALIAS2 = "ltags";

    public static final String MESSAGE_FAILURE = "You do not have any tags!";
    public static final String MESSAGE_SUCCESS = "You have the following tags: ";


    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(getFeedbackForUser());
    }

    private String getFeedbackForUser() {

        String feedbackForUser;

        ArrayList<Tag> listOfAllTags = getAllTagsWithNoDuplicates();

        if (listOfAllTags.isEmpty()) {
            feedbackForUser = MESSAGE_FAILURE;
        } else {
            feedbackForUser = MESSAGE_SUCCESS + getListOfAllTagsInString(listOfAllTags);
        }

        return feedbackForUser;
    }

    private ArrayList<Tag> getAllTagsWithNoDuplicates() {
        ArrayList<Tag> listOfAllTags = new ArrayList<Tag>();

        for (ReadOnlyPerson p : model.getAddressBook().getPersonList()) {
            for (Tag tag : p.getTags()) {
                if (!listOfAllTags.contains(tag)) {
                    listOfAllTags.add(tag);
                }
            }
        }
        return listOfAllTags;
    }

    private String getListOfAllTagsInString(ArrayList<Tag> listOfAllTags) {

        ArrayList<String> listOfAllTagNames = getAllTagNamesWithNoDuplicates(listOfAllTags);

        sortListOfAllTagNamesAlphabetically(listOfAllTagNames);

        return getSortedTagNamesAsString(listOfAllTagNames);
    }

    private ArrayList<String> getAllTagNamesWithNoDuplicates(ArrayList<Tag> listOfAllTags) {
        ArrayList<String> listOfAllTagNames = new ArrayList<String>();

        for (Tag tag : listOfAllTags) {
            listOfAllTagNames.add(tag.getTagName());
        }
        return listOfAllTagNames;
    }

    private void sortListOfAllTagNamesAlphabetically(ArrayList<String> listOfAllTagNames) {
        Collections.sort(listOfAllTagNames);
    }

    private String getSortedTagNamesAsString(ArrayList<String> listOfAllTagNames) {
        StringBuilder tagNamesInListOfAllTags = new StringBuilder("");
        for (String tagName : listOfAllTagNames) {
            tagNamesInListOfAllTags.append("[").append(tagName).append("] ");
        }

        return tagNamesInListOfAllTags.toString().trim();
    }

}
