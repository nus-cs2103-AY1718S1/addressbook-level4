//@@author a0107442n
package seedu.address.model.event;

import java.util.TreeMap;
import java.util.logging.Logger;

import com.sun.javafx.collections.MapListenerHelper;

import javafx.beans.InvalidationListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.LogsCenter;

/**
 * A TreeMap with Obervable properties, designed for better implementation of
 * EventList as a TreeMap.
 */

public class ObservableTreeMap<K, V> extends TreeMap<K, V> implements
        ObservableMap<K, V> {

    private static final Logger logger = LogsCenter.getLogger(ObservableTreeMap.class);

    private MapListenerHelper<K, V> mapListenerHelper;

    /**
     * A subclass of MapChangeListener.Change for ObervableTreeMap
     */
    public class Change extends MapChangeListener.Change<K, V> {
        private final K key;
        private final V old;
        private final V added;
        private final boolean wasAdded;
        private final boolean wasRemoved;

        public Change(K key, V old, V added, boolean wasAdded, boolean wasRemoved) {
            super(ObservableTreeMap.this);
            assert(wasAdded || wasRemoved);
            this.key = key;
            this.old = old;
            this.added = added;
            this.wasAdded = wasAdded;
            this.wasRemoved = wasRemoved;
        }

        @Override
        public boolean wasAdded() {
            return wasAdded;
        }

        @Override
        public boolean wasRemoved() {
            return wasRemoved;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValueAdded() {
            return added;
        }

        @Override
        public V getValueRemoved() {
            return old;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (wasAdded) {
                if (wasRemoved) {
                    builder.append("replaced ").append(old).append("by ").append(added);
                } else {
                    builder.append("added ").append(added);
                }
            } else {
                builder.append("removed ").append(old);
            }
            builder.append(" at key ").append(key);
            return builder.toString();
        }
    }

    protected void callObservers(MapChangeListener.Change<K, V> change) {
        MapListenerHelper.fireValueChangedEvent(mapListenerHelper, change);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        mapListenerHelper = MapListenerHelper.addListener(mapListenerHelper, listener);
    }


    @Override
    public void addListener(MapChangeListener<? super K, ? super V> observer) {
        mapListenerHelper = MapListenerHelper.addListener(mapListenerHelper, observer);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        mapListenerHelper = MapListenerHelper.removeListener(mapListenerHelper, listener);
    }

    @Override
    public void removeListener(MapChangeListener<? super K, ? super V> observer) {
        mapListenerHelper = MapListenerHelper.removeListener(mapListenerHelper, observer);
    }

    /*public static <K, V, U> ObservableList<U> map(ObservableTreeMap<K, V>
            sourceMap, Function<V, U> mapper) {
        logger.info("OTM ========= size = " + sourceMap.values().size());
        return new TransformationTreeMap<K, V, U>(sourceMap, mapper);
    }*/

}
