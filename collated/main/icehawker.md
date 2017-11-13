# icehawker
###### \java\seedu\address\logic\commands\BackupCommand.java
``` java
/**
 * Backs up user's address book in their designated directory.
 */
public class BackupCommand extends Command {

    public static final String COMMAND_WORD = "backup";

    public static final String COMMAND_ALIAS = "b";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (alias: " + COMMAND_ALIAS + ")"
            + ": Backs up your address book to the directory of your choice. Note that existing backups in the same "
            + "location must be removed or renamed as file overwriting is OFF. \n"
            + "Parameters: "
            + "Location of target directory.";

    // if input does not contain "\addressbook.xml", it will be appended by parser.
    public static final String BACKUP_DIR_SUFFIX = "addressbook.xml";
    public static final String BACKUP_DIR_SUFFIX_ALT = "\\addressbook.xml";

    public static final String BACKUP_SUCCESS_MESSAGE = "Address Book backed up at directory: %1$s.";
    public static final String BACKUP_FAILURE_MESSAGE = "Address Book could not be backed up at directory: %1$s. "
            + "Please check target path.";
    private String address;

    public BackupCommand(String targetAddress) {
        this.address = targetAddress;
    }

    @Override
    public CommandResult execute() throws CommandException {
        // source expected to stay in default directory
        Path source = Paths.get("data/addressbook.xml");
        // user defined target directory
        Path target = Paths.get(address);
        try {
            // clone addressbook into target
            Files.copy(source, target);
        } catch (IOException e1) {
            return new CommandResult(String.format(BACKUP_FAILURE_MESSAGE, address));
        }

        return new CommandResult(String.format(BACKUP_SUCCESS_MESSAGE, address));
    }

    public String getLocation() {
        return this.address;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BackupCommand // instanceof handles nulls
                && this.address.equals(((BackupCommand) other).address)); // state check
    }
}
```
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
###### \java\seedu\address\logic\commands\HelpCommand.java
``` java
    public static final String COMMAND_QUICK_HELP_WORD = "command";
    public static final String COMMAND_QUICK_HELP =
            "Quick command keyword help: " + "    F1: Full Help window;     F2: Calendar; \n"
                    + AddCommand.COMMAND_WORD + " / " + AddCommand.COMMAND_ALIAS + ";     "
                    + ClearCommand.COMMAND_WORD + " / " + ClearCommand.COMMAND_ALIAS + ";     "
                    + CopyCommand.COMMAND_WORD + " / " + CopyCommand.COMMAND_ALIAS + ";     "
                    + DeleteCommand.COMMAND_WORD + " / " + DeleteCommand.COMMAND_ALIAS + ";     "
                    + CalendarCommand.COMMAND_WORD + " / " + CalendarCommand.COMMAND_ALIAS + ";     "
                    + EditCommand.COMMAND_WORD + " / " + EditCommand.COMMAND_ALIAS + ";     "
                    + ExitCommand.COMMAND_WORD + " / " + ExitCommand.COMMAND_ALIAS + ";     "
                    + FindCommand.COMMAND_WORD + " / " + FindCommand.COMMAND_ALIAS + ";     "
                    + HelpCommand.COMMAND_WORD + " / " + HelpCommand.COMMAND_ALIAS + "; \n"
                    + HistoryCommand.COMMAND_WORD + " / " + HistoryCommand.COMMAND_ALIAS + ";     "
                    + ListCommand.COMMAND_WORD + " / " + ListCommand.COMMAND_ALIAS + ";     "
                    + RedoCommand.COMMAND_WORD + " / " + RedoCommand.COMMAND_ALIAS + ";     "
                    + ScheduleCommand.COMMAND_WORD + " / " + ScheduleCommand.COMMAND_ALIAS + ";     "
                    + LocateCommand.COMMAND_WORD + " / " + LocateCommand.COMMAND_ALIAS + ";     "
                    + UndoCommand.COMMAND_WORD + " / " + UndoCommand.COMMAND_ALIAS + ";     "
                    + BackupCommand.COMMAND_WORD + " / " + BackupCommand.COMMAND_ALIAS + ";     "
                    + Country.COMMAND_WORD;

```
###### \java\seedu\address\logic\commands\HelpCommand.java
``` java
        case Country.COMMAND_WORD:
            commandResult = Country.getCodeList();
            break;

        case HelpCommand.COMMAND_QUICK_HELP_WORD:
            commandResult = COMMAND_QUICK_HELP;
            break;

        case BackupCommand.COMMAND_ALIAS:
            //fallthrough

        case BackupCommand.COMMAND_WORD:
            commandResult = BackupCommand.MESSAGE_USAGE;
            break;

```
###### \java\seedu\address\logic\parser\BackupCommandParser.java
``` java
/**
 * Parses input arguments to create directory String used in BackupCommand
 */
public class BackupCommandParser implements Parser<BackupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BackupCommand
     * and returns a BackupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public BackupCommand parse(String args) throws ParseException {
        try {
            String targetAddress = ParserUtil.parseBackup(args);
            return new BackupCommand(targetAddress);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
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
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code address} returns a cleaned version, in case user has not included the file name in input.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the input address is invalid.
     */
    public static String parseBackup(String address) throws IllegalValueException {
        requireNonNull(address);
        if (!address.contains(BACKUP_DIR_SUFFIX)) {
            // if input ends with '\' character, concat without '\' symbol
            if (address.contains("/(?:\\)$/")) {
                return address.trim().concat(BACKUP_DIR_SUFFIX);
            } else {
                return address.trim().concat(BACKUP_DIR_SUFFIX_ALT);
            }
        }
        return address.trim();
    }

```
###### \java\seedu\address\model\person\Country.java
``` java
/**
 * Corresponding alias list of each country code and its corresponding .countryName().
 * Guarantees: immutable; is valid as declared in {@link #isValidCode(String)}
 */
public class Country {

    public static final String COMMAND_WORD = "codes";
    public static final String DEFAULT_COUNTRY_CODE = "";
    public static final String DEFAULT_COUNTRY = "Country Unavailable";
    private static List<String> codeList;
    private static Map <String, String> countryMap;
    public final String value;


    /**
     * Converts a country code to country name.
     */
    public Country(String countryCode) {
        requireNonNull(countryCode);
        this.countryMap = createMap();
        this.value = getName(countryCode); // value is a country name
    }

    /**
     * Creates map of country codes and names.
     */
    public static Map <String, String> createMap() {
        Map <String, String> countries = new HashMap<>();
        // NOTE: all country codes with prefix "1-" and "44-" are not yet allowed by regex.
        countries.put(DEFAULT_COUNTRY_CODE, DEFAULT_COUNTRY);
        countries.put("65", "Singapore");
        countries.put("93", "Afghanistan");
        countries.put("355", "Albania");
        countries.put("213", "Algeria");
        countries.put("1-684", "American Samoa");
        countries.put("376", "Andorra");
        countries.put("244", "Angola");
        countries.put("1-264", "Anguilla");
        countries.put("672", "Antarctica");
        countries.put("1-268", "Antigua and Barbuda");
        countries.put("54", "Argentina");
        countries.put("374", "Armenia");
        countries.put("297", "Aruba");
        countries.put("61", "Australia");
        countries.put("43", "Austria");
        countries.put("994", "Azerbaijan");
        countries.put("1-242", "Bahamas");
        countries.put("973", "Bahrain");
        countries.put("880", "Bangladesh");
        countries.put("1-246", "Barbados");
        countries.put("375", "Belarus");
        countries.put("32", "Belgium");
        countries.put("501", "Belize");
        countries.put("229", "Benin");
        countries.put("1-441", "Bermuda");
        countries.put("975", "Bhutan");
        countries.put("591", "Bolivia");
        countries.put("387", "Bosnia and Herzegovina");
        countries.put("267", "Botswana");
        countries.put("55", "Brazil");
        countries.put("246", "British Indian Ocean Territory");
        countries.put("1-284", "British Virgin Islands");
        countries.put("673", "Brunei");
        countries.put("359", "Bulgaria");
        countries.put("226", "Burkina Faso");
        countries.put("257", "Burundi");
        countries.put("855", "Cambodia");
        countries.put("237", "Cameroon");
        countries.put("1", "Canada");
        countries.put("238", "Cape Verde");
        countries.put("1-345", "Cayman Islands");
        countries.put("236", "Central African Republic");
        countries.put("235", "Chad");
        countries.put("56", "Chile");
        countries.put("86", "China");
        countries.put("61", "Christmas Island");
        countries.put("61", "Cocos Islands");
        countries.put("57", "Colombia");
        countries.put("269", "Comoros");
        countries.put("682", "Cook Islands");
        countries.put("506", "Costa Rica");
        countries.put("385", "Croatia");
        countries.put("53", "Cuba");
        countries.put("599", "Curacao");
        countries.put("357", "Cyprus");
        countries.put("420", "Czech Republic");
        countries.put("243", "Democratic Republic of the Congo");
        countries.put("45", "Denmark");
        countries.put("253", "Djibouti");
        countries.put("1-767", "Dominica");
        countries.put("1-809", "Dominican Republic");
        countries.put("1-829", "Dominican Republic");
        countries.put("1-849", "Dominican Republic");
        countries.put("670", "East Timor");
        countries.put("593", "Ecuador");
        countries.put("20", "Egypt");
        countries.put("503", "El Salvador");
        countries.put("240", "Equatorial Guinea");
        countries.put("291", "Eritrea");
        countries.put("372", "Estonia");
        countries.put("251", "Ethiopia");
        countries.put("500", "Falkland Islands");
        countries.put("298", "Faroe Islands");
        countries.put("679", "Fiji");
        countries.put("358", "Finland");
        countries.put("33", "France");
        countries.put("689", "French Polynesia");
        countries.put("241", "Gabon");
        countries.put("220", "Gambia");
        countries.put("995", "Georgia");
        countries.put("49", "Germany");
        countries.put("233", "Ghana");
        countries.put("350", "Gibraltar");
        countries.put("30", "Greece");
        countries.put("299", "Greenland");
        countries.put("1-473", "Grenada");
        countries.put("1-671", "Guam");
        countries.put("502", "Guatemala");
        countries.put("44-1481", "Guernsey");
        countries.put("224", "Guinea");
        countries.put("245", "Guinea-Bissau");
        countries.put("592", "Guyana");
        countries.put("509", "Haiti");
        countries.put("504", "Honduras");
        countries.put("852", "Hong Kong");
        countries.put("36", "Hungary");
        countries.put("354", "Iceland");
        countries.put("91", "India");
        countries.put("62", "Indonesia");
        countries.put("98", "Iran");
        countries.put("964", "Iraq");
        countries.put("353", "Ireland");
        countries.put("44-1624", "Isle of Man");
        countries.put("972", "Israel");
        countries.put("39", "Italy");
        countries.put("225", "Ivory Coast");
        countries.put("1-876", "Jamaica");
        countries.put("81", "Japan");
        countries.put("44-1534", "Jersey");
        countries.put("962", "Jordan");
        countries.put("7", "Kazakhstan");
        countries.put("254", "Kenya");
        countries.put("686", "Kiribati");
        countries.put("383", "Kosovo");
        countries.put("965", "Kuwait");
        countries.put("996", "Kyrgyzstan");
        countries.put("856", "Laos");
        countries.put("371", "Latvia");
        countries.put("961", "Lebanon");
        countries.put("266", "Lesotho");
        countries.put("231", "Liberia");
        countries.put("218", "Libya");
        countries.put("423", "Liechtenstein");
        countries.put("370", "Lithuania");
        countries.put("352", "Luxembourg");
        countries.put("853", "Macau");
        countries.put("389", "Macedonia");
        countries.put("261", "Madagascar");
        countries.put("265", "Malawi");
        countries.put("60", "Malaysia");
        countries.put("960", "Maldives");
        countries.put("223", "Mali");
        countries.put("356", "Malta");
        countries.put("692", "Marshall Islands");
        countries.put("222", "Mauritania");
        countries.put("230", "Mauritius");
        countries.put("262", "Mayotte");
        countries.put("52", "Mexico");
        countries.put("691", "Micronesia");
        countries.put("373", "Moldova");
        countries.put("377", "Monaco");
        countries.put("976", "Mongolia");
        countries.put("382", "Montenegro");
        countries.put("1-664", "Montserrat");
        countries.put("212", "Morocco");
        countries.put("258", "Mozambique");
        countries.put("95", "Myanmar");
        countries.put("264", "Namibia");
        countries.put("674", "Nauru");
        countries.put("977", "Nepal");
        countries.put("31", "Netherlands");
        countries.put("599", "Netherlands Antilles");
        countries.put("687", "New Caledonia");
        countries.put("64", "New Zealand");
        countries.put("505", "Nicaragua");
        countries.put("227", "Niger");
        countries.put("234", "Nigeria");
        countries.put("683", "Niue");
        countries.put("850", "North Korea");
        countries.put("1-670", "Northern Mariana Islands");
        countries.put("47", "Norway");
        countries.put("968", "Oman");
        countries.put("92", "Pakistan");
        countries.put("680", "Palau");
        countries.put("970", "Palestine");
        countries.put("507", "Panama");
        countries.put("675", "Papua New Guinea");
        countries.put("595", "Paraguay");
        countries.put("51", "Peru");
        countries.put("63", "Philippines");
        countries.put("64", "Pitcairn");
        countries.put("48", "Poland");
        countries.put("351", "Portugal");
        countries.put("1-787, 1-939", "Puerto Rico");
        countries.put("974", "Qatar");
        countries.put("242", "Republic of the Congo");
        countries.put("262", "Reunion");
        countries.put("40", "Romania");
        countries.put("7", "Russia");
        countries.put("250", "Rwanda");
        countries.put("590", "Saint Barthelemy");
        countries.put("290", "Saint Helena");
        countries.put("1-869", "Saint Kitts and Nevis");
        countries.put("1-758", "Saint Lucia");
        countries.put("590", "Saint Martin");
        countries.put("508", "Saint Pierre and Miquelon");
        countries.put("1-784", "Saint Vincent and the Grenadines");
        countries.put("685", "Samoa");
        countries.put("378", "San Marino");
        countries.put("239", "Sao Tome and Principe");
        countries.put("966", "Saudi Arabia");
        countries.put("221", "Senegal");
        countries.put("381", "Serbia");
        countries.put("248", "Seychelles");
        countries.put("232", "Sierra Leone");
        countries.put("65", "Singapore");
        countries.put("1-721", "Sint Maarten");
        countries.put("421", "Slovakia");
        countries.put("386", "Slovenia");
        countries.put("677", "Solomon Islands");
        countries.put("252", "Somalia");
        countries.put("27", "South Africa");
        countries.put("82", "South Korea");
        countries.put("211", "South Sudan");
        countries.put("34", "Spain");
        countries.put("94", "Sri Lanka");
        countries.put("249", "Sudan");
        countries.put("597", "Suriname");
        countries.put("47", "Svalbard and Jan Mayen");
        countries.put("268", "Swaziland");
        countries.put("46", "Sweden");
        countries.put("41", "Switzerland");
        countries.put("963", "Syria");
        countries.put("886", "Taiwan");
        countries.put("992", "Tajikistan");
        countries.put("255", "Tanzania");
        countries.put("66", "Thailand");
        countries.put("228", "Togo");
        countries.put("690", "Tokelau");
        countries.put("676", "Tonga");
        countries.put("1-868", "Trinidad and Tobago");
        countries.put("216", "Tunisia");
        countries.put("90", "Turkey");
        countries.put("993", "Turkmenistan");
        countries.put("1-649", "Turks and Caicos Islands");
        countries.put("688", "Tuvalu");
        countries.put("1-340", "U.S. Virgin Islands");
        countries.put("256", "Uganda");
        countries.put("380", "Ukraine");
        countries.put("971", "United Arab Emirates");
        countries.put("44", "United Kingdom");
        countries.put("1", "United States");
        countries.put("598", "Uruguay");
        countries.put("998", "Uzbekistan");
        countries.put("678", "Vanuatu");
        countries.put("379", "Vatican");
        countries.put("58", "Venezuela");
        countries.put("84", "Vietnam");
        countries.put("681", "Wallis and Futuna");
        countries.put("212", "Western Sahara");
        countries.put("967", "Yemen");
        countries.put("260", "Zambia");
        countries.put("263", "Zimbabwe");
        codeList = new ArrayList<String>(countries.keySet());
        Collections.sort(codeList);
        return countries;
    }

    public static String getCodeList() {
        int count = 0;
        String output = "Valid codes:";
        for (String code:codeList) {
            if (count < 29) {
                output += code + ", ";
            } else {
                output += code + ", \n";
            }
            count += 1; // record iterations until next newline
            count = count % 30; // repeat to 30
        }
        return output;
    }

    /**
     * Generic code-to-name lookup (Does not require Country object instantiation).
     */
    public static String getName(String code) {
        return countryMap.getOrDefault(code, DEFAULT_COUNTRY);
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
###### \java\seedu\address\model\person\Phone.java
``` java
    // without prefix
    public static final String CODE_VALIDATION_REGEX =
            "^(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|2[98654321]\\d|9[8543210]|8[6421]|"
            + "6[6543210]|5[87654321]|4[987654310]|3[9643210]|2[70]|7|1)$";
    private static final String PHONE_VALIDATION_REGEX = "\\d{4,16}";
    // with country code prefix
    // current regex DOES NOT INCLUDE codes from 1000 onwards!
    private static final String PHONE_VALIDATION_REGEX_ALT =
            "\\+(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|2[98654321]\\d|9[8543210]|8[6421]|"
            + "6[6543210]|5[87654321]|4[987654310]|3[9643210]|2[70]|7|1)\\s\\d{4,16}$";
    public final String value;
    private String countryCode;
```
###### \java\seedu\address\model\person\Phone.java
``` java
    /**
     * Extracts country code from a valid phone number, otherwise returns a default code.
     * Note that any unacceptable input has already been rejected upstream by {@link #isValidPhone(String)}.
     */
    public static String trimCode(String trimmedPhone) {
        // only attempt to extract country code if regex is ALT
        if (trimmedPhone.matches(PHONE_VALIDATION_REGEX_ALT)) {
            // take pattern: end with whitespace (expected for ALT regex)
            String[] split = trimmedPhone.split("\\s+");
            return (split[0].trim()).substring(1);
        } else {
            return DEFAULT_COUNTRY_CODE.trim();
        }
    }

    /**
     * Returns true if a given string is a valid person country code. Only for JUnit tests.
     */
    public static boolean isValidCode(String test) {
        return test.matches(CODE_VALIDATION_REGEX);
    }

    /**
     * Returns requested country code String.
     * Note that any unacceptable input has already been rejected upstream by {@link #isValidPhone(String)}.
     */
    public String getCountryCode() {
        return countryCode;
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
###### \java\seedu\address\ui\WelcomeScreen.java
``` java
        raise(new NewResultAvailableEvent(COMMAND_QUICK_HELP, false));
```
