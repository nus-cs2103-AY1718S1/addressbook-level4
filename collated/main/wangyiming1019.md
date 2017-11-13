# wangyiming1019
###### \java\seedu\address\logic\commands\AddTagCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_FULL;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Adds a tag to the persons in the latest list from the address book.
 */
public class AddTagCommand extends AddCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_TAG_FULL
            + ": Adds the tag to the persons with the index numbers used in the last person list."
            + " Command is case-sensitive. \n"
            + "Parameters: "
            + "[INDEX] [MORE INDEXES] (index must be a positive integer)"
            + "[" + PREFIX_TAG + "TAG]... \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG_FULL + " 1 2 t/friends \n";


    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added Tag: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in all persons in the current list.";

    private final ArrayList<Index> targetIndexes;
    private final Tag addTag;

    /**
     * @param targetIndexes of the persons in the filtered person list to edit
     * @param addTag tag to add to given target indexes
     */
    public AddTagCommand(Tag addTag, ArrayList<Index> targetIndexes) {

        requireNonNull(targetIndexes);
        requireNonNull(addTag);

        this.targetIndexes = targetIndexes;
        this.addTag = addTag;
    }
    /**
     * @param addTag tag to add to all entries in the address book
     */
    public AddTagCommand(Tag addTag) {

        requireNonNull(addTag);

        this.targetIndexes = new ArrayList<>();
        this.addTag = addTag;
    }
    /**
     * Check whether the index within the range then checks whether the specific persons have the tag.
     * If not, add the tag to the person that doesn't have the given tag.
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        boolean allPersonsContainGivenTag = true;
        if (targetIndexes.size() == 0) {
            reinitlializeArray(lastShownList.size());
        }
        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
        // check whether all persons have the given tag
        for (int i = 0; i < targetIndexes.size(); i++) {
            int personIndex = targetIndexes.get(i).getZeroBased();
            ReadOnlyPerson personToAddTag = lastShownList.get(personIndex);
            if (!personToAddTag.getTags().contains(addTag)) {
                allPersonsContainGivenTag = false;
            }
        }

        if (allPersonsContainGivenTag) {
            throw  new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        try {
            model.addTag(this.addTag, this.targetIndexes);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, addTag));
    }

    private void reinitlializeArray(int size) {
        for (int i = 0; i < size; i++) {
            targetIndexes.add(Index.fromZeroBased(i));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        // state check
        AddTagCommand e = (AddTagCommand) other;
        return targetIndexes.equals(e.targetIndexes)
                && addTag.equals(e.addTag);
    }
}
```
###### \java\seedu\address\logic\commands\DeleteTagCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_FULL;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Deletes a tag from identified persons using the last displayed indexes from the address book.
 */
public class DeleteTagCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_TAG_FULL
            + ": Deletes the tag from the persons with the index numbers used in the last person list."
            + " Command is case-sensitive. \n"
            + "Parameters: "
            + "[INDEX] [MORE INDEXES] (index must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]... \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG_FULL + " 1 2 t/friends \n";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_NONEXISTENT_TAG = "This is an nonexistent tag in the given persons list.";

    private final ArrayList<Index> targetIndexes;
    private final Tag toDelete;

    /**
     * @param targetIndexes of the persons in the filtered person list to edit
     * @param toDelete tag to delete from given target indexes
     */
    public DeleteTagCommand(Tag toDelete, ArrayList<Index> targetIndexes) {

        requireNonNull(targetIndexes);
        requireNonNull(toDelete);

        this.targetIndexes = targetIndexes;
        this.toDelete = toDelete;
    }
    /**
     * @param toDelete tag to delete from given target indexes
     */
    public DeleteTagCommand(Tag toDelete) {

        requireNonNull(toDelete);
        this.targetIndexes = new ArrayList<>();
        this.toDelete = toDelete;
    }
    /**
     * Check whether the index within the range then checks whether the tag exists among the specific persons.
     * If yes, delete the tag from the specific person in the person list.
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        boolean nonexistentTag = true;

        if (targetIndexes.size() == 0) {
            reinitlializeArray(lastShownList.size());
        }

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
        // check any person have the tag
        for (int i = 0; i < targetIndexes.size(); i++) {
            int targetIndex = targetIndexes.get(i).getZeroBased();
            ReadOnlyPerson readOnlyPerson = lastShownList.get(targetIndex);
            if (readOnlyPerson.getTags().contains(toDelete)) {
                nonexistentTag = false;
            }
        }

        if (nonexistentTag) {
            throw  new CommandException(MESSAGE_NONEXISTENT_TAG);
        }

        try {
            model.deleteTag(this.toDelete, this.targetIndexes);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, toDelete));
    }

    private void reinitlializeArray(int size) {
        for (int i = 0; i < size; i++) {
            targetIndexes.add(Index.fromZeroBased(i));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        // state check
        DeleteTagCommand e = (DeleteTagCommand) other;
        return targetIndexes.equals(e.targetIndexes)
                && toDelete.equals(e.toDelete);
    }
}
```
###### \java\seedu\address\logic\commands\EditPersonCommand.java
``` java
        public void setFavourite(Boolean favourite) {
            this.favourite = favourite;
        }

        public Optional<Boolean> getFavourite() {
            return Optional.ofNullable(favourite);
        }
```
###### \java\seedu\address\logic\commands\FavouriteCommand.java
``` java
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;


/**
 * Favourites a person identified using it's last displayed index from the address book.
 */
