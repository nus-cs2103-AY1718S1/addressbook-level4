package seedu.address.model.person;

import java.util.HashMap;
import java.util.HashSet;

import seedu.address.model.person.exceptions.DuplicatePropertyException;

/**
 * Manages the different properties (both pre-loaded ones and customize ones) of all persons stored in the
 * application.
 *
 * Pre-loaded properties include {@code Address}, {@code Email}, {@code Name}, {@code Phone}. These pre-loaded
 * properties are subjected to changes in later versions.
 *
 * Customize properties include all properties except the pre-loaded ones, which are added by the following command
 * <pre>{@code config --add-property <property_name> ...}</pre>.
 */
public class PropertyManager {
    // A collection of the short names of all available properties.
    private static final HashSet<String> shortNames = new HashSet<>();

    // Mapping from property short name to its full name.
    private static final HashMap<String, String> propertyFullNames = new HashMap<>();

    // Mapping from property name to the corresponding constraint message and validation regular expression.
    private static final HashMap<String, String> propertyConstraintMessages = new HashMap<>();
    private static final HashMap<String, String> propertyValidationRegex = new HashMap<>();

    public PropertyManager() {

    }

    public static String getConstraintMessage(String propertyShortName) {

    }

    /**
     * Adds a new available property with all the required information for setting up a property.
     *
     * @param shortName is the short-form name of this property, usually consists of one or two letters, like a
     *                  (stands for address). It is usually the initial of the property name.
     * @param fullName is the full-form name of this property, should be a legal English word.
     * @param message is the constraint message of this property. It will be displayed when the value of this
     *                property does not pass the validation check.
     * @param regex is the regular expression used to perform input validation.
     *
     * @throws DuplicatePropertyException is thrown when a property with the same{@code shortName} already exists.
     */
    public static void addNewProperty(String shortName, String fullName, String message, String regex)
            throws DuplicatePropertyException {
        if (shortNames.contains(shortName)) {
            throw new DuplicatePropertyException();
        }
    }
}
