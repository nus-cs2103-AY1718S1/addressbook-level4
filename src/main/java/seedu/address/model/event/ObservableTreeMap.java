//@@author a0107442n
package seedu.address.model.event;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

import com.sun.javafx.collections.MapListenerHelper;

import javafx.beans.InvalidationListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

/**
 * A TreeMap with Obervable properties, designed for better implementation of
 * EventList as a TreeMap.
 */

public class ObservableTreeMap<K, V> extends TreeMap<K, V> implements
        ObservableMap<K, V> {

    private MapListenerHelper<K, V> mapListenerHelper;

    protected void callObservers(MapChangeListener.Change<K,V> change) {
        MapListenerHelper.fireValueChangedEvent(mapListenerHelper, change);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        mapListenerHelper = MapListenerHelper.addListener(mapListenerHelper, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        mapListenerHelper = MapListenerHelper.removeListener(mapListenerHelper, listener);
    }

    @Override
    public void addListener(MapChangeListener<? super K, ? super V> observer) {
        mapListenerHelper = MapListenerHelper.addListener(mapListenerHelper, observer);
    }

    @Override
    public void removeListener(MapChangeListener<? super K, ? super V> observer) {
        mapListenerHelper = MapListenerHelper.removeListener(mapListenerHelper, observer);
    }

    public static <K,V> ObservableTreeMap<K, V> map (ObservableTreeMap<K, V>
            sourceMap, Function<Map.Entry<? extends K, ? extends V>, Map.Entry<? extends K, ? extends V>> mapper) {
        return new TransformationTreeMap<K,V>(sourceMap, mapper);
    }

}
