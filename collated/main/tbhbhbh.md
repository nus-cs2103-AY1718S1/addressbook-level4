# tbhbhbh
###### \java\seedu\address\commons\events\ui\SocialRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.UserName;

/**
 * Represents a change in the browser to view the person's social media profile, whether it is Facebook/Instagram.
 */
public class SocialRequestEvent extends BaseEvent {

    private final UserName userName;
    private final String socialMediaLink;

    public SocialRequestEvent(UserName userName, String socialMediaLink) {
        this.userName = userName;
        this.socialMediaLink = socialMediaLink;
    }

    public UserName getUserName() {
        return userName;
    }

    public String getSocialMediaLink() {
        return socialMediaLink;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": finds all persons whose information contain "
            + "any of the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: names (eg. Tan, Alex, Abdullah), tags (eg. colleagues, friends), initials (eg. A, b, x), "
            + "birthday month (eg. 05, 12)"
            + "Format: KEYWORD [MORE_KEYWORDS]...\n"
            + "(The format for entering this command differs depending on what you are searching for. Go into our "
            + "help window for more information)"
            + "Example: " + COMMAND_WORD + " alice colleagues";

    public static final String BIRTHDAY_USAGE = COMMAND_WORD + ": Search for persons whose birthday month is "
            + "represented by [01-12] for [January to December].\n"
            + "Parameters: MM (must be a non-zero integer 01 to 12)\n"
            + "Example: " + COMMAND_WORD + " 09";

    private final Predicate<ReadOnlyPerson> searchPredicate;

    public FindCommand(PersonContainsKeywordsPredicate searchPredicate) {
        this.searchPredicate = searchPredicate;
    }
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(searchPredicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.searchPredicate.equals(((FindCommand) other).searchPredicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SocialCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.SocialRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UserName;

/**
 * Accesses the person's social media profile on the browser.
 */

public class SocialCommand extends Command {
    public static final String COMMAND_WORD = "social";
    public static final String MESSAGE_SOCIAL_EMPTY = " does not have chosen social media: ";
    public static final String MESSAGE_SOCIAL_UNSUPPORTED = "Chosen social media not supported: ";
    public static final String MESSAGE_IF_MISSING = "(Check the person's username fields if his/her page does not "
            + "exist)";
    public static final String MESSAGE_INSTAGRAM = "'s Instagram profile\n";
    public static final String MESSAGE_TWITTER = "'s Twitter profile\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens up the target index's Twitter or Instagram "
            + "profile.\n"
            + "Parameters: INDEX CHOSEN_SOCIAL_MEDIA\n"
            + "CHOSEN_SOCIAL_MEDIA: ig or tw\n"
            + "Example: " + COMMAND_WORD + " 1 ig";

    public static final String INSTAGRAM_URL_PREFIX = "https://www.instagram.com/";
    public static final String TWITTER_URL_PREFIX = "https://www.twitter.com/";

    private final Index index;
    private final String socialMedia;

    private String messageSocialSuccess = "Successfully loaded %1$s";
    private String url;
    private UserName userName;
    private Name realName;

    public SocialCommand(Index index, String socialMedia) {
        this.index = index;
        this.socialMedia = socialMedia;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + index.getOneBased());
        }

        realName = lastShownList.get(index.getZeroBased()).getName();

        if (this.isInstagramName()) {
            userName = lastShownList.get(index.getZeroBased()).getInstagramName();
            url = INSTAGRAM_URL_PREFIX;
            messageSocialSuccess = messageSocialSuccess + MESSAGE_INSTAGRAM + MESSAGE_IF_MISSING;
        } else if (this.isTwitterName()) {
            userName = lastShownList.get(index.getZeroBased()).getTwitterName();
            url = TWITTER_URL_PREFIX;
            messageSocialSuccess = messageSocialSuccess + MESSAGE_TWITTER + MESSAGE_IF_MISSING;
        } else {
            throw new CommandException(MESSAGE_SOCIAL_UNSUPPORTED
                    + socialMedia);
        }

        if (url.equals(TWITTER_URL_PREFIX + userName) || url.equals(INSTAGRAM_URL_PREFIX + userName)) {
            throw new CommandException(realName + MESSAGE_SOCIAL_EMPTY + convertToActualName(socialMedia));
        }
        EventsCenter.getInstance().post(new SocialRequestEvent(userName, url));
        return new CommandResult(String.format(messageSocialSuccess, realName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SocialCommand // instanceof handles nulls
                && this.socialMedia.equals(((SocialCommand) other).socialMedia)
                && this.index.equals(((SocialCommand) other).index)); // state check
    }

    public String getMessageSocialSuccess() {
        return this.messageSocialSuccess;
    }

    public boolean isInstagramName() {
        return this.socialMedia.equals("ig");
    }

    public boolean isTwitterName() {
        return this.socialMedia.equals("tw");
    }

    public String convertToActualName(String socialMedia) {
        return (socialMedia.equals("tw") ? "Twitter" : "Instagram");
    }

}
```
###### \java\seedu\address\logic\commands\TagDeleteCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Deletes a tag identified using its tag name from the address book.
 */
public class TagDeleteCommand extends UndoableCommand {

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted tag: %1$s";
    public static final String COMMAND_WORD = "tagdelete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tag identified by the tag name in the address book.\n"
            + "Parameters: TAGNAME\n"
            + "Example: " + COMMAND_WORD + " friends";

    private final String tagName;
    private Tag tagToDelete;

    public TagDeleteCommand(String tagName) {
        requireNonNull(tagName);
        this.tagName = tagName.trim();

    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<Tag> tagList = model.getAddressBook().getTagList();

        try {
            System.out.println("" + Tag.MESSAGE_TAG_CONSTRAINTS);
            tagToDelete = new Tag(tagName);
            if (!tagList.contains(tagToDelete)) {
                throw new CommandException(Messages.MESSAGE_INVALID_TAG_NAME);
            }
            model.deleteTag(tagToDelete);
        } catch (IllegalValueException ive) {
            throw new CommandException(Tag.MESSAGE_TAG_CONSTRAINTS);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, tagName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagDeleteCommand // instanceof handles nulls
                && this.tagName.equals(((TagDeleteCommand) other).tagName)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\TagEditCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;
import static seedu.address.model.tag.Tag.isValidTagName;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Edits a tag identified using its tag name from the address book and
 * replaces it with a new tag name provided by the user.
 */
public class TagEditCommand extends UndoableCommand {
    public static final String MESSAGE_EDIT_TAG_SUCCESS = "Edited tag: %1$s to %2$s";
    public static final String COMMAND_WORD = "tagedit";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the tag identified by the tag name in the address book to a new tag name provided by the user.\n"
            + "Parameters: TAGNAME NEWTAGNAME\n"
            + "Example: " + COMMAND_WORD + " groupmates " + "friends";

    private final String oldTagName;
    private final String newTagName;
    private Tag tagToEdit;
    private Tag newTag;

    public TagEditCommand(String oldTagName, String newTagName) {
        requireNonNull(oldTagName);
        requireNonNull(newTagName);

        this.oldTagName = oldTagName.trim();
        this.newTagName = newTagName.trim();
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<Tag> tagList = model.getAddressBook().getTagList();

        try {
            newTag = new Tag(newTagName);
            tagToEdit = new Tag(oldTagName);
            if (!isValidTagName(newTag.tagName)) {
                throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
            }
            if (newTag.tagName.isEmpty() || tagToEdit.tagName.isEmpty()) {
                throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
            }
            if (!tagList.contains(tagToEdit)) {
                throw new CommandException(Messages.MESSAGE_UNKNOWN_TAG_NAME);
            }
            model.editTag(tagToEdit, newTag);
        } catch (IllegalValueException ive) {
            throw new CommandException(Tag.MESSAGE_TAG_CONSTRAINTS);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(
                String.format(
                        MESSAGE_EDIT_TAG_SUCCESS,
                        tagToEdit.toString(),
                        newTag.toString()
                )
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagEditCommand // instanceof handles nulls
                && this.oldTagName.equals(((TagEditCommand) other).oldTagName)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.person.Birthday.BIRTHDAY_MONTH_REGEX;

import java.util.Arrays;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsBirthdayPredicate;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        // Single keyword as argument and check that it contains an non-zero integer
        if (nameKeywords.length == 1) {
            System.out.println(Character.isLetter(nameKeywords[0].charAt(0)));
            if (Character.isLetter(nameKeywords[0].charAt(0))) {
                return new FindCommand(new PersonContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
            } else if (StringUtil.isNonZeroUnsignedInteger(nameKeywords[0])
                    && nameKeywords[0].matches(BIRTHDAY_MONTH_REGEX)) {
                return new FindCommand(new PersonContainsBirthdayPredicate(nameKeywords[0]));
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \java\seedu\address\logic\parser\SocialCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SocialCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SocialCommand object
 */
public class SocialCommandParser implements Parser<SocialCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SocialCommand
     * and returns an SocialCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SocialCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] argsArr = args.trim().split(" ");
        Index index;
        String socialMedia;

        if (argsArr.length == 1 || argsArr.length == 0) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argsArr[0]);
            socialMedia = argsArr[1];

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialCommand.MESSAGE_USAGE));
        }
        return new SocialCommand(index, socialMedia);
    }
}
```
###### \java\seedu\address\logic\parser\TagDeleteCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.TagDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TagDeleteCommand object
 */
public class TagDeleteCommandParser implements Parser<TagDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagDeleteCommand
     * and returns a TagDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagDeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagDeleteCommand.MESSAGE_USAGE));
        }

        return new TagDeleteCommand(trimmedArgs);
    }

}
```
###### \java\seedu\address\logic\parser\TagEditCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;
import static seedu.address.model.tag.Tag.isValidTagName;

import seedu.address.logic.commands.TagEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TagEditCommand object
 */
public class TagEditCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the TagEditCommand
     * and returns a TagEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] stringArr = args.split(" ");

        if (stringArr.length < 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagEditCommand.MESSAGE_USAGE));
        }
        if (!isValidTagName(stringArr[1]) || !isValidTagName(stringArr[2])) {
            throw new ParseException(MESSAGE_TAG_CONSTRAINTS);
        }

        String oldTag = stringArr[1].trim();
        String newTag = stringArr[2].trim();

        return new TagEditCommand(oldTag, newTag);
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException {
        for (int i = 0; i < addressBook.getPersonList().size(); i++) {
            ReadOnlyPerson oldPerson = addressBook.getPersonList().get(i);

            Person newPerson = new Person(oldPerson);
            Set<Tag> newTags = new HashSet<>(newPerson.getTags());
            newTags.remove(tag);
            newPerson.setTags(newTags);

            addressBook.updatePerson(oldPerson, newPerson);
        }
    }

    @Override
    public void editTag(Tag oldTag, Tag newTag) throws PersonNotFoundException, DuplicatePersonException {
        for (int i = 0; i < addressBook.getPersonList().size(); i++) {
            ReadOnlyPerson oldPerson = addressBook.getPersonList().get(i);
            if (oldPerson.getTags().contains(oldTag)) {
                Person newPerson = new Person(oldPerson);
                Set<Tag> newTags = new HashSet<>(newPerson.getTags());
                newTags.remove(oldTag);
                newTags.add(newTag);
                newPerson.setTags(newTags);

                addressBook.updatePerson(oldPerson, newPerson);
            }
        }
    }
```
###### \java\seedu\address\model\person\PersonContainsKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson} matches any or all of the keywords given depending on the number of keywords.
 * Case 1: Given only 1 keyword, {@code ReadOnlyPerson}'s {@code Name} OR {@code Tag} must match the keyword
 * Case 2: Given >1 keywords, {@code ReadOnlyPerson}'s {@code Name} AND {@code Tag} must match the keywords
 * More to be added later including Birthday
 */
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;
    private List<String> keywordsLower;
    private List<String> personTagsLower;
    private String[] nameArr;
    private List<Tag> tagList = new ArrayList<>();
    private List<String> masterList = new ArrayList<>();

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        keywordsLower = new ArrayList<>();
        personTagsLower = new ArrayList<>();
        setUpCaseInsensitiveKeywords();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {

        clearMasterAndTagLists();
        setUpMasterList(person);

        if (keywords.size() == 1) {

            /* Case 1: keyword is a character (aka initial)
             * searches for people with names (Both first and last names) that start with the character
             */
            if (keywordIsSingleCharacter()) {
                return findNamesWithFirstChar(keywordsLower.toString().charAt(1));
            }

            /* Case 2: keyword can either be a name or a tag
             * searches for the keyword in the person's name or tags
             */
            return keywordsLower.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                    || personTagsLower.contains(keywordsLower.get(0));
        }

        /* Case 3: more than 1 keyword
         * Only supports 1 name, but multiple tags
         * a) name + tag
         * b) tag + tag + tag...
         */
        return masterList.containsAll(keywordsLower);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

    private boolean findNamesWithFirstChar(char firstChar) {
        return (nameStartsWith(firstChar));
    }

    /**
     * This method returns true if the keyword given in the command is a single character
     */
    private boolean keywordIsSingleCharacter() {
        if (keywords.toString().length() == 3) {
            return true;
        }
        return false;
    }

    /**
     * This method returns true if any part of a person's name begins with the given character
     */
    private boolean nameStartsWith(char character) {
        for (String name : nameArr) {
            if (name.toLowerCase().charAt(0) == character) {
                return true;
            }
        }
        return false;
    }

    private void clearMasterAndTagLists() {
        masterList.clear();
        tagList.clear();
    }

    private void setUpCaseInsensitiveKeywords() {
        keywordsLower.clear();
        for (int i = 0; i < keywords.size(); i++) {
            keywordsLower.add(keywords.get(i).toLowerCase());
        }
    }

    private void setUpTagList(ReadOnlyPerson person) {
        tagList.clear();
        personTagsLower.clear();
        tagList.addAll(person.getTags());
        for (int i = 0; i < tagList.size(); i++) {
            String tagNameToAdd = tagList.get(i).toString().substring(1, tagList.get(i).toString().length() - 1);
            personTagsLower.add(tagNameToAdd.toLowerCase());
            masterList.add(tagNameToAdd.toLowerCase());
        }
    }

    private void setUpNameList(ReadOnlyPerson person) {
        nameArr = person.getName().toString().split("\\s+");
        for (int i = 0; i < nameArr.length; i++) {
            masterList.add(nameArr[i].toLowerCase());
        }
    }

    private void setUpMasterList(ReadOnlyPerson person) {
        setUpTagList(person);
        setUpNameList(person);
    }
}
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts AddressBook by name.
     */
    public void sortByName() {
        FXCollections.sort(internalList, comparator);
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Loads the person's Instagram or Twitter depending on if the person has his/her social media fields filled in.
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        if (!person.getInstagramName().toString().isEmpty()) {
            loadPage(INSTAGRAM_URL_PREFIX + person.getInstagramName());
        } else if (!person.getTwitterName().toString().isEmpty()) {
            loadPage(TWITTER_URL_PREFIX + person.getTwitterName());
        }
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * This method will use the built-in browser to open the selected index's social media profile (either Twitter
     * or Instagram).
     * @param userName is a UserName of the person
     */
    private void handleSocial(UserName userName, String socialMediaLink) {
        browserPanel.loadPage(socialMediaLink + userName);
    }


```
