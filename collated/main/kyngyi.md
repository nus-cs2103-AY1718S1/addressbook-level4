# kyngyi
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
        ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());
        String personToDeleteName = personToDelete.getName().toString();
        String[] nameArray = {personToDeleteName};
        model.updateFilteredMeetingList(new MeetingContainsFullWordPredicate(Arrays.asList(nameArray)));
        List<ReadOnlyMeeting> lastShownMeetingList = model.getFilteredMeetingList();

        while (!lastShownMeetingList.isEmpty()) {
            int initialListSize = lastShownMeetingList.size();
            try {
                Index firstIndex = ParserUtil.parseIndex("1");
                ReadOnlyMeeting meetingToDelete = lastShownMeetingList.get(firstIndex.getZeroBased());
                model.deleteMeeting(meetingToDelete);
            } catch (IllegalValueException ive) {
                assert false : "Error in deleting first item";
            } catch (MeetingNotFoundException mnfe) {
                assert false : "The target meeting cannot be missing";
            } catch (IndexOutOfBoundsException ioobe) {
                assert false : "Index out of bounds";
            }
            int endListSize = lastShownMeetingList.size();
            if (initialListSize == endListSize) {
                break;
            }
        }

        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        String personToEditName = personToEdit.getName().toString();
        String[] nameArray = {personToEditName};
        model.updateFilteredMeetingList(new MeetingContainsFullWordPredicate(Arrays.asList(nameArray)));
        List<ReadOnlyMeeting> lastShownMeetingList = model.getFilteredMeetingList();

        if (!editedPerson.getName().toString().equalsIgnoreCase(personToEditName)) {
            while (!lastShownMeetingList.isEmpty()) {
                int initialListSize = lastShownMeetingList.size();
                EditMeetingCommand.EditMeetingDescriptor editedMeetingDescriptor =
                        new EditMeetingCommand.EditMeetingDescriptor();

                try {
                    Index firstIndex = ParserUtil.parseIndex("1");
                    ReadOnlyMeeting meetingToEdit = lastShownMeetingList.get(firstIndex.getZeroBased());

                    Meeting editedMeeting = createEditedMeeting(meetingToEdit, editedMeetingDescriptor,
                            personToEdit, editedPerson);

                    model.updateMeeting(meetingToEdit, editedMeeting);
                } catch (DuplicateMeetingException dpe) {
                    throw new CommandException(MESSAGE_DUPLICATE_MEETING);
                } catch (MeetingNotFoundException pnfe) {
                    throw new AssertionError("The target meeting cannot be missing");
                } catch (MeetingBeforeCurrDateException mde) {
                    throw new CommandException(MESSAGE_OVERDUE_MEETING);
                } catch (MeetingClashException mce) {
                    throw new CommandException(MESSAGE_MEETING_CLASH);
                } catch (IllegalValueException ive) {
                    assert false : "Error in deleting first item";
                }
                int endListSize = lastShownMeetingList.size();
                if (initialListSize == endListSize) {
                    break;
                }
            }
        } else {
            for (ReadOnlyMeeting meeting : lastShownMeetingList) {
                EditMeetingCommand.EditMeetingDescriptor editedMeetingDescriptor =
                        new EditMeetingCommand.EditMeetingDescriptor();

                Meeting editedMeeting = createEditedMeeting(meeting, editedMeetingDescriptor,
                        personToEdit, editedPerson);

                try {
                    model.updateMeeting(meeting, editedMeeting);
                } catch (DuplicateMeetingException dpe) {
                    throw new CommandException(MESSAGE_DUPLICATE_MEETING);
                } catch (MeetingNotFoundException pnfe) {
                    throw new AssertionError("The target meeting cannot be missing");
                } catch (MeetingBeforeCurrDateException mde) {
                    throw new CommandException(MESSAGE_OVERDUE_MEETING);
                } catch (MeetingClashException mce) {
                    throw new CommandException(MESSAGE_MEETING_CLASH);
                }
            }
        }

        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
```
###### \java\seedu\address\logic\commands\EditMeetingCommand.java
``` java
/**
 * Edits the details of an existing meeting in the address book.
 */
