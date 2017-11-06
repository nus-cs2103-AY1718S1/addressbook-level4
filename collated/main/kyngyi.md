# kyngyi
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
        ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());
        String personToDeleteName = personToDelete.getName().toString();
        String[] nameArray = {personToDeleteName};
        model.updateFilteredMeetingList(new MeetingContainsFullWordPredicate(Arrays.asList(nameArray)));
        List<ReadOnlyMeeting> lastShownMeetingList = model.getFilteredMeetingList();

        while (true) {
            try {
                Index firstIndex = ParserUtil.parseIndex("1");
                ReadOnlyMeeting meetingToDelete = lastShownMeetingList.get(firstIndex.getZeroBased());
                model.deleteMeeting(meetingToDelete);
            } catch (IllegalValueException ive) {
                assert false : "Error in deleting first item";
            } catch (MeetingNotFoundException mnfe) {
                assert false : "The target meeting cannot be missing";
            } catch (IndexOutOfBoundsException ioobe) {
                break;
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
                meetingToEdit.getPersonName(), meetingToEdit.getPersonPhone());

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
                                               EditMeetingDescriptor editMeetingDescriptor, PersonToMeet person,
                                               PhoneNum phone) {
        assert meetingToEdit != null;

        NameMeeting updatedName = editMeetingDescriptor.getName().orElse(meetingToEdit.getName());
        DateTime updatedDate = editMeetingDescriptor.getDate().orElse(meetingToEdit.getDate());
        Place updatedPlace = editMeetingDescriptor.getPlace().orElse(meetingToEdit.getPlace());
        MeetingTag updatedTag = editMeetingDescriptor.getMeetTag().orElse(meetingToEdit.getMeetTag());

        return new Meeting(updatedName, updatedDate, updatedPlace, person, phone, updatedTag);
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
        private PersonToMeet personName;
        private PhoneNum phoneNum;
        private MeetingTag meetTag;

        public EditMeetingDescriptor() {
        }

        public EditMeetingDescriptor(EditMeetingCommand.EditMeetingDescriptor toCopy) {
            this.name = toCopy.name;
            this.date = toCopy.date;
            this.place = toCopy.place;
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

        public void setPersonToMeet(PersonToMeet name) {
            this.personName = name; }

        public void setPhoneNum (PhoneNum phoneNum) {
            this.phoneNum = phoneNum; }

        public void setMeetTag (MeetingTag meetTag) {
            this.meetTag = meetTag; }

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
                    && getPlace().equals(e.getPlace());
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

    @Override
    public boolean test(ReadOnlyMeeting meeting) {
        return (keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsFullWordIgnoreCase(meeting.getPersonName().fullName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MeetingContainsFullWordPredicate // instanceof handles nulls
                && this.keywords.equals(((MeetingContainsFullWordPredicate) other).keywords)); // state check
    }
}
```
