# Chng-Zhi-Xuan
###### \java\seedu\address\logic\commands\BookmarkCommand.java
``` java
/**
 * Bookmarks a place identified using it's last displayed index from the address book.
 */
public class BookmarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "bookmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds bookmark to specified place.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_BOOKMARK_SUCCESS = "Bookmarked place: %1$s";

    private static final String MESSAGE_DUPLICATE_TAGS = "Place already has that tag.";

    private final Index targetIndex;
    private final String bookmarkString = "Bookmarked";

    public BookmarkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPlace> lastShownList = model.getFilteredPlaceList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
        }

        ReadOnlyPlace placeToBookmark = lastShownList.get(targetIndex.getZeroBased());

        try {
            Tag bookmarkTag = new Tag(bookmarkString);
            model.addTag(placeToBookmark, bookmarkTag);
        } catch (DuplicatePlaceException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PLACE);
        } catch (PlaceNotFoundException pnfe) {
            assert false : "The target place cannot be missing";
        } catch (UniqueTagList.DuplicateTagException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TAGS);
        } catch (IllegalValueException e) {
            assert false : "Tag cannot be invalid";
        }

        return new CommandResult(String.format(MESSAGE_BOOKMARK_SUCCESS, placeToBookmark));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookmarkCommand // instanceof handles nulls
                && this.targetIndex.equals(((BookmarkCommand) other).targetIndex)); // state check
    }
}

```
###### \java\seedu\address\logic\commands\ClearBookmarkCommand.java
``` java
/**
 * Clears all bookmark tags from all places
 */
public class ClearBookmarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear_bookmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": clears all bookmarks in address book\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_CLEAR_BOOKMARK_SUCCESS = "Cleared bookmarks";
    private final String bookmarkString = "Bookmarked";

    public ClearBookmarkCommand() {

    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        Tag toRemove = null;

        model.updateFilteredPlaceList(PREDICATE_SHOW_ALL_PLACES);


        try {
            toRemove = new Tag (bookmarkString);
            model.removeAllTags(toRemove);
        } catch (DuplicatePlaceException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PLACE);
        } catch (PlaceNotFoundException pnfe) {
            assert false : "The target place cannot be missing";
        } catch (IllegalValueException e) {
            assert false : "Tag cannot be invalid";
        }

        return new CommandResult(String.format(MESSAGE_CLEAR_BOOKMARK_SUCCESS, toRemove.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClearBookmarkCommand); // instanceof handles nulls
    }
}

```
###### \java\seedu\address\logic\commands\DirCommand.java
``` java
/**
 * Shows direction from place1 to place2 using their last displayed index in the tourist book
 */
public class DirCommand extends Command {

    public static final String COMMAND_WORD = "dir";
    public static final String COMMAND_WORD_ALIAS = "Dir";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the direction from one place to another, specified by their index used in the last list"
            + " that it was shown.\n"
            + "Parameters: INDEX1 INDEX2 (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DIR_SUCCESS = "Directions shown on Google maps.";

    private final Index indexFrom;
    private final Index indexTo;
    private final String emptySpace = " ";
    private final String plus = "+";
    private final String singaporeConcat = "+Singapore+";

    public DirCommand(Index indexFrom, Index indexTo) {
        this.indexFrom = indexFrom;
        this.indexTo = indexTo;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPlace> lastShownList = model.getFilteredPlaceList();

        if (indexFrom.getZeroBased() == indexTo.getZeroBased()
            || indexFrom.getZeroBased() >= lastShownList.size()
            || indexTo.getZeroBased() >= lastShownList.size()) {

            throw new CommandException(Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
        }

        ReadOnlyPlace placeStart = lastShownList.get(indexFrom.getZeroBased());
        ReadOnlyPlace placeDestination = lastShownList.get(indexTo.getZeroBased());

        String fullUrl = "https://www.google.com.sg/maps/dir/"
                + placeStart.getName().fullName.replaceAll(emptySpace, plus)
                + singaporeConcat
                + placeStart.getPostalCode()
                + "/"
                + placeDestination.getName().fullName.replaceAll(emptySpace, plus)
                + singaporeConcat
                + placeDestination.getPostalCode();



        MainWindow.loadUrl(fullUrl);


        EventsCenter.getInstance().post(new GotoRequestEvent(indexFrom));
        return new CommandResult(String.format(MESSAGE_DIR_SUCCESS, indexFrom.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DirCommand // instanceof handles nulls
                && this.indexTo.equals(((DirCommand) other).indexTo)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case DirCommand.COMMAND_WORD:
        case DirCommand.COMMAND_WORD_ALIAS:
            return new DirCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ClearBookmarkCommand.COMMAND_WORD:
            return new ClearBookmarkCommandParser().parse(arguments);

        case BookmarkCommand.COMMAND_WORD:
            return new BookmarkCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\BookmarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new BookmarkCommand object
 */
public class BookmarkCommandParser implements Parser<BookmarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BookmarkCommand
     * and returns an BookmarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BookmarkCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new BookmarkCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookmarkCommand.MESSAGE_USAGE));
        }
    }

}

```
###### \java\seedu\address\logic\parser\ClearBookmarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ClearBookmarkCommand object
 */
public class ClearBookmarkCommandParser implements Parser<ClearBookmarkCommand> {

    private final String emptyString = "";

