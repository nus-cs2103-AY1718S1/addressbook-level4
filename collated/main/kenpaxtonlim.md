# kenpaxtonlim
###### \java\seedu\address\commons\events\ui\ChangeBrowserPanelUrlEvent.java
``` java
/**
 * Indicates a request to change the url on the browser panel.
 */
public class ChangeBrowserPanelUrlEvent extends BaseEvent {

    public final String url;

    public ChangeBrowserPanelUrlEvent(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\AddRemoveTagsCommand.java
``` java
/**
 * Adds tags to an existing person in the address book.
 */
public class AddRemoveTagsCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tags";
    public static final String COMMAND_ALIAS = "t";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add or remove tags to the person identified "
            + "by the index number used in the last person listing. \n"
            + "Parameters: TYPE (either add or remove) INDEX (must be a positive integer) "
            + "TAG [TAG] (can add 1 or more tags)... \n"
            + "Example: " + COMMAND_WORD + " add 1 "
            + "NUS CS2103 classmate";

    public static final String MESSAGE_ADD_TAGS_SUCCESS = "Added Tag/s to Person: %1$s";
    public static final String MESSAGE_REMOVE_TAGS_SUCCESS = "Removed Tag/s to Person: %1$s";
    public static final String MESSAGE_NO_TAG = "One or more tags must be entered.";

    private final boolean isAdd;
    private final Index index;
    private final Set<Tag> tags;

    public AddRemoveTagsCommand(boolean isAdd, Index index, Set<Tag> tags) {
        requireNonNull(isAdd);
        requireNonNull(index);
        requireNonNull(tags);

        this.isAdd = isAdd;
        this.index = index;
        this.tags = tags;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (tags.isEmpty()) {
            throw new CommandException(MESSAGE_NO_TAG);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson;

        if (isAdd) {
            editedPerson = addTagsToPerson(personToEdit, tags);
        } else {
            editedPerson = removeTagsFromPerson(personToEdit, tags);
        }


        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("The target person should not be duplicated");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return isAdd ? new CommandResult(String.format(MESSAGE_ADD_TAGS_SUCCESS, editedPerson))
                : new CommandResult(String.format(MESSAGE_REMOVE_TAGS_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with the {@code tags} to be added.
     */
    private static Person addTagsToPerson(ReadOnlyPerson personToEdit, Set<Tag> tags) {
        assert personToEdit != null;

        Set<Tag> personTags = personToEdit.getTags();
        HashSet<Tag> newTags = new HashSet<Tag>(personTags);
        newTags.addAll(tags);

        AccessCount accessCount = new AccessCount((personToEdit.getAccessCount().numAccess() + 1));

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), newTags,
                personToEdit.getCreatedAt(), personToEdit.getSocialMedia(), accessCount);

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with the {@code tags} to be removed.
     */
    private static Person removeTagsFromPerson(ReadOnlyPerson personToEdit, Set<Tag> tags)  {
        assert personToEdit != null;

        Set<Tag> personTags = personToEdit.getTags();
        HashSet<Tag> newTags = new HashSet<Tag>(personTags);
        newTags.removeAll(tags);

        AccessCount accessCount = new AccessCount(personToEdit.getAccessCount().numAccess() + 1);

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), newTags, personToEdit.getCreatedAt(),
                personToEdit.getSocialMedia(), accessCount);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddRemoveTagsCommand)) {
            return false;
        }

        AddRemoveTagsCommand e = (AddRemoveTagsCommand) other;
        return isAdd == e.isAdd && index.equals(e.index) && tags.equals(e.tags);
    }
}
```
###### \java\seedu\address\logic\commands\SocialMediaCommand.java
``` java
/**
 * Display social media page of the person identified using it's last displayed index from the address book.
 */
public class SocialMediaCommand extends Command {

    public static final String COMMAND_WORD = "socialmedia";
    public static final String COMMAND_ALIAS = "sm";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the social media of the person identified by the index number used in the last person listing.\n"
            + "Parameters: TYPE (either \"facebook\", \"twitter\", or\"instagram\")"
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "facebook 1";

    public static final String TYPE_FACEBOOK = "facebook";
    public static final String TYPE_TWITTER = "twitter";
    public static final String TYPE_INSTAGRAM = "instagram";

