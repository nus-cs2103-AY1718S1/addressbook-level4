package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Stores mapping of prefixes to their respective arguments. Each key may be associated with multiple argument values.
 * Values for a given key are stored in a list, and the insertion ordering is maintained. Keys are unique, but the list
 * of argument values allows it to contain duplicate argument values, i.e. the same argument value can be inserted
 * multiple times for the same prefix.
 */
public class ArgumentMultimap {

    /** Prefixes mapped to their respective arguments **/
    private final Map<Prefix, List<String>> internalMap = new HashMap<>();

    /**
     * Associates the specified argument value with {@code prefix} key in this map.
     * If the map previously contained a mapping for the key, the new value is appended to the list of existing values.
     *
     * @param prefix   Prefix key with which the specified argument value is to be associated
     * @param argValue Argument value to be associated with the specified prefix key
     */
    void put(Prefix prefix, String argValue) {
        List<String> argValues = getAllValues(prefix);
        argValues.add(argValue);
        internalMap.put(prefix, argValues);
    }

    /**
     * Returns the last value of {@code prefix}.
     */
    public Optional<String> getValue(Prefix prefix) {
        List<String> values = getAllValues(prefix);
        return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Returns all values of {@code prefix}.
     * If the prefix does not exist or has no values, this will return an empty list.
     * Modifying the returned list will not affect the underlying data structure of the ArgumentMultimap.
     */
    public List<String> getAllValues(Prefix prefix) {
        if (!internalMap.containsKey(prefix)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(internalMap.get(prefix));
    }

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

    /**
     * Returns the preamble (text before the first valid prefix). Trims any leading/trailing spaces.
     */
    public String getPreamble() {
        return getValue(new Prefix("")).orElse("");
    }
}
