package seedu.address.model.property;

import static seedu.address.commons.core.Messages.PROPERTY_EXISTS;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.property.exceptions.DuplicatePropertyException;

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
