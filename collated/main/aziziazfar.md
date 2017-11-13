# aziziazfar
###### \java\seedu\address\commons\events\model\ListSizeEvent.java
``` java
/**
 *  Represents an event where displayed list size is updated
 *  Find, List, Delete multiple, and Sort commands will update the ListSizeLabel.
 *
 */
public class ListSizeEvent extends BaseEvent {

    private int sizeOfList;

    public ListSizeEvent(int size) {
        requireNonNull(size);

        this.sizeOfList = size;
    }

    @Override
    public String toString() {
        return (" " + sizeOfList + " ");
    }

    public int getToggle() {
        return this.sizeOfList;
    }
}
```
###### \java\seedu\address\logic\commands\person\DeleteCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> personsToDelete = new ArrayList<>();
        int counter = 0;
        for (Index checkException : targetIndices) {
            List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
            if (checkException.getZeroBased() - counter >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
        for (Index targetIndex : targetIndices) {
            List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
            ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased() - counter);
            personsToDelete.add(personToDelete);

            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
            counter++;
        }
        StringBuilder builder = new StringBuilder();
        for (ReadOnlyPerson toAppend : personsToDelete) {
            builder.append("\n" + toAppend.toString());
        }
        EventsCenter.getInstance().post(new ListSizeEvent(personsToDelete.size()));
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, builder));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndices.equals(((DeleteCommand) other).targetIndices)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\person\FindCommand.java
``` java
    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        EventsCenter.getInstance().post(new ListSizeEvent(model.getFilteredPersonList().size()));
        EventsCenter.getInstance().post(new ToggleListAllStyleEvent());
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }
```
###### \java\seedu\address\logic\commands\person\ListCommand.java
``` java
    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_NOT_HIDDEN);
        EventsCenter.getInstance().post(new ToggleListAllStyleEvent());
        EventsCenter.getInstance().post(new ListSizeEvent(model.getFilteredPersonList().size()));
        EventsCenter.getInstance().post(new ToggleSearchBoxStyle(false));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name, String command) throws IllegalValueException {
        requireNonNull(name);
        if (command.equals("add")) {
            return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.of(new Name(0));
        } else {
            return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
        }
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone, String command) throws IllegalValueException {
        requireNonNull(phone);
        if (command.equals("add")) {
            return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.of(new Phone(0));
        } else {
            return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
        }
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address, String command)
            throws IllegalValueException {
        requireNonNull(address);
        if (command.equals("add")) {
            return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.of(new Address(0));
        } else {
            return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
        }
    }

```
###### \java\seedu\address\logic\parser\person\AddCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_BIRTHDAY, PREFIX_TAG);
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME), "add").get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE), "add").get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL), "add").get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS), "add").get();
            Birthday birthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY), "add").get();
            Remark remark = new Remark(""); // add command does not allow adding remarks straight away
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            boolean isPrivate = false;
            boolean isPinned = false;
            boolean isSelected = false;

            ReadOnlyPerson person = new Person(name, phone, email, address, birthday, remark, tagList,
                    isPrivate, isPinned, isSelected);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
```
###### \java\seedu\address\logic\parser\person\AddCommandParser.java
``` java
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
```
###### \java\seedu\address\logic\parser\person\DeleteCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        if (args.length() == 2) {
            try {
                Index index = ParserUtil.parseIndex(args);
                return new DeleteCommand(index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        } else if (args.contains("~")) {
            String[] indices = args.trim().split("~");
            List<Index> indexes = new ArrayList<>();
            for (int i = Integer.parseInt(indices[0]); i <= Integer.parseInt(indices[1]); i++) {
                try {
                    indexes.add(ParserUtil.parseIndex(Integer.toString(i)));
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
                }
            }
            return new DeleteCommand(indexes);
        } else {
            String[] tokens = args.trim().split(" ");
            List<Index> indexes = new ArrayList<>();

            for (String token : tokens) {
                try {
                    indexes.add(ParserUtil.parseIndex(token));
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
                }
            }
            return new DeleteCommand(indexes);
        }
    }
```
###### \java\seedu\address\model\person\Address.java
``` java
    /**
     * Provides a default address (" ") when field is empty.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Address(int checkValue) throws IllegalValueException {
        if (checkValue != 0) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        this.value = " ";
    }
    //@author aziziazfar

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && this.value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Email.java
``` java
    /**
     * Provides a default email (" ") when field is empty.
     *
     * @throws IllegalValueException if given email string is invalid.
     */
    public Email(int checkValue) throws IllegalValueException {
        if (checkValue != 0) {
            throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
        }
        this.value = " ";
    }
```
###### \java\seedu\address\model\person\Name.java
``` java
    /**
     * Provides a default name (" ") when field is empty.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Name(int checkValue) throws IllegalValueException {
        if (checkValue != 0) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.fullName = " ";
    }
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        Name name = new Name(0);
        Birthday birthday = new Birthday(0);
        Phone phone = new Phone(0);
        Email email = new Email(0);
        Address address = new Address(0);

        if (!isEmptyField(this.name)) {
            name = new Name(this.name);
        }
        if (!isEmptyField(this.phone)) {
            phone = new Phone(this.phone);
        }
        if (!isEmptyField(this.email)) {
            email = new Email(this.email);
        }
        if (!isEmptyField(this.address)) {
            address = new Address(this.address);
        }
        if (!isEmptyField(this.birthday)) {
            birthday = new Birthday(this.birthday);
        }

        final Remark remark = new Remark(this.remark);
        final Set<Tag> tags = new HashSet<>(personTags);
        final boolean isPrivate = this.isPrivate;
        final boolean isPinned = this.isPinned;
        final boolean isSelected = false;
        return new Person(name, phone, email, address, birthday, remark, tags, isPrivate, isPinned, isSelected);
    }

    /**
     * Checks whether the person field is empty.
     */
    public boolean isEmptyField(String input) {
        return input.equals(" ");
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        this.personCardIndex = displayedIndex + "";
        id.setText(displayedIndex + ". ");
        initTags(person);
        initPin(person);
        bindListeners(person);
        /* add colors to colors list*/
        colors.add("cadetblue");
        colors.add("cornflowerblue");
        colors.add("dodgerblue");
        colors.add("deepskyblue");
        colors.add("mediumblue");
        colors.add("royalblue");
        colors.add("steelblue");
        colors.add("lightseagreen");
        colors.add("teal");
        colors.add("steelblue");
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java

    /**
     * * Initialises the tags for {@code Person}
     * with the color properties.
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName)
                    + ";-fx-background-radius: 3 3 3 3");
            tags.getChildren().add(tagLabel);
        });
    }

    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue) && !colors.isEmpty()) {
            tagColors.put(tagValue, colors.get(0));
            colors.add(colors.get(0)); // keeps the loop
            colors.remove(0);
        }

        return tagColors.get(tagValue);
    }

```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    @Subscribe
    private void handleNewListResultAvailable(ListSizeEvent event) {
        Label listSizeLabel = new Label(event.toString());
        listSizeLabel.setStyle("-fx-background-color: #00bf00;"
                + " -fx-background-radius: 80 80 80 80;"
                + " -fx-background-size: 35px");
        listSizeDisplay.getChildren().setAll(listSizeLabel);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));
    }

```