public class FavouriteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "favourite";
    public static final String COMMAND_ALIAS = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Favourites the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAVOURITE_PERSON_SUCCESS = "Favourited Person: %1$s";
    public static final String MESSAGE_DUPLICATE_FAVOURITE = "Person is already favourited.";

    private final Index targetIndex;

    public FavouriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToFavourite = lastShownList.get(targetIndex.getZeroBased());
        Person editedPerson = new Person(personToFavourite.getName(),
                personToFavourite.getPhone(), personToFavourite.getEmail(),
                personToFavourite.getAddress(), true,
                personToFavourite.getRemark(), personToFavourite.getAvatar(),
                personToFavourite.getTags());

        try {
            model.updatePerson(personToFavourite, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_FAVOURITE);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_FAVOURITE_PERSON_SUCCESS, personToFavourite));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteCommand // instanceof handles nulls
                && this.targetIndex.equals(((FavouriteCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FavouriteListCommand.java
``` java
import seedu.address.model.person.NameContainsFavouritePredicate;

/**
 * Lists all favourited persons in the address book to the user.
 */
public class FavouriteListCommand extends Command {

    public static final String COMMAND_WORD = "showfavourite";
    public static final String COMMAND_ALIAS = "sfav";

    public static final String MESSAGE_SUCCESS = "Listed all favourited persons.";

    private static final NameContainsFavouritePredicate predicate = new NameContainsFavouritePredicate();

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\FindTagCommand.java
``` java
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_FULL;

import seedu.address.model.person.NameContainsTagsPredicate;

/**
 * Finds and lists all persons in address book who has a tag that contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTagCommand extends FindCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_TAG_FULL
            + ": Finds all persons whose tags contain any of "
            + "the specified tags (not case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG_FULL + " friends colleagues";

    private final NameContainsTagsPredicate predicate;

    public FindTagCommand(NameContainsTagsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagCommand // instanceof handles nulls
                && this.predicate.equals(((FindTagCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\TagListCommand.java
``` java
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
```
###### \java\seedu\address\logic\commands\UnfavouriteCommand.java
``` java
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;


/**
 * Unfavourites a person identified using it's last displayed index from the address book.
 */
public class UnfavouriteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unfavourite";
    public static final String COMMAND_ALIAS = "unfav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unfavourites the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNFAVOURITE_PERSON_SUCCESS = "Unfavourited Person: %1$s";
    public static final String MESSAGE_NOTFAVOURITEYET_PERSON = "Person is not favourited yet.";

    private final Index targetIndex;

    public UnfavouriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUnfavourite = lastShownList.get(targetIndex.getZeroBased());
        Person editedPerson = new Person(personToUnfavourite.getName(),
                personToUnfavourite.getPhone(), personToUnfavourite.getEmail(),
                personToUnfavourite.getAddress(), false,
                personToUnfavourite.getRemark(), personToUnfavourite.getAvatar(),
                personToUnfavourite.getTags());

        try {
            model.updatePerson(personToUnfavourite, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_NOTFAVOURITEYET_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_UNFAVOURITE_PERSON_SUCCESS, personToUnfavourite));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnfavouriteCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnfavouriteCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FavouriteCommand.COMMAND_WORD:
        case FavouriteCommand.COMMAND_ALIAS:
            return new FavouriteCommandParser().parse(arguments);

        case FavouriteListCommand.COMMAND_WORD:
        case FavouriteListCommand.COMMAND_ALIAS:
            return new FavouriteListCommand();

        case UnfavouriteCommand.COMMAND_WORD:
        case UnfavouriteCommand.COMMAND_ALIAS:
            return new UnfavouriteCommandParser().parse(arguments);

        case TagListCommand.COMMAND_WORD:
        case TagListCommand.COMMAND_ALIAS:
            return new TagListCommand();
```
###### \java\seedu\address\logic\parser\AddTagCommandParser.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class AddTagCommandParser extends AddCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns a AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        try {
            String tagName = argMultimap.getValue(PREFIX_TAG).orElse("");
            Tag toAdd = new Tag(tagName);
            String indexes = argMultimap.getPreamble();

            if (indexes.trim().isEmpty()) {
                return new AddTagCommand(toAdd);
            }
            ArrayList<Index> indexList = toArrayList(indexes);

            return new AddTagCommand(toAdd, indexList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns an ArrayList of the indexes in the given {@code String}
     */
    protected static ArrayList<Index> toArrayList(String indexes) throws IllegalValueException {
        ArrayList<Index> indexList = new ArrayList<Index>();
        String[] indexArray = indexes.split(" ");
        for (String s: indexArray) {
            if (!s.isEmpty()) {
                indexList.add(ParserUtil.parseIndex(s));
            }
        }
        return indexList;
    }
}
```
###### \java\seedu\address\logic\parser\DeleteTagCommandParser.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteTagCommand object
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTagCommand
     * and returns a DeleteTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }
        try {
            String indexes = argMultimap.getPreamble();
            String tagName = argMultimap.getValue(PREFIX_TAG).orElse("");
            Tag toDelete = new Tag(tagName);
            if (indexes.trim().isEmpty()) {
                return new DeleteTagCommand(toDelete);
            }
            ArrayList<Index> indexList = convertToArrayList(indexes);
            return new DeleteTagCommand(toDelete, indexList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns an ArrayList of the indexes in the given {@code String}
     */
    private static ArrayList<Index> convertToArrayList(String indexes) throws IllegalValueException {
        ArrayList<Index> indexList = new ArrayList<Index>();
        String[] indexArray = indexes.split(" ");
        for (String s: indexArray) {
            if (!s.isEmpty()) {
                indexList.add(ParserUtil.parseIndex(s));
            }
        }
        return indexList;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\logic\parser\FavouriteCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FavouriteCommand object
 */
public class FavouriteCommandParser implements Parser<FavouriteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FavouriteCommand
     * and returns an FavouriteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FavouriteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FavouriteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\FindTagCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsTagsPredicate;

/**
 * Parses input arguments and creates a new FindTagCommand object
 */
public class FindTagCommandParser implements Parser<FindTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagCommand
     * and returns an FindTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");
        ArrayList<String> tagToFindList = new ArrayList<String>();
        for (String tagToFind : tagKeywords) {
            tagToFindList.add(tagToFind);
        }
        return new FindTagCommand(new NameContainsTagsPredicate(tagToFindList));
    }

}
```
###### \java\seedu\address\logic\parser\UnfavouriteCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UnfavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnfavouriteCommand object
 */
public class UnfavouriteCommandParser implements Parser<UnfavouriteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnfavouriteCommand
     * and returns an UnfavouriteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnfavouriteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnfavouriteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfavouriteCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Favourites the given person {@code target} to this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code target} is not in this {@code AddressBook}.
     */
    public void favouritePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        persons.favouritePerson(target);
    }

    /**
     * Unfavourites the given person {@code target} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code target} is not in this {@code AddressBook}.
     */
    public void unfavouritePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        persons.unfavouritePerson(target);
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /** Deletes given tag from specific persons */
    void deleteTag(Tag toDelete, ArrayList<Index> targetIndexes) throws PersonNotFoundException,
            DuplicatePersonException;
```
###### \java\seedu\address\model\Model.java
``` java
    /** Adds given tag to specific persons */
    void addTag(Tag toAdd, ArrayList<Index> targetIndexes) throws PersonNotFoundException,
            DuplicatePersonException;
```
###### \java\seedu\address\model\Model.java
``` java
    /** Favourites the given person */
    void favouritePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Unfavourites the given person */
    void unfavouritePerson(ReadOnlyPerson target) throws PersonNotFoundException;
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Delete input tag from the specific persons shown in the last list.
     */
    @Override
    public synchronized void deleteTag(Tag toDelete, ArrayList<Index> personIndexes) throws PersonNotFoundException,
            DuplicatePersonException {
        for (int i = 0; i < personIndexes.size(); i++) {
            int index = personIndexes.get(i).getZeroBased();
            ReadOnlyPerson personWithTag = this.getFilteredPersonList().get(index);
            Person personWithoutTag = new Person(personWithTag);
            Set<Tag> newTags = new HashSet<Tag>(personWithoutTag.getTags());
            newTags.remove(toDelete);
            personWithoutTag.setTags(newTags);
            addressBook.updatePerson(personWithTag, personWithoutTag);
            indicateAddressBookChanged();
        }
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Adds input tag to the specific persons shown in the last list.
     */
    @Override
    public synchronized void addTag(Tag toAdd, ArrayList<Index> personIndexes) throws PersonNotFoundException,
            DuplicatePersonException {
        for (int i = 0; i < personIndexes.size(); i++) {
            int index = personIndexes.get(i).getZeroBased();
            ReadOnlyPerson personWithoutTag = this.getFilteredPersonList().get(index);
            Person personWithTag = new Person(personWithoutTag);
            Set<Tag> newTags = new HashSet<Tag>(personWithTag.getTags());
            newTags.add(toAdd);
            personWithTag.setTags(newTags);
            addressBook.updatePerson(personWithoutTag, personWithTag);
            indicateAddressBookChanged();
        }
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void favouritePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.favouritePerson(target);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void unfavouritePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.unfavouritePerson(target);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\NameContainsFavouritePredicate.java
``` java
import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} are favoured.
 */
public class NameContainsFavouritePredicate implements Predicate<ReadOnlyPerson> {

    public NameContainsFavouritePredicate() {
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getFavourite();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsFavouritePredicate); // instanceof handles nulls
    }
}
```
###### \java\seedu\address\model\person\NameContainsTagsPredicate.java
``` java
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the tags given.
 */
public class NameContainsTagsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> tags;

    public NameContainsTagsPredicate(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        String allTagString = convertTagToString(person);
        final List<String> wantedTag = new ArrayList<>();
        final List<String> unwantedTag = new ArrayList<>();
        updateWantedTagUnwantedTag(wantedTag, unwantedTag);
        boolean isOnlyUnwantedTags = isOnlyUnwantedTagsCheck(wantedTag, unwantedTag);

        if (isOnlyUnwantedTags) {
            return !(unwantedTag.stream()
                    .anyMatch((inputTag -> StringUtil.containsWordIgnoreCase(allTagString, inputTag))));
        }

        return wantedTag.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(allTagString, keyword))
                && !(unwantedTag.stream()
                .anyMatch((keyword -> StringUtil.containsWordIgnoreCase(allTagString, keyword))));
    }

    /**
     * check only unwanted tag list has elements
     * @return a boolean value
     */

    private boolean isOnlyUnwantedTagsCheck(List<String> wantedTag,
                                            List<String> unwantedTag) {
        if (wantedTag.isEmpty() && !unwantedTag.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Update the wantedTag and unwantedTag list
     * @param wantedTag list of tags to be searched
     * @param unwantedTag list of tags to not be searched
     */

    private void updateWantedTagUnwantedTag(List<String> wantedTag, List<String> unwantedTag) {
        for (String everyTag : tags) {
            if (!everyTag.startsWith("/")) {
                wantedTag.add(everyTag);
            } else {
                unwantedTag.add(everyTag.substring(1));
            }
        }

    }

    /**
     * Convert a set of tags to Strings
     */

    private String convertTagToString(ReadOnlyPerson person) {
        Set<Tag> personTags = person.getTags();
        StringBuilder allTagNames = new StringBuilder();
        for (Tag tag : personTags) {
            allTagNames.append(tag.getTagName());
            allTagNames.append(" ");
        }
        return allTagNames.toString().trim();
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setFavourite(Boolean favourite) {
        this.favourite.set(requireNonNull(favourite));
    }

    @Override
    public ObjectProperty<Boolean> favouriteProperty() {
        return favourite;
    }

    @Override
    public Boolean getFavourite() {
        return favourite.get();
    }
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Favourites the equivalent person in the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public void favouritePerson(ReadOnlyPerson toFavourite) throws PersonNotFoundException {
        requireNonNull(toFavourite);
        int index = internalList.indexOf(toFavourite);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        internalList.get(index).setFavourite(true);
    }

    /**
     * Unfavourites the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public void unfavouritePerson(ReadOnlyPerson toUnfavourite) throws PersonNotFoundException {
        requireNonNull(toUnfavourite);
        int index = internalList.indexOf(toUnfavourite);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        internalList.get(index).setFavourite(false);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * display the heart for favourite person
     */
    private void initFavourite(ReadOnlyPerson person) {
        boolean favouriteStatus = person.getFavourite();
        Label favouriteLabel = new Label();
        Image starFilled = new Image(getClass().getResource("/images/heart.png").toExternalForm());
        if (favouriteStatus) {
            favouriteLabel.setGraphic(new ImageView(starFilled));
            favouriteLabel.setStyle("-fx-background-color: transparent; -fx-border-color: transparent");
        }
        cardPane.getChildren().add(favouriteLabel);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Locate hashed colour for tag. If not found, new colour is assigned to tag
     * @param tag
     * @return
     */
    private String getTagColour(String tag) {
        if (!colourHash.containsKey(tag)) {
            int randomiser = randomNumber.nextInt(Colours.values().length);
            String colour = Colours.values()[randomiser].toString();
            colourHash.put(tag, colour);
        }
        return colourHash.get(tag);
    }

    /**
     * Assigns each tag a colour
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label newTagLabel = new Label(tag.getTagName());

            newTagLabel.setStyle("-fx-background-color: " + this.getTagColour(tag.getTagName()));

            tags.getChildren().add(newTagLabel);
        });
    }
```
