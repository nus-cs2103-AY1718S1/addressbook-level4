package seedu.address.commons.function;

import java.util.function.Consumer;

/**
 * A Consumer that allows lambda expressions to throw exception.
 * @param <T>
 */
@FunctionalInterface
public interface ThrowingConsumer<T> extends Consumer<T> {

    @Override
    default void accept(final T elem) {
        try {
            acceptThrows(elem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void acceptThrows(T elem) throws Exception;

}
