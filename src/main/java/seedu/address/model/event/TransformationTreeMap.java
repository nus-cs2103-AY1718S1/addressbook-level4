package seedu.address.model.event;

import java.util.Map;
import java.util.function.Function;

import javafx.collections.MapChangeListener;

public class TransformationTreeMap<K,V> extends ObservableTreeMap<K, V> {

    private ObservableTreeMap<? extends K, ? extends V> source;
    private MapChangeListener<K,V> sourceListener;
    private Function<Map.Entry<? extends K,? extends V>, Map.Entry<? extends K, ? extends V>> mapper

    public TransformationTreeMap(ObservableTreeMap<? extends K, ? extends V> source, Function<Map.Entry<? extends K,?
            extends V>, Map.Entry<? extends K, ? extends V>> mapper) {
        if (source == null) {
            throw new NullPointerException();
        }
        this.source = source;
        this.mapper = mapper;
        source.addListener(new TreeMapChangeListener<K, V>(getListener()));

    }

    private MapChangeListener<K,V> getListener() {
        if (sourceListener == null) {
            sourceListener = c -> {
                TransformationTreeMap.this.sourceChanged(c);
            };
        }
        return sourceListener;
    }

    protected void sourceChanged(MapChangeListener.Change<? extends K, ? extends V> change) {
        callObservers(new MapChangeListener.Change<K,V>(this) {
                @Override
                public boolean wasAdded() { return change.wasAdded(); }

                @Override
                public boolean wasRemoved() { return change.wasRemoved(); }

                @Override
                public K getKey() { return change.getKey(); }

                @Override
                public V getValueAdded() { return change.getValueAdded(); }

                @Override
                public V getValueRemoved() { return change.getValueRemoved(); }
        });
    }

    @Override
    public V get(K key) {
        return mapper.apply(getSource().get(key));
    }

    public final ObservableTreeMap<? extends K, ? extends V> getSource() {
        return this.source;
    }

}
