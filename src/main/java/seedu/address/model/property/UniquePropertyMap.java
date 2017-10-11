package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.address.model.property.exceptions.DuplicatePropertyException;

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
    private final ObservableMap<String, Property> internalMap = FXCollections.observableHashMap();

    /**
     * Constructs empty PropertyList.
     */
    public UniquePropertyMap() {}

    /**
     * Creates a UniquePropertyMap using given properties.
     * Enforces no nulls.
     */
    public UniquePropertyMap(Map<String, Property> properties) {
        requireAllNonNull(properties);
        internalMap.putAll(properties);
    }

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
    public void setProperties(Map<String, Property> properties) {
        requireAllNonNull(properties);
        internalMap.putAll(properties);
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

    /**
     * Adds a property to the list.
     *
     * @throws DuplicatePropertyException if the given property already exists in this list (or there exists a
     * property that is equal to the one in the argument). Since we are using {@link java.util.HashMap}, another
     * method must be used when we want to update the value of an existing property.
     */
    public void add(Property toAdd) throws DuplicatePropertyException {
        String shortName = toAdd.getShortName();

        requireNonNull(toAdd);
        if (containsProperty(shortName)) {
            throw new DuplicatePropertyException();
        }
        internalMap.put(shortName, toAdd);
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

    @Override
    public int hashCode() {
        return internalMap.hashCode();
    }
}
