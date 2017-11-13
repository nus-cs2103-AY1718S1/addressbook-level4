package seedu.address.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import org.junit.Test;

import javafx.collections.MapChangeListener;
import seedu.address.model.event.ObservableTreeMap;

public class ObservableTreeMapTest {
    private static final int VALID_KEY = 1;
    private static final int VALID_KEY_TWO = 2;
    private static final int VALID_VALUE = 2;
    private static final int VALID_VALUE_TWO = 4;
    private TreeMap<Integer, Integer> internalMap = new TreeMap<Integer, Integer>();


    @Test
    public void addListener_addElement() {
        ObservableTreeMap<Integer, Integer> testMap1 = new ObservableTreeMap<>(internalMap);
        ObservableTreeMap<Integer, Integer> testMap2 = new ObservableTreeMap<>(internalMap);
        testMap1.addListener(new MapChangeListener<Integer, Integer>() {
            @Override
            public void onChanged(Change<? extends Integer, ? extends Integer> change) {
                boolean added = change.wasAdded();
                if (added != change.wasRemoved()) {
                    if (added) {
                        testMap2.put(change.getKey(), change.getValueAdded());
                    }
                }
            }
        });

        testMap1.put(VALID_KEY, VALID_VALUE);
        assertTrue(testMap2.size() == testMap1.size());
        assertTrue(testMap2.containsKey(VALID_KEY));
        assertTrue(testMap2.containsValue(VALID_VALUE));
        assertTrue(testMap2.get(VALID_KEY) == VALID_VALUE);
        assertTrue(testMap2.get(VALID_KEY) == VALID_VALUE);

        assertTrue(testMap2.keySet().size() == testMap1.keySet().size());
        assertTrue(testMap2.keySet().contains(VALID_KEY));
        assertTrue(testMap2.values().size() == testMap1.values().size());
        assertTrue(testMap2.keySet().toArray().length == testMap2.keySet().toArray().length);
        assertTrue(testMap2.values().contains(VALID_VALUE));
        assertTrue(testMap2.entrySet().size() == testMap1.entrySet().size());
        assertTrue(testMap2.values().toArray().length == testMap2.values().toArray().length);
        assertTrue(testMap2.entrySet().equals(testMap1.entrySet()));

        assertTrue(testMap1.equals(testMap2));
    }

    @Test
    public void addListener_removeElement() {
        internalMap.put(VALID_KEY, VALID_VALUE);
        ObservableTreeMap<Integer, Integer> testMap1 = new ObservableTreeMap<>(internalMap);
        ObservableTreeMap<Integer, Integer> testMap2 = new ObservableTreeMap<>(internalMap);
        testMap1.addListener(new MapChangeListener<Integer, Integer>() {
            @Override
            public void onChanged(Change<? extends Integer, ? extends Integer> change) {
                boolean removed = change.wasRemoved();
                if (removed != change.wasAdded()) {
                    if (removed) {
                        testMap2.remove(change.getValueRemoved());
                    }
                }
            }
        });

        testMap1.remove(VALID_KEY, VALID_VALUE);
        assertTrue(testMap2.isEmpty());

        assertTrue(testMap2.keySet().size() == testMap1.keySet().size());
        assertTrue(!testMap2.keySet().contains(VALID_KEY));
        assertTrue(testMap2.values().size() == testMap1.values().size());
        assertTrue(!testMap2.values().contains(VALID_VALUE));
        assertTrue(testMap2.entrySet().size() == testMap1.entrySet().size());
        assertTrue(testMap2.entrySet().equals(testMap1.entrySet()));

        assertTrue(testMap1.equals(testMap2));
    }