public class EditMeetingCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editmeeting";
    public static final String COMMAND_ALIAS = "em";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the meeting identified "
            + "by the index number used in the last meeting listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "01-01-2017 12:00 "
            + PREFIX_LOCATION + "Clementi MRT";

    public static final String MESSAGE_EDIT_MEETING_SUCCESS = "Edited Meeting: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEETING = "This meeting already exists in the address book.";

    private final Index index;
    private final EditMeetingDescriptor editMeetingDescriptor;

    /**
     * @param index of the meeting in the filtered Meeting list to edit
     * @param editMeetingDescriptor details to edit the meeting with
     */
    public EditMeetingCommand(Index index, EditMeetingDescriptor editMeetingDescriptor) {
        requireNonNull(index);
        requireNonNull(editMeetingDescriptor);

        this.index = index;
        this.editMeetingDescriptor = new EditMeetingDescriptor(editMeetingDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyMeeting> lastShownList = model.getFilteredMeetingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
        }

        ReadOnlyMeeting meetingToEdit = lastShownList.get(index.getZeroBased());
        Meeting editedMeeting = createEditedMeeting(meetingToEdit, editMeetingDescriptor,
                meetingToEdit.getPersonsMeet());

        try {
            model.updateMeeting(meetingToEdit, editedMeeting);
        } catch (DuplicateMeetingException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        } catch (MeetingNotFoundException pnfe) {
            throw new AssertionError("The target meeting cannot be missing");
        } catch (MeetingBeforeCurrDateException mde) {
            throw new CommandException(MESSAGE_OVERDUE_MEETING);
        } catch (MeetingClashException mce) {
            throw new CommandException(MESSAGE_MEETING_CLASH);
        }
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        return new CommandResult(String.format(MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting));
    }
    /**
     * Creates and returns a {@code Meeting} with the details of {@code meetingToEdit}
     * edited with {@code editMeetingDescriptor}.
     */
    private static Meeting createEditedMeeting(ReadOnlyMeeting meetingToEdit,
                                               EditMeetingDescriptor editMeetingDescriptor,
                                               List<ReadOnlyPerson> persons) {
        assert meetingToEdit != null;

        NameMeeting updatedName = editMeetingDescriptor.getName().orElse(meetingToEdit.getName());
        DateTime updatedDate = editMeetingDescriptor.getDate().orElse(meetingToEdit.getDate());
        Place updatedPlace = editMeetingDescriptor.getPlace().orElse(meetingToEdit.getPlace());
        MeetingTag updatedTag = editMeetingDescriptor.getMeetTag().orElse(meetingToEdit.getMeetTag());

        return new Meeting(updatedName, updatedDate, updatedPlace, persons, updatedTag);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMeetingCommand)) {
            return false;
        }

        // state check
        EditMeetingCommand e = (EditMeetingCommand) other;
        return index.equals(e.index)
                && editMeetingDescriptor.equals(e.editMeetingDescriptor);
    }

    /**
     * Stores the details to edit the Meeting with. Each non-empty field value will replace the
     * corresponding field value of the meeting.
     */
    public static class EditMeetingDescriptor {
        private NameMeeting name;
        private DateTime date;
        private Place place;
        private List<ReadOnlyPerson> personsMeet;
        private MeetingTag meetTag;

        public EditMeetingDescriptor() {
        }

        public EditMeetingDescriptor(EditMeetingCommand.EditMeetingDescriptor toCopy) {
            this.name = toCopy.name;
            this.date = toCopy.date;
            this.place = toCopy.place;
            this.meetTag = toCopy.meetTag;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.date, this.place);
        }
        public void setNameMeeting(NameMeeting name) {
            this.name = name;
        }

        public Optional<NameMeeting> getName() {
            return Optional.ofNullable(name);
        }

        public void setDate(DateTime date) {
            this.date = date;
        }

        public Optional<DateTime> getDate() {
            return Optional.ofNullable(date);
        }

        public void setPlace(Place place) {
            this.place = place;
        }

        public Optional<Place> getPlace() {
            return Optional.ofNullable(place);
        }

        public Optional<MeetingTag> getMeetTag() {
            return Optional.ofNullable(meetTag);
        }

        public void setPersonsMeet(List<ReadOnlyPerson> persons) {
            this.personsMeet = persons;
        }

        public Optional<List<ReadOnlyPerson>> getPersonsMeet() {
            return Optional.ofNullable(personsMeet);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMeetingDescriptor)) {
                return false;
            }

            // state check
            EditMeetingDescriptor e = (EditMeetingDescriptor) other;

            return getName().equals(e.getName())
                    && getDate().equals(e.getDate())
                    && getPlace().equals(e.getPlace())
                    && getMeetTag().equals(e.getMeetTag());
        }
    }
}
```
###### \java\seedu\address\logic\commands\FindExactMeetingCommand.java
``` java
/**
 * Finds and lists all meetings in address book whose meeting name contains all of the keywords.
 * Keyword matching is case sensitive.
 */
