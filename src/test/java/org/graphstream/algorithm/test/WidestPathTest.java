package org.graphstream.algorithm.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.WidestPath;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Test;

/**
 * Test for modified Dijkstra's algorithm for widest path.
 * Most of this based on the existing tests for original Dijkstra's, found <a href="https://goo.gl/uP5Gy6">here</a>.
 */
public class WidestPathTest {
    /**
     * A sample weighted graph for testing purposes.
     */
    private static Graph toyGraph() {
        //              B---9--E
        //             /|      |
        //            / |      |
        //           /  |      |
        //          14  2      6
        //         /    |      |
        //        /     |      |
        //       A---9--C--11--F
        //        \     |     /
        //         \    |    /
        //          7  10   15
        //           \  |  /
        //            \ | /
        //             \|/
        //              D      G
        Graph g = new SingleGraph("toy");
        g.addNode("A").addAttribute("xy", 0, 1);
        g.addNode("B").addAttribute("xy", 1, 2);
        g.addNode("C").addAttribute("xy", 1, 1);
        g.addNode("D").addAttribute("xy", 1, 0);
        g.addNode("E").addAttribute("xy", 2, 2);
        g.addNode("F").addAttribute("xy", 2, 1);
        g.addNode("G").addAttribute("xy", 2, 0);
        g.addEdge("AB", "A", "B").addAttribute("length", 14);
        g.addEdge("AC", "A", "C").addAttribute("length", 9);
        g.addEdge("AD", "A", "D").addAttribute("length", 7);
        g.addEdge("BC", "B", "C").addAttribute("length", 2);
        g.addEdge("CD", "C", "D").addAttribute("length", 10);
        g.addEdge("BE", "B", "E").addAttribute("length", 9);
        g.addEdge("CF", "C", "F").addAttribute("length", 11);
        g.addEdge("DF", "D", "F").addAttribute("length", 15);
        g.addEdge("EF", "E", "F").addAttribute("length", 6);
        for (Node n : g) {
            n.addAttribute("label", n.getId());
        }
        for (Edge e : g.getEachEdge()) {
            e.addAttribute("label", "" + (int) e.getNumber("length"));
        }
        return g;
    }

    //@@author Xenonym
    @Test
    public void widestPathTest() {
        Graph g = toyGraph();

        WidestPath d = new WidestPath(Dijkstra.Element.EDGE, "result", "length");
        d.init(g);
        Node source = g.getNode("A");
        d.setSource(source);

        // check the source node
        assertEquals(d.getSource(), source);

        d.compute();

        // check parent access methods
        assertNull(d.getParent(source));
        assertNull(d.getEdgeFromParent(source));
        assertEquals(source, d.getParent(g.getNode("C")));
        assertEquals(g.getEdge("CD"), d.getEdgeFromParent(g.getNode("D")));
        assertNull(d.getParent(g.getNode("G")));
        assertNull(d.getEdgeFromParent(g.getNode("G")));


        // check path widths
        assertEquals(Double.POSITIVE_INFINITY, d.getPathLength(g.getNode("A")), 0);
        assertEquals(14, d.getPathLength(g.getNode("B")), 0);
        assertEquals(9, d.getPathLength(g.getNode("C")), 0);
        assertEquals(9, d.getPathLength(g.getNode("D")), 0);
        assertEquals(9, d.getPathLength(g.getNode("E")), 0);
        assertEquals(9, d.getPathLength(g.getNode("F")), 0);
        assertEquals(Double.NEGATIVE_INFINITY, d.getPathLength(g.getNode("G")), 0);

        // check tree length
        assertEquals(53, d.getTreeLength(), 0);

        // check nodes on path A->E
        String[] nodesAe = {"E", "B", "A"};
        int i = 0;
        for (Node n : d.getPathNodes(g.getNode("E"))) {
            assertEquals(nodesAe[i], n.getId());
            i++;
        }
        assertEquals(3, i);

        // check edges on path A->F
        String[] edgesAf = {"CF", "AC"};
        i = 0;
        for (Edge e : d.getPathEdges(g.getNode("F"))) {
            assertEquals(edgesAf[i], e.getId());
            i++;
        }
        assertEquals(2, i);

        // check if path A->E is constructed correctly
        List<Node> ln = d.getPath(g.getNode("E")).getNodePath();
        assertEquals(3, ln.size());
        for (i = 0; i < 3; i++) {
            assertEquals(nodesAe[2 - i], ln.get(i).getId());
        }

        // There is no path A->G
        assertFalse(d.getPathNodesIterator(g.getNode("G")).hasNext());
        assertFalse(d.getPathEdgesIterator(g.getNode("G")).hasNext());

        d.clear();
        assertFalse(source.hasAttribute("result"));
    }
    //@@author
}