    public static final String URL_FACEBOOK = "https://www.facebook.com/";
    public static final String URL_TWITTER = "https://twitter.com/";
    public static final String URL_INSTAGRAM = "https://www.instagram.com/";

    public static final String MESSAGE_SUCCESS = "Social media shown!";
    public static final String MESSAGE_NO_FACEBOOK = "This person has no facebook.";
    public static final String MESSAGE_NO_TWITTER = "This person has no twitter.";
    public static final String MESSAGE_NO_INSTAGRAM = "This person has no instagram.";
    public static final String MESSAGE_INVALID_TYPE = "No such social media type.";

    private final Index targetIndex;
    private final String type;

    public SocialMediaCommand(Index targetIndex, String type) {
        this.targetIndex = targetIndex;
        this.type = type;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        ReadOnlyPerson personToEdit = lastShownList.get(targetIndex.getZeroBased());
        personToEdit.incrementAccess();

        switch(type) {
        case TYPE_FACEBOOK:
            if (personToEdit.getSocialMedia().facebook.equals("")) {
                throw new CommandException(MESSAGE_NO_FACEBOOK);
            } else {
                EventsCenter.getInstance().post(new ChangeBrowserPanelUrlEvent(
                        URL_FACEBOOK + personToEdit.getSocialMedia().facebook));
            }
            break;
        case TYPE_TWITTER:
            if (personToEdit.getSocialMedia().twitter.equals("")) {
                throw new CommandException(MESSAGE_NO_TWITTER);
            } else {
                EventsCenter.getInstance().post(new ChangeBrowserPanelUrlEvent(
                        URL_TWITTER + personToEdit.getSocialMedia().twitter));
            }
            break;
        case TYPE_INSTAGRAM:
            if (personToEdit.getSocialMedia().instagram.equals("")) {
                throw new CommandException(MESSAGE_NO_INSTAGRAM);
            } else {
                EventsCenter.getInstance().post(new ChangeBrowserPanelUrlEvent(
                        URL_INSTAGRAM + personToEdit.getSocialMedia().instagram));
            }
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_TYPE);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SocialMediaCommand)) {
            return false;
        }

        SocialMediaCommand e = (SocialMediaCommand) other;
        return targetIndex.equals(e.targetIndex) && type.equals(e.type);
    }
}
```
###### \java\seedu\address\logic\parser\AddRemoveTagsCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddRemoveTagsCommand object
 */
public class AddRemoveTagsCommandParser implements Parser<AddRemoveTagsCommand> {

    private static final int ARGUMENT_START_INDEX = 1;
    private static final int TYPE_ARGUMENT_INDEX = 0;
    private static final int INDEX_ARGUMENT_INDEX = 1;
    private static final int TAG_ARGUMENT_INDEX = 2;

    private static final String TYPE_ADD = "add";
    private static final String TYPE_REMOVE = "remove";
    /**
     * Parses the given {@code String} of arguments in the context of the AddRemoveTagsCommand
     * and returns an AddRemoveTagsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddRemoveTagsCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (args.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemoveTagsCommand.MESSAGE_USAGE));
        }

        List<String> argsList = Arrays.asList(args.substring(ARGUMENT_START_INDEX).split(" "));

        if (argsList.size() < TAG_ARGUMENT_INDEX + 1 || argsList.contains("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemoveTagsCommand.MESSAGE_USAGE));
        }

        boolean isAdd;
        if (argsList.get(TYPE_ARGUMENT_INDEX).equals(TYPE_ADD)) {
            isAdd = true;
        } else if (argsList.get(TYPE_ARGUMENT_INDEX).equals(TYPE_REMOVE)) {
            isAdd = false;
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemoveTagsCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argsList.get(INDEX_ARGUMENT_INDEX));
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemoveTagsCommand.MESSAGE_USAGE));
        }

        List<String> tagsList = argsList.subList(TAG_ARGUMENT_INDEX, argsList.size());
        Set<Tag> tagsSet;
        try {
            tagsSet = ParserUtil.parseTags(tagsList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        return new AddRemoveTagsCommand(isAdd, index, tagsSet);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses three {@code String facebook, twitter, instagram} into an {@code SocialMedia}.
     */
    public static SocialMedia parseSocialMedia(Optional<String> facebook,
            Optional<String> twitter, Optional<String> instagram) throws IllegalValueException {
        requireAllNonNull(facebook, twitter, instagram);

        String fb = facebook.isPresent() ? facebook.get() : "";
        String tw = twitter.isPresent() ? twitter.get() : "";
        String in = instagram.isPresent() ? instagram.get() : "";

        return new SocialMedia(fb, tw, in);
    }
```
###### \java\seedu\address\logic\parser\SocialMediaCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SocialMediaCommand object
 */
