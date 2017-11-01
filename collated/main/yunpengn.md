# yunpengn
###### \java\seedu\address\logic\commands\configs\AddPropertyCommand.java
``` java
/**
 * Adds a new property to the application.
 */
public class AddPropertyCommand extends ConfigCommand {
    public static final String MESSAGE_USAGE = "Example: " + COMMAND_WORD + " --add-property "
            + "s/b f/birthday m/Birthday needs to be a valid date format "
            + "r/^(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])[0-9]{4}";

    static final String MESSAGE_DUPLICATE_PROPERTY =
            "Another property with the same short name already exists in the application.";
    static final String MESSAGE_INVALID_REGEX = "The regular expression you provide is invalid.";

    private final String shortName;
    private final String fullName;
    private final String constraintMessage;
    private final String regex;

    public AddPropertyCommand(String configValue, String shortName, String fullName, String message, String regex) {
        super(ADD_PROPERTY, configValue);
        this.shortName = shortName;
        this.fullName = fullName;
        this.constraintMessage = message;
        this.regex = regex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.addProperty(shortName, fullName, constraintMessage, regex);
            return new CommandResult(String.format(MESSAGE_SUCCESS, configValue));
        } catch (DuplicatePropertyException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PROPERTY);
        } catch (PatternSyntaxException pse) {
            throw new CommandException(MESSAGE_INVALID_REGEX);
        }
    }
}
```
###### \java\seedu\address\logic\commands\configs\ChangeTagColorCommand.java
``` java
/**
 * Changes the color of an existing tag.
 */
public class ChangeTagColorCommand extends ConfigCommand {
    public static final String MESSAGE_SUCCESS = "The color of tag %1$s has been changed to %2$s.";
    public static final String MESSAGE_USAGE =  "Example: " + COMMAND_WORD + " --set-tag-color "
            + "friends blue";
    private static final String MESSAGE_NO_SUCH_TAG = "There is no such tag.";

    private Tag tag;
    private String newColor;

    public ChangeTagColorCommand(String configValue, String tagName, String tagColor) throws ParseException {
        super(TAG_COLOR, configValue);

        try {
            tag = new Tag(tagName);
        } catch (IllegalValueException e) {
            throw new ParseException(MESSAGE_TAG_CONSTRAINTS);
        }
        this.newColor = tagColor;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!model.hasTag(tag)) {
            throw new CommandException(MESSAGE_NO_SUCH_TAG);
        }

        model.setTagColor(tag, newColor);
        return new CommandResult(String.format(MESSAGE_SUCCESS, tag, newColor));
    }
}
```
###### \java\seedu\address\logic\commands\configs\ConfigCommand.java
``` java
/**
 * Customizes the configuration of the application.
 */
public abstract class ConfigCommand extends Command {
    public static final String COMMAND_WORD = "config";
    public static final String COMMAND_ALIAS = "cfg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the configuration of the application. "
            + "Parameters: " + "--CONFIG_TYPE "
            + "NEW_CONFIG_VALUE\n"
            + "Example: " + COMMAND_WORD + " --set-tag-color "
            + "friends #9381a0";

    public static final String MESSAGE_SUCCESS = "Configuration changed: %1$s";

    /**
     * Different types of sub-commands within {@link ConfigCommand}.
     */
    public enum ConfigType {
        ADD_PROPERTY, TAG_COLOR
    }

    public static final HashMap<String, ConfigType> TO_ENUM_CONFIG_TYPE = new HashMap<>();

    static {
        TO_ENUM_CONFIG_TYPE.put("add-property", ConfigType.ADD_PROPERTY);
        TO_ENUM_CONFIG_TYPE.put("set-tag-color", ConfigType.TAG_COLOR);
    }

    protected String configValue;

    private ConfigType configType;

    public ConfigCommand(ConfigType configType, String configValue) {
        this.configType = configType;
        this.configValue = configValue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ConfigCommand // instanceof handles nulls
                && configType.equals(((ConfigCommand) other).configType)
                && configValue.equals(((ConfigCommand) other).configValue));
    }
}
```
###### \java\seedu\address\logic\commands\imports\ImportCommand.java
``` java
/**
 * Imports data from various format to the application.
 */
public abstract class ImportCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_ALIAS = "i";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports data from various locations in various formats.\n"
            + "Examples:\n"
            + COMMAND_WORD + " --script C:\\Users\\John Doe\\Documents\\bonus.bo (Windows)\n"
            + COMMAND_WORD + " /Users/John Doe/Documents/bonus.xml (macOS, Linux)\n";

    public static final String MESSAGE_IMPORT_SUCCESS = "Imported data from: %1$s";
    public static final String MESSAGE_PROBLEM_READING_FILE = "There is a problem when the application tried to"
            + " read the given file. Please check the file permission.";
    public static final String MESSAGE_NOT_XML_FILE = "According to the extension, the file is not a valid XML "
            + "file.\nYou need to specify with explicit parameter if you want to use other formats.";
    public static final String MESSAGE_NOT_BO_FILE = "According to the extension, the file is not a valid BoNUS"
            + "script file (should end with .bo).";
```
###### \java\seedu\address\logic\commands\imports\ImportCommand.java
``` java
    /**
     * Different types of sub-commands within {@link ImportCommand}.
     */
    public enum ImportType {
        XML, SCRIPT, NUSMODS
    }

    public static final HashMap<String, ImportType> TO_ENUM_IMPORT_TYPE = new HashMap<>();

    static {
        TO_ENUM_IMPORT_TYPE.put("xml", ImportType.XML);
        TO_ENUM_IMPORT_TYPE.put("script", ImportType.SCRIPT);
        TO_ENUM_IMPORT_TYPE.put("nusmods", ImportType.NUSMODS);
    }

    protected String path;

    private ImportType importType;

    public ImportCommand(String path, ImportType importType) {
        this.path = path;
        this.importType = importType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && importType.equals(((ImportCommand) other).importType)
                && path.equals(((ImportCommand) other).path));
    }
}
```
###### \java\seedu\address\logic\parser\ArgumentMultimap.java
``` java
    /**
     * Returns the mapping of {@code Prefix} and their corresponding last values for all {@code prefix}es (only if
     * there is a value present). <b>Notice</b>: the return {@code HashMap} does not include preamble and tags.
     */
    public HashMap<Prefix, String> getAllValues() {
        HashMap<Prefix, String> values = new HashMap<>();

        // Need to manually remove preamble from here. We are creating a new copy of all prefixes, so the actual
        // instance variable will not be affected.
        Set<Prefix> prefixes = new HashSet<>(internalMap.keySet());
        prefixes.remove(new Prefix(""));
        prefixes.remove(PREFIX_TAG);

        for (Prefix prefix: prefixes) {
            getValue(prefix).ifPresent(s -> values.put(prefix, s));
        }

        return values;
    }
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    /* Prefix definitions for adding a new customize property. */
    public static final Prefix PREFIX_SHORT_NAME = new Prefix("s/");
    public static final Prefix PREFIX_FULL_NAME = new Prefix("f/");
    public static final Prefix PREFIX_MESSAGE = new Prefix("m/");
    public static final Prefix PREFIX_REGEX = new Prefix("r/");
```
###### \java\seedu\address\logic\parser\ConfigCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ConfigCommand object
 */
