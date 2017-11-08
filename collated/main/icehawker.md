# icehawker
###### \java\seedu\address\logic\commands\CopyCommand.java
``` java
/**
 * Changes the remark of an existing person in the address book.
 */
public class CopyCommand extends Command {

    public static final String COMMAND_WORD = "copy";

    public static final String COMMAND_ALIAS = "y";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Outputs a selectable string of text "
            + "that contains email addresses of the selected person(s) in email-friendly format.\n"
            + "Parameters: INDEX (One or more, positive integers separated by comma) \n"
            + "Example: " + COMMAND_WORD + " 1 " + "\n"
            + "Output: johndoe@example.com";

    public static final String MESSAGE_COPY_PERSON_SUCCESS = "Copied to clipboard: %1$s";
    private ArrayList<String> outputList = new ArrayList<>();

    private final ArrayList<Index> targetIndices;

    public CopyCommand(ArrayList<Index> targetIndices) {
        Collections.sort(targetIndices);
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson targetPerson = lastShownList.get(targetIndex.getZeroBased());
            for (Email email: targetPerson.getEmails()) {
                outputList.add(email.toString());
            }
        }

        // outputList without square brackets
        String messageOutput = outputList.toString().substring(1, outputList.toString().length() - 1);
        // outputList use semi-colon separator
        messageOutput = messageOutput.replace(",", ";");

        // copy string to clipboard
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection messageOutputSelection = new StringSelection(messageOutput);
        clipboard.setContents(messageOutputSelection, null);

        return new CommandResult(String.format(MESSAGE_COPY_PERSON_SUCCESS, outputList));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CopyCommand // instanceof handles nulls
                && this.targetIndices.equals(((CopyCommand) other).targetIndices)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setCountry(Country country) {
            this.country = country;
        }

        public Optional<Country> getCountry() {
            return Optional.ofNullable(country);
        }

```
###### \java\seedu\address\logic\parser\CopyCommandParser.java
``` java
/**
 * Parses input arguments and creates a new CopyCommand object
 */
public class CopyCommandParser implements Parser<CopyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CopyCommand parse(String args) throws ParseException {
        String invalidCommandString = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyCommand.MESSAGE_USAGE);
        try {
            String trimmedArgs = args.trim();
            String[] indicesInString = trimmedArgs.split("\\s+");

            ArrayList<Index> indices = new ArrayList<>();
            for (int i = 0; i < indicesInString.length; i++) {
                Index index = ParserUtil.parseIndex(indicesInString[i]);

                // Check if there are repeated indices
                if (i >= 1) {
                    for (Index indexInList: indices) {
                        if (indexInList.equals(index)) {
                            throw new ParseException(invalidCommandString);
                        }
                    }
                }
                indices.add(index);
            }

            return new CopyCommand(indices);
        } catch (IllegalValueException ive) {
            throw new ParseException(invalidCommandString);
        }
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> country} into an {@code Optional<Country>} if {@code country} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Country> parseCountry(Optional<String> country) throws IllegalValueException {
        requireNonNull(country);
        return country.isPresent() ? Optional.of(new Country(country.get())) : Optional.empty();
    }


    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

```
###### \java\seedu\address\model\person\Country.java
``` java
/**
 * Corresponding alias list of each country code and its corresponding .countryName().
 * Guarantees: immutable; is valid as declared in {@link #isValidCode(String)}
 */
public class Country {

    public static final String DEFAULT_COUNTRY_CODE = "";
    public static final String DEFAULT_COUNTRY = "Country Unavailable";
    public final String value;

    /**
     * Converts a country code to country name.
     */
    public Country(String countryCode) {
        requireNonNull(countryCode);
        this.value = countryGetter(countryCode); // value is a country name
    }

    public static String countryGetter(String code) {
        return getName(code);
    }

    public static String getName(String code) {
        switch (code) {
        case "65": return "Singapore";
        case "263": return "Zimbabwe";
        case "260": return "Zambia";
        case "967": return "Yemen";
        case "212": return "Western Sahara";
        case "681": return "Wallis and Futuna";
        case "1340": return "U.S. Virgin Islands";
        case "84": return "Vietnam";
        case "58": return "Venezuela";
        case "379": return "Vatican";
        case "678": return "Vanuatu";
        case "998": return "Uzkbekistan";
        case "1": return "United States";
        case "598": return "Uruguay";
        case "380": return "Ukraine";
        case "44": return "United Kingdom";
        case "256": return "Uganda";
        case "971": return "United Arab Emirates";
        case "688": return "Tuvalu";
        case "1649": return "Turks and Caicos Islands";
        case "993": return "Turkmenistan";
        case "90": return "Turkey";
        case "216": return "Tunisia";
        case "1868": return "Trinidad and Tobago";
        case "676": return "Tonga";
        case "690": return "Tokelau";
        case "228": return "Togo";
        case "66": return "Thailand";
        case "255": return "Tanzania";
        case "992": return "Tajikistan";
        case "886": return "Taiwan";
        case "963": return "Syria";
        case "41": return "Switzerland";
        case "46": return "Sweden";
        case "268": return "Swaziland";
        case "47": return "Svalbard and Jan Mayen";
        case "597": return "Suriname";
        case "249": return "Sudan";
        case "1784": return "Saint Vincent and the Grenadines";

        //case DEFAULT_COUNTRY_CODE: // fallthrough
        default: return DEFAULT_COUNTRY;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Country // instanceof handles nulls
                && this.value.equals(((Country) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Replaces this person's emails with the emails in the argument tag set.
     */
    public void setCountry(Country country) {
        this.country.set(requireNonNull(country));
    }

    @Override
    public ObjectProperty<Country> countryProperty() {
        return country;
    }

    @Override
    public Country getCountry() {
        return country.get();
    }

```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
                // Phone, Country fields edited to ensure sample persons are populated with Country information
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Country(""),
                        getEmailSet("alexyeoh@example.com"),
                    new Address("30, Geylang Street 29, #06-40, Singapore 760770"), getScheduleSet(asList("15-01-2017",
                    "03-01-2018"), asList("Team meeting", "Interview"), asList("Alex Yeoh", "Alex Yeoh")),
                        getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("+65 99272758"), new Country("65"),
                        getEmailSet("berniceyu@example.com"),
                    new Address("30, Lorong 3 Serangoon Gardens, #07-18, Singapore 807777"), getScheduleSet(asList(
                    "15-01-2017", "04-04-2017"), asList("Team meeting", "Team bonding"),
                        asList("Bernice Yu", "Bernice Yu")), getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("+84 93210283"), new Country("84"),
                        getEmailSet("charlotte@example.com"),
                    new Address("11, Ang Mo Kio Street 74, #11-04, singapore 506666"),
                    getScheduleSet(asList("15-01-2017", "04-04-2017"), asList("Team meeting", "Team bonding"),
                            asList("Charlotte Oliveiro", "Charlotte Oliveiro")), getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("+65 91031282"), new Country("65"),
                        getEmailSet("lidavid@example.com"),
                    new Address("436, Serangoon Gardens Street 26, #16-43, singapore 707777"), getScheduleSet(asList(
                    "15-01-2017", "03-01-2018"), asList("Team meeting", "Interview"),
                        asList("David Li", "David Li")), getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("+58 92492021"), new Country("58"),
                        getEmailSet("irfan@example.com"),
                    new Address("47, Tampines Street 20, #17-35, singapore 303333"),
                    getScheduleSet(asList("15-01-2017", "08-09-2018"), asList("Team meeting", "Study Tour"),
                            asList("Irfan Ibrahim", "Irfan Ibrahim")), getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Country(""),
                        getEmailSet("royb@example.com"),
                    new Address("45, Aljunied Street 85, #11-31, singapore 304444"),
                    getScheduleSet(asList("15-01-2017", "25-12-2017"), asList("Team meeting", "Christmas dinner"),
                            asList("Roy Balakrishnan", "Roy Balakrishnan")), getTagSet("colleagues"))
```
