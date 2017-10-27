package seedu.address.model.event;

import java.lang.ref.WeakReference;

import javafx.beans.NamedArg;
import javafx.collections.MapChangeListener;

public class TreeMapChangeListener<K, V> implements MapChangeListener<K,V> {

    private final WeakReference<MapChangeListener<K,V>> ref;

    public TreeMapChangeListener(@NamedArg("listener") MapChangeListener<K,V> listener) {
        if (listener == null) {
            throw new NullPointerException("Listener must be specified.");
        }
        this.ref = new WeakReference<MapChangeListener<K, V>>(listener);
    }

    @Override
    public void onChanged(Change<? extends K, ? extends V> change) {
        final MapChangeListener<K, V> listener = ref.get();
        if (listener != null) {
            listener.onChanged(change);
        } else {
            change.getMap().removeListener(this);
        }
    }
}