    /**
     * Parses the given {@code String} of arguments in the context of the ClearBookmarkCommand
     * and returns an ClearBookmarkCommand object for execution.
     * @throws ParseException if the user provided any arguments
     */
    public ClearBookmarkCommand parse(String args) throws ParseException {

        if (!args.trim().equals(emptyString)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearBookmarkCommand.MESSAGE_USAGE));
        } else {
            return new ClearBookmarkCommand();
        }
    }

}
```
###### \java\seedu\address\logic\parser\DirCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DirCommand object
 */
public class DirCommandParser implements Parser<DirCommand> {

    private final int indexFirst = 0;
    private final int indexSecond = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the DirCommand
     * and returns an DirCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DirCommand parse(String args) throws ParseException {

        try {
            Index fromIndex = ParserUtil.parseIndexFromPosition(args, indexFirst);
            Index toIndex = ParserUtil.parseIndexFromPosition(args, indexSecond);
            return new DirCommand(fromIndex, toIndex);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DirCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code oneBasedIndex} into an {@code Index} at the given position and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer) or
     * if the position is out of string index bounds.
     */
    public static Index parseIndexFromPosition(String oneBasedIndex, int zeroBasedPosition)
                                                        throws IllegalValueException {
        String indexAtPosition = "";
        String[] indexes;

        try {
            String trimmedIndex = oneBasedIndex.trim();
            indexes = trimmedIndex.split(" ");

            indexAtPosition = indexes[zeroBasedPosition];

        } catch (IndexOutOfBoundsException iobe) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }

        if (!StringUtil.isNonZeroUnsignedInteger(indexAtPosition)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(indexAtPosition));

    }
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Adds tag to target place in address book.
     *
     * @throws PlaceNotFoundException if place cannot be found in the list.
     *
     * @throws DuplicatePlaceException if after removal, the place's details causes the place to be
     *      equivalent to another existing place in the list.
     *
     * @throws UniqueTagList.DuplicateTagException if added tag is a duplicate.
     */
    void addTag(ReadOnlyPlace place, Tag tag) throws DuplicatePlaceException, PlaceNotFoundException,
                                                    UniqueTagList.DuplicateTagException;

    /**
     * Removes given tag from all places in address book.
     *
     * @throws PlaceNotFoundException if place cannot be found in the list.
     *
     * @throws DuplicatePlaceException if after removal, the place's details causes the place to be
     *      equivalent to another existing place in the list.
     */
    void removeAllTags(Tag tagName) throws PlaceNotFoundException, DuplicatePlaceException;
```
###### \java\seedu\address\model\Model.java
``` java

    /**
     * Replaces the given place {@code target} with {@code editedPlace}.
     *
     * @throws DuplicatePlaceException if updating the place's details causes the place to be equivalent to
     *      another existing place in the list.
     * @throws PlaceNotFoundException if {@code target} could not be found in the list.
     */
    void updatePlace(ReadOnlyPlace target, ReadOnlyPlace editedPlace)
            throws DuplicatePlaceException, PlaceNotFoundException;

    /** Returns an unmodifiable view of the filtered place list */
    ObservableList<ReadOnlyPlace> getFilteredPlaceList();

    /**
     * Updates the filter of the filtered place list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPlaceList(Predicate<ReadOnlyPlace> predicate);

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Adds given tag to target place
     * @param place
     * @param newTag
     * @throws DuplicatePlaceException
     * @throws PlaceNotFoundException
     * @throws UniqueTagList.DuplicateTagException
     */
    public void addTag(ReadOnlyPlace place, Tag newTag) throws DuplicatePlaceException, PlaceNotFoundException,
                                                                    UniqueTagList.DuplicateTagException {
        Predicate oldPredicate = filteredPlaces.getPredicate();
        filteredPlaces.setPredicate(PREDICATE_SHOW_ALL_PLACES);

        Place updatedPlace = new Place(place);

        Set<Tag> currentTags = updatedPlace.getTags();
        Set<Tag> updatedTags = new HashSet<Tag>();

        updatedTags.addAll(currentTags);

        if (updatedTags.contains(newTag)) {
            throw new UniqueTagList.DuplicateTagException();
        } else {
            updatedTags.add(newTag);
            updatedPlace.setTags(updatedTags);
            addressBook.updatePlace(place, updatedPlace);
            indicateAddressBookChanged();
        }

        filteredPlaces.setPredicate(oldPredicate);
    }

    /**
     * Removes given Tag from all places in address book
     * @param tag
     * @throws PlaceNotFoundException
     * @throws DuplicatePlaceException
     */

    public void removeAllTags(Tag tag) throws PlaceNotFoundException, DuplicatePlaceException {
        Predicate oldPredicate = filteredPlaces.getPredicate();
        filteredPlaces.setPredicate(PREDICATE_SHOW_ALL_PLACES);

        for (int i = 0; i < addressBook.getPlaceList().size(); i++) {
            ReadOnlyPlace currentPlace = addressBook.getPlaceList().get(i);
            Place updatedPlace = new Place(currentPlace);

            Set<Tag> currentTags = updatedPlace.getTags();
            Set<Tag> updatedTags = new HashSet<Tag>();

            Iterator<Tag> iter = currentTags.iterator();

            while (iter.hasNext()) {
                Tag currTag = iter.next();
                if (!currTag.tagName.equals(tag.tagName)) {
                    updatedTags.add(currTag);
                }
            }

            updatedPlace.setTags(updatedTags);
            indicateAddressBookChanged();

            addressBook.updatePlace(currentPlace, updatedPlace);
        }

        filteredPlaces.setPredicate(oldPredicate);
    }
```
