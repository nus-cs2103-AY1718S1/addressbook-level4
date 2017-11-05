package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.PROPERTY_EXISTS;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;

//@@author yunpengn
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
     * but change-insulated against the internal map.
     */
    public Set<Property> toSet() {
        return new HashSet<>(internalMap.values());
    }

    /**
     * Returns all properties (collection of values in all entries) in this map as a sorted list based on the full
     * name of each property. This list is mutable but change-insulated against the internal map.
     */
    public List<Property> toSortedList() {
        List<Property> list = new ArrayList<>(internalMap.values());
        list.sort(Comparator.comparing(Property::getFullName));
        return list;
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