public class ConfigCommandParser implements Parser<ConfigCommand> {
    // Some messages ready to use.
    public static final String CONFIG_TYPE_NOT_FOUND = "The configuration you want to change is not "
            + "available or the command entered is incomplete.";
    public static final String COLOR_CODE_WRONG = "The color must be one of the pre-defined color names or "
            + "a valid hexadecimal RGB value";
    private static final String MESSAGE_REGEX_TOGETHER = "Constraint message and regular expression must be "
            + "both present or absent";

    /* Regular expressions for validation. ArgumentMultiMap not applicable here. */
    private static final Pattern CONFIG_COMMAND_FORMAT = Pattern.compile("--(?<configType>\\S+)(?<configValue>.+)");
    private static final Pattern TAG_COLOR_FORMAT = Pattern.compile("(?<tagName>\\p{Alnum}+)\\s+(?<tagNewColor>.+)");
    private static final Pattern RGB_FORMAT = Pattern.compile("#(?<rgbValue>([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$)");

    // Some pre-defined colors for convenience.
    private static final HashMap<String, String> preDefinedColors = new HashMap<>();

    /**
     * Loads all pre-defined colors here. If you want to define more, you can get more color codes can be obtained from
     * https://www.w3schools.com/colors/colors_names.asp Make sure you put them in alphabetical order.
     */
    static {
        preDefinedColors.put("black", "#000000");
        preDefinedColors.put("blue", "#0000FF");
        preDefinedColors.put("brown", "#A52A2A");
        preDefinedColors.put("green", "#008000");
        preDefinedColors.put("red", "#FF0000");
        preDefinedColors.put("white", "#FFFFFF");
        preDefinedColors.put("yellow", "#FFFF00");
    }

