package org.graphstream.algorithm;

import org.graphstream.algorithm.util.FibonacciHeap;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

//@@author Xenonym
/**
 * Modified Dijkstra's algorithm for computing widest paths.
 * The below algorithm is based on pseudocode from <a href="https://stackoverflow.com/a/18553217">here</a>.
 */
public class WidestPath extends Dijkstra {
    public WidestPath(Element element, String resultAttribute, String lengthAttribute) {
        super(element, resultAttribute, lengthAttribute);
    }

    @Override
    protected void makeTree() {
        // initialization
        FibonacciHeap<Double, Node> heap = new FibonacciHeap<>();
        for (Node node : graph) {
            Data data = new Data();
            double v = node == source ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
            data.fn = heap.add(-v, node);
            data.edgeFromParent = null;
            node.addAttribute(resultAttribute, data);
        }

        // main loop
        while (!heap.isEmpty()) {
            Node u = heap.extractMin();
            Data dataU = u.getAttribute(resultAttribute);
            dataU.distance = -dataU.fn.getKey();
            if (dataU.distance == Double.NEGATIVE_INFINITY) {
                break;
            }
            dataU.fn = null;
            if (dataU.edgeFromParent != null) {
                edgeOn(dataU.edgeFromParent);
            }
            for (Edge e : u.getEachLeavingEdge()) {
                Node v = e.getOpposite(u);
                Data dataV = v.getAttribute(resultAttribute);
                if (dataV.fn == null) {
                    continue;
                }
                double tryDist = Math.max(dataV.distance, Math.min(dataU.distance, getLength(e, v)));
                if (tryDist > -dataV.fn.getKey()) {
                    dataV.edgeFromParent = e;
                    heap.decreaseKey(dataV.fn, -tryDist);
                }
            }
        }
    }
}
