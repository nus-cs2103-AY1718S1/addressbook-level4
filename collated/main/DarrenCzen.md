# DarrenCzen
###### /java/seedu/address/commons/events/ui/AccessLocationRequestEvent.java
``` java
/**
 * An event requesting to view the location of a person.
 */
public class AccessLocationRequestEvent extends BaseEvent {
    public final String location;

    public AccessLocationRequestEvent(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/AccessWebsiteRequestEvent.java
``` java
/**
 * An event requesting to view the website of a person.
 */
public class AccessWebsiteRequestEvent extends BaseEvent {

    public final String website;

    public AccessWebsiteRequestEvent(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/logic/commands/AccessCommand.java
``` java
/**
 * Accesses a person's website in the address book.
 */
public class AccessCommand extends Command {
    public static final String COMMAND_WORD = "access";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Accesses the website of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ACCESS_PERSON_SUCCESS = "Accessed website of Person: %2$s at Index %1$s";

    private final Index targetIndex;

    public AccessCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person = lastShownList.get(targetIndex.getZeroBased());
        String name = person.getName().toString();
        String website = person.getWebsite().toString();

        if (website.equals("NIL")) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEBSITE);
        }

        EventsCenter.getInstance().post(new AccessWebsiteRequestEvent(website));
        return new CommandResult(String.format(MESSAGE_ACCESS_PERSON_SUCCESS, targetIndex.getOneBased(), name));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccessCommand // instanceof handles nulls
                && this.targetIndex.equals(((AccessCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        public void setWebsite(Website website) {
            this.website = website;
        }

        public Optional<Website> getWebsite() {
            return Optional.ofNullable(website);
        }

```
###### /java/seedu/address/logic/commands/LocationCommand.java
``` java
/**
 * Accesses a person's location in the address book.
 */
public class LocationCommand extends Command {
    public static final String COMMAND_WORD = "locate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Accesses the location of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LOCATE_PERSON_SUCCESS = "Accessed location of Person: %2$s at Index %1$s";

    private final Index targetIndex;

    public LocationCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person = lastShownList.get(targetIndex.getZeroBased());
        String name = person.getName().toString();
        String location = person.getAddress().toString();

        if (location.equals("NIL")) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOCATION);
        }

        EventsCenter.getInstance().post(new AccessLocationRequestEvent(location));
        return new CommandResult(String.format(MESSAGE_LOCATE_PERSON_SUCCESS, targetIndex.getOneBased(), name));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationCommand // instanceof handles nulls
                && this.targetIndex.equals(((LocationCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
/**
 * Sorts all persons in the address book alphabetically for the user.
 */

public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all persons in the list";

    @Override
    public CommandResult executeUndoableCommand() {
        model.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/parser/AccessCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AccessCommand object
 */
public class AccessCommandParser implements Parser<AccessCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AccessCommand
     * and returns an AccessCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AccessCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new AccessCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccessCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/AddCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {

        Address address;
        Birthday birthday;
        HomeNumber homeNumber;
        SchEmail schEmail;
        Website website;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_HOME_NUMBER,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_WEBSITE, PREFIX_SCH_EMAIL,
                        PREFIX_BIRTHDAY, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            // Optional Address Field
            Optional<Address> tempAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));
            address = (tempAddress.isPresent()) ? tempAddress.get() : new Address(null);

            // Optional Birthday Field
            Optional<Birthday> tempBirthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY));
            birthday = (tempBirthday.isPresent()) ? tempBirthday.get() : new Birthday(null);

            // Optional HomeNumber Field
            Optional<HomeNumber> tempHomeNumber = ParserUtil.parseHomeNumber(argMultimap.getValue(PREFIX_HOME_NUMBER));
            homeNumber = (tempHomeNumber.isPresent()) ? tempHomeNumber.get() : new HomeNumber(null);

            // Optional SchEmail Field
            Optional<SchEmail> tempSchEmail = ParserUtil.parseSchEmail(argMultimap.getValue(PREFIX_SCH_EMAIL));
            schEmail = (tempSchEmail.isPresent()) ? tempSchEmail.get() : new SchEmail(null);

            // Optional Website
            Optional<Website> tempWebsite = ParserUtil.parseWebsite(argMultimap.getValue(PREFIX_WEBSITE));
            website = (tempWebsite.isPresent()) ? tempWebsite.get() : new Website(null);

            ReadOnlyPerson person = new Person(name, phone, homeNumber,
                    email, schEmail, website, address, birthday, false, tagList);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

```
###### /java/seedu/address/logic/parser/LocationCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AccessCommand object
 */
public class LocationCommandParser implements Parser<LocationCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LocationCommand
     * and returns an LocationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public LocationCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new LocationCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocationCommand.MESSAGE_USAGE));
        }

    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /** Ensures that every person in the AddressBook
     *  is sorted in an alphabetical order.
     */
    public void sort() {
        persons.sort();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void sort() {
        addressBook.sort();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/person/Address.java
``` java
    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Address(String address) throws IllegalValueException {
        if (address == null) {
            this.value = ADDRESS_TEMPORARY;
        } else {
            if (!isValidAddress(address)) {
                throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
            }
            this.value = address;
        }
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX)
                || test.matches(ADDRESS_TEMPORARY);
    }

```
###### /java/seedu/address/model/person/Birthday.java
``` java
    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        if (birthday == null) {
            this.value = BIRTHDAY_TEMPORARY;
        } else {
            String trimmedBirthday = birthday.trim();
            if (!isValidBirthday(trimmedBirthday)) {
                throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
            }
            this.value = trimmedBirthday;
        }
    }


    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX)
                || test.matches(BIRTHDAY_TEMPORARY);
    }
```
###### /java/seedu/address/model/person/HomeNumber.java
``` java
    /**
     * Validates given home number.
     *
     * @throws IllegalValueException if given home string is invalid.
     */
    public HomeNumber(String homeNumber) throws IllegalValueException {
        if (homeNumber == null) {
            this.value = HOME_NUMBER_TEMPORARY;
        } else {
            String trimmedHomeNumber = homeNumber.trim();
            if (!isValidHomeNumber(trimmedHomeNumber)) {
                throw new IllegalValueException(MESSAGE_HOME_NUMBER_CONSTRAINTS);
            }
            this.value = trimmedHomeNumber;
        }
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidHomeNumber(String test) {
        return test.matches(HOME_NUMBER_VALIDATION_REGEX)
                || test.matches(HOME_NUMBER_TEMPORARY);
    }

```
###### /java/seedu/address/model/person/Name.java
``` java
    /**
     * This method converts a name to become capitalized fully.
     * e.g. from "dArrEn cHiN" to "Darren Chin"
     */
    public static String toCapitalized(String s) {

        final String delimiters = " ";
        StringBuilder newString = new StringBuilder();
        boolean isCapital = true;

        for (char c : s.toCharArray()) {
            c = (isCapital) ? Character.toUpperCase(c) : Character.toLowerCase(c);
            newString.append(c);

            isCapital = (delimiters.indexOf((int) c) >= 0);
        }
        return newString.toString();
    }
```
###### /java/seedu/address/model/person/Person.java
``` java
    public void setWebsite(Website website) {
        this.website.set(requireNonNull(website));
    }
```
###### /java/seedu/address/model/person/SchEmail.java
``` java
    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public SchEmail(String schEmail) throws IllegalValueException {
        if (schEmail == null) {
            this.value = SCH_EMAIL_TEMPORARY;
        } else {
            String trimmedSchEmail = schEmail.trim();
            if (!isValidSchEmail(trimmedSchEmail)) {
                throw new IllegalValueException(MESSAGE_SCH_EMAIL_CONSTRAINTS);
            }
            this.value = trimmedSchEmail;
        }
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidSchEmail(String test) {
        return test.matches(SCH_EMAIL_VALIDATION_REGEX)
                || test.matches(SCH_EMAIL_TEMPORARY);
    }

```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Sorts every person in the list alphabetically.
     */
    public void sort() {
        internalList.sort((r1, r2) -> (
                r1.getName().toString().compareTo(r2.getName().toString())));
    }

```
###### /java/seedu/address/model/person/Website.java
``` java
/**
 * Represents a Person's website information in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWebsite(String)}
 */

public class Website {
    public static final String MESSAGE_WEBSITE_CONSTRAINTS =
            "Website inputted should follow format https://www.anyName.com/anyContent"
                    + " where both anyName and anyContent can be alphanumeric."
                    + " You must have https://www. and a domain name like .com";
    public static final String WEBSITE_TEMPORARY = "NIL";

    public final String value;

    /**
     * Validates given website.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Website(String websiteName) throws IllegalValueException {
        if (websiteName == null) {
            this.value =  WEBSITE_TEMPORARY;
        } else {
            String trimmedWebsite = websiteName.trim();

            if (!isValidWebsite(trimmedWebsite)) {
                throw new IllegalValueException(MESSAGE_WEBSITE_CONSTRAINTS);
            }

            this.value = trimmedWebsite;
        }
    }

    /**
     * Returns if a given string is a valid person website.
     */
    public static boolean isValidWebsite(String test) {
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);

        return urlValidator.isValid(test) || test.matches(WEBSITE_TEMPORARY);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Website // instanceof handles nulls
                && this.value.equals(((Website) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    /**
     * Access website through browser panel based on person's link
     * @param website
     */
    public void handleWebsiteAccess(String website) {
        loadPage(website);
    }

    private void loadPersonLocation(String location) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX + location.replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);
    }

```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    @Subscribe
    private void handleAccessWebsiteEvent(AccessWebsiteRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleWebsiteAccess(event.website);
    }

    @Subscribe
    private void handleAccessLocationEvent(AccessLocationRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonLocation(event.location);
    }
}
```