    @Override
    public ConfigCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Defensive programming here to use trim again.
        final Matcher matcher = CONFIG_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfigCommand.MESSAGE_USAGE));
        }

        final String configType = matcher.group("configType").trim();
        if (!checkConfigType(configType)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CONFIG_TYPE_NOT_FOUND));
        }

        final ConfigType enumConfigType = toEnumType(configType);
        final String configValue = matcher.group("configValue").trim();

        return checkConfigValue(enumConfigType, configValue);
    }

    private boolean checkConfigType(String type) {
        return ConfigCommand.TO_ENUM_CONFIG_TYPE.containsKey(type);
    }

    private ConfigType toEnumType(String type) {
        return ConfigCommand.TO_ENUM_CONFIG_TYPE.get(type);
    }

    /**
     * Validates the input for different {@link ConfigType} and creates an {@link ConfigCommand} accordingly.
     */
    private ConfigCommand checkConfigValue(ConfigType enumConfigType, String value) throws ParseException {
        switch (enumConfigType) {
        case ADD_PROPERTY:
            return checkAddProperty(value);
        case TAG_COLOR:
            return checkTagColor(value);
        default:
            System.err.println("Unknown ConfigType. Should never come to here.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CONFIG_TYPE_NOT_FOUND));
        }
    }

    /**
     * Creates an {@link ChangeTagColorCommand}.
     */
    private ChangeTagColorCommand checkTagColor(String value) throws ParseException {
        Matcher matcher = TAG_COLOR_FORMAT.matcher(value.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeTagColorCommand.MESSAGE_USAGE));
        }

        final String tagName = matcher.group("tagName").trim();
        String tagColor = matcher.group("tagNewColor").trim();

        // Use the corresponding RGB value to replace pre-defined color names.
        if (preDefinedColors.containsKey(tagColor)) {
            tagColor = preDefinedColors.get(tagColor);
        }

        matcher = RGB_FORMAT.matcher(tagColor.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, COLOR_CODE_WRONG));
        }
        String colorRgbValue = matcher.group("rgbValue").trim();

        return new ChangeTagColorCommand(value, tagName, colorRgbValue);
    }

    /**
     * Creates an {@link AddPropertyCommand}.
     */
    private AddPropertyCommand checkAddProperty(String value) throws ParseException {
        /*
        * Hack here: ArgumentTokenizer requires a whitespace before each prefix to count for an occurrence. Thus, we
        * have to explicitly add a whitespace before the string so as to successfully extract the first prefix.
        */
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(" " + value,
                PREFIX_SHORT_NAME, PREFIX_FULL_NAME, PREFIX_MESSAGE, PREFIX_REGEX);

        // shortName and fullName must be supplied by the user.
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_SHORT_NAME, PREFIX_FULL_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPropertyCommand.MESSAGE_USAGE));
        }
        String shortName = argMultimap.getValue(PREFIX_SHORT_NAME).get();
        String fullName = capitalize(argMultimap.getValue(PREFIX_FULL_NAME).get());

        // message and regex must be supplied together or both be absent.
        if (!(ParserUtil.arePrefixesPresent(argMultimap, PREFIX_MESSAGE, PREFIX_REGEX)
                || ParserUtil.arePrefixesAbsent(argMultimap, PREFIX_MESSAGE, PREFIX_REGEX))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REGEX_TOGETHER));
        }
        String message = argMultimap.getValue(PREFIX_MESSAGE).orElse(String.format(DEFAULT_MESSAGE, fullName));
        String regex = argMultimap.getValue(PREFIX_REGEX).orElse(DEFAULT_REGEX);

        return new AddPropertyCommand(value, shortName, fullName, message, regex);
    }

    /**
     * Converts the first letter in {@code str} to upper-case (only if it starts with an alphabet).
     */
    private String capitalize(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }

        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
```
###### \java\seedu\address\logic\parser\ImportCommandParser.java
``` java
/**
 * Parses input arguments and creates a new sub-command of {@link ImportCommand} object.
 */
public class ImportCommandParser implements Parser<ImportCommand> {
    // Some messages ready to use.
    public static final String IMPORT_TYPE_NOT_FOUND = "The format of the data you want to import is "
            + "currently not supported";

    /* Regular expressions for validation. */
    private static final Pattern IMPORT_COMMAND_FORMAT = Pattern.compile("--(?<importType>\\S+)\\s+(?<path>.+)");
    private static final String ARG_BEGIN_WITH = "--";
    private static final String IMPORT_DEFAULT_TYPE = "--xml ";

    @Override
    public ImportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.trim();

        // Be default, import from .xml file if not specified by the user.
        if (!args.startsWith(ARG_BEGIN_WITH)) {
            args = IMPORT_DEFAULT_TYPE + args;
        }

