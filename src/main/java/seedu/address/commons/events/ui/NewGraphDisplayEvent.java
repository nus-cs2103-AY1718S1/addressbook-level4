package seedu.address.commons.events.ui;

import org.graphstream.graph.implementations.SingleGraph;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new graph display is available.
 */
public class NewGraphDisplayEvent extends BaseEvent {

    private final SingleGraph graph;
    public final String message;

    public NewGraphDisplayEvent(SingleGraph graph, String message) {
        this.graph = graph;
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public SingleGraph getGraph() {
        return this.graph;
    }

}
