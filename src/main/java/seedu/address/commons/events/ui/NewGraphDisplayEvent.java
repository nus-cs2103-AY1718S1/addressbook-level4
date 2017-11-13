package seedu.address.commons.events.ui;

import org.graphstream.graph.implementations.SingleGraph;

import seedu.address.commons.events.BaseEvent;

//@@author joanneong
/**
 * Indicates that a new graph display is available.
 */
public class NewGraphDisplayEvent extends BaseEvent {

    public final String message;
    private final SingleGraph graph;

    public NewGraphDisplayEvent(SingleGraph graph, String message) {
        this.message = message;
        this.graph = graph;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public SingleGraph getGraph() {
        return this.graph;
    }

}