        // Matches the import file type and import file path.
        final Matcher matcher = IMPORT_COMMAND_FORMAT.matcher(args);
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        final String importType = matcher.group("importType").trim();
        if (!checkImportType(importType)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, IMPORT_TYPE_NOT_FOUND));
        }

        final ImportType enumImportType = toEnumType(importType);
        final String path = matcher.group("path").trim();

        return checkImportPath(enumImportType, path);
    }

    private boolean checkImportType(String type) {
        return ImportCommand.TO_ENUM_IMPORT_TYPE.containsKey(type);
    }

    private ImportType toEnumType(String type) {
        return ImportCommand.TO_ENUM_IMPORT_TYPE.get(type);
    }

    /**
     * Validates the input for different {@link ImportType} and creates an {@link ImportCommand} accordingly.
     */
    private ImportCommand checkImportPath(ImportType enumImportType, String path) throws ParseException {
        switch (enumImportType) {
        case XML:
            return checkXmlImport(path);
        case SCRIPT:
            return checkScriptImport(path);
        case NUSMODS:
            return checkNusmodsImport(path);
        default:
            System.err.println("Unknown ImportType. Should never come to here.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, IMPORT_TYPE_NOT_FOUND));
        }
    }

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses all properties in the given {@code HashMap}.
     *
     * @return a set containing all properties parsed.
     */
    public static Set<Property> parseProperties(HashMap<Prefix, String> values)
            throws IllegalValueException, PropertyNotFoundException {
        requireNonNull(values);
        Set<Property> properties = new HashSet<>();

        for (Map.Entry<Prefix, String> entry: values.entrySet()) {
            properties.add(new Property(entry.getKey().getPrefixValue(), entry.getValue()));
        }

        return properties;
    }
```
###### \java\seedu\address\logic\parser\person\AddCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        Set<Prefix> prefixes = PropertyManager.getAllPrefixes();
        prefixes.add(PREFIX_TAG);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);

        // TODO: Keep this checking for now. These pre-loaded properties are compulsory.
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Set<Property> propertyList = ParserUtil.parseProperties(argMultimap.getAllValues());
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            return new AddCommand(new Person(propertyList, tagList));
        } catch (IllegalValueException | PropertyNotFoundException | DuplicatePropertyException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    //=========== Model support for property component =============================================================

    /**
     * Adds a new customize property to {@code PropertyManager}.
     *
     * @throws DuplicatePropertyException if there already exists a property with the same {@code shortName}.
     * @throws PatternSyntaxException if the given regular expression contains invalid syntax.
     */
    @Override
    public void addProperty(String shortName, String fullName, String message, String regex)
            throws DuplicatePropertyException, PatternSyntaxException {
        PropertyManager.addNewProperty(shortName, fullName, message, regex);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Changes the displayed color of an existing tag (through {@link TagColorManager}).
     */
    public void setTagColor(Tag tag, String color) {
        TagColorManager.setColor(tag, color);
        indicateAddressBookChanged();
        raise(new TagColorChangedEvent(tag, color));
    }
```
###### \java\seedu\address\model\property\Address.java
``` java
/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address extends Property {
    private static final String PROPERTY_SHORT_NAME = "a";

    public Address(String value) throws IllegalValueException, PropertyNotFoundException {
        super(PROPERTY_SHORT_NAME, value);
    }

    /**
     * Returns true if a given string is a valid address.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(PropertyManager.getPropertyValidationRegex(PROPERTY_SHORT_NAME));
    }
}
```
###### \java\seedu\address\model\property\Email.java
``` java
/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email extends Property {
    private static final String PROPERTY_SHORT_NAME = "e";

    public Email(String value) throws IllegalValueException, PropertyNotFoundException {
        super(PROPERTY_SHORT_NAME, value);
    }

    /**
     * Returns true if a given string is a valid email address.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(PropertyManager.getPropertyValidationRegex(PROPERTY_SHORT_NAME));
    }
}
```
###### \java\seedu\address\model\property\exceptions\DuplicatePropertyException.java
``` java
/**
 * Signals that the property with the same short name already exists.
 */
public class DuplicatePropertyException extends Exception {
    public DuplicatePropertyException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\model\property\exceptions\PropertyNotFoundException.java
``` java
/**
 * Signals that the required property has not been defined yet.
 */
public class PropertyNotFoundException extends Exception {
    public PropertyNotFoundException() {
        super("Property not found.");
    }

    public PropertyNotFoundException(String shortName) {
        super(String.format(PROPERTY_NOT_FOUND, shortName));
    }
}
```
###### \java\seedu\address\model\property\Name.java
``` java
/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name extends Property {
    private static final String PROPERTY_SHORT_NAME = "n";

    public Name(String value) throws IllegalValueException, PropertyNotFoundException {
        super(PROPERTY_SHORT_NAME, value);
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(PropertyManager.getPropertyValidationRegex(PROPERTY_SHORT_NAME));
    }
}
```
###### \java\seedu\address\model\property\Phone.java
``` java
/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone extends Property {
    private static final String PROPERTY_SHORT_NAME = "p";

    public Phone(String value) throws IllegalValueException, PropertyNotFoundException {
        super(PROPERTY_SHORT_NAME, value);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PropertyManager.getPropertyValidationRegex(PROPERTY_SHORT_NAME));
    }
}
```
###### \java\seedu\address\model\property\Property.java
``` java
/**
 * A generic class that represents a property of a person. All properties of a person (including name, email, phone
 * and address) should inherit from this class.
 */
public class Property {
    /**
     * Why do we only store three fields as instance variables in this class?<br>
     *
     * {@link #shortName} is used as the identifier for the property, {@link #fullName} is used stored because we may
     * need to access it frequently (it will be a bad design decision if we have to perform HashMap access operation
     * whenever we need to get the full name of a property), and {@link #value} must be stored here apparently.
     */
    private final String shortName;
    private final String fullName;
    private String value;

    /**
     * Creates a property via its name in short form and its input value.
     *
     * @param shortName is the short name (identifier) of this property.
     */
    public Property(String shortName, String value) throws IllegalValueException, PropertyNotFoundException {
        if (!PropertyManager.containsShortName(shortName)) {
            throw new PropertyNotFoundException(shortName);
        }

        this.shortName = shortName;

        requireNonNull(value);
        if (!isValid(value)) {
            throw new IllegalValueException(PropertyManager.getPropertyConstraintMessage(shortName));
        }
        this.value = value;
        this.fullName = PropertyManager.getPropertyFullName(shortName);
    }

