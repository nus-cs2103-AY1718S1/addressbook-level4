# Pengyuz
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
/**
 * Deletes a person identified using it's last displayed index from the address book.
 */

public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_WORD_2 = "remove";
    public static final String COMMAND_WORD_3 = "-";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted";
    private boolean allvalid = true;
    private boolean exist = false;
    private boolean duplicate = false;

    private ArrayList<Index> targetIndexs = new ArrayList<>();
    private String target = "";

    public DeleteCommand(ArrayList<Index> targetIndex) {
        this.targetIndexs = targetIndex;
    }
    public DeleteCommand(String target) {
        this.target = target;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList =  model.getFilteredPersonList();
        ArrayList<ReadOnlyPerson> personstodelete =  new ArrayList<ReadOnlyPerson>();
        if (target != "") {
            for (ReadOnlyPerson p: lastShownList) {
                if (p.getName().fullName.equals(target) && exist == true) {
                    personstodelete.add(p);
                    duplicate = true;
                }
                if (p.getName().fullName.equals(target)) {
                    personstodelete.add(p);
                    exist = true;
                }

            }
        } else {
            for (Index s: targetIndexs) {
                if (s.getZeroBased() >= lastShownList.size()) {
                    allvalid = false;
                } else {
                    personstodelete.add(lastShownList.get(s.getZeroBased()));
                    exist = true;
                }
            }
        }

        if (exist && duplicate) {
            List<String> duplicatePerson = Arrays.asList(target);
            NameContainsKeywordsPredicate updatedpredicate = new NameContainsKeywordsPredicate(duplicatePerson);
            model.updateFilteredPersonList(updatedpredicate);
            return new CommandResult("Duplicate persons exist, please choose one to delete.");
        }

        if (allvalid && exist) {
            try {
                model.deletePerson(personstodelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
            return new CommandResult(MESSAGE_DELETE_PERSON_SUCCESS);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }


    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndexs.equals(((DeleteCommand) other).targetIndexs))
                && (other instanceof DeleteCommand
                && this.target.equals(((DeleteCommand) other).target)); // state check
    }
}

