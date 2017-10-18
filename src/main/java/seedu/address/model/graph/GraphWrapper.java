package seedu.address.model.graph;

import javafx.collections.ObservableList;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.implementations.SingleNode;
import org.graphstream.util.set.FixedArrayList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.HashMap;
import java.util.Observable;

import static java.util.Objects.requireNonNull;

/**
 * This class is a wrapper class of SingleGraph class in GraphStream
 * It is used when creating a new SingleGraph when changes happen in the lastShownList
 */
public class GraphWrapper {

    private SingleGraph graph;
    private final Model model;
    private final ObservableList<ReadOnlyPerson> filteredPersons;
    private final AdjacencyList adjList;
    private final HashMap<Integer, String> personsFixedIndexList;
    private final String graphID = "ImARandomGraphID";

    private final String nodeAttributePersonName = "PersonName";
    private final String nodeAttributePerson = "Person";

    private final String MESSAGE_PERSON_DOESNOT_EXIST = "The person does not exist in this address book.";

    public GraphWrapper(Model model) {
        this.graph = new SingleGraph(graphID);
        this.model = model;
        this.filteredPersons = model.getFilteredPersonList();
        this.adjList = model.getAdjList();
        this.personsFixedIndexList = model.getPersonsFirxedIndexList();
    }

    /**
     * Add all the persons in the last displayed list into graph
     * the ID of the node formed for a person is the person's index in the last displayed list
     * @return graph
     */
    private SingleGraph initiateGraphNodes() {
        try {
            for (ReadOnlyPerson person : filteredPersons) {
                SingleNode node = graph.addNode(getNodeIDFromPerson(person));
                node.addAttribute(nodeAttributePersonName, person.getName());
                node.addAttribute(nodeAttributePerson, person);
            }
        } catch (IllegalValueException ive) {
            assert false : "it should not happen.";
        }

        return graph;
    }

    private String getNodeIDFromPerson(ReadOnlyPerson person) throws IllegalValueException {
        requireNonNull(person);
        int indexOfThePerson = filteredPersons.indexOf(person);
        if (indexOfThePerson == -1) {
            throw new IllegalValueException(MESSAGE_PERSON_DOESNOT_EXIST);
        } else {
            return Integer.toString(indexOfThePerson);
        }
    }

    /**
     * fix the format of edge ID
     */
    private String computeEdgeID(ReadOnlyPerson person1, ReadOnlyPerson person2) {
        return  Integer.toString(filteredPersons.indexOf(person1)) + "--"
                + Integer.toString(filteredPersons.indexOf(person2));
    }


    /**
     * Add a directed edge from one person to another
     * remove the previous undirected edge between the two persons (if exists) and add a directed edge
     * @return the directed edge from fromPerson to toPerson
     */
    private Edge addDirectedEdge(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson) {
        String designatedEdgeID = computeEdgeID(fromPerson, toPerson);
        Edge previousEdge = graph.getEdge(designatedEdgeID);
        if (previousEdge != null && !previousEdge.isDirected()) {
            graph.removeEdge(previousEdge);
        }
        try {
            graph.addEdge(designatedEdgeID, getNodeIDFromPerson(fromPerson),
                    getNodeIDFromPerson(toPerson), true);
        } catch (IllegalValueException ive) {
            assert false : "it should not happen.";
        }

        return graph.getEdge(designatedEdgeID);
    }

    /**
     * Add an undirected edge between two persons
     * remove the previous directed edge between the two persons (if exists) and add an undirected edge
     * @return the undirected edge between firstPerson and secondPerson
     */
    private Edge addUndirectedEdge(ReadOnlyPerson firstPerson, ReadOnlyPerson secondPerson) {
        String designatedEdgeID1 = computeEdgeID(firstPerson, secondPerson);
        Edge previousEdge1 = graph.getEdge(designatedEdgeID1);
        String designatedEdgeID2 = computeEdgeID(secondPerson, firstPerson);
        Edge previousEdge2 = graph.getEdge(designatedEdgeID2);
        if (previousEdge1 != null && previousEdge1.isDirected()) {
            graph.removeEdge(previousEdge1);
        }
        if (previousEdge2 != null && previousEdge2.isDirected()) {
            graph.removeEdge(previousEdge2);
        }
        try {
            graph.addEdge(designatedEdgeID1, getNodeIDFromPerson(firstPerson),
                    getNodeIDFromPerson(secondPerson), false);
        } catch (IllegalValueException ive) {
            assert false : "it should not happen.";
        }

        return graph.getEdge(designatedEdgeID1);
    }

    private SingleGraph initiateGraphEdges() {
        return graph;
    }
}