public class SocialMediaCommandParser implements Parser<SocialMediaCommand> {

    private static final int ARGUMENT_START_INDEX = 1;
    private static final int TYPE_ARGUMENT_INDEX = 0;
    private static final int INDEX_ARGUMENT_INDEX = 1;

    @Override
    public SocialMediaCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (args.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE));
        }

        List<String> argsList = Arrays.asList(args.substring(ARGUMENT_START_INDEX).split(" "));

        if (argsList.size() < INDEX_ARGUMENT_INDEX + 1 || argsList.contains("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE));
        }

        String type = argsList.get(TYPE_ARGUMENT_INDEX);
        if (!(type.equals(SocialMediaCommand.TYPE_FACEBOOK)
                || type.equals(SocialMediaCommand.TYPE_TWITTER)
                || type.equals(SocialMediaCommand.TYPE_INSTAGRAM))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argsList.get(INDEX_ARGUMENT_INDEX));
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE));
        }

        return new SocialMediaCommand(index, type);
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public ObjectProperty<SocialMedia> socialMediaProperty() {
        return socialMedia;
    }

    @Override
    public SocialMedia getSocialMedia() {
        return socialMedia.get();
    }

    public void setSocialMedia(SocialMedia socialMedia) {
        this.socialMedia.set(requireNonNull(socialMedia));
    }
```
###### \java\seedu\address\model\person\SocialMedia.java
``` java
/**
 * Represents a Person's social media usernames in the address book.
 */
public class SocialMedia {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Social media username should be alphanumeric without spaces";
    public static final String USERNAME_VALIDATION_REGEX = "[^\\s]+|[\\s*]";

    public final String facebook;
    public final String twitter;
    public final String instagram;

    /**
     * All usernames are empty.
     */
    public SocialMedia() {
        facebook = "";
        twitter = "";
        instagram = "";
    }

    /**
     * Validates given usernames.
     *
     * @throws IllegalValueException if either of given username string is invalid.
     */
    public SocialMedia(String facebook, String twitter, String instagram) throws IllegalValueException {
        if (facebook == null) {
            facebook = "";
        }

        if (twitter == null) {
            twitter = "";
        }

        if (instagram == null) {
            instagram = "";
        }

        /*if(!isValidName(facebook) || !isValidName(twitter) || !isValidName(instagram)) {
            throw new IllegalValueException(MESSAGE_USERNAME_CONSTRAINTS);
        }*/

        this.facebook = facebook;
        this.twitter = twitter;
        this.instagram = instagram;
    }

    public SocialMedia(SocialMedia oldData, SocialMedia newData) {
        facebook = newData.facebook.equals("") ? oldData.facebook : newData.facebook;
        twitter = newData.twitter.equals("") ? oldData.twitter : newData.twitter;
        instagram = newData.instagram.equals("") ? oldData.instagram : newData.instagram;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        String toString = "";

        if (!facebook.equals("")) {
            toString += "FB: " + facebook + " ";
        }
        if (!twitter.equals("")) {
            toString += "TW: " + twitter + " ";
        }
        if (!instagram.equals("")) {
            toString += "IG: " + instagram;
        }
        if (toString.equals("")) {
            toString = "-No Social Media Accounts-";
        }
        return toString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SocialMedia // instanceof handles nulls
                && this.facebook.equals(((SocialMedia) other).facebook)
                && this.twitter.equals(((SocialMedia) other).twitter)
                && this.instagram.equals(((SocialMedia) other).instagram)); // state check
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleChangeBrowserPanelUrlEvent(ChangeBrowserPanelUrlEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPage(event.url);
    }
```
