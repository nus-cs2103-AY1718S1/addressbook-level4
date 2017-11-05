# taojiashu
###### \java\seedu\address\logic\commands\ExitCommand.java
``` java
    public static final String MESSAGE_CONFIRMATION = "Type 'exit' again to confirm to exit";
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

```
###### \java\seedu\address\logic\commands\ExitCommand.java
``` java
    @Override
    public CommandResult execute() {
        List<String> previousCommands = history.getHistory();

        if (previousCommands.isEmpty()) {
            return new CommandResult(MESSAGE_CONFIRMATION);
        }

        Collections.reverse(previousCommands);
        if (previousCommands.get(0).equals("exit")) {
            EventsCenter.getInstance().post(new ExitAppRequestEvent());
            return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
        } else {
            return new CommandResult(MESSAGE_CONFIRMATION);
        }
    }

```
###### \java\seedu\address\logic\commands\FavouriteCommand.java
``` java
/**
 * Mark a person in the contact as favourite
 */
public class FavouriteCommand extends Command {

    public static final String COMMAND_WORD_1 = "favourite";
    public static final String COMMAND_WORD_2 = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Mark the person as favourite "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD_1
            + "OR "
            + COMMAND_WORD_2 + " 1 ";

    public static final String MESSAGE_ARGUMENTS = "INDEX: %1$d";
    public static final String MESSAGE_FAVOURITE_SUCCESS = "Favourite Person: %1$s";
    public static final String MESSAGE_DEFAVOURITE_SUCCESS = "Remove Person from Favourites: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private boolean changedToFav;

    /**
     * @param index of the person in the filtered person list to mark as favourite
     */
    public FavouriteCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Favourite favourite = personToEdit.getFavourite();
        favourite.toggleFavourite();
        changedToFav = favourite.getFavourite();

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), favourite, personToEdit.getBirthday(), personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (changedToFav) {
            return new CommandResult(generateFavouriteSuccessMessage(editedPerson));
        } else {
            return new CommandResult(generateDeFavouriteSuccessMessage(editedPerson));
        }
    }

    private String generateFavouriteSuccessMessage(ReadOnlyPerson personToEdit) {
        return String.format(MESSAGE_FAVOURITE_SUCCESS, personToEdit);
    }

    private String generateDeFavouriteSuccessMessage(ReadOnlyPerson personToEdit) {
        return String.format(MESSAGE_DEFAVOURITE_SUCCESS, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if they are the same object
        if (other == this) {
            return true;
        }

        // instanceof handles null
        if (!(other instanceof FavouriteCommand)) {
            return false;
        }

        // state check
        FavouriteCommand e = (FavouriteCommand) other;
        return index.equals(e.index);
    }

}
```
###### \java\seedu\address\logic\commands\ShowFavouriteCommand.java
``` java
/**
 * List all favourite persons
 */
public class ShowFavouriteCommand extends Command {

    public static final String COMMAND_WORD_1 = "showFavourite";
    public static final String COMMAND_WORD_2 = "sf";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1
            + " OR "
            + COMMAND_WORD_2;

    @Override
    public CommandResult execute() {
        final IsFavouritePredicate predicate = new IsFavouritePredicate();
        model.updateFilteredPersonList(predicate);
        return new CommandResult((getMessageForPersonListShownSummary(model.getFilteredPersonList().size())));
    }
}
```
###### \java\seedu\address\logic\parser\FavouriteCommandParser.java
``` java
/**
 * Parser for FavouriteCommand
 */
public class FavouriteCommandParser implements Parser<FavouriteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FavouriteCommand
     * and returns a FavouriteCommand object for execution
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
###### \java\seedu\address\model\person\Favourite.java
``` java
/**
 *  Represents whether a Person is a favourite contact or not
 */
public class Favourite {

    private boolean favourite;
    private String status;

    /**
     * Default constructor
     * if no parameter is passed in, the favourite value is initialised to false
     */
    public Favourite() {
        this.favourite = false;
        this.status = "False";
    }

    public Favourite(boolean favourite) {
        this.favourite = favourite;
        this.status = favourite ? "True" : "False";
    }

    private void setFavouriteStatus() {
        status = favourite ? "True" : "False";
    }

    /**
     * Sets favourite to the opposite value.
     * Updates the status too.
     */
    public void toggleFavourite() {
        favourite = !favourite;
        setFavouriteStatus();
    }

    public boolean getFavourite() {
        return favourite;
    }

    public String getStatus() {
        setFavouriteStatus(); // Ensure the status is in sync with favourite
        return status;
    }

    @Override
    public String toString() {
        setFavouriteStatus(); // Ensure the status is in sync with favourite
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Favourite) // instanceof handles nulls
                && this.favourite == ((Favourite) other).favourite; // state check
    }

    @Override
    public int hashCode() {
        setFavouriteStatus();
        return status.hashCode();
    }
}
```
###### \java\seedu\address\model\person\IsFavouritePredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Favourite} is "True".
 */
public class IsFavouritePredicate implements Predicate<ReadOnlyPerson> {

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getFavourite().getFavourite();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IsFavouritePredicate); // instanceof handles nulls
    }
}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Favourite(), new Birthday("191010"),
                    getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Favourite(true),
                        new Birthday(), getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Favourite(),
                        new Birthday("191010"), getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Favourite(true),
                        new Birthday(), getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Favourite(true),
                        new Birthday("170398"), getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Favourite(), new Birthday("110696"),
                    getTagSet("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    @XmlElement(required = true)
    private String favourite;
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        favourite = source.getFavourite().getStatus();
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        final Favourite favourite = new Favourite();
        if (this.favourite.equals("True")) {
            favourite.toggleFavourite();
        } else if (!this.favourite.equals("False")) {
            throw new IllegalValueException("Illegal favourite status");
        }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        initFavouriteLabel(person);
        initBirthdayLabel(person);
        initTags(person);
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        person.favouriteProperty().addListener((observable, oldValue, newValue) -> initFavouriteLabel(person));
        //birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        person.birthdayProperty().addListener((observable, oldValue, newValue) -> initBirthdayLabel(person));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Sets the colour of a favourite label based on its favourite status
     */
    private void initFavouriteLabel(ReadOnlyPerson person) {
        boolean favouriteStatus = person.getFavourite().getFavourite();
        Label favouriteLabel = new Label();
        Image starFilled = new Image(getClass().getResource("/images/Gold_Star.png").toExternalForm());
        Image starTransparent = new Image(getClass().getResource("/images/Star_star.png").toExternalForm());
        if (favouriteStatus) {
            favouriteLabel.setGraphic(new ImageView(starFilled));
            favouriteLabel.setStyle("-fx-background-color: transparent; -fx-border-color: transparent");
        } else {
            favouriteLabel.setGraphic(new ImageView((starTransparent)));
            favouriteLabel.setStyle("-fx-background-color: transparent; -fx-border-color: transparent");

        }
        cardPane.getChildren().add(favouriteLabel);
    }

```
