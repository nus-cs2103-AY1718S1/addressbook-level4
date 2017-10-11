package seedu.address.model.person;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import seedu.address.model.person.exceptions.DuplicatePropertyException;

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
    // A collection of the short names of all available properties.
    private static final HashSet<String> propertyShortNames = new HashSet<>();

    // Mapping from property short name to its full name.
    private static final HashMap<String, String> propertyFullNames = new HashMap<>();

    // Mapping from property name to the corresponding constraint message and validation regular expression.
    private static final HashMap<String, String> propertyConstraintMessages = new HashMap<>();
    private static final HashMap<String, String> propertyValidationRegex = new HashMap<>();

    // Records whether has been initialized before.
    private static boolean initialized = false;

    /**
     * Makes use of static initialization block to guarantee all pre-loaded properties are included.
     */
    static {
        if (!initialized) {
            try {
                // Adds name as a pre-loaded property.
                addNewProperty("n", "name",
                        "Person names should only contain alphanumeric characters and spaces, "
                                + "and it should not be blank",
                        "[\\p{Alnum}][\\p{Alnum} ]*");

                // Adds email as a pre-loaded property.
                addNewProperty("e", "email",
                        "Person emails should be 2 alphanumeric/period strings separated by '@'",
                        "[\\w\\.]+@[\\w\\.]+");

                // Adds phone number as a pre-loaded property.
                addNewProperty("p", "phone",
                        "Phone numbers can only contain numbers, and should be at least 3 digits long",
                        "\\d{3,}");

                // Adds address as a pre-loaded property.
                addNewProperty("a", "address",
                        "Person addresses can take any values, and it should not be blank",
                        "[^\\s].*");
            } catch (DuplicatePropertyException dpe) {
                throw new RuntimeException("PropertyManager cannot be initialized. Stopping the application.");
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
        if (propertyShortNames.contains(shortName)) {
            throw new DuplicatePropertyException();
        }
        // Checks whether the regular expression is valid.
        Pattern.compile(regex);

        propertyShortNames.add(shortName);
        propertyFullNames.put(shortName, fullName);
        propertyConstraintMessages.put(shortName, message);
        propertyValidationRegex.put(shortName, regex);
    }

    public static boolean containsShortName(String shortName) {
        return propertyShortNames.contains(shortName);
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
}
