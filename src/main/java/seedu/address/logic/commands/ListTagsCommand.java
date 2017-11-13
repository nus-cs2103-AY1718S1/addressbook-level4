//@@author PokkaKiyo
package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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

    private static final Logger logger = LogsCenter.getLogger(ListTagsCommand.class);

    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(getFeedbackForUser());
    }

    private String getFeedbackForUser() {

        String feedbackForUser;

        ArrayList<Tag> listOfAllTags = getUniqueListOfTags();

        if (listOfAllTags.isEmpty()) {
            logger.info("------ ListTagsCommand found no tags attached to any contacts");
            feedbackForUser = MESSAGE_FAILURE;
        } else {
            feedbackForUser = MESSAGE_SUCCESS + getListOfAllTagsInString(listOfAllTags);
        }

        assert feedbackForUser != null : "feedbackForUser should contain some message and not be null";

        return feedbackForUser;
    }

    private ArrayList<Tag> getUniqueListOfTags() {
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

        ArrayList<String> listOfAllTagNames = getUniqueListOfTagNames(listOfAllTags);

        sortTagNamesAlphabetically(listOfAllTagNames);

        return convertSortedTagNamesToString(listOfAllTagNames);
    }

    private ArrayList<String> getUniqueListOfTagNames(ArrayList<Tag> listOfAllTags) {
        ArrayList<String> listOfAllTagNames = new ArrayList<String>();

        for (Tag tag : listOfAllTags) {
            listOfAllTagNames.add(tag.getTagName());
        }
        return listOfAllTagNames;
    }

    private void sortTagNamesAlphabetically(ArrayList<String> listOfAllTagNames) {
        Collections.sort(listOfAllTagNames, String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Converts the list of all tag names into a string to display
     * @param listOfAllTagNames the ArrayList containing all Tag names that are attached to at least 1 contact
     * @return a String with all the names of the Tags, formatted appropriately.
     */
    private String convertSortedTagNamesToString(ArrayList<String> listOfAllTagNames) {
        StringBuilder tagNamesInListOfAllTags = new StringBuilder("");
        for (String tagName : listOfAllTagNames) {
            tagNamesInListOfAllTags.append("[").append(tagName).append("] ");
        }

        return tagNamesInListOfAllTags.toString().trim();
    }

}
