# aziziazfar
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
        for (ReadOnlyPerson toAppend: personsToDelete) {
            builder.append("\n" + toAppend.toString());
        }
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

            ReadOnlyPerson person = new Person(name, phone, email, address, birthday, remark, tagList,
                    isPrivate, isPinned);

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