    @Test
    public void addListener_addMultipleElement() {
        TreeMap<Integer, Integer> toAdd = new TreeMap<>();
        toAdd.put(VALID_KEY, VALID_VALUE);
        toAdd.put(VALID_KEY_TWO, VALID_VALUE_TWO);
        ObservableTreeMap<Integer, Integer> testMap1 = new ObservableTreeMap<>(internalMap);
        ObservableTreeMap<Integer, Integer> testMap2 = new ObservableTreeMap<>(internalMap);
        testMap1.addListener(new MapChangeListener<Integer, Integer>() {
            @Override
            public void onChanged(Change<? extends Integer, ? extends Integer> change) {
                boolean added = change.wasAdded();
                if (added != change.wasRemoved()) {
                    if (added) {
                        testMap2.put(change.getKey(), change.getValueAdded());
                    }
                }
            }
        });

        testMap1.putAll(toAdd);

        assertTrue(testMap2.size() == testMap1.size());
        assertTrue(testMap1.containsKey(VALID_KEY));
        assertTrue(testMap1.containsKey(VALID_KEY_TWO));
        assertTrue(testMap1.containsValue(VALID_VALUE));
        assertTrue(testMap1.containsValue(VALID_VALUE_TWO));
        assertTrue(testMap2.get(VALID_KEY) == testMap1.get(VALID_KEY));
        assertTrue(testMap2.get(VALID_KEY_TWO) == testMap1.get(VALID_KEY_TWO));

        assertTrue(testMap2.keySet().size() == testMap1.keySet().size());
        assertTrue(testMap2.keySet().containsAll(new ArrayList<>(Arrays.asList(VALID_KEY, VALID_KEY_TWO))));
        assertTrue(testMap2.values().size() == testMap1.values().size());
        assertTrue(testMap2.keySet().toArray().length == testMap2.keySet().toArray().length);
        assertTrue(testMap2.values().containsAll(new ArrayList<>(Arrays.asList(VALID_VALUE, VALID_VALUE_TWO))));
        assertTrue(testMap2.entrySet().size() == testMap1.entrySet().size());
        assertTrue(testMap2.values().toArray().length == testMap2.values().toArray().length);
        assertTrue(testMap2.entrySet().equals(testMap1.entrySet()));

        assertTrue(testMap1.equals(testMap2));
    }

    @Test
    public void addListener_removeMultipleElement() {
        TreeMap<Integer, Integer> toAdd = new TreeMap<>();
        toAdd.put(VALID_KEY, VALID_VALUE);
        toAdd.put(VALID_KEY_TWO, VALID_VALUE_TWO);
        internalMap.putAll(toAdd);
        ObservableTreeMap<Integer, Integer> testMap1 = new ObservableTreeMap<>(internalMap);
        ObservableTreeMap<Integer, Integer> testMap2 = new ObservableTreeMap<>(internalMap);
        testMap1.addListener(new MapChangeListener<Integer, Integer>() {
            @Override
            public void onChanged(Change<? extends Integer, ? extends Integer> change) {
                boolean removed = change.wasRemoved();
                if (removed != change.wasAdded()) {
                    if (removed) {
                        testMap2.remove(change.getValueRemoved());
                    }
                }
            }
        });

        testMap1.remove(VALID_KEY);
        testMap1.remove(VALID_KEY_TWO);

        assertTrue(!testMap1.containsKey(VALID_KEY));
        assertTrue(!testMap1.containsKey(VALID_KEY_TWO));
        assertTrue(!testMap1.containsValue(VALID_VALUE));
        assertTrue(!testMap1.containsValue(VALID_VALUE_TWO));
        assertTrue(testMap2.isEmpty());

        assertTrue(testMap2.keySet().size() == testMap1.keySet().size());
        assertTrue(!testMap2.keySet().contains(VALID_KEY));
        assertTrue(testMap2.values().size() == testMap1.values().size());
        assertTrue(testMap2.keySet().toArray().length == testMap2.keySet().toArray().length);
        assertTrue(!testMap2.values().contains(VALID_VALUE));
        assertTrue(testMap2.entrySet().size() == testMap1.entrySet().size());
        assertTrue(testMap2.values().toArray().length == testMap2.values().toArray().length);
        assertTrue(testMap2.entrySet().equals(testMap1.entrySet()));

        assertTrue(testMap1.equals(testMap2));
    }
}