    /**
     * Returns if a given string is a valid value for this property.
     */
    public boolean isValid(String test) {
        return test.matches(PropertyManager.getPropertyValidationRegex(shortName));
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            // short circuit if same object.
            return true;
        } else if (other instanceof Property) {
            // instanceof handles nulls and type checking.
            Property otherProperty = (Property) (other);
            // key-value pair check
            return this.shortName.equals(otherProperty.getShortName())
                    && this.value.equals(otherProperty.getValue());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\property\PropertyManager.java
``` java
/**
 * Manages the different properties (both pre-loaded ones and customize ones) of all persons stored in the
 * application.
 *
 * Pre-loaded properties include {@code Address}, {@code Email}, {@code Name}, {@code Phone}. These pre-loaded
 * properties are subjected to changes in later versions.
 *
 * Customize properties include all properties except the pre-loaded ones, which are added by the following command:
 * <pre>{@code config --add-property <property_name> ...}</pre>
 *
 * TODO: Should we extend {@link seedu.address.commons.core.ComponentManager} as superclass?
 */
public class PropertyManager {
    // Default constraint setting for all properties.
    public static final String DEFAULT_MESSAGE = "%1$s can take any values, but it should not be blank.";
    public static final String DEFAULT_REGEX = "[^\\s].*";

    private static final String DEFAULT_PREFIX = "%1$s/";

    // Mapping from property short name to its prefixes (for all available properties).
    private static final HashMap<String, Prefix> propertyPrefixes = new HashMap<>();

    // Mapping from property short name to its full name.
    private static final HashMap<String, String> propertyFullNames = new HashMap<>();

    // Mapping from property name to the corresponding constraint message and validation regular expression.
    private static final HashMap<String, String> propertyConstraintMessages = new HashMap<>();
    private static final HashMap<String, String> propertyValidationRegex = new HashMap<>();

    // Records whether has been initialized before.
    private static boolean initialized = false;

    /**
     * Util for initialization of default pre-loaded properties. This method should not be called if there is
     * existing data loaded from local storage file.
     */
    public static void initializePropertyManager() {
        if (!initialized) {
            try {
                // Adds name as a pre-loaded property.
                addNewProperty("n", "Name",
                        "Person names should only contain alphanumeric characters and spaces, "
                                + "and it should not be blank",
                        "[\\p{Alnum}][\\p{Alnum} ]*");

                // Adds email as a pre-loaded property.
                addNewProperty("e", "Email",
                        "Person emails should be 2 alphanumeric/period strings separated by '@'",
                        "[\\w\\.]+@[\\w\\.]+");

                // Adds phone number as a pre-loaded property.
                addNewProperty("p", "Phone",
                        "Phone numbers can only contain numbers, and should be at least 3 digits long",
                        "\\d{3,}");

                // Adds address as a pre-loaded property.
                addNewProperty("a", "Address",
                        String.format(DEFAULT_MESSAGE, "Address"), DEFAULT_REGEX);

                // Adds time/date as a pre-loaded property.
                addNewProperty("dt", "DateTime", "Event date & time must be numbers "
                        + "followed by ddmmyyyy hh:mm",
                        "^(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])[0-9]{4}"
                                + "(\\s((0[1-9]|1[0-9]|2[0-3]):([0-5][0-9]))?$)");
            } catch (DuplicatePropertyException dpe) {
                throw new AssertionError("PreLoaded properties cannot be invalid", dpe);
            }

            initialized = true;
        }
    }

    /**
     * Adds a new available property with all the required information for setting up a property.
     *
     * TODO: Should we allow duplicates in full names of different properties?
     *
     * @param shortName is the short-form name of this property, usually consists of one or two letters, like a
     *                  (stands for address). It is usually the initial of the property name.
     * @param fullName is the full-form name of this property, should be a legal English word.
     * @param message is the constraint message of this property. It will be displayed when the value of this
     *                property does not pass the validation check.
     * @param regex is the regular expression used to perform input validation.
     *
     * @throws DuplicatePropertyException is thrown when a property with the same{@code shortName} already exists.
     * @throws PatternSyntaxException is thrown if the given regex is {@code regex} is invalid.
     */
    public static void addNewProperty(String shortName, String fullName, String message, String regex)
            throws DuplicatePropertyException, PatternSyntaxException {
        // Checks whether there exists a property with the same name.
        if (propertyPrefixes.containsKey(shortName)) {
            throw new DuplicatePropertyException(String.format(PROPERTY_EXISTS, shortName));
        }
        // Checks whether the regular expression is valid.
        Pattern.compile(regex);

        propertyPrefixes.put(shortName, new Prefix(String.format(DEFAULT_PREFIX, shortName)));
        propertyFullNames.put(shortName, fullName);
        propertyConstraintMessages.put(shortName, message);
        propertyValidationRegex.put(shortName, regex);
    }

    /**
     * Clears all properties stored in the {@link PropertyManager}.
     */
    public static void clearAllProperties() {
        propertyPrefixes.clear();
        propertyFullNames.clear();
        propertyConstraintMessages.clear();
        propertyValidationRegex.clear();
    }

    public static boolean containsShortName(String shortName) {
        return propertyPrefixes.containsKey(shortName);
    }

    public static String getPropertyFullName(String shortName) {
        return propertyFullNames.get(shortName);
    }

    public static String getPropertyConstraintMessage(String shortName) {
        return propertyConstraintMessages.get(shortName);
    }

    public static String getPropertyValidationRegex(String shortName) {
        return propertyValidationRegex.get(shortName);
    }

    public static HashSet<String> getAllShortNames() {
        return new HashSet<>(propertyPrefixes.keySet());
    }

    public static HashSet<Prefix> getAllPrefixes() {
        return new HashSet<>(propertyPrefixes.values());
    }
}
```
###### \java\seedu\address\model\property\UniquePropertyMap.java
``` java
/**
 * A HashMap of properties that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of map (list) operations for the app's features.
 *
 * Notice: Uniqueness is directly supported by internal HashMap, which makes it different from
 * {@link seedu.address.model.person.UniquePersonList} and {@link seedu.address.model.tag.UniqueTagList}.
 *
 * @see Property#equals(Object)
 */
public class UniquePropertyMap implements Iterable<Property> {
    private static final String PROPERTY_NOT_FOUND = "This person does not have such property.";
    private final ObservableMap<String, Property> internalMap = FXCollections.observableHashMap();

    /**
     * Constructs empty PropertyList.
     */
    public UniquePropertyMap() {}

    /**
     * Creates a UniquePropertyMap using given properties.
     * Enforces no nulls.
     */
    public UniquePropertyMap(Set<Property> properties) throws DuplicatePropertyException {
        requireAllNonNull(properties);

        for (Property property: properties) {
            add(property);
        }
    }

    /**
     * Returns all properties (collection of values in all entries) in this map as a Set. This set is mutable
     * and change-insulated against the internal list.
     */
    public Set<Property> toSet() {
        return new HashSet<>(internalMap.values());
    }

    /**
     * Replaces all the properties in this map with those in the argument property map.
     */
    public void setProperties(Set<Property> properties) throws DuplicatePropertyException {
        requireAllNonNull(properties);
        internalMap.clear();

        for (Property property: properties) {
            add(property);
        }
    }

    /**
     * Merges all properties from the argument list into this list. If a property with the same shortName already
     * exists in the list, it will not be merged in.
     */
    public void mergeFrom(UniquePropertyMap from) {
        for (Property property: from) {
            if (!containsProperty(property)) {
                internalMap.put(property.getShortName(), property);
            }
        }
    }

    /**
     * Returns true if there exists a property with the given shortName in the list.
     */
    public boolean containsProperty(String shortName) {
        requireNonNull(shortName);
        return internalMap.containsKey(shortName);
    }

    /**
     * Returns true if the list containsProperty an equivalent Property (with the same shortName)
     * as the given argument.
     */
    public boolean containsProperty(Property toCheck) {
        requireNonNull(toCheck);
        return containsProperty(toCheck.getShortName());
    }

    public String getPropertyValue(String shortName) throws PropertyNotFoundException {
        if (!containsProperty(shortName)) {
            throw new PropertyNotFoundException();
        }
        return internalMap.get(shortName).getValue();
    }

    /**
     * Adds a property to the map.
     *
     * @throws DuplicatePropertyException if the given property already exists in this list (or there exists a
     * property that is equal to the one in the argument). Since we are using {@link java.util.HashMap}, another
     * method must be used when we want to update the value of an existing property.
     */
    public void add(Property toAdd) throws DuplicatePropertyException {
        requireNonNull(toAdd);
        String shortName = toAdd.getShortName();

        if (containsProperty(shortName)) {
            throw new DuplicatePropertyException(String.format(PROPERTY_EXISTS, shortName));
        }
        internalMap.put(shortName, toAdd);
    }

    /**
     * Updates the value of an existing property in the map.
     *
     * @throws PropertyNotFoundException if there is no property with the same shortName in this map previously.
     */
    public void update(Property toUpdate) throws PropertyNotFoundException {
        requireNonNull(toUpdate);
        String shortName = toUpdate.getShortName();

        if (!containsProperty(shortName)) {
            throw new PropertyNotFoundException();
        }
        internalMap.put(shortName, toUpdate);
    }

    /**
     * Updates the value of the property if there already exists a property with the same shortName, otherwise
     * adds a new property.
     */
    public void addOrUpdate(Property toSet) {
        requireNonNull(toSet);
        String shortName = toSet.getShortName();

        internalMap.put(shortName, toSet);
    }

    @Override
    public Iterator<Property> iterator() {
        return toSet().iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableMap<String, Property> asObservableList() {
        return FXCollections.unmodifiableObservableMap(internalMap);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePropertyMap // instanceof handles nulls
                && this.internalMap.equals(((UniquePropertyMap) other).internalMap));
    }

    /**
     * Utilizes {@link #equals(Object)} because {@link java.util.HashMap} does not enforce
     * ordering anyway.
     */
    public boolean equalsOrderInsensitive(UniquePropertyMap other) {
        return equals(other);
    }

    /**
     * Returns the size of this map.
     */
    public int size() {
        return internalMap.size();
    }

    @Override
    public int hashCode() {
        return internalMap.hashCode();
    }
}
```
###### \java\seedu\address\model\tag\TagColorManager.java
``` java
/**
 * Manages the displayed color of all tags.
 *
 * TODO: Should we extract color out to be a {@code Color} class?
 */
public class TagColorManager {
    /**
     * Stores the colors for all existing tags here so that the same tag always has the same color. Notice this
     * {@code HashMap} has to be declared as a class variable. See the {@code equal} method in {@link Tag} class.
     */
    private static HashMap<Tag, String> internalMap = new HashMap<>();

    // Random number generator (non-secure purpose)
    private static final Random randomGenerator = new Random();

    /**
     * The upper (exclusive) bound should be equal to {@code Math.pow(16, 6)}. The lower (inclusive) bound should be
     * equal to {@code Math.pow(16, 5)}. Thus, the interval is {@code Math.pow(16, 6) - Math.pow(16, 5)}.
     */
    private static final int RGB_INTERVAL = 15728640;
    private static final int RGB_LOWER_BOUND = 1048576;

    public static String getColor(Tag tag) throws TagNotFoundException {
        if (!internalMap.containsKey(tag)) {
            throw new TagNotFoundException();
        }

        return internalMap.get(tag);
    }

    public static boolean contains(Tag tag) {
        return internalMap.containsKey(tag);
    }

    /**
     * Changes the color of a specific {@link Tag}.
     *
     * @param tag is the tag whose displayed color will be changed.
     * @param color is the RGB value of its new color.
     */
    public static void setColor(Tag tag, String color) {
        internalMap.put(tag, color);
    }

    /**
     * Randomly assign a color to the given {@code tag}. Notice the selection of random color is not cryptographically
     * secured.
     */
    public static void setColor(Tag tag) {
        int randomColorCode = randomGenerator.nextInt(RGB_INTERVAL) + RGB_LOWER_BOUND;
        setColor(tag, Integer.toHexString(randomColorCode));
    }
}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    public static Event[] getSampleEvents() {
        try {
            return new Event[]{
                new Event(new Name("Volleyball Practice"), new DateTime("25122017 08:30"),
                        new Address("OCBC ARENA Hall 3, #01-111")),
                new Event(new Name("CS2103T Lecture"), new DateTime("20102017 14:00"),
                        new Address("iCube Auditorium, NUS")),
            };
        } catch (IllegalValueException | PropertyNotFoundException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyPerson source) {
        name = source.getName().getValue();
        phone = source.getPhone().getValue();
        email = source.getEmail().getValue();
        address = source.getAddress().getValue();

        properties = new ArrayList<>();
        for (Property property: source.getProperties()) {
            properties.add(new XmlAdaptedProperty(property));
        }

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException, PropertyNotFoundException, DuplicatePropertyException {
        final List<Property> personProperties = new ArrayList<>();
        for (XmlAdaptedProperty property: properties) {
            personProperties.add(property.toModelType());
        }

        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        final Set<Property> properties = new HashSet<>(personProperties);
        final Set<Tag> tags = new HashSet<>(personTags);

        return new Person(properties, tags);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedProperty.java
``` java
/**
 * JAXB-friendly adapted version of the {@link Property}, stored within each person.
 */
public class XmlAdaptedProperty {
    @XmlAttribute
    private String shortName;
    @XmlValue
    private String value;

    /**
     * Constructs an XmlAdaptedProperty.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedProperty() {}

    /**
     * Converts a given Property into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedProperty(Property source) {
        this.shortName = source.getShortName();
        this.value = source.getValue();
    }

    /**
     * Converts this jaxb-friendly adapted property object into the model's Property object.
     *
     * @return a Property object used in model.
     * @throws IllegalValueException if there were any data constraints violated in the adapted property.
     * @throws PropertyNotFoundException the same as above.
     */
    public Property toModelType() throws IllegalValueException, PropertyNotFoundException {
        return new Property(shortName, value);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPropertyInfo.java
``` java
/**
 * JAXB-friendly adapted version of the {@link Property}, stores the general information of each property.
 */
public class XmlAdaptedPropertyInfo {
    @XmlElement
    private String shortName;
    @XmlElement
    private String fullName;
    @XmlElement
    private String message;
    @XmlElement
    private String regex;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPropertyInfo() {}

    public XmlAdaptedPropertyInfo(String shortName, String fullName, String message, String regex) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.message = message;
        this.regex = regex;
    }

    public void toModelType() throws DuplicatePropertyException {
        PropertyManager.addNewProperty(shortName, fullName, message, regex);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPropertyManager.java
``` java
/**
 * JAXB-friendly adapted version of the {@link PropertyManager}.
 */
public class XmlAdaptedPropertyManager {
    @XmlElement
    private List<XmlAdaptedPropertyInfo> property;

    public XmlAdaptedPropertyManager() {
        property = new ArrayList<>();
        for (String shortName: PropertyManager.getAllShortNames()) {
            XmlAdaptedPropertyInfo info = new XmlAdaptedPropertyInfo(shortName,
                    PropertyManager.getPropertyFullName(shortName),
                    PropertyManager.getPropertyConstraintMessage(shortName),
                    PropertyManager.getPropertyValidationRegex(shortName));
            property.add(info);
        }
    }

    /**
     * Initialize all properties by adding them to {@link PropertyManager}.
     */
    public void initializeProperties() {
        try {
            for (XmlAdaptedPropertyInfo info: property) {
                info.toModelType();
            }
        } catch (DuplicatePropertyException dpe) {
            // TODO: better error handling
            dpe.printStackTrace();
        }
    }
}
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    /**
     * Initialize the {@link PropertyManager} by clearing all existing properties and load information about new
     * properties from the storage file.
     */
    public void initializePropertyManager() {
        PropertyManager.clearAllProperties();
        properties.initializeProperties();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Take note of the following two methods, which overload each other. The one without parameter is used as the
     * callback when the user clicks on the sidebar button; the other one is used as the subscriber when the user
     * enters some command(s) that raise(s) the corresponding event(s).
     */
    @FXML
    private void handleSwitchToContacts() {
        dataDetailsPanelPlaceholder.getChildren().clear();
        dataListPanelPlaceholder.getChildren().clear();
        dataListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

    @Subscribe
    public void handleSwitchToContacts(SwitchToContactsListEvent event) {
        dataDetailsPanelPlaceholder.getChildren().clear();
        dataListPanelPlaceholder.getChildren().clear();
        dataListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

    @FXML
    private void handleSwitchToEvents() {
        dataDetailsPanelPlaceholder.getChildren().clear();
        dataListPanelPlaceholder.getChildren().clear();
        dataListPanelPlaceholder.getChildren().add(eventListPanel.getRoot());
    }

    @Subscribe
    public void handleSwitchToEvents(SwitchToEventsListEvent event) {
        dataDetailsPanelPlaceholder.getChildren().clear();
        dataListPanelPlaceholder.getChildren().clear();
        dataListPanelPlaceholder.getChildren().add(eventListPanel.getRoot());
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson person = event.getNewSelection().person;

        dataDetailsPanelPlaceholder.getChildren().clear();
        dataDetailsPanelPlaceholder.getChildren().add(new PersonDetailsPanel(person).getRoot());
    }
```
###### \java\seedu\address\ui\PropertyLabel.java
``` java
/**
 * A customize JavaFX {@link Label} class used to display the key-value pairs of all properties.
 */
public class PropertyLabel extends Label {
    public PropertyLabel(String text, String style) {
        super(text);
        this.getStyleClass().add(style);
    }
}
```
###### \resources\view\MainWindow.fxml
``` fxml
  <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4, 0.5" minWidth="600.0" prefWidth="1000.0" VBox.vgrow="ALWAYS">
      <VBox fx:id="sideButtonBar" alignment="CENTER" maxWidth="80.0" minWidth="80.0" prefWidth="80.0">
         <padding>
            <Insets bottom="10.0" top="10.0" />
         </padding>
         <children>
            <ImageView fx:id="switchToContactsButton" fitHeight="50.0" fitWidth="50.0" onMouseClicked="#handleSwitchToContacts" pickOnBounds="true" styleClass="sidebar-button">
               <VBox.margin>
                  <Insets bottom="50.0" top="50.0" />
               </VBox.margin>
               <image>
                  <Image url="@../images/contacts.png" />
               </image>
            </ImageView>
            <ImageView fx:id="switchToEventsButton" fitHeight="50.0" fitWidth="50.0" layoutX="20.0" layoutY="20.0" onMouseClicked="#handleSwitchToEvents" pickOnBounds="true" styleClass="sidebar-button">
               <image>
                  <Image url="@../images/events.png" />
               </image>
               <VBox.margin>
                  <Insets bottom="50.0" top="50.0" />
               </VBox.margin>
            </ImageView>
         </children>
      </VBox>
    <VBox fx:id="dataList" minWidth="340" prefWidth="340.0" SplitPane.resizableWithParent="false">
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
      <StackPane fx:id="dataListPanelPlaceholder" VBox.vgrow="ALWAYS" />
    </VBox>

    <StackPane fx:id="dataDetailsPanelPlaceholder">
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
    </StackPane>
  </SplitPane>
```
###### \resources\view\person\PersonDetailsPanel.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>


<VBox prefHeight="600.0" prefWidth="580.0" stylesheets="@../../css/Extensions.css" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <HBox prefHeight="200.0">
         <children>
            <ImageView fitHeight="200.0" fitWidth="200.0" pickOnBounds="true" preserveRatio="true">
               <image>
                  <Image url="@../../images/default_person_photo.png" />
               </image>
            </ImageView>
            <Label fx:id="name" styleClass="details-name-huge-label" text="\\\$name" wrapText="true">
               <font>
                  <Font size="45.0" />
               </font>
               <HBox.margin>
                  <Insets left="30.0" top="50.0" />
               </HBox.margin>
            </Label>
         </children>
      </HBox>
      <HBox>
         <children>
            <ListView fx:id="propertyListKeys" />
            <ListView fx:id="propertyListValues" prefWidth="500.0" />
         </children>
         <VBox.margin>
            <Insets top="30.0" />
         </VBox.margin>
      </HBox>
   </children>
</VBox>
```
