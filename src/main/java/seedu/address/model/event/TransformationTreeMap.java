package seedu.address.model.event;

import java.util.function.Function;

import javafx.collections.MapChangeListener;

/**
 * Subclass of ObservableTreeMap created by mapping an ObservableTreeMap with a given mapper, which can be used to
 * bind one ObservableTreeMap to another through the method map.
 */
public class TransformationTreeMap<K, V, U> extends ObservableTreeMap<K, U> {

    private ObservableTreeMap<? extends K, ? extends V> source;
    private MapChangeListener<K, V> sourceListener;
    private final Function<? super V, ? extends U> mapper;

    public TransformationTreeMap(ObservableTreeMap<? extends K, ? extends V> source, Function<? super V, ? extends U>
            mapper) {
        if (source == null) {
            throw new NullPointerException();
        }
        this.source = source;
        this.mapper = mapper;
        source.addListener(new TreeMapChangeListener<>(getListener()));

    }

    private MapChangeListener<K, V> getListener() {
        if (sourceListener == null) {
            sourceListener = c -> {
                TransformationTreeMap.this.sourceChanged(c);
            };
        }
        return sourceListener;
    }

    /**
     * Called when a change from the source is triggered.
     * @param change the change
     */
    protected void sourceChanged(MapChangeListener.Change<? extends K, ? extends V> change) {
        callObservers(new MapChangeListener.Change<K, U>(this) {
                @Override
                public boolean wasAdded() {
                    return change.wasAdded();
                }

                @Override
                public boolean wasRemoved() {
                    return change.wasRemoved();
                }

                @Override
                public K getKey() {
                    return change.getKey();
                }

                @Override
                public U getValueAdded() {
                    return mapper.apply(change.getValueAdded());
                }

                @Override
                public U getValueRemoved() {
                    return mapper.apply(change.getValueRemoved());
                }
        });
    }

    @Override
    public U get(Object key) {
        return mapper.apply(getSource().get(key));
    }

    public final ObservableTreeMap<? extends K, ? extends V> getSource() {
        return this.source;
    }

}