```
###### \java\seedu\address\logic\commands\HelpCommand.java
``` java
/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_WORD_2 = "h";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    private String commandword = "";
    public HelpCommand() {}
    public HelpCommand(String args) {
        commandword = args;
    }

    @Override
    public CommandResult execute() {
        if ("add".equals(commandword)) {
            return new CommandResult(AddCommand.MESSAGE_USAGE);
        } else if ("clear".equals(commandword)) {
            return new CommandResult(ClearCommand.MESSAGE_USAGE);
        } else if ("delete".equals(commandword)) {
            return new CommandResult(DeleteCommand.MESSAGE_USAGE);
        } else if ("edit".equals(commandword)) {
            return new CommandResult(EditCommand.MESSAGE_USAGE);
        } else if ("exit".equals(commandword)) {
            return new CommandResult(ExitCommand.MESSAGE_USAGE);
        } else if ("find".equals(commandword)) {
            return new CommandResult(FindCommand.MESSAGE_USAGE);
        } else if ("history".equals(commandword)) {
            return new CommandResult(HistoryCommand.MESSAGE_USAGE);
        } else if ("list".equals(commandword)) {
            return new CommandResult(ListCommand.MESSAGE_USAGE);
        } else if ("redo".equals(commandword)) {
            return new CommandResult(RedoCommand.MESSAGE_USAGE);
        } else if ("select".equals(commandword)) {
            return new CommandResult(SelectCommand.MESSAGE_USAGE);
        } else if ("sort".equals(commandword)) {
            return new CommandResult(SortCommand.MESSAGE_USAGE);
        } else if ("tagadd".equals(commandword)) {
            return new CommandResult(TagAddCommand.MESSAGE_USAGE);
        } else if ("tagremove".equals(commandword)) {
            return new CommandResult(TagRemoveCommand.MESSAGE_USAGE);
        } else if ("tagfind".equals(commandword)) {
            return new CommandResult(TagFindCommand.MESSAGE_USAGE);
        } else if ("birthdayadd".equals(commandword)) {
            return new CommandResult(BirthdayAddCommand.MESSAGE_USAGE);
        } else if ("birthdayremove".equals(commandword)) {
            return new CommandResult(BirthdayRemoveCommand.MESSAGE_USAGE);
        } else if ("mapshow".equals(commandword)) {
            return new CommandResult(MapShowCommand.MESSAGE_USAGE);
        } else if ("maproute".equals(commandword)) {
            return new CommandResult(MapRouteCommand.MESSAGE_USAGE);
        } else if ("scheduleadd".equals(commandword)) {
            return new CommandResult(ScheduleAddCommand.MESSAGE_USAGE);
        } else if ("scheduleremove".equals(commandword)) {
            return new CommandResult(ScheduleRemoveCommand.MESSAGE_USAGE);
        } else if ("export".equals(commandword)) {
            return new CommandResult(ExportCommand.MESSAGE_USAGE);
        } else if ("undo".equals(commandword)) {
            return new CommandResult(UndoCommand.MESSAGE_USAGE);
        } else {
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HelpCommand // instanceof handles nulls
                && this.commandword.equals(((HelpCommand) other).commandword)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\DeleteCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String argsWithoutpre;
        ArrayList<Index> index;
        String arguments = args.trim();
        if (arguments.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        int indexOfname = arguments.indexOf("n/");
        int indexOfnumbers = arguments.indexOf("I/");

        if ((indexOfname == -1) && (indexOfnumbers == -1)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        String atStart = arguments.substring(0, 2);
        if ((!atStart.equals("n/")) && (!atStart.equals("I/"))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        if (atStart.equals("I/")) {
            argsWithoutpre = arguments.replace("I/", "").trim();
            try {
                index = ParserUtil.parseIndexes(argsWithoutpre);
                return new DeleteCommand(index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        } else {
            argsWithoutpre = arguments.replace("n/", "").trim();
            if (argsWithoutpre.equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            return new DeleteCommand(argsWithoutpre);
        }
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses  {@code oneBasedIndex} into an {@code numbers} and return it.the commas will be deleted.
     *
     * @throws IllegalValueException if the specified index is invalid.
     */
    public static ArrayList<Index> parseIndexes(String oneBasedIndexes) throws IllegalValueException {
        String[] ns = oneBasedIndexes.trim().split(" ");
        ArrayList<Index> numbers = new ArrayList<>();
        boolean allvalid = true;
        for (String a : ns) {
            String s = a.trim();
            if (StringUtil.isNonZeroUnsignedInteger(s)) {
                numbers.add(Index.fromOneBased(Integer.parseInt(s)));
            } else {
                allvalid = false;

            }
        }
        if (!allvalid) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return numbers;

    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }

    //// Event-related parsing

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<EventName>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EventName> parseEventName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new EventName(name.get())) : Optional.empty();
    }

    /**
     * Parses two {@code Optional<String> time} and {@code String duration} into an
     * {@code Optional<EventTime>} if {@code time} is present.
     *
     * String duration is guaranteed to be initialised from input validation in
     * @see ScheduleAddCommandParser
     *
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EventTime> parseEventTime(Optional<String> time, String duration)
            throws DateTimeParseException, NumberFormatException, IllegalValueException {
        requireNonNull(time);
        requireNonNull(duration);
        return (time.isPresent())
                ? Optional.of(new EventTime(DateTimeUtil.parseStringToLocalDateTime(time.get()),
                DateTimeUtil.parseDuration(duration)))
                : Optional.empty();
    }

    /**
     * Parses a {@code String duration} into an {@code EventDuration}
     *
     * String duration is guaranteed to be initialised from input validation in
     * @see ScheduleAddCommandParser

     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static EventDuration parseEventDuration(String duration)
            throws NumberFormatException, IllegalValueException {
        requireNonNull(duration);
        return new EventDuration(DateTimeUtil.parseDuration(duration));
    }


}
```