public class FindExactMeetingCommand extends Command {

    public static final String COMMAND_WORD = "findexactmeeting";
    public static final String COMMAND_ALIAS = "fem";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all meetings which contains the exactly of "
            + "the specified words (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_ALIAS + " Tuition Class";

    private final MeetingContainsFullWordPredicate predicate;

    public FindExactMeetingCommand(MeetingContainsFullWordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredMeetingList(predicate);
        return new CommandResult(getMessageForMeetingListShownSummary(model.getFilteredMeetingList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindExactMeetingCommand // instanceof handles nulls
                && this.predicate.equals(((FindExactMeetingCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\EditMeetCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditMeetCommandParser implements Parser<EditMeetingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditMeetingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_LOCATION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMeetingCommand.MESSAGE_USAGE));
        }

        EditMeetingDescriptor editMeetingDescriptor = new EditMeetingDescriptor();
        try {
            ParserUtil.parseNameMeeting(argMultimap.getValue(PREFIX_NAME))
                    .ifPresent(editMeetingDescriptor::setNameMeeting);
            ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).ifPresent(editMeetingDescriptor::setDate);
            ParserUtil.parsePlace(argMultimap.getValue(PREFIX_LOCATION)).ifPresent(editMeetingDescriptor::setPlace);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editMeetingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditMeetingCommand.MESSAGE_NOT_EDITED);
        }

        return new EditMeetingCommand(index, editMeetingDescriptor);
    }

}
```
###### \java\seedu\address\logic\parser\FindExactMeetingCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindExactMeetingCommandParser implements Parser<FindExactMeetingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindExactMeetingCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindExactMeetingCommand.MESSAGE_USAGE));
        }

        return new FindExactMeetingCommand(new MeetingContainsFullWordPredicate(Arrays.asList(trimmedArgs)));
    }
}
```
###### \java\seedu\address\model\meeting\MeetingContainsFullWordPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyMeeting}'s {@code Meeting} matches the whole word given.
 */
public class MeetingContainsFullWordPredicate implements Predicate<ReadOnlyMeeting> {
    private final List<String> keywords;

    public MeetingContainsFullWordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Tests if a {@code ReadOnlyMeeting}'s {@code List<ReadOnlyPerson>} contains any persons with name
     * matching any of the keywords given.
     */
    private boolean personListContainsFullWord(String phrase, List<ReadOnlyPerson> target) {
        for (int indexTarget = 0; indexTarget < target.size(); indexTarget++) {
            if (StringUtil.containsFullWordIgnoreCase(target.get(indexTarget).getName().fullName, phrase)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean test(ReadOnlyMeeting meeting) {
        return (personListContainsFullWord(keywords.get(0), meeting.getPersonsMeet()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MeetingContainsFullWordPredicate // instanceof handles nulls
                && this.keywords.equals(((MeetingContainsFullWordPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\meeting\MeetingContainsKeywordsPredicate.java
``` java
    /**
     * Tests if a {@code ReadOnlyMeeting}'s {@code List<ReadOnlyPerson>} contains any persons with name
     * matching any of the keywords given.
     */
    private boolean personListContainsKeyword(List<String> keywords, List<ReadOnlyPerson> target) {
        for (int indexKeyword = 0; indexKeyword < keywords.size(); indexKeyword++) {
            for (int indexTarget = 0; indexTarget < target.size(); indexTarget++) {
                if (StringUtil.containsWordIgnoreCase(target.get(indexTarget).getName().fullName, (
                        keywords.get(indexKeyword)))) {
                    return true;
                }
            }
        }
        return false;
    }
```
