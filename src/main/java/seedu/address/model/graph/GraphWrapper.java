package seedu.address.model.graph;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.WidestPath;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.implementations.SingleNode;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.RelationshipDirection;

//@@author wenmogu
/**
 * This class is a wrapper class of SingleGraph class in GraphStream
 * It is used when creating a new SingleGraph when changes happen in the lastShownList
 */
public class GraphWrapper {

    public static final String MESSAGE_PERSON_DOES_NOT_EXIST = "The person does not exist in this address book.";
    private static final GraphWrapper instance = new GraphWrapper();
    private static final String graphId = "ImARandomGraphID";

    private SingleGraph graph;
    private Viewer viewer;
    private View view;
    private Model model;
    private ObservableList<ReadOnlyPerson> filteredPersons;
    private boolean rebuildNext;

    private final String nodeAttributeNodeLabel = "ui.label";
    private final String nodeAttributeCe = "ce";
    private final String nodeAttributePerson = "Person";

    private GraphWrapper() {
        this.graph = new SingleGraph(graphId);
        this.viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);
        this.view = viewer.addDefaultView(false);
        rebuildNext = true;
    }

    public static GraphWrapper getInstance() {
        return instance;
    }

    private void setData(Model model) {
        this.model = model;
        this.filteredPersons = model.getFilteredPersonList();
    }

    /**
     * Add all the persons in the last displayed list into graph
     * the ID of the node formed for a person is the person's index in the last displayed list
     * @return graph
     */
    private SingleGraph initiateGraphNodes() {
        try {
            for (ReadOnlyPerson person : filteredPersons) {
                String personIndexInFilteredPersons = getNodeIdFromPerson(person);
                SingleNode node = graph.addNode(personIndexInFilteredPersons);
                String personLabel = (Integer.parseInt(personIndexInFilteredPersons) + 1) + ". "
                        + person.getName().toString();
                node.addAttribute(nodeAttributeNodeLabel, personLabel);
            }
        } catch (IllegalValueException ive) {
            assert false : "it should not happen.";
        }

        return graph;
    }

    private String getNodeIdFromPerson(ReadOnlyPerson person) throws IllegalValueException {
        requireNonNull(person);
        int indexOfThePerson = filteredPersons.indexOf(person);
        if (indexOfThePerson == -1) {
            throw new IllegalValueException(MESSAGE_PERSON_DOES_NOT_EXIST);
        } else {
            return Integer.toString(indexOfThePerson);
        }
    }

    /**
     * standardize the format of edge ID
     */
    private String computeEdgeId(ReadOnlyPerson person1, ReadOnlyPerson person2) {
        return  Integer.toString(filteredPersons.indexOf(person1)) + "_"
                + Integer.toString(filteredPersons.indexOf(person2));
    }

    /**
     * removes the previous edge (if exists) with a different RelationshipDirection from
     * the edge to be added.
     * @param fromPerson
     * @param toPerson
     * @param intendedDirectionOfRedundantEdge
     * @return String
     */
    private String checkForRedundantEdgeAndRemove(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson,
                                                RelationshipDirection intendedDirectionOfRedundantEdge) {
        requireAllNonNull(fromPerson, toPerson, intendedDirectionOfRedundantEdge);
        String redundantEdgeId1 = computeEdgeId(fromPerson, toPerson);
        String redundantEdgeId2 = computeEdgeId(toPerson, fromPerson);
        Edge redundantEdge1 = graph.getEdge(redundantEdgeId1);
        Edge redundantEdge2 = graph.getEdge(redundantEdgeId2);

        if (intendedDirectionOfRedundantEdge.isDirected()) {
            if (redundantEdge1 != null) {
                graph.removeEdge(redundantEdge1);
            }
            if (redundantEdge2 != null && !redundantEdge2.isDirected()) {
                graph.removeEdge(redundantEdge2);
            }
        } else {
            if (redundantEdge1 != null) {
                graph.removeEdge(redundantEdge1);
            }
            if (redundantEdge2 != null) {
                graph.removeEdge(redundantEdge2);
            }
        }
        return redundantEdgeId1;
    }

    /**
     * Add a directed edge from one person to another
     * remove the previous undirected edge between the two persons (if exists) and add a directed edge
     * @return the directed edge from fromPerson to toPerson
     */
    private Edge addDirectedEdge(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson) {
        String designatedEdgeId = checkForRedundantEdgeAndRemove(fromPerson,
                toPerson, RelationshipDirection.UNDIRECTED);

        try {
            graph.addEdge(designatedEdgeId, getNodeIdFromPerson(fromPerson),
                    getNodeIdFromPerson(toPerson), true);
        } catch (IllegalValueException ive) {
            assert false : "it should not happen.";
        }

        return graph.getEdge(designatedEdgeId);
    }

    /**
     * Add an undirected edge between two persons
     * remove the previous directed edge between the two persons (if exists) and add an undirected edge
     * @return the undirected edge between firstPerson and secondPerson
     */
    private Edge addUndirectedEdge(ReadOnlyPerson firstPerson, ReadOnlyPerson secondPerson) {
        String designatedEdgeId1 = checkForRedundantEdgeAndRemove(firstPerson,
                secondPerson, RelationshipDirection.DIRECTED);
        String designatedEdgeId2 = checkForRedundantEdgeAndRemove(secondPerson,
                firstPerson, RelationshipDirection.DIRECTED);

        try {
            graph.addEdge(designatedEdgeId1, getNodeIdFromPerson(firstPerson),
                    getNodeIdFromPerson(secondPerson), false);
        } catch (IllegalValueException ive) {
            assert false : "it should not happen.";
        }

        return graph.getEdge(designatedEdgeId1);
    }

    /**
     * add an edge between two persons with direction specified
     */
    public Edge addEdge(ReadOnlyPerson firstPerson, ReadOnlyPerson secondPerson, RelationshipDirection direction) {
        requireAllNonNull(firstPerson, secondPerson, direction);
        if (direction.isDirected()) {
            return addDirectedEdge(firstPerson, secondPerson);
        } else {
            return addUndirectedEdge(firstPerson, secondPerson);
        }
    }

    private void clear() {
        graph.clear();
        this.model = null;
        this.filteredPersons = null;
    }

    /**
     * Read all the edges from model and store into graph
     * @return
     */
    private SingleGraph initiateGraphEdges() {
        for (ReadOnlyPerson person: filteredPersons) {
            Set<Relationship> relationshipSet = person.getRelationships();
            for (Relationship relationship: relationshipSet) {
                Edge edge = addEdge(relationship.getFromPerson(), relationship.getToPerson(),
                        relationship.getDirection());
                String edgeLabel = relationship.getName().toString() + " "
                        + relationship.getConfidenceEstimate().toString();
                edge.addAttribute(nodeAttributeNodeLabel, edgeLabel);
                edge.addAttribute(nodeAttributeCe, relationship.getConfidenceEstimate().value);
            }
        }

        return graph;
    }

    /**
     * Produce a graph based on model given if next rebuild was not cancelled.
     */
    public SingleGraph buildGraph(Model model) {
        requireNonNull(model);
        if (rebuildNext) {
            this.clear();
            this.setData(model);
            this.initiateGraphNodes();
            this.initiateGraphEdges();
        } else {
            rebuildNext = true;
        }

        return graph;
    }

    /**
     * Returns the view attached to the viewer for the graph.
     */
    public View getView() {
        return this.view;
    }
    //@@author

    //@@author Xenonym
    /**
     * Highlights the shortest path between two people with the highest confidence.
     * Paths with higher minimum confidence estimates are preferred.
     * Cancels next graph rebuild for styling to remain.
     * @return the number of nodes in the path between the two given people.
     */
    public int highlightShortestPath(ReadOnlyPerson from, ReadOnlyPerson to) {
        WidestPath widestPath = new WidestPath(Dijkstra.Element.EDGE, null, nodeAttributeCe);
        Node fromNode;
        Node toNode;

        resetGraph(); // reset graph to clear previously highlighted path first, if any
        try {
            fromNode = graph.getNode(getNodeIdFromPerson(from));
            toNode = graph.getNode(getNodeIdFromPerson(to));
        } catch (IllegalValueException ive) {
            throw new AssertionError("impossible to have nonexistent persons in graph");
        }

        widestPath.init(graph);
        widestPath.setSource(fromNode);
        widestPath.compute();

        for (Node n : widestPath.getPathNodes(toNode)) {
            n.addAttribute("ui.style", "fill-color: blue;");
        }

        for (Edge e : widestPath.getPathEdges(toNode)) {
            e.addAttribute("ui.style", "fill-color: red;");
        }

        rebuildNext = false;
        return widestPath.getPath(toNode).getNodeCount();
    }

    private void resetGraph() {
        graph.clear();
        initiateGraphNodes();
        initiateGraphEdges();
    }
}
